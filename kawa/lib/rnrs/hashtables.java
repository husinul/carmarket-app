package kawa.lib.rnrs;

import gnu.expr.LambdaExp;
import gnu.expr.ModuleBody;
import gnu.expr.ModuleInfo;
import gnu.expr.ModuleMethod;
import gnu.expr.SetExp;
import gnu.kawa.functions.ArithOp;
import gnu.kawa.functions.ParseFormat;
import gnu.kawa.util.AbstractHashTable;
import gnu.kawa.util.HashNode;
import gnu.kawa.xml.XDataType;
import gnu.lists.Consumer;
import gnu.lists.FVector;
import gnu.lists.Pair;
import gnu.lists.Sequence;
import gnu.mapping.CallContext;
import gnu.mapping.Procedure;
import gnu.mapping.SimpleSymbol;
import gnu.mapping.Symbol;
import gnu.mapping.Values;
import gnu.mapping.WrongType;
import kawa.lib.kawa.hashtable;
import kawa.lib.kawa.hashtable.HashTable;
import kawa.lib.lists;
import kawa.lib.misc;
import kawa.standard.Scheme;

/* compiled from: hashtables.scm */
public class hashtables extends ModuleBody {
    public static final hashtables $instance;
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
    static final SimpleSymbol Lit3;
    static final SimpleSymbol Lit4;
    static final SimpleSymbol Lit5;
    static final SimpleSymbol Lit6;
    static final SimpleSymbol Lit7;
    static final SimpleSymbol Lit8;
    static final SimpleSymbol Lit9;
    public static final ModuleMethod equal$Mnhash;
    static final ModuleMethod hash$Mnby$Mnidentity;
    static final ModuleMethod hash$Mnfor$Mneqv;
    public static final ModuleMethod hashtable$Mnclear$Ex;
    public static final ModuleMethod hashtable$Mncontains$Qu;
    public static final ModuleMethod hashtable$Mncopy;
    public static final ModuleMethod hashtable$Mndelete$Ex;
    public static final ModuleMethod hashtable$Mnentries;
    public static final ModuleMethod hashtable$Mnequivalence$Mnfunction;
    public static final ModuleMethod hashtable$Mnhash$Mnfunction;
    public static final ModuleMethod hashtable$Mnkeys;
    public static final ModuleMethod hashtable$Mnmutable$Qu;
    public static final ModuleMethod hashtable$Mnref;
    public static final ModuleMethod hashtable$Mnset$Ex;
    public static final ModuleMethod hashtable$Mnsize;
    public static final ModuleMethod hashtable$Mnupdate$Ex;
    public static final ModuleMethod hashtable$Qu;
    public static final ModuleMethod make$Mneq$Mnhashtable;
    public static final ModuleMethod make$Mneqv$Mnhashtable;
    public static final ModuleMethod make$Mnhashtable;
    public static final ModuleMethod string$Mnci$Mnhash;
    public static final ModuleMethod string$Mnhash;
    public static final ModuleMethod symbol$Mnhash;

