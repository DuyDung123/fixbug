package com.example.fixbug.api.rakuten;

import com.example.fixbug.api.requesthelper.RetrofitClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface IRakutenService {
    IRakutenService SERVICE = RetrofitClient.getInstance().getService("https://api.rms.rakuten.co.jp/", IRakutenService.class);

    @GET("/es/1.0/item/search")
    Call<ResponseBody> search(@Header("Authorization") String authorization, @QueryMap Map<String, String> mapParam);
}
