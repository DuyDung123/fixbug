package com.example.fixbug.patternbridge;

import java.io.FileWriter;

public class FileLogger implements MessageLogger {
    @Override
    public void log(String msg) {
        // viet ham ghi ra file log.txt
        try {
            FileWriter fw = new FileWriter("log/log.txt", true);
            fw.append((char) 10);
            fw.write(msg);
            fw.close();
        } catch (Exception ex) {
            System.out.println("loi log trong FileLogger: " + ex);
        }
    }
}
