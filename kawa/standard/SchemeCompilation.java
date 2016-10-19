package kawa.standard;

import gnu.expr.Special;
import kawa.lang.Lambda;
import kawa.repl;

public class SchemeCompilation {
    public static final Lambda lambda;
    public static final repl repl;

    static {
        lambda = new Lambda();
        repl = new repl(Scheme.instance);
        lambda.setKeywords(Special.optional, Special.rest, Special.key);
    }
}
