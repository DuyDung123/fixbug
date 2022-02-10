package com.example.fixbug.api.mail.response;

import java.util.List;

public class ListEmailResponse {
    private List<Message> messages;
    private int resultSizeEstimate;

    public List<Message> getMessages() {
        return messages;
    }

    public int getResultSizeEstimate() {
        return resultSizeEstimate;
    }

    public static class Message{
        private String id;
        private String threadId;

        public String getId() {
            return id;
        }

        public String getThreadId() {
            return threadId;
        }
    }

}
