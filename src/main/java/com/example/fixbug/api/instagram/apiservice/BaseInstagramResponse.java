package com.example.fixbug.api.instagram.apiservice;

import com.google.gson.annotations.SerializedName;

public class BaseInstagramResponse {
    private Error error;

    public Error getError() {
        return error;
    }

    public static class Error {
        private String message;

        private String type;

        private int code;

        @SerializedName("error_subcode")
        private int errorSubCode;

        @SerializedName("fbtrace_id")
        private String fbTraceId;

        public String getMessage() {
            return message;
        }

        public String getType() {
            return type;
        }

        public int getCode() {
            return code;
        }

        public int getErrorSubCode() {
            return errorSubCode;
        }

        public String getFbTraceId() {
            return fbTraceId;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
