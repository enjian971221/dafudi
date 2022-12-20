package com.zkwl.qhzwj.ui;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.king.base.util.ToastUtils;
import com.king.zxing.CaptureActivity;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.zkwl.qhzgyz.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ZbarActivity extends CaptureActivity {

    private TextView left;
    private ImageView right;
    private View stusbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_zbar;
    }

    @Override
    public void initUI() {
        super.initUI();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        QMUIStatusBarHelper.translucent(this);
        QMUIStatusBarHelper.setStatusBarDarkMode(this);





        left = findViewById(R.id.base_back_tv);
        stusbar = findViewById(R.id.stusbar);

        int statusbarHeight = QMUIStatusBarHelper.getStatusbarHeight(this);
        LinearLayout.LayoutParams params_1= (LinearLayout.LayoutParams) stusbar.getLayoutParams();
        params_1.height=statusbarHeight;
        stusbar.setLayoutParams(params_1);

        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }






    @Override
    public boolean onResultCallback(String result) {
        if (!TextUtils.isEmpty(result)) {
            Log.e("result", "" + result);
        }else {
            ToastUtils.showToast(this,"扫描二维码异常请重试");
        }
        return false;
    }
}

