/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.baidu.tripsight.core.di.ApplicationComponent;
import com.baidu.tripsight.core.di.ApplicationModule;
import com.baidu.tripsight.core.di.DaggerApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * application
 *
 * @author zhangtianjun
 */
public class TripsightApplication extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent.Builder builder = DaggerApplicationComponent.builder();
        builder.module(new ApplicationModule(this));
        return builder.create();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
