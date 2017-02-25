package com.krld.papermessages.misc;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FLog {

    private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

    private static String timestamp() {
        return format.format(new Date(System.currentTimeMillis()));
    }

    public static void d(String s) {
        System.out.println(timestamp() + " " + s);
    }

    public static void e(String s) {
        System.err.println(timestamp() + " " + s);
    }
}
