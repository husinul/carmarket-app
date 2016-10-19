package gnu.bytecode;

import com.google.appinventor.components.common.PropertyTypeConstants;
import gnu.expr.SetExp;
import gnu.kawa.functions.ArithOp;
import gnu.kawa.functions.ParseFormat;
import gnu.kawa.xml.XDataType;
import gnu.lists.Sequence;
import gnu.text.PrettyWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import kawa.Telnet;

public class StackMapTableAttr extends MiscAttr {
    public static boolean compressStackMapTable;
    int countLocals;
    int countStack;
    int[] encodedLocals;
    int[] encodedStack;
    int numEntries;
    int prevPosition;

    static {
        compressStackMapTable = true;
    }

    public StackMapTableAttr() {
        super("StackMapTable", null, 0, 0);
        this.prevPosition = -1;
        put2(0);
    }

    public StackMapTableAttr(byte[] data, CodeAttr code) {
        super("StackMapTable", data, 0, data.length);
        this.prevPosition = -1;
        addToFrontOf(code);
        this.numEntries = u2(0);
    }

    public Method getMethod() {
        return ((CodeAttr) this.container).getMethod();
    }

    public void write(DataOutputStream dstr) throws IOException {
        put2(0, this.numEntries);
        super.write(dstr);
    }

    void emitVerificationType(int encoding) {
        int tag = encoding & 255;
        put1(tag);
        if (tag >= 7) {
            put2(encoding >> 8);
        }
    }

    int encodeVerificationType(Type type, CodeAttr code) {
        if (type == null) {
            return 0;
        }
        if (type instanceof UninitializedType) {
            Label label = ((UninitializedType) type).label;
            if (label == null) {
                return 6;
            }
            return (label.position << 8) | 8;
        }
        type = type.getImplementationType();
        if (type instanceof PrimType) {
            switch (type.signature.charAt(0)) {
                case 'B':
                case 'C':
                case 'I':
                case PrettyWriter.NEWLINE_SPACE /*83*/:
                case 'Z':
                    return 1;
                case 'D':
                    return 3;
                case PrettyWriter.NEWLINE_FILL /*70*/:
                    return 2;
                case 'J':
                    return 4;
                default:
                    return 0;
            }
        } else if (type == Type.nullType) {
            return 5;
        } else {
            return (code.getConstants().addClass((ObjectType) type).index << 8) | 7;
        }
    }

    public void emitStackMapEntry(Label label, CodeAttr code) {
        int curLocalsCount;
        int offset_delta = (label.position - this.prevPosition) - 1;
        int rawLocalsCount = label.localTypes.length;
        int length = this.encodedLocals.length;
        if (rawLocalsCount > r0) {
            Object tmp = new int[(this.encodedLocals.length + rawLocalsCount)];
            System.arraycopy(this.encodedLocals, 0, tmp, 0, this.countLocals);
            this.encodedLocals = tmp;
        }
        int rawStackCount = label.stackTypes.length;
        if (rawStackCount > this.encodedStack.length) {
            tmp = new int[(this.encodedStack.length + rawStackCount)];
            System.arraycopy(this.encodedStack, 0, tmp, 0, this.countStack);
            this.encodedStack = tmp;
        }
        int unchangedLocals = 0;
        int i = 0;
        int curLocalsCount2 = 0;
        while (i < rawLocalsCount) {
            int prevType = this.encodedLocals[curLocalsCount2];
            int nextType = encodeVerificationType(label.localTypes[i], code);
            if (prevType == nextType && unchangedLocals == curLocalsCount2) {
                unchangedLocals = curLocalsCount2 + 1;
            }
            curLocalsCount = curLocalsCount2 + 1;
            this.encodedLocals[curLocalsCount2] = nextType;
            if (nextType == 3 || nextType == 4) {
                i++;
            }
            i++;
            curLocalsCount2 = curLocalsCount;
        }
        curLocalsCount = curLocalsCount2;
        while (curLocalsCount > 0) {
            if (this.encodedLocals[curLocalsCount - 1] != 0) {
                break;
            }
            curLocalsCount--;
        }
        i = 0;
        int curStackCount = 0;
        while (i < rawStackCount) {
            prevType = this.encodedStack[curStackCount];
            Type t = label.stackTypes[i];
            if (t == Type.voidType) {
                i++;
                t = label.stackTypes[i];
            }
            nextType = encodeVerificationType(t, code);
            int curStackCount2 = curStackCount + 1;
            this.encodedStack[curStackCount] = nextType;
            i++;
            curStackCount = curStackCount2;
        }
        int localsDelta = curLocalsCount - this.countLocals;
        if (!compressStackMapTable || localsDelta != 0 || curLocalsCount != unchangedLocals || curStackCount > 1) {
            if (compressStackMapTable && curStackCount == 0) {
                length = this.countLocals;
                if (curLocalsCount < r0 && unchangedLocals == curLocalsCount && localsDelta >= -3) {
                    put1(localsDelta + Telnet.WILL);
                    put2(offset_delta);
                }
            }
            if (compressStackMapTable && curStackCount == 0) {
                length = this.countLocals;
                if (r0 == unchangedLocals && localsDelta <= 3) {
                    put1(localsDelta + Telnet.WILL);
                    put2(offset_delta);
                    for (i = 0; i < localsDelta; i++) {
                        emitVerificationType(this.encodedLocals[unchangedLocals + i]);
                    }
                }
            }
            put1(255);
            put2(offset_delta);
            put2(curLocalsCount);
            for (i = 0; i < curLocalsCount; i++) {
                emitVerificationType(this.encodedLocals[i]);
            }
            put2(curStackCount);
            for (i = 0; i < curStackCount; i++) {
                emitVerificationType(this.encodedStack[i]);
            }
        } else if (curStackCount != 0) {
            if (offset_delta <= 63) {
                put1(offset_delta + 64);
            } else {
                put1(247);
                put2(offset_delta);
            }
            emitVerificationType(this.encodedStack[0]);
        } else if (offset_delta <= 63) {
            put1(offset_delta);
        } else {
            put1(Telnet.WILL);
            put2(offset_delta);
        }
        this.countLocals = curLocalsCount;
        this.countStack = curStackCount;
        this.prevPosition = label.position;
        this.numEntries++;
    }

