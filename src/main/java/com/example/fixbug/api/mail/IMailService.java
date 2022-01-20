package com.example.fixbug.api.mail;

import com.example.fixbug.api.mail.response.MailRefreshTokenResponse;
import com.example.fixbug.api.requesthelper.RetrofitClient;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import java.util.Map;

public interface IMailService {
    IMailService SERVICE = RetrofitClient.getInstance().getService("https://oauth2.googleapis.com/", IMailService.class);

    @POST("/token")
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @FormUrlEncoded
    Call<MailRefreshTokenResponse> refreshToken(@FieldMap Map<String ,String> refreshToken);
}
