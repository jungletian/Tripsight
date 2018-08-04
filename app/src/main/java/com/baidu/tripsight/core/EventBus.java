/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * 事件总线
 *
 * @author jungletian
 */
public final class EventBus extends Bus {
    private static Bus bus;
    private final Handler mainThread = new Handler(Looper.getMainLooper());

    private EventBus() {
        throw new AssertionError("no instances");
    }

    public static Bus get() {
        if (bus == null) {
            bus = new EventBus();
        }
        return bus;
    }

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mainThread.post(() -> EventBus.super.post(event));
        }
    }
}