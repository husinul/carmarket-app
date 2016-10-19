package kawa.lib;

import gnu.expr.LambdaExp;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.SetExp;
import gnu.kawa.functions.ArithOp;
import gnu.kawa.functions.FileUtils;
import gnu.kawa.functions.Format;
import gnu.kawa.functions.IsEqual;
import gnu.kawa.functions.ParseFormat;
import gnu.kawa.xml.XDataType;
import gnu.lists.Consumer;
import gnu.lists.LList;
import gnu.lists.Sequence;
import gnu.mapping.CallContext;
import gnu.mapping.InPort;
import gnu.mapping.OutPort;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import gnu.text.FilePath;
import gnu.text.Path;
import gnu.text.URIPath;
import java.io.File;
import java.io.IOException;
import kawa.standard.readchar;

/* compiled from: files.scm */
public class files extends ModuleBody {
    public static final ModuleMethod $Mn$Grpathname;
    public static final ModuleMethod $Pcfile$Mnseparator;
    public static final files $instance;
    static final SimpleSymbol Lit0;
    static final SimpleSymbol Lit1;
    static final SimpleSymbol Lit10;
    static final SimpleSymbol Lit11;
    static final SimpleSymbol Lit12;
    static final SimpleSymbol Lit13;
    static final SimpleSymbol Lit14;
    static final SimpleSymbol Lit15;
    static final SimpleSymbol Lit16;
    static final SimpleSymbol Lit17;
    static final SimpleSymbol Lit18;
    static final SimpleSymbol Lit19;
    static final SimpleSymbol Lit2;
    static final SimpleSymbol Lit20;
    static final SimpleSymbol Lit21;
    static final SimpleSymbol Lit22;
    static final SimpleSymbol Lit23;
    static final SimpleSymbol Lit24;
    static final SimpleSymbol Lit25;
    static final SimpleSymbol Lit26;
    static final SimpleSymbol Lit27;
    static final SimpleSymbol Lit28;
    static final SimpleSymbol Lit29;
    static final SimpleSymbol Lit3;
    static final SimpleSymbol Lit4;
    static final SimpleSymbol Lit5;
    static final SimpleSymbol Lit6;
    static final SimpleSymbol Lit7;
    static final SimpleSymbol Lit8;
    static final SimpleSymbol Lit9;
    public static final ModuleMethod URI$Qu;
    public static final ModuleMethod absolute$Mnpath$Qu;
    public static final ModuleMethod copy$Mnfile;
    public static final ModuleMethod create$Mndirectory;
    public static final ModuleMethod delete$Mnfile;
    public static final ModuleMethod directory$Mnfiles;
    public static final ModuleMethod file$Mndirectory$Qu;
    public static final ModuleMethod file$Mnexists$Qu;
    public static final ModuleMethod file$Mnreadable$Qu;
    public static final ModuleMethod file$Mnwritable$Qu;
    public static final ModuleMethod filepath$Qu;
    public static final ModuleMethod make$Mntemporary$Mnfile;
    public static final ModuleMethod path$Mnauthority;
    public static final ModuleMethod path$Mndirectory;
    public static final ModuleMethod path$Mnextension;
    public static final ModuleMethod path$Mnfile;
    public static final ModuleMethod path$Mnfragment;
    public static final ModuleMethod path$Mnhost;
    public static final ModuleMethod path$Mnlast;
    public static final ModuleMethod path$Mnparent;
    public static final ModuleMethod path$Mnport;
    public static final ModuleMethod path$Mnquery;
    public static final ModuleMethod path$Mnscheme;
    public static final ModuleMethod path$Mnuser$Mninfo;
    public static final ModuleMethod path$Qu;
    public static final ModuleMethod rename$Mnfile;
    public static final ModuleMethod resolve$Mnuri;
    public static final ModuleMethod system$Mntmpdir;

