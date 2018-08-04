/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.demo;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * demo module
 *
 * @author zhangtianjun
 */
@Module
public class DemoModule {

    @Provides
    Demo provideDemo() {
        return new Demo("title", "http://xxx", "http://yyy");
    }

    @Provides
    DemoPresenter provideDemoPresenter(Retrofit retrofit) {
        return new DemoPresenter(retrofit.create(DemoService.class));
    }
}
