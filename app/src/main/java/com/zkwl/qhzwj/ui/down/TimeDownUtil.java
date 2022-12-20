package com.zkwl.qhzwj.ui.down;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.util.Log;

/**
 * @author songkai
 * @date on 2019/8/2
 */
public class TimeDownUtil {

    private static TimeDownUtil mInstance;
    private TimerDownListener mTimerDownListener;
    private ValueAnimator mValueAnimator;
    private int timerCount = -1;
    private boolean isOnclickCancel = false;

    public static TimeDownUtil getInstance() {
        if (mInstance != null) {
            return mInstance;
        } else {
            return new TimeDownUtil();
        }
    }

    public void setTimerDownListener(TimerDownListener timerDownListener) {
        mTimerDownListener = timerDownListener;
    }

    /**
     * 开始倒计时
     */
    public void startDownTimer(DownTimerEnum timerEnum) {
        isOnclickCancel = false;
        timerCount = -1;
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
            mValueAnimator = null;
        }
        mValueAnimator = ValueAnimator.ofInt(timerEnum.getTimeInt(), 0);
        mValueAnimator.setDuration(timerEnum.getTimeTotalInt());
        mValueAnimator.setInterpolator(new TimeInterpolator() {
            @Override
            public float getInterpolation(float input) {
//                Log.d("ValueAnimator", "getInterpolation:--> " + input);
                return input;
            }
        });
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer integer = (Integer) animation.getAnimatedValue();
                if (integer != timerCount) {
                    timerCount = integer;
                    mTimerDownListener.tiemChange(integer);
                }
            }

        });
        mValueAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.d("TimeDownUtil", "onAnimationEnd: ");
                if (!isOnclickCancel) {
                    mTimerDownListener.timeEnd();
                } else {
                    Log.d("TimeDownUtil", "onAnimationEnd:取消了不执行end方法 ");
                }
                timerCount = -1;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                isOnclickCancel = true;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mValueAnimator.start();
    }

    public void endTimer() {
        Log.d("TimeDownUtil", "endTimer:关闭 ");
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
            mValueAnimator = null;
            Log.d("TimeDownUtil", "endTimer:关闭2 ");
        }
    }


}
