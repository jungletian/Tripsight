/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.demo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * demo 网络接口
 *
 * @author zhangtianjun
 */
public interface DemoService {

    @GET("/posts")
    Observable<List<Demo>> getDemos();


    @GET("/photos")
    Observable<List<Demo>> getPhotos();
}
