package com.example.fixbug.fcm;

import com.google.gson.annotations.SerializedName;

public class FirebaseMessagingObject {

    public FirebaseMessagingObject(int type, Data data) {
        this.type = type;
        this.data = data;
    }

    @SerializedName("type")
    private int type;

    @SerializedName("data")
    private Data data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data{

        public Data(long id) {
            this.id = id;
        }

        @SerializedName("id")
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }
    }
}
