package com.zkwl.app_classroom.util;

import android.content.Context;

/**
 * @Description: SharedPreferences工具类
 * @Author: LM
 * @CreateDate: 2020/3/20 15:55
 * @Question:
 */
public class SpsUtil {


    public static String getString(Context context,String key, String... defaultValue) {
        return context.getSharedPreferences("app", Context.MODE_PRIVATE).getString(key, defaultValue == null ? "" : defaultValue[0]);
    }

}