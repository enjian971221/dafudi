package com.zkwl.qhzwj.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.zkwl.qhzwj.App;

/**
 * @Description: SharedPreferences工具类
 * @Author: LM
 * @CreateDate: 2020/3/20 15:55
 * @Question:
 */
public class SpsUtil {

    //用户状态 全部以1开头

    public static void put(String key, Object value) {
        SharedPreferences.Editor edit = App.activity.get().getSharedPreferences("app", Context.MODE_PRIVATE).edit();
        if (value instanceof String) {
            edit.putString(key, (String) value);
        } else if (value instanceof Boolean) {
            edit.putBoolean(key, (Boolean) value);
        } else if (value instanceof Integer) {
            edit.putInt(key, (Integer) value);
        } else if (value instanceof Long) {
            edit.putLong(key, (Long) value);
        } else if (value instanceof Float) {
            edit.putFloat(key, (Float) value);
        } else {
            throw new RuntimeException("SharedPreferences 不可保存当前类型");
        }
        edit.apply();
    }

    public static String getString(String key, String... defaultValue) {
        return App.activity.get().getSharedPreferences("app", Context.MODE_PRIVATE).getString(key, defaultValue == null ? "" : defaultValue[0]);
    }

    public static Boolean getBoolean(String key, boolean... defaultValue) {
        return App.activity.get().getSharedPreferences("app", Context.MODE_PRIVATE).getBoolean(key, defaultValue != null && defaultValue[0]);
    }

    public static Integer getInteger(String key, int... defaultValue) {
        return App.activity.get().getSharedPreferences("app", Context.MODE_PRIVATE).getInt(key, defaultValue == null ? 0 : defaultValue[0]);
    }

    public static Long getLong(String key, long... defaultValue) {
        return App.activity.get().getSharedPreferences("app", Context.MODE_PRIVATE).getLong(key, defaultValue == null ? 0 : defaultValue[0]);
    }

    public static Float getFloat(String key, float... defaultValue) {
        return App.activity.get().getSharedPreferences("app", Context.MODE_PRIVATE).getFloat(key, defaultValue == null ? 0 : defaultValue[0]);
    }
}