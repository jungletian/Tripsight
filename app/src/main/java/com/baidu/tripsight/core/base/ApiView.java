/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.base;

import android.support.annotation.NonNull;

/**
 * with ApiPresenter
 *
 * @author zhangtianjun
 */
public interface ApiView extends BaseView {

    /**
     * 展示接口错误视图或 Toast 提示
     *
     * @param message 提示信息
     */
    void showError(@NonNull String message);
}
