package com.zkwl.qhzwj.ui;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.king.base.util.StringUtils;
import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.orhanobut.logger.Logger;
import com.zkwl.qhzgyz.R;
import com.zkwl.qhzgyz.databinding.ActivityLoginBinding;
import com.zkwl.qhzwj.LoginViewModel;
import com.zkwl.qhzwj.MainActivity;
import com.zkwl.qhzwj.api.Constants;
import com.zkwl.qhzwj.bean.UserLoginBean;
import com.zkwl.qhzwj.ui.down.DownTimerEnum;
import com.zkwl.qhzwj.ui.down.TimeDownUtil;
import com.zkwl.qhzwj.ui.down.TimerDownListener;
import com.zkwl.qhzwj.util.EncryptorUtils;
import com.zkwl.qhzwj.util.SpsUtil;
import com.zkwl.qhzwj.util.StatusBar;
import com.zkwl.qhzwj.util.UserDataManager;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class LoginActivity extends BaseActivity<LoginViewModel, ActivityLoginBinding> {

    private String mInputPhone;//输入的手机号
    private String mInputPwd;//输入的密码
    private String mDevice_id = "";
    private TimeDownUtil mTimeDownUtil;
    private String loginType = "pwd";


    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBar.setStatusBar(this, true);
        registerMessageEvent(message -> {
            Timber.d("message:%s", message);
            ToastUtils.showToast(getContext(), message);
        });
        mTimeDownUtil = TimeDownUtil.getInstance();
        mTimeDownUtil.setTimerDownListener(new TimerDownListener() {
            @Override
            public void tiemChange(int timecount) {
                getViewDataBinding().sendCode.setText(timecount + "s");
            }

            @Override
            public void timeEnd() {
                getViewDataBinding().sendCode.setText("发送验证码");
                getViewDataBinding().sendCode.setClickable(true);
            }
        });
        if (!SpsUtil.getBoolean("user_is_read_privacy", false)) {
            showSecurityDialog();
        }

        getViewDataBinding().submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(getViewDataBinding().mEtPhone.getText().toString())) {
                    ToastUtils.showToast(getContext(), "请输入11位手机号");
                    return;
                }
                mInputPhone = getViewDataBinding().mEtPhone.getText().toString();
                if (mInputPhone.length() != 11) {
                    ToastUtils.showToast(getContext(), "请输入11位手机号");
                    return;
                }
                //登录类型 1：验证码登录，2：密码登录
                String login_type = "";
                if ("pwd".equals(loginType)) {
                    login_type = "2";
                    if (TextUtils.isEmpty(getViewDataBinding().mEtPwd.getText().toString())) {
                        ToastUtils.showToast(getContext(), "请输入6-11位密码");
                        return;
                    }
                    mInputPwd = getViewDataBinding().mEtPwd.getText().toString();
                    if (mInputPwd.length() < 6 || mInputPwd.length() > 20) {
                        ToastUtils.showToast(getContext(), "请输入6-11位密码");
                        return;
                    }
                } else {
                    login_type = "1";
                    if (TextUtils.isEmpty(getViewDataBinding().mEtCode.getText())) {
                        ToastUtils.showToast(getContext(), "请输入验证码");
                        return;
                    }
                    mInputPwd = getViewDataBinding().mEtCode.getText().toString();
                    if (mInputPwd.length() != 6) {
                        ToastUtils.showToast(getContext(), "请输入6位验证码");
                        return;
                    }
                }
                getViewModel().Login(mInputPhone, EncryptorUtils.zgPwd(mInputPwd),mDevice_id,login_type);
            }
        });

        getViewModel().getLogindata().observe(this,data ->{
            if (data.getData() != null) {
                UserLoginBean loginBean = data.getData();
                UserDataManager.saveUserInfo(loginBean);
                Logger.d("登录成功-->" + loginBean.getArea_token().toString());
                SpsUtil.put(Constants.USER_IS_FRIST_LOGIN, true);
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                finish();
            }
            SpsUtil.put("userinfo",data.getData().toString());
        });
        getViewModel().getuserLoginSendCode().observe(this,data ->{
            ToastUtils.showToast(getContext(),"发送成功");
            mTimeDownUtil.startDownTimer(DownTimerEnum.OneMinute);
            getViewDataBinding().sendCode.setClickable(false);
        });


        getViewDataBinding().zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RegisterShowActivity.class));
            }
        });

        getViewDataBinding().sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(getViewDataBinding().mEtPhone.getText().toString())) {
                    ToastUtils.showToast(getContext(),"请输入手机号");
                    return;
                }
                mInputPhone = getViewDataBinding().mEtPhone.getText().toString();
                if (mInputPhone.length() != 11) {
                    ToastUtils.showToast(getContext(),"请输入11位手机号");
                    return;
                }
                getViewModel().userLoginSendCode(mInputPhone);
            }
        });


        getViewDataBinding().codeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getViewDataBinding().codeTv.getText().toString().equals("验证码登录")){
                    loginType="code";
                    getViewDataBinding().codell.setVisibility(View.VISIBLE);
                    getViewDataBinding().passll.setVisibility(View.GONE);
                    getViewDataBinding().codeTv.setText("密码登录");
                }else {
                    loginType = "pwd";
                    getViewDataBinding().codell.setVisibility(View.GONE);
                    getViewDataBinding().passll.setVisibility(View.VISIBLE);
                    getViewDataBinding().codeTv.setText("验证码登录");
                }
            }
        });

    }

    private String getPushDeviceId() {
        try {
            String deviceId = PushServiceFactory.getCloudPushService().getDeviceId();
            if (StringUtils.isNotBlank(deviceId)) {
                return deviceId;
            } else {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    private Dialog securityDialog;

    private void showSecurityDialog() {
        securityDialog = new Dialog(this, R.style.dialog_center);
        securityDialog.setCancelable(false);//返回键也会屏蔽
        securityDialog.setCanceledOnTouchOutside(false);
        View view = View.inflate(this, R.layout.dialog_sercurity, null);
        TextView tv_msg = view.findViewById(R.id.sercurity_tv_msgnotice);
        TextView tv_cancel = view.findViewById(R.id.sercurity_tv_cancel);
        TextView tv_positive = view.findViewById(R.id.sercurity_tv_positive);
        SpannableStringBuilder spannable = new SpannableStringBuilder(tv_msg.getText());
        spannable.setSpan(new ForegroundColorSpan(Color.parseColor("#C89C3C")), 129, 140, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_msg.setMovementMethod(LinkMovementMethod.getInstance());
        spannable.setSpan(new TextClick(), 129, 140, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_msg.setText(spannable);
        tv_msg.setHighlightColor(Color.parseColor("#00ffffff"));
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityDialog.dismiss();
                finish();
            }
        });
        tv_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                securityDialog.dismiss();
                SpsUtil.put("user_is_read_privacy", true);
            }
        });
        securityDialog.setContentView(view);
        securityDialog.show();
    }

    private class TextClick extends ClickableSpan {
        @Override
        public void onClick(View widget) { //在此处理点击事件
            startActivity(new Intent(LoginActivity.this, PrivacyActivity.class));
        }

        @Override
        public void updateDrawState(@NonNull TextPaint ds) {
            ds.setColor(Color.parseColor("#C89C3C"));
        }
    }


}
