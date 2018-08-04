/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.di;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.baidu.tripsight.R;
import com.baidu.tripsight.demo.DemoActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * debug 布局
 *
 * @author zhangtianjun
 */
public class DebugFrameLayout extends FrameLayout {
    private final LayoutInflater layoutInflater;

    public DebugFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        layoutInflater.inflate(R.layout.debug_layout_drawer, this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.show_demo_button)
    public void showDaggerDemo() {
        Intent intent = new Intent(getContext(), DemoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }
}
