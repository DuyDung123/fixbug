package com.example.fixbug.api.rakuten;

import com.example.fixbug.api.requesthelper.RetrofitClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import java.util.Map;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;

public interface IRakutenService {
    IRakutenService SERVICE = RetrofitClient.getInstance().getService("https://api.rms.rakuten.co.jp/", IRakutenService.class);

    IRakutenService SERVICEDEV = RetrofitClient.getInstance().getService("https://app.rakuten.co.jp/services/", IRakutenService.class);

    @GET("/es/1.0/item/search")
    Call<ResponseBody> search(@Header("Authorization") String authorization, @QueryMap Map<String,String> request);

    @GET("api/IchibaItem/Search/20170706")
    Call<IchibaResponse> searchIchiba(@QueryMap Map<String, String> query);
}
