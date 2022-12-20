package com.zkwl.app_healthy;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.king.retrofit.retrofithelper.RetrofitHelper;
import com.zkwl.app_healthy.api.HealthyConstants;

import timber.log.Timber;

//@HiltAndroidApp //模块化时使用，非模块的时候请注释掉
public class HealthyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitHelper.getInstance().setBaseUrl(HealthyConstants.BASE_URL);
        Timber.plant(new Timber.Tree() {
            @Override
            protected void log(int i, @Nullable String s, @NonNull String s1, @Nullable Throwable throwable) {
                Log.e("debug",""+s1);
            }
        });



    }
}
