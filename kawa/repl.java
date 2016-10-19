package kawa;

import gnu.bytecode.ClassType;
import gnu.expr.ApplicationMainSupport;
import gnu.expr.Compilation;
import gnu.expr.Language;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleExp;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleManager;
import gnu.lists.FString;
import gnu.mapping.Environment;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.Procedure0or1;
import gnu.mapping.Values;
import gnu.text.SourceMessages;
import gnu.text.SyntaxException;
import gnu.text.WriterManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;

public class repl extends Procedure0or1 {
    public static String compilationTopname;
    static int defaultParseOptions;
    public static String homeDirectory;
    public static boolean noConsole;
    static Language previousLanguage;
    static boolean shutdownRegistered;
    Language language;

    static {
        compilationTopname = null;
        defaultParseOptions = 72;
        shutdownRegistered = WriterManager.instance.registerShutdownHook();
    }

    public repl(Language language) {
        this.language = language;
    }

    public Object apply0() {
        Shell.run(this.language, Environment.getCurrent());
        return Values.empty;
    }

    public Object apply1(Object env) {
        Shell.run(this.language, (Environment) env);
        return Values.empty;
    }

    static void bad_option(String str) {
        System.err.println("kawa: bad option '" + str + "'");
        printOptions(System.err);
        System.exit(-1);
    }

    public static void printOption(PrintStream out, String option, String doc) {
        out.print(" ");
        out.print(option);
        int len = option.length() + 1;
        for (int i = 0; i < 30 - len; i++) {
            out.print(" ");
        }
        out.print(" ");
        out.println(doc);
    }

    public static void printOptions(PrintStream out) {
        int i;
        out.println("Usage: [java kawa.repl | kawa] [options ...]");
        out.println();
        out.println(" Generic options:");
        printOption(out, "--help", "Show help about options");
        printOption(out, "--author", "Show author information");
        printOption(out, "--version", "Show version information");
        out.println();
        out.println(" Options");
        printOption(out, "-e <expr>", "Evaluate expression <expr>");
        printOption(out, "-c <expr>", "Same as -e, but make sure ~/.kawarc.scm is run first");
        printOption(out, "-f <filename>", "File to interpret");
        printOption(out, "-s| --", "Start reading commands interactively from console");
        printOption(out, "-w", "Launch the interpreter in a GUI window");
        printOption(out, "--server <port>", "Start a server accepting telnet connections on <port>");
        printOption(out, "--debug-dump-zip", "Compiled interactive expressions to a zip archive");
        printOption(out, "--debug-print-expr", "Print generated internal expressions");
        printOption(out, "--debug-print-final-expr", "Print expression after any optimizations");
        printOption(out, "--debug-error-prints-stack-trace", "Print stack trace with errors");
        printOption(out, "--debug-warning-prints-stack-trace", "Print stack trace with warnings");
        printOption(out, "--[no-]full-tailcalls", "(Don't) use full tail-calls");
        printOption(out, "-C <filename> ...", "Compile named files to Java class files");
        printOption(out, "--output-format <format>", "Use <format> when printing top-level output");
        printOption(out, "--<language>", "Select source language, one of:");
        String[][] languages = Language.getLanguages();
        for (i = 0; i < languages.length; i++) {
            out.print("   ");
            String[] lang = languages[i];
            int nwords = lang.length - 1;
            for (int j = 0; j < nwords; j++) {
                out.print(lang[j] + " ");
            }
            if (i == 0) {
                out.print("[default]");
            }
            out.println();
        }
        out.println(" Compilation options, must be specified before -C");
        printOption(out, "-d <dirname>", "Directory to place .class files in");
        printOption(out, "-P <prefix>", "Prefix to prepand to class names");
        printOption(out, "-T <topname>", "name to give to top-level class");
        printOption(out, "--main", "Generate an application, with a main method");
        printOption(out, "--applet", "Generate an applet");
        printOption(out, "--servlet", "Generate a servlet");
        printOption(out, "--module-static", "Top-level definitions are by default static");
        ArrayList<String> keys = Compilation.options.keys();
        for (i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            printOption(out, "--" + name, Compilation.options.getDoc(name));
        }
        out.println();
        out.println("For more information go to:  http://www.gnu.org/software/kawa/");
    }

    static void checkInitFile() {
        if (homeDirectory == null) {
            Object scmHomeDirectory;
            File initFile = null;
            homeDirectory = System.getProperty("user.home");
            if (homeDirectory != null) {
                scmHomeDirectory = new FString(homeDirectory);
                initFile = new File(homeDirectory, "/".equals(System.getProperty("file.separator")) ? ".kawarc.scm" : "kawarc.scm");
            } else {
                scmHomeDirectory = Boolean.FALSE;
            }
            Environment.getCurrent().put("home-directory", scmHomeDirectory);
            if (initFile != null && initFile.exists() && !Shell.runFileOrClass(initFile.getPath(), true, 0)) {
                System.exit(-1);
            }
        }
    }

    public static void setArgs(String[] args, int arg_start) {
        ApplicationMainSupport.setArgs(args, arg_start);
    }

    public static void getLanguageFromFilenameExtension(String name) {
        if (previousLanguage == null) {
            previousLanguage = Language.getInstanceFromFilenameExtension(name);
            if (previousLanguage != null) {
                Language.setDefaults(previousLanguage);
                return;
            }
        }
        getLanguage();
    }

