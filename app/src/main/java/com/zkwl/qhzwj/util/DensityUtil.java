package com.zkwl.qhzwj.util;

import android.content.Context;
import android.content.res.Resources;
import android.view.Display;
import android.view.WindowManager;

import com.zkwl.qhzwj.App;


/**
 * 屏幕宽高和尺寸转换
 * Created by yangjianli on 2017/8/26.
 */

public class DensityUtil {

    public static final String TAG = "DensityUtil";

    private static WindowManager windowManager;
    private static int width;
    private static int height;

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        Resources resources = context.getResources();
        float scale = 0;
        if(resources != null){
            scale = resources.getDisplayMetrics().density;
        }

        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        Resources resources = context.getResources();
        float scale = 0;
        if(resources != null){
            scale = resources.getDisplayMetrics().density;
        }
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        Resources resources = context.getResources();
        float fontScale = 0;
        if(resources != null){
            fontScale = resources.getDisplayMetrics().scaledDensity;
        }
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        Resources resources = context.getResources();
        float fontScale = 0;
        if(resources != null){
            fontScale = resources.getDisplayMetrics().scaledDensity;
        }
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 得到当前屏幕的分辨率的宽
     */
    public static int getScreenWidth() {
        if(windowManager == null){
            windowManager = (WindowManager) App.activity.get().getSystemService(Context.WINDOW_SERVICE);
            Display mDisplay = windowManager.getDefaultDisplay();
            // 宽高
            width = mDisplay.getWidth();
            height = mDisplay.getHeight();

        }
        return width;
    }

    /**
     * 得到当前屏幕的分辨率的高
     */
    public static int getScreenHeight() {
        if(windowManager == null){
            windowManager = (WindowManager) App.activity.get().getSystemService(Context.WINDOW_SERVICE);
            Display mDisplay = windowManager.getDefaultDisplay();
            // 宽高
            width = mDisplay.getWidth();
            height = mDisplay.getHeight();
        }
        return height;
    }
}
