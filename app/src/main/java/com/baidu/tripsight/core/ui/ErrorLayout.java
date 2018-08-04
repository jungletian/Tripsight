/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.baidu.tripsight.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 网络错误通用布局
 *
 * @author zhangtianjun
 */
public class ErrorLayout extends FrameLayout {

    @BindView(R.id.text_error_view)
    TextView textView;
    private RetryClickListener listener;

    public ErrorLayout(@NonNull Context context) {
        this(context, null);
    }

    public ErrorLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);
    }

    public void setErrorText(String errorText) {
        textView.setText(errorText);
    }

    public void setRetryClickListener(@Nullable RetryClickListener listener) {
        this.listener = listener;
    }

    @OnClick(R.id.text_error_view)
    public void onRetryClick() {
        if (listener != null) {
            listener.onRetryClick();
        }
        this.setVisibility(GONE);
    }

    public interface RetryClickListener {
        void onRetryClick();
    }
}
