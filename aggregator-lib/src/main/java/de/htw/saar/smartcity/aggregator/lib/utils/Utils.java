package de.htw.saar.smartcity.aggregator.lib.utils;

public final class Utils {

    private Utils() { }

    public static boolean isBlankOrNull(String str) {
        return (str == null || "".equals(str.trim()));
    }

    public static String limitLoggedMsg(String loggedMsg, Integer maxSize) {

        String limited = loggedMsg;
        if(limited.length() > maxSize) {
            limited = loggedMsg.substring(0, maxSize-3);
            limited += "...";
        }
        return limited;
    }
}
