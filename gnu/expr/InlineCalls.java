package gnu.expr;

import gnu.bytecode.Access;
import gnu.bytecode.ClassType;
import gnu.bytecode.Method;
import gnu.bytecode.PrimType;
import gnu.bytecode.Type;
import gnu.kawa.reflect.CompileReflect;
import gnu.kawa.reflect.Invoke;
import gnu.kawa.util.IdentityHashTable;
import gnu.kawa.xml.ElementType;
import gnu.mapping.Procedure;
import gnu.mapping.Values;
import gnu.math.IntNum;
import java.lang.reflect.InvocationTargetException;

public class InlineCalls extends ExpExpVisitor<Type> {
    private static Class[] inlinerMethodArgTypes;

    public static Expression inlineCalls(Expression exp, Compilation comp) {
        return new InlineCalls(comp).visit(exp, null);
    }

    public InlineCalls(Compilation comp) {
        setContext(comp);
    }

    public Expression visit(Expression exp, Type required) {
        if (!exp.getFlag(1)) {
            exp.setFlag(1);
            exp = (Expression) super.visit(exp, required);
            exp.setFlag(1);
        }
        return checkType(exp, required);
    }

    public Expression checkType(Expression exp, Type required) {
        boolean incompatible = true;
        Type expType = exp.getType();
        if ((required instanceof ClassType) && ((ClassType) required).isInterface() && expType.isSubtype(Compilation.typeProcedure) && !expType.isSubtype(required)) {
            if (exp instanceof LambdaExp) {
                Method amethod = ((ClassType) required).checkSingleAbstractMethod();
                if (amethod != null) {
                    LambdaExp lexp = (LambdaExp) exp;
                    Expression oexp = new ObjectExp();
                    oexp.setLocation(exp);
                    oexp.supers = new Expression[]{new QuoteExp(required)};
                    oexp.setTypes(getCompilation());
                    String mname = amethod.getName();
                    oexp.addMethod(lexp, mname);
                    Declaration mdecl = oexp.addDeclaration(mname, Compilation.typeProcedure);
                    oexp.firstChild = lexp;
                    oexp.declareParts(this.comp);
                    return visit(oexp, required);
                }
            }
            incompatible = true;
        } else {
            if (expType == Type.toStringType) {
                expType = Type.javalangStringType;
            }
            if (required == null || required.compare(expType) != -3) {
                incompatible = false;
            }
            if (incompatible && (required instanceof TypeValue)) {
                Expression converted = ((TypeValue) required).convertValue(exp);
                if (converted != null) {
                    return converted;
                }
            }
        }
        if (incompatible) {
            Language language = this.comp.getLanguage();
            this.comp.error('w', "type " + language.formatType(expType) + " is incompatible with required type " + language.formatType(required), exp);
        }
        return exp;
    }

    protected Expression visitApplyExp(ApplyExp exp, Type required) {
        Expression func = exp.func;
        if (func instanceof LambdaExp) {
            LambdaExp lexp = (LambdaExp) func;
            Expression inlined = inlineCall((LambdaExp) func, exp.args, false);
            if (inlined != null) {
                return visit(inlined, required);
            }
        }
        func = visit(func, null);
        exp.func = func;
        return func.validateApply(exp, this, required, null);
    }

    public final Expression visitApplyOnly(ApplyExp exp, Type required) {
        return exp.func.validateApply(exp, this, required, null);
    }

    public static Integer checkIntValue(Expression exp) {
        if (exp instanceof QuoteExp) {
            QuoteExp qarg = (QuoteExp) exp;
            IntNum value = qarg.getValue();
            if (!qarg.isExplicitlyTyped() && (value instanceof IntNum)) {
                IntNum ivalue = value;
                if (ivalue.inIntRange()) {
                    return Integer.valueOf(ivalue.intValue());
                }
            }
        }
        return null;
    }

