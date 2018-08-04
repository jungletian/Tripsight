/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.di;

import com.baidu.tripsight.InjectorBuilder;
import com.baidu.tripsight.core.fragment.DebugInfoFragment;
import com.baidu.tripsight.core.fragment.DebugNetworkFragment;
import com.baidu.tripsight.demo.DemoActivity;
import com.baidu.tripsight.demo.DemoModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * debug 注射对象声明
 *
 * @author zhangtianjun
 */
@Module
public abstract class DebugInjectorBuilder extends InjectorBuilder {

    /**
     * modules: 可以向 DemoActivity 注射的 modules（可以多个）
     *
     * @return 声明注射的对象 （向 DemoActivity 注射）
     */
    @ContributesAndroidInjector(modules = DemoModule.class)
    abstract DemoActivity contributeDebugActivity();

    @ContributesAndroidInjector(modules = DebugModule.class)
    abstract DebugNetworkFragment contributeDebugNetworkFragment();

    @ContributesAndroidInjector(modules = DemoModule.class)
    abstract DebugInfoFragment contributeDebugInfoFragment();
}
