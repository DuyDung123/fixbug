package com.example.fixbug.api.line;

import com.example.fixbug.api.line.reponse.CsvExport;
import com.example.fixbug.api.requesthelper.RequestHelper;
import com.example.fixbug.api.requesthelper.ResponseAPI;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class LineStepLmeModel {

    private static final String TAG = LineStepLmeModel.class.getSimpleName();

    public void notifyAddFriend(String url, ResponseAPI<ResponseBody> callback) {
        RequestHelper.executeSyncRequest(ILineService.SERVICE.notifyAddFriend(url), callback);
    }

    public CsvExport getListDataExport(String csvManagementId, String userId) {
        RequestBody requestCsvManagementId = RequestBody.create(MediaType.parse("multipart/form-data"), csvManagementId);
        RequestBody requestUserId = RequestBody.create(MediaType.parse("multipart/form-data"), userId);
        return RequestHelper.executeSyncRequest(ILineService.SERVICE.getListDataExport(requestCsvManagementId, requestUserId));
    }
}
