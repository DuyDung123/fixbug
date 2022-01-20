package com.example.fixbug.api.requesthelper;
import com.example.fixbug.api.mail.response.MailRefreshTokenResponse;
import com.example.fixbug.utils.Logger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public final class RequestHelper {
    private static final String TAG = RequestHelper.class.getSimpleName();

    private RequestHelper() {
    }

    public static RequestHelper getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private static final class InstanceHolder {
        private static final RequestHelper INSTANCE = new RequestHelper();

        private InstanceHolder() {
            //no instance
        }
    }

    public static <T> void executeASyncRequest(Call<T> call, ResponseAPI<T> callback) {
        call.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    T responseData = response.body();
                    if (responseData == null) {
                        if (callback != null) {
                            callback.onFailure(ResponseAPI.ON_RESPONSE_NULL, "Response null.");
                        }
                    } else {
                        if (callback != null) {
                            callback.onSuccess(responseData, response.code());
                        }
                    }
                } else {
                    try {
                        if (response.errorBody() != null) {
                            if (callback != null) {
                                callback.onFailure(response.code(), response.errorBody().string());
                            }
                        } else {
                            if (callback != null) {
                                callback.onFailure(response.code(), "Unknown error.");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (callback != null) {
                            callback.onFailure(response.code(), e.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable throwable) {
                if (callback != null) {
                    callback.onFailure(ResponseAPI.ON_FAILURE, throwable.getMessage());
                }
            }
        });
    }

    public static <T> T executeSyncRequest(Call<T> request, ResponseAPI<T> callback) {
        try {
            Response<T> response = request.execute();

            Logger.info(TAG, "#executeSyncRequest url: " + request.request().url() + " - code: " + response.code());
            T responseData = response.body();
            if (response.isSuccessful()) {
                if (callback != null) {
                    callback.onSuccess(responseData, response.code());
                }
                return responseData;
            } else {
                try {

                    if (response.errorBody() != null) {
                        String responseStr =  response.errorBody().string();
                        Logger.error(TAG, "#executeSyncRequest FAILURE: " + request.request().url() + " - " + responseStr);

                        if (callback != null) {
                            callback.onFailure(response.code(),responseStr);
                        }
                        return null;
                    } else {
                        Logger.error(TAG, "#executeSyncRequest FAILURE: " + request.request().url() + " - 2 Unknown error.");
                        if (callback != null) {
                            callback.onFailure(response.code(), "Unknown error.");
                        }
                        return null;
                    }
                } catch (Exception e) {
                    Logger.error(TAG, "#executeSyncRequest FAILURE: " + request.request().url() + " - EXCEPTION: "+ e.getMessage());
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            Logger.error(TAG, ("#executeSyncReques Exception: " + request.request().url() + " - " + e.getMessage()));
        }
        if (callback != null) {
            callback.onFailure(0, "Unknown error.");
        }
        return null;
    }

    public static <T> T executeSyncRequest(Call<T> request) {
        try {
            Response<T> response = request.execute();
            T responseData = response.body();
            if (response.isSuccessful()) {
                return responseData;
            } else {
                try {
                    if (response.errorBody() != null) {
                        Logger.error(TAG, "#executeSyncRequest FAILURE: " + request.request().url() + " - " + response.errorBody().string());
                    } else {
                        Logger.error(TAG, "#executeSyncRequest FAILURE: " + request.request().url() + " - 2 Unknown error.");
                    }
                    return null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            Logger.error(TAG, ("#executeSyncReques Exception: " + request.request().url() + " - " + e.getMessage()));
        }
        return null;
    }
}
