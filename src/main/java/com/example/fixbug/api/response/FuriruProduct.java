package com.example.fixbug.api.response;

import com.google.gson.annotations.SerializedName;

public class FuriruProduct {

    @SerializedName("item_result")
    private Boolean itemResult;

    @SerializedName("item_id")
    private String itemId;

    @SerializedName("error_message")
    private String errorMessage;

    public Boolean getItemResult() {
        return itemResult;
    }

    public String getItemId() {
        return itemId;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
