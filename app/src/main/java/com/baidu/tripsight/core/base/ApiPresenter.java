/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.base;

import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * api presenter
 *
 * @author zhangtianjun
 */
public abstract class ApiPresenter<V extends ApiView, M> extends BasePresenter<V> {

    private final CompositeDisposable mDisposables = new CompositeDisposable();

    protected void subscribe(@NonNull Observable<M> source) {
        unSubscribe();
        Disposable disposable = source.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new PresenterFilter<>(this))
                .subscribe(this::onSuccess, this::onError);
        mDisposables.add(disposable);
    }

    private void unSubscribe() {
        mDisposables.clear();
    }

    @Override
    protected V getView() {
        V view = super.getView();
        if (view == null) throw new IllegalStateException("Illegal state: view has been detached");
        return view;
    }

    /**
     * api请求失败
     */

    @CallSuper
    protected void onError(Throwable throwable) {
        if (isViewAttached()) {
            getView().showError("网络连接不可用");
        }
    }

    /**
     * api请求成功后的返回结果
     *
     * @param m 返回的数据
     */
    public abstract void onSuccess(M m);

    @Override
    public void detachView() {
        super.detachView();
        unSubscribe();
    }

    private static class PresenterFilter<M> implements Predicate<M> {

        private final ApiPresenter presenter;

        private PresenterFilter(ApiPresenter presenter) {
            this.presenter = presenter;
        }

        @Override
        public boolean test(M m) throws Exception {
            return presenter != null && presenter.isViewAttached();
        }
    }
}
