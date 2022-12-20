package com.zkwl.app_classroom.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.king.base.util.ToastUtils;
import com.zkwl.app_classroom.R;
import com.zkwl.app_classroom.adapter.PayListAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * 底部弹窗Fragment
 */
public class PayDetailFragment extends DialogFragment {
    private RelativeLayout mRlPayWay, rePayDetail;
    private LinearLayout LinPayWay;
    private Button btnPay;
    private TextView mTvCloseOne;
    private TextView mTvPayType;//支付方式
    private TextView mTvShowType;//支付方式
    private String mPayType = "ali";
    private TextView mTvTypeCancel;
    private TextView mTvPayMoney;
    private LinearLayout mLlTypeAli;
    private ImageView mIvTypeAli;
    private LinearLayout mLLTypeWChat;
    private ImageView mIvTypeWChat;
    private LinearLayout mLlTypeMe;
    private ImageView mIvTypeMe;


    private double mOwnerbalance = 0;//业主的钱包金额
    private double mPayMoney = 0;//需要支付的金额
    private PayDialogListener mPayDialogListener;

    private String mGoodOrderType = "";


    private RecyclerView paylist;
    private LinearLayout isnotlist;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mGoodOrderType = bundle.getString("good_order_type");
        mOwnerbalance = bundle.getDouble("owner_balance", 0);
        mPayMoney = bundle.getDouble("pay_money", 0);


        Dialog dialog = new Dialog(getActivity(), R.style.PayBottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.pay_dialog_item);
        dialog.setCanceledOnTouchOutside(false); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.PayAnimBottom);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
