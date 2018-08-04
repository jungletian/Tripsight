/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.ui.home;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * home 注射剂
 *
 * @author zhangtianjun
 */
@Module
public class HomeModule {

    @Provides
    HomePresenter provideHomePresenter(Retrofit service) {
        return new HomePresenter(service.create(HomeService.class));
    }
}
