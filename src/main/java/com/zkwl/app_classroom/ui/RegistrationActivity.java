package com.zkwl.app_classroom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.zkwl.app_classroom.R;
import com.zkwl.app_classroom.adapter.RegistrationAdapter;
import com.zkwl.app_classroom.databinding.ActivityRegistrationBinding;
import com.zkwl.app_classroom.fragment.PayDetailFragment;
import com.zkwl.app_classroom.fragment.PayDialogListener;
import com.zkwl.app_classroom.model.ClassRomDetailsModel;
import com.zkwl.app_classroom.ui.pwd.PayPasswordDialog;
import com.zkwl.app_classroom.util.ClassroomImgUtil;
import com.zkwl.app_classroom.util.EncryptorUtils;
import com.zkwl.app_classroom.util.IsInstallWeChatOrAliPayUtil;
import com.zkwl.app_classroom.util.StatusBar;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

/**
 * 报名信息
 */


@AndroidEntryPoint
public class RegistrationActivity extends BaseActivity<ClassRomDetailsModel, ActivityRegistrationBinding> {

    private JSONObject tump;
    private RegistrationAdapter registrationAdapter;
    private List<JSONObject> registlist =new ArrayList<>();
    private Double banlnce;

    @Override
    public int getLayoutId() {
        return R.layout.activity_registration;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        registerMessageEvent(message -> {
            Timber.d("message:%s", message);
            ToastUtils.showToast(getContext(), message);
        });

        StatusBar.setStatusBar(this, true);
        getViewDataBinding().topbar.setTitle("报名信息");
        getViewDataBinding().topbar.addLeftBackImageButton().setOnClickListener(v -> finish());


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

        });

        getViewModel().getBalanceinfo();
        getViewModel().getliveDataBalanceinfo().observe(this,data ->{
            banlnce  =data.getData().getDouble("balance");
        });

        registrationAdapter =new RegistrationAdapter(this, registlist, R.layout.item_registration, new RegistrationAdapter.click() {
            @Override
            public void delete(JSONObject item, int position) {
                registlist.remove(position);
                registrationAdapter.notifyDataSetChanged();
                Log.e("delete","registlist"+registlist.toString());

                if (tump.getIntValue("is_charge") == 2) {
                    BigDecimal listsize =new BigDecimal(registlist.size());
                    BigDecimal price =tump.getBigDecimal("price");
                    BigDecimal setScale = price.multiply(listsize);
                    getViewDataBinding().price1.setText("¥"+setScale.toPlainString()+"");
                }
            }

            @Override
            public void update(JSONObject item, int position) {
                registlist.set(position,item);
                Log.e("update","registlist"+registlist.toString());
            }
        });
        getViewDataBinding().recyclerView.setAdapter(registrationAdapter);


        getViewDataBinding().classroomAddll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("nick_name","");
                jsonObject.put("mobile_phone","");
                registlist.add(jsonObject);
                registrationAdapter.notifyDataSetChanged();
                if (tump.getIntValue("is_charge") == 2) {
                    BigDecimal listsize =new BigDecimal(registlist.size());
                    BigDecimal price =tump.getBigDecimal("price");
                    BigDecimal setScale = price.multiply(listsize);
                    getViewDataBinding().price1.setText("¥"+setScale.toPlainString()+"");
                }
            }
        });

        getViewDataBinding().buytv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registlist.size()==0){
                    ToastUtils.showToast(getContext(),"请添加报名人!");
                    return;
                }
                for (int i=0;i<registlist.size();i++){
                    if (TextUtils.isEmpty(registlist.get(i).getString("nick_name")) || TextUtils.isEmpty(registlist.get(i).getString("mobile_phone"))){
                        ToastUtils.showToast(getContext(),"第"+(i+1)+"条报名信息不完整，请填写!");
                        return;
                    }
                }

                if (tump.getIntValue("is_charge") == 2) {
                    //是否收费
                    payShowDialog(tump.getDouble("price"));
                } else {
                    //免费支付渠道
                    getViewModel().CourseAlipay(registlist.toString(), tump.getIntValue("id") + "");
                }
            }
        });
        getViewModel().getCoursepayLiveData().observe(this, data -> {
            if (data.getData().getIntValue("pay_status") == 1) {
                payWChatPay(data.getData().getJSONObject("pay_params"));
            } else if (data.getData().getIntValue("pay_status") == 2) {
                ToastUtils.showToast(getContext(),"支付成功");
                finish();
            }
        });

        getViewModel().getliveDataCourseAlipay().observe(this, data -> {
            if (data.getData().getIntValue("pay_status") == 1) {
                aliPay(data.getData().getString("pay_params"));
            } else if (data.getData().getIntValue("pay_status") == 2) {
                ToastUtils.showToast(getContext(),"支付成功");
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getViewModel().getCourseinfo(getIntent().getStringExtra("id"));
    }

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
                if (IsInstallWeChatOrAliPayUtil.checkAliPayInstalled(RegistrationActivity.this)) {
                    getViewModel().CourseAlipay(registlist.toString(), tump.getIntValue("id") + "");
                } else {
                    ToastUtils.showToast(RegistrationActivity.this, "未安装支付宝，请下载....");
                }
            }

            @Override
            public void payNextWchat() {
                //微信
                if (IsInstallWeChatOrAliPayUtil.isWeixinAvilible(RegistrationActivity.this)) {
                    getViewModel().CourseWechatpay(registlist.toString(), tump.getIntValue("id") + "");
                } else {
                    ToastUtils.showToast(RegistrationActivity.this, "未安装微信，请下载....");
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
                        startActivity(new Intent(RegistrationActivity.this, InputPayPwdActivity.class));
                        finish();
                    }
                })
                .show();
    }

    private void payWChatPay(JSONObject data) {

        Intent intent = new Intent("com.zkwl.qhzwj.receiver.MY_BROADCAST");
        intent.putExtra("data", data.toString());
        intent.putExtra("type", "wechat");
        sendBroadcast(intent);
        finish();




    }


    public void aliPay(String pay_params) {


        Intent intent = new Intent("com.zkwl.qhzwj.receiver.MY_BROADCAST");
        intent.putExtra("data", pay_params);
        intent.putExtra("type", "alipay");
        sendBroadcast(intent);
        finish();

    }


    private void showInputPwdDialog() {
        final PayPasswordDialog dialog = new PayPasswordDialog(this, R.style.pwd_input_dialog);
        dialog.setDialogClick(new PayPasswordDialog.DialogClick() {
            @Override
            public void doConfirm(String password) {
                dialog.dismiss();
                getViewModel().CourseYue(registlist.toString(), tump.getIntValue("id") + "", EncryptorUtils.zgPwd(password));
            }
        });
        dialog.show();
    }


}
