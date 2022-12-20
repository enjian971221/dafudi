1. // 是否正式版（正式版把每个独立的子模块合并成一个整体） isRelease = true  模块化开发 合并的时候需将模块的Application的注解去掉（@HiltAndroidApp）
   1. 课程插件采用广播的形式通知主程序，主程序需创建一个广播类,并在合适的位置比如（baseactivity）动态注册 代码示例：
      
      1.动态注册广播，建议放在主程序 
        IntentFilter intentFilter=new IntentFilter();
         intentFilter.addAction("com.zkwl.qhzwj.receiver.MY_BROADCAST");
         WechatAlipayReceiver wechatAlipayReceiver=new WechatAlipayReceiver();
         registerReceiver(wechatAlipayReceiver,intentFilter);
   
      3. public class WechatAlipayReceiver  extends BroadcastReceiver {

         private IWXAPI mMsgApi;

         @SuppressLint("HandlerLeak")
         private Handler mHandler = new Handler() {
         public void handleMessage(Message msg) {
         switch (msg.what) {
         case 1: {
         PayResult payResult = new PayResult((Map<String, String>) msg.obj);
         /**
      4. 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
         */
         String resultInfo = payResult.getResult();// 同步返回需要验证的信息
         String resultStatus = payResult.getResultStatus();
         // 判断resultStatus 为9000则代表支付成功
         if (TextUtils.equals(resultStatus, "9000")) {
         // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。

                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        }
                        break;
                    }
                }
            }
         };

         @Override
         public void onReceive(Context context, Intent intent) {
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
       