    void printVerificationType(int encoding, ClassTypeWriter dst) {
        int tag = encoding & 255;
        switch (tag) {
            case Sequence.EOF_VALUE /*0*/:
                dst.print("top/unavailable");
            case ParseFormat.SEEN_MINUS /*1*/:
                dst.print(PropertyTypeConstants.PROPERTY_TYPE_INTEGER);
            case SetExp.DEFINING_FLAG /*2*/:
                dst.print(PropertyTypeConstants.PROPERTY_TYPE_FLOAT);
            case XDataType.ANY_ATOMIC_TYPE_CODE /*3*/:
                dst.print("double");
            case SetExp.GLOBAL_FLAG /*4*/:
                dst.print("long");
            case ArithOp.DIVIDE_INEXACT /*5*/:
                dst.print("null");
            case ArithOp.QUOTIENT /*6*/:
                dst.print("uninitialized this");
            case ArithOp.QUOTIENT_EXACT /*7*/:
                int index = encoding >> 8;
                dst.printOptionalIndex(index);
                dst.printConstantTersely(index, 7);
            case SetExp.PREFER_BINDING2 /*8*/:
                int offset = encoding >> 8;
                dst.print("uninitialized object created at ");
                dst.print(offset);
            default:
                dst.print("<bad verification type tag " + tag + '>');
        }
    }

    int extractVerificationType(int startOffset, int tag) {
        if (tag == 7 || tag == 8) {
            return tag | (u2(startOffset + 1) << 8);
        }
        return tag;
    }

    static int[] reallocBuffer(int[] buffer, int needed) {
        if (buffer == null) {
            return new int[(needed + 10)];
        }
        if (needed <= buffer.length) {
            return buffer;
        }
        int[] tmp = new int[(needed + 10)];
        System.arraycopy(buffer, 0, tmp, 0, buffer.length);
        return tmp;
    }

    int extractVerificationTypes(int startOffset, int count, int startIndex, int[] buffer) {
        int offset = startOffset;
        int startIndex2 = startIndex;
        while (true) {
            count--;
            if (count < 0) {
                return offset;
            }
            int encoding;
            if (offset >= this.dataLength) {
                encoding = -1;
            } else {
                int tag = this.data[offset];
                encoding = extractVerificationType(offset, tag);
                int i = (tag == 7 || tag == 8) ? 3 : 1;
                offset += i;
            }
            startIndex = startIndex2 + 1;
            buffer[startIndex2] = encoding;
            startIndex2 = startIndex;
        }
    }

