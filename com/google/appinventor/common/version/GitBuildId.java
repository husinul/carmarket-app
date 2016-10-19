package com.google.appinventor.common.version;

import gnu.kawa.xml.ElementType;

public final class GitBuildId {
    public static final String ACRA_URI = "${acra.uri}";
    public static final String ANT_BUILD_DATE = "September 13 2016";
    public static final String GIT_BUILD_FINGERPRINT = "bb69cf126226d13c0be8c6155f4b6b8906e34669";
    public static final String GIT_BUILD_VERSION = "nb151";

    private GitBuildId() {
    }

    public static String getVersion() {
        String version = GIT_BUILD_VERSION;
        if (version == ElementType.MATCH_ANY_LOCALNAME || version.contains(" ")) {
            return "none";
        }
        return version;
    }

    public static String getFingerprint() {
        return GIT_BUILD_FINGERPRINT;
    }

    public static String getDate() {
        return ANT_BUILD_DATE;
    }

    public static String getAcraUri() {
        if (ACRA_URI.equals(ACRA_URI)) {
            return ElementType.MATCH_ANY_LOCALNAME;
        }
        return ACRA_URI.trim();
    }
}