    static {
        Lit22 = (SimpleSymbol) new SimpleSymbol("symbol-hash").readResolve();
        Lit21 = (SimpleSymbol) new SimpleSymbol("string-ci-hash").readResolve();
        Lit20 = (SimpleSymbol) new SimpleSymbol("string-hash").readResolve();
        Lit19 = (SimpleSymbol) new SimpleSymbol("equal-hash").readResolve();
        Lit18 = (SimpleSymbol) new SimpleSymbol("hashtable-mutable?").readResolve();
        Lit17 = (SimpleSymbol) new SimpleSymbol("hashtable-hash-function").readResolve();
        Lit16 = (SimpleSymbol) new SimpleSymbol("hashtable-equivalence-function").readResolve();
        Lit15 = (SimpleSymbol) new SimpleSymbol("hashtable-entries").readResolve();
        Lit14 = (SimpleSymbol) new SimpleSymbol("hashtable-keys").readResolve();
        Lit13 = (SimpleSymbol) new SimpleSymbol("hashtable-clear!").readResolve();
        Lit12 = (SimpleSymbol) new SimpleSymbol("hashtable-copy").readResolve();
        Lit11 = (SimpleSymbol) new SimpleSymbol("hashtable-update!").readResolve();
        Lit10 = (SimpleSymbol) new SimpleSymbol("hashtable-contains?").readResolve();
        Lit9 = (SimpleSymbol) new SimpleSymbol("hashtable-delete!").readResolve();
        Lit8 = (SimpleSymbol) new SimpleSymbol("hashtable-set!").readResolve();
        Lit7 = (SimpleSymbol) new SimpleSymbol("hashtable-ref").readResolve();
        Lit6 = (SimpleSymbol) new SimpleSymbol("hashtable-size").readResolve();
        Lit5 = (SimpleSymbol) new SimpleSymbol("hashtable?").readResolve();
        Lit4 = (SimpleSymbol) new SimpleSymbol("make-hashtable").readResolve();
        Lit3 = (SimpleSymbol) new SimpleSymbol("make-eqv-hashtable").readResolve();
        Lit2 = (SimpleSymbol) new SimpleSymbol("make-eq-hashtable").readResolve();
        Lit1 = (SimpleSymbol) new SimpleSymbol("hash-for-eqv").readResolve();
        Lit0 = (SimpleSymbol) new SimpleSymbol("hash-by-identity").readResolve();
        $instance = new hashtables();
        ModuleBody moduleBody = $instance;
        hash$Mnby$Mnidentity = new ModuleMethod(moduleBody, 1, Lit0, 4097);
        hash$Mnfor$Mneqv = new ModuleMethod(moduleBody, 2, Lit1, 4097);
        make$Mneq$Mnhashtable = new ModuleMethod(moduleBody, 3, Lit2, LambdaExp.ATTEMPT_INLINE);
        make$Mneqv$Mnhashtable = new ModuleMethod(moduleBody, 5, Lit3, LambdaExp.ATTEMPT_INLINE);
        make$Mnhashtable = new ModuleMethod(moduleBody, 7, Lit4, 12290);
        hashtable$Qu = new ModuleMethod(moduleBody, 9, Lit5, 4097);
        hashtable$Mnsize = new ModuleMethod(moduleBody, 10, Lit6, 4097);
        hashtable$Mnref = new ModuleMethod(moduleBody, 11, Lit7, 12291);
        hashtable$Mnset$Ex = new ModuleMethod(moduleBody, 12, Lit8, 12291);
        hashtable$Mndelete$Ex = new ModuleMethod(moduleBody, 13, Lit9, 8194);
        hashtable$Mncontains$Qu = new ModuleMethod(moduleBody, 14, Lit10, 8194);
        hashtable$Mnupdate$Ex = new ModuleMethod(moduleBody, 15, Lit11, 16388);
        hashtable$Mncopy = new ModuleMethod(moduleBody, 16, Lit12, 8193);
        hashtable$Mnclear$Ex = new ModuleMethod(moduleBody, 18, Lit13, 8193);
        hashtable$Mnkeys = new ModuleMethod(moduleBody, 20, Lit14, 4097);
        hashtable$Mnentries = new ModuleMethod(moduleBody, 21, Lit15, 4097);
        hashtable$Mnequivalence$Mnfunction = new ModuleMethod(moduleBody, 22, Lit16, 4097);
        hashtable$Mnhash$Mnfunction = new ModuleMethod(moduleBody, 23, Lit17, 4097);
        hashtable$Mnmutable$Qu = new ModuleMethod(moduleBody, 24, Lit18, 4097);
        equal$Mnhash = new ModuleMethod(moduleBody, 25, Lit19, 4097);
        string$Mnhash = new ModuleMethod(moduleBody, 26, Lit20, 4097);
        string$Mnci$Mnhash = new ModuleMethod(moduleBody, 27, Lit21, 4097);
        symbol$Mnhash = new ModuleMethod(moduleBody, 28, Lit22, 4097);
        $instance.run();
    }

