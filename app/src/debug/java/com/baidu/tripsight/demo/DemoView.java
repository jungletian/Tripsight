/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.demo;

import com.baidu.tripsight.core.base.ApiView;
import com.baidu.tripsight.core.base.ApiPresenter;

import java.util.List;

/**
 * define Activity or Fragment function which called by {@link ApiPresenter}
 *
 * @author zhangtianjun
 */
public interface DemoView extends ApiView {
    void showLoading();

    void showDemos(List<Demo> demos);
}
