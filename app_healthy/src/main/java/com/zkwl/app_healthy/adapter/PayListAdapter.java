package com.zkwl.app_healthy.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.king.base.adapter.BaseRecyclerAdapter;
import com.zkwl.app_healthy.R;
import com.zkwl.app_healthy.databinding.AdapterPaylistBinding;

import java.util.List;

/**
 * Created by enjina on 2021/10/13.
 */

public class PayListAdapter extends BaseRecyclerAdapter<JSONObject, BindingHolder<AdapterPaylistBinding>> {



    onclick onclick;

    public PayListAdapter(Context context, List<JSONObject> imglist, int layoutId, onclick click) {
        super(context, imglist, layoutId);
        this.onclick = click;
    }



    @Override
    public void bindViewDatas(BindingHolder<AdapterPaylistBinding> helper, JSONObject item, int position) {

        helper.setText(R.id.text, item.getString("text"));
        if (item.getString("text").trim().equals("支付宝支付")) {
            ((ImageView) helper.getView(R.id.img)).setImageResource(R.mipmap.ic_pay_ali);
        } else if (item.getString("text").trim().equals("微信支付")) {
            ((ImageView) helper.getView(R.id.img)).setImageResource(R.mipmap.ic_pay_w);
        } else {
            ((ImageView) helper.getView(R.id.img)).setImageResource(R.mipmap.ic_pay_me);
        }

        if (item.getBoolean("select")!=null && item.getBoolean("select")){
            helper.getView(R.id.iv_pay_dialog_item_type_w).setVisibility(View.VISIBLE);
        }else {
            helper.getView(R.id.iv_pay_dialog_item_type_w).setVisibility(View.GONE);
        }

        helper.getView(R.id.ll_pay_dialog_item_type_w).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclick.click(item.getString("text").trim());
            }
        });

    }


    public interface onclick{
        void click(String tump);
    }
}

