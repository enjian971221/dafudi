package com.zkwl.app_healthy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.zkwl.app_healthy.ui.OrderListActivity;


public class PaysuccessReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            if (intent.getStringExtra("type").equals("wechat")){
                context.startActivity(new Intent(context, OrderListActivity.class).putExtra("type","wechat"));
            }else if (intent.getStringExtra("type").equals("alipay")){
                context.startActivity(new Intent(context, OrderListActivity.class).putExtra("type","alipay"));
            }
        }
    }
}
