/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.di;

import android.content.Context;

import com.baidu.tripsight.core.network.ApiEnvironmentCache;

import dagger.Module;
import dagger.Provides;

/**
 * @author zhangtianjun
 */
@Module
public class DebugModule {

    @Provides
    public ApiEnvironmentCache provideApiEnvironment(@ForApplication Context context) {
        return new ApiEnvironmentCache(context);
    }
}
