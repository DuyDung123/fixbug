package com.example.fixbug.api.line;

import com.example.fixbug.api.line.reponse.BotTrackingFriend;
import com.example.fixbug.api.line.reponse.CsvExport;
import com.example.fixbug.api.line.reponse.GetListFriend;
import com.example.fixbug.api.requesthelper.RetrofitClient;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;


public interface ILineService {

    String BASE_URL_SERVER_LINE = "https://s.lmes.jp";

    ILineService SERVICE = RetrofitClient.getInstance().getService(ILineService.BASE_URL_SERVER_LINE, ILineService.class);

    @POST("/api/affilate/get-list-friend-by-landing")
    @Multipart
    Call<GetListFriend> getListFriend(@Part("uLand") RequestBody uLand, @Part("user_id") RequestBody userId);

    @POST("/api/affilate/get-list-data-export")
    @Multipart
    Call<CsvExport> getListDataExport(@Part("csvManagementId") RequestBody csvManagementId, @Part("user_id") RequestBody userId);

    @POST("/ajax/update-tracking-friend")
    @Multipart
    Call<BotTrackingFriend> updateTrackingFriend (@Part("room_id") String roomId, @Part("message") RequestBody message);

    @GET
    Call<ResponseBody> notifyAddFriend(@Url String url);
}
