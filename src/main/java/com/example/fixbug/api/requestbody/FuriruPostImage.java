package com.example.fixbug.api.requestbody;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class FuriruPostImage {

    @SerializedName("item_id")
    private String itemId;

    @SerializedName("total_num")
    private String totalNum;

    @SerializedName("current_num")
    private String currentNum;

    @SerializedName("image")
    private File imageFile;

    public FuriruPostImage() {
    }

    public FuriruPostImage(String itemId, String totalNum, String currentNum, File imageFile) {
        this.itemId = itemId;
        this.totalNum = totalNum;
        this.currentNum = currentNum;
        this.imageFile = imageFile;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public String getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(String currentNum) {
        this.currentNum = currentNum;
    }

    public File getImageFile() {
        return imageFile;
    }

    public void setImageFile(File imageFile) {
        this.imageFile = imageFile;
    }
}
