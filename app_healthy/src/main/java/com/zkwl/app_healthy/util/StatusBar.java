package com.zkwl.app_healthy.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

public class StatusBar {

    public static void setStatusBar(Activity activity,boolean... lightModel) {
        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        QMUIStatusBarHelper.translucent(activity);
        if (null != lightModel && lightModel.length > 0) {
            if (lightModel[0]) {
                QMUIStatusBarHelper.setStatusBarLightMode(activity);
            } else {
                QMUIStatusBarHelper.setStatusBarDarkMode(activity);
            }
        }
    }
    public static void chageInputState(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


}
