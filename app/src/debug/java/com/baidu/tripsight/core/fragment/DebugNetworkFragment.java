/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.tripsight.R;
import com.baidu.tripsight.core.base.BaseDaggerFragment;
import com.baidu.tripsight.core.network.ApiEnvironment;
import com.baidu.tripsight.core.network.ApiEnvironmentCache;
import com.baidu.tripsight.util.ToastUtils;
import com.jakewharton.processphoenix.ProcessPhoenix;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 网络环境切换
 *
 * @author zhangtianjun
 */
public class DebugNetworkFragment extends BaseDaggerFragment {

    @Inject
    ApiEnvironmentCache apiEnvironmentCache;
    private final Runnable rebirthRunnable = () -> ProcessPhoenix.triggerRebirth(getContext().getApplicationContext());
    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.debug_network_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ToastUtils.showSuccessToast(getActivity(), "当前网络环境：" + apiEnvironmentCache.get());
    }

    @OnClick(R.id.debug_text_debug)
    public void onSelectDebugEnvironment() {
        apiEnvironmentCache.set(ApiEnvironment.DEBUG);
        triggerRebirth();
    }

    @OnClick(R.id.debug_text_release)
    public void onSelectReleaseEnvironment() {
        apiEnvironmentCache.set(ApiEnvironment.RELEASE);
        triggerRebirth();
    }

    private void triggerRebirth() {
        ToastUtils.showToast(getActivity(), "切换为：" + apiEnvironmentCache.get());
        handler.postDelayed(rebirthRunnable, 1500);
    }
}
