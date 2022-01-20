package com.example.fixbug.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private static final org.apache.log4j.Logger log4j = org.apache.log4j.LogManager.getLogger(Logger.class);


    private static Logger logger;

    public static void info(String message) {
        log4j.info(message);
    }

    public static void info(String tag, String message) {
        log4j.info(getLogMessage(tag, message));
    }

    public static void debug(String tag, String message) {
        log4j.debug(message);
    }

    public static void warning(String message) {
        warning(null, message);
    }

    public static void warning(String tag, String message) {
        log4j.warn(getLogMessage(tag, message));
    }

    public static void d(String tag, String message) {
        log4j.info(getLogMessage(tag, message));
    }

    public static void d(String tag, String message, Throwable throwable) {
        log4j.info(getLogMessage(tag, message), throwable);
    }

    public static void error(String message) {
        error(null, message);
    }

    public static void error(String message, Throwable throwable) {
        log4j.error(getLogMessage(null, message), throwable);
    }

    public static void error(String tag, String message) {
        log4j.error(getLogMessage(tag, message));
    }


    public static String getLogMessage(String tag, String message) {
        return String.format("%s - %s", tag, message);
    }

    public static synchronized void setup() {
        if (logger == null) {
            logger = new Logger();
        }
    }


    private Logger() {
        //no instance
    }


    private String getCurrentTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return formatter.format(new Date());
    }

}