    public static Long checkLongValue(Expression exp) {
        if (exp instanceof QuoteExp) {
            QuoteExp qarg = (QuoteExp) exp;
            IntNum value = qarg.getValue();
            if (!qarg.isExplicitlyTyped() && (value instanceof IntNum)) {
                IntNum ivalue = value;
                if (ivalue.inLongRange()) {
                    return Long.valueOf(ivalue.longValue());
                }
            }
        }
        return null;
    }

    public QuoteExp fixIntValue(Expression exp) {
        Integer ival = checkIntValue(exp);
        if (ival != null) {
            return new QuoteExp(ival, this.comp.getLanguage().getTypeFor(Integer.TYPE));
        }
        return null;
    }

    public QuoteExp fixLongValue(Expression exp) {
        Long ival = checkLongValue(exp);
        if (ival != null) {
            return new QuoteExp(ival, this.comp.getLanguage().getTypeFor(Long.TYPE));
        }
        return null;
    }

    protected Expression visitQuoteExp(QuoteExp exp, Type required) {
        if (exp.getRawType() != null || exp.isSharedConstant()) {
            return exp;
        }
        Object value = exp.getValue();
        if (value == null) {
            return exp;
        }
        Type vtype = this.comp.getLanguage().getTypeFor(value.getClass());
        if (vtype == Type.toStringType) {
            vtype = Type.javalangStringType;
        }
        exp.type = vtype;
        if (!(required instanceof PrimType)) {
            return exp;
        }
        char sig1 = required.getSignature().charAt(0);
        QuoteExp ret = sig1 == Access.INNERCLASS_CONTEXT ? fixIntValue(exp) : sig1 == 'J' ? fixLongValue(exp) : null;
        if (ret != null) {
            return ret;
        }
        return exp;
    }

    protected Expression visitReferenceExp(ReferenceExp exp, Type required) {
        Declaration decl = exp.getBinding();
        if (!(decl == null || decl.field != null || decl.getCanWrite())) {
            Expression dval = decl.getValue();
            if ((dval instanceof QuoteExp) && dval != QuoteExp.undefined_exp) {
                return visitQuoteExp((QuoteExp) dval, required);
            }
            if ((dval instanceof ReferenceExp) && !decl.isAlias()) {
                ReferenceExp rval = (ReferenceExp) dval;
                Declaration rdecl = rval.getBinding();
                Type dtype = decl.getType();
                if (!(rdecl == null || rdecl.getCanWrite() || ((dtype != null && dtype != Type.pointer_type && dtype != rdecl.getType()) || rval.getDontDereference()))) {
                    return visitReferenceExp(rval, required);
                }
            }
            if (!exp.isProcedureName() && (decl.flags & 1048704) == 1048704) {
                this.comp.error('e', "unimplemented: reference to method " + decl.getName() + " as variable");
                this.comp.error('e', decl, "here is the definition of ", ElementType.MATCH_ANY_LOCALNAME);
            }
        }
        return (Expression) super.visitReferenceExp(exp, required);
    }

    protected Expression visitIfExp(IfExp exp, Type required) {
        Expression test = (Expression) exp.test.visit(this, null);
        if (test instanceof ReferenceExp) {
            Declaration decl = ((ReferenceExp) test).getBinding();
            if (decl != null) {
                Expression value = decl.getValue();
                if ((value instanceof QuoteExp) && value != QuoteExp.undefined_exp) {
                    test = value;
                }
            }
        }
        exp.test = test;
        if (this.exitValue == null) {
            exp.then_clause = visit(exp.then_clause, required);
        }
        if (this.exitValue == null && exp.else_clause != null) {
            exp.else_clause = visit(exp.else_clause, required);
        }
        if (test instanceof QuoteExp) {
            return exp.select(this.comp.getLanguage().isTrue(((QuoteExp) test).getValue()));
        }
        if (!test.getType().isVoid()) {
            return exp;
        }
        boolean truth = this.comp.getLanguage().isTrue(Values.empty);
        this.comp.error('w', "void-valued condition is always " + truth);
        return new BeginExp(test, exp.select(truth));
    }

