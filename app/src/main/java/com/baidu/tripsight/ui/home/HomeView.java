/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.ui.home;

import com.baidu.tripsight.api.HomeModel;
import com.baidu.tripsight.core.base.ApiView;

import java.util.List;

/**
 * home view
 *
 * @author zhangtianjun
 */
public interface HomeView extends ApiView {
    void request();

    void showHomeVideos(List<HomeModel> homeModels);

}
