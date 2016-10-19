package com.google.appinventor.components.runtime.util;

import android.util.Log;
import com.google.appinventor.components.runtime.util.Ev3Constants.UIWriteSubcode;
import gnu.expr.Declaration;
import gnu.expr.ModuleExp;
import gnu.kawa.xml.ElementType;
import gnu.kawa.xml.XDataType;
import gnu.lists.Sequence;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Vector;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NanoHTTPD {
    public static final String HTTP_BADREQUEST = "400 Bad Request";
    public static final String HTTP_FORBIDDEN = "403 Forbidden";
    public static final String HTTP_INTERNALERROR = "500 Internal Server Error";
    public static final String HTTP_NOTFOUND = "404 Not Found";
    public static final String HTTP_NOTIMPLEMENTED = "501 Not Implemented";
    public static final String HTTP_NOTMODIFIED = "304 Not Modified";
    public static final String HTTP_OK = "200 OK";
    public static final String HTTP_PARTIALCONTENT = "206 Partial Content";
    public static final String HTTP_RANGE_NOT_SATISFIABLE = "416 Requested Range Not Satisfiable";
    public static final String HTTP_REDIRECT = "301 Moved Permanently";
    private static final String LICENCE = "Copyright (C) 2001,2005-2011 by Jarno Elonen <elonen@iki.fi>\nand Copyright (C) 2010 by Konstantinos Togias <info@ktogias.gr>\n\nRedistribution and use in source and binary forms, with or without\nmodification, are permitted provided that the following conditions\nare met:\n\nRedistributions of source code must retain the above copyright notice,\nthis list of conditions and the following disclaimer. Redistributions in\nbinary form must reproduce the above copyright notice, this list of\nconditions and the following disclaimer in the documentation and/or other\nmaterials provided with the distribution. The name of the author may not\nbe used to endorse or promote products derived from this software without\nspecific prior written permission. \n \nTHIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR\nIMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES\nOF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.\nIN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,\nINCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT\nNOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,\nDATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY\nTHEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT\n(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE\nOF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.";
    private static final String LOG_TAG = "AppInvHTTPD";
    public static final String MIME_DEFAULT_BINARY = "application/octet-stream";
    public static final String MIME_HTML = "text/html";
    public static final String MIME_PLAINTEXT = "text/plain";
    public static final String MIME_XML = "text/xml";
    private static final int REPL_STACK_SIZE = 262144;
    private static SimpleDateFormat gmtFrmt;
    protected static PrintStream myErr;
    protected static PrintStream myOut;
    private static int theBufferSize;
    private static Hashtable theMimeTypes;
    private ThreadPoolExecutor myExecutor;
    private File myRootDir;
    private final ServerSocket myServerSocket;
    private int myTcpPort;
    private Thread myThread;

    /* renamed from: com.google.appinventor.components.runtime.util.NanoHTTPD.1 */
    class C01531 implements Runnable {
        C01531() {
        }

        public void run() {
            while (true) {
                try {
                    HTTPSession hTTPSession = new HTTPSession(NanoHTTPD.this.myServerSocket.accept());
                } catch (IOException e) {
                    return;
                }
            }
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.util.NanoHTTPD.2 */
    class C01542 extends FileInputStream {
        final /* synthetic */ long val$dataLen;

        C01542(File x0, long j) {
            this.val$dataLen = j;
            super(x0);
        }

        public int available() throws IOException {
            return (int) this.val$dataLen;
        }
    }

    private class HTTPSession implements Runnable {
        private Socket mySocket;

        public HTTPSession(Socket s) {
            this.mySocket = s;
            Log.d(NanoHTTPD.LOG_TAG, "NanoHTTPD: getPoolSize() = " + NanoHTTPD.this.myExecutor.getPoolSize());
            NanoHTTPD.this.myExecutor.execute(this);
        }

        public void run() {
            try {
                InputStream is = this.mySocket.getInputStream();
                if (is != null) {
                    byte[] buf = new byte[Declaration.TYPE_SPECIFIED];
                    int rlen = is.read(buf, 0, Declaration.TYPE_SPECIFIED);
                    if (rlen > 0) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buf, 0, rlen)));
                        Properties pre = new Properties();
                        Properties parms = new Properties();
                        Properties header = new Properties();
                        Properties files = new Properties();
                        decodeHeader(bufferedReader, pre, parms, header);
                        String method = pre.getProperty("method");
                        String uri = pre.getProperty("uri");
                        long size = Long.MAX_VALUE;
                        String contentLength = header.getProperty("content-length");
                        if (contentLength != null) {
                            try {
                                size = (long) Integer.parseInt(contentLength);
                            } catch (NumberFormatException e) {
                            }
                        }
                        int splitbyte = 0;
                        boolean sbfound = false;
                        while (splitbyte < rlen) {
                            if (buf[splitbyte] == 13) {
                                splitbyte++;
                                if (buf[splitbyte] == 10) {
                                    splitbyte++;
                                    if (buf[splitbyte] == 13) {
                                        splitbyte++;
                                        if (buf[splitbyte] == 10) {
                                            sbfound = true;
                                            break;
                                        }
                                    } else {
                                        continue;
                                    }
                                } else {
                                    continue;
                                }
                            }
                            splitbyte++;
                        }
                        splitbyte++;
                        ByteArrayOutputStream f = new ByteArrayOutputStream();
                        if (splitbyte < rlen) {
                            f.write(buf, splitbyte, rlen - splitbyte);
                        }
                        if (splitbyte < rlen) {
                            size -= (long) ((rlen - splitbyte) + 1);
                        } else if (!sbfound || size == Long.MAX_VALUE) {
                            size = 0;
                        }
                        buf = new byte[Declaration.NOT_DEFINING];
                        while (rlen >= 0 && size > 0) {
                            rlen = is.read(buf, 0, Declaration.NOT_DEFINING);
                            size -= (long) rlen;
                            if (rlen > 0) {
                                f.write(buf, 0, rlen);
                            }
                        }
                        byte[] fbuf = f.toByteArray();
                        BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(fbuf)));
                        if (method.equalsIgnoreCase("POST")) {
                            String contentType = ElementType.MATCH_ANY_LOCALNAME;
                            String property = header.getProperty("content-type");
                            StringTokenizer stringTokenizer = new StringTokenizer(contentTypeHeader, "; ");
                            if (stringTokenizer.hasMoreTokens()) {
                                contentType = stringTokenizer.nextToken();
                            }
                            if (contentType.equalsIgnoreCase("multipart/form-data")) {
                                if (!stringTokenizer.hasMoreTokens()) {
                                    sendError(NanoHTTPD.HTTP_BADREQUEST, "BAD REQUEST: Content type is multipart/form-data but boundary missing. Usage: GET /example/file.html");
                                }
                                stringTokenizer = new StringTokenizer(stringTokenizer.nextToken(), "=");
                                if (stringTokenizer.countTokens() != 2) {
                                    sendError(NanoHTTPD.HTTP_BADREQUEST, "BAD REQUEST: Content type is multipart/form-data but boundary syntax error. Usage: GET /example/file.html");
                                }
                                stringTokenizer.nextToken();
                                decodeMultipartData(stringTokenizer.nextToken(), fbuf, in, parms, files);
                            } else {
                                String postLine = ElementType.MATCH_ANY_LOCALNAME;
                                char[] pbuf = new char[Declaration.NOT_DEFINING];
                                for (int read = in.read(pbuf); read >= 0; read = in.read(pbuf)) {
                                    if (postLine.endsWith("\r\n")) {
                                        break;
                                    }
                                    postLine = postLine + String.valueOf(pbuf, 0, read);
                                }
                                decodeParms(postLine.trim(), parms);
                            }
                        }
                        if (method.equalsIgnoreCase("PUT")) {
                            files.put("content", saveTmpFile(fbuf, 0, f.size()));
                        }
                        Properties properties = parms;
                        Properties properties2 = files;
                        Response r = NanoHTTPD.this.serve(uri, method, header, properties, properties2, this.mySocket);
                        if (r == null) {
                            sendError(NanoHTTPD.HTTP_INTERNALERROR, "SERVER INTERNAL ERROR: Serve() returned a null response.");
                        } else {
                            sendResponse(r.status, r.mimeType, r.header, r.data);
                        }
                        in.close();
                        is.close();
                    }
                }
            } catch (IOException ioe) {
                sendError(NanoHTTPD.HTTP_INTERNALERROR, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
            } catch (InterruptedException e2) {
            } catch (Throwable th) {
            }
        }

        private void decodeHeader(BufferedReader in, Properties pre, Properties parms, Properties header) throws InterruptedException {
            try {
                String inLine = in.readLine();
                if (inLine != null) {
                    StringTokenizer st = new StringTokenizer(inLine);
                    if (!st.hasMoreTokens()) {
                        sendError(NanoHTTPD.HTTP_BADREQUEST, "BAD REQUEST: Syntax error. Usage: GET /example/file.html");
                    }
                    pre.put("method", st.nextToken());
                    if (!st.hasMoreTokens()) {
                        sendError(NanoHTTPD.HTTP_BADREQUEST, "BAD REQUEST: Missing URI. Usage: GET /example/file.html");
                    }
                    String uri = st.nextToken();
                    int qmi = uri.indexOf(63);
                    if (qmi >= 0) {
                        decodeParms(uri.substring(qmi + 1), parms);
                        uri = decodePercent(uri.substring(0, qmi));
                    } else {
                        uri = decodePercent(uri);
                    }
                    if (st.hasMoreTokens()) {
                        String line = in.readLine();
                        while (line != null && line.trim().length() > 0) {
                            int p = line.indexOf(58);
                            if (p >= 0) {
                                header.put(line.substring(0, p).trim().toLowerCase(), line.substring(p + 1).trim());
                            }
                            line = in.readLine();
                        }
                    }
                    pre.put("uri", uri);
                }
            } catch (IOException ioe) {
                sendError(NanoHTTPD.HTTP_INTERNALERROR, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
            }
        }

        private void decodeMultipartData(String boundary, byte[] fbuf, BufferedReader in, Properties parms, Properties files) throws InterruptedException {
            try {
                int[] bpositions = getBoundaryPositions(fbuf, boundary.getBytes());
                int boundarycount = 1;
                String mpline = in.readLine();
                while (mpline != null) {
                    int p;
                    if (mpline.indexOf(boundary) == -1) {
                        sendError(NanoHTTPD.HTTP_BADREQUEST, "BAD REQUEST: Content type is multipart/form-data but next chunk does not start with boundary. Usage: GET /example/file.html");
                    }
                    boundarycount++;
                    Properties item = new Properties();
                    mpline = in.readLine();
                    while (mpline != null && mpline.trim().length() > 0) {
                        p = mpline.indexOf(58);
                        if (p != -1) {
                            item.put(mpline.substring(0, p).trim().toLowerCase(), mpline.substring(p + 1).trim());
                        }
                        mpline = in.readLine();
                    }
                    if (mpline != null) {
                        String contentDisposition = item.getProperty("content-disposition");
                        if (contentDisposition == null) {
                            sendError(NanoHTTPD.HTTP_BADREQUEST, "BAD REQUEST: Content type is multipart/form-data but no content-disposition info found. Usage: GET /example/file.html");
                        }
                        StringTokenizer st = new StringTokenizer(contentDisposition, "; ");
                        Properties disposition = new Properties();
                        while (st.hasMoreTokens()) {
                            String token = st.nextToken();
                            p = token.indexOf(61);
                            if (p != -1) {
                                disposition.put(token.substring(0, p).trim().toLowerCase(), token.substring(p + 1).trim());
                            }
                        }
                        String pname = disposition.getProperty("name");
                        pname = pname.substring(1, pname.length() - 1);
                        String value = ElementType.MATCH_ANY_LOCALNAME;
                        if (item.getProperty("content-type") != null) {
                            int length = bpositions.length;
                            if (boundarycount > r0) {
                                sendError(NanoHTTPD.HTTP_INTERNALERROR, "Error processing request");
                            }
                            int offset = stripMultipartHeaders(fbuf, bpositions[boundarycount - 2]);
                            files.put(pname, saveTmpFile(fbuf, offset, (bpositions[boundarycount - 1] - offset) - 4));
                            value = disposition.getProperty("filename");
                            value = value.substring(1, value.length() - 1);
                            do {
                                mpline = in.readLine();
                                if (mpline == null) {
                                    break;
                                }
                            } while (mpline.indexOf(boundary) == -1);
                        } else {
                            while (mpline != null && mpline.indexOf(boundary) == -1) {
                                mpline = in.readLine();
                                if (mpline != null) {
                                    int d = mpline.indexOf(boundary);
                                    if (d == -1) {
                                        value = value + mpline;
                                    } else {
                                        value = value + mpline.substring(0, d - 2);
                                    }
                                }
                            }
                        }
                        parms.put(pname, value);
                    }
                }
            } catch (IOException ioe) {
                sendError(NanoHTTPD.HTTP_INTERNALERROR, "SERVER INTERNAL ERROR: IOException: " + ioe.getMessage());
            }
        }

        public int[] getBoundaryPositions(byte[] b, byte[] boundary) {
            int matchcount = 0;
            int matchbyte = -1;
            Vector matchbytes = new Vector();
            int i = 0;
            while (i < b.length) {
                if (b[i] == boundary[matchcount]) {
                    if (matchcount == 0) {
                        matchbyte = i;
                    }
                    matchcount++;
                    if (matchcount == boundary.length) {
                        matchbytes.addElement(new Integer(matchbyte));
                        matchcount = 0;
                        matchbyte = -1;
                    }
                } else {
                    i -= matchcount;
                    matchcount = 0;
                    matchbyte = -1;
                }
                i++;
            }
            int[] ret = new int[matchbytes.size()];
            for (i = 0; i < ret.length; i++) {
                ret[i] = ((Integer) matchbytes.elementAt(i)).intValue();
            }
            return ret;
        }

        private String saveTmpFile(byte[] b, int offset, int len) {
            String path = ElementType.MATCH_ANY_LOCALNAME;
            if (len > 0) {
                try {
                    File temp = File.createTempFile("NanoHTTPD", ElementType.MATCH_ANY_LOCALNAME, new File(System.getProperty("java.io.tmpdir")));
                    OutputStream fstream = new FileOutputStream(temp);
                    fstream.write(b, offset, len);
                    fstream.close();
                    path = temp.getAbsolutePath();
                } catch (Exception e) {
                    NanoHTTPD.myErr.println("Error: " + e.getMessage());
                }
            }
            return path;
        }

        private int stripMultipartHeaders(byte[] b, int offset) {
            int i = offset;
            while (i < b.length) {
                if (b[i] == UIWriteSubcode.ADDRESS) {
                    i++;
                    if (b[i] == (byte) 10) {
                        i++;
                        if (b[i] == UIWriteSubcode.ADDRESS) {
                            i++;
                            if (b[i] == (byte) 10) {
                                break;
                            }
                        } else {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
                i++;
            }
            return i + 1;
        }

        private String decodePercent(String str) throws InterruptedException {
            try {
                StringBuffer sb = new StringBuffer();
                int i = 0;
                while (i < str.length()) {
                    char c = str.charAt(i);
                    switch (c) {
                        case Sequence.PROCESSING_INSTRUCTION_VALUE /*37*/:
                            sb.append((char) Integer.parseInt(str.substring(i + 1, i + 3), 16));
                            i += 2;
                            break;
                        case XDataType.NAME_TYPE_CODE /*43*/:
                            sb.append(' ');
                            break;
                        default:
                            sb.append(c);
                            break;
                    }
                    i++;
                }
                return sb.toString();
            } catch (Exception e) {
                sendError(NanoHTTPD.HTTP_BADREQUEST, "BAD REQUEST: Bad percent-encoding.");
                return null;
            }
        }

        private void decodeParms(String parms, Properties p) throws InterruptedException {
            if (parms != null) {
                StringTokenizer st = new StringTokenizer(parms, "&");
                while (st.hasMoreTokens()) {
                    String e = st.nextToken();
                    int sep = e.indexOf(61);
                    if (sep >= 0) {
                        p.put(decodePercent(e.substring(0, sep)).trim(), decodePercent(e.substring(sep + 1)));
                    }
                }
            }
        }

        private void sendError(String status, String msg) throws InterruptedException {
            sendResponse(status, NanoHTTPD.MIME_PLAINTEXT, null, new ByteArrayInputStream(msg.getBytes()));
            throw new InterruptedException();
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void sendResponse(java.lang.String r14, java.lang.String r15, java.util.Properties r16, java.io.InputStream r17) {
            /*
            r13 = this;
            if (r14 != 0) goto L_0x0011;
        L_0x0002:
            r10 = new java.lang.Error;	 Catch:{ IOException -> 0x000a }
            r11 = "sendResponse(): Status can't be null.";
            r10.<init>(r11);	 Catch:{ IOException -> 0x000a }
            throw r10;	 Catch:{ IOException -> 0x000a }
        L_0x000a:
            r3 = move-exception;
            r10 = r13.mySocket;	 Catch:{ Throwable -> 0x0102 }
            r10.close();	 Catch:{ Throwable -> 0x0102 }
        L_0x0010:
            return;
        L_0x0011:
            r10 = r13.mySocket;	 Catch:{ IOException -> 0x000a }
            r5 = r10.getOutputStream();	 Catch:{ IOException -> 0x000a }
            r7 = new java.io.PrintWriter;	 Catch:{ IOException -> 0x000a }
            r7.<init>(r5);	 Catch:{ IOException -> 0x000a }
            r10 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x000a }
            r10.<init>();	 Catch:{ IOException -> 0x000a }
            r11 = "HTTP/1.0 ";
            r10 = r10.append(r11);	 Catch:{ IOException -> 0x000a }
            r10 = r10.append(r14);	 Catch:{ IOException -> 0x000a }
            r11 = " \r\n";
            r10 = r10.append(r11);	 Catch:{ IOException -> 0x000a }
            r10 = r10.toString();	 Catch:{ IOException -> 0x000a }
            r7.print(r10);	 Catch:{ IOException -> 0x000a }
            if (r15 == 0) goto L_0x0056;
        L_0x003a:
            r10 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x000a }
            r10.<init>();	 Catch:{ IOException -> 0x000a }
            r11 = "Content-Type: ";
            r10 = r10.append(r11);	 Catch:{ IOException -> 0x000a }
            r10 = r10.append(r15);	 Catch:{ IOException -> 0x000a }
            r11 = "\r\n";
            r10 = r10.append(r11);	 Catch:{ IOException -> 0x000a }
            r10 = r10.toString();	 Catch:{ IOException -> 0x000a }
            r7.print(r10);	 Catch:{ IOException -> 0x000a }
        L_0x0056:
            if (r16 == 0) goto L_0x0062;
        L_0x0058:
            r10 = "Date";
            r0 = r16;
            r10 = r0.getProperty(r10);	 Catch:{ IOException -> 0x000a }
            if (r10 != 0) goto L_0x008b;
        L_0x0062:
            r10 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x000a }
            r10.<init>();	 Catch:{ IOException -> 0x000a }
            r11 = "Date: ";
            r10 = r10.append(r11);	 Catch:{ IOException -> 0x000a }
            r11 = com.google.appinventor.components.runtime.util.NanoHTTPD.gmtFrmt;	 Catch:{ IOException -> 0x000a }
            r12 = new java.util.Date;	 Catch:{ IOException -> 0x000a }
            r12.<init>();	 Catch:{ IOException -> 0x000a }
            r11 = r11.format(r12);	 Catch:{ IOException -> 0x000a }
            r10 = r10.append(r11);	 Catch:{ IOException -> 0x000a }
            r11 = "\r\n";
            r10 = r10.append(r11);	 Catch:{ IOException -> 0x000a }
            r10 = r10.toString();	 Catch:{ IOException -> 0x000a }
            r7.print(r10);	 Catch:{ IOException -> 0x000a }
        L_0x008b:
            if (r16 == 0) goto L_0x00c4;
        L_0x008d:
            r2 = r16.keys();	 Catch:{ IOException -> 0x000a }
        L_0x0091:
            r10 = r2.hasMoreElements();	 Catch:{ IOException -> 0x000a }
            if (r10 == 0) goto L_0x00c4;
        L_0x0097:
            r4 = r2.nextElement();	 Catch:{ IOException -> 0x000a }
            r4 = (java.lang.String) r4;	 Catch:{ IOException -> 0x000a }
            r0 = r16;
            r9 = r0.getProperty(r4);	 Catch:{ IOException -> 0x000a }
            r10 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x000a }
            r10.<init>();	 Catch:{ IOException -> 0x000a }
            r10 = r10.append(r4);	 Catch:{ IOException -> 0x000a }
            r11 = ": ";
            r10 = r10.append(r11);	 Catch:{ IOException -> 0x000a }
            r10 = r10.append(r9);	 Catch:{ IOException -> 0x000a }
            r11 = "\r\n";
            r10 = r10.append(r11);	 Catch:{ IOException -> 0x000a }
            r10 = r10.toString();	 Catch:{ IOException -> 0x000a }
            r7.print(r10);	 Catch:{ IOException -> 0x000a }
            goto L_0x0091;
        L_0x00c4:
            r10 = "\r\n";
            r7.print(r10);	 Catch:{ IOException -> 0x000a }
            r7.flush();	 Catch:{ IOException -> 0x000a }
            if (r17 == 0) goto L_0x00ed;
        L_0x00ce:
            r6 = r17.available();	 Catch:{ IOException -> 0x000a }
            r10 = com.google.appinventor.components.runtime.util.NanoHTTPD.theBufferSize;	 Catch:{ IOException -> 0x000a }
            r1 = new byte[r10];	 Catch:{ IOException -> 0x000a }
        L_0x00d8:
            if (r6 <= 0) goto L_0x00ed;
        L_0x00da:
            r11 = 0;
            r10 = com.google.appinventor.components.runtime.util.NanoHTTPD.theBufferSize;	 Catch:{ IOException -> 0x000a }
            if (r6 <= r10) goto L_0x00fa;
        L_0x00e1:
            r10 = com.google.appinventor.components.runtime.util.NanoHTTPD.theBufferSize;	 Catch:{ IOException -> 0x000a }
        L_0x00e5:
            r0 = r17;
            r8 = r0.read(r1, r11, r10);	 Catch:{ IOException -> 0x000a }
            if (r8 > 0) goto L_0x00fc;
        L_0x00ed:
            r5.flush();	 Catch:{ IOException -> 0x000a }
            r5.close();	 Catch:{ IOException -> 0x000a }
            if (r17 == 0) goto L_0x0010;
        L_0x00f5:
            r17.close();	 Catch:{ IOException -> 0x000a }
            goto L_0x0010;
        L_0x00fa:
            r10 = r6;
            goto L_0x00e5;
        L_0x00fc:
            r10 = 0;
            r5.write(r1, r10, r8);	 Catch:{ IOException -> 0x000a }
            r6 = r6 - r8;
            goto L_0x00d8;
        L_0x0102:
            r10 = move-exception;
            goto L_0x0010;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.util.NanoHTTPD.HTTPSession.sendResponse(java.lang.String, java.lang.String, java.util.Properties, java.io.InputStream):void");
        }
    }

    public class Response {
        public InputStream data;
        public Properties header;
        public String mimeType;
        public String status;

        public Response() {
            this.header = new Properties();
            this.status = NanoHTTPD.HTTP_OK;
        }

        public Response(String status, String mimeType, InputStream data) {
            this.header = new Properties();
            this.status = status;
            this.mimeType = mimeType;
            this.data = data;
        }

        public Response(String status, String mimeType, String txt) {
            this.header = new Properties();
            this.status = status;
            this.mimeType = mimeType;
            try {
                this.data = new ByteArrayInputStream(txt.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException uee) {
                uee.printStackTrace();
            }
        }

        public void addHeader(String name, String value) {
            this.header.put(name, value);
        }
    }

    private class myThreadFactory implements ThreadFactory {
        private myThreadFactory() {
        }

        public Thread newThread(Runnable r) {
            Thread retval = new Thread(new ThreadGroup("biggerstack"), r, "HTTPD Session", 262144);
            retval.setDaemon(true);
            return retval;
        }
    }

    public Response serve(String uri, String method, Properties header, Properties parms, Properties files, Socket mySocket) {
        myOut.println(method + " '" + uri + "' ");
        Enumeration e = header.propertyNames();
        while (e.hasMoreElements()) {
            String value = (String) e.nextElement();
            myOut.println("  HDR: '" + value + "' = '" + header.getProperty(value) + "'");
        }
        e = parms.propertyNames();
        while (e.hasMoreElements()) {
            value = (String) e.nextElement();
            myOut.println("  PRM: '" + value + "' = '" + parms.getProperty(value) + "'");
        }
        e = files.propertyNames();
        while (e.hasMoreElements()) {
            value = (String) e.nextElement();
            myOut.println("  UPLOADED: '" + value + "' = '" + files.getProperty(value) + "'");
        }
        return serveFile(uri, header, this.myRootDir, true);
    }

    public NanoHTTPD(int port, File wwwroot) throws IOException {
        this.myExecutor = new ThreadPoolExecutor(2, 10, 5, TimeUnit.SECONDS, new SynchronousQueue(), new myThreadFactory());
        this.myTcpPort = port;
        this.myRootDir = wwwroot;
        this.myServerSocket = new ServerSocket(this.myTcpPort);
        this.myThread = new Thread(new C01531());
        this.myThread.setDaemon(true);
        this.myThread.start();
    }

    public void stop() {
        try {
            this.myServerSocket.close();
            this.myThread.join();
        } catch (IOException e) {
        } catch (InterruptedException e2) {
        }
    }

    public static void main(String[] args) {
        myOut.println("NanoHTTPD 1.25 (C) 2001,2005-2011 Jarno Elonen and (C) 2010 Konstantinos Togias\n(Command line options: [-p port] [-d root-dir] [--licence])\n");
        int port = 80;
        File wwwroot = new File(".").getAbsoluteFile();
        for (int i = 0; i < args.length; i++) {
            if (args[i].equalsIgnoreCase("-p")) {
                port = Integer.parseInt(args[i + 1]);
            } else if (args[i].equalsIgnoreCase("-d")) {
                wwwroot = new File(args[i + 1]).getAbsoluteFile();
            } else if (args[i].toLowerCase().endsWith("licence")) {
                myOut.println("Copyright (C) 2001,2005-2011 by Jarno Elonen <elonen@iki.fi>\nand Copyright (C) 2010 by Konstantinos Togias <info@ktogias.gr>\n\nRedistribution and use in source and binary forms, with or without\nmodification, are permitted provided that the following conditions\nare met:\n\nRedistributions of source code must retain the above copyright notice,\nthis list of conditions and the following disclaimer. Redistributions in\nbinary form must reproduce the above copyright notice, this list of\nconditions and the following disclaimer in the documentation and/or other\nmaterials provided with the distribution. The name of the author may not\nbe used to endorse or promote products derived from this software without\nspecific prior written permission. \n \nTHIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR\nIMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES\nOF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.\nIN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,\nINCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT\nNOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,\nDATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY\nTHEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT\n(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE\nOF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.\n");
                break;
            }
        }
        try {
            NanoHTTPD nanoHTTPD = new NanoHTTPD(port, wwwroot);
        } catch (IOException ioe) {
            myErr.println("Couldn't start server:\n" + ioe);
            System.exit(-1);
        }
        myOut.println("Now serving files in port " + port + " from \"" + wwwroot + "\"");
        myOut.println("Hit Enter to stop.\n");
        try {
            System.in.read();
        } catch (Throwable th) {
        }
    }

    private String encodeUri(String uri) {
        String newUri = ElementType.MATCH_ANY_LOCALNAME;
        StringTokenizer st = new StringTokenizer(uri, "/ ", true);
        while (st.hasMoreTokens()) {
            String tok = st.nextToken();
            if (tok.equals("/")) {
                newUri = newUri + "/";
            } else if (tok.equals(" ")) {
                newUri = newUri + "%20";
            } else {
                newUri = newUri + URLEncoder.encode(tok);
            }
        }
        return newUri;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.appinventor.components.runtime.util.NanoHTTPD.Response serveFile(java.lang.String r41, java.util.Properties r42, java.io.File r43, boolean r44) {
        /*
        r40 = this;
        r29 = 0;
        r35 = r43.isDirectory();
        if (r35 != 0) goto L_0x001d;
    L_0x0008:
        r29 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r35 = "500 Internal Server Error";
        r36 = "text/plain";
        r37 = "INTERNAL ERRROR: serveFile(): given homeDir is not a directory.";
        r0 = r29;
        r1 = r40;
        r2 = r35;
        r3 = r36;
        r4 = r37;
        r0.<init>(r2, r3, r4);
    L_0x001d:
        if (r29 != 0) goto L_0x0086;
    L_0x001f:
        r35 = r41.trim();
        r36 = java.io.File.separatorChar;
        r37 = 47;
        r41 = r35.replace(r36, r37);
        r35 = 63;
        r0 = r41;
        r1 = r35;
        r35 = r0.indexOf(r1);
        if (r35 < 0) goto L_0x004d;
    L_0x0037:
        r35 = 0;
        r36 = 63;
        r0 = r41;
        r1 = r36;
        r36 = r0.indexOf(r1);
        r0 = r41;
        r1 = r35;
        r2 = r36;
        r41 = r0.substring(r1, r2);
    L_0x004d:
        r35 = "..";
        r0 = r41;
        r1 = r35;
        r35 = r0.startsWith(r1);
        if (r35 != 0) goto L_0x0071;
    L_0x0059:
        r35 = "..";
        r0 = r41;
        r1 = r35;
        r35 = r0.endsWith(r1);
        if (r35 != 0) goto L_0x0071;
    L_0x0065:
        r35 = "../";
        r0 = r41;
        r1 = r35;
        r35 = r0.indexOf(r1);
        if (r35 < 0) goto L_0x0086;
    L_0x0071:
        r29 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r35 = "403 Forbidden";
        r36 = "text/plain";
        r37 = "FORBIDDEN: Won't serve ../ for security reasons.";
        r0 = r29;
        r1 = r40;
        r2 = r35;
        r3 = r36;
        r4 = r37;
        r0.<init>(r2, r3, r4);
    L_0x0086:
        r14 = new java.io.File;
        r0 = r43;
        r1 = r41;
        r14.<init>(r0, r1);
        if (r29 != 0) goto L_0x00ac;
    L_0x0091:
        r35 = r14.exists();
        if (r35 != 0) goto L_0x00ac;
    L_0x0097:
        r29 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r35 = "404 Not Found";
        r36 = "text/plain";
        r37 = "Error 404, file not found.";
        r0 = r29;
        r1 = r40;
        r2 = r35;
        r3 = r36;
        r4 = r37;
        r0.<init>(r2, r3, r4);
    L_0x00ac:
        if (r29 != 0) goto L_0x0638;
    L_0x00ae:
        r35 = r14.isDirectory();
        if (r35 == 0) goto L_0x0638;
    L_0x00b4:
        r35 = "/";
        r0 = r41;
        r1 = r35;
        r35 = r0.endsWith(r1);
        if (r35 != 0) goto L_0x0120;
    L_0x00c0:
        r35 = new java.lang.StringBuilder;
        r35.<init>();
        r0 = r35;
        r1 = r41;
        r35 = r0.append(r1);
        r36 = "/";
        r35 = r35.append(r36);
        r41 = r35.toString();
        r29 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r35 = "301 Moved Permanently";
        r36 = "text/html";
        r37 = new java.lang.StringBuilder;
        r37.<init>();
        r38 = "<html><body>Redirected: <a href=\"";
        r37 = r37.append(r38);
        r0 = r37;
        r1 = r41;
        r37 = r0.append(r1);
        r38 = "\">";
        r37 = r37.append(r38);
        r0 = r37;
        r1 = r41;
        r37 = r0.append(r1);
        r38 = "</a></body></html>";
        r37 = r37.append(r38);
        r37 = r37.toString();
        r0 = r29;
        r1 = r40;
        r2 = r35;
        r3 = r36;
        r4 = r37;
        r0.<init>(r2, r3, r4);
        r35 = "Location";
        r0 = r29;
        r1 = r35;
        r2 = r41;
        r0.addHeader(r1, r2);
    L_0x0120:
        if (r29 != 0) goto L_0x0638;
    L_0x0122:
        r35 = new java.io.File;
        r36 = "index.html";
        r0 = r35;
        r1 = r36;
        r0.<init>(r14, r1);
        r35 = r35.exists();
        if (r35 == 0) goto L_0x0264;
    L_0x0133:
        r14 = new java.io.File;
        r35 = new java.lang.StringBuilder;
        r35.<init>();
        r0 = r35;
        r1 = r41;
        r35 = r0.append(r1);
        r36 = "/index.html";
        r35 = r35.append(r36);
        r35 = r35.toString();
        r0 = r43;
        r1 = r35;
        r14.<init>(r0, r1);
        r30 = r29;
    L_0x0155:
        if (r30 != 0) goto L_0x0634;
    L_0x0157:
        r21 = 0;
        r35 = r14.getCanonicalPath();	 Catch:{ IOException -> 0x062c }
        r36 = 46;
        r10 = r35.lastIndexOf(r36);	 Catch:{ IOException -> 0x062c }
        if (r10 < 0) goto L_0x017f;
    L_0x0165:
        r35 = theMimeTypes;	 Catch:{ IOException -> 0x062c }
        r36 = r14.getCanonicalPath();	 Catch:{ IOException -> 0x062c }
        r37 = r10 + 1;
        r36 = r36.substring(r37);	 Catch:{ IOException -> 0x062c }
        r36 = r36.toLowerCase();	 Catch:{ IOException -> 0x062c }
        r35 = r35.get(r36);	 Catch:{ IOException -> 0x062c }
        r0 = r35;
        r0 = (java.lang.String) r0;	 Catch:{ IOException -> 0x062c }
        r21 = r0;
    L_0x017f:
        if (r21 != 0) goto L_0x0183;
    L_0x0181:
        r21 = "application/octet-stream";
    L_0x0183:
        r35 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x062c }
        r35.<init>();	 Catch:{ IOException -> 0x062c }
        r36 = r14.getAbsolutePath();	 Catch:{ IOException -> 0x062c }
        r35 = r35.append(r36);	 Catch:{ IOException -> 0x062c }
        r36 = r14.lastModified();	 Catch:{ IOException -> 0x062c }
        r35 = r35.append(r36);	 Catch:{ IOException -> 0x062c }
        r36 = "";
        r35 = r35.append(r36);	 Catch:{ IOException -> 0x062c }
        r36 = r14.length();	 Catch:{ IOException -> 0x062c }
        r35 = r35.append(r36);	 Catch:{ IOException -> 0x062c }
        r35 = r35.toString();	 Catch:{ IOException -> 0x062c }
        r35 = r35.hashCode();	 Catch:{ IOException -> 0x062c }
        r11 = java.lang.Integer.toHexString(r35);	 Catch:{ IOException -> 0x062c }
        r32 = 0;
        r12 = -1;
        r35 = "range";
        r0 = r42;
        r1 = r35;
        r28 = r0.getProperty(r1);	 Catch:{ IOException -> 0x062c }
        if (r28 == 0) goto L_0x0206;
    L_0x01c2:
        r35 = "bytes=";
        r0 = r28;
        r1 = r35;
        r35 = r0.startsWith(r1);	 Catch:{ IOException -> 0x062c }
        if (r35 == 0) goto L_0x0206;
    L_0x01ce:
        r35 = "bytes=";
        r35 = r35.length();	 Catch:{ IOException -> 0x062c }
        r0 = r28;
        r1 = r35;
        r28 = r0.substring(r1);	 Catch:{ IOException -> 0x062c }
        r35 = 45;
        r0 = r28;
        r1 = r35;
        r24 = r0.indexOf(r1);	 Catch:{ IOException -> 0x062c }
        if (r24 <= 0) goto L_0x0206;
    L_0x01e8:
        r35 = 0;
        r0 = r28;
        r1 = r35;
        r2 = r24;
        r35 = r0.substring(r1, r2);	 Catch:{ NumberFormatException -> 0x0631 }
        r32 = java.lang.Long.parseLong(r35);	 Catch:{ NumberFormatException -> 0x0631 }
        r35 = r24 + 1;
        r0 = r28;
        r1 = r35;
        r35 = r0.substring(r1);	 Catch:{ NumberFormatException -> 0x0631 }
        r12 = java.lang.Long.parseLong(r35);	 Catch:{ NumberFormatException -> 0x0631 }
    L_0x0206:
        r16 = r14.length();	 Catch:{ IOException -> 0x062c }
        if (r28 == 0) goto L_0x05c0;
    L_0x020c:
        r36 = 0;
        r35 = (r32 > r36 ? 1 : (r32 == r36 ? 0 : -1));
        if (r35 < 0) goto L_0x05c0;
    L_0x0212:
        r35 = (r32 > r16 ? 1 : (r32 == r16 ? 0 : -1));
        if (r35 < 0) goto L_0x0506;
    L_0x0216:
        r29 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;	 Catch:{ IOException -> 0x062c }
        r35 = "416 Requested Range Not Satisfiable";
        r36 = "text/plain";
        r37 = "";
        r0 = r29;
        r1 = r40;
        r2 = r35;
        r3 = r36;
        r4 = r37;
        r0.<init>(r2, r3, r4);	 Catch:{ IOException -> 0x062c }
        r35 = "Content-Range";
        r36 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x05a8 }
        r36.<init>();	 Catch:{ IOException -> 0x05a8 }
        r37 = "bytes 0-0/";
        r36 = r36.append(r37);	 Catch:{ IOException -> 0x05a8 }
        r0 = r36;
        r1 = r16;
        r36 = r0.append(r1);	 Catch:{ IOException -> 0x05a8 }
        r36 = r36.toString();	 Catch:{ IOException -> 0x05a8 }
        r0 = r29;
        r1 = r35;
        r2 = r36;
        r0.addHeader(r1, r2);	 Catch:{ IOException -> 0x05a8 }
        r35 = "ETag";
        r0 = r29;
        r1 = r35;
        r0.addHeader(r1, r11);	 Catch:{ IOException -> 0x05a8 }
    L_0x0256:
        r35 = "Accept-Ranges";
        r36 = "bytes";
        r0 = r29;
        r1 = r35;
        r2 = r36;
        r0.addHeader(r1, r2);
        return r29;
    L_0x0264:
        r35 = new java.io.File;
        r36 = "index.htm";
        r0 = r35;
        r1 = r36;
        r0.<init>(r14, r1);
        r35 = r35.exists();
        if (r35 == 0) goto L_0x0299;
    L_0x0275:
        r14 = new java.io.File;
        r35 = new java.lang.StringBuilder;
        r35.<init>();
        r0 = r35;
        r1 = r41;
        r35 = r0.append(r1);
        r36 = "/index.htm";
        r35 = r35.append(r36);
        r35 = r35.toString();
        r0 = r43;
        r1 = r35;
        r14.<init>(r0, r1);
        r30 = r29;
        goto L_0x0155;
    L_0x0299:
        if (r44 == 0) goto L_0x04ed;
    L_0x029b:
        r35 = r14.canRead();
        if (r35 == 0) goto L_0x04ed;
    L_0x02a1:
        r15 = r14.list();
        r35 = new java.lang.StringBuilder;
        r35.<init>();
        r36 = "<html><body><h1>Directory ";
        r35 = r35.append(r36);
        r0 = r35;
        r1 = r41;
        r35 = r0.append(r1);
        r36 = "</h1><br/>";
        r35 = r35.append(r36);
        r25 = r35.toString();
        r35 = r41.length();
        r36 = 1;
        r0 = r35;
        r1 = r36;
        if (r0 <= r1) goto L_0x0321;
    L_0x02ce:
        r35 = 0;
        r36 = r41.length();
        r36 = r36 + -1;
        r0 = r41;
        r1 = r35;
        r2 = r36;
        r34 = r0.substring(r1, r2);
        r35 = 47;
        r31 = r34.lastIndexOf(r35);
        if (r31 < 0) goto L_0x0321;
    L_0x02e8:
        r35 = r34.length();
        r0 = r31;
        r1 = r35;
        if (r0 >= r1) goto L_0x0321;
    L_0x02f2:
        r35 = new java.lang.StringBuilder;
        r35.<init>();
        r0 = r35;
        r1 = r25;
        r35 = r0.append(r1);
        r36 = "<b><a href=\"";
        r35 = r35.append(r36);
        r36 = 0;
        r37 = r31 + 1;
        r0 = r41;
        r1 = r36;
        r2 = r37;
        r36 = r0.substring(r1, r2);
        r35 = r35.append(r36);
        r36 = "\">..</a></b><br/>";
        r35 = r35.append(r36);
        r25 = r35.toString();
    L_0x0321:
        if (r15 == 0) goto L_0x04bf;
    L_0x0323:
        r19 = 0;
    L_0x0325:
        r0 = r15.length;
        r35 = r0;
        r0 = r19;
        r1 = r35;
        if (r0 >= r1) goto L_0x04bf;
    L_0x032e:
        r6 = new java.io.File;
        r35 = r15[r19];
        r0 = r35;
        r6.<init>(r14, r0);
        r7 = r6.isDirectory();
        if (r7 == 0) goto L_0x036b;
    L_0x033d:
        r35 = new java.lang.StringBuilder;
        r35.<init>();
        r0 = r35;
        r1 = r25;
        r35 = r0.append(r1);
        r36 = "<b>";
        r35 = r35.append(r36);
        r25 = r35.toString();
        r35 = new java.lang.StringBuilder;
        r35.<init>();
        r36 = r15[r19];
        r35 = r35.append(r36);
        r36 = "/";
        r35 = r35.append(r36);
        r35 = r35.toString();
        r15[r19] = r35;
    L_0x036b:
        r35 = new java.lang.StringBuilder;
        r35.<init>();
        r0 = r35;
        r1 = r25;
        r35 = r0.append(r1);
        r36 = "<a href=\"";
        r35 = r35.append(r36);
        r36 = new java.lang.StringBuilder;
        r36.<init>();
        r0 = r36;
        r1 = r41;
        r36 = r0.append(r1);
        r37 = r15[r19];
        r36 = r36.append(r37);
        r36 = r36.toString();
        r0 = r40;
        r1 = r36;
        r36 = r0.encodeUri(r1);
        r35 = r35.append(r36);
        r36 = "\">";
        r35 = r35.append(r36);
        r36 = r15[r19];
        r35 = r35.append(r36);
        r36 = "</a>";
        r35 = r35.append(r36);
        r25 = r35.toString();
        r35 = r6.isFile();
        if (r35 == 0) goto L_0x0414;
    L_0x03bd:
        r22 = r6.length();
        r35 = new java.lang.StringBuilder;
        r35.<init>();
        r0 = r35;
        r1 = r25;
        r35 = r0.append(r1);
        r36 = " &nbsp;<font size=2>(";
        r35 = r35.append(r36);
        r25 = r35.toString();
        r36 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r35 = (r22 > r36 ? 1 : (r22 == r36 ? 0 : -1));
        if (r35 >= 0) goto L_0x0448;
    L_0x03de:
        r35 = new java.lang.StringBuilder;
        r35.<init>();
        r0 = r35;
        r1 = r25;
        r35 = r0.append(r1);
        r0 = r35;
        r1 = r22;
        r35 = r0.append(r1);
        r36 = " bytes";
        r35 = r35.append(r36);
        r25 = r35.toString();
    L_0x03fd:
        r35 = new java.lang.StringBuilder;
        r35.<init>();
        r0 = r35;
        r1 = r25;
        r35 = r0.append(r1);
        r36 = ")</font>";
        r35 = r35.append(r36);
        r25 = r35.toString();
    L_0x0414:
        r35 = new java.lang.StringBuilder;
        r35.<init>();
        r0 = r35;
        r1 = r25;
        r35 = r0.append(r1);
        r36 = "<br/>";
        r35 = r35.append(r36);
        r25 = r35.toString();
        if (r7 == 0) goto L_0x0444;
    L_0x042d:
        r35 = new java.lang.StringBuilder;
        r35.<init>();
        r0 = r35;
        r1 = r25;
        r35 = r0.append(r1);
        r36 = "</b>";
        r35 = r35.append(r36);
        r25 = r35.toString();
    L_0x0444:
        r19 = r19 + 1;
        goto L_0x0325;
    L_0x0448:
        r36 = 1048576; // 0x100000 float:1.469368E-39 double:5.180654E-318;
        r35 = (r22 > r36 ? 1 : (r22 == r36 ? 0 : -1));
        if (r35 >= 0) goto L_0x0486;
    L_0x044f:
        r35 = new java.lang.StringBuilder;
        r35.<init>();
        r0 = r35;
        r1 = r25;
        r35 = r0.append(r1);
        r36 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r36 = r22 / r36;
        r35 = r35.append(r36);
        r36 = ".";
        r35 = r35.append(r36);
        r36 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r36 = r22 % r36;
        r38 = 10;
        r36 = r36 / r38;
        r38 = 100;
        r36 = r36 % r38;
        r35 = r35.append(r36);
        r36 = " KB";
        r35 = r35.append(r36);
        r25 = r35.toString();
        goto L_0x03fd;
    L_0x0486:
        r35 = new java.lang.StringBuilder;
        r35.<init>();
        r0 = r35;
        r1 = r25;
        r35 = r0.append(r1);
        r36 = 1048576; // 0x100000 float:1.469368E-39 double:5.180654E-318;
        r36 = r22 / r36;
        r35 = r35.append(r36);
        r36 = ".";
        r35 = r35.append(r36);
        r36 = 1048576; // 0x100000 float:1.469368E-39 double:5.180654E-318;
        r36 = r22 % r36;
        r38 = 10;
        r36 = r36 / r38;
        r38 = 100;
        r36 = r36 % r38;
        r35 = r35.append(r36);
        r36 = " MB";
        r35 = r35.append(r36);
        r25 = r35.toString();
        goto L_0x03fd;
    L_0x04bf:
        r35 = new java.lang.StringBuilder;
        r35.<init>();
        r0 = r35;
        r1 = r25;
        r35 = r0.append(r1);
        r36 = "</body></html>";
        r35 = r35.append(r36);
        r25 = r35.toString();
        r29 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r35 = "200 OK";
        r36 = "text/html";
        r0 = r29;
        r1 = r40;
        r2 = r35;
        r3 = r36;
        r4 = r25;
        r0.<init>(r2, r3, r4);
        r30 = r29;
        goto L_0x0155;
    L_0x04ed:
        r29 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r35 = "403 Forbidden";
        r36 = "text/plain";
        r37 = "FORBIDDEN: No directory listing.";
        r0 = r29;
        r1 = r40;
        r2 = r35;
        r3 = r36;
        r4 = r37;
        r0.<init>(r2, r3, r4);
        r30 = r29;
        goto L_0x0155;
    L_0x0506:
        r36 = 0;
        r35 = (r12 > r36 ? 1 : (r12 == r36 ? 0 : -1));
        if (r35 >= 0) goto L_0x0510;
    L_0x050c:
        r36 = 1;
        r12 = r16 - r36;
    L_0x0510:
        r36 = r12 - r32;
        r38 = 1;
        r26 = r36 + r38;
        r36 = 0;
        r35 = (r26 > r36 ? 1 : (r26 == r36 ? 0 : -1));
        if (r35 >= 0) goto L_0x051e;
    L_0x051c:
        r26 = 0;
    L_0x051e:
        r8 = r26;
        r18 = new com.google.appinventor.components.runtime.util.NanoHTTPD$2;	 Catch:{ IOException -> 0x062c }
        r0 = r18;
        r1 = r40;
        r0.<init>(r14, r8);	 Catch:{ IOException -> 0x062c }
        r0 = r18;
        r1 = r32;
        r0.skip(r1);	 Catch:{ IOException -> 0x062c }
        r29 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;	 Catch:{ IOException -> 0x062c }
        r35 = "206 Partial Content";
        r0 = r29;
        r1 = r40;
        r2 = r35;
        r3 = r21;
        r4 = r18;
        r0.<init>(r2, r3, r4);	 Catch:{ IOException -> 0x062c }
        r35 = "Content-Length";
        r36 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x05a8 }
        r36.<init>();	 Catch:{ IOException -> 0x05a8 }
        r37 = "";
        r36 = r36.append(r37);	 Catch:{ IOException -> 0x05a8 }
        r0 = r36;
        r36 = r0.append(r8);	 Catch:{ IOException -> 0x05a8 }
        r36 = r36.toString();	 Catch:{ IOException -> 0x05a8 }
        r0 = r29;
        r1 = r35;
        r2 = r36;
        r0.addHeader(r1, r2);	 Catch:{ IOException -> 0x05a8 }
        r35 = "Content-Range";
        r36 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x05a8 }
        r36.<init>();	 Catch:{ IOException -> 0x05a8 }
        r37 = "bytes ";
        r36 = r36.append(r37);	 Catch:{ IOException -> 0x05a8 }
        r0 = r36;
        r1 = r32;
        r36 = r0.append(r1);	 Catch:{ IOException -> 0x05a8 }
        r37 = "-";
        r36 = r36.append(r37);	 Catch:{ IOException -> 0x05a8 }
        r0 = r36;
        r36 = r0.append(r12);	 Catch:{ IOException -> 0x05a8 }
        r37 = "/";
        r36 = r36.append(r37);	 Catch:{ IOException -> 0x05a8 }
        r0 = r36;
        r1 = r16;
        r36 = r0.append(r1);	 Catch:{ IOException -> 0x05a8 }
        r36 = r36.toString();	 Catch:{ IOException -> 0x05a8 }
        r0 = r29;
        r1 = r35;
        r2 = r36;
        r0.addHeader(r1, r2);	 Catch:{ IOException -> 0x05a8 }
        r35 = "ETag";
        r0 = r29;
        r1 = r35;
        r0.addHeader(r1, r11);	 Catch:{ IOException -> 0x05a8 }
        goto L_0x0256;
    L_0x05a8:
        r20 = move-exception;
    L_0x05a9:
        r29 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;
        r35 = "403 Forbidden";
        r36 = "text/plain";
        r37 = "FORBIDDEN: Reading file failed.";
        r0 = r29;
        r1 = r40;
        r2 = r35;
        r3 = r36;
        r4 = r37;
        r0.<init>(r2, r3, r4);
        goto L_0x0256;
    L_0x05c0:
        r35 = "if-none-match";
        r0 = r42;
        r1 = r35;
        r35 = r0.getProperty(r1);	 Catch:{ IOException -> 0x062c }
        r0 = r35;
        r35 = r11.equals(r0);	 Catch:{ IOException -> 0x062c }
        if (r35 == 0) goto L_0x05e7;
    L_0x05d2:
        r29 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;	 Catch:{ IOException -> 0x062c }
        r35 = "304 Not Modified";
        r36 = "";
        r0 = r29;
        r1 = r40;
        r2 = r35;
        r3 = r21;
        r4 = r36;
        r0.<init>(r2, r3, r4);	 Catch:{ IOException -> 0x062c }
        goto L_0x0256;
    L_0x05e7:
        r29 = new com.google.appinventor.components.runtime.util.NanoHTTPD$Response;	 Catch:{ IOException -> 0x062c }
        r35 = "200 OK";
        r36 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x062c }
        r0 = r36;
        r0.<init>(r14);	 Catch:{ IOException -> 0x062c }
        r0 = r29;
        r1 = r40;
        r2 = r35;
        r3 = r21;
        r4 = r36;
        r0.<init>(r2, r3, r4);	 Catch:{ IOException -> 0x062c }
        r35 = "Content-Length";
        r36 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x05a8 }
        r36.<init>();	 Catch:{ IOException -> 0x05a8 }
        r37 = "";
        r36 = r36.append(r37);	 Catch:{ IOException -> 0x05a8 }
        r0 = r36;
        r1 = r16;
        r36 = r0.append(r1);	 Catch:{ IOException -> 0x05a8 }
        r36 = r36.toString();	 Catch:{ IOException -> 0x05a8 }
        r0 = r29;
        r1 = r35;
        r2 = r36;
        r0.addHeader(r1, r2);	 Catch:{ IOException -> 0x05a8 }
        r35 = "ETag";
        r0 = r29;
        r1 = r35;
        r0.addHeader(r1, r11);	 Catch:{ IOException -> 0x05a8 }
        goto L_0x0256;
    L_0x062c:
        r20 = move-exception;
        r29 = r30;
        goto L_0x05a9;
    L_0x0631:
        r35 = move-exception;
        goto L_0x0206;
    L_0x0634:
        r29 = r30;
        goto L_0x0256;
    L_0x0638:
        r30 = r29;
        goto L_0x0155;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.util.NanoHTTPD.serveFile(java.lang.String, java.util.Properties, java.io.File, boolean):com.google.appinventor.components.runtime.util.NanoHTTPD$Response");
    }

    static {
        theMimeTypes = new Hashtable();
        StringTokenizer st = new StringTokenizer("css            text/css htm            text/html html           text/html xml            text/xml txt            text/plain asc            text/plain gif            image/gif jpg            image/jpeg jpeg           image/jpeg png            image/png mp3            audio/mpeg m3u            audio/mpeg-url mp4            video/mp4 ogv            video/ogg flv            video/x-flv mov            video/quicktime swf            application/x-shockwave-flash js                     application/javascript pdf            application/pdf doc            application/msword ogg            application/x-ogg zip            application/octet-stream exe            application/octet-stream class          application/octet-stream ");
        while (st.hasMoreTokens()) {
            theMimeTypes.put(st.nextToken(), st.nextToken());
        }
        theBufferSize = ModuleExp.EXPORT_SPECIFIED;
        myOut = System.out;
        myErr = System.err;
        gmtFrmt = new SimpleDateFormat("E, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
        gmtFrmt.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
}
