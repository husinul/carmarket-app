package kawa;

import gnu.expr.LambdaExp;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Telnet implements Runnable {
    public static final int DO = 253;
    public static final int DONT = 254;
    public static final int ECHO = 1;
    static final int EOF = 236;
    static final int IAC = 255;
    static final int IP = 244;
    static final int LINEMODE = 34;
    static final int NAWS = 31;
    static final int NOP = 241;
    static final int OPTION_NO = 0;
    static final int OPTION_WANTNO = 1;
    static final int OPTION_WANTNO_OPPOSITE = 2;
    static final int OPTION_WANTYES = 3;
    static final int OPTION_WANTYES_OPPOSITE = 4;
    static final int OPTION_YES = 5;
    static final int SB = 250;
    static final int SE = 240;
    public static final int SUPPRESS_GO_AHEAD = 3;
    static final int TM = 6;
    static final int TTYPE = 24;
    public static final int WILL = 251;
    public static final int WONT = 252;
    TelnetInputStream in;
    boolean isServer;
    final byte[] optionsState;
    TelnetOutputStream out;
    final byte preferredLineMode;
    InputStream sin;
    OutputStream sout;
    public byte[] terminalType;
    public short windowHeight;
    public short windowWidth;

    public TelnetInputStream getInputStream() {
        return this.in;
    }

    public TelnetOutputStream getOutputStream() {
        return this.out;
    }

    boolean change(int command, int option) {
        if (option == TM) {
            return true;
        }
        if (this.isServer && option == NAWS) {
            return true;
        }
        byte[] buf;
        if (this.isServer && command == WILL && option == LINEMODE) {
            buf = new byte[OPTION_WANTNO_OPPOSITE];
            buf[OPTION_NO] = (byte) 1;
            buf[OPTION_WANTNO] = (byte) 3;
            try {
                this.out.writeSubCommand(LINEMODE, buf);
                return true;
            } catch (IOException e) {
                return true;
            }
        } else if (this.isServer && command == WILL && option == TTYPE) {
            buf = new byte[OPTION_WANTNO];
            buf[OPTION_NO] = (byte) 1;
            try {
                this.out.writeSubCommand(option, buf);
                return true;
            } catch (IOException e2) {
                return true;
            }
        } else {
            if (!this.isServer && option == OPTION_WANTNO) {
                if (command == DO) {
                    return false;
                }
                if (command == WILL) {
                    return true;
                }
            }
            return false;
        }
    }

    public void subCommand(byte[] buf, int off, int len) {
        switch (buf[off]) {
            case TTYPE /*24*/:
                byte[] type = new byte[(len - 1)];
                System.arraycopy(buf, OPTION_WANTNO, type, OPTION_NO, len - 1);
                this.terminalType = type;
                System.err.println("terminal type: '" + new String(type) + "'");
            case NAWS /*31*/:
                if (len == OPTION_YES) {
                    this.windowWidth = (short) ((buf[OPTION_WANTNO] << 8) + (buf[OPTION_WANTNO_OPPOSITE] & IAC));
                    this.windowHeight = (short) ((buf[SUPPRESS_GO_AHEAD] << 8) + (buf[OPTION_WANTYES_OPPOSITE] & IAC));
                }
            case LINEMODE /*34*/:
                System.err.println("SBCommand LINEMODE " + buf[OPTION_WANTNO] + " len:" + len);
                if (buf[OPTION_WANTNO] == (byte) 3) {
                    for (int i = OPTION_WANTNO_OPPOSITE; i + OPTION_WANTNO_OPPOSITE < len; i += SUPPRESS_GO_AHEAD) {
                        System.err.println("  " + buf[i] + "," + buf[i + OPTION_WANTNO] + "," + buf[i + OPTION_WANTNO_OPPOSITE]);
                    }
                }
            default:
        }
    }

    void handle(int command, int option) throws IOException {
        boolean otherSide;
        boolean wantOn = true;
        int i = DONT;
        int i2 = DO;
        if (command < DO) {
            otherSide = true;
        } else {
            otherSide = false;
        }
        if ((command & OPTION_WANTNO) == 0) {
            wantOn = false;
        }
        byte state = this.optionsState[option];
        if (otherSide) {
            state = (byte) (state >> SUPPRESS_GO_AHEAD);
        }
        TelnetOutputStream telnetOutputStream;
        switch ((state >> SUPPRESS_GO_AHEAD) & 7) {
            case OPTION_NO /*0*/:
                if (wantOn) {
                    if (!change(command, option)) {
                        telnetOutputStream = this.out;
                        if (!otherSide) {
                            i = WONT;
                        }
                        telnetOutputStream.writeCommand(i, option);
                        break;
                    }
                    state = (byte) 5;
                    this.out.writeCommand(otherSide ? DO : WILL, option);
                    break;
                }
                return;
            case OPTION_WANTNO /*1*/:
                state = (byte) 0;
                break;
            case OPTION_WANTNO_OPPOSITE /*2*/:
                state = (byte) 3;
                telnetOutputStream = this.out;
                if (!otherSide) {
                    i2 = WILL;
                }
                telnetOutputStream.writeCommand(i2, option);
                break;
            case SUPPRESS_GO_AHEAD /*3*/:
                if (!wantOn) {
                    state = (byte) 0;
                    break;
                }
                state = (byte) 5;
                change(command, option);
                break;
            case OPTION_WANTYES_OPPOSITE /*4*/:
                if (!wantOn) {
                    state = (byte) 0;
                    break;
                }
                state = (byte) 1;
                telnetOutputStream = this.out;
                if (!otherSide) {
                    i = WONT;
                }
                telnetOutputStream.writeCommand(i, option);
                break;
            case OPTION_YES /*5*/:
                if (!wantOn) {
                    state = (byte) 0;
                    change(command, option);
                    this.out.writeCommand(otherSide ? DONT : WONT, option);
                    break;
                }
                return;
        }
        if (otherSide) {
            state = (byte) ((this.optionsState[option] & 199) | (state << SUPPRESS_GO_AHEAD));
        } else {
            state = (byte) ((this.optionsState[option] & 248) | state);
        }
        this.optionsState[option] = state;
    }

    public void request(int command, int option) throws IOException {
        boolean otherSide;
        boolean wantOn = true;
        if (command >= DO) {
            otherSide = true;
        } else {
            otherSide = false;
        }
        if ((command & OPTION_WANTNO) == 0) {
            wantOn = false;
        }
        byte state = this.optionsState[option];
        if (otherSide) {
            state = (byte) (state >> SUPPRESS_GO_AHEAD);
        }
        switch (state & 7) {
            case OPTION_NO /*0*/:
                if (wantOn) {
                    state = (byte) 3;
                    this.out.writeCommand(command, option);
                    break;
                }
                break;
            case OPTION_WANTNO /*1*/:
                if (wantOn) {
                    state = (byte) 2;
                    break;
                }
                break;
            case OPTION_WANTNO_OPPOSITE /*2*/:
                if (!wantOn) {
                    state = (byte) 1;
                    break;
                }
                break;
            case SUPPRESS_GO_AHEAD /*3*/:
                if (!wantOn) {
                    state = (byte) 4;
                    break;
                }
                break;
            case OPTION_WANTYES_OPPOSITE /*4*/:
                break;
            case OPTION_YES /*5*/:
                if (!wantOn) {
                    state = (byte) 1;
                    this.out.writeCommand(command, option);
                    break;
                }
                break;
        }
        if (wantOn) {
            state = (byte) 3;
        }
        if (otherSide) {
            state = (byte) ((this.optionsState[option] & 199) | (state << SUPPRESS_GO_AHEAD));
        } else {
            state = (byte) ((this.optionsState[option] & 248) | state);
        }
        this.optionsState[option] = state;
    }

    static void usage() {
        System.err.println("Usage:  [java] kawa.Telnet HOST [PORT#]");
        System.exit(-1);
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            usage();
        }
        String host = args[OPTION_NO];
        int port = 23;
        if (args.length > OPTION_WANTNO) {
            port = Integer.parseInt(args[OPTION_WANTNO]);
        }
        try {
            Telnet telnet = new Telnet(new Socket(host, port), false);
            TelnetOutputStream tout = telnet.getOutputStream();
            Thread t = new Thread(telnet);
            t.setPriority(Thread.currentThread().getPriority() + OPTION_WANTNO);
            t.start();
            byte[] buffer = new byte[LambdaExp.SEQUENCE_RESULT];
            while (true) {
                int ch = System.in.read();
                if (ch < 0) {
                    t.stop();
                    return;
                }
                buffer[OPTION_NO] = (byte) ch;
                int avail = System.in.available();
                if (avail > 0) {
                    if (avail > buffer.length - 1) {
                        avail = buffer.length - 1;
                    }
                    avail = System.in.read(buffer, OPTION_WANTNO, avail);
                }
                tout.write(buffer, OPTION_NO, avail + OPTION_WANTNO);
            }
        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    public Telnet(Socket socket, boolean isServer) throws IOException {
        this.preferredLineMode = (byte) 3;
        this.optionsState = new byte[LambdaExp.NO_FIELD];
        this.sin = socket.getInputStream();
        this.sout = socket.getOutputStream();
        this.out = new TelnetOutputStream(this.sout);
        this.in = new TelnetInputStream(this.sin, this);
        this.isServer = isServer;
    }

    public void run() {
        try {
            TelnetInputStream tin = getInputStream();
            byte[] buffer = new byte[LambdaExp.SEQUENCE_RESULT];
            while (true) {
                int ch = tin.read();
                if (ch >= 0) {
                    buffer[OPTION_NO] = (byte) ch;
                    int avail = tin.available();
                    if (avail > 0) {
                        if (avail > buffer.length - 1) {
                            avail = buffer.length - 1;
                        }
                        avail = tin.read(buffer, OPTION_WANTNO, avail);
                    }
                    System.out.write(buffer, OPTION_NO, avail + OPTION_WANTNO);
                } else {
                    return;
                }
            }
        } catch (IOException ex) {
            System.err.println(ex);
            System.exit(-1);
        }
    }
}
