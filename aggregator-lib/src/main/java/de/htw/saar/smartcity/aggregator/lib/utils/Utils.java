package de.htw.saar.smartcity.aggregator.lib.utils;

/**
 * The type Utils.
 */
public final class Utils {

    private Utils() { }

    /**
     * Is blank or null boolean.
     *
     * @param str the str
     * @return the boolean
     */
    public static boolean isBlankOrNull(String str) {
        return (str == null || "".equals(str.trim()));
    }

    /**
     * Limit logged msg string.
     *
     * @param loggedMsg the logged msg
     * @param maxSize   the max size
     * @return the string
     */
    public static String limitLoggedMsg(String loggedMsg, Integer maxSize) {

        String limited = loggedMsg;
        if(limited.length() > maxSize) {
            limited = loggedMsg.substring(0, maxSize-3);
            limited += "...";
        }
        return limited;
    }

    /**
     * Convert to double double.
     *
     * @param o the o
     * @return the double
     */
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
