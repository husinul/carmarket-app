package com.google.appinventor.components.common;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlEntities {
    private static final Pattern HTML_ENTITY_PATTERN;
    private static final Map<String, Character> lookup;

    static {
        HTML_ENTITY_PATTERN = Pattern.compile("&(#?[0-9a-zA-Z]+);");
        lookup = new HashMap();
        lookup.put("Agrave", Character.valueOf('\u00c0'));
        lookup.put("agrave", Character.valueOf('\u00e0'));
        lookup.put("Aacute", Character.valueOf('\u00c1'));
        lookup.put("aacute", Character.valueOf('\u00e1'));
        lookup.put("Acirc", Character.valueOf('\u00c2'));
        lookup.put("acirc", Character.valueOf('\u00e2'));
        lookup.put("Atilde", Character.valueOf('\u00c3'));
        lookup.put("atilde", Character.valueOf('\u00e3'));
        lookup.put("Auml", Character.valueOf('\u00c4'));
        lookup.put("auml", Character.valueOf('\u00e4'));
        lookup.put("Aring", Character.valueOf('\u00c5'));
        lookup.put("aring", Character.valueOf('\u00e5'));
        lookup.put("AElig", Character.valueOf('\u00c6'));
        lookup.put("aelig", Character.valueOf('\u00e6'));
        lookup.put("Ccedil", Character.valueOf('\u00c7'));
        lookup.put("ccedil", Character.valueOf('\u00e7'));
        lookup.put("Egrave", Character.valueOf('\u00c8'));
        lookup.put("egrave", Character.valueOf('\u00e8'));
        lookup.put("Eacute", Character.valueOf('\u00c9'));
        lookup.put("eacute", Character.valueOf('\u00e9'));
        lookup.put("Ecirc", Character.valueOf('\u00ca'));
        lookup.put("ecirc", Character.valueOf('\u00ea'));
        lookup.put("Euml", Character.valueOf('\u00cb'));
        lookup.put("euml", Character.valueOf('\u00eb'));
        lookup.put("Igrave", Character.valueOf('\u00cc'));
        lookup.put("igrave", Character.valueOf('\u00ec'));
        lookup.put("Iacute", Character.valueOf('\u00cd'));
        lookup.put("iacute", Character.valueOf('\u00ed'));
        lookup.put("Icirc", Character.valueOf('\u00ce'));
        lookup.put("icirc", Character.valueOf('\u00ee'));
        lookup.put("Iuml", Character.valueOf('\u00cf'));
        lookup.put("iuml", Character.valueOf('\u00ef'));
        lookup.put("ETH", Character.valueOf('\u00d0'));
        lookup.put("eth", Character.valueOf('\u00f0'));
        lookup.put("Ntilde", Character.valueOf('\u00d1'));
        lookup.put("ntilde", Character.valueOf('\u00f1'));
        lookup.put("Ograve", Character.valueOf('\u00d2'));
        lookup.put("ograve", Character.valueOf('\u00f2'));
        lookup.put("Oacute", Character.valueOf('\u00d3'));
        lookup.put("oacute", Character.valueOf('\u00f3'));
        lookup.put("Ocirc", Character.valueOf('\u00d4'));
        lookup.put("ocirc", Character.valueOf('\u00f4'));
        lookup.put("Otilde", Character.valueOf('\u00d5'));
        lookup.put("otilde", Character.valueOf('\u00f5'));
        lookup.put("Ouml", Character.valueOf('\u00d6'));
        lookup.put("ouml", Character.valueOf('\u00f6'));
        lookup.put("Oslash", Character.valueOf('\u00d8'));
        lookup.put("oslash", Character.valueOf('\u00f8'));
        lookup.put("Ugrave", Character.valueOf('\u00d9'));
        lookup.put("ugrave", Character.valueOf('\u00f9'));
        lookup.put("Uacute", Character.valueOf('\u00da'));
        lookup.put("uacute", Character.valueOf('\u00fa'));
        lookup.put("Ucirc", Character.valueOf('\u00db'));
        lookup.put("ucirc", Character.valueOf('\u00fb'));
        lookup.put("Uuml", Character.valueOf('\u00dc'));
        lookup.put("uuml", Character.valueOf('\u00fc'));
        lookup.put("Yacute", Character.valueOf('\u00dd'));
        lookup.put("yacute", Character.valueOf('\u00fd'));
        lookup.put("THORN", Character.valueOf('\u00de'));
        lookup.put("thorn", Character.valueOf('\u00fe'));
        lookup.put("szlig", Character.valueOf('\u00df'));
        lookup.put("yuml", Character.valueOf('\u00ff'));
        lookup.put("Yuml", Character.valueOf('\u0178'));
        lookup.put("OElig", Character.valueOf('\u0152'));
        lookup.put("oelig", Character.valueOf('\u0153'));
        lookup.put("Scaron", Character.valueOf('\u0160'));
        lookup.put("scaron", Character.valueOf('\u0161'));
        lookup.put("Alpha", Character.valueOf('\u0391'));
        lookup.put("Beta", Character.valueOf('\u0392'));
        lookup.put("Gamma", Character.valueOf('\u0393'));
        lookup.put("Delta", Character.valueOf('\u0394'));
        lookup.put("Epsilon", Character.valueOf('\u0395'));
        lookup.put("Zeta", Character.valueOf('\u0396'));
        lookup.put("Eta", Character.valueOf('\u0397'));
        lookup.put("Theta", Character.valueOf('\u0398'));
        lookup.put("Iota", Character.valueOf('\u0399'));
        lookup.put("Kappa", Character.valueOf('\u039a'));
        lookup.put("Lambda", Character.valueOf('\u039b'));
        lookup.put("Mu", Character.valueOf('\u039c'));
        lookup.put("Nu", Character.valueOf('\u039d'));
        lookup.put("Xi", Character.valueOf('\u039e'));
        lookup.put("Omicron", Character.valueOf('\u039f'));
        lookup.put("Pi", Character.valueOf('\u03a0'));
        lookup.put("Rho", Character.valueOf('\u03a1'));
        lookup.put("Sigma", Character.valueOf('\u03a3'));
        lookup.put("Tau", Character.valueOf('\u03a4'));
        lookup.put("Upsilon", Character.valueOf('\u03a5'));
        lookup.put("Phi", Character.valueOf('\u03a6'));
        lookup.put("Chi", Character.valueOf('\u03a7'));
        lookup.put("Psi", Character.valueOf('\u03a8'));
        lookup.put("Omega", Character.valueOf('\u03a9'));
        lookup.put("alpha", Character.valueOf('\u03b1'));
        lookup.put("beta", Character.valueOf('\u03b2'));
        lookup.put("gamma", Character.valueOf('\u03b3'));
        lookup.put("delta", Character.valueOf('\u03b4'));
        lookup.put("epsilon", Character.valueOf('\u03b5'));
        lookup.put("zeta", Character.valueOf('\u03b6'));
        lookup.put("eta", Character.valueOf('\u03b7'));
        lookup.put("theta", Character.valueOf('\u03b8'));
        lookup.put("iota", Character.valueOf('\u03b9'));
        lookup.put("kappa", Character.valueOf('\u03ba'));
        lookup.put("lambda", Character.valueOf('\u03bb'));
        lookup.put("mu", Character.valueOf('\u03bc'));
        lookup.put("nu", Character.valueOf('\u03bd'));
        lookup.put("xi", Character.valueOf('\u03be'));
        lookup.put("omicron", Character.valueOf('\u03bf'));
        lookup.put("pi", Character.valueOf('\u03c0'));
        lookup.put("rho", Character.valueOf('\u03c1'));
        lookup.put("sigmaf", Character.valueOf('\u03c2'));
        lookup.put("sigma", Character.valueOf('\u03c3'));
        lookup.put("tau", Character.valueOf('\u03c4'));
        lookup.put("upsilon", Character.valueOf('\u03c5'));
        lookup.put("phi", Character.valueOf('\u03c6'));
        lookup.put("chi", Character.valueOf('\u03c7'));
        lookup.put("psi", Character.valueOf('\u03c8'));
        lookup.put("omega", Character.valueOf('\u03c9'));
        lookup.put("thetasym", Character.valueOf('\u03d1'));
        lookup.put("upsih", Character.valueOf('\u03d2'));
        lookup.put("piv", Character.valueOf('\u03d6'));
        lookup.put("iexcl", Character.valueOf('\u00a1'));
        lookup.put("cent", Character.valueOf('\u00a2'));
        lookup.put("pound", Character.valueOf('\u00a3'));
        lookup.put("curren", Character.valueOf('\u00a4'));
        lookup.put("yen", Character.valueOf('\u00a5'));
        lookup.put("brvbar", Character.valueOf('\u00a6'));
        lookup.put("sect", Character.valueOf('\u00a7'));
        lookup.put("uml", Character.valueOf('\u00a8'));
        lookup.put("copy", Character.valueOf('\u00a9'));
        lookup.put("ordf", Character.valueOf('\u00aa'));
        lookup.put("laquo", Character.valueOf('\u00ab'));
        lookup.put("not", Character.valueOf('\u00ac'));
        lookup.put("shy", Character.valueOf('\u00ad'));
        lookup.put("reg", Character.valueOf('\u00ae'));
        lookup.put("macr", Character.valueOf('\u00af'));
        lookup.put("deg", Character.valueOf('\u00b0'));
        lookup.put("plusmn", Character.valueOf('\u00b1'));
        lookup.put("sup2", Character.valueOf('\u00b2'));
        lookup.put("sup3", Character.valueOf('\u00b3'));
        lookup.put("acute", Character.valueOf('\u00b4'));
        lookup.put("micro", Character.valueOf('\u00b5'));
        lookup.put("para", Character.valueOf('\u00b6'));
        lookup.put("middot", Character.valueOf('\u00b7'));
        lookup.put("cedil", Character.valueOf('\u00b8'));
        lookup.put("sup1", Character.valueOf('\u00b9'));
        lookup.put("ordm", Character.valueOf('\u00ba'));
        lookup.put("raquo", Character.valueOf('\u00bb'));
        lookup.put("frac14", Character.valueOf('\u00bc'));
        lookup.put("frac12", Character.valueOf('\u00bd'));
        lookup.put("frac34", Character.valueOf('\u00be'));
        lookup.put("iquest", Character.valueOf('\u00bf'));
        lookup.put("times", Character.valueOf('\u00d7'));
        lookup.put("divide", Character.valueOf('\u00f7'));
        lookup.put("fnof", Character.valueOf('\u0192'));
        lookup.put("circ", Character.valueOf('\u02c6'));
        lookup.put("tilde", Character.valueOf('\u02dc'));
        lookup.put("lrm", Character.valueOf('\u200e'));
        lookup.put("rlm", Character.valueOf('\u200f'));
        lookup.put("ndash", Character.valueOf('\u2013'));
        lookup.put("endash", Character.valueOf('\u2013'));
        lookup.put("mdash", Character.valueOf('\u2014'));
        lookup.put("emdash", Character.valueOf('\u2014'));
        lookup.put("lsquo", Character.valueOf('\u2018'));
        lookup.put("rsquo", Character.valueOf('\u2019'));
        lookup.put("sbquo", Character.valueOf('\u201a'));
        lookup.put("ldquo", Character.valueOf('\u201c'));
        lookup.put("rdquo", Character.valueOf('\u201d'));
        lookup.put("bdquo", Character.valueOf('\u201e'));
        lookup.put("dagger", Character.valueOf('\u2020'));
        lookup.put("Dagger", Character.valueOf('\u2021'));
        lookup.put("bull", Character.valueOf('\u2022'));
        lookup.put("hellip", Character.valueOf('\u2026'));
        lookup.put("permil", Character.valueOf('\u2030'));
        lookup.put("prime", Character.valueOf('\u2032'));
        lookup.put("Prime", Character.valueOf('\u2033'));
        lookup.put("lsaquo", Character.valueOf('\u2039'));
        lookup.put("rsaquo", Character.valueOf('\u203a'));
        lookup.put("oline", Character.valueOf('\u203e'));
        lookup.put("frasl", Character.valueOf('\u2044'));
        lookup.put("euro", Character.valueOf('\u20ac'));
        lookup.put("image", Character.valueOf('\u2111'));
        lookup.put("weierp", Character.valueOf('\u2118'));
        lookup.put("real", Character.valueOf('\u211c'));
        lookup.put("trade", Character.valueOf('\u2122'));
        lookup.put("alefsym", Character.valueOf('\u2135'));
        lookup.put("larr", Character.valueOf('\u2190'));
        lookup.put("uarr", Character.valueOf('\u2191'));
        lookup.put("rarr", Character.valueOf('\u2192'));
        lookup.put("darr", Character.valueOf('\u2193'));
        lookup.put("harr", Character.valueOf('\u2194'));
        lookup.put("crarr", Character.valueOf('\u21b5'));
        lookup.put("lArr", Character.valueOf('\u21d0'));
        lookup.put("uArr", Character.valueOf('\u21d1'));
        lookup.put("rArr", Character.valueOf('\u21d2'));
        lookup.put("dArr", Character.valueOf('\u21d3'));
        lookup.put("hArr", Character.valueOf('\u21d4'));
        lookup.put("forall", Character.valueOf('\u2200'));
        lookup.put("part", Character.valueOf('\u2202'));
        lookup.put("exist", Character.valueOf('\u2203'));
        lookup.put("empty", Character.valueOf('\u2205'));
        lookup.put("nabla", Character.valueOf('\u2207'));
        lookup.put("isin", Character.valueOf('\u2208'));
        lookup.put("notin", Character.valueOf('\u2209'));
        lookup.put("ni", Character.valueOf('\u220b'));
        lookup.put("prod", Character.valueOf('\u220f'));
        lookup.put("sum", Character.valueOf('\u2211'));
        lookup.put("minus", Character.valueOf('\u2212'));
        lookup.put("lowast", Character.valueOf('\u2217'));
        lookup.put("radic", Character.valueOf('\u221a'));
        lookup.put("prop", Character.valueOf('\u221d'));
        lookup.put("infin", Character.valueOf('\u221e'));
        lookup.put("ang", Character.valueOf('\u2220'));
        lookup.put("and", Character.valueOf('\u2227'));
        lookup.put("or", Character.valueOf('\u2228'));
        lookup.put("cap", Character.valueOf('\u2229'));
        lookup.put("cup", Character.valueOf('\u222a'));
        lookup.put("int", Character.valueOf('\u222b'));
        lookup.put("there4", Character.valueOf('\u2234'));
        lookup.put("sim", Character.valueOf('\u223c'));
        lookup.put("cong", Character.valueOf('\u2245'));
        lookup.put("asymp", Character.valueOf('\u2248'));
        lookup.put("ne", Character.valueOf('\u2260'));
        lookup.put("equiv", Character.valueOf('\u2261'));
        lookup.put("le", Character.valueOf('\u2264'));
        lookup.put("ge", Character.valueOf('\u2265'));
        lookup.put("sub", Character.valueOf('\u2282'));
        lookup.put("sup", Character.valueOf('\u2283'));
        lookup.put("nsub", Character.valueOf('\u2284'));
        lookup.put("sube", Character.valueOf('\u2286'));
        lookup.put("supe", Character.valueOf('\u2287'));
        lookup.put("oplus", Character.valueOf('\u2295'));
        lookup.put("otimes", Character.valueOf('\u2297'));
        lookup.put("perp", Character.valueOf('\u22a5'));
        lookup.put("sdot", Character.valueOf('\u22c5'));
        lookup.put("lceil", Character.valueOf('\u2308'));
        lookup.put("rceil", Character.valueOf('\u2309'));
        lookup.put("lfloor", Character.valueOf('\u230a'));
        lookup.put("rfloor", Character.valueOf('\u230b'));
        lookup.put("lang", Character.valueOf('\u2329'));
        lookup.put("rang", Character.valueOf('\u232a'));
        lookup.put("loz", Character.valueOf('\u25ca'));
        lookup.put("spades", Character.valueOf('\u2660'));
        lookup.put("clubs", Character.valueOf('\u2663'));
        lookup.put("hearts", Character.valueOf('\u2665'));
        lookup.put("diams", Character.valueOf('\u2666'));
        lookup.put("gt", Character.valueOf('>'));
        lookup.put("GT", Character.valueOf('>'));
        lookup.put("lt", Character.valueOf('<'));
        lookup.put("LT", Character.valueOf('<'));
        lookup.put("quot", Character.valueOf('\"'));
        lookup.put("QUOT", Character.valueOf('\"'));
        lookup.put("amp", Character.valueOf('&'));
        lookup.put("AMP", Character.valueOf('&'));
        lookup.put("apos", Character.valueOf('\''));
        lookup.put("nbsp", Character.valueOf('\u00a0'));
        lookup.put("ensp", Character.valueOf('\u2002'));
        lookup.put("emsp", Character.valueOf('\u2003'));
        lookup.put("thinsp", Character.valueOf('\u2009'));
        lookup.put("zwnj", Character.valueOf('\u200c'));
        lookup.put("zwj", Character.valueOf('\u200d'));
    }

    public static Character toCharacter(String entityName) {
        return (Character) lookup.get(entityName);
    }

    public static String decodeHtmlText(String htmlText) {
        if (htmlText.length() == 0 || htmlText.indexOf(38) == -1) {
            return htmlText;
        }
        StringBuilder output = new StringBuilder();
        int lastMatchEnd = 0;
        Matcher matcher = HTML_ENTITY_PATTERN.matcher(htmlText);
        while (matcher.find()) {
            String entity = matcher.group(1);
            Character convertedEntity = null;
            if (entity.startsWith("#x")) {
                String hhhh = entity.substring(2);
                try {
                    System.out.println("hex number is " + hhhh);
                    convertedEntity = Character.valueOf((char) Integer.parseInt(hhhh, 16));
                } catch (NumberFormatException e) {
                }
            } else if (entity.startsWith("#")) {
                try {
                    convertedEntity = Character.valueOf((char) Integer.parseInt(entity.substring(1)));
                } catch (NumberFormatException e2) {
                }
            } else {
                convertedEntity = (Character) lookup.get(entity);
            }
            if (convertedEntity != null) {
                output.append(htmlText.substring(lastMatchEnd, matcher.start()));
                output.append(convertedEntity);
                lastMatchEnd = matcher.end();
            }
        }
        if (lastMatchEnd < htmlText.length()) {
            output.append(htmlText.substring(lastMatchEnd));
        }
        return output.toString();
    }
}
