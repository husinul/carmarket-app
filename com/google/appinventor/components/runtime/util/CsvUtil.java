package com.google.appinventor.components.runtime.util;

import gnu.expr.SetExp;
import gnu.kawa.functions.ArithOp;
import gnu.kawa.xml.ElementType;
import gnu.kawa.xml.XDataType;
import gnu.lists.Sequence;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public final class CsvUtil {

    private static class CsvParser implements Iterator<List<String>> {
        private final Pattern ESCAPED_QUOTE_PATTERN;
        private final char[] buf;
        private int cellLength;
        private int delimitedCellLength;
        private final Reader in;
        private Exception lastException;
        private int limit;
        private boolean opened;
        private int pos;
        private long previouslyRead;

        public CsvParser(Reader in) {
            this.ESCAPED_QUOTE_PATTERN = Pattern.compile("\"\"");
            this.buf = new char[10240];
            this.opened = true;
            this.cellLength = -1;
            this.delimitedCellLength = -1;
            this.in = in;
        }

        public void skip(long charPosition) throws IOException {
            while (charPosition > 0) {
                int n = this.in.read(this.buf, 0, Math.min((int) charPosition, this.buf.length));
                if (n >= 0) {
                    this.previouslyRead += (long) n;
                    charPosition -= (long) n;
                } else {
                    return;
                }
            }
        }

        public boolean hasNext() {
            if (this.limit == 0) {
                fill();
            }
            return (this.pos < this.limit || indexAfterCompactionAndFilling(this.pos) < this.limit) && lookingAtCell();
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public java.util.ArrayList<java.lang.String> next() {
            /*
            r10 = this;
            r4 = 1;
            r5 = 0;
            r2 = com.google.appinventor.components.runtime.collect.Lists.newArrayList();
        L_0x0006:
            r6 = r10.buf;
            r7 = r10.pos;
            r6 = r6[r7];
            r7 = 34;
            if (r6 == r7) goto L_0x005e;
        L_0x0010:
            r6 = new java.lang.String;
            r7 = r10.buf;
            r8 = r10.pos;
            r9 = r10.cellLength;
            r6.<init>(r7, r8, r9);
            r6 = r6.trim();
            r2.add(r6);
        L_0x0022:
            r6 = r10.delimitedCellLength;
            if (r6 <= 0) goto L_0x0081;
        L_0x0026:
            r6 = r10.buf;
            r7 = r10.pos;
            r8 = r10.delimitedCellLength;
            r7 = r7 + r8;
            r7 = r7 + -1;
            r6 = r6[r7];
            r7 = 44;
            if (r6 != r7) goto L_0x0081;
        L_0x0035:
            r3 = r4;
        L_0x0036:
            r6 = r10.pos;
            r7 = r10.delimitedCellLength;
            r6 = r6 + r7;
            r10.pos = r6;
            r6 = -1;
            r10.cellLength = r6;
            r10.delimitedCellLength = r6;
            r6 = r10.pos;
            r7 = r10.limit;
            if (r6 < r7) goto L_0x0052;
        L_0x0048:
            r6 = r10.pos;
            r6 = r10.indexAfterCompactionAndFilling(r6);
            r7 = r10.limit;
            if (r6 >= r7) goto L_0x0083;
        L_0x0052:
            r1 = r4;
        L_0x0053:
            if (r3 == 0) goto L_0x005d;
        L_0x0055:
            if (r1 == 0) goto L_0x005d;
        L_0x0057:
            r6 = r10.lookingAtCell();
            if (r6 != 0) goto L_0x0006;
        L_0x005d:
            return r2;
        L_0x005e:
            r0 = new java.lang.String;
            r6 = r10.buf;
            r7 = r10.pos;
            r7 = r7 + 1;
            r8 = r10.cellLength;
            r8 = r8 + -2;
            r0.<init>(r6, r7, r8);
            r6 = r10.ESCAPED_QUOTE_PATTERN;
            r6 = r6.matcher(r0);
            r7 = "\"";
            r6 = r6.replaceAll(r7);
            r6 = r6.trim();
            r2.add(r6);
            goto L_0x0022;
        L_0x0081:
            r3 = r5;
            goto L_0x0036;
        L_0x0083:
            r1 = r5;
            goto L_0x0053;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.util.CsvUtil.CsvParser.next():java.util.ArrayList<java.lang.String>");
        }

        public long getCharPosition() {
            return this.previouslyRead + ((long) this.pos);
        }

        private int indexAfterCompactionAndFilling(int i) {
            if (this.pos > 0) {
                i = compact(i);
            }
            fill();
            return i;
        }

        private int compact(int i) {
            int oldPos = this.pos;
            this.pos = 0;
            int toMove = this.limit - oldPos;
            if (toMove > 0) {
                System.arraycopy(this.buf, oldPos, this.buf, 0, toMove);
            }
            this.limit -= oldPos;
            this.previouslyRead += (long) oldPos;
            return i - oldPos;
        }

        private void fill() {
            int toFill = this.buf.length - this.limit;
            while (this.opened && toFill > 0) {
                try {
                    int n = this.in.read(this.buf, this.limit, toFill);
                    if (n == -1) {
                        this.opened = false;
                    } else {
                        this.limit += n;
                        toFill -= n;
                    }
                } catch (IOException e) {
                    this.lastException = e;
                    this.opened = false;
                }
            }
        }

        private boolean lookingAtCell() {
            return this.buf[this.pos] == '\"' ? findUnescapedEndQuote(this.pos + 1) : findUnquotedCellEnd(this.pos);
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private boolean findUnescapedEndQuote(int r3) {
            /*
            r2 = this;
            r1 = 34;
        L_0x0002:
            r0 = r2.limit;
            if (r3 < r0) goto L_0x000e;
        L_0x0006:
            r3 = r2.indexAfterCompactionAndFilling(r3);
            r0 = r2.limit;
            if (r3 >= r0) goto L_0x0032;
        L_0x000e:
            r0 = r2.buf;
            r0 = r0[r3];
            if (r0 != r1) goto L_0x002f;
        L_0x0014:
            r0 = r3 + 1;
            r3 = r2.checkedIndex(r0);
            r0 = r2.limit;
            if (r3 == r0) goto L_0x0024;
        L_0x001e:
            r0 = r2.buf;
            r0 = r0[r3];
            if (r0 == r1) goto L_0x002f;
        L_0x0024:
            r0 = r2.pos;
            r0 = r3 - r0;
            r2.cellLength = r0;
            r0 = r2.findDelimOrEnd(r3);
        L_0x002e:
            return r0;
        L_0x002f:
            r3 = r3 + 1;
            goto L_0x0002;
        L_0x0032:
            r0 = new java.lang.IllegalArgumentException;
            r1 = "Syntax Error. unclosed quoted cell";
            r0.<init>(r1);
            r2.lastException = r0;
            r0 = 0;
            goto L_0x002e;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.util.CsvUtil.CsvParser.findUnescapedEndQuote(int):boolean");
        }

        private boolean findDelimOrEnd(int i) {
            while (true) {
                if (i >= this.limit) {
                    i = indexAfterCompactionAndFilling(i);
                    if (i >= this.limit) {
                        this.delimitedCellLength = this.limit - this.pos;
                        return true;
                    }
                }
                switch (this.buf[i]) {
                    case ArithOp.ASHIFT_GENERAL /*9*/:
                    case SetExp.SET_IF_UNBOUND /*32*/:
                        i++;
                    case ArithOp.ASHIFT_LEFT /*10*/:
                    case XDataType.NCNAME_TYPE_CODE /*44*/:
                        this.delimitedCellLength = checkedIndex(i + 1) - this.pos;
                        return true;
                    case ArithOp.AND /*13*/:
                        int j = checkedIndex(i + 1);
                        if (this.buf[j] == '\n') {
                            j = checkedIndex(j + 1);
                        }
                        this.delimitedCellLength = j - this.pos;
                        return true;
                    default:
                        this.lastException = new IOException("Syntax Error: non-whitespace between closing quote and delimiter or end");
                        return false;
                }
            }
        }

        private int checkedIndex(int i) {
            return i < this.limit ? i : indexAfterCompactionAndFilling(i);
        }

        private boolean findUnquotedCellEnd(int i) {
            while (true) {
                if (i >= this.limit) {
                    i = indexAfterCompactionAndFilling(i);
                    if (i >= this.limit) {
                        int i2 = this.limit - this.pos;
                        this.cellLength = i2;
                        this.delimitedCellLength = i2;
                        return true;
                    }
                }
                switch (this.buf[i]) {
                    case ArithOp.ASHIFT_LEFT /*10*/:
                    case XDataType.NCNAME_TYPE_CODE /*44*/:
                        this.cellLength = i - this.pos;
                        this.delimitedCellLength = this.cellLength + 1;
                        return true;
                    case ArithOp.AND /*13*/:
                        this.cellLength = i - this.pos;
                        int j = checkedIndex(i + 1);
                        if (this.buf[j] == '\n') {
                            j = checkedIndex(j + 1);
                        }
                        this.delimitedCellLength = j - this.pos;
                        return true;
                    case Sequence.DOCUMENT_VALUE /*34*/:
                        this.lastException = new IllegalArgumentException("Syntax Error: quote in unquoted cell");
                        return false;
                    default:
                        i++;
                }
            }
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public void throwAnyProblem() throws Exception {
            if (this.lastException != null) {
                throw this.lastException;
            }
        }
    }

    private CsvUtil() {
    }

    public static YailList fromCsvTable(String csvString) throws Exception {
        CsvParser csvParser = new CsvParser(new StringReader(csvString));
        List csvList = new ArrayList();
        while (csvParser.hasNext()) {
            csvList.add(YailList.makeList(csvParser.next()));
        }
        csvParser.throwAnyProblem();
        return YailList.makeList(csvList);
    }

    public static YailList fromCsvRow(String csvString) throws Exception {
        CsvParser csvParser = new CsvParser(new StringReader(csvString));
        if (csvParser.hasNext()) {
            YailList row = YailList.makeList(csvParser.next());
            if (csvParser.hasNext()) {
                throw new IllegalArgumentException("CSV text has multiple rows. Expected just one row.");
            }
            csvParser.throwAnyProblem();
            return row;
        }
        throw new IllegalArgumentException("CSV text cannot be parsed as a row.");
    }

    public static String toCsvRow(YailList csvRow) {
        StringBuilder csvStringBuilder = new StringBuilder();
        makeCsvRow(csvRow, csvStringBuilder);
        return csvStringBuilder.toString();
    }

    public static String toCsvTable(YailList csvList) {
        StringBuilder csvStringBuilder = new StringBuilder();
        for (Object rowObj : csvList.toArray()) {
            makeCsvRow((YailList) rowObj, csvStringBuilder);
            csvStringBuilder.append("\r\n");
        }
        return csvStringBuilder.toString();
    }

    private static void makeCsvRow(YailList row, StringBuilder csvStringBuilder) {
        String fieldDelim = ElementType.MATCH_ANY_LOCALNAME;
        for (Object fieldObj : row.toArray()) {
            csvStringBuilder.append(fieldDelim).append("\"").append(fieldObj.toString().replaceAll("\"", "\"\"")).append("\"");
            fieldDelim = ",";
        }
    }
}
