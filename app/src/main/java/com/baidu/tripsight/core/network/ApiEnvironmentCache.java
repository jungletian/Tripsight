/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.network;

import android.content.Context;
import android.content.SharedPreferences;

import com.baidu.tripsight.core.data.IntPreference;

import static android.content.Context.MODE_PRIVATE;

/**
 * 服务器环境缓存
 *
 * @author jungletian
 */

public final class ApiEnvironmentCache {

    private final IntPreference apiEnvironment;

    public ApiEnvironmentCache(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(context.getPackageName(), MODE_PRIVATE);
        apiEnvironment = new IntPreference(sharedPreferences, "debug_api_environment",
                ApiEnvironment.RELEASE.ordinal());
    }

    public ApiEnvironment get() {
        int value = apiEnvironment.get();
        return ApiEnvironment.values()[value];
    }

    public void set(ApiEnvironment value) {
        apiEnvironment.set(value.ordinal());
    }

}
