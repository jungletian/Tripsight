/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.ui.publish;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.baidu.tripsight.R;
import com.baidu.tripsight.core.base.BaseDaggerActivity;
import com.baidu.tripsight.util.Utils;

/**
 * 发布器
 *
 * @author zhangtianjun
 */
public class PublishActivity extends BaseDaggerActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setFullScreen(getWindow());
        setContentView(R.layout.activity_publish);
    }
}
