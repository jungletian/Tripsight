/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.demo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.tripsight.R;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * demo adapter
 *
 * @author zhangtianjun
 */
public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.DemoHolder> {
    private final List<Demo> demos;

    public DemoAdapter(List<Demo> demos) {
        this.demos = demos;
    }

    @NonNull
    @Override
    public DemoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DemoHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.debug_item_demo, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DemoHolder demoHolder, int i) {
        demoHolder.bindDemo(demos.get(i));
    }

    @Override
    public int getItemCount() {
        return demos.size();
    }

    static class DemoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.debug_image_demo)
        ImageView imageView;
        @BindView(R.id.debug_post_title)
        TextView titleView;
        @BindView(R.id.debug_post_body)
        TextView bodyView;

        public DemoHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindDemo(Demo demo) {
            Glide.with(itemView).load(demo.getUrl()).into(imageView);
            titleView.setText(demo.getTitle());
            bodyView.setText(demo.getThumbnailUrl());
        }
    }
}