    public hashtables() {
        ModuleInfo.register(this);
    }

    public static void hashtableClear$Ex(HashTable hashTable) {
        hashtableClear$Ex(hashTable, 64);
    }

    public static HashTable hashtableCopy(HashTable hashTable) {
        return hashtableCopy(hashTable, false);
    }

    public static HashTable makeEqHashtable() {
        return makeEqHashtable(AbstractHashTable.DEFAULT_INITIAL_SIZE);
    }

    public static HashTable makeEqvHashtable() {
        return makeEqvHashtable(AbstractHashTable.DEFAULT_INITIAL_SIZE);
    }

    public static HashTable makeHashtable(Procedure procedure, Procedure procedure2) {
        return makeHashtable(procedure, procedure2, AbstractHashTable.DEFAULT_INITIAL_SIZE);
    }

    public final void run(CallContext $ctx) {
        Consumer $result = $ctx.consumer;
    }

    static int hashByIdentity(Object obj) {
        return System.identityHashCode(obj);
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
            case ArithOp.DIVIDE_INEXACT /*5*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case ArithOp.ASHIFT_GENERAL /*9*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case ArithOp.ASHIFT_LEFT /*10*/:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case SetExp.PROCEDURE /*16*/:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.INT_S8_VALUE /*18*/:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.INT_S16_VALUE /*20*/:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.INT_U32_VALUE /*21*/:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.INT_S32_VALUE /*22*/:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.INT_U64_VALUE /*23*/:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.INT_S64_VALUE /*24*/:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.FLOAT_VALUE /*25*/:
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.DOUBLE_VALUE /*26*/:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.BOOLEAN_VALUE /*27*/:
                if (!(obj instanceof CharSequence)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.proc = moduleMethod;
                callContext.pc = 1;
                return 0;
            case Sequence.TEXT_BYTE_VALUE /*28*/:
                if (!(obj instanceof Symbol)) {
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

    static int hashForEqv(Object obj) {
        return obj.hashCode();
    }

    public static HashTable makeEqHashtable(int k) {
        return new HashTable(Scheme.isEq, hash$Mnby$Mnidentity, AbstractHashTable.DEFAULT_INITIAL_SIZE);
    }

    public int match0(ModuleMethod moduleMethod, CallContext callContext) {
        switch (moduleMethod.selector) {
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            case ArithOp.DIVIDE_INEXACT /*5*/:
                callContext.proc = moduleMethod;
                callContext.pc = 0;
                return 0;
            default:
                return super.match0(moduleMethod, callContext);
        }
    }

    public static HashTable makeEqvHashtable(int k) {
        return new HashTable(Scheme.isEqv, hash$Mnfor$Mneqv, AbstractHashTable.DEFAULT_INITIAL_SIZE);
    }

    public Object apply0(ModuleMethod moduleMethod) {
        switch (moduleMethod.selector) {
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                return makeEqHashtable();
            case ArithOp.DIVIDE_INEXACT /*5*/:
                return makeEqvHashtable();
            default:
                return super.apply0(moduleMethod);
        }
    }

    public static HashTable makeHashtable(Procedure comparison, Procedure hash, int size) {
        return new HashTable(comparison, hash, size);
    }

    public int match2(ModuleMethod moduleMethod, Object obj, Object obj2, CallContext callContext) {
        switch (moduleMethod.selector) {
            case ArithOp.QUOTIENT_EXACT /*7*/:
                if (!(obj instanceof Procedure)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Procedure)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case ArithOp.AND /*13*/:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case ArithOp.IOR /*14*/:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case SetExp.PROCEDURE /*16*/:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            case Sequence.INT_S8_VALUE /*18*/:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.proc = moduleMethod;
                callContext.pc = 2;
                return 0;
            default:
                return super.match2(moduleMethod, obj, obj2, callContext);
        }
    }

    public int match3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, CallContext callContext) {
        switch (moduleMethod.selector) {
            case ArithOp.QUOTIENT_EXACT /*7*/:
                if (!(obj instanceof Procedure)) {
                    return -786431;
                }
                callContext.value1 = obj;
                if (!(obj2 instanceof Procedure)) {
                    return -786430;
                }
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case ArithOp.ASHIFT_RIGHT /*11*/:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            case ArithOp.LSHIFT_RIGHT /*12*/:
                if (!(obj instanceof HashTable)) {
                    return -786431;
                }
                callContext.value1 = obj;
                callContext.value2 = obj2;
                callContext.value3 = obj3;
                callContext.proc = moduleMethod;
                callContext.pc = 3;
                return 0;
            default:
                return super.match3(moduleMethod, obj, obj2, obj3, callContext);
        }
    }

    public static boolean isHashtable(Object obj) {
        return obj instanceof HashTable;
    }

    public static int hashtableSize(HashTable ht) {
        return ht.size();
    }

    public static Object hashtableRef(HashTable ht, Object key, Object default_) {
        HashNode node = ht.getNode(key);
        return node == null ? default_ : node.getValue();
    }

    public static void hashtableSet$Ex(HashTable ht, Object key, Object value) {
        hashtable.hashtableCheckMutable(ht);
        ht.put(key, value);
    }

    public Object apply3(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3) {
        switch (moduleMethod.selector) {
            case ArithOp.QUOTIENT_EXACT /*7*/:
                try {
                    try {
                        try {
                            return makeHashtable((Procedure) obj, (Procedure) obj2, ((Number) obj3).intValue());
                        } catch (ClassCastException e) {
                            throw new WrongType(e, "make-hashtable", 3, obj3);
                        }
                    } catch (ClassCastException e2) {
                        throw new WrongType(e2, "make-hashtable", 2, obj2);
                    }
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "make-hashtable", 1, obj);
                }
            case ArithOp.ASHIFT_RIGHT /*11*/:
                try {
                    return hashtableRef((HashTable) obj, obj2, obj3);
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "hashtable-ref", 1, obj);
                }
            case ArithOp.LSHIFT_RIGHT /*12*/:
                try {
                    hashtableSet$Ex((HashTable) obj, obj2, obj3);
                    return Values.empty;
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "hashtable-set!", 1, obj);
                }
            default:
                return super.apply3(moduleMethod, obj, obj2, obj3);
        }
    }

    public static void hashtableDelete$Ex(HashTable ht, Object key) {
        hashtable.hashtableCheckMutable(ht);
        ht.remove(key);
    }

    public static boolean isHashtableContains(HashTable ht, Object key) {
        return ((ht.getNode(key) == null ? 1 : 0) + 1) & 1;
    }

    public static Object hashtableUpdate$Ex(HashTable ht, Object key, Procedure proc, Object default_) {
        hashtable.hashtableCheckMutable(ht);
        HashNode node = ht.getNode(key);
        if (node != null) {
            return node.setValue(proc.apply1(node.getValue()));
        }
        hashtableSet$Ex(ht, key, proc.apply1(default_));
        return Values.empty;
    }

    public Object apply4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4) {
        if (moduleMethod.selector != 15) {
            return super.apply4(moduleMethod, obj, obj2, obj3, obj4);
        }
        try {
            try {
                return hashtableUpdate$Ex((HashTable) obj, obj2, (Procedure) obj3, obj4);
            } catch (ClassCastException e) {
                throw new WrongType(e, "hashtable-update!", 3, obj3);
            }
        } catch (ClassCastException e2) {
            throw new WrongType(e2, "hashtable-update!", 1, obj);
        }
    }

    public int match4(ModuleMethod moduleMethod, Object obj, Object obj2, Object obj3, Object obj4, CallContext callContext) {
        if (moduleMethod.selector != 15) {
            return super.match4(moduleMethod, obj, obj2, obj3, obj4, callContext);
        }
        if (!(obj instanceof HashTable)) {
            return -786431;
        }
        callContext.value1 = obj;
        callContext.value2 = obj2;
        if (!(obj3 instanceof Procedure)) {
            return -786429;
        }
        callContext.value3 = obj3;
        callContext.value4 = obj4;
        callContext.proc = moduleMethod;
        callContext.pc = 4;
        return 0;
    }

    public static HashTable hashtableCopy(HashTable ht, boolean mutable) {
        return new HashTable(ht, mutable);
    }

    public static void hashtableClear$Ex(HashTable ht, int k) {
        hashtable.hashtableCheckMutable(ht);
        ht.clear();
    }

    public Object apply2(ModuleMethod moduleMethod, Object obj, Object obj2) {
        boolean z = true;
        switch (moduleMethod.selector) {
            case ArithOp.QUOTIENT_EXACT /*7*/:
                try {
                    try {
                        return makeHashtable((Procedure) obj, (Procedure) obj2);
                    } catch (ClassCastException e) {
                        throw new WrongType(e, "make-hashtable", 2, obj2);
                    }
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "make-hashtable", 1, obj);
                }
            case ArithOp.AND /*13*/:
                try {
                    hashtableDelete$Ex((HashTable) obj, obj2);
                    return Values.empty;
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "hashtable-delete!", 1, obj);
                }
            case ArithOp.IOR /*14*/:
                try {
                    return isHashtableContains((HashTable) obj, obj2) ? Boolean.TRUE : Boolean.FALSE;
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "hashtable-contains?", 1, obj);
                }
            case SetExp.PROCEDURE /*16*/:
                try {
                    HashTable hashTable = (HashTable) obj;
                    try {
                        if (obj2 == Boolean.FALSE) {
                            z = false;
                        }
                        return hashtableCopy(hashTable, z);
                    } catch (ClassCastException e3) {
                        throw new WrongType(e3, "hashtable-copy", 2, obj2);
                    }
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "hashtable-copy", 1, obj);
                }
            case Sequence.INT_S8_VALUE /*18*/:
                try {
                    try {
                        hashtableClear$Ex((HashTable) obj, ((Number) obj2).intValue());
                        return Values.empty;
                    } catch (ClassCastException e32) {
                        throw new WrongType(e32, "hashtable-clear!", 2, obj2);
                    }
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "hashtable-clear!", 1, obj);
                }
            default:
                return super.apply2(moduleMethod, obj, obj2);
        }
    }

    public static FVector hashtableKeys(HashTable ht) {
        return ht.keysVector();
    }

    public static Object hashtableEntries(HashTable ht) {
        Pair pair = ht.entriesVectorPair();
        return misc.values(lists.car.apply1(pair), lists.cdr.apply1(pair));
    }

    public static Procedure hashtableEquivalenceFunction(HashTable ht) {
        return (Procedure) ht.equivalenceFunction.apply1(ht);
    }

    public static Object hashtableHashFunction(HashTable ht) {
        Object hasher = ht.hashFunction.apply1(ht);
        Boolean x = Scheme.isEqv.apply2(hasher, hash$Mnby$Mnidentity);
        if (x != Boolean.FALSE) {
            if (x == Boolean.FALSE) {
                return hasher;
            }
        } else if (Scheme.isEqv.apply2(hasher, hash$Mnfor$Mneqv) == Boolean.FALSE) {
            return hasher;
        }
        return Boolean.FALSE;
    }

    public static Object isHashtableMutable(HashTable ht) {
        return Scheme.applyToArgs.apply1(ht.mutable ? Boolean.TRUE : Boolean.FALSE);
    }

    public static int equalHash(Object key) {
        return key.hashCode();
    }

    public static int stringHash(CharSequence s) {
        return s.hashCode();
    }

    public static int stringCiHash(CharSequence s) {
        return s.toString().toLowerCase().hashCode();
    }

    public static int symbolHash(Symbol s) {
        return s.hashCode();
    }

    public Object apply1(ModuleMethod moduleMethod, Object obj) {
        switch (moduleMethod.selector) {
            case ParseFormat.SEEN_MINUS /*1*/:
                return Integer.valueOf(hashByIdentity(obj));
            case SetExp.DEFINING_FLAG /*2*/:
                return Integer.valueOf(hashForEqv(obj));
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                try {
                    return makeEqHashtable(((Number) obj).intValue());
                } catch (ClassCastException e) {
                    throw new WrongType(e, "make-eq-hashtable", 1, obj);
                }
            case ArithOp.DIVIDE_INEXACT /*5*/:
                try {
                    return makeEqvHashtable(((Number) obj).intValue());
                } catch (ClassCastException e2) {
                    throw new WrongType(e2, "make-eqv-hashtable", 1, obj);
                }
            case ArithOp.ASHIFT_GENERAL /*9*/:
                return isHashtable(obj) ? Boolean.TRUE : Boolean.FALSE;
            case ArithOp.ASHIFT_LEFT /*10*/:
                try {
                    return Integer.valueOf(hashtableSize((HashTable) obj));
                } catch (ClassCastException e22) {
                    throw new WrongType(e22, "hashtable-size", 1, obj);
                }
            case SetExp.PROCEDURE /*16*/:
                try {
                    return hashtableCopy((HashTable) obj);
                } catch (ClassCastException e222) {
                    throw new WrongType(e222, "hashtable-copy", 1, obj);
                }
            case Sequence.INT_S8_VALUE /*18*/:
                try {
                    hashtableClear$Ex((HashTable) obj);
                    return Values.empty;
                } catch (ClassCastException e2222) {
                    throw new WrongType(e2222, "hashtable-clear!", 1, obj);
                }
            case Sequence.INT_S16_VALUE /*20*/:
                try {
                    return hashtableKeys((HashTable) obj);
                } catch (ClassCastException e22222) {
                    throw new WrongType(e22222, "hashtable-keys", 1, obj);
                }
            case Sequence.INT_U32_VALUE /*21*/:
                try {
                    return hashtableEntries((HashTable) obj);
                } catch (ClassCastException e222222) {
                    throw new WrongType(e222222, "hashtable-entries", 1, obj);
                }
            case Sequence.INT_S32_VALUE /*22*/:
                try {
                    return hashtableEquivalenceFunction((HashTable) obj);
                } catch (ClassCastException e2222222) {
                    throw new WrongType(e2222222, "hashtable-equivalence-function", 1, obj);
                }
            case Sequence.INT_U64_VALUE /*23*/:
                try {
                    return hashtableHashFunction((HashTable) obj);
                } catch (ClassCastException e22222222) {
                    throw new WrongType(e22222222, "hashtable-hash-function", 1, obj);
                }
            case Sequence.INT_S64_VALUE /*24*/:
                try {
                    return isHashtableMutable((HashTable) obj);
                } catch (ClassCastException e222222222) {
                    throw new WrongType(e222222222, "hashtable-mutable?", 1, obj);
                }
            case Sequence.FLOAT_VALUE /*25*/:
                return Integer.valueOf(equalHash(obj));
            case Sequence.DOUBLE_VALUE /*26*/:
                try {
                    return Integer.valueOf(stringHash((CharSequence) obj));
                } catch (ClassCastException e2222222222) {
                    throw new WrongType(e2222222222, "string-hash", 1, obj);
                }
            case Sequence.BOOLEAN_VALUE /*27*/:
                try {
                    return Integer.valueOf(stringCiHash((CharSequence) obj));
                } catch (ClassCastException e22222222222) {
                    throw new WrongType(e22222222222, "string-ci-hash", 1, obj);
                }
            case Sequence.TEXT_BYTE_VALUE /*28*/:
                try {
                    return Integer.valueOf(symbolHash((Symbol) obj));
                } catch (ClassCastException e222222222222) {
                    throw new WrongType(e222222222222, "symbol-hash", 1, obj);
                }
            default:
                return super.apply1(moduleMethod, obj);
        }
    }
}
