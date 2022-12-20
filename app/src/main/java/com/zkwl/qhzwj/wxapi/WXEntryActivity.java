package com.zkwl.qhzwj.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zkwl.qhzgyz.R;
import com.zkwl.qhzwj.api.Constants;

public class WXEntryActivity  extends AppCompatActivity implements IWXAPIEventHandler {
    private IWXAPI mMsgApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        // 将该app注册到微信
        mMsgApi = WXAPIFactory.createWXAPI(this, Constants.PAYWCHATID);
        mMsgApi.handleIntent(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mMsgApi.handleIntent(intent, this);
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }


    @Override
    public void onResp(BaseResp baseResp) {
        Log.e("WXEntryActivity-->" ,""+baseResp.errCode);
        finish();
    }
}

