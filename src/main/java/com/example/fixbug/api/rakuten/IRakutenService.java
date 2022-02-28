package com.example.fixbug.api.rakuten;

import com.example.fixbug.api.requesthelper.RetrofitClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IRakutenService {
    IRakutenService SERVICE = RetrofitClient.getInstance().getService("https://api.rms.rakuten.co.jp/", IRakutenService.class);

    @GET("/es/1.0/item/search")
    Call<ResponseBody> search(@Header("Authorization") String authorization, @Body RequestBody request);
}
