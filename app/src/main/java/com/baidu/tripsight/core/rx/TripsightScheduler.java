/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.rx;

import io.reactivex.Scheduler;
import io.reactivex.internal.schedulers.NewThreadWorker;
import io.reactivex.internal.schedulers.RxThreadFactory;

/**
 * 调度器
 *
 * @author zhangtianjun
 */
public class TripsightScheduler extends Scheduler {

    private static final String THREAD_PREFIX = "Tripsight-";

    private TripsightScheduler() {
    }

    public static TripsightScheduler create() {
        return new TripsightScheduler();
    }

    @Override
    public Scheduler.Worker createWorker() {
        return new NewThreadWorker(new RxThreadFactory(THREAD_PREFIX));
    }
}
