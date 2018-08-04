/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.ui.home;

import com.baidu.tripsight.api.HomeModel;
import com.baidu.tripsight.core.base.ApiPresenter;

import java.util.List;

/**
 * home presenter
 *
 * @author zhangtianjun
 */
public class HomePresenter extends ApiPresenter<HomeView, List<HomeModel>> {

    private final HomeService homeService;

    public HomePresenter(HomeService homeService) {
        this.homeService = homeService;
    }

    public void requestHomeData() {
        subscribe(homeService.getPhotos());
    }

    @Override
    public void onSuccess(List<HomeModel> homeModels) {
        getView().showHomeVideos(homeModels);
    }
}
