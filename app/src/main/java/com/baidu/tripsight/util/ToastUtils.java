/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

/**
 * show Toast
 *
 * @author zhangtianjun
 */
public class ToastUtils {

    public static void showSuccessToast(Context context, @NonNull String message) {
        Toasty.success(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, @NonNull String message) {
        Toasty.success(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void showErrorToast(Context context, @NonNull String message) {
        Toasty.error(context, message, Toast.LENGTH_SHORT).show();
    }

    private ToastUtils() {
        throw new AssertionError("no instances");
    }
}
