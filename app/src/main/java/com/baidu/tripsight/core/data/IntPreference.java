/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.tripsight.core.data;

import android.content.SharedPreferences;

/**
 * int 类型存储
 *
 * @author jungletian
 */
public class IntPreference {
    private final SharedPreferences preferences;
    private final String key;
    private final int defaultValue;

    public IntPreference(SharedPreferences preferences, String key) {
        this(preferences, key, 0);
    }

    public IntPreference(SharedPreferences preferences, String key, int defaultValue) {
        this.preferences = preferences;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public int get() {
        return preferences.getInt(key, defaultValue);
    }

    public boolean isSet() {
        return preferences.contains(key);
    }

    public void set(int value) {
        preferences.edit().putInt(key, value).apply();
    }

    public void delete() {
        preferences.edit().remove(key).apply();
    }
}
