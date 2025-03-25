package com.example.fixbug.api.instagram;

import com.example.fixbug.api.instagram.apiservice.response.InfoUser;
import com.example.fixbug.api.requesthelper.RequestHelper;
import com.example.fixbug.api.requesthelper.ResponseAPI;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstagramModel {
    private static final Logger LOGGER = LogManager.getLogger(InstagramModel.class);

    public static void sentMessages(String idBot, String botAccessToken, String recipient, String message) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("recipient", recipient);
        queryMap.put("message", message);
        queryMap.put("access_token", botAccessToken);
        RequestHelper.executeSyncRequest(RequestHelper.getInstance().getInstagramService().messages(idBot, queryMap));
    }

    public static InfoUser getInfoUser(String idUserInstagram, String botAccessToken) {
        Map<String, String> queryMap = new HashMap<>();
        queryMap.put("fields", "name,username,profile_pic,follower_count,is_user_follow_business,is_business_follow_user");
        queryMap.put("access_token", botAccessToken);
        List<InfoUser> infoUsers = new ArrayList<>();
        RequestHelper.executeSyncRequest(RequestHelper.getInstance().getInstagramService().getInfoUser(idUserInstagram, queryMap), new ResponseAPI<InfoUser>() {
            @Override
            public void onSuccess(InfoUser response, int code) {
                infoUsers.add(response);
                LOGGER.info("#getInfoUser onSuccess: " + idUserInstagram);
            }

            @Override
            public void onFailure(int codeError, String message) {
                LOGGER.info("#getInfoUser onFailure: " + idUserInstagram + " - message: " + message);
                InfoUser infoUser = new InfoUser();
                infoUsers.add(infoUser);
            }
        });
        return infoUsers.get(0);
    }
}
