package com.example.fixbug.api.instagram.apiservice;

import com.example.fixbug.api.instagram.apiservice.requestbody.SendQuickReply;
import com.example.fixbug.api.instagram.apiservice.response.GetPageAccessToken;
import com.example.fixbug.api.instagram.apiservice.response.InfoUser;
import com.example.fixbug.api.instagram.apiservice.response.ListConversationsResponse;
import com.example.fixbug.api.instagram.apiservice.response.SendMessagesResponse;
import com.example.fixbug.utils.Constants;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface IInstagramService {
    /**
     * send Messages normal
     * @param idPageFB => id page fb get from account
     * @param queryMap => recipient: id account recipient, message: message send, access_token: page token
     * @return SendMessagesResponse
     */
    @POST("/" + Constants.INSTAGRAM_VERSION_API + "{idPageFB}/messages")
    Call<SendMessagesResponse> messages(@Path("idPageFB") String idPageFB, @QueryMap Map<String, String> queryMap);

    /**
     * send Messages QuickReply
     * @param queryMap put access_token
     * @param body SendQuickReply class
     * @return BaseInstagramResponse
     */
    @POST("/" + Constants.INSTAGRAM_VERSION_API + "/me/messages")
    Call<BaseInstagramResponse> messages(@QueryMap Map<String, String> queryMap, @Body SendQuickReply body);

    /**
     * get page token
     * @param queryMap put access_token
     * @return GetPageAccessToken
     */
    @GET("/" + Constants.INSTAGRAM_VERSION_API + "/me/accounts")
    Call<GetPageAccessToken> getPageAccessToken(@QueryMap Map<String, String> queryMap);

    /**
     * convert Short To Long Access Token
     * @param queryMap put: grant_type, client_secret, fb_exchange_token, client_id
     * @return BaseInstagramResponse
     */
    @GET("/" + Constants.INSTAGRAM_VERSION_API + "/oauth/access_token")
    Call<BaseInstagramResponse> convertShortToLongAccessToken(@QueryMap Map<String, String> queryMap);

    /**
     * get List Conversations of idPageFB
     * @param idPageFB id page fb get from account
     * @param queryMap platform = instagram, access_token
     * @return BaseInstagramResponse
     */
    @GET("/" + Constants.INSTAGRAM_VERSION_API + "{idPageFB}/conversations")
    Call<ListConversationsResponse> getListConversations(@Path("idPageFB") String idPageFB, @QueryMap Map<String, String> queryMap);


    @GET("/" + Constants.INSTAGRAM_VERSION_API + "/{idUserInstagram}")
    Call<InfoUser> getInfoUser(@Path("idUserInstagram") String idUserInstagram, @QueryMap Map<String, String> queryMap);
}