//        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 3 / 5;
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 1 / 2;
        window.setAttributes(lp);
        initView(dialog);
        return dialog;
    }

    List<JSONObject> list = new ArrayList<>();
    ArrayList<String> pay_mode;
    PayListAdapter adapter;

    private void initView(Dialog dialog) {
        mRlPayWay = (RelativeLayout) dialog.findViewById(R.id.ll_pay_dialog_item_pay_way);
        rePayDetail = (RelativeLayout) dialog.findViewById(R.id.re_pay_detail);//付款详情
        LinPayWay = (LinearLayout) dialog.findViewById(R.id.lin_pay_way);//付款方式
        mTvPayMoney = (TextView) dialog.findViewById(R.id.tv_pay_dialog_item_pay_money);//付款方式
        mTvShowType = (TextView) dialog.findViewById(R.id.tv_pay_dialog_item_pay_type);//付款方式
        mTvShowType.setText(mGoodOrderType);
        BigDecimal mPayMoneyBigDecimal = new BigDecimal(mPayMoney);//不以科学计数法显示，正常显示保留两位小数
        mTvPayMoney.setText(mPayMoneyBigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP) + "");
        mTvPayType = (TextView) dialog.findViewById(R.id.tv_pay_dialog_item_type);//付款方式
        btnPay = (Button) dialog.findViewById(R.id.btn_confirm_pay);
        mTvCloseOne = (TextView) dialog.findViewById(R.id.tv_pay_dialog_item_close);
        mTvTypeCancel = (TextView) dialog.findViewById(R.id.tv_pay_dialog_item_type_cancel);
        mLlTypeAli = (LinearLayout) dialog.findViewById(R.id.ll_pay_dialog_item_type_ali);
        mIvTypeAli = (ImageView) dialog.findViewById(R.id.iv_pay_dialog_item_type_ali);
        mLLTypeWChat = (LinearLayout) dialog.findViewById(R.id.ll_pay_dialog_item_type_w);
        mIvTypeWChat = (ImageView) dialog.findViewById(R.id.iv_pay_dialog_item_type_w);
        mLlTypeMe = (LinearLayout) dialog.findViewById(R.id.ll_pay_dialog_item_type_me);
        mIvTypeMe = (ImageView) dialog.findViewById(R.id.iv_pay_dialog_item_type_me);
        paylist = (RecyclerView) dialog.findViewById(R.id.paylist);
        isnotlist = (LinearLayout) dialog.findViewById(R.id.isnotlist);
        mRlPayWay.setOnClickListener(listener);
        btnPay.setOnClickListener(listener);
        mTvCloseOne.setOnClickListener(listener);
        mTvTypeCancel.setOnClickListener(listener);
        mLlTypeAli.setOnClickListener(listener);
        mLLTypeWChat.setOnClickListener(listener);
        mLlTypeMe.setOnClickListener(listener);


        pay_mode = getArguments().getStringArrayList("pay_mode");
        if (pay_mode !=null && pay_mode.size() > 0) {
            paylist.setVisibility(View.VISIBLE);
            isnotlist.setVisibility(View.GONE);



            if (pay_mode.get(0).equals("支付宝支付")) {
                mPayType = "ali";
                mTvPayType.setText(pay_mode.get(0));
            } else if (pay_mode.get(0).equals("微信支付")) {
                mPayType = "w";
                mTvPayType.setText(pay_mode.get(0));
            } else {
                mPayType = "me";
                BigDecimal bigDecimal = new BigDecimal(mOwnerbalance);//不以科学计数法显示，正常显示保留两位小数
                mTvPayType.setText(pay_mode.get(0)+"(" + bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP) + "" + ")");
            }


            for (String tump : pay_mode) {
                JSONObject object = new JSONObject();
                object.put("text", tump);
                if ("ali".equals(mPayType) && tump.equals("支付宝支付")) {
                    object.put("select", true);
                }
                if ("w".equals(mPayType) && tump.equals("微信支付")) {
                    object.put("select", true);
                }
                if ("me".equals(mPayType) && tump.equals("余额支付")) {
                    object.put("select", true);
                }

                list.add(object);
            }
            paylist.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new PayListAdapter(getContext(),list,R.layout.adapter_paylist,  new PayListAdapter.onclick() {
                @Override
                public void click(String type) {
                    Animation slide_left_to_right = AnimationUtils.loadAnimation(getActivity(), R.anim.pay_slide_left_to_right);
                    Animation slide_left_to_left_in = AnimationUtils.loadAnimation(getActivity(), R.anim.pay_slide_left_to_left_in);
                    rePayDetail.startAnimation(slide_left_to_left_in);
                    rePayDetail.setVisibility(View.VISIBLE);
                    LinPayWay.startAnimation(slide_left_to_right);
                    LinPayWay.setVisibility(View.GONE);
                    if (type.equals("支付宝支付")) {
                        mPayType = "ali";
                        mTvPayType.setText(type);
                    } else if (type.equals("微信支付")) {
                        mPayType = "w";
                        mTvPayType.setText(type);
                    } else {
                        mPayType = "me";
                        BigDecimal bigDecimal = new BigDecimal(mOwnerbalance);//不以科学计数法显示，正常显示保留两位小数
                        mTvPayType.setText("余额支付"+"(" + bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP) + "" + ")");
                    }
                    list.clear();
                    for (String tump : pay_mode) {
                        JSONObject object = new JSONObject();
                        object.put("text", tump);
                        if ("ali".equals(mPayType) && tump.trim().equals("支付宝支付")) {
                            object.put("select", true);
                        }
                        if ("w".equals(mPayType) && tump.trim().equals("微信支付")) {
                            object.put("select", true);
                        }
                        if ("me".equals(mPayType) && tump.trim().equals("余额支付")) {
                            object.put("select", true);
                        }
                        list.add(object);
                    }
                    adapter.notifyDataSetChanged();
                }
            });
            paylist.setAdapter(adapter);


        } else {
            paylist.setVisibility(View.GONE);
        }

    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Animation slide_left_to_left = AnimationUtils.loadAnimation(getActivity(), R.anim.pay_slide_left_to_left);
            Animation slide_right_to_left = AnimationUtils.loadAnimation(getActivity(), R.anim.pay_slide_right_to_left);
            Animation slide_left_to_right = AnimationUtils.loadAnimation(getActivity(), R.anim.pay_slide_left_to_right);
            Animation slide_left_to_left_in = AnimationUtils.loadAnimation(getActivity(), R.anim.pay_slide_left_to_left_in);
            int id = view.getId();
            if (id == R.id.ll_pay_dialog_item_pay_way) {//选择方式
                rePayDetail.startAnimation(slide_left_to_left);
                rePayDetail.setVisibility(View.GONE);
                LinPayWay.startAnimation(slide_right_to_left);
                LinPayWay.setVisibility(View.VISIBLE);
                if ("ali".equals(mPayType)) {
                    mIvTypeAli.setVisibility(View.VISIBLE);
                    mIvTypeWChat.setVisibility(View.GONE);
                    mIvTypeMe.setVisibility(View.GONE);
                } else if ("w".equals(mPayType)) {
                    mIvTypeAli.setVisibility(View.GONE);
                    mIvTypeMe.setVisibility(View.GONE);
                    mIvTypeWChat.setVisibility(View.VISIBLE);
                } else {
                    mIvTypeAli.setVisibility(View.GONE);
                    mIvTypeMe.setVisibility(View.VISIBLE);
                    mIvTypeWChat.setVisibility(View.GONE);
                }
            } else if (id == R.id.btn_confirm_pay) {//确认付款
                getDialog().dismiss();
                if ("ali".equals(mPayType)) {
                    mPayDialogListener.payNextAli();
                } else if ("w".equals(mPayType)) {
                    mPayDialogListener.payNextWchat();
                } else {
                    mPayDialogListener.payNextMe();
                }
            } else if (id == R.id.tv_pay_dialog_item_close) {//取消付款
                getDialog().dismiss();
                mPayDialogListener.payCancel();
            } else if (id == R.id.tv_pay_dialog_item_type_cancel) {
                rePayDetail.startAnimation(slide_left_to_left_in);
                rePayDetail.setVisibility(View.VISIBLE);
                LinPayWay.startAnimation(slide_left_to_right);
                LinPayWay.setVisibility(View.GONE);
            } else if (id == R.id.ll_pay_dialog_item_type_ali) {//支付宝支付
                rePayDetail.startAnimation(slide_left_to_left_in);
                rePayDetail.setVisibility(View.VISIBLE);
                LinPayWay.startAnimation(slide_left_to_right);
                LinPayWay.setVisibility(View.GONE);
                mPayType = "ali";
                mTvPayType.setText("支付宝支付");
            } else if (id == R.id.ll_pay_dialog_item_type_w) {//微信支付
                rePayDetail.startAnimation(slide_left_to_left_in);
                rePayDetail.setVisibility(View.VISIBLE);
                LinPayWay.startAnimation(slide_left_to_right);
                LinPayWay.setVisibility(View.GONE);
                mPayType = "w";
                mTvPayType.setText("微信支付");
            } else if (id == R.id.ll_pay_dialog_item_type_me) {//余额支付
                if (mOwnerbalance >= mPayMoney) {
                    rePayDetail.startAnimation(slide_left_to_left_in);
                    rePayDetail.setVisibility(View.VISIBLE);
                    LinPayWay.startAnimation(slide_left_to_right);
                    LinPayWay.setVisibility(View.GONE);
                    mPayType = "me";
                    BigDecimal bigDecimal = new BigDecimal(mOwnerbalance);//不以科学计数法显示，正常显示保留两位小数
                    mTvPayType.setText("余额支付(" + bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP) + "" + ")");
                } else {
                    ToastUtils.showToast(getContext(), "余额不足");
                }
            }
        }
    };


    public void setPayDialogListener(PayDialogListener payDialogListener) {
        this.mPayDialogListener = payDialogListener;
    }
}