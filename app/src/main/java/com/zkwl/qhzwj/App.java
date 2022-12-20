package com.zkwl.qhzwj;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.king.retrofit.retrofithelper.RetrofitHelper;
import com.zkwl.qhzwj.api.Constants;

import java.lang.ref.WeakReference;

import dagger.hilt.android.HiltAndroidApp;



@HiltAndroidApp
public class App extends Application  implements Application.ActivityLifecycleCallbacks {

    public static WeakReference<Activity> activity;

    private String tempClass;


    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        initLogger();
        //设置BaseUrl
        RetrofitHelper.getInstance().setBaseUrl(Constants.BASE_URL);
        PushServiceFactory.init(this);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        tempClass = activity.getClass().getSimpleName();
        App.activity = new WeakReference<>(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
    }

    @Override
    public void onActivityResumed(Activity activity) {
        if (!activity.getClass().getSimpleName().equals(tempClass)) {
            App.activity = new WeakReference<>(activity);
        }
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {

    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }

    private void initLogger(){
        //初始化日志打印
//        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
//                .methodOffset(5)
//                .tag(Constants.TAG)
//                .build();
//        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }

}
