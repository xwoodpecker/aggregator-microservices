package de.htw.saar.smartcity.aggregator.utils;

public final class Utils {

    private Utils() { }

    public static boolean isBlankOrNull(String str) {
        return (str == null || "".equals(str.trim()));
    }
}
