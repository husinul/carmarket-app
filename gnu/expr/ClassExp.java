package gnu.expr;

import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Field;
import gnu.bytecode.Member;
import gnu.bytecode.Method;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.kawa.xml.ElementType;
import gnu.mapping.OutPort;
import java.util.Hashtable;
import java.util.Vector;

public class ClassExp extends LambdaExp {
    public static final int CLASS_SPECIFIED = 65536;
    public static final int HAS_SUBCLASS = 131072;
    public static final int INTERFACE_SPECIFIED = 32768;
    public static final int IS_ABSTRACT = 16384;
    public String classNameSpecifier;
    public LambdaExp clinitMethod;
    boolean explicitInit;
    public LambdaExp initMethod;
    ClassType instanceType;
    boolean partsDeclared;
    boolean simple;
    public int superClassIndex;
    public Expression[] supers;

    public boolean isSimple() {
        return this.simple;
    }

    public void setSimple(boolean value) {
        this.simple = value;
    }

    public final boolean isAbstract() {
        return getFlag(IS_ABSTRACT);
    }

    public boolean isMakingClassPair() {
        return this.type != this.instanceType;
    }

    public Type getType() {
        return this.simple ? Compilation.typeClass : Compilation.typeClassType;
    }

    public ClassType getClassType() {
        return this.type;
    }

    public ClassExp() {
        this.superClassIndex = -1;
    }

    public ClassExp(boolean simple) {
        this.superClassIndex = -1;
        this.simple = simple;
        ClassType classType = new ClassType();
        this.type = classType;
        this.instanceType = classType;
    }

    protected boolean mustCompile() {
        return true;
    }

    public void compile(Compilation comp, Target target) {
        if (!(target instanceof IgnoreTarget)) {
            compileMembers(comp);
            compilePushClass(comp, target);
        }
    }

    public void compilePushClass(Compilation comp, Target target) {
        ClassType new_class = this.type;
        CodeAttr code = comp.getCode();
        comp.loadClassRef(new_class);
        boolean needsLink = getNeedsClosureEnv();
        if (!isSimple() || needsLink) {
            Type typeType;
            int nargs;
            if (isMakingClassPair() || needsLink) {
                if (new_class == this.instanceType) {
                    code.emitDup(this.instanceType);
                } else {
                    comp.loadClassRef(this.instanceType);
                }
                typeType = ClassType.make("gnu.expr.PairClassType");
                nargs = needsLink ? 3 : 2;
            } else {
                typeType = ClassType.make("gnu.bytecode.Type");
                nargs = 1;
            }
            Type[] argsClass = new Type[nargs];
            if (needsLink) {
                getOwningLambda().loadHeapFrame(comp);
                nargs--;
                argsClass[nargs] = Type.pointer_type;
            }
            ClassType typeClass = ClassType.make("java.lang.Class");
            while (true) {
                nargs--;
                if (nargs >= 0) {
                    argsClass[nargs] = typeClass;
                } else {
                    code.emitInvokeStatic(typeType.addMethod("make", argsClass, typeType, 9));
                    target.compileFromStack(comp, typeType);
                    return;
                }
            }
        }
    }

