package com.example.fixbug.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class FCMService {
    public String pushNotification(PnsRequest pnsRequest) {
        Message message = Message.builder()
                .putData("score", "850")
                .putData("time", "2:45")
                .putData("content", "hello")
                .setToken("")
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
        return response;
    }
}
