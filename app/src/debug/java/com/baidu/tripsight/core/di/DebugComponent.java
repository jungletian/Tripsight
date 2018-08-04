/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.di;

import com.baidu.tripsight.DebugTripsightApplication;
import com.baidu.tripsight.core.network.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;

/**
 * Dagger2 的核心是 {@link Component} 和 {@link dagger.Module} 两个注解；
 * <p>
 * 1. Component 可以理解为注射器
 * 2. Module 可以理解为注射剂
 * 3. AndroidInjection.inject(this); 可以理解为注射（打针）
 *
 * @author zhangtianjun
 */
@Singleton
@Component(modules = {ApplicationModule.class, DebugInjectorBuilder.class})
public interface DebugComponent extends AndroidInjector<DebugTripsightApplication> {

    @Component.Builder
    interface Builder {
        Builder applicationModule(ApplicationModule module);

        Builder debugNetworkModule(NetworkModule networkModule);

        DebugComponent create();
    }
}