    public static void getLanguage() {
        if (previousLanguage == null) {
            previousLanguage = Language.getInstance(null);
            Language.setDefaults(previousLanguage);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int processArgs(java.lang.String[] r45, int r46, int r47) {
        /*
        r39 = 0;
    L_0x0002:
        r0 = r46;
        r1 = r47;
        if (r0 >= r1) goto L_0x0772;
    L_0x0008:
        r14 = r45[r46];
        r3 = "-c";
        r3 = r14.equals(r3);
        if (r3 != 0) goto L_0x001a;
    L_0x0012:
        r3 = "-e";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x006b;
    L_0x001a:
        r46 = r46 + 1;
        r0 = r46;
        r1 = r47;
        if (r0 != r1) goto L_0x0025;
    L_0x0022:
        bad_option(r14);
    L_0x0025:
        getLanguage();
        r3 = r46 + 1;
        r0 = r45;
        setArgs(r0, r3);
        r3 = "-c";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x003a;
    L_0x0037:
        checkInitFile();
    L_0x003a:
        r2 = gnu.expr.Language.getDefaultLanguage();
        r7 = new gnu.text.SourceMessages;
        r7.<init>();
        r3 = gnu.mapping.Environment.getCurrent();
        r4 = new gnu.mapping.CharArrayInPort;
        r5 = r45[r46];
        r4.<init>(r5);
        r5 = gnu.mapping.OutPort.outDefault();
        r6 = 0;
        r21 = kawa.Shell.run(r2, r3, r4, r5, r6, r7);
        if (r21 == 0) goto L_0x0066;
    L_0x0059:
        r3 = gnu.mapping.OutPort.errDefault();
        r0 = r21;
        kawa.Shell.printError(r0, r7, r3);
        r3 = -1;
        java.lang.System.exit(r3);
    L_0x0066:
        r39 = 1;
    L_0x0068:
        r46 = r46 + 1;
        goto L_0x0002;
    L_0x006b:
        r3 = "-f";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x009e;
    L_0x0073:
        r46 = r46 + 1;
        r0 = r46;
        r1 = r47;
        if (r0 != r1) goto L_0x007e;
    L_0x007b:
        bad_option(r14);
    L_0x007e:
        r22 = r45[r46];
        getLanguageFromFilenameExtension(r22);
        r3 = r46 + 1;
        r0 = r45;
        setArgs(r0, r3);
        checkInitFile();
        r3 = 1;
        r4 = 0;
        r0 = r22;
        r3 = kawa.Shell.runFileOrClass(r0, r3, r4);
        if (r3 != 0) goto L_0x009b;
    L_0x0097:
        r3 = -1;
        java.lang.System.exit(r3);
    L_0x009b:
        r39 = 1;
        goto L_0x0068;
    L_0x009e:
        r3 = "--script";
        r3 = r14.startsWith(r3);
        if (r3 == 0) goto L_0x00e7;
    L_0x00a6:
        r3 = 8;
        r18 = r14.substring(r3);
        r46 = r46 + 1;
        r37 = 0;
        r3 = r18.length();
        if (r3 <= 0) goto L_0x00ba;
    L_0x00b6:
        r37 = java.lang.Integer.parseInt(r18);	 Catch:{ Throwable -> 0x00e3 }
    L_0x00ba:
        r0 = r46;
        r1 = r47;
        if (r0 != r1) goto L_0x00c3;
    L_0x00c0:
        bad_option(r14);
    L_0x00c3:
        r22 = r45[r46];
        getLanguageFromFilenameExtension(r22);
        r3 = r46 + 1;
        r0 = r45;
        setArgs(r0, r3);
        checkInitFile();
        r3 = 1;
        r0 = r22;
        r1 = r37;
        r3 = kawa.Shell.runFileOrClass(r0, r3, r1);
        if (r3 != 0) goto L_0x00e1;
    L_0x00dd:
        r3 = -1;
        java.lang.System.exit(r3);
    L_0x00e1:
        r3 = -1;
    L_0x00e2:
        return r3;
    L_0x00e3:
        r21 = move-exception;
        r46 = r47;
        goto L_0x00ba;
    L_0x00e7:
        r3 = "\\";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x0263;
    L_0x00ef:
        r46 = r46 + 1;
        r0 = r46;
        r1 = r47;
        if (r0 != r1) goto L_0x00fa;
    L_0x00f7:
        bad_option(r14);
    L_0x00fa:
        r22 = r45[r46];
        r7 = new gnu.text.SourceMessages;
        r7.<init>();
        r23 = new java.io.BufferedInputStream;	 Catch:{ Throwable -> 0x021b }
        r3 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x021b }
        r0 = r22;
        r3.<init>(r0);	 Catch:{ Throwable -> 0x021b }
        r0 = r23;
        r0.<init>(r3);	 Catch:{ Throwable -> 0x021b }
        r15 = r23.read();	 Catch:{ Throwable -> 0x021b }
        r3 = 35;
        if (r15 != r3) goto L_0x01d0;
    L_0x0117:
        r34 = new java.lang.StringBuffer;	 Catch:{ Throwable -> 0x021b }
        r3 = 100;
        r0 = r34;
        r0.<init>(r3);	 Catch:{ Throwable -> 0x021b }
        r44 = new java.util.Vector;	 Catch:{ Throwable -> 0x021b }
        r3 = 10;
        r0 = r44;
        r0.<init>(r3);	 Catch:{ Throwable -> 0x021b }
        r43 = 0;
    L_0x012b:
        r3 = 10;
        if (r15 == r3) goto L_0x013a;
    L_0x012f:
        r3 = 13;
        if (r15 == r3) goto L_0x013a;
    L_0x0133:
        if (r15 < 0) goto L_0x013a;
    L_0x0135:
        r15 = r23.read();	 Catch:{ Throwable -> 0x021b }
        goto L_0x012b;
    L_0x013a:
        r15 = r23.read();	 Catch:{ Throwable -> 0x021b }
        if (r15 >= 0) goto L_0x0164;
    L_0x0140:
        r3 = java.lang.System.err;	 Catch:{ Throwable -> 0x021b }
        r4 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x021b }
        r4.<init>();	 Catch:{ Throwable -> 0x021b }
        r5 = "unexpected end-of-file processing argument line for: '";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x021b }
        r0 = r22;
        r4 = r4.append(r0);	 Catch:{ Throwable -> 0x021b }
        r5 = 39;
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x021b }
        r4 = r4.toString();	 Catch:{ Throwable -> 0x021b }
        r3.println(r4);	 Catch:{ Throwable -> 0x021b }
        r3 = -1;
        java.lang.System.exit(r3);	 Catch:{ Throwable -> 0x021b }
    L_0x0164:
        if (r43 != 0) goto L_0x024b;
    L_0x0166:
        r3 = 92;
        if (r15 == r3) goto L_0x0172;
    L_0x016a:
        r3 = 39;
        if (r15 == r3) goto L_0x0172;
    L_0x016e:
        r3 = 34;
        if (r15 != r3) goto L_0x0175;
    L_0x0172:
        r43 = r15;
        goto L_0x013a;
    L_0x0175:
        r3 = 10;
        if (r15 == r3) goto L_0x017d;
    L_0x0179:
        r3 = 13;
        if (r15 != r3) goto L_0x022c;
    L_0x017d:
        r3 = r34.length();	 Catch:{ Throwable -> 0x021b }
        if (r3 <= 0) goto L_0x018c;
    L_0x0183:
        r3 = r34.toString();	 Catch:{ Throwable -> 0x021b }
        r0 = r44;
        r0.addElement(r3);	 Catch:{ Throwable -> 0x021b }
    L_0x018c:
        r29 = r44.size();	 Catch:{ Throwable -> 0x021b }
        if (r29 <= 0) goto L_0x01d0;
    L_0x0192:
        r0 = r29;
        r0 = new java.lang.String[r0];	 Catch:{ Throwable -> 0x021b }
        r33 = r0;
        r0 = r44;
        r1 = r33;
        r0.copyInto(r1);	 Catch:{ Throwable -> 0x021b }
        r3 = 0;
        r0 = r33;
        r1 = r29;
        r24 = processArgs(r0, r3, r1);	 Catch:{ Throwable -> 0x021b }
        if (r24 < 0) goto L_0x01d0;
    L_0x01aa:
        r0 = r24;
        r1 = r29;
        if (r0 >= r1) goto L_0x01d0;
    L_0x01b0:
        r3 = java.lang.System.err;	 Catch:{ Throwable -> 0x021b }
        r4 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x021b }
        r4.<init>();	 Catch:{ Throwable -> 0x021b }
        r5 = "";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x021b }
        r5 = r29 - r24;
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x021b }
        r5 = " unused meta args";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x021b }
        r4 = r4.toString();	 Catch:{ Throwable -> 0x021b }
        r3.println(r4);	 Catch:{ Throwable -> 0x021b }
    L_0x01d0:
        getLanguageFromFilenameExtension(r22);	 Catch:{ Throwable -> 0x021b }
        r0 = r23;
        r1 = r22;
        r10 = gnu.mapping.InPort.openFile(r0, r1);	 Catch:{ Throwable -> 0x021b }
        r3 = r46 + 1;
        r0 = r45;
        setArgs(r0, r3);	 Catch:{ Throwable -> 0x021b }
        checkInitFile();	 Catch:{ Throwable -> 0x021b }
        r20 = gnu.mapping.OutPort.errDefault();	 Catch:{ Throwable -> 0x021b }
        r8 = gnu.expr.Language.getDefaultLanguage();	 Catch:{ Throwable -> 0x021b }
        r9 = gnu.mapping.Environment.getCurrent();	 Catch:{ Throwable -> 0x021b }
        r11 = gnu.mapping.OutPort.outDefault();	 Catch:{ Throwable -> 0x021b }
        r12 = 0;
        r13 = r7;
        r21 = kawa.Shell.run(r8, r9, r10, r11, r12, r13);	 Catch:{ Throwable -> 0x021b }
        r3 = 20;
        r0 = r20;
        r7.printAll(r0, r3);	 Catch:{ Throwable -> 0x021b }
        if (r21 == 0) goto L_0x0229;
    L_0x0204:
        r0 = r21;
        r3 = r0 instanceof gnu.text.SyntaxException;	 Catch:{ Throwable -> 0x021b }
        if (r3 == 0) goto L_0x021a;
    L_0x020a:
        r0 = r21;
        r0 = (gnu.text.SyntaxException) r0;	 Catch:{ Throwable -> 0x021b }
        r35 = r0;
        r3 = r35.getMessages();	 Catch:{ Throwable -> 0x021b }
        if (r3 != r7) goto L_0x021a;
    L_0x0216:
        r3 = 1;
        java.lang.System.exit(r3);	 Catch:{ Throwable -> 0x021b }
    L_0x021a:
        throw r21;	 Catch:{ Throwable -> 0x021b }
    L_0x021b:
        r21 = move-exception;
        r3 = gnu.mapping.OutPort.errDefault();
        r0 = r21;
        kawa.Shell.printError(r0, r7, r3);
        r3 = 1;
        java.lang.System.exit(r3);
    L_0x0229:
        r3 = -1;
        goto L_0x00e2;
    L_0x022c:
        r3 = 32;
        if (r15 == r3) goto L_0x0234;
    L_0x0230:
        r3 = 9;
        if (r15 != r3) goto L_0x0253;
    L_0x0234:
        r3 = r34.length();	 Catch:{ Throwable -> 0x021b }
        if (r3 <= 0) goto L_0x013a;
    L_0x023a:
        r3 = r34.toString();	 Catch:{ Throwable -> 0x021b }
        r0 = r44;
        r0.addElement(r3);	 Catch:{ Throwable -> 0x021b }
        r3 = 0;
        r0 = r34;
        r0.setLength(r3);	 Catch:{ Throwable -> 0x021b }
        goto L_0x013a;
    L_0x024b:
        r3 = 92;
        r0 = r43;
        if (r0 != r3) goto L_0x025b;
    L_0x0251:
        r43 = 0;
    L_0x0253:
        r3 = (char) r15;	 Catch:{ Throwable -> 0x021b }
        r0 = r34;
        r0.append(r3);	 Catch:{ Throwable -> 0x021b }
        goto L_0x013a;
    L_0x025b:
        r0 = r43;
        if (r15 != r0) goto L_0x0253;
    L_0x025f:
        r43 = 0;
        goto L_0x013a;
    L_0x0263:
        r3 = "-s";
        r3 = r14.equals(r3);
        if (r3 != 0) goto L_0x0273;
    L_0x026b:
        r3 = "--";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x028c;
    L_0x0273:
        r46 = r46 + 1;
        getLanguage();
        setArgs(r45, r46);
        checkInitFile();
        r3 = gnu.expr.Language.getDefaultLanguage();
        r4 = gnu.mapping.Environment.getCurrent();
        kawa.Shell.run(r3, r4);
        r3 = -1;
        goto L_0x00e2;
    L_0x028c:
        r3 = "-w";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x02a6;
    L_0x0294:
        r46 = r46 + 1;
        getLanguage();
        setArgs(r45, r46);
        checkInitFile();
        startGuiConsole();
        r39 = 1;
        goto L_0x0068;
    L_0x02a6:
        r3 = "-d";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x02c6;
    L_0x02ae:
        r46 = r46 + 1;
        r0 = r46;
        r1 = r47;
        if (r0 != r1) goto L_0x02b9;
    L_0x02b6:
        bad_option(r14);
    L_0x02b9:
        r26 = gnu.expr.ModuleManager.getInstance();
        r3 = r45[r46];
        r0 = r26;
        r0.setCompilationDirectory(r3);
        goto L_0x0068;
    L_0x02c6:
        r3 = "--target";
        r3 = r14.equals(r3);
        if (r3 != 0) goto L_0x02d6;
    L_0x02ce:
        r3 = "target";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x0359;
    L_0x02d6:
        r46 = r46 + 1;
        r0 = r46;
        r1 = r47;
        if (r0 != r1) goto L_0x02e1;
    L_0x02de:
        bad_option(r14);
    L_0x02e1:
        r14 = r45[r46];
        r3 = "7";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x02ef;
    L_0x02eb:
        r3 = 3342336; // 0x330000 float:4.68361E-39 double:1.6513334E-317;
        gnu.expr.Compilation.defaultClassFileVersion = r3;
    L_0x02ef:
        r3 = "6";
        r3 = r14.equals(r3);
        if (r3 != 0) goto L_0x02ff;
    L_0x02f7:
        r3 = "1.6";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x0305;
    L_0x02ff:
        r3 = 3276800; // 0x320000 float:4.591775E-39 double:1.6189543E-317;
        gnu.expr.Compilation.defaultClassFileVersion = r3;
        goto L_0x0068;
    L_0x0305:
        r3 = "5";
        r3 = r14.equals(r3);
        if (r3 != 0) goto L_0x0315;
    L_0x030d:
        r3 = "1.5";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x031b;
    L_0x0315:
        r3 = 3211264; // 0x310000 float:4.49994E-39 double:1.586575E-317;
        gnu.expr.Compilation.defaultClassFileVersion = r3;
        goto L_0x0068;
    L_0x031b:
        r3 = "1.4";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x0329;
    L_0x0323:
        r3 = 3145728; // 0x300000 float:4.408104E-39 double:1.554196E-317;
        gnu.expr.Compilation.defaultClassFileVersion = r3;
        goto L_0x0068;
    L_0x0329:
        r3 = "1.3";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x0337;
    L_0x0331:
        r3 = 3080192; // 0x2f0000 float:4.316268E-39 double:1.521817E-317;
        gnu.expr.Compilation.defaultClassFileVersion = r3;
        goto L_0x0068;
    L_0x0337:
        r3 = "1.2";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x0345;
    L_0x033f:
        r3 = 3014656; // 0x2e0000 float:4.224433E-39 double:1.489438E-317;
        gnu.expr.Compilation.defaultClassFileVersion = r3;
        goto L_0x0068;
    L_0x0345:
        r3 = "1.1";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x0354;
    L_0x034d:
        r3 = 2949123; // 0x2d0003 float:4.132602E-39 double:1.4570604E-317;
        gnu.expr.Compilation.defaultClassFileVersion = r3;
        goto L_0x0068;
    L_0x0354:
        bad_option(r14);
        goto L_0x0068;
    L_0x0359:
        r3 = "-P";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x0372;
    L_0x0361:
        r46 = r46 + 1;
        r0 = r46;
        r1 = r47;
        if (r0 != r1) goto L_0x036c;
    L_0x0369:
        bad_option(r14);
    L_0x036c:
        r3 = r45[r46];
        gnu.expr.Compilation.classPrefixDefault = r3;
        goto L_0x0068;
    L_0x0372:
        r3 = "-T";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x038b;
    L_0x037a:
        r46 = r46 + 1;
        r0 = r46;
        r1 = r47;
        if (r0 != r1) goto L_0x0385;
    L_0x0382:
        bad_option(r14);
    L_0x0385:
        r3 = r45[r46];
        compilationTopname = r3;
        goto L_0x0068;
    L_0x038b:
        r3 = "-C";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x03a4;
    L_0x0393:
        r46 = r46 + 1;
        r0 = r46;
        r1 = r47;
        if (r0 != r1) goto L_0x039e;
    L_0x039b:
        bad_option(r14);
    L_0x039e:
        compileFiles(r45, r46, r47);
        r3 = -1;
        goto L_0x00e2;
    L_0x03a4:
        r3 = "--output-format";
        r3 = r14.equals(r3);
        if (r3 != 0) goto L_0x03b4;
    L_0x03ac:
        r3 = "--format";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x03c6;
    L_0x03b4:
        r46 = r46 + 1;
        r0 = r46;
        r1 = r47;
        if (r0 != r1) goto L_0x03bf;
    L_0x03bc:
        bad_option(r14);
    L_0x03bf:
        r3 = r45[r46];
        kawa.Shell.setDefaultFormat(r3);
        goto L_0x0068;
    L_0x03c6:
        r3 = "--connect";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x043c;
    L_0x03ce:
        r46 = r46 + 1;
        r0 = r46;
        r1 = r47;
        if (r0 != r1) goto L_0x03d9;
    L_0x03d6:
        bad_option(r14);
    L_0x03d9:
        r3 = r45[r46];
        r4 = "-";
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x042c;
    L_0x03e3:
        r31 = 0;
    L_0x03e5:
        r38 = new java.net.Socket;	 Catch:{ IOException -> 0x041a }
        r3 = 0;
        r3 = java.net.InetAddress.getByName(r3);	 Catch:{ IOException -> 0x041a }
        r0 = r38;
        r1 = r31;
        r0.<init>(r3, r1);	 Catch:{ IOException -> 0x041a }
        r17 = new kawa.Telnet;	 Catch:{ IOException -> 0x041a }
        r3 = 1;
        r0 = r17;
        r1 = r38;
        r0.<init>(r1, r3);	 Catch:{ IOException -> 0x041a }
        r36 = r17.getInputStream();	 Catch:{ IOException -> 0x041a }
        r40 = r17.getOutputStream();	 Catch:{ IOException -> 0x041a }
        r32 = new java.io.PrintStream;	 Catch:{ IOException -> 0x041a }
        r3 = 1;
        r0 = r32;
        r1 = r40;
        r0.<init>(r1, r3);	 Catch:{ IOException -> 0x041a }
        java.lang.System.setIn(r36);	 Catch:{ IOException -> 0x041a }
        java.lang.System.setOut(r32);	 Catch:{ IOException -> 0x041a }
        java.lang.System.setErr(r32);	 Catch:{ IOException -> 0x041a }
        goto L_0x0068;
    L_0x041a:
        r21 = move-exception;
        r3 = java.lang.System.err;
        r0 = r21;
        r0.printStackTrace(r3);
        r3 = new java.lang.Error;
        r4 = r21.toString();
        r3.<init>(r4);
        throw r3;
    L_0x042c:
        r3 = r45[r46];	 Catch:{ NumberFormatException -> 0x0433 }
        r31 = java.lang.Integer.parseInt(r3);	 Catch:{ NumberFormatException -> 0x0433 }
        goto L_0x03e5;
    L_0x0433:
        r21 = move-exception;
        r3 = "--connect port#";
        bad_option(r3);
        r31 = -1;
        goto L_0x03e5;
    L_0x043c:
        r3 = "--server";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x04e5;
    L_0x0444:
        getLanguage();
        r46 = r46 + 1;
        r0 = r46;
        r1 = r47;
        if (r0 != r1) goto L_0x0452;
    L_0x044f:
        bad_option(r14);
    L_0x0452:
        r3 = r45[r46];
        r4 = "-";
        r3 = r3.equals(r4);
        if (r3 == 0) goto L_0x04d4;
    L_0x045c:
        r31 = 0;
    L_0x045e:
        r41 = new java.net.ServerSocket;	 Catch:{ IOException -> 0x04c9 }
        r0 = r41;
        r1 = r31;
        r0.<init>(r1);	 Catch:{ IOException -> 0x04c9 }
        r31 = r41.getLocalPort();	 Catch:{ IOException -> 0x04c9 }
        r3 = java.lang.System.err;	 Catch:{ IOException -> 0x04c9 }
        r4 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x04c9 }
        r4.<init>();	 Catch:{ IOException -> 0x04c9 }
        r5 = "Listening on port ";
        r4 = r4.append(r5);	 Catch:{ IOException -> 0x04c9 }
        r0 = r31;
        r4 = r4.append(r0);	 Catch:{ IOException -> 0x04c9 }
        r4 = r4.toString();	 Catch:{ IOException -> 0x04c9 }
        r3.println(r4);	 Catch:{ IOException -> 0x04c9 }
    L_0x0485:
        r3 = java.lang.System.err;	 Catch:{ IOException -> 0x04c9 }
        r4 = "waiting ... ";
        r3.print(r4);	 Catch:{ IOException -> 0x04c9 }
        r3 = java.lang.System.err;	 Catch:{ IOException -> 0x04c9 }
        r3.flush();	 Catch:{ IOException -> 0x04c9 }
        r16 = r41.accept();	 Catch:{ IOException -> 0x04c9 }
        r3 = java.lang.System.err;	 Catch:{ IOException -> 0x04c9 }
        r4 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x04c9 }
        r4.<init>();	 Catch:{ IOException -> 0x04c9 }
        r5 = "got connection from ";
        r4 = r4.append(r5);	 Catch:{ IOException -> 0x04c9 }
        r5 = r16.getInetAddress();	 Catch:{ IOException -> 0x04c9 }
        r4 = r4.append(r5);	 Catch:{ IOException -> 0x04c9 }
        r5 = " port:";
        r4 = r4.append(r5);	 Catch:{ IOException -> 0x04c9 }
        r5 = r16.getPort();	 Catch:{ IOException -> 0x04c9 }
        r4 = r4.append(r5);	 Catch:{ IOException -> 0x04c9 }
        r4 = r4.toString();	 Catch:{ IOException -> 0x04c9 }
        r3.println(r4);	 Catch:{ IOException -> 0x04c9 }
        r3 = gnu.expr.Language.getDefaultLanguage();	 Catch:{ IOException -> 0x04c9 }
        r0 = r16;
        kawa.TelnetRepl.serve(r3, r0);	 Catch:{ IOException -> 0x04c9 }
        goto L_0x0485;
    L_0x04c9:
        r21 = move-exception;
        r3 = new java.lang.Error;
        r4 = r21.toString();
        r3.<init>(r4);
        throw r3;
    L_0x04d4:
        r3 = r45[r46];	 Catch:{ NumberFormatException -> 0x04db }
        r31 = java.lang.Integer.parseInt(r3);	 Catch:{ NumberFormatException -> 0x04db }
        goto L_0x045e;
    L_0x04db:
        r21 = move-exception;
        r3 = "--server port#";
        bad_option(r3);
        r31 = -1;
        goto L_0x045e;
    L_0x04e5:
        r3 = "--http-auto-handler";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x0505;
    L_0x04ed:
        r46 = r46 + 2;
        r0 = r46;
        r1 = r47;
        if (r0 < r1) goto L_0x04f8;
    L_0x04f5:
        bad_option(r14);
    L_0x04f8:
        r3 = java.lang.System.err;
        r4 = "kawa: HttpServer classes not found";
        r3.println(r4);
        r3 = -1;
        java.lang.System.exit(r3);
        goto L_0x0068;
    L_0x0505:
        r3 = "--http-start";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x0527;
    L_0x050d:
        r46 = r46 + 1;
        r0 = r46;
        r1 = r47;
        if (r0 < r1) goto L_0x051a;
    L_0x0515:
        r3 = "missing httpd port argument";
        bad_option(r3);
    L_0x051a:
        r3 = java.lang.System.err;
        r4 = "kawa: HttpServer classes not found";
        r3.println(r4);
        r3 = -1;
        java.lang.System.exit(r3);
        goto L_0x0068;
    L_0x0527:
        r3 = "--main";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x0534;
    L_0x052f:
        r3 = 1;
        gnu.expr.Compilation.generateMainDefault = r3;
        goto L_0x0068;
    L_0x0534:
        r3 = "--applet";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x0544;
    L_0x053c:
        r3 = defaultParseOptions;
        r3 = r3 | 16;
        defaultParseOptions = r3;
        goto L_0x0068;
    L_0x0544:
        r3 = "--servlet";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x0557;
    L_0x054c:
        r3 = defaultParseOptions;
        r3 = r3 | 32;
        defaultParseOptions = r3;
        r3 = 2;
        gnu.kawa.servlet.HttpRequestContext.importServletDefinitions = r3;
        goto L_0x0068;
    L_0x0557:
        r3 = "--debug-dump-zip";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x0565;
    L_0x055f:
        r3 = "kawa-zip-dump-";
        gnu.expr.ModuleExp.dumpZipPrefix = r3;
        goto L_0x0068;
    L_0x0565:
        r3 = "--debug-print-expr";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x0572;
    L_0x056d:
        r3 = 1;
        gnu.expr.Compilation.debugPrintExpr = r3;
        goto L_0x0068;
    L_0x0572:
        r3 = "--debug-print-final-expr";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x057f;
    L_0x057a:
        r3 = 1;
        gnu.expr.Compilation.debugPrintFinalExpr = r3;
        goto L_0x0068;
    L_0x057f:
        r3 = "--debug-error-prints-stack-trace";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x058c;
    L_0x0587:
        r3 = 1;
        gnu.text.SourceMessages.debugStackTraceOnError = r3;
        goto L_0x0068;
    L_0x058c:
        r3 = "--debug-warning-prints-stack-trace";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x0599;
    L_0x0594:
        r3 = 1;
        gnu.text.SourceMessages.debugStackTraceOnWarning = r3;
        goto L_0x0068;
    L_0x0599:
        r3 = "--module-nonstatic";
        r3 = r14.equals(r3);
        if (r3 != 0) goto L_0x05a9;
    L_0x05a1:
        r3 = "--no-module-static";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x05ae;
    L_0x05a9:
        r3 = -1;
        gnu.expr.Compilation.moduleStatic = r3;
        goto L_0x0068;
    L_0x05ae:
        r3 = "--module-static";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x05bb;
    L_0x05b6:
        r3 = 1;
        gnu.expr.Compilation.moduleStatic = r3;
        goto L_0x0068;
    L_0x05bb:
        r3 = "--module-static-run";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x05c8;
    L_0x05c3:
        r3 = 2;
        gnu.expr.Compilation.moduleStatic = r3;
        goto L_0x0068;
    L_0x05c8:
        r3 = "--no-inline";
        r3 = r14.equals(r3);
        if (r3 != 0) goto L_0x05d8;
    L_0x05d0:
        r3 = "--inline=none";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x05dd;
    L_0x05d8:
        r3 = 0;
        gnu.expr.Compilation.inlineOk = r3;
        goto L_0x0068;
    L_0x05dd:
        r3 = "--no-console";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x05ea;
    L_0x05e5:
        r3 = 1;
        noConsole = r3;
        goto L_0x0068;
    L_0x05ea:
        r3 = "--inline";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x05f7;
    L_0x05f2:
        r3 = 1;
        gnu.expr.Compilation.inlineOk = r3;
        goto L_0x0068;
    L_0x05f7:
        r3 = "--cps";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x0604;
    L_0x05ff:
        r3 = 4;
        gnu.expr.Compilation.defaultCallConvention = r3;
        goto L_0x0068;
    L_0x0604:
        r3 = "--full-tailcalls";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x0611;
    L_0x060c:
        r3 = 3;
        gnu.expr.Compilation.defaultCallConvention = r3;
        goto L_0x0068;
    L_0x0611:
        r3 = "--no-full-tailcalls";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x061e;
    L_0x0619:
        r3 = 1;
        gnu.expr.Compilation.defaultCallConvention = r3;
        goto L_0x0068;
    L_0x061e:
        r3 = "--pedantic";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x062b;
    L_0x0626:
        r3 = 1;
        gnu.expr.Language.requirePedantic = r3;
        goto L_0x0068;
    L_0x062b:
        r3 = "--help";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x063e;
    L_0x0633:
        r3 = java.lang.System.out;
        printOptions(r3);
        r3 = 0;
        java.lang.System.exit(r3);
        goto L_0x0068;
    L_0x063e:
        r3 = "--author";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x0653;
    L_0x0646:
        r3 = java.lang.System.out;
        r4 = "Per Bothner <per@bothner.com>";
        r3.println(r4);
        r3 = 0;
        java.lang.System.exit(r3);
        goto L_0x0068;
    L_0x0653:
        r3 = "--version";
        r3 = r14.equals(r3);
        if (r3 == 0) goto L_0x067b;
    L_0x065b:
        r3 = java.lang.System.out;
        r4 = "Kawa ";
        r3.print(r4);
        r3 = java.lang.System.out;
        r4 = kawa.Version.getVersion();
        r3.print(r4);
        r3 = java.lang.System.out;
        r3.println();
        r3 = java.lang.System.out;
        r4 = "Copyright (C) 2009 Per Bothner";
        r3.println(r4);
        r39 = 1;
        goto L_0x0068;
    L_0x067b:
        r3 = r14.length();
        if (r3 <= 0) goto L_0x076c;
    L_0x0681:
        r3 = 0;
        r3 = r14.charAt(r3);
        r4 = 45;
        if (r3 != r4) goto L_0x076c;
    L_0x068a:
        r28 = r14;
        r3 = r28.length();
        r4 = 2;
        if (r3 <= r4) goto L_0x06b0;
    L_0x0693:
        r3 = 0;
        r0 = r28;
        r3 = r0.charAt(r3);
        r4 = 45;
        if (r3 != r4) goto L_0x06b0;
    L_0x069e:
        r3 = 1;
        r0 = r28;
        r3 = r0.charAt(r3);
        r4 = 45;
        if (r3 != r4) goto L_0x06c1;
    L_0x06a9:
        r3 = 2;
    L_0x06aa:
        r0 = r28;
        r28 = r0.substring(r3);
    L_0x06b0:
        r25 = gnu.expr.Language.getInstance(r28);
        if (r25 == 0) goto L_0x06c7;
    L_0x06b6:
        r3 = previousLanguage;
        if (r3 != 0) goto L_0x06c3;
    L_0x06ba:
        gnu.expr.Language.setDefaults(r25);
    L_0x06bd:
        previousLanguage = r25;
        goto L_0x0068;
    L_0x06c1:
        r3 = 1;
        goto L_0x06aa;
    L_0x06c3:
        gnu.expr.Language.setCurrentLanguage(r25);
        goto L_0x06bd;
    L_0x06c7:
        r3 = "=";
        r0 = r28;
        r19 = r0.indexOf(r3);
        if (r19 >= 0) goto L_0x072d;
    L_0x06d1:
        r30 = 0;
    L_0x06d3:
        r3 = "no-";
        r0 = r28;
        r3 = r0.startsWith(r3);
        if (r3 == 0) goto L_0x073f;
    L_0x06dd:
        r3 = r28.length();
        r4 = 3;
        if (r3 <= r4) goto L_0x073f;
    L_0x06e4:
        r42 = 1;
    L_0x06e6:
        if (r30 != 0) goto L_0x06f3;
    L_0x06e8:
        if (r42 == 0) goto L_0x06f3;
    L_0x06ea:
        r30 = "no";
        r3 = 3;
        r0 = r28;
        r28 = r0.substring(r3);
    L_0x06f3:
        r3 = gnu.expr.Compilation.options;
        r0 = r28;
        r1 = r30;
        r27 = r3.set(r0, r1);
        if (r27 == 0) goto L_0x0068;
    L_0x06ff:
        if (r42 == 0) goto L_0x0722;
    L_0x0701:
        r3 = "unknown option name";
        r0 = r27;
        if (r0 != r3) goto L_0x0722;
    L_0x0707:
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "both '--no-' prefix and '=";
        r3 = r3.append(r4);
        r0 = r30;
        r3 = r3.append(r0);
        r4 = "' specified";
        r3 = r3.append(r4);
        r27 = r3.toString();
    L_0x0722:
        r3 = "unknown option name";
        r0 = r27;
        if (r0 != r3) goto L_0x0742;
    L_0x0728:
        bad_option(r14);
        goto L_0x0068;
    L_0x072d:
        r3 = r19 + 1;
        r0 = r28;
        r30 = r0.substring(r3);
        r3 = 0;
        r0 = r28;
        r1 = r19;
        r28 = r0.substring(r3, r1);
        goto L_0x06d3;
    L_0x073f:
        r42 = 0;
        goto L_0x06e6;
    L_0x0742:
        r3 = java.lang.System.err;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "kawa: bad option '";
        r4 = r4.append(r5);
        r4 = r4.append(r14);
        r5 = "': ";
        r4 = r4.append(r5);
        r0 = r27;
        r4 = r4.append(r0);
        r4 = r4.toString();
        r3.println(r4);
        r3 = -1;
        java.lang.System.exit(r3);
        goto L_0x0068;
    L_0x076c:
        r3 = gnu.expr.ApplicationMainSupport.processSetProperty(r14);
        if (r3 != 0) goto L_0x0068;
    L_0x0772:
        if (r39 == 0) goto L_0x0777;
    L_0x0774:
        r3 = -1;
        goto L_0x00e2;
    L_0x0777:
        r3 = r46;
        goto L_0x00e2;
        */
        throw new UnsupportedOperationException("Method not decompiled: kawa.repl.processArgs(java.lang.String[], int, int):int");
    }

    public static void compileFiles(String[] args, int iArg, int maxArg) {
        int i;
        ModuleManager manager = ModuleManager.getInstance();
        Compilation[] comps = new Compilation[(maxArg - iArg)];
        ModuleInfo[] infos = new ModuleInfo[(maxArg - iArg)];
        SourceMessages messages = new SourceMessages();
        for (i = iArg; i < maxArg; i++) {
            Compilation comp;
            String arg = args[i];
            getLanguageFromFilenameExtension(arg);
            try {
                comp = Language.getDefaultLanguage().parse(InPort.openFile(arg), messages, defaultParseOptions);
                if (compilationTopname != null) {
                    ClassType ctype = new ClassType(Compilation.mangleNameIfNeeded(compilationTopname));
                    ModuleExp mexp = comp.getModule();
                    mexp.setType(ctype);
                    mexp.setName(compilationTopname);
                    comp.mainClass = ctype;
                }
                infos[i - iArg] = manager.find(comp);
                comps[i - iArg] = comp;
            } catch (FileNotFoundException ex) {
                System.err.println(ex);
                System.exit(-1);
                break;
            } catch (Throwable ex2) {
                if (!((ex2 instanceof SyntaxException) && ((SyntaxException) ex2).getMessages() == messages)) {
                    internalError(ex2, null, arg);
                }
            }
            if (messages.seenErrorsOrWarnings()) {
                System.err.println("(compiling " + arg + ')');
                if (messages.checkErrors(System.err, 20)) {
                    System.exit(1);
                }
            }
        }
        for (i = iArg; i < maxArg; i++) {
            arg = args[i];
            comp = comps[i - iArg];
            try {
                System.err.println("(compiling " + arg + " to " + comp.mainClass.getName() + ')');
                infos[i - iArg].loadByStages(14);
                boolean sawErrors = messages.seenErrors();
                messages.checkErrors(System.err, 50);
                if (sawErrors) {
                    System.exit(-1);
                }
                comps[i - iArg] = comp;
                sawErrors = messages.seenErrors();
                messages.checkErrors(System.err, 50);
                if (sawErrors) {
                    System.exit(-1);
                }
            } catch (Throwable ex22) {
                internalError(ex22, comp, arg);
            }
        }
    }

    static void internalError(Throwable ex, Compilation comp, Object arg) {
        StringBuffer sbuf = new StringBuffer();
        if (comp != null) {
            String file = comp.getFileName();
            int line = comp.getLineNumber();
            if (file != null && line > 0) {
                sbuf.append(file);
                sbuf.append(':');
                sbuf.append(line);
                sbuf.append(": ");
            }
        }
        sbuf.append("internal error while compiling ");
        sbuf.append(arg);
        System.err.println(sbuf.toString());
        ex.printStackTrace(System.err);
        System.exit(-1);
    }

    public static void main(String[] args) {
        try {
            int iArg = processArgs(args, 0, args.length);
            if (iArg >= 0) {
                if (iArg < args.length) {
                    String filename = args[iArg];
                    getLanguageFromFilenameExtension(filename);
                    setArgs(args, iArg + 1);
                    checkInitFile();
                    boolean runFileOrClass = Shell.runFileOrClass(filename, false, 0);
                } else {
                    getLanguage();
                    setArgs(args, iArg);
                    checkInitFile();
                    if (shouldUseGuiConsole()) {
                        startGuiConsole();
                    } else if (!Shell.run(Language.getDefaultLanguage(), Environment.getCurrent())) {
                        System.exit(-1);
                    }
                }
                if (!shutdownRegistered) {
                    OutPort.runCleanups();
                }
                ModuleBody.exitDecrement();
            }
        } finally {
            if (!shutdownRegistered) {
                OutPort.runCleanups();
            }
            ModuleBody.exitDecrement();
        }
    }

    public static boolean shouldUseGuiConsole() {
        if (noConsole) {
            return true;
        }
        try {
            if (Class.forName("java.lang.System").getMethod("console", new Class[0]).invoke(new Object[0], new Object[0]) == null) {
                return true;
            }
        } catch (Throwable th) {
        }
        return false;
    }

    private static void startGuiConsole() {
        try {
            Class.forName("kawa.GuiConsole").newInstance();
        } catch (Exception ex) {
            System.err.println("failed to create Kawa window: " + ex);
            System.exit(-1);
        }
    }
}
