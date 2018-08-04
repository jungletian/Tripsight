/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.network;

import android.content.Context;

import com.baidu.tripsight.BuildConfig;
import com.baidu.tripsight.core.EventBus;
import com.baidu.tripsight.core.di.ForApplication;
import com.baidu.tripsight.core.rx.TripsightScheduler;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;

import java.io.File;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.baidu.tripsight.api.ApiConfig.BASE_URL;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * 网络相关注射剂
 *
 * @author zhangtianjun
 */
@Module
public class NetworkModule {

    static final int CONNECT_TIMEOUT_MILLIS = 30 * 1000; // 30s
    static final int READ_TIMEOUT_MILLIS = 30 * 1000; // 30s
    static final int DISK_CACHE_SIZE = 20 * 1024 * 1024; // 20M

    @Singleton
    @Provides
    protected OkHttpClient provideOkHttpClient(@ForApplication Context context) {
        File cacheDir = new File(context.getCacheDir(), "http");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            builder.addNetworkInterceptor(new StethoInterceptor());
        }
        return builder.connectTimeout(CONNECT_TIMEOUT_MILLIS, MILLISECONDS)
                .readTimeout(READ_TIMEOUT_MILLIS, MILLISECONDS)
                .cache(cache)
                .build();
    }

    @Singleton
    @Provides
    Bus provideEventBus() {
        return EventBus.get();
    }

    @Singleton
    @Provides
    Scheduler provideDefaultScheduler() {
        return TripsightScheduler.create();
    }

    @Singleton
    @Provides
    protected ApiEnvironment provideApiEnvironment() {
        return ApiEnvironment.RELEASE;
    }

    @Singleton
    @Provides
    protected HttpUrl provideBaseUrl(ApiEnvironment apiEnvironment) {
        return HttpUrl.parse(BASE_URL);
    }

    @Singleton
    @Provides
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        // gsonBuilder regist here
        gsonBuilder.setLenient();
        return gsonBuilder.create();
    }

    @Singleton
    @Provides
    protected Retrofit provideRetrofit(HttpUrl baseUrl, OkHttpClient httpClient, Scheduler scheduler, Gson gson) {
        OkHttpClient.Builder newBuilder = httpClient.newBuilder();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(newBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(scheduler));
        return builder.build();
    }
}
