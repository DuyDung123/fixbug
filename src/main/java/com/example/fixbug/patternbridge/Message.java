package com.example.fixbug.patternbridge;

public abstract class Message {
    protected MessageLogger messageLogger;

    public Message() {
    }

    public Message(MessageLogger messageLogger) {
        this.messageLogger = messageLogger;
    }

    public abstract void log(String msg);
}
