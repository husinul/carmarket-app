package gnu.mapping;

public interface EnvironmentKey {
    public static final Object FUNCTION;

    Object getKeyProperty();

    Symbol getKeySymbol();

    boolean matches(EnvironmentKey environmentKey);

    boolean matches(Symbol symbol, Object obj);

    static {
        FUNCTION = Symbol.FUNCTION;
    }
}
