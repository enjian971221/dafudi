package com.zkwl.app_classroom.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.fastjson.JSONObject;
import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.zkwl.app_classroom.R;
import com.zkwl.app_classroom.adapter.FragmentAdapter;
import com.zkwl.app_classroom.databinding.ActivityClassroomdetailsBinding;
import com.zkwl.app_classroom.fragment.AboutInstructorFragment;
import com.zkwl.app_classroom.fragment.CourseCatalogFragment;
import com.zkwl.app_classroom.fragment.CourseIntroductionFragment;
import com.zkwl.app_classroom.fragment.PayDetailFragment;
import com.zkwl.app_classroom.fragment.PayDialogListener;
import com.zkwl.app_classroom.model.ClassRomDetailsModel;
import com.zkwl.app_classroom.ui.pwd.PayPasswordDialog;
import com.zkwl.app_classroom.util.ClassroomImgUtil;
import com.zkwl.app_classroom.util.EncryptorUtils;
import com.zkwl.app_classroom.util.IsInstallWeChatOrAliPayUtil;
import com.zkwl.app_classroom.util.StatusBar;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class ClassroomDetailsActivity extends BaseActivity<ClassRomDetailsModel, ActivityClassroomdetailsBinding> {



    @Override
    public int getLayoutId() {
        return R.layout.activity_classroomdetails;
    }

    private List<Fragment> mFragments;
    List<String> titles = new ArrayList<>();
    private JSONObject tump;
    private Double banlnce;

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {



        StatusBar.setStatusBar(this, true);
        getViewDataBinding().topbar.setTitle("课程详情");
        getViewDataBinding().topbar.addLeftBackImageButton().setOnClickListener(v -> finish());

        mFragments = new ArrayList<>();


        titles.add("课程简介");
        mFragments.add(new CourseIntroductionFragment());




        int course_type = getIntent().getIntExtra("course_type", 0);
        if (course_type==1){
            //线上
            titles.add("课程目录");
            mFragments.add(new CourseCatalogFragment());

        }




        titles.add("讲师介绍");
        mFragments.add(new AboutInstructorFragment());

        FragmentAdapter adatper = new FragmentAdapter(getSupportFragmentManager(), mFragments, titles);
        getViewDataBinding().viewPager.setAdapter(adatper);
        getViewDataBinding().viewPager.setOffscreenPageLimit(4);
        //将TabLayout和ViewPager关联起来。
        getViewDataBinding().xTablayout.setupWithViewPager(getViewDataBinding().viewPager);


        getViewModel().getBalanceinfo();
        getViewModel().getliveDataBalanceinfo().observe(this,data ->{
            banlnce  =data.getData().getDouble("balance");
        });

        getViewModel().getCourseinfoLiveData().observe(this, data -> {

            tump = data.getData();

            getViewDataBinding().title.setText(data.getData().getString("title"));

            if (data.getData().getIntValue("is_charge") == 2) {
                getViewDataBinding().price.setText("¥" + data.getData().getString("price"));
                getViewDataBinding().price1.setText("¥" + data.getData().getString("price"));
            } else {
                getViewDataBinding().price.setText("免费");
                getViewDataBinding().price1.setText("免费");
            }


            getViewDataBinding().peopleNumber.setText("" + data.getData().getIntValue("people_number") + "人已学习");
            ClassroomImgUtil.loadImage(getViewDataBinding().imageUrl, data.getData().getString("image_url"), 1);

            if (tump.getIntValue("is_buy") == 2 && tump.getIntValue("user_buy") == 1) {
                getViewDataBinding().buytv.setEnabled(true);

            } else if (tump.getIntValue("is_buy") == 1 && tump.getIntValue("user_buy") == 2) {
                getViewDataBinding().buytv.setEnabled(false);
                getViewDataBinding().buytv.setBackgroundColor(Color.parseColor("#D3D3D3"));
                getViewDataBinding().buytv.setText("已购买");
            }
        });

        getViewModel().getCoursepayLiveData().observe(this, data -> {
            if (data.getData().getIntValue("pay_status") == 1) {
                payWChatPay(data.getData().getJSONObject("pay_params"));
            } else if (data.getData().getIntValue("pay_status") == 2) {
                ToastUtils.showToast(getContext(),"支付成功");
                getViewModel().getCourseinfo(getIntent().getStringExtra("id"));
            }
        });

        getViewModel().getliveDataCourseAlipay().observe(this, data -> {
            if (data.getData().getIntValue("pay_status") == 1) {
                aliPay(data.getData().getString("pay_params"));
            } else if (data.getData().getIntValue("pay_status") == 2) {
                ToastUtils.showToast(getContext(),"支付成功");
                getViewModel().getCourseinfo(getIntent().getStringExtra("id"));
            }
        });
        getViewModel().getliveDataCourseYue().observe(this, data -> {
            if (data.getData().getIntValue("pay_status") == 1) {
                ToastUtils.showToast(getContext(),data.getMessage());
            } else if (data.getData().getIntValue("pay_status") == 2) {
                ToastUtils.showToast(getContext(),"支付成功");
                getViewModel().getCourseinfo(getIntent().getStringExtra("id"));
            }
        });

        getViewDataBinding().buytv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIntent().getIntExtra("course_type", 0)==2){
                    startActivity(new Intent(ClassroomDetailsActivity.this, RegistrationActivity.class)
                            .putExtra("id",getIntent().getStringExtra("id")));
                    return;
                }
                if (tump == null) {
                    return;
                }
                if (tump.getIntValue("is_buy") == 2 && tump.getIntValue("user_buy") == 1) {
                    //用户是否购买  是否可以购买或
                    if (tump.getIntValue("is_charge") == 2) {
                        //是否收费
                        payShowDialog(tump.getDouble("price"));
                    } else {
                        //免费支付渠道
                        getViewModel().CourseAlipay("", tump.getIntValue("id") + "");
                    }
                }
            }
        });

