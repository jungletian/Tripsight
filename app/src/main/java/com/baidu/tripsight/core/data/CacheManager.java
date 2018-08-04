/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.data;

import android.content.Context;

/**
 * SharedPreferences 封装缓存
 *
 * @author zhangtianjun
 */
public class CacheManager {
    private static final String CACHE_DEVICE_TOKEN_KEY = "cache_device_token";
    private static final String CACHE_PWD_KEY = "cache_pwd";
    private static final String CACHE_USER_ID_KEY = "cache_user_id";
    private StringPreference tokenPreference;
    private StringPreference pwdPreference;
    private StringPreference userIdPreference;

    public CacheManager(Context context) {
        tokenPreference = new StringPreference(
                context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE),
                CACHE_DEVICE_TOKEN_KEY);
        pwdPreference = new StringPreference(
                context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE),
                CACHE_PWD_KEY);
        userIdPreference = new StringPreference(
                context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE),
                CACHE_USER_ID_KEY);
    }

    public void setCacheToken(String token) {
        tokenPreference.set(token);
    }

    public void setCachePwd(String pwd) {
        pwdPreference.set(pwd);
    }

    public void setCacheUserId(String userId) {
        userIdPreference.set(userId);
    }

    public String getCacheToken() {
        return tokenPreference.get();
    }

    public String getCachePwd() {
        return pwdPreference.get();
    }

    public String getCacheUserId() {
        return userIdPreference.get();
    }

    public boolean hasAccount() {
        return userIdPreference.isSet() && tokenPreference.isSet();
    }
}
