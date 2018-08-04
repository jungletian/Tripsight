/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.base;

import java.lang.ref.WeakReference;

/**
 * base presenter
 *
 * @author zhangtianjun
 */
public abstract class BasePresenter<V extends BaseView> implements Presenter<V> {

    private WeakReference<V> mViewRef;

    @Override
    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    protected V getView() {
        return mViewRef == null ? null : mViewRef.get();
    }

    protected boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    @Override
    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }
}
