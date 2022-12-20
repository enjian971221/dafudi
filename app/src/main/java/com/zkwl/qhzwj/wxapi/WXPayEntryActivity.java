package com.zkwl.qhzwj.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.king.base.util.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zkwl.qhzgyz.R;
import com.zkwl.qhzwj.api.Constants;

import timber.log.Timber;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {


    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wchat_pay_result);
        //设置共同沉浸式样式
        api = WXAPIFactory.createWXAPI(this, Constants.PAYWCHATID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d("WXPayEntryActivity", "onPayFinish, errCode = " + resp.errCode);
        Log.d("WXPayEntryActivity", "onPayFinish, getType = " + resp.getType());
        Timber.d("微信支付后回调--->" + resp.errCode);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            finishActAndStartAct(resp.errCode);
        }
    }

    private void finishActAndStartAct(int errCode) {
        if (0 == errCode) {
            ToastUtils.showToast(getBaseContext(),"支付成功");

            Intent intent = new Intent("com.zkwl.qhzwj.receiver.Pay_BROADCAST");
            intent.putExtra("type", "wechat");
            sendBroadcast(intent);

            finish();
        } else if (-1 == errCode) {
            ToastUtils.showToast(getBaseContext(),"支付失败");
            finish();
        } else if (-2 == errCode) {
            ToastUtils.showToast(getBaseContext(),"支付取消");
            finish();
        }
    }
}
