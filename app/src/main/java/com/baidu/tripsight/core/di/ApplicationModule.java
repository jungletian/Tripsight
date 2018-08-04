/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.di;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.location.LocationManager;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;

import com.baidu.tripsight.BuildConfig;
import com.baidu.tripsight.InjectorBuilder;
import com.baidu.tripsight.TripsightApplication;
import com.baidu.tripsight.core.data.CacheManager;
import com.baidu.tripsight.core.network.NetworkModule;
import com.baidu.tripsight.util.DeviceUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.android.support.AndroidSupportInjectionModule;

import static android.content.Context.LOCATION_SERVICE;

/**
 * 应用注射剂
 *
 * @author zhangtianjun
 */
@Module(includes = {AndroidSupportInjectionModule.class, NetworkModule.class, InjectorBuilder.class})
public class ApplicationModule {

    TripsightApplication application;

    public ApplicationModule(TripsightApplication application) {
        this.application = application;
    }

    @Singleton
    @ForApplication
    @Provides
    Context provideApplicationContext() {
        return application;
    }

    @Singleton
    @Provides
    CacheManager provideCacheManager(@ForApplication Context context) {
        return new CacheManager(context);
    }

    @Singleton
    @Provides
    LocationManager provideLocationManager(
            @ForApplication Context context) {
        return (LocationManager) context.getSystemService(LOCATION_SERVICE);
    }

    @Singleton
    @Provides
    AssetManager provideAssetManager(@ForApplication Context context) {
        return context.getAssets();
    }

    @Singleton
    @Provides
    WindowManager provideWindowManager(@ForApplication Context context) {
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    @Singleton
    @Provides
    DeviceUtils provideDeviceUtils(@ForApplication Context context,
                                   SharedPreferences sharedPreferences) {
        return new DeviceUtils(context, sharedPreferences);
    }

    @Singleton
    @Provides
    String provideRawDeviceId(DeviceUtils deviceUtils) {
        return deviceUtils.getDeviceId();
    }

    @Singleton
    @Provides
    int provideVersionCode(@ForApplication Context context) {
        return BuildConfig.VERSION_CODE;
    }

    @Singleton
    @Provides
    String provideVersionName(@ForApplication Context context) {
        return BuildConfig.VERSION_NAME;
    }

    @Singleton
    @Provides
    Point provideScreenSize(WindowManager windowManager) {
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            display.getRealSize(size);
        } else {
            display.getSize(size);
        }
        return size;
    }
}