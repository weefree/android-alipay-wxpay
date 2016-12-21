package com.box.demo.net;

import com.box.demo.Config;
import com.example.paydemo.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zer0 on 2016/6/7.
 */
public class NetClient {

    public static final String HOST_URL = Config.Host;
    private static NetClient netClient = new NetClient();
    private static ApiService api;

    public NetClient(){
        //测试环境输出log
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG?HttpLoggingInterceptor.Level.BODY:HttpLoggingInterceptor.Level.NONE);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(HOST_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//支持rxjava
                .build();

        api = retrofit.create(ApiService.class);
    }

    public static ApiService getApi(){
        return netClient.api;
    }

}
