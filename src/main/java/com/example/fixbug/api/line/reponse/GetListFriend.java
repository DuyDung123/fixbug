package com.example.fixbug.api.line.reponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetListFriend extends BaseResponse {

    public Data data;

    public Data getData() {
        return data;
    }

    public static class Data {
        public List<Friend> friends;

        public List<Friend> getFriends() {
            return friends;
        }
    }

    public static class Friend {
        private int id;

        @SerializedName("landing_id")
        private int landingId;

        @SerializedName("bot_id")
        private int botId;

        @SerializedName("line_id")
        private String lineId;

        private Object email;

        @SerializedName("time_click")
        private String timeClick;

        private int action;

        @SerializedName("bot_line_user_id")
        private int botLineUserId;

        @SerializedName("param_qrcode_connect_outside")
        private Object paramQrcodeConnectOutside;

        @SerializedName("is_old_friend")
        private int isOldFriend;

        private int is_action_web;

        private String lineUserName;

        private Object lineUserAvatar;

        public int getId() {
            return id;
        }

        public int getLandingId() {
            return landingId;
        }

        public int getBotId() {
            return botId;
        }

        public String getLineId() {
            return lineId;
        }

        public Object getEmail() {
            return email;
        }

        public String getTimeClick() {
            return timeClick;
        }

        public int getAction() {
            return action;
        }

        public int getBotLineUserId() {
            return botLineUserId;
        }

        public Object getParamQrcodeConnectOutside() {
            return paramQrcodeConnectOutside;
        }

        public int getIsOldFriend() {
            return isOldFriend;
        }

        public int getIs_action_web() {
            return is_action_web;
        }

        public String getLineUserName() {
            return lineUserName;
        }

        public Object getLineUserAvatar() {
            return lineUserAvatar;
        }
    }
}
