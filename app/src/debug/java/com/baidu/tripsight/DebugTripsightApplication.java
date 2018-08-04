/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight;

import com.baidu.tripsight.core.di.ApplicationModule;
import com.baidu.tripsight.core.di.DaggerDebugComponent;
import com.baidu.tripsight.core.di.DebugNetworkModule;
import com.baidu.tripsight.core.log.HttpLoggerInterceptor;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * debug application
 *
 * @author zhangtianjun
 */
public class DebugTripsightApplication extends TripsightApplication {

    private final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggerInterceptor());

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerDebugComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .debugNetworkModule(new DebugNetworkModule(httpLoggingInterceptor, this))
                .create();
    }
}
