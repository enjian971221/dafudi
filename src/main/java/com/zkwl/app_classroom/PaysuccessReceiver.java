package com.zkwl.app_classroom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zkwl.app_classroom.ui.PaySuccessActivity;

public class PaysuccessReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (intent.getStringExtra("type").equals("wechat")){
                context.startActivity(new Intent(context, PaySuccessActivity.class).putExtra("type","wechat"));
            }else if (intent.getStringExtra("type").equals("alipay")){
                context.startActivity(new Intent(context, PaySuccessActivity.class).putExtra("type","alipay"));
            }
        }
    }
}
