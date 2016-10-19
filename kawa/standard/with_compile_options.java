package kawa.standard;

import gnu.expr.BeginExp;
import gnu.expr.Expression;
import gnu.expr.ScopeExp;
import gnu.lists.LList;
import gnu.lists.Pair;
import java.util.Stack;
import kawa.lang.Syntax;
import kawa.lang.Translator;

public class with_compile_options extends Syntax {
    public static final with_compile_options with_compile_options;

    static {
        with_compile_options = new with_compile_options();
        with_compile_options.setName("with-compile-options");
    }

    public void scanForm(Pair form, ScopeExp defs, Translator tr) {
        Stack stack = new Stack();
        LList rest = getOptions(form.getCdr(), stack, this, tr);
        if (rest != LList.Empty) {
            if (rest == form.getCdr()) {
                tr.scanBody(rest, defs, false);
                return;
            }
            Pair rest2 = new Pair(stack, tr.scanBody(rest, defs, true));
            tr.currentOptions.popOptionValues(stack);
            tr.formStack.add(Translator.makePair(form, form.getCar(), rest2));
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.Object getOptions(java.lang.Object r13, java.util.Stack r14, kawa.lang.Syntax r15, kawa.lang.Translator r16) {
        /*
        r7 = 0;
        r0 = r16;
        r3 = r0.currentOptions;
        r8 = 0;
    L_0x0006:
        r10 = r13 instanceof kawa.lang.SyntaxForm;
        if (r10 == 0) goto L_0x0012;
    L_0x000a:
        r8 = r13;
        r8 = (kawa.lang.SyntaxForm) r8;
        r13 = r8.getDatum();
        goto L_0x0006;
    L_0x0012:
        r10 = r13 instanceof gnu.lists.Pair;
        if (r10 != 0) goto L_0x003b;
    L_0x0016:
        if (r7 != 0) goto L_0x0036;
    L_0x0018:
        r10 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "no option keyword in ";
        r11 = r11.append(r12);
        r12 = r15.getName();
        r11 = r11.append(r12);
        r11 = r11.toString();
        r0 = r16;
        r0.error(r10, r11);
    L_0x0036:
        r10 = kawa.lang.Translator.wrapSyntax(r13, r8);
    L_0x003a:
        return r10;
    L_0x003b:
        r4 = r13;
        r4 = (gnu.lists.Pair) r4;
        r10 = r4.getCar();
        r5 = kawa.lang.Translator.stripSyntax(r10);
        r10 = r5 instanceof gnu.expr.Keyword;
        if (r10 == 0) goto L_0x0016;
    L_0x004a:
        r5 = (gnu.expr.Keyword) r5;
        r1 = r5.getName();
        r7 = 1;
        r0 = r16;
        r6 = r0.pushPositionOf(r4);
        r13 = r4.getCdr();	 Catch:{ all -> 0x0114 }
    L_0x005b:
        r10 = r13 instanceof kawa.lang.SyntaxForm;	 Catch:{ all -> 0x0114 }
        if (r10 == 0) goto L_0x0068;
    L_0x005f:
        r0 = r13;
        r0 = (kawa.lang.SyntaxForm) r0;	 Catch:{ all -> 0x0114 }
        r8 = r0;
        r13 = r8.getDatum();	 Catch:{ all -> 0x0114 }
        goto L_0x005b;
    L_0x0068:
        r10 = r13 instanceof gnu.lists.Pair;	 Catch:{ all -> 0x0114 }
        if (r10 != 0) goto L_0x0094;
    L_0x006c:
        r10 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r11 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0114 }
        r11.<init>();	 Catch:{ all -> 0x0114 }
        r12 = "keyword ";
        r11 = r11.append(r12);	 Catch:{ all -> 0x0114 }
        r11 = r11.append(r1);	 Catch:{ all -> 0x0114 }
        r12 = " not followed by value";
        r11 = r11.append(r12);	 Catch:{ all -> 0x0114 }
        r11 = r11.toString();	 Catch:{ all -> 0x0114 }
        r0 = r16;
        r0.error(r10, r11);	 Catch:{ all -> 0x0114 }
        r10 = gnu.lists.LList.Empty;	 Catch:{ all -> 0x0114 }
        r0 = r16;
        r0.popPositionOf(r6);
        goto L_0x003a;
    L_0x0094:
        r0 = r13;
        r0 = (gnu.lists.Pair) r0;	 Catch:{ all -> 0x0114 }
        r4 = r0;
        r10 = r4.getCar();	 Catch:{ all -> 0x0114 }
        r9 = kawa.lang.Translator.stripSyntax(r10);	 Catch:{ all -> 0x0114 }
        r13 = r4.getCdr();	 Catch:{ all -> 0x0114 }
        r2 = r3.getLocal(r1);	 Catch:{ all -> 0x0114 }
        r10 = r3.getInfo(r1);	 Catch:{ all -> 0x0114 }
        if (r10 != 0) goto L_0x00cf;
    L_0x00ae:
        r10 = 119; // 0x77 float:1.67E-43 double:5.9E-322;
        r11 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0114 }
        r11.<init>();	 Catch:{ all -> 0x0114 }
        r12 = "unknown compile option: ";
        r11 = r11.append(r12);	 Catch:{ all -> 0x0114 }
        r11 = r11.append(r1);	 Catch:{ all -> 0x0114 }
        r11 = r11.toString();	 Catch:{ all -> 0x0114 }
        r0 = r16;
        r0.error(r10, r11);	 Catch:{ all -> 0x0114 }
        r0 = r16;
        r0.popPositionOf(r6);
        goto L_0x0006;
    L_0x00cf:
        r10 = r9 instanceof gnu.lists.FString;	 Catch:{ all -> 0x0114 }
        if (r10 == 0) goto L_0x00f0;
    L_0x00d3:
        r9 = r9.toString();	 Catch:{ all -> 0x0114 }
    L_0x00d7:
        r10 = r16.getMessages();	 Catch:{ all -> 0x0114 }
        r3.set(r1, r9, r10);	 Catch:{ all -> 0x0114 }
        if (r14 == 0) goto L_0x00e9;
    L_0x00e0:
        r14.push(r1);	 Catch:{ all -> 0x0114 }
        r14.push(r2);	 Catch:{ all -> 0x0114 }
        r14.push(r9);	 Catch:{ all -> 0x0114 }
    L_0x00e9:
        r0 = r16;
        r0.popPositionOf(r6);
        goto L_0x0006;
    L_0x00f0:
        r10 = r9 instanceof java.lang.Boolean;	 Catch:{ all -> 0x0114 }
        if (r10 != 0) goto L_0x00d7;
    L_0x00f4:
        r10 = r9 instanceof java.lang.Number;	 Catch:{ all -> 0x0114 }
        if (r10 != 0) goto L_0x00d7;
    L_0x00f8:
        r9 = 0;
        r10 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r11 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0114 }
        r11.<init>();	 Catch:{ all -> 0x0114 }
        r12 = "invalid literal value for key ";
        r11 = r11.append(r12);	 Catch:{ all -> 0x0114 }
        r11 = r11.append(r1);	 Catch:{ all -> 0x0114 }
        r11 = r11.toString();	 Catch:{ all -> 0x0114 }
        r0 = r16;
        r0.error(r10, r11);	 Catch:{ all -> 0x0114 }
        goto L_0x00d7;
    L_0x0114:
        r10 = move-exception;
        r0 = r16;
        r0.popPositionOf(r6);
        throw r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.standard.with_compile_options.getOptions(java.lang.Object, java.util.Stack, kawa.lang.Syntax, kawa.lang.Translator):java.lang.Object");
    }

    public Expression rewriteForm(Pair form, Translator tr) {
        Stack stack;
        Object rest;
        Expression result;
        BeginExp bresult;
        Pair obj = form.getCdr();
        if (obj instanceof Pair) {
            Pair p = obj;
            if (p.getCar() instanceof Stack) {
                stack = (Stack) p.getCar();
                rest = p.getCdr();
                tr.currentOptions.pushOptionValues(stack);
                result = tr.rewrite_body(rest);
                if (result instanceof BeginExp) {
                    bresult = new BeginExp(new Expression[]{result});
                } else {
                    bresult = (BeginExp) result;
                }
                bresult.setCompileOptions(stack);
                return bresult;
            }
        }
        stack = new Stack();
        rest = getOptions(obj, stack, this, tr);
        try {
            result = tr.rewrite_body(rest);
            if (result instanceof BeginExp) {
                bresult = new BeginExp(new Expression[]{result});
            } else {
                bresult = (BeginExp) result;
            }
            bresult.setCompileOptions(stack);
            return bresult;
        } finally {
            tr.currentOptions.popOptionValues(stack);
        }
    }
}
