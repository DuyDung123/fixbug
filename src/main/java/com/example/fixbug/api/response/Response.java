package com.example.fixbug.api.response;

import java.io.Serializable;

public class Response<T> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private static final String RESULT_OK = "ok";
    private static final String RESULT_ERROR = "error";
    private static final int RESULT_CODE = 200;

    private String result;

    private T data;

    private String errorMessages;

    private int code;

    public Response(T data){
        this.result = RESULT_OK;
        this.data = data;
        this.code = RESULT_CODE;
    }

    public Response(String errorMessages, int code){
        this.result = RESULT_ERROR;
        this.errorMessages = errorMessages;
        this.code = code;
    }

    public Response(){
        this.result = RESULT_OK;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMessages() {
        return errorMessages;
    }

    public void setErrorMessages(String errorMessages) {
        this.errorMessages = errorMessages;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}