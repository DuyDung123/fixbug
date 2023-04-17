package com.example.fixbug.patternbridge.demo;

import com.example.fixbug.patternbridge.*;

public class DemoBridge {

    // chọn log kiểu show ra console
    public MessageLogger messageLogger = new ConsoleLogger();

    // chọn cách thức hiển thị kiểu mã hóa
    Message encryptedMessage = new EncryptedMessage(messageLogger);

    Message textMessage = new TextMessage(messageLogger);

    public Message getEncryptedMessage() {
        return encryptedMessage;
    }

    public Message getTextMessage() {
        return textMessage;
    }
}
