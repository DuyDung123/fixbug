package com.example.fixbug.patternbridge;

public class TextMessage extends Message {

    public TextMessage() {
    }

    public TextMessage(MessageLogger messageLogger) {
        super(messageLogger);
    }

    public MessageLogger getMessageLogger() {
        return messageLogger;
    }

    public void setMessageLogger(MessageLogger messageLogger) {
        this.messageLogger = messageLogger;
    }

    public String preProcess(String str) {
        return str;
    }

    @Override
    public void log(String msg) {
        this.messageLogger.log(preProcess(msg));
    }
}
