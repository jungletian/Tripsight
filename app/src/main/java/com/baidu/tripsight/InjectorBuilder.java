/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight;

import com.baidu.tripsight.core.base.BaseDaggerActivity;
import com.baidu.tripsight.core.base.BaseDaggerFragment;
import com.baidu.tripsight.ui.home.HomeModule;
import com.baidu.tripsight.ui.home.HomeFragment;
import com.baidu.tripsight.ui.publish.PublishActivity;

import dagger.Module;
import dagger.android.DaggerService;
import dagger.android.DaggerContentProvider;
import dagger.android.DaggerBroadcastReceiver;
import dagger.android.ContributesAndroidInjector;


/**
 * 在这里声明要注射的对象，前提要继承 {@link BaseDaggerActivity} or {@link BaseDaggerFragment}
 * <p>
 * Service BroadcastReceiver ContentProvider 可以考虑继承 {@link DaggerService} {@link DaggerBroadcastReceiver} {@link DaggerContentProvider} 或在对应的声明周期方法中调用 AndroidInjection.inject(this, context);
 * <p>
 *
 * @author zhangtianjun
 * @see {https://google.github.io/dagger/android.html}
 */
@Module
public abstract class InjectorBuilder {

    @ContributesAndroidInjector(modules = {HomeModule.class})
    abstract MainActivity contributeHomeActivity();

    @ContributesAndroidInjector(modules = {HomeModule.class})
    abstract HomeFragment contributeHomeFragment();

    @ContributesAndroidInjector(modules = {HomeModule.class})
    abstract PublishActivity contributePublishActivity();
}