    void printVerificationTypes(int[] encodings, int startIndex, int count, ClassTypeWriter dst) {
        int regno = 0;
        for (int i = 0; i < startIndex + count; i++) {
            int encoding = encodings[i];
            int tag = encoding & 255;
            if (i >= startIndex) {
                dst.print("  ");
                if (regno < 100) {
                    if (regno < 10) {
                        dst.print(' ');
                    }
                    dst.print(' ');
                }
                dst.print(regno);
                dst.print(": ");
                printVerificationType(encoding, dst);
                dst.println();
            }
            regno++;
            if (tag == 3 || tag == 4) {
                regno++;
            }
        }
    }

    public void print(ClassTypeWriter dst) {
        dst.print("Attribute \"");
        dst.print(getName());
        dst.print("\", length:");
        dst.print(getLength());
        dst.print(", number of entries: ");
        dst.println(this.numEntries);
        int ipos = 2;
        int pc_offset = -1;
        Method method = getMethod();
        int[] encodedTypes = null;
        int curLocals = (method.getStaticFlag() ? 0 : 1) + method.arg_types.length;
        int i = 0;
        while (true) {
            int i2 = this.numEntries;
            if (i < r0) {
                i2 = this.dataLength;
                if (ipos < r0) {
                    int ipos2 = ipos + 1;
                    int tag = u1(ipos);
                    pc_offset++;
                    if (tag <= 127) {
                        pc_offset += tag & 63;
                        ipos = ipos2;
                    } else {
                        if (ipos2 + 1 < this.dataLength) {
                            pc_offset += u2(ipos2);
                            ipos = ipos2 + 2;
                        } else {
                            return;
                        }
                    }
                    dst.print("  offset: ");
                    dst.print(pc_offset);
                    if (tag <= 63) {
                        dst.println(" - same_frame");
                    } else if (tag <= 127 || tag == 247) {
                        dst.println(tag <= 127 ? " - same_locals_1_stack_item_frame" : " - same_locals_1_stack_item_frame_extended");
                        encodedTypes = reallocBuffer(encodedTypes, 1);
                        ipos = extractVerificationTypes(ipos, 1, 0, encodedTypes);
                        printVerificationTypes(encodedTypes, 0, 1, dst);
                    } else if (tag <= 246) {
                        dst.print(" - tag reserved for future use - ");
                        dst.println(tag);
                        return;
                    } else if (tag <= 250) {
                        count = 251 - tag;
                        dst.print(" - chop_frame - undefine ");
                        dst.print(count);
                        dst.println(" locals");
                        curLocals -= count;
                    } else if (tag == 251) {
                        dst.println(" - same_frame_extended");
                    } else if (tag <= 254) {
                        count = tag - 251;
                        dst.print(" - append_frame - define ");
                        dst.print(count);
                        dst.println(" more locals");
                        encodedTypes = reallocBuffer(encodedTypes, curLocals + count);
                        ipos = extractVerificationTypes(ipos, count, curLocals, encodedTypes);
                        printVerificationTypes(encodedTypes, curLocals, count, dst);
                        curLocals += count;
                    } else {
                        if (ipos + 1 < this.dataLength) {
                            int num_locals = u2(ipos);
                            ipos += 2;
                            dst.print(" - full_frame.  Locals count: ");
                            dst.println(num_locals);
                            encodedTypes = reallocBuffer(encodedTypes, num_locals);
                            ipos = extractVerificationTypes(ipos, num_locals, 0, encodedTypes);
                            printVerificationTypes(encodedTypes, 0, num_locals, dst);
                            curLocals = num_locals;
                            if (ipos + 1 < this.dataLength) {
                                int num_stack = u2(ipos);
                                ipos += 2;
                                dst.print("    (end of locals)");
                                int nspaces = Integer.toString(pc_offset).length();
                                while (true) {
                                    nspaces--;
                                    if (nspaces < 0) {
                                        break;
                                    }
                                    dst.print(' ');
                                }
                                dst.print("       Stack count: ");
                                dst.println(num_stack);
                                encodedTypes = reallocBuffer(encodedTypes, num_stack);
                                ipos = extractVerificationTypes(ipos, num_stack, 0, encodedTypes);
                                printVerificationTypes(encodedTypes, 0, num_stack, dst);
                                int curStack = num_stack;
                            } else {
                                return;
                            }
                        }
                        return;
                    }
                    if (ipos < 0) {
                        dst.println("<ERROR - missing data>");
                        return;
                    }
                    i++;
                } else {
                    return;
                }
            }
            return;
        }
    }
}
