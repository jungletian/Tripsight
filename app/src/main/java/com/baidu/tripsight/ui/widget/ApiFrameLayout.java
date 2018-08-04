/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.ui.widget;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.baidu.tripsight.R;
import com.baidu.tripsight.core.ui.ErrorLayout;
import com.baidu.tripsight.core.ui.ErrorLayout.RetryClickListener;
import com.google.common.base.Strings;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 封装网络接口布局
 *
 * @author zhangtianjun
 */
public class ApiFrameLayout extends FrameLayout {

    @BindView(R.id.error_layout)
    ErrorLayout errorLayout;

    @BindView(R.id.loading_layout)
    View loadingLayout;

    public ApiFrameLayout(@NonNull Context context) {
        this(context, null);
    }

    public ApiFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ApiFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setId(R.id.id_api_frame_layout);
        LayoutInflater.from(getContext()).inflate(R.layout.include_error_layout, this);
        LayoutInflater.from(getContext()).inflate(R.layout.include_loading_layout, this);
        ButterKnife.bind(this);
    }

    /**
     * 显示 loading
     *
     * @param hideOthers 是否隐藏其他视图
     */
    public void showLoading(boolean hideOthers) {
        if (hideOthers) {
            for (int i = 0; i < getChildCount(); i++) {
                getChildAt(i).setVisibility(GONE);
            }
        }
        loadingLayout.setVisibility(VISIBLE);
    }

    /**
     * 显示 error
     *
     * @param retryClickListener 重试按钮点击事件
     * @param errorText          error 信息
     */
    public void showError(@Nullable RetryClickListener retryClickListener, String errorText) {
        errorLayout.setRetryClickListener(retryClickListener);
        errorLayout.setVisibility(VISIBLE);
        loadingLayout.setVisibility(GONE);
        if (!Strings.isNullOrEmpty(errorText)) {
            errorLayout.setErrorText(errorText);
        }
    }

    public void showError(@Nullable RetryClickListener retryClickListener) {
        showError(retryClickListener, null);
    }

    /**
     * 显示 content
     *
     * @param contentView content view
     */
    public void showContentView(@NonNull View contentView) {
        contentView.setVisibility(VISIBLE);
        loadingLayout.setVisibility(GONE);
        errorLayout.setVisibility(GONE);
    }

    public void showContentView(@IdRes int id) {
        showContentView(findViewById(id));
    }
}
