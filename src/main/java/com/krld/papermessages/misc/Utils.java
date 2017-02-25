package com.krld.papermessages.misc;

import com.krld.papermessages.api.errors.ApiError;
import io.vertx.ext.web.RoutingContext;

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

    public static boolean isEmpty(String s) {
        return s == null || s.isEmpty();
    }

    public static void fail(RoutingContext context, ApiError error) {
        context.put(Constants.EXTRA_ERROR, error);
        context.fail(400);
    }
}
