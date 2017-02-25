package com.krld.papermessages.misc;

import java.util.Random;

public class Utils {
    private static final char[] subset =
            "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final Random prng = new Random();

    public static String randomString(final int length) {
        char[] chars = new char[length];
        final int subsetLength = subset.length;
        for (int i = 0; i < length; i++) {
            int index = prng.nextInt(subsetLength);
            chars[i] = subset[index];
        }
        return new String(chars);
    }
}
