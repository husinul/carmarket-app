package gnu.expr;

import gnu.bytecode.Type;

public abstract class Target {
    public static final Target Ignore;
    public static final Target pushObject;

    public abstract void compileFromStack(Compilation compilation, Type type);

    public abstract Type getType();

    static {
        Ignore = new IgnoreTarget();
        pushObject = new StackTarget(Type.pointer_type);
    }

    public static Target pushValue(Type type) {
        return type.isVoid() ? Ignore : StackTarget.getInstance(type);
    }
}
