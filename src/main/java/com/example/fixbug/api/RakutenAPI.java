package com.example.fixbug.api;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RakutenAPI {
    public void SearchItem(String xml){
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/xml"), xml);
    }
}
