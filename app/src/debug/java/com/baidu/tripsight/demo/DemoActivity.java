/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.baidu.tripsight.R;
import com.baidu.tripsight.core.base.BaseDaggerActivity;
import com.baidu.tripsight.core.ui.ErrorLayout;
import com.baidu.tripsight.util.ToastUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * demo activity
 *
 * @author zhangtianjun
 */
public class DemoActivity extends BaseDaggerActivity implements DemoView, ErrorLayout.RetryClickListener {

    @Inject
    DemoPresenter demoPresenter;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.loading_layout)
    View loadingView;
    @BindView(R.id.error_layout)
    ErrorLayout errorView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.debug_activity_demo);
        ButterKnife.bind(this);
        errorView.setRetryClickListener(this);
        demoPresenter.attachView(this);
        requestDemo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        demoPresenter.detachView();
    }

    private void requestDemo() {
        showLoading();
        demoPresenter.requestDemos(1);
    }

    @Override
    public void showLoading() {
        recyclerView.setVisibility(View.GONE);
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDemos(List<Demo> demos) {
        DemoAdapter adapter = new DemoAdapter(demos);
        recyclerView.setAdapter(adapter);
        recyclerView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void showError(@NonNull String message) {
        errorView.setVisibility(View.VISIBLE);
        loadingView.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        ToastUtils.showErrorToast(this, message);
    }

    @Override
    public void onRetryClick() {
        requestDemo();
    }
}
