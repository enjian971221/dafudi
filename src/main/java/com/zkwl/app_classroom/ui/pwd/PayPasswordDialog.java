package com.zkwl.app_classroom.ui.pwd;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zkwl.app_classroom.R;


/**
 * Created by Administrator on 2018/3/19.
 */

public class PayPasswordDialog extends Dialog implements View.OnClickListener {
    Context context;
    private TextView tvReturn;
    private PassWordLayout payPassword;
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    private TextView tv6;
    private TextView tv7;
    private TextView tv8;
    private TextView tv9;
    private TextView tv;
    private TextView tvDel;

    public PayPasswordDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.pwd_pay_dialog, null);
        setContentView(view);
        initView();

        Window window = getWindow();
        WindowManager.LayoutParams mParams = window.getAttributes();
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(mParams);
        setCanceledOnTouchOutside(true);


        tvReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payPassword.removePwd();
            }
        });
        payPassword.setPwdChangeListener(new PassWordLayout.pwdChangeListener() {
            @Override
            public void onChange(String pwd) {

            }

            @Override
            public void onNull() {

            }

            @Override
            public void onFinished(String password) {
                if (dialogClick != null) {
                    dialogClick.doConfirm(password);
                }
            }
        });
        tv.setOnClickListener(this);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
        tv8.setOnClickListener(this);
        tv9.setOnClickListener(this);

    }

    private void initView() {
        tvReturn = (TextView) findViewById(R.id.tv_return);
        payPassword = (PassWordLayout) findViewById(R.id.passLayout);
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv6 = (TextView) findViewById(R.id.tv6);
        tv7 = (TextView) findViewById(R.id.tv7);
        tv8 = (TextView) findViewById(R.id.tv8);
        tv9 = (TextView) findViewById(R.id.tv9);
        tv = (TextView) findViewById(R.id.tv);
        tvDel = (TextView) findViewById(R.id.tv_del);
    }

    DialogClick dialogClick;

    public void setDialogClick(DialogClick dialogClick) {
        this.dialogClick = dialogClick;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv) {
            payPassword.addPwd("0");
        } else if (id == R.id.tv1) {
            payPassword.addPwd("1");
        } else if (id == R.id.tv2) {
            payPassword.addPwd("2");
        } else if (id == R.id.tv3) {
            payPassword.addPwd("3");
        } else if (id == R.id.tv4) {
            payPassword.addPwd("4");
        } else if (id == R.id.tv5) {
            payPassword.addPwd("5");
        } else if (id == R.id.tv6) {
            payPassword.addPwd("6");
        } else if (id == R.id.tv7) {
            payPassword.addPwd("7");
        } else if (id == R.id.tv8) {
            payPassword.addPwd("8");
        } else if (id == R.id.tv9) {
            payPassword.addPwd("9");
        }
    }

    public interface DialogClick {
        void doConfirm(String password);
    }

}