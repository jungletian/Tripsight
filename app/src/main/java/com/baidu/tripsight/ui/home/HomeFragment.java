/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.ui.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.baidu.tripsight.R;
import com.baidu.tripsight.api.HomeModel;
import com.baidu.tripsight.core.base.BaseDaggerFragment;
import com.baidu.tripsight.ui.widget.ApiFrameLayout;
import com.baidu.tripsight.ui.widget.PlaceholderDrawable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.verticalviewpager.VerticalViewPager;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static com.google.common.collect.Lists.newArrayList;

/**
 * home fragment
 *
 * @author zhangtianjun
 */
public class HomeFragment extends BaseDaggerFragment implements HomeView {

    @BindView(R.id.vertical_viewpager)
    VerticalViewPager verticalViewPager;

    @BindView(R.id.id_api_frame_layout)
    ApiFrameLayout apiFrameLayout;

    @Inject
    HomePresenter homePresenter;
    private DemoPagerAdapter pagerAdapter;

    public static Fragment create() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pagerAdapter = new DemoPagerAdapter();
        verticalViewPager.setAdapter(pagerAdapter);
        homePresenter.attachView(this);
        request();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        homePresenter.detachView();
    }

    @Override
    public void request() {
        apiFrameLayout.showLoading(true);
        homePresenter.requestHomeData();
    }

    @Override
    public void showHomeVideos(List<HomeModel> homeModels) {
        apiFrameLayout.showContentView(verticalViewPager);
        pagerAdapter.setHomeModels(homeModels);
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(@NonNull String message) {
        apiFrameLayout.showError(this::request);
    }

    static class DemoPagerAdapter extends PagerAdapter {
        private List<HomeModel> homeModels = newArrayList();

        public DemoPagerAdapter() {
        }

        public void setHomeModels(List<HomeModel> homeModels) {
            this.homeModels.addAll(homeModels);
        }

        @Override
        public int getCount() {
            return homeModels.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = new ImageView(container.getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT));
            Glide.with(container)
                    .load(homeModels.get(position).getUrl())
                    .apply(RequestOptions.centerCropTransform().placeholder(PlaceholderDrawable.create(container.getContext())))
                    .into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
