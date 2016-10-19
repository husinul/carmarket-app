package gnu.expr;

import gnu.lists.Consumer;
import gnu.lists.Sequence;
import gnu.text.Printable;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectStreamException;

public class Special implements Printable, Externalizable {
    public static final Special abstractSpecial;
    public static final Special dfault;
    public static final Object eof;
    public static final Special key;
    public static final Special optional;
    public static final Special rest;
    public static final Special undefined;
    private String name;

    static {
        undefined = new Special("undefined");
        optional = new Special("optional");
        rest = new Special("rest");
        key = new Special("key");
        dfault = new Special("default");
        abstractSpecial = new Special("abstract");
        eof = Sequence.eofValue;
    }

    private Special(String n) {
        this.name = new String(n);
    }

    public static Special make(String name) {
        if (name == "optional") {
            return optional;
        }
        if (name == "rest") {
            return rest;
        }
        if (name == "key") {
            return key;
        }
        if (name == "default") {
            return dfault;
        }
        return new Special(name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public final String toString() {
        return "#!" + this.name;
    }

    public void print(Consumer out) {
        out.write("#!");
        out.write(this.name);
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(this.name);
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.name = in.readUTF();
    }

    public Object readResolve() throws ObjectStreamException {
        return make(this.name);
    }
}
