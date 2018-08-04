/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.log;

import com.orhanobut.logger.Logger;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 网络日志拦截器
 *
 * @author zhangtianjun
 */
public class HttpLoggerInterceptor implements HttpLoggingInterceptor.Logger {
    @Override
    public void log(String message) {
        Logger.t("HTTP").d(message);
    }
}
