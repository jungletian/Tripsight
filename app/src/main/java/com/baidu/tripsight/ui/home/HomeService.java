/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.ui.home;

import com.baidu.tripsight.api.HomeModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * home 接口声明
 *
 * @author zhangtianjun
 */
public interface HomeService {

    @GET("/photos")
    Observable<List<HomeModel>> getPhotos();
}