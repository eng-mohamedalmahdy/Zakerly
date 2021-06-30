package com.graduationproject.zakerly.core.network.retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.graduationproject.zakerly.core.constants.SiteURLs.*;

public class RetrofitClient {
    private static Retrofit instance;
    private static NotificationAPI api;

    private static Retrofit getInstance() {
        if (instance == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

            instance = new Retrofit.Builder()
                    .baseUrl(FCM_BASE_URL)
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return instance;
    }

    public static NotificationAPI getApi() {
        if (api == null) {
            api = getInstance().create(NotificationAPI.class);
        }
        return api;
    }
}
