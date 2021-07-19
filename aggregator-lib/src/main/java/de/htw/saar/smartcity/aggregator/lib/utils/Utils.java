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

    public static Double convertToDouble(Object o) {
        Double val = null;
        if (o instanceof Number) {
            val = ((Number) o).doubleValue();
        }
        if(o instanceof Boolean) {
            val = ((Boolean) o) ? 1.0 : 0.0;
        }
        try {
            if (o instanceof String) {
                val = Double.valueOf((String) o);
            }
        } catch (NumberFormatException nfe) { }

        return val;
    }
}
