package com.michael.p2p.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for logging messages with a timestamp.
 */
public class Logger {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void log(String message) {
        String timestamp = sdf.format(new Date());
        System.out.println(timestamp + " [" + Thread.currentThread().getName() + "] " + message);
    }
}