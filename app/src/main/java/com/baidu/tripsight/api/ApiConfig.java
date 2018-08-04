/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.api;

/**
 * 接口配置
 *
 * @author zhangtianjun
 */
public class ApiConfig {

    public static final String TEST_BASE_URL = "https://jsonplaceholder.typicode.com";
    public static final String BASE_URL = "https://my-json-server.typicode.com";

    private ApiConfig() {
        throw new AssertionError("no instances");
    }
}
