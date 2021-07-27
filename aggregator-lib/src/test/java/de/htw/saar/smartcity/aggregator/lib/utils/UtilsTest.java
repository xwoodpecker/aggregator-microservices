package de.htw.saar.smartcity.aggregator.lib.utils;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilsTest {

    @Test
    public void testIsBlankOrNull() {
        assertTrue(Utils.isBlankOrNull(""));
        assertTrue(Utils.isBlankOrNull(" "));
        assertTrue(Utils.isBlankOrNull("    "));
        assertTrue(Utils.isBlankOrNull(null));
    }

    @Test
    public void testLimitLoggedMsg() {
        assertEquals(Utils.limitLoggedMsg(randomString(101), 100).length(), 100);
        assertEquals(Utils.limitLoggedMsg(randomString(100), 50).length(), 50);
        assertEquals(Utils.limitLoggedMsg(randomString(50), 100).length(), 50);
    }

    @Test
    public void test() {
        assertEquals((Utils.convertToDouble(23.0)).getClass(), Double.class);
        assertEquals((Utils.convertToDouble(23L)).getClass(), Double.class);
        assertEquals((Utils.convertToDouble(23)).getClass(), Double.class);
        assertEquals((Utils.convertToDouble("23")).getClass(), Double.class);
        assertEquals((Utils.convertToDouble("23.5")).getClass(), Double.class);
        assertEquals((Utils.convertToDouble("0")).getClass(), Double.class);
        assertEquals((Utils.convertToDouble(false)).getClass(), Double.class);
        assertEquals((Utils.convertToDouble(true)).getClass(), Double.class);
        assertEquals(Utils.convertToDouble("false"), null);
        assertEquals(Utils.convertToDouble("cat"), null);
        assertEquals(Utils.convertToDouble(null), null);
    }

    private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static Random rnd = new Random();

    private static String randomString(int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
    }
}
