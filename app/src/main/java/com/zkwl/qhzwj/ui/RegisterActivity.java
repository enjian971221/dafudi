package com.zkwl.qhzwj.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.king.base.util.StringUtils;
import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.zkwl.qhzgyz.R;
import com.zkwl.qhzgyz.databinding.ActivityZhuceBinding;
import com.zkwl.qhzwj.LoginViewModel;
import com.zkwl.qhzwj.ui.down.DownTimerEnum;
import com.zkwl.qhzwj.ui.down.TimeDownUtil;
import com.zkwl.qhzwj.ui.down.TimerDownListener;
import com.zkwl.qhzwj.util.EncryptorUtils;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;


@AndroidEntryPoint
public class RegisterActivity extends BaseActivity<LoginViewModel, ActivityZhuceBinding> {

    private TimeDownUtil mTimeDownUtil;
    private String loginType = "pwd";
    private String mCommunity_id = "";


    @Override
    public int getLayoutId() {
        return R.layout.activity_zhuce;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        getViewDataBinding().back.setOnClickListener(view -> finish());
        mCommunity_id = getIntent().getStringExtra("community_id");
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



        getViewDataBinding().sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(getViewDataBinding().mEtPhone.getText().toString())) {
                    ToastUtils.showToast(getContext(),"请输入手机号");
                    return;
                }
                if (getViewDataBinding().mEtPhone.getText().toString().length() != 11) {
                    ToastUtils.showToast(getContext(),"请输入11位手机号");
                    return;
                }
                getViewModel().sendRegisterCode(getViewDataBinding().mEtPhone.getText().toString());
            }
        });

        getViewModel().getsendRegisterCode().observe(this,data ->{
            ToastUtils.showToast(getContext(),"发送成功");
            mTimeDownUtil.startDownTimer(DownTimerEnum.OneMinute);
            getViewDataBinding().sendCode.setClickable(false);
        });



        getViewDataBinding().submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInput()){
                    String zgPwd = EncryptorUtils.zgPwd(mInputPwd);
                    getViewModel().registerUser(mInputPhone, mInputCode, zgPwd, zgPwd, mCommunity_id);
                }
            }
        });

        getViewModel().getregisterUser().observe(this,data ->{
            ToastUtils.showToast(getContext(),"注册成功");
            finish();
        });

    }
    private String mInputPhone;
    private String mInputCode;
    private String mInputPwd;

    private boolean checkInput() {
        if (TextUtils.isEmpty(getViewDataBinding().mEtPhone.getText())) {
            ToastUtils.showToast(getContext(),"请输入手机号");
            return false;
        }
        mInputPhone = getViewDataBinding().mEtPhone.getText().toString().trim();
        if (mInputPhone.length() != 11) {
            ToastUtils.showToast(getContext(),"请输入11位手机号");
            return false;
        }
        if (TextUtils.isEmpty(getViewDataBinding().mEtCode.getText())) {
            ToastUtils.showToast(getContext(),"请输入验证码");
            return false;
        }
        mInputCode = getViewDataBinding().mEtCode.getText().toString().trim();
        if (mInputCode.length() != 6) {
            ToastUtils.showToast(getContext(),"请输入6位验证码");
            return false;
        }
        if (TextUtils.isEmpty(getViewDataBinding().mEtPwd.getText())) {
            ToastUtils.showToast(getContext(),"请输入密码");
            return false;
        }
        mInputPwd = getViewDataBinding().mEtPwd.getText().toString().trim();
        if (mInputPwd.length() < 6 || mInputPwd.length() > 20) {
            ToastUtils.showToast(getContext(),"请输入6-20位密码");
            return false;
        }
        if (TextUtils.isEmpty(getViewDataBinding().mEtPwdTwo.getText())) {
            ToastUtils.showToast(getContext(),"请输入确认密码");
            return false;
        }
        if (!StringUtils.equals(mInputPwd, getViewDataBinding().mEtPwdTwo.getText().toString().trim())) {
            ToastUtils.showToast(getContext(),"两次密码输入不一致");
            return false;
        }
        return true;
    }

}
