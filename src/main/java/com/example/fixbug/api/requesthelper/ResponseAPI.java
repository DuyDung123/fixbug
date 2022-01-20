package com.example.fixbug.api.requesthelper;

public interface ResponseAPI<T> {
    static final int ON_RESPONSE_NULL = 1;
    static final int ON_FAILURE = 2;
    static final int ON_EXCEPTION = 3;

    void onSuccess(T response, int code);

    void onFailure(int code, String message);
}
