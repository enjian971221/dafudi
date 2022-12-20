package com.zkwl.qhzwj.receiver;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zkwl.qhzwj.api.Constants;
import com.zkwl.qhzwj.bean.PayResult;

import java.util.Map;

public class WechatAlipayReceiver  extends BroadcastReceiver {

    private IWXAPI mMsgApi;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Intent intent = new Intent("com.zkwl.qhzwj.receiver.Pay_BROADCAST");
                        intent.putExtra("type", "alipay");
                        context.sendBroadcast(intent);

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                    }
                    break;
                }
            }
        }
    };

    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        if (intent != null) {
            if (intent.getStringExtra("type").equals("wechat")){
                Log.e("onReceive",""+intent.getStringExtra("data"));
                mMsgApi = WXAPIFactory.createWXAPI(context, Constants.PAYWCHATID);

                try {
                    org.json.JSONObject json = new org.json.JSONObject(intent.getStringExtra("data"));
                    PayReq req = new PayReq();
                    req.appId = json.getString("appid");
                    req.partnerId = json.getString("partnerid");
                    req.prepayId = json.getString("prepayid");
                    req.nonceStr = json.getString("noncestr");
                    req.timeStamp = json.getString("timestamp");
                    req.packageValue = json.getString("package");
                    req.sign = json.getString("sign");
                    req.extData = "app data"; // optional
                    mMsgApi.sendReq(req);//发送调起微信的请求
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "解析异常", Toast.LENGTH_SHORT).show();
                }
            }else if (intent.getStringExtra("type").equals("alipay")){


                final Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask((Activity) context);
                        Map<String, String> result = alipay.payV2(intent.getStringExtra("data"), true);
                        Log.e("msp", result.toString());

                        Message msg = new Message();
                        msg.what = 1;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                // 必须异步调用
                Thread payThread = new Thread(payRunnable);
                payThread.start();

            }

        }
    }
}