    static {
        Lit29 = (SimpleSymbol) new SimpleSymbol("make-temporary-file").readResolve();
        Lit28 = (SimpleSymbol) new SimpleSymbol("resolve-uri").readResolve();
        Lit27 = (SimpleSymbol) new SimpleSymbol("system-tmpdir").readResolve();
        Lit26 = (SimpleSymbol) new SimpleSymbol("%file-separator").readResolve();
        Lit25 = (SimpleSymbol) new SimpleSymbol("->pathname").readResolve();
        Lit24 = (SimpleSymbol) new SimpleSymbol("directory-files").readResolve();
        Lit23 = (SimpleSymbol) new SimpleSymbol("create-directory").readResolve();
        Lit22 = (SimpleSymbol) new SimpleSymbol("copy-file").readResolve();
        Lit21 = (SimpleSymbol) new SimpleSymbol("rename-file").readResolve();
        Lit20 = (SimpleSymbol) new SimpleSymbol("delete-file").readResolve();
        Lit19 = (SimpleSymbol) new SimpleSymbol("file-writable?").readResolve();
        Lit18 = (SimpleSymbol) new SimpleSymbol("file-readable?").readResolve();
        Lit17 = (SimpleSymbol) new SimpleSymbol("file-directory?").readResolve();
        Lit16 = (SimpleSymbol) new SimpleSymbol("file-exists?").readResolve();
        Lit15 = (SimpleSymbol) new SimpleSymbol("path-fragment").readResolve();
        Lit14 = (SimpleSymbol) new SimpleSymbol("path-query").readResolve();
        Lit13 = (SimpleSymbol) new SimpleSymbol("path-port").readResolve();
        Lit12 = (SimpleSymbol) new SimpleSymbol("path-extension").readResolve();
        Lit11 = (SimpleSymbol) new SimpleSymbol("path-last").readResolve();
        Lit10 = (SimpleSymbol) new SimpleSymbol("path-parent").readResolve();
        Lit9 = (SimpleSymbol) new SimpleSymbol("path-directory").readResolve();
        Lit8 = (SimpleSymbol) new SimpleSymbol("path-file").readResolve();
        Lit7 = (SimpleSymbol) new SimpleSymbol("path-host").readResolve();
        Lit6 = (SimpleSymbol) new SimpleSymbol("path-user-info").readResolve();
        Lit5 = (SimpleSymbol) new SimpleSymbol("path-authority").readResolve();
        Lit4 = (SimpleSymbol) new SimpleSymbol("path-scheme").readResolve();
        Lit3 = (SimpleSymbol) new SimpleSymbol("absolute-path?").readResolve();
        Lit2 = (SimpleSymbol) new SimpleSymbol("URI?").readResolve();
        Lit1 = (SimpleSymbol) new SimpleSymbol("filepath?").readResolve();
        Lit0 = (SimpleSymbol) new SimpleSymbol("path?").readResolve();
        $instance = new files();
        ModuleBody moduleBody = $instance;
        path$Qu = new ModuleMethod(moduleBody, 1, Lit0, 4097);
        filepath$Qu = new ModuleMethod(moduleBody, 2, Lit1, 4097);
        URI$Qu = new ModuleMethod(moduleBody, 3, Lit2, 4097);
        absolute$Mnpath$Qu = new ModuleMethod(moduleBody, 4, Lit3, 4097);
        path$Mnscheme = new ModuleMethod(moduleBody, 5, Lit4, 4097);
        path$Mnauthority = new ModuleMethod(moduleBody, 6, Lit5, 4097);
        path$Mnuser$Mninfo = new ModuleMethod(moduleBody, 7, Lit6, 4097);
        path$Mnhost = new ModuleMethod(moduleBody, 8, Lit7, 4097);
        path$Mnfile = new ModuleMethod(moduleBody, 9, Lit8, 4097);
        path$Mndirectory = new ModuleMethod(moduleBody, 10, Lit9, 4097);
        path$Mnparent = new ModuleMethod(moduleBody, 11, Lit10, 4097);
        path$Mnlast = new ModuleMethod(moduleBody, 12, Lit11, 4097);
        path$Mnextension = new ModuleMethod(moduleBody, 13, Lit12, 4097);
        path$Mnport = new ModuleMethod(moduleBody, 14, Lit13, 4097);
        path$Mnquery = new ModuleMethod(moduleBody, 15, Lit14, 4097);
        path$Mnfragment = new ModuleMethod(moduleBody, 16, Lit15, 4097);
        file$Mnexists$Qu = new ModuleMethod(moduleBody, 17, Lit16, 4097);
        file$Mndirectory$Qu = new ModuleMethod(moduleBody, 18, Lit17, 4097);
        file$Mnreadable$Qu = new ModuleMethod(moduleBody, 19, Lit18, 4097);
        file$Mnwritable$Qu = new ModuleMethod(moduleBody, 20, Lit19, 4097);
        delete$Mnfile = new ModuleMethod(moduleBody, 21, Lit20, 4097);
        rename$Mnfile = new ModuleMethod(moduleBody, 22, Lit21, 8194);
        copy$Mnfile = new ModuleMethod(moduleBody, 23, Lit22, 8194);
        create$Mndirectory = new ModuleMethod(moduleBody, 24, Lit23, 4097);
        directory$Mnfiles = new ModuleMethod(moduleBody, 25, Lit24, 4097);
        $Mn$Grpathname = new ModuleMethod(moduleBody, 26, Lit25, 4097);
        $Pcfile$Mnseparator = new ModuleMethod(moduleBody, 27, Lit26, 0);
        system$Mntmpdir = new ModuleMethod(moduleBody, 28, Lit27, 0);
        resolve$Mnuri = new ModuleMethod(moduleBody, 29, Lit28, 8194);
        make$Mntemporary$Mnfile = new ModuleMethod(moduleBody, 30, Lit29, LambdaExp.ATTEMPT_INLINE);
        $instance.run();
    }

