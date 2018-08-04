/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.ui.account;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baidu.tripsight.R;
import com.baidu.tripsight.core.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * account fragment
 *
 * @author zhangtianjun
 */
public class AccountFragment extends BaseFragment {

    public static Fragment create() {
        return new AccountFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, view);
        return view;
    }
}
