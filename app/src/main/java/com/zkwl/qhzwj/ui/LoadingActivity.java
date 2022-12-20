package com.zkwl.qhzwj.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.zkwl.qhzwj.MainActivity;
import com.zkwl.qhzwj.api.Constants;
import com.zkwl.qhzwj.util.SpsUtil;

/**
 * @author songkai
 * @date on 2019/8/6
 * 启动页
 */
public class LoadingActivity extends AppCompatActivity {
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ImmersionBar.with(this).fitsSystemWindows(true).statusBarColor("#88C947").init();
//        ActivityUtils.getManager().finishActivity(ShopGoodInfoActivity.class);
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SpsUtil.getBoolean(Constants.USER_IS_FRIST_LOGIN, false)) {
                    if (getIntent() != null && getIntent().getData() != null) {
                        Uri uri = getIntent().getData();
                        String id = uri.getQueryParameter("id");
//                        Logger.d("第三方进来的--->" + id);
//                        Intent intentInfo = new Intent(LoadingActivity.this, ShopGoodInfoActivity.class);
//                        intentInfo.putExtra("g_id", id);
//                        intentInfo.putExtra("type", "web");
//                        startActivity(intentInfo);
//                        finish();
                    } else {
                        startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                        finish();
                    }
                } else {
                    startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                    finish();
                }

            }
        }, 200);
    }

    @Override
    protected void onDestroy() {
        mHandler = null;
        super.onDestroy();
    }
}
