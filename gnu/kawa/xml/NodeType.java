package gnu.kawa.xml;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.bytecode.ClassType;
import gnu.bytecode.CodeAttr;
import gnu.bytecode.Method;
import gnu.bytecode.ObjectType;
import gnu.bytecode.Type;
import gnu.bytecode.Variable;
import gnu.expr.ApplyExp;
import gnu.expr.Compilation;
import gnu.expr.ConditionalTarget;
import gnu.expr.Declaration;
import gnu.expr.Expression;
import gnu.expr.Target;
import gnu.expr.TypeValue;
import gnu.kawa.reflect.InstanceOf;
import gnu.lists.AbstractSequence;
import gnu.lists.NodePredicate;
import gnu.lists.Sequence;
import gnu.mapping.Procedure;
import gnu.xml.NodeTree;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class NodeType extends ObjectType implements TypeValue, NodePredicate, Externalizable {
    public static final int ATTRIBUTE_OK = 4;
    public static final int COMMENT_OK = 16;
    public static final int DOCUMENT_OK = 8;
    public static final int ELEMENT_OK = 2;
    public static final int PI_OK = 32;
    public static final int TEXT_OK = 1;
    public static final NodeType anyNodeTest;
    static final Method coerceMethod;
    static final Method coerceOrNullMethod;
    public static final NodeType commentNodeTest;
    public static final NodeType documentNodeTest;
    public static final NodeType nodeType;
    public static final NodeType textNodeTest;
    public static final ClassType typeKNode;
    public static final ClassType typeNodeType;
    int kinds;

    public NodeType(String name, int kinds) {
        super(name);
        this.kinds = -1;
        this.kinds = kinds;
    }

    public NodeType(String name) {
        this(name, -1);
    }

    public void emitCoerceFromObject(CodeAttr code) {
        code.emitPushInt(this.kinds);
        code.emitInvokeStatic(coerceMethod);
    }

    public Expression convertValue(Expression value) {
        Method method = coerceMethod;
        Expression[] expressionArr = new Expression[TEXT_OK];
        expressionArr[0] = value;
        ApplyExp aexp = new ApplyExp(method, expressionArr);
        aexp.setType(this);
        return aexp;
    }

    public Object coerceFromObject(Object obj) {
        return coerceForce(obj, this.kinds);
    }

    public Type getImplementationType() {
        return typeKNode;
    }

    public int compare(Type other) {
        return getImplementationType().compare(other);
    }

    public boolean isInstance(Object obj) {
        if (!(obj instanceof KNode)) {
            return false;
        }
        KNode pos = (KNode) obj;
        return isInstancePos(pos.sequence, pos.getPos());
    }

    public boolean isInstancePos(AbstractSequence seq, int ipos) {
        return isInstance(seq, ipos, this.kinds);
    }

    public static boolean isInstance(AbstractSequence seq, int ipos, int kinds) {
        int kind = seq.getNextKind(ipos);
        if (kinds >= 0) {
            switch (kind) {
                case Sequence.EOF_VALUE /*0*/:
                    return false;
                case Sequence.INT_U8_VALUE /*17*/:
                case Sequence.INT_S8_VALUE /*18*/:
                case Sequence.INT_U16_VALUE /*19*/:
                case Sequence.INT_S16_VALUE /*20*/:
                case Sequence.INT_U32_VALUE /*21*/:
                case Sequence.INT_S32_VALUE /*22*/:
                case Sequence.INT_U64_VALUE /*23*/:
                case Sequence.INT_S64_VALUE /*24*/:
                case Sequence.FLOAT_VALUE /*25*/:
                case Sequence.DOUBLE_VALUE /*26*/:
                case Sequence.BOOLEAN_VALUE /*27*/:
                case Sequence.TEXT_BYTE_VALUE /*28*/:
                case Sequence.CHAR_VALUE /*29*/:
                case PI_OK /*32*/:
                    if ((kinds & TEXT_OK) == 0) {
                        return false;
                    }
                    return true;
                case Sequence.ELEMENT_VALUE /*33*/:
                    if ((kinds & ELEMENT_OK) == 0) {
                        return false;
                    }
                    return true;
                case Sequence.DOCUMENT_VALUE /*34*/:
                    if ((kinds & DOCUMENT_OK) == 0) {
                        return false;
                    }
                    return true;
                case Sequence.ATTRIBUTE_VALUE /*35*/:
                    if ((kinds & ATTRIBUTE_OK) == 0) {
                        return false;
                    }
                    return true;
                case Sequence.COMMENT_VALUE /*36*/:
                    if ((kinds & COMMENT_OK) == 0) {
                        return false;
                    }
                    return true;
                case Sequence.PROCESSING_INSTRUCTION_VALUE /*37*/:
                    if ((kinds & PI_OK) == 0) {
                        return false;
                    }
                    return true;
                default:
                    return true;
            }
        } else if (kind != 0) {
            return true;
        } else {
            return false;
        }
    }

    public static KNode coerceForce(Object obj, int kinds) {
        KNode pos = coerceOrNull(obj, kinds);
        if (pos != null) {
            return pos;
        }
        throw new ClassCastException("coerce from " + obj.getClass());
    }

    public static KNode coerceOrNull(Object obj, int kinds) {
        KNode pos;
        if (obj instanceof NodeTree) {
            pos = KNode.make((NodeTree) obj);
        } else if (!(obj instanceof KNode)) {
            return null;
        } else {
            pos = (KNode) obj;
        }
        if (!isInstance(pos.sequence, pos.ipos, kinds)) {
            pos = null;
        }
        return pos;
    }

    protected void emitCoerceOrNullMethod(Variable incoming, Compilation comp) {
        CodeAttr code = comp.getCode();
        if (incoming != null) {
            code.emitLoad(incoming);
        }
        code.emitPushInt(this.kinds);
        code.emitInvokeStatic(coerceOrNullMethod);
    }

    public void emitTestIf(Variable incoming, Declaration decl, Compilation comp) {
        CodeAttr code = comp.getCode();
        emitCoerceOrNullMethod(incoming, comp);
        if (decl != null) {
            code.emitDup();
            decl.compileStore(comp);
        }
        code.emitIfNotNull();
    }

    public void emitIsInstance(Variable incoming, Compilation comp, Target target) {
        if (target instanceof ConditionalTarget) {
            ConditionalTarget ctarget = (ConditionalTarget) target;
            emitCoerceOrNullMethod(incoming, comp);
            CodeAttr code = comp.getCode();
            if (ctarget.trueBranchComesFirst) {
                code.emitGotoIfCompare1(ctarget.ifFalse, 198);
            } else {
                code.emitGotoIfCompare1(ctarget.ifTrue, 199);
            }
            ctarget.emitGotoFirstBranch(code);
            return;
        }
        InstanceOf.emitIsInstance(this, incoming, comp, target);
    }

    static {
        typeKNode = ClassType.make("gnu.kawa.xml.KNode");
        typeNodeType = ClassType.make("gnu.kawa.xml.NodeType");
        nodeType = new NodeType("gnu.kawa.xml.KNode");
        coerceMethod = typeNodeType.getDeclaredMethod("coerceForce", (int) ELEMENT_OK);
        coerceOrNullMethod = typeNodeType.getDeclaredMethod("coerceOrNull", (int) ELEMENT_OK);
        documentNodeTest = new NodeType("document-node", DOCUMENT_OK);
        textNodeTest = new NodeType(PropertyTypeConstants.PROPERTY_TYPE_TEXT, TEXT_OK);
        commentNodeTest = new NodeType("comment", COMMENT_OK);
        anyNodeTest = new NodeType("node");
    }

    public Procedure getConstructor() {
        return null;
    }

    public String toString() {
        return "NodeType " + getName();
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        String name = getName();
        if (name == null) {
            name = ElementType.MATCH_ANY_LOCALNAME;
        }
        out.writeUTF(name);
        out.writeInt(this.kinds);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        String name = in.readUTF();
        if (name.length() > 0) {
            setName(name);
        }
        this.kinds = in.readInt();
    }
}
