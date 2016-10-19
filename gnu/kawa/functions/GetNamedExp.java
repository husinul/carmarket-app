package gnu.kawa.functions;

import gnu.bytecode.Access;
import gnu.bytecode.Type;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.InlineCalls;
import gnu.expr.PrimProcedure;
import gnu.expr.ReferenceExp;
import gnu.mapping.CallContext;
import gnu.mapping.Environment;
import gnu.mapping.Location;
import gnu.mapping.Symbol;
import gnu.text.PrettyWriter;

/* compiled from: CompileNamedPart */
class GetNamedExp extends ApplyExp {
    static final Declaration castDecl;
    static final Declaration fieldDecl;
    static final Declaration instanceOfDecl;
    static final Declaration invokeDecl;
    static final Declaration invokeStaticDecl;
    static final Declaration makeDecl;
    static final Declaration staticFieldDecl;
    public String combinedName;
    char kind;
    PrimProcedure[] methods;

    public void apply(CallContext ctx) throws Throwable {
        if (this.combinedName != null) {
            Environment env = Environment.getCurrent();
            Symbol sym = env.getSymbol(this.combinedName);
            String unb = Location.UNBOUND;
            String value = env.get(sym, null, unb);
            if (value != unb) {
                ctx.writeValue(value);
                return;
            }
        }
        super.apply(ctx);
    }

    public GetNamedExp(Expression[] args) {
        super(GetNamedPart.getNamedPart, args);
    }

    protected GetNamedExp setProcedureKind(char kind) {
        this.type = Compilation.typeProcedure;
        this.kind = kind;
        return this;
    }

    public Expression validateApply(ApplyExp exp, InlineCalls visitor, Type required, Declaration decl) {
        Expression[] xargs;
        Expression[] pargs = getArgs();
        Expression context = pargs[0];
        Expression[] args = exp.getArgs();
        switch (this.kind) {
            case 'C':
                decl = castDecl;
                xargs = new Expression[(args.length + 1)];
                System.arraycopy(args, 1, xargs, 2, args.length - 1);
                xargs[0] = context;
                xargs[1] = args[0];
                break;
            case 'I':
                decl = instanceOfDecl;
                xargs = new Expression[(args.length + 1)];
                System.arraycopy(args, 1, xargs, 2, args.length - 1);
                xargs[0] = args[0];
                xargs[1] = context;
                break;
            case PrettyWriter.NEWLINE_MISER /*77*/:
                decl = invokeDecl;
                xargs = new Expression[(args.length + 2)];
                xargs[0] = pargs[0];
                xargs[1] = pargs[1];
                System.arraycopy(args, 0, xargs, 2, args.length);
                break;
            case PrettyWriter.NEWLINE_LINEAR /*78*/:
                decl = makeDecl;
                xargs = new Expression[(args.length + 1)];
                System.arraycopy(args, 0, xargs, 1, args.length);
                xargs[0] = context;
                break;
            case PrettyWriter.NEWLINE_SPACE /*83*/:
                decl = invokeStaticDecl;
                xargs = new Expression[(args.length + 2)];
                xargs[0] = context;
                xargs[1] = pargs[1];
                System.arraycopy(args, 0, xargs, 2, args.length);
                break;
            default:
                return exp;
        }
        Expression result = new ApplyExp(new ReferenceExp(decl), xargs);
        result.setLine((Expression) exp);
        return visitor.visit(result, required);
    }

    public boolean side_effects() {
        if (this.kind == 'S' || this.kind == 'N' || this.kind == Access.CLASS_CONTEXT || this.kind == Access.INNERCLASS_CONTEXT) {
            return false;
        }
        if (this.kind == Access.METHOD_CONTEXT) {
            return getArgs()[0].side_effects();
        }
        return true;
    }

    static {
        fieldDecl = Declaration.getDeclarationFromStatic("gnu.kawa.reflect.SlotGet", "field");
        staticFieldDecl = Declaration.getDeclarationFromStatic("gnu.kawa.reflect.SlotGet", "staticField");
        makeDecl = Declaration.getDeclarationFromStatic("gnu.kawa.reflect.Invoke", "make");
        invokeDecl = Declaration.getDeclarationFromStatic("gnu.kawa.reflect.Invoke", "invoke");
        invokeStaticDecl = Declaration.getDeclarationFromStatic("gnu.kawa.reflect.Invoke", "invokeStatic");
        instanceOfDecl = Declaration.getDeclarationFromStatic("kawa.standard.Scheme", "instanceOf");
        castDecl = Declaration.getDeclarationFromStatic("gnu.kawa.functions.Convert", "as");
    }
}
