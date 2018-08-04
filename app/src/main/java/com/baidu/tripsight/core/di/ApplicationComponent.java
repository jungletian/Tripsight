/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.di;

import com.baidu.tripsight.TripsightApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

/**
 * 应用注射器
 *
 * @author zhangtianjun
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent extends AndroidInjector<TripsightApplication> {

    @Component.Builder
    interface Builder {
        ApplicationComponent.Builder module(ApplicationModule app);

        ApplicationComponent create();
    }
}
