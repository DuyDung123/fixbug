package com.example.fixbug.api.response;

import com.google.gson.annotations.SerializedName;

public class FuriruImage {

    @SerializedName("img_result")
    private boolean imgResult;

    @SerializedName("img_id")
    private String imgId;


    public boolean isImgResult() {
        return imgResult;
    }

    public String getImgId() {
        return imgId;
    }
}
