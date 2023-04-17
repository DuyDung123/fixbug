package com.example.fixbug.patternbridge;

public class ConsoleLogger implements MessageLogger {
    @Override
    public void log(String msg) {
        System.out.println(msg);
    }
}
