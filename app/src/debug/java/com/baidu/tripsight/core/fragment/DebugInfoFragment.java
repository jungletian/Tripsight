/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.tripsight.BuildConfig;
import com.baidu.tripsight.R;
import com.baidu.tripsight.core.base.BaseDaggerFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设备信息
 *
 * @author zhangtianjun
 */
public class DebugInfoFragment extends BaseDaggerFragment {

    private static final DateFormat DATE_DISPLAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd hh:mm a");

    @BindView(R.id.debug_build_name)
    TextView buildNameView;
    @BindView(R.id.debug_build_code)
    TextView buildCodeView;

    @BindView(R.id.debug_build_sha)
    TextView buildShaView;

    @BindView(R.id.debug_build_date)
    TextView buildDateView;

    @BindView(R.id.debug_device_make)
    TextView deviceMakeView;

    @BindView(R.id.debug_device_model)
    TextView deviceModelView;

    @BindView(R.id.debug_device_resolution)
    TextView deviceResolutionView;

    @BindView(R.id.debug_device_density)
    TextView deviceDensityView;

    @BindView(R.id.debug_device_release)
    TextView deviceReleaseView;

    @BindView(R.id.debug_device_api)
    TextView deviceApiView;

    @BindView(R.id.debug_cuid)
    TextView cuid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.debug_info_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupBuildSection();
        setupDeviceSection();
    }

    private void setupBuildSection() {
        buildNameView.setText(BuildConfig.VERSION_NAME);
        buildCodeView.setText(String.valueOf(BuildConfig.VERSION_CODE));
        buildShaView.setText(BuildConfig.GIT_SHA);
        buildDateView.setText(DATE_DISPLAY_FORMAT.format(BuildConfig.BUILD_TIME));
    }

    private void setupDeviceSection() {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        String densityBucket = getDensityString(displayMetrics);
        deviceMakeView.setText(Build.MANUFACTURER);
        deviceModelView.setText(Build.MODEL);
        deviceResolutionView.setText(displayMetrics.heightPixels + "x" + displayMetrics.widthPixels);
        deviceDensityView.setText(displayMetrics.densityDpi + "dpi (" + densityBucket + ")");
        deviceReleaseView.setText(Build.VERSION.RELEASE);
        deviceApiView.setText(String.valueOf(Build.VERSION.SDK_INT));
    }


    @OnClick(R.id.debug_cuid)
    public void onCUIDClicked() {
        ClipboardManager clipboard =
                (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("CUID", cuid.getText());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(DebugInfoFragment.this.getActivity(), "已复制", Toast.LENGTH_SHORT).show();
    }

    private static String getDensityString(DisplayMetrics displayMetrics) {
        switch (displayMetrics.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                return "ldpi";
            case DisplayMetrics.DENSITY_MEDIUM:
                return "mdpi";
            case DisplayMetrics.DENSITY_HIGH:
                return "hdpi";
            case DisplayMetrics.DENSITY_XHIGH:
                return "xhdpi";
            case DisplayMetrics.DENSITY_XXHIGH:
                return "xxhdpi";
            case DisplayMetrics.DENSITY_XXXHIGH:
                return "xxxhdpi";
            case DisplayMetrics.DENSITY_TV:
                return "tvdpi";
            default:
                return "unknown";
        }
    }
}
