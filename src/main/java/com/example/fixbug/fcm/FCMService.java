package com.example.fixbug.fcm;

import com.google.firebase.messaging.*;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class FCMService {
    public String pushNotification(FirebaseMessagingObject pnsRequest) {
        Message message = Message.builder()
                .putData("content", "dũng test hello anh test = topic anh có nhận được thì trả lời em")
                .putData("title", "title")
                .setTopic("dev.dominator.user_799")
                .build();

//                Message message = Message.builder()
//                .putData("content", "dũng test hello anh test = token anh có nhận được thì trả lời em")
//                .putData("title", "title")
//                .setToken("dsqucyae3E7CrEqRBaG_WQ:APA91bFhqgoPQUZzMtRjy-WQhx2NoH0uS0JIxMbF1nF3O_iY-_2i3C3WTQ5sZKTQoe1kVAfoirCxoDrRZ8twaFKNyDKIkVE3sOmY15NTE3yPNjR_w2nUS4dv8CHLIXIW1vvIaYn5MFQa")
//                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
        return response;
    }

    public String sendNotification(){

        FirebaseMessagingObject fcmObject = new FirebaseMessagingObject(2, new FirebaseMessagingObject.Data(111));

        Notification notification = Notification
                .builder()
                .setTitle("売れました")
                .setBody("dũng gửi thông báo cho anh thịnh test")
                .build();

        // for iOS
        Aps aps = Aps.builder()
                .setSound("default")
                .setBadge(15)
                .build();

        ApnsConfig apnsConfig = ApnsConfig.builder()
                .setAps(aps)
                .build();

        // for Android
        AndroidNotification androidNofi = AndroidNotification.builder()
                .setSound("default")
                .build();

        AndroidConfig androidConfig = AndroidConfig.builder()
                .setNotification(androidNofi)
                .build();

        Message message = Message
                .builder()
                .setTopic("dev.dominator.user_799")//260/799
                .setNotification(notification)
                .setAndroidConfig(androidConfig)
                .setApnsConfig(apnsConfig)
                .putData("type", fcmObject.getType() +"")
                .putData("data", new Gson().toJson(fcmObject.getData()))
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Successfully sent message: " + response);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
        return response;
    }
}
//                .putData("data", "{\"id\": 111}")