    protected Expression visitBeginExp(BeginExp exp, Type required) {
        int last = exp.length - 1;
        for (int i = 0; i <= last; i++) {
            Type type;
            Expression[] expressionArr = exp.exps;
            Expression expression = exp.exps[i];
            if (i < last) {
                type = null;
            } else {
                type = required;
            }
            expressionArr[i] = visit(expression, type);
        }
        return exp;
    }

    protected Expression visitScopeExp(ScopeExp exp, Type required) {
        exp.visitChildren(this, null);
        visitDeclarationTypes(exp);
        for (Declaration decl = exp.firstDecl(); decl != null; decl = decl.nextDecl()) {
            if (decl.type == null) {
                Type type;
                Expression val = decl.getValue();
                decl.type = Type.objectType;
                if (val == null || val == QuoteExp.undefined_exp) {
                    type = Type.objectType;
                } else {
                    type = val.getType();
                }
                decl.setType(type);
            }
        }
        return exp;
    }

    protected Expression visitLetExp(LetExp exp, Type required) {
        Declaration decl = exp.firstDecl();
        int n = exp.inits.length;
        int i = 0;
        while (i < n) {
            Expression init0 = exp.inits[i];
            boolean typeSpecified = decl.getFlag(8192);
            Type dtype = (!typeSpecified || init0 == QuoteExp.undefined_exp) ? null : decl.getType();
            Expression init = visit(init0, dtype);
            exp.inits[i] = init;
            if (decl.value == init0) {
                Expression dvalue = init;
                decl.value = init;
                if (!typeSpecified) {
                    decl.setType(dvalue.getType());
                }
            }
            i++;
            decl = decl.nextDecl();
        }
        if (this.exitValue == null) {
            exp.body = visit(exp.body, required);
        }
        if (exp.body instanceof ReferenceExp) {
            ReferenceExp ref = exp.body;
            Declaration d = ref.getBinding();
            if (d != null && d.context == exp && !ref.getDontDereference() && n == 1) {
                init = exp.inits[0];
                Expression texp = d.getTypeExp();
                if (texp != QuoteExp.classObjectExp) {
                    return visitApplyOnly(Compilation.makeCoercion(init, texp), null);
                }
                return init;
            }
        }
        return exp;
    }

    protected Expression visitLambdaExp(LambdaExp exp, Type required) {
        Declaration firstDecl = exp.firstDecl();
        if (firstDecl != null && firstDecl.isThisParameter() && !exp.isClassMethod() && firstDecl.type == null) {
            firstDecl.setType(this.comp.mainClass);
        }
        return visitScopeExp((ScopeExp) exp, required);
    }

    protected Expression visitTryExp(TryExp exp, Type required) {
        if (exp.getCatchClauses() == null && exp.getFinallyClause() == null) {
            return visit(exp.try_clause, required);
        }
        return (Expression) super.visitTryExp(exp, required);
    }

    protected Expression visitSetExpValue(Expression new_value, Type required, Declaration decl) {
        Type type = (decl == null || decl.isAlias()) ? null : decl.type;
        return visit(new_value, type);
    }

    protected Expression visitSetExp(SetExp exp, Type required) {
        Declaration decl = exp.getBinding();
        super.visitSetExp(exp, required);
        if (!(exp.isDefining() || decl == null || (decl.flags & 1048704) != 1048704)) {
            this.comp.error('e', "can't assign to method " + decl.getName(), exp);
        }
        if (decl != null && decl.getFlag(8192) && CompileReflect.checkKnownClass(decl.getType(), this.comp) < 0) {
            decl.setType(Type.errorType);
        }
        return exp;
    }

