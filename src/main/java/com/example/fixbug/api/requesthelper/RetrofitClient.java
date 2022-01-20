package com.example.fixbug.api.requesthelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class RetrofitClient {

    private final Map<String, Retrofit> clients;

    public static RetrofitClient getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private RetrofitClient() {
        clients = new HashMap<>();
    }

    private Retrofit getClient(String baseUrl) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = clients.get(baseUrl);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(createHttpClient())
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
            clients.put(baseUrl, retrofit);
        }
        return retrofit;
    }

    private static OkHttpClient createHttpClient() {
        return new OkHttpClient.Builder().readTimeout(600, TimeUnit.SECONDS)
                .connectTimeout(600, TimeUnit.SECONDS)
                .build();
    }

    public <T> T getService(String baseUrl, Class<T> service) {
        Retrofit retrofit = getClient(baseUrl);
        return retrofit.create(service);
    }

    private static final class InstanceHolder {

        private static final RetrofitClient INSTANCE = new RetrofitClient();

        private InstanceHolder() {
            //no instance
        }
    }
}