    public files() {
        ModuleInfo.register(this);
    }

    public static FilePath makeTemporaryFile() {
        return makeTemporaryFile("kawa~d.tmp");
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    public static boolean isPath(Object path) {
        return path instanceof Path;
    }

    public int match1(ModuleMethod moduleMethod, Object obj, CallContext callContext) {
        switch (moduleMethod.selector) {
            case ParseFormat.SEEN_MINUS /*1*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case SetExp.DEFINING_FLAG /*2*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case SetExp.GLOBAL_FLAG /*4*/:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case ArithOp.DIVIDE_INEXACT /*5*/:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case ArithOp.QUOTIENT /*6*/:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case ArithOp.QUOTIENT_EXACT /*7*/:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case SetExp.PREFER_BINDING2 /*8*/:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case ArithOp.ASHIFT_GENERAL /*9*/:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case ArithOp.ASHIFT_LEFT /*10*/:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case ArithOp.ASHIFT_RIGHT /*11*/:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case ArithOp.LSHIFT_RIGHT /*12*/:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case ArithOp.AND /*13*/:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case ArithOp.IOR /*14*/:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case ArithOp.XOR /*15*/:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case SetExp.PROCEDURE /*16*/:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.INT_U8_VALUE /*17*/:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.INT_S8_VALUE /*18*/:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.INT_U16_VALUE /*19*/:
                if (FilePath.coerceToFilePathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.INT_S16_VALUE /*20*/:
                if (FilePath.coerceToFilePathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.INT_U32_VALUE /*21*/:
                if (FilePath.coerceToFilePathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.INT_S64_VALUE /*24*/:
                if (FilePath.coerceToFilePathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.FLOAT_VALUE /*25*/:
                if (FilePath.coerceToFilePathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.DOUBLE_VALUE /*26*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case XDataType.DAY_TIME_DURATION_TYPE_CODE /*30*/:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            default:
                return super.match1(moduleMethod, obj, callContext);
        }
    }

    public static boolean isFilepath(Object path) {
        return path instanceof FilePath;
    }

    public static boolean URI$Qu(Object path) {
        return path instanceof URIPath;
    }

    public static boolean isAbsolutePath(Path path) {
        return path.isAbsolute();
    }

    public static Object pathScheme(Path p) {
        String s = p.getScheme();
        return s == null ? Boolean.FALSE : s;
    }

    public static Object pathAuthority(Path p) {
        String s = p.getAuthority();
        return s == null ? Boolean.FALSE : s;
    }

    public static Object pathUserInfo(Path p) {
        String s = p.getUserInfo();
        return s == null ? Boolean.FALSE : s;
    }

    public static String pathHost(Path p) {
        return p.getHost();
    }

    public static Object pathFile(Path p) {
        String s = p.getPath();
        return s == null ? Boolean.FALSE : s;
    }

    public static Object pathDirectory(Path p) {
        Path s = p.getDirectory();
        return s == null ? Boolean.FALSE : s.toString();
    }

    public static Object pathParent(Path p) {
        Path s = p.getParent();
        return s == null ? Boolean.FALSE : s.toString();
    }

    public static Object pathLast(Path p) {
        String s = p.getLast();
        return s == null ? Boolean.FALSE : s;
    }

    public static Object pathExtension(Path p) {
        String s = p.getExtension();
        return s == null ? Boolean.FALSE : s;
    }

    public static int pathPort(Path p) {
        return p.getPort();
    }

    public static Object pathQuery(Path p) {
        String s = p.getQuery();
        return s == null ? Boolean.FALSE : s;
    }

    public static Object pathFragment(Path p) {
        String s = p.getFragment();
        return s == null ? Boolean.FALSE : s;
    }

    public static boolean isFileExists(Path file) {
        return file.exists();
    }

    public static boolean isFileDirectory(Path file) {
        return file.isDirectory();
    }

    public static boolean isFileReadable(FilePath file) {
        return file.toFile().canRead();
    }

    public static boolean isFileWritable(FilePath file) {
        return file.toFile().canWrite();
    }

    public static void deleteFile(FilePath file) {
        if (!file.delete()) {
            throw new IOException(Format.formatToString(0, "cannot delete ~a", file).toString());
        }
    }

    public static boolean renameFile(FilePath oldname, FilePath newname) {
        return oldname.toFile().renameTo(newname.toFile());
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case Sequence.INT_S32_VALUE /*22*/:
                if (FilePath.coerceToFilePathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (FilePath.coerceToFilePathOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case Sequence.INT_U64_VALUE /*23*/:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (Path.coerceToPathOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case Sequence.CHAR_VALUE /*29*/:
                if (Path.coerceToPathOrNull(obj) == null) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (Path.coerceToPathOrNull(obj2) == null) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public static void copyFile(Path from, Path to) {
        InPort in = ports.openInputFile(from);
        OutPort out = ports.openOutputFile(to);
        for (Object ch = readchar.readChar.apply1(in); !ports.isEofObject(ch); ch = readchar.readChar.apply1(in)) {
            ports.writeChar(ch, out);
        }
        ports.closeOutputPort(out);
        ports.closeInputPort(in);
    }

    public static boolean createDirectory(FilePath dirname) {
        return dirname.toFile().mkdir();
    }

    public static Object directoryFiles(FilePath dir) {
        File toFile = dir.toFile();
        String[] files = new File(toFile == null ? null : toFile.toString()).list();
        if (files == null) {
            return Boolean.FALSE;
        }
        return LList.makeList(files, 0);
    }

    public static Path $To$Pathname(Object filename) {
        return Path.valueOf(filename);
    }

    public static String $PcFileSeparator() {
        return System.getProperty("file.separator");
    }

    public int match0(ModuleMethod moduleMethod, CallContext callContext) {
        switch (moduleMethod.selector) {
            case Sequence.BOOLEAN_VALUE /*27*/:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case Sequence.TEXT_BYTE_VALUE /*28*/:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case XDataType.DAY_TIME_DURATION_TYPE_CODE /*30*/:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            default:
                return super.match0(moduleMethod, callContext);
        }
    }

    public static String systemTmpdir() {
        String name = System.getProperty("java.io.tmpdir");
        if (name != null) {
            return name;
        }
        return IsEqual.apply($PcFileSeparator(), "\\") ? "C:\\temp" : "/tmp";
    }

    public static Path resolveUri(Path uri, Path base) {
        return base.resolve(uri);
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        switch (moduleMethod.selector) {
            case Sequence.INT_S32_VALUE /*22*/:
                try {
                    try {
                        return renameFile(FilePath.makeFilePath(obj), FilePath.makeFilePath(obj2)) ? Boolean.TRUE : Boolean.FALSE;
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "rename-file", 2, obj2);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "rename-file", 1, obj);
                }
            case Sequence.INT_U64_VALUE /*23*/:
                try {
                    try {
                        copyFile(Path.valueOf(obj), Path.valueOf(obj2));
                        return Values.empty;
                    } catch (ClassCastException e22) {
                        throw new WrongType(e22, "copy-file", 2, obj2);
                    }
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "copy-file", 1, obj);
                }
            case Sequence.CHAR_VALUE /*29*/:
                try {
                    try {
                        return resolveUri(Path.valueOf(obj), Path.valueOf(obj2));
                    } catch (ClassCastException e2222) {
                        throw new WrongType(e2222, "resolve-uri", 2, obj2);
                    }
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "resolve-uri", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public static FilePath makeTemporaryFile(CharSequence fmt) {
        return FilePath.makeFilePath(FileUtils.createTempFile(fmt.toString()));
    }

    public Object apply0(ModuleMethod moduleMethod) {
        switch (moduleMethod.selector) {
            case Sequence.BOOLEAN_VALUE /*27*/:
                return $PcFileSeparator();
            case Sequence.TEXT_BYTE_VALUE /*28*/:
                return systemTmpdir();
            case XDataType.DAY_TIME_DURATION_TYPE_CODE /*30*/:
                return makeTemporaryFile();
            default:
                return super.apply0(moduleMethod);
        }
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case ParseFormat.SEEN_MINUS /*1*/:
                return isPath(obj) ? Boolean.TRUE : Boolean.FALSE;
            case SetExp.DEFINING_FLAG /*2*/:
                return isFilepath(obj) ? Boolean.TRUE : Boolean.FALSE;
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                return URI$Qu(obj) ? Boolean.TRUE : Boolean.FALSE;
            case SetExp.GLOBAL_FLAG /*4*/:
                try {
                    return isAbsolutePath(Path.valueOf(obj)) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e) {
                    throw new WrongType(e, "absolute-path?", 1, obj);
                }
            case ArithOp.DIVIDE_INEXACT /*5*/:
                try {
                    return pathScheme(Path.valueOf(obj));
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "path-scheme", 1, obj);
                }
            case ArithOp.QUOTIENT /*6*/:
                try {
                    return pathAuthority(Path.valueOf(obj));
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "path-authority", 1, obj);
                }
            case ArithOp.QUOTIENT_EXACT /*7*/:
                try {
                    return pathUserInfo(Path.valueOf(obj));
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "path-user-info", 1, obj);
                }
            case SetExp.PREFER_BINDING2 /*8*/:
                try {
                    return pathHost(Path.valueOf(obj));
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "path-host", 1, obj);
                }
            case ArithOp.ASHIFT_GENERAL /*9*/:
                try {
                    return pathFile(Path.valueOf(obj));
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "path-file", 1, obj);
                }
            case ArithOp.ASHIFT_LEFT /*10*/:
                try {
                    return pathDirectory(Path.valueOf(obj));
                } catch (ClassCastException e222222) {
                    throw new WrongType(e222222, "path-directory", 1, obj);
                }
            case ArithOp.ASHIFT_RIGHT /*11*/:
                try {
                    return pathParent(Path.valueOf(obj));
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "path-parent", 1, obj);
                }
            case ArithOp.LSHIFT_RIGHT /*12*/:
                try {
                    return pathLast(Path.valueOf(obj));
                } catch (ClassCastException e22222222) {
                    throw new WrongType(e22222222, "path-last", 1, obj);
                }
            case ArithOp.AND /*13*/:
                try {
                    return pathExtension(Path.valueOf(obj));
                } catch (ClassCastException e222222222) {
                    throw new WrongType(e222222222, "path-extension", 1, obj);
                }
            case ArithOp.IOR /*14*/:
                try {
                    return Integer.valueOf(pathPort(Path.valueOf(obj)));
                } catch (ClassCastException e2222222222) {
                    throw new WrongType(e2222222222, "path-port", 1, obj);
                }
            case ArithOp.XOR /*15*/:
                try {
                    return pathQuery(Path.valueOf(obj));
                } catch (ClassCastException e22222222222) {
                    throw new WrongType(e22222222222, "path-query", 1, obj);
                }
            case SetExp.PROCEDURE /*16*/:
                try {
                    return pathFragment(Path.valueOf(obj));
                } catch (ClassCastException e222222222222) {
                    throw new WrongType(e222222222222, "path-fragment", 1, obj);
                }
            case Sequence.INT_U8_VALUE /*17*/:
                try {
                    return isFileExists(Path.valueOf(obj)) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e2222222222222) {
                    throw new WrongType(e2222222222222, "file-exists?", 1, obj);
                }
            case Sequence.INT_S8_VALUE /*18*/:
                try {
                    return isFileDirectory(Path.valueOf(obj)) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e22222222222222) {
                    throw new WrongType(e22222222222222, "file-directory?", 1, obj);
                }
            case Sequence.INT_U16_VALUE /*19*/:
                try {
                    return isFileReadable(FilePath.makeFilePath(obj)) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e222222222222222) {
                    throw new WrongType(e222222222222222, "file-readable?", 1, obj);
                }
            case Sequence.INT_S16_VALUE /*20*/:
                try {
                    return isFileWritable(FilePath.makeFilePath(obj)) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e2222222222222222) {
                    throw new WrongType(e2222222222222222, "file-writable?", 1, obj);
                }
            case Sequence.INT_U32_VALUE /*21*/:
                try {
                    deleteFile(FilePath.makeFilePath(obj));
                    return Values.empty;
                } catch (ClassCastException e22222222222222222) {
                    throw new WrongType(e22222222222222222, "delete-file", 1, obj);
                }
            case Sequence.INT_S64_VALUE /*24*/:
                try {
                    return createDirectory(FilePath.makeFilePath(obj)) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e222222222222222222) {
                    throw new WrongType(e222222222222222222, "create-directory", 1, obj);
                }
            case Sequence.FLOAT_VALUE /*25*/:
                try {
                    return directoryFiles(FilePath.makeFilePath(obj));
                } catch (ClassCastException e2222222222222222222) {
                    throw new WrongType(e2222222222222222222, "directory-files", 1, obj);
                }
            case Sequence.DOUBLE_VALUE /*26*/:
                return $To$Pathname(obj);
            case XDataType.DAY_TIME_DURATION_TYPE_CODE /*30*/:
                try {
                    return makeTemporaryFile((CharSequence) obj);
                } catch (ClassCastException e22222222222222222222) {
                    throw new WrongType(e22222222222222222222, "make-temporary-file", 1, obj);
                }
            default:
                return super.apply1(moduleMethod, obj);
        }
    }
}
