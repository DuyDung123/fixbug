package com.example.fixbug.api.instagram.apiservice.response;

import com.example.fixbug.api.instagram.apiservice.BaseInstagramResponse;
import com.google.gson.annotations.SerializedName;


import java.util.Date;
import java.util.List;

public class ListConversationsResponse extends BaseInstagramResponse {
    private List<Datum> data;

    public static class Datum{
        private String id;

        @SerializedName("updated_time")
        private Date updatedTime;
    }
}
