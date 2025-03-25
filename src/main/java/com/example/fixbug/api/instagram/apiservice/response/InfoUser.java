package com.example.fixbug.api.instagram.apiservice.response;

import com.example.fixbug.api.instagram.apiservice.BaseInstagramResponse;
import com.google.gson.annotations.SerializedName;

public class InfoUser extends BaseInstagramResponse {

    private String id;

    private String name;

    private String username;

    @SerializedName("profile_pic")
    private String profilePic;

    @SerializedName("follower_count")
    private int followerCount;

    @SerializedName("is_user_follow_business")
    private boolean isUserFollowBusiness;

    @SerializedName("is_business_follow_user")
    private boolean isBusinessFollowUser;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public boolean isUserFollowBusiness() {
        return isUserFollowBusiness;
    }

    public boolean isBusinessFollowUser() {
        return isBusinessFollowUser;
    }
}
