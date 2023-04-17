package com.example.fixbug.api.wssj;

import com.example.fixbug.api.requesthelper.RetrofitClient;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface IWssjServices {

    IWssjServices SERVICES = RetrofitClient.getInstance().getService("http://133.130.73.227:6789/", IWssjServices.class);

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })
    @POST
    Call<GetListAmazonBuyResponse> getListAmazonBuyProduct(@Url String host, @Header("account-id") String accountId, @Body Map<String, String> body);
}