    private static synchronized Class[] getInlinerMethodArgTypes() throws Exception {
        Class[] t;
        synchronized (InlineCalls.class) {
            t = inlinerMethodArgTypes;
            if (t == null) {
                t = new Class[]{Class.forName("gnu.expr.ApplyExp"), Class.forName("gnu.expr.InlineCalls"), Class.forName("gnu.bytecode.Type"), Class.forName("gnu.mapping.Procedure")};
                inlinerMethodArgTypes = t;
            }
        }
        return t;
    }

    public Expression maybeInline(ApplyExp exp, Type required, Procedure proc) {
        try {
            synchronized (proc) {
                Object property = proc.getProperty(Procedure.validateApplyKey, null);
                if (property instanceof String) {
                    String inliners = (String) property;
                    int colon = inliners.indexOf(58);
                    java.lang.reflect.Method method = null;
                    if (colon > 0) {
                        String cname = inliners.substring(0, colon);
                        method = Class.forName(cname, true, proc.getClass().getClassLoader()).getDeclaredMethod(inliners.substring(colon + 1), getInlinerMethodArgTypes());
                    }
                    if (method == null) {
                        error('e', "inliner property string for " + proc + " is not of the form CLASS:METHOD");
                        return null;
                    }
                    property = method;
                }
                if (property != null) {
                    Object[] vargs = new Object[]{exp, this, required, proc};
                    if (property instanceof Procedure) {
                        return (Expression) ((Procedure) property).applyN(vargs);
                    }
                    if (property instanceof java.lang.reflect.Method) {
                        return (Expression) ((java.lang.reflect.Method) property).invoke(null, vargs);
                    }
                }
                return null;
            }
        } catch (Throwable th) {
            Throwable ex = th;
            if (ex instanceof InvocationTargetException) {
                ex = ((InvocationTargetException) ex).getTargetException();
            }
            this.messages.error('e', "caught exception in inliner for " + proc + " - " + ex, ex);
        }
    }

    public static Expression inlineCall(LambdaExp lexp, Expression[] args, boolean makeCopy) {
        if (lexp.keywords != null || (lexp.nameDecl != null && !makeCopy)) {
            return null;
        }
        boolean varArgs = lexp.max_args < 0;
        if ((lexp.min_args != lexp.max_args || lexp.min_args != args.length) && (!varArgs || lexp.min_args != 0)) {
            return null;
        }
        Expression[] deepCopy;
        Declaration prev = null;
        int i = 0;
        if (makeCopy) {
            IdentityHashTable mapper = new IdentityHashTable();
            deepCopy = Expression.deepCopy(args, mapper);
            if (deepCopy == null && args != null) {
                return null;
            }
        }
        mapper = null;
        deepCopy = args;
        if (varArgs) {
            Expression[] xargs = new Expression[(args.length + 1)];
            xargs[0] = QuoteExp.getInstance(lexp.firstDecl().type);
            System.arraycopy(args, 0, xargs, 1, args.length);
            deepCopy = new Expression[]{new ApplyExp(Invoke.make, xargs)};
        }
        Expression let = new LetExp(deepCopy);
        Declaration param = lexp.firstDecl();
        while (param != null) {
            Declaration next = param.nextDecl();
            if (makeCopy) {
                Declaration ldecl = let.addDeclaration(param.symbol, param.type);
                if (param.typeExp != null) {
                    ldecl.typeExp = Expression.deepCopy(param.typeExp);
                    if (ldecl.typeExp == null) {
                        return null;
                    }
                }
                mapper.put(param, ldecl);
            } else {
                lexp.remove(prev, param);
                let.add(prev, param);
            }
            if (!(varArgs || param.getCanWrite())) {
                param.setValue(deepCopy[i]);
            }
            prev = param;
            param = next;
            i++;
        }
        Expression body = lexp.body;
        if (makeCopy) {
            body = Expression.deepCopy(body, mapper);
            if (body == null && lexp.body != null) {
                return null;
            }
        }
        let.body = body;
        return let;
    }
}
