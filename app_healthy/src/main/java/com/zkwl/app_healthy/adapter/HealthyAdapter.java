package com.zkwl.app_healthy.adapter;

import android.content.Context;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.king.base.adapter.BaseRecyclerAdapter;
import com.zkwl.app_healthy.databinding.ItemHealthyhomeBinding;
import com.zkwl.app_healthy.util.HealthyImgUtil;

import java.util.List;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class HealthyAdapter extends BaseRecyclerAdapter<JSONObject, BindingHolder<ItemHealthyhomeBinding>> {

    click click;
    Context context;


    public HealthyAdapter(Context context, List<JSONObject> imglist, int layoutId, click click) {
        super(context, imglist, layoutId);
        this.context = context;
        this.click = click;
    }



    @Override
    public void bindViewDatas(BindingHolder<ItemHealthyhomeBinding> holder, JSONObject item, int position) {

        HealthyImgUtil.loadImage(holder.mBinding.goodsImg,item.getString("goods_img"),15);
        holder.mBinding.goodsName.setText(item.getString("goods_name"));

        if (item.getIntValue("goods_type")==1){
            holder.mBinding.vipPrice.setVisibility(View.GONE);
            holder.mBinding.goodsPrice.setText("¥"+item.getString("goods_price"));
        }else {
            holder.mBinding.goodsPrice.setText("原价 ¥"+item.getString("goods_price"));
            holder.mBinding.vipPrice.setText("原价 ¥"+item.getString("vip_price"));
        }

        holder.mBinding.rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.itemclick(item,position);
            }
        });


    }


    public interface click {
        void itemclick(JSONObject item, int position);
    }
}