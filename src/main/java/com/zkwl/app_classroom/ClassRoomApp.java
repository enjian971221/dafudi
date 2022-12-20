package com.zkwl.app_classroom;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.king.retrofit.retrofithelper.RetrofitHelper;
import com.zkwl.app_classroom.api.ClassroomConstants;

import timber.log.Timber;

//@HiltAndroidApp //模块化时使用，非模块的时候请注释掉
public class ClassRoomApp extends Application {

    private static ClassRoomApp instance;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        RetrofitHelper.getInstance().setBaseUrl(ClassroomConstants.BASE_URL);
        Timber.plant(new Timber.Tree() {
            @Override
            protected void log(int i, @Nullable String s, @NonNull String s1, @Nullable Throwable throwable) {
                Log.e("debug",""+s1);
            }
        });
    }
    public static ClassRoomApp getInstance(){
        return instance;
    }

}
