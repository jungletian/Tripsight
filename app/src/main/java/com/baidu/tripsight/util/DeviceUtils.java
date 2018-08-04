/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.baidu.tripsight.core.data.StringPreference;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

/**
 * Device 工具类
 *
 * @author zhangtianjun
 */
public class DeviceUtils {
    private static final String DEVICE_ID = "android_mixed_device_id";
    private final Context context;
    private final StringPreference deviceIdPreference;

    public DeviceUtils(Context context, SharedPreferences sharedPreferences) {
        this.context = Preconditions.checkNotNull(context);
        this.deviceIdPreference = new StringPreference(sharedPreferences, DEVICE_ID, "");
    }

    @SuppressLint("HardwareIds")
    private static String buildDeviceId(Context context, String imei, String serialNumber) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(imei)) {
            sb.append(imei);
        }
        if (!TextUtils.isEmpty(serialNumber)) {
            sb.append(serialNumber);
        }
        String androidId = android.provider.Settings.Secure.getString(context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        if (!TextUtils.isEmpty(androidId)) {
            sb.append(androidId);
        }
        String id = sb.toString();
        if (TextUtils.isEmpty(id)) {
            id = String.valueOf(new Random().nextDouble());
        }
        return Utils.getHash(id);
    }

    public float getDensity() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.density;
    }

    public String getDeviceId() {
        if (deviceIdPreference.isSet()) {
            return deviceIdPreference.get();
        } else {
            if (hasPermission(Manifest.permission.READ_PHONE_STATE)) {
                TelephonyManager tm =
                        (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                @SuppressLint("MissingPermission") String deviceId = buildDeviceId(context, tm.getDeviceId(), tm.getSimSerialNumber());
                deviceIdPreference.set(deviceId);
                return deviceIdPreference.get();
            } else {
                // if we don't have READ_PHONE_STATE permission, the device id will not be saved
                return buildDeviceId(context, null, null);
            }
        }
    }

    public Point getScreenSize() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public String getDeviceName() {
        return Build.MODEL;
    }

    /**
     * 获得mac地址
     */
    @SuppressWarnings("WifiManagerPotentialLeak")
    public String getDeviceMAC() {
        if (hasPermission(Manifest.permission.ACCESS_WIFI_STATE)) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wInfo = wifiManager.getConnectionInfo();
            if (wInfo != null) {
                return wInfo.getMacAddress();
            }
        }
        return null;
    }

    /**
     * 返回IMEI的值
     */
    @SuppressLint("MissingPermission")
    public String getIMEI() {
        if (hasPermission(Manifest.permission.READ_PHONE_STATE)) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId();
        } else {
            return null;
        }
    }

    private boolean hasPermission(String permission) {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(context,
                permission);
    }

    @SuppressLint("MissingPermission")
    public String getIMSI() {
        if (hasPermission(Manifest.permission.READ_PHONE_STATE)) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getSubscriberId();
        } else {
            return null;
        }
    }

    public boolean isDeviceRooted() {
        String buildTags = android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }
        try {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                return true;
            }
        } catch (Throwable e) {
            // ignore
        }
        return false;
    }

    public long[] getSDCardMemory() {
        long[] sdCardInfo = new long[2];
        if (hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                File sdcardDir = Environment.getExternalStorageDirectory();
                StatFs sf = new StatFs(sdcardDir.getPath());
                long bSize = sf.getBlockSize();
                long bCount = sf.getBlockCount();
                long availBlocks = sf.getAvailableBlocks();
                sdCardInfo[0] = bSize * bCount; //总大小
                sdCardInfo[1] = bSize * availBlocks; //可用大小
            }
        }
        return sdCardInfo;
    }

    public long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSize();
        long totalBlocks = stat.getBlockCount();
        return totalBlocks * blockSize;
    }

    public String getAndroidId() {
        String androidId =
                Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return Strings.emptyToNull(androidId);
    }

    @SuppressWarnings("DefaultCharset")
    public String getCpuName() {
        try {
            FileReader fr = new FileReader("/proc/cpuinfo");
            BufferedReader br = new BufferedReader(fr);
            String text = br.readLine();
            String[] array = text.split(":\\s+", 2);
            return array[1];
        } catch (IOException e) {
            return "";
        }
    }

    private DeviceUtils() {
        throw new AssertionError("no instances");
    }
}
