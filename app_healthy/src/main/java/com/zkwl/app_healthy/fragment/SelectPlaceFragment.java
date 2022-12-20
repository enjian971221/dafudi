package com.zkwl.app_healthy.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.king.base.util.ToastUtils;
import com.zkwl.app_healthy.R;
import com.zkwl.app_healthy.adapter.HealthyPlaceAdapter;
import com.zkwl.app_healthy.adapter.HealthyPlacenowAdapter;
import com.zkwl.app_healthy.ui.SearchMoreActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * 底部弹窗Fragment
 */
public class SelectPlaceFragment extends DialogFragment {


    private SelectplaceDialogListener mSelectplaceDialogListener;
    private HealthyPlaceAdapter healthyPlaceAdapter;
    private HealthyPlacenowAdapter healthyPlacenowAdapter;
    private List<JSONObject> imglist = new ArrayList<>();
    private List<JSONObject> placelist = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();

        Dialog dialog = new Dialog(getActivity(), R.style.PayBottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.selectplace_dialog_item);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.PayAnimBottom);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 4 / 5;
//        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 1 / 2;
        window.setAttributes(lp);


        ImageView seemore = dialog.findViewById(R.id.seemore);
        TextView btn_confirm_pay = dialog.findViewById(R.id.btn_confirm_pay);
        RecyclerView recyclerViewnow = (RecyclerView)dialog.findViewById(R.id.recyclerViewnow);
        RecyclerView recyclerViewplace = (RecyclerView)dialog.findViewById(R.id.recyclerViewplace);
        seemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult (new Intent(getActivity(), SearchMoreActivity.class), 22222);
            }
        });

        btn_confirm_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (placelist== null || placelist.size()==0){
                    ToastUtils.showToast(getContext(),"请选择门店");
                    return;
                }
                JSONObject jsonObject = placelist.get(0);
                getDialog().dismiss();
                mSelectplaceDialogListener.payNext(jsonObject);

            }
        });


        String data = bundle.getString("data");
        JSONArray jsonArray = JSONArray.parseArray(data);

        imglist.clear();

        for (int i = 0; i < jsonArray.size(); i++) {
            imglist.add(jsonArray.getJSONObject(i));
        }


        healthyPlacenowAdapter =new HealthyPlacenowAdapter(getActivity(), placelist, R.layout.item_healthyplacenow, new HealthyPlacenowAdapter.click() {
            @Override
            public void itemclick(JSONObject item, int position) {

            }
        });
        recyclerViewnow.setAdapter(healthyPlacenowAdapter);

        healthyPlaceAdapter = new HealthyPlaceAdapter(getActivity(), imglist, R.layout.item_healthyplace, new HealthyPlaceAdapter.click() {
            @Override
            public void itemclick(JSONObject item, int position) {
                healthyPlaceAdapter.setpostion(position);

                placelist.clear();
                placelist.add(item);
                healthyPlacenowAdapter.notifyDataSetChanged();

            }
        });
        recyclerViewplace.setAdapter(healthyPlaceAdapter);



        return dialog;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== RESULT_OK){
            placelist.clear();
            String items = data.getStringExtra("item");
            JSONObject item  = JSONObject.parseObject(items);

            placelist.add(item);
            healthyPlacenowAdapter.notifyDataSetChanged();



        }
    }

    public void setPayDialogListener(SelectplaceDialogListener selectplaceDialogListener) {
        this.mSelectplaceDialogListener = selectplaceDialogListener;
    }
}