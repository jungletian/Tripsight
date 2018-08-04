/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.demo;

import com.baidu.tripsight.core.base.ApiPresenter;

import java.util.List;

/**
 * demo presenter
 *
 * @author zhangtianjun
 */
public class DemoPresenter extends ApiPresenter<DemoView, List<Demo>> {

    private final DemoService demoService;

    public DemoPresenter(DemoService demoService) {
        this.demoService = demoService;
    }

    public void requestDemos(int nid) {
        subscribe(demoService.getPhotos());
    }

    @Override
    public void onSuccess(List<Demo> demos) {
        getView().showDemos(demos);
    }
}