//        getViewModel().Checkpaypwd();
//        getViewModel().getliveDataCheckpaypwd().observe(this,data->{
//
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getViewModel().getCourseinfo(getIntent().getStringExtra("id"));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    /**
     * 显示订单选择支付方式
     *
     * @param price
     */
    private void payShowDialog(Double price) {
        PayDetailFragment payDetailFragment = new PayDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putString("good_order_type", "订单");
        bundle.putDouble("owner_balance", banlnce);
        bundle.putDouble("pay_money", price);
        payDetailFragment.setArguments(bundle);
        payDetailFragment.setPayDialogListener(new PayDialogListener() {
            @Override
            public void payNextAli() {
                //支付宝
                if (IsInstallWeChatOrAliPayUtil.checkAliPayInstalled(ClassroomDetailsActivity.this)) {
                    getViewModel().CourseAlipay("", tump.getIntValue("id") + "");
                } else {
                    ToastUtils.showToast(ClassroomDetailsActivity.this, "未安装支付宝，请下载....");
                }
            }

            @Override
            public void payNextWchat() {
                //微信
                if (IsInstallWeChatOrAliPayUtil.isWeixinAvilible(ClassroomDetailsActivity.this)) {
                    getViewModel().CourseWechatpay("", tump.getIntValue("id") + "");
                } else {
                    ToastUtils.showToast(ClassroomDetailsActivity.this, "未安装微信，请下载....");
                }
            }

            @Override
            public void payNextMe() {
                //余额
                boolean isSetPayPwd = true;

                if (isSetPayPwd) {
                    showInputPwdDialog();
                } else {
                    showSetPwdDialog();
                }
            }

            @Override
            public void payCancel() {
            }
        });
        payDetailFragment.show(getSupportFragmentManager(), "payInfoFragment");
    }


    /**
     * 显示去设置支付密码
     */
    private void showSetPwdDialog() {
        new QMUIDialog.MessageDialogBuilder(this)
                .setTitle("提示")
                .setMessage("请先设置支付密码")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("去设置", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        startActivity(new Intent(ClassroomDetailsActivity.this, InputPayPwdActivity.class));
                        finish();
                    }
                })
                .show();
    }


    /**
     * 显示去支付
     */
    private void showInputPwdDialog() {
        final PayPasswordDialog dialog = new PayPasswordDialog(this, R.style.pwd_input_dialog);
        dialog.setDialogClick(new PayPasswordDialog.DialogClick() {
            @Override
            public void doConfirm(String password) {
                dialog.dismiss();
                getViewModel().CourseYue("", tump.getIntValue("id") + "", EncryptorUtils.zgPwd(password));
            }
        });
        dialog.show();
    }

//    private IWXAPI mMsgApi;

    private void payWChatPay(JSONObject data) {

        Intent intent = new Intent("com.zkwl.qhzwj.receiver.MY_BROADCAST");
        intent.putExtra("data", data.toString());
        intent.putExtra("type", "wechat");
        sendBroadcast(intent);




//        String url = "https://wx.tenpay.com/cgi-bin/mmpayweb-bin/checkmweb?"+"prepay_id="+ data.getString("prepayid")+"&package="+data.getString("package")+"&redirect_url=http://devland.sdzkworld.com/api/proprietor/payment/wechat_notify";
//        Log.e("url",""+url);
//
//        Intent intent = new Intent();
//
//        intent.setAction(Intent.ACTION_VIEW);
//
//        intent.setData(Uri.parse(URLDecoder.decode(url)));
//
//        startActivity(intent);
//
//
//        try {
//            org.json.JSONObject json = new org.json.JSONObject(data);
//            PayReq req = new PayReq();
//            req.appId = json.getString("appid");
//            req.partnerId = json.getString("partnerid");
//            req.prepayId = json.getString("prepayid");
//            req.nonceStr = json.getString("noncestr");
//            req.timeStamp = json.getString("timestamp");
//            req.packageValue = json.getString("package");
//            req.sign = json.getString("sign");
//            req.extData = "app data"; // optional
//            mMsgApi.sendReq(req);//发送调起微信的请求
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(this, "解析异常", Toast.LENGTH_SHORT).show();
//        }
    }


    public void aliPay(String pay_params) {


        Intent intent = new Intent("com.zkwl.qhzwj.receiver.MY_BROADCAST");
        intent.putExtra("data", pay_params);
        intent.putExtra("type", "alipay");
        sendBroadcast(intent);

//        final Runnable payRunnable = new Runnable() {
//            @Override
//            public void run() {
//                PayTask alipay = new PayTask(ClassroomDetailsActivity.this);
//                Map<String, String> result = alipay.payV2(pay_params, true);
//                Log.e("msp", result.toString());
//
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        };
//
//        // 必须异步调用
//        Thread payThread = new Thread(payRunnable);
//        payThread.start();
    }




}
