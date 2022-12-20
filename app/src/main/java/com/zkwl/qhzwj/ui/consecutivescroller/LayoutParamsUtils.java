package com.zkwl.qhzwj.ui.consecutivescroller;

import android.view.ViewGroup;

/**
 * @Author teach liang
 * @Description
 * @Date 2020/5/22
 */
public class LayoutParamsUtils {

    /**
     * 使子view的topMargin和bottomMargin属性无效
     *
     * @param params
     */
    public static void invalidTopAndBottomMargin(ViewGroup.MarginLayoutParams params){
        if (params != null) {
            params.topMargin = 0;
            params.bottomMargin = 0;
        }
    }
}