    protected ClassType getCompiledClassType(Compilation comp) {
        return this.type;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setTypes(gnu.expr.Compilation r26) {
        /*
        r25 = this;
        r0 = r25;
        r0 = r0.supers;
        r22 = r0;
        if (r22 != 0) goto L_0x004d;
    L_0x0008:
        r15 = 0;
    L_0x0009:
        r0 = new gnu.bytecode.ClassType[r15];
        r20 = r0;
        r19 = 0;
        r7 = 0;
        r5 = 0;
        r8 = r7;
    L_0x0012:
        if (r5 >= r15) goto L_0x00bd;
    L_0x0014:
        r22 = gnu.expr.Language.getDefaultLanguage();
        r0 = r25;
        r0 = r0.supers;
        r23 = r0;
        r23 = r23[r5];
        r17 = r22.getTypeFor(r23);
        r0 = r17;
        r0 = r0 instanceof gnu.bytecode.ClassType;
        r22 = r0;
        if (r22 != 0) goto L_0x0057;
    L_0x002c:
        r0 = r25;
        r0 = r0.supers;
        r22 = r0;
        r22 = r22[r5];
        r0 = r26;
        r1 = r22;
        r0.setLine(r1);
        r22 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r23 = "invalid super type";
        r0 = r26;
        r1 = r22;
        r2 = r23;
        r0.error(r1, r2);
        r7 = r8;
    L_0x0049:
        r5 = r5 + 1;
        r8 = r7;
        goto L_0x0012;
    L_0x004d:
        r0 = r25;
        r0 = r0.supers;
        r22 = r0;
        r0 = r22;
        r15 = r0.length;
        goto L_0x0009;
    L_0x0057:
        r21 = r17;
        r21 = (gnu.bytecode.ClassType) r21;
        r11 = r21.getModifiers();	 Catch:{ RuntimeException -> 0x0091 }
    L_0x005f:
        r0 = r11 & 512;
        r22 = r0;
        if (r22 != 0) goto L_0x00b8;
    L_0x0065:
        if (r8 >= r5) goto L_0x0089;
    L_0x0067:
        r22 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r24 = "duplicate superclass for ";
        r23 = r23.append(r24);
        r0 = r23;
        r1 = r25;
        r23 = r0.append(r1);
        r23 = r23.toString();
        r0 = r26;
        r1 = r22;
        r2 = r23;
        r0.error(r1, r2);
    L_0x0089:
        r19 = r21;
        r0 = r25;
        r0.superClassIndex = r5;
        r7 = r8;
        goto L_0x0049;
    L_0x0091:
        r4 = move-exception;
        r11 = 0;
        if (r26 == 0) goto L_0x005f;
    L_0x0095:
        r22 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r24 = "unknown super-type ";
        r23 = r23.append(r24);
        r24 = r21.getName();
        r23 = r23.append(r24);
        r23 = r23.toString();
        r0 = r26;
        r1 = r22;
        r2 = r23;
        r0.error(r1, r2);
        goto L_0x005f;
    L_0x00b8:
        r7 = r8 + 1;
        r20[r8] = r21;
        goto L_0x0049;
    L_0x00bd:
        if (r19 == 0) goto L_0x00d9;
    L_0x00bf:
        r0 = r25;
        r0 = r0.flags;
        r22 = r0;
        r23 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
        r22 = r22 & r23;
        if (r22 == 0) goto L_0x00d9;
    L_0x00cc:
        r22 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r23 = "cannot be interface since has superclass";
        r0 = r26;
        r1 = r22;
        r2 = r23;
        r0.error(r1, r2);
    L_0x00d9:
        r0 = r25;
        r0 = r0.simple;
        r22 = r0;
        if (r22 != 0) goto L_0x021f;
    L_0x00e1:
        if (r19 != 0) goto L_0x021f;
    L_0x00e3:
        r0 = r25;
        r0 = r0.flags;
        r22 = r0;
        r23 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
        r22 = r22 & r23;
        if (r22 != 0) goto L_0x021f;
    L_0x00ef:
        r22 = 131072; // 0x20000 float:1.83671E-40 double:6.47582E-319;
        r0 = r25;
        r1 = r22;
        r22 = r0.getFlag(r1);
        if (r22 != 0) goto L_0x010f;
    L_0x00fb:
        r0 = r25;
        r0 = r0.nameDecl;
        r22 = r0;
        if (r22 == 0) goto L_0x021f;
    L_0x0103:
        r0 = r25;
        r0 = r0.nameDecl;
        r22 = r0;
        r22 = r22.isPublic();
        if (r22 == 0) goto L_0x021f;
    L_0x010f:
        r16 = new gnu.expr.PairClassType;
        r16.<init>();
        r0 = r16;
        r1 = r25;
        r1.type = r0;
        r22 = 1;
        r0 = r16;
        r1 = r22;
        r0.setInterface(r1);
        r0 = r25;
        r0 = r0.instanceType;
        r22 = r0;
        r0 = r22;
        r1 = r16;
        r1.instanceType = r0;
        r22 = 1;
        r0 = r22;
        r6 = new gnu.bytecode.ClassType[r0];
        r22 = 0;
        r0 = r25;
        r0 = r0.type;
        r23 = r0;
        r6[r22] = r23;
        r0 = r25;
        r0 = r0.instanceType;
        r22 = r0;
        r23 = gnu.bytecode.Type.pointer_type;
        r22.setSuper(r23);
        r0 = r25;
        r0 = r0.instanceType;
        r22 = r0;
        r0 = r22;
        r0.setInterfaces(r6);
    L_0x0155:
        r0 = r25;
        r0 = r0.type;
        r22 = r0;
        if (r19 != 0) goto L_0x015f;
    L_0x015d:
        r19 = gnu.bytecode.Type.pointer_type;
    L_0x015f:
        r0 = r22;
        r1 = r19;
        r0.setSuper(r1);
        if (r8 != r15) goto L_0x0239;
    L_0x0168:
        r6 = r20;
    L_0x016a:
        r0 = r25;
        r0 = r0.type;
        r22 = r0;
        r0 = r22;
        r0.setInterfaces(r6);
        r0 = r25;
        r0 = r0.type;
        r22 = r0;
        r22 = r22.getName();
        if (r22 != 0) goto L_0x021e;
    L_0x0181:
        r0 = r25;
        r0 = r0.classNameSpecifier;
        r22 = r0;
        if (r22 == 0) goto L_0x024a;
    L_0x0189:
        r0 = r25;
        r12 = r0.classNameSpecifier;
    L_0x018d:
        if (r12 != 0) goto L_0x028f;
    L_0x018f:
        r13 = new java.lang.StringBuffer;
        r22 = 100;
        r0 = r22;
        r13.<init>(r0);
        r22 = r26.getModule();
        r0 = r22;
        r1 = r26;
        r0.classFor(r1);
        r0 = r26;
        r0 = r0.mainClass;
        r22 = r0;
        r22 = r22.getName();
        r0 = r22;
        r13.append(r0);
        r22 = 36;
        r0 = r22;
        r13.append(r0);
        r9 = r13.length();
        r5 = 0;
    L_0x01be:
        r13.append(r5);
        r12 = r13.toString();
        r0 = r26;
        r22 = r0.findNamedClass(r12);
        if (r22 != 0) goto L_0x0288;
    L_0x01cd:
        r0 = r25;
        r0 = r0.type;
        r22 = r0;
        r0 = r22;
        r0.setName(r12);
        r0 = r25;
        r0 = r0.type;
        r22 = r0;
        r0 = r26;
        r1 = r22;
        r0.addClass(r1);
        r22 = r25.isMakingClassPair();
        if (r22 == 0) goto L_0x021e;
    L_0x01eb:
        r0 = r25;
        r0 = r0.instanceType;
        r22 = r0;
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r0 = r25;
        r0 = r0.type;
        r24 = r0;
        r24 = r24.getName();
        r23 = r23.append(r24);
        r24 = "$class";
        r23 = r23.append(r24);
        r23 = r23.toString();
        r22.setName(r23);
        r0 = r25;
        r0 = r0.instanceType;
        r22 = r0;
        r0 = r26;
        r1 = r22;
        r0.addClass(r1);
    L_0x021e:
        return;
    L_0x021f:
        r22 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
        r0 = r25;
        r1 = r22;
        r22 = r0.getFlag(r1);
        if (r22 == 0) goto L_0x0155;
    L_0x022c:
        r0 = r25;
        r0 = r0.instanceType;
        r22 = r0;
        r23 = 1;
        r22.setInterface(r23);
        goto L_0x0155;
    L_0x0239:
        r6 = new gnu.bytecode.ClassType[r8];
        r22 = 0;
        r23 = 0;
        r0 = r20;
        r1 = r22;
        r2 = r23;
        java.lang.System.arraycopy(r0, r1, r6, r2, r8);
        goto L_0x016a;
    L_0x024a:
        r12 = r25.getName();
        if (r12 == 0) goto L_0x018d;
    L_0x0250:
        r14 = r12.length();
        r22 = 2;
        r0 = r22;
        if (r14 <= r0) goto L_0x018d;
    L_0x025a:
        r22 = 0;
        r0 = r22;
        r22 = r12.charAt(r0);
        r23 = 60;
        r0 = r22;
        r1 = r23;
        if (r0 != r1) goto L_0x018d;
    L_0x026a:
        r22 = r14 + -1;
        r0 = r22;
        r22 = r12.charAt(r0);
        r23 = 62;
        r0 = r22;
        r1 = r23;
        if (r0 != r1) goto L_0x018d;
    L_0x027a:
        r22 = 1;
        r23 = r14 + -1;
        r0 = r22;
        r1 = r23;
        r12 = r12.substring(r0, r1);
        goto L_0x018d;
    L_0x0288:
        r13.setLength(r9);
        r5 = r5 + 1;
        goto L_0x01be;
    L_0x028f:
        r22 = r25.isSimple();
        if (r22 == 0) goto L_0x029d;
    L_0x0295:
        r0 = r25;
        r0 = r0 instanceof gnu.expr.ObjectExp;
        r22 = r0;
        if (r22 == 0) goto L_0x02a5;
    L_0x029d:
        r0 = r26;
        r12 = r0.generateClassName(r12);
        goto L_0x01cd;
    L_0x02a5:
        r18 = 0;
        r13 = new java.lang.StringBuffer;
        r22 = 100;
        r0 = r22;
        r13.<init>(r0);
    L_0x02b0:
        r22 = 46;
        r0 = r22;
        r1 = r18;
        r3 = r12.indexOf(r0, r1);
        if (r3 >= 0) goto L_0x02fc;
    L_0x02bc:
        if (r18 != 0) goto L_0x0347;
    L_0x02be:
        r0 = r26;
        r0 = r0.mainClass;
        r22 = r0;
        if (r22 != 0) goto L_0x031f;
    L_0x02c6:
        r10 = 0;
    L_0x02c7:
        if (r10 != 0) goto L_0x032a;
    L_0x02c9:
        r3 = -1;
    L_0x02ca:
        if (r3 <= 0) goto L_0x0333;
    L_0x02cc:
        r22 = 0;
        r23 = r3 + 1;
        r0 = r22;
        r1 = r23;
        r22 = r10.substring(r0, r1);
        r0 = r22;
        r13.append(r0);
    L_0x02dd:
        r22 = r12.length();
        r0 = r18;
        r1 = r22;
        if (r0 >= r1) goto L_0x02f6;
    L_0x02e7:
        r0 = r18;
        r22 = r12.substring(r0);
        r22 = gnu.expr.Compilation.mangleNameIfNeeded(r22);
        r0 = r22;
        r13.append(r0);
    L_0x02f6:
        r12 = r13.toString();
        goto L_0x01cd;
    L_0x02fc:
        r0 = r18;
        r22 = r12.substring(r0, r3);
        r22 = gnu.expr.Compilation.mangleNameIfNeeded(r22);
        r0 = r22;
        r13.append(r0);
        r18 = r3 + 1;
        r22 = r12.length();
        r0 = r18;
        r1 = r22;
        if (r0 >= r1) goto L_0x02b0;
    L_0x0317:
        r22 = 46;
        r0 = r22;
        r13.append(r0);
        goto L_0x02b0;
    L_0x031f:
        r0 = r26;
        r0 = r0.mainClass;
        r22 = r0;
        r10 = r22.getName();
        goto L_0x02c7;
    L_0x032a:
        r22 = 46;
        r0 = r22;
        r3 = r10.lastIndexOf(r0);
        goto L_0x02ca;
    L_0x0333:
        r0 = r26;
        r0 = r0.classPrefix;
        r22 = r0;
        if (r22 == 0) goto L_0x02dd;
    L_0x033b:
        r0 = r26;
        r0 = r0.classPrefix;
        r22 = r0;
        r0 = r22;
        r13.append(r0);
        goto L_0x02dd;
    L_0x0347:
        r22 = 1;
        r0 = r18;
        r1 = r22;
        if (r0 != r1) goto L_0x02dd;
    L_0x034f:
        r22 = r12.length();
        r0 = r18;
        r1 = r22;
        if (r0 >= r1) goto L_0x02dd;
    L_0x0359:
        r22 = 0;
        r0 = r22;
        r13.setLength(r0);
        r0 = r26;
        r0 = r0.mainClass;
        r22 = r0;
        r22 = r22.getName();
        r0 = r22;
        r13.append(r0);
        r22 = 36;
        r0 = r22;
        r13.append(r0);
        goto L_0x02dd;
        */
        throw new UnsupportedOperationException("Method not decompiled: gnu.expr.ClassExp.setTypes(gnu.expr.Compilation):void");
    }

    public void declareParts(Compilation comp) {
        if (!this.partsDeclared) {
            this.partsDeclared = true;
            Hashtable<String, Declaration> seenFields = new Hashtable();
            for (Declaration decl = firstDecl(); decl != null; decl = decl.nextDecl()) {
                if (decl.getCanRead()) {
                    int flags = decl.getAccessFlags((short) 1);
                    if (decl.getFlag(2048)) {
                        flags |= 8;
                    }
                    if (isMakingClassPair()) {
                        flags |= LambdaExp.SEQUENCE_RESULT;
                        this.type.addMethod(slotToMethodName("get", decl.getName()), flags, Type.typeArray0, decl.getType().getImplementationType());
                        this.type.addMethod(slotToMethodName("set", decl.getName()), flags, new Type[]{ftype}, Type.voidType);
                    } else {
                        String fname = Compilation.mangleNameIfNeeded(decl.getName());
                        decl.field = this.instanceType.addField(fname, decl.getType(), flags);
                        decl.setSimple(false);
                        Declaration old = (Declaration) seenFields.get(fname);
                        if (old != null) {
                            ScopeExp.duplicateDeclarationError(old, decl, comp);
                        }
                        seenFields.put(fname, decl);
                    }
                }
            }
            LambdaExp child = this.firstChild;
            while (child != null) {
                if (child.isAbstract()) {
                    setFlag(IS_ABSTRACT);
                }
                if ("*init*".equals(child.getName())) {
                    this.explicitInit = true;
                    if (child.isAbstract()) {
                        comp.error('e', "*init* method cannot be abstract", child);
                    }
                    if (this.type instanceof PairClassType) {
                        comp.error('e', "'*init*' methods only supported for simple classes");
                    }
                }
                child.outer = this;
                if (!((child == this.initMethod || child == this.clinitMethod || child.nameDecl == null || child.nameDecl.getFlag(2048)) && isMakingClassPair())) {
                    child.addMethodFor(this.type, comp, null);
                }
                if (isMakingClassPair()) {
                    child.addMethodFor(this.instanceType, comp, this.type);
                }
                child = child.nextSibling;
            }
            if (!(this.explicitInit || this.instanceType.isInterface())) {
                Compilation.getConstructor(this.instanceType, this);
            }
            if (isAbstract()) {
                this.instanceType.setModifiers(this.instanceType.getModifiers() | LambdaExp.SEQUENCE_RESULT);
            }
            if (this.nameDecl != null) {
                this.instanceType.setModifiers((this.instanceType.getModifiers() & -2) | this.nameDecl.getAccessFlags((short) 1));
            }
        }
    }

    static void getImplMethods(ClassType interfaceType, String mname, Type[] paramTypes, Vector vec) {
        ClassType implType;
        if (interfaceType instanceof PairClassType) {
            implType = ((PairClassType) interfaceType).instanceType;
        } else if (interfaceType.isInterface()) {
            try {
                Class reflectClass = interfaceType.getReflectClass();
                if (reflectClass != null) {
                    implType = (ClassType) Type.make(Class.forName(interfaceType.getName() + "$class", false, reflectClass.getClassLoader()));
                } else {
                    return;
                }
            } catch (Throwable th) {
                return;
            }
        } else {
            return;
        }
        Type[] itypes = new Type[(paramTypes.length + 1)];
        itypes[0] = interfaceType;
        System.arraycopy(paramTypes, 0, itypes, 1, paramTypes.length);
        Method implMethod = implType.getDeclaredMethod(mname, itypes);
        if (implMethod != null) {
            int count = vec.size();
            if (count != 0) {
                if (vec.elementAt(count - 1).equals(implMethod)) {
                    return;
                }
            }
            vec.addElement(implMethod);
            return;
        }
        ClassType[] superInterfaces = interfaceType.getInterfaces();
        for (ClassType implMethods : superInterfaces) {
            getImplMethods(implMethods, mname, paramTypes, vec);
        }
    }

    private static void usedSuperClasses(ClassType clas, Compilation comp) {
        comp.usedClass(clas.getSuperclass());
        ClassType[] interfaces = clas.getInterfaces();
        if (interfaces != null) {
            int i = interfaces.length;
            while (true) {
                i--;
                if (i >= 0) {
                    comp.usedClass(interfaces[i]);
                } else {
                    return;
                }
            }
        }
    }

    public ClassType compileMembers(Compilation comp) {
        ClassType saveClass = comp.curClass;
        Method saveMethod = comp.method;
        try {
            CodeAttr code;
            Method[] methods;
            int nmethods;
            int i;
            Method meth;
            String mname;
            Type[] ptypes;
            Type rtype;
            Method mimpl;
            char ch;
            int length;
            Type ftype;
            String fname;
            Field fld;
            ClassType new_class = getCompiledClassType(comp);
            comp.curClass = new_class;
            LambdaExp outer = outerLambda();
            Member enclosing = null;
            if (outer instanceof ClassExp) {
                enclosing = outer.type;
            } else if (outer != null && !(outer instanceof ModuleExp)) {
                Object enclosing2 = saveMethod;
            } else if (outer instanceof ModuleExp) {
                if (this.type.getName().indexOf(36) > 0) {
                    enclosing = outer.type;
                }
            }
            if (enclosing != null) {
                new_class.setEnclosingMember(enclosing);
                if (enclosing instanceof ClassType) {
                    ((ClassType) enclosing).addMemberClass(new_class);
                }
            }
            ClassType classType = this.instanceType;
            if (r0 != new_class) {
                this.instanceType.setEnclosingMember(this.type);
                this.type.addMemberClass(this.instanceType);
            }
            usedSuperClasses(this.type, comp);
            if (this.type != this.instanceType) {
                usedSuperClasses(this.instanceType, comp);
            }
            String filename = getFileName();
            if (filename != null) {
                new_class.setSourceFile(filename);
            }
            LambdaExp saveLambda = comp.curLambda;
            comp.curLambda = this;
            allocFrame(comp);
            for (Expression child = this.firstChild; child != null; child = child.nextSibling) {
                if (!child.isAbstract()) {
                    Method save_method = comp.method;
                    LambdaExp save_lambda = comp.curLambda;
                    String saveFilename = comp.getFileName();
                    int saveLine = comp.getLineNumber();
                    int saveColumn = comp.getColumnNumber();
                    comp.setLine(child);
                    comp.method = child.getMainMethod();
                    Declaration childDecl = child.nameDecl;
                    if (childDecl == null || !childDecl.getFlag(2048)) {
                        child.declareThis(comp.curClass);
                    }
                    comp.curClass = this.instanceType;
                    comp.curLambda = child;
                    comp.method.initCode();
                    child.allocChildClasses(comp);
                    child.allocParameters(comp);
                    if ("*init*".equals(child.getName())) {
                        code = comp.getCode();
                        if (this.staticLinkField != null) {
                            code.emitPushThis();
                            code.emitLoad(code.getCurrentScope().getVariable(1));
                            code.emitPutField(this.staticLinkField);
                        }
                        Expression bodyFirst = child.body;
                        while (bodyFirst instanceof BeginExp) {
                            BeginExp bbody = (BeginExp) bodyFirst;
                            if (bbody.length == 0) {
                                bodyFirst = null;
                            } else {
                                bodyFirst = bbody.exps[0];
                            }
                        }
                        ClassType calledInit = null;
                        if (bodyFirst instanceof ApplyExp) {
                            Expression exp = ((ApplyExp) bodyFirst).func;
                            if (exp instanceof QuoteExp) {
                                Object value = ((QuoteExp) exp).getValue();
                                if (value instanceof PrimProcedure) {
                                    PrimProcedure pproc = (PrimProcedure) value;
                                    if (pproc.isSpecial()) {
                                        if ("<init>".equals(pproc.method.getName())) {
                                            calledInit = pproc.method.getDeclaringClass();
                                        }
                                    }
                                }
                            }
                        }
                        ClassType superClass = this.instanceType.getSuperclass();
                        if (calledInit != null) {
                            bodyFirst.compileWithPosition(comp, Target.Ignore);
                            classType = this.instanceType;
                            if (!(calledInit == r0 || calledInit == superClass)) {
                                comp.error('e', "call to <init> for not this or super class");
                            }
                        } else if (superClass != null) {
                            invokeDefaultSuperConstructor(superClass, comp, this);
                        }
                        child.enterFunction(comp);
                        classType = this.instanceType;
                        if (calledInit != r0) {
                            comp.callInitMethods(getCompiledClassType(comp), new Vector(10));
                        }
                        if (calledInit != null) {
                            Expression.compileButFirst(child.body, comp);
                        } else {
                            child.compileBody(comp);
                        }
                    } else {
                        child.enterFunction(comp);
                        child.compileBody(comp);
                    }
                    child.compileEnd(comp);
                    child.generateApplyMethods(comp);
                    comp.method = save_method;
                    comp.curClass = new_class;
                    comp.curLambda = save_lambda;
                    comp.setLine(saveFilename, saveLine, saveColumn);
                }
            }
            if (!this.explicitInit) {
                if (!this.instanceType.isInterface()) {
                    comp.generateConstructor(this.instanceType, this);
                    if (isAbstract()) {
                        methods = this.type.getAbstractMethods();
                        nmethods = methods.length;
                    } else {
                        methods = null;
                        nmethods = 0;
                    }
                    for (i = 0; i < nmethods; i++) {
                        meth = methods[i];
                        mname = meth.getName();
                        ptypes = meth.getParameterTypes();
                        rtype = meth.getReturnType();
                        mimpl = this.instanceType.getMethod(mname, ptypes);
                        if (mimpl != null || mimpl.isAbstract()) {
                            if (mname.length() > 3 && mname.charAt(2) == 't' && mname.charAt(1) == 'e') {
                                ch = mname.charAt(0);
                                if (ch == 'g' || ch == 's') {
                                    if (ch == 's' && rtype.isVoid()) {
                                        length = ptypes.length;
                                        if (r0 == 1) {
                                            ftype = ptypes[0];
                                            fname = Character.toLowerCase(mname.charAt(3)) + mname.substring(4);
                                            fld = this.instanceType.getField(fname);
                                            if (fld == null) {
                                                fld = this.instanceType.addField(fname, ftype, 1);
                                            }
                                            code = this.instanceType.addMethod(mname, 1, ptypes, rtype).startCode();
                                            code.emitPushThis();
                                            if (ch != 'g') {
                                                code.emitGetField(fld);
                                            } else {
                                                code.emitLoad(code.getArg(1));
                                                code.emitPutField(fld);
                                            }
                                            code.emitReturn();
                                        }
                                    }
                                    if (ch == 'g' && ptypes.length == 0) {
                                        ftype = rtype;
                                        fname = Character.toLowerCase(mname.charAt(3)) + mname.substring(4);
                                        fld = this.instanceType.getField(fname);
                                        if (fld == null) {
                                            fld = this.instanceType.addField(fname, ftype, 1);
                                        }
                                        code = this.instanceType.addMethod(mname, 1, ptypes, rtype).startCode();
                                        code.emitPushThis();
                                        if (ch != 'g') {
                                            code.emitLoad(code.getArg(1));
                                            code.emitPutField(fld);
                                        } else {
                                            code.emitGetField(fld);
                                        }
                                        code.emitReturn();
                                    }
                                }
                            }
                            Vector vec = new Vector();
                            getImplMethods(this.type, mname, ptypes, vec);
                            if (vec.size() != 1) {
                                comp.error('e', (vec.size() == 0 ? "missing implementation for " : "ambiguous implementation for ") + meth);
                            } else {
                                code = this.instanceType.addMethod(mname, 1, ptypes, rtype).startCode();
                                for (Variable var = code.getCurrentScope().firstVar(); var != null; var = var.nextVar()) {
                                    code.emitLoad(var);
                                }
                                code.emitInvokeStatic((Method) vec.elementAt(0));
                                code.emitReturn();
                            }
                        }
                    }
                    generateApplyMethods(comp);
                    comp.curLambda = saveLambda;
                    return new_class;
                }
            }
            if (this.initChain != null) {
                this.initChain.reportError("unimplemented: explicit constructor cannot initialize ", comp);
            }
            if (isAbstract()) {
                methods = this.type.getAbstractMethods();
                nmethods = methods.length;
            } else {
                methods = null;
                nmethods = 0;
            }
            for (i = 0; i < nmethods; i++) {
                meth = methods[i];
                mname = meth.getName();
                ptypes = meth.getParameterTypes();
                rtype = meth.getReturnType();
                mimpl = this.instanceType.getMethod(mname, ptypes);
                if (mimpl != null) {
                }
                ch = mname.charAt(0);
                length = ptypes.length;
                if (r0 == 1) {
                    ftype = ptypes[0];
                    fname = Character.toLowerCase(mname.charAt(3)) + mname.substring(4);
                    fld = this.instanceType.getField(fname);
                    if (fld == null) {
                        fld = this.instanceType.addField(fname, ftype, 1);
                    }
                    code = this.instanceType.addMethod(mname, 1, ptypes, rtype).startCode();
                    code.emitPushThis();
                    if (ch != 'g') {
                        code.emitGetField(fld);
                    } else {
                        code.emitLoad(code.getArg(1));
                        code.emitPutField(fld);
                    }
                    code.emitReturn();
                }
                ftype = rtype;
                fname = Character.toLowerCase(mname.charAt(3)) + mname.substring(4);
                fld = this.instanceType.getField(fname);
                if (fld == null) {
                    fld = this.instanceType.addField(fname, ftype, 1);
                }
                code = this.instanceType.addMethod(mname, 1, ptypes, rtype).startCode();
                code.emitPushThis();
                if (ch != 'g') {
                    code.emitLoad(code.getArg(1));
                    code.emitPutField(fld);
                } else {
                    code.emitGetField(fld);
                }
                code.emitReturn();
            }
            generateApplyMethods(comp);
            comp.curLambda = saveLambda;
            return new_class;
        } finally {
            comp.curClass = saveClass;
            comp.method = saveMethod;
        }
    }

    protected <R, D> R visit(ExpVisitor<R, D> visitor, D d) {
        Compilation comp = visitor.getCompilation();
        if (comp == null) {
            return visitor.visitClassExp(this, d);
        }
        ClassType saveClass = comp.curClass;
        try {
            comp.curClass = this.type;
            R visitClassExp = visitor.visitClassExp(this, d);
            return visitClassExp;
        } finally {
            comp.curClass = saveClass;
        }
    }

    protected <R, D> void visitChildren(ExpVisitor<R, D> visitor, D d) {
        LambdaExp save = visitor.currentLambda;
        visitor.currentLambda = this;
        this.supers = visitor.visitExps(this.supers, this.supers.length, d);
        try {
            for (LambdaExp child = this.firstChild; child != null && visitor.exitValue == null; child = child.nextSibling) {
                if (this.instanceType != null) {
                    Declaration firstParam = child.firstDecl();
                    if (firstParam != null && firstParam.isThisParameter()) {
                        firstParam.setType(this.type);
                    }
                }
                visitor.visitLambdaExp(child, d);
            }
            visitor.currentLambda = save;
        } catch (Throwable th) {
            visitor.currentLambda = save;
        }
    }

    static void loadSuperStaticLink(Expression superExp, ClassType superClass, Compilation comp) {
        CodeAttr code = comp.getCode();
        superExp.compile(comp, Target.pushValue(Compilation.typeClassType));
        code.emitInvokeStatic(ClassType.make("gnu.expr.PairClassType").getDeclaredMethod("extractStaticLink", 1));
        code.emitCheckcast(superClass.getOuterLinkType());
    }

    static void invokeDefaultSuperConstructor(ClassType superClass, Compilation comp, LambdaExp lexp) {
        CodeAttr code = comp.getCode();
        Method superConstructor = superClass.getDeclaredMethod("<init>", 0);
        if (superConstructor == null) {
            comp.error('e', "super class does not have a default constructor");
            return;
        }
        code.emitPushThis();
        if (superClass.hasOuterLink() && (lexp instanceof ClassExp)) {
            ClassExp clExp = (ClassExp) lexp;
            loadSuperStaticLink(clExp.supers[clExp.superClassIndex], superClass, comp);
        }
        code.emitInvokeSpecial(superConstructor);
    }

    public void print(OutPort out) {
        out.startLogicalBlock("(" + getExpClassName() + "/", ")", 2);
        Object name = getSymbol();
        if (name != null) {
            out.print(name);
            out.print('/');
        }
        out.print(this.id);
        out.print("/fl:");
        out.print(Integer.toHexString(this.flags));
        if (this.supers.length > 0) {
            out.writeSpaceFill();
            out.startLogicalBlock("supers:", ElementType.MATCH_ANY_LOCALNAME, 2);
            for (Expression print : this.supers) {
                print.print(out);
                out.writeSpaceFill();
            }
            out.endLogicalBlock(ElementType.MATCH_ANY_LOCALNAME);
        }
        out.print('(');
        int i = 0;
        if (this.keywords != null) {
            int length = this.keywords.length;
        }
        for (Declaration decl = firstDecl(); decl != null; decl = decl.nextDecl()) {
            if (i > 0) {
                out.print(' ');
            }
            decl.printInfo(out);
            i++;
        }
        out.print(") ");
        for (LambdaExp child = this.firstChild; child != null; child = child.nextSibling) {
            out.writeBreakLinear();
            child.print(out);
        }
        if (this.body != null) {
            out.writeBreakLinear();
            this.body.print(out);
        }
        out.endLogicalBlock(")");
    }

    public Field compileSetField(Compilation comp) {
        return new ClassInitializer(this, comp).field;
    }

    public static String slotToMethodName(String prefix, String sname) {
        if (!Compilation.isValidJavaName(sname)) {
            sname = Compilation.mangleName(sname, false);
        }
        int slen = sname.length();
        StringBuffer sbuf = new StringBuffer(slen + 3);
        sbuf.append(prefix);
        if (slen > 0) {
            sbuf.append(Character.toTitleCase(sname.charAt(0)));
            sbuf.append(sname.substring(1));
        }
        return sbuf.toString();
    }

    public Declaration addMethod(LambdaExp lexp, Object mname) {
        Declaration mdecl = addDeclaration(mname, Compilation.typeProcedure);
        lexp.outer = this;
        lexp.setClassMethod(true);
        mdecl.noteValue(lexp);
        mdecl.setFlag(1048576);
        mdecl.setProcedureDecl(true);
        lexp.setSymbol(mname);
        return mdecl;
    }
}
