/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.demo;

import com.google.gson.annotations.SerializedName;

/**
 * demo model
 *
 * @author zhangtianjun
 */
public class Demo {

    @SerializedName("title")
    private final String title;
    @SerializedName("url")
    private final String url;
    @SerializedName("thumbnailUrl")
    private final String thumbnailUrl;

    public Demo(String title, String url, String thumbnailUrl) {
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
