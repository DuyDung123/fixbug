package com.example.fixbug.api.instagram.apiservice.response;

import com.example.fixbug.api.instagram.apiservice.BaseInstagramResponse;
import com.google.gson.annotations.SerializedName;

public class SendMessagesResponse extends BaseInstagramResponse {
    @SerializedName("recipient_id")
    private String recipientId;

    @SerializedName("message_id")
    private String messageId;

    public String getRecipientId() {
        return recipientId;
    }

    public String getMessageId() {
        return messageId;
    }
}
