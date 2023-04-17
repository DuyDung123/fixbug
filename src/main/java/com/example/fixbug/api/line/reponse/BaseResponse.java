package com.example.fixbug.api.line.reponse;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {
    private String result;
    @SerializedName("error_code")
    private Object errorCode;
    @SerializedName("error_message")
    private String errorMessage;

    public String getResult() {
        return result;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
