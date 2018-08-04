/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.di;

import android.content.Context;

import com.baidu.tripsight.api.ApiConfig;
import com.baidu.tripsight.core.network.ApiEnvironment;
import com.baidu.tripsight.core.network.ApiEnvironmentCache;
import com.baidu.tripsight.core.network.NetworkModule;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author zhangtianjun
 */
public class DebugNetworkModule extends NetworkModule {

    private final HttpLoggingInterceptor httpLoggingInterceptor;
    private final ApiEnvironmentCache apiEnvironmentCache;

    public DebugNetworkModule(HttpLoggingInterceptor httpLoggingInterceptor, Context context) {
        this.httpLoggingInterceptor = checkNotNull(httpLoggingInterceptor);
        apiEnvironmentCache = new ApiEnvironmentCache(context);
    }

    @Override
    public ApiEnvironment provideApiEnvironment() {
        return apiEnvironmentCache.get();
    }

    @Override
    protected HttpUrl provideBaseUrl(ApiEnvironment apiEnvironment) {
        switch (apiEnvironment) {
            case RELEASE:
                return super.provideBaseUrl(apiEnvironment);
            case DEBUG:
            default:
                return HttpUrl.parse(ApiConfig.TEST_BASE_URL);
        }
    }

    @Override
    protected OkHttpClient provideOkHttpClient(Context context) {
        OkHttpClient originClient = super.provideOkHttpClient(context);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return originClient.newBuilder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }
}
