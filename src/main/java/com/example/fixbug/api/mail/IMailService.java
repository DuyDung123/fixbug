package com.example.fixbug.api.mail;

import com.example.fixbug.api.mail.response.ListEmailResponse;
import com.example.fixbug.api.mail.response.MailRefreshTokenResponse;
import com.example.fixbug.api.mail.response.MessageEmailResponse;
import com.example.fixbug.api.requesthelper.RetrofitClient;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface IMailService {
    IMailService SERVICE = RetrofitClient.getInstance().getService("https://oauth2.googleapis.com/", IMailService.class);
    IMailService SERVICE_READ_EMAIL = RetrofitClient.getInstance().getService("https://www.googleapis.com/", IMailService.class);

    @POST("/token")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    Call<MailRefreshTokenResponse> refreshToken(@FieldMap Map<String ,String> refreshToken);

    @GET("/gmail/v1/users/{email}/messages")
    Call<ListEmailResponse> getListEmail(@Header("Authorization") String token, @Path("email") String email, @QueryMap Map<String,String> map);

    @GET("/gmail/v1/users/{email}/messages{id}?format=full")
    Call<MessageEmailResponse> getMessageEmail(@Header("Authorization") String token, @Path("email") String email, @Query("id") String id);
}
