package com.example.fixbug.api.instagram.apiservice.requestbody;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SendQuickReply {
    private Recipient recipient;

    @SerializedName("messaging_type")
    public String messagingType;

    private Message message;

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public void setMessagingType(String messagingType) {
        this.messagingType = messagingType;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public static class Message{
        private String text;

        @SerializedName("quick_replies")
        private List<QuickReply> quickReplies;

        public void setText(String text) {
            this.text = text;
        }

        public void setQuickReplies(List<QuickReply> quickReplies) {
            this.quickReplies = quickReplies;
        }
    }

    public static class QuickReply{
        @SerializedName("content_type")
        private String contentType;

        private String title;

        private String payload;

        public void setContentType(String contentType) {
            this.contentType = contentType;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setPayload(String payload) {
            this.payload = payload;
        }
    }

    public static class Recipient{
        private String id;

        public void setId(String id) {
            this.id = id;
        }
    }
}
