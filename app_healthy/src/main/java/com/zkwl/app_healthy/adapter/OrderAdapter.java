package com.zkwl.app_healthy.adapter;

import android.content.Context;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.king.base.adapter.BaseRecyclerAdapter;
import com.zkwl.app_healthy.databinding.ItemOrderBinding;
import com.zkwl.app_healthy.util.HealthyImgUtil;

import java.util.List;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class OrderAdapter extends BaseRecyclerAdapter<JSONObject, BindingHolder<ItemOrderBinding>> {

    click click;
    Context context;


    public OrderAdapter(Context context, List<JSONObject> imglist, int layoutId, click click) {
        super(context, imglist, layoutId);
        this.context = context;
        this.click = click;
    }



    @Override
    public void bindViewDatas(BindingHolder<ItemOrderBinding> holder, JSONObject item, int position) {

        HealthyImgUtil.loadImage(holder.mBinding.logo,item.getString("img"),2);
        holder.mBinding.name.setText(item.getString("goods_name"));
        if (item.getIntValue("status")==1){
            holder.mBinding.area.setText("待付款:¥"+item.getString("order_price"));
            holder.mBinding.buytv.setText("去支付");
        }else if (item.getIntValue("status")==2){
            holder.mBinding.area.setText("待消费:¥"+item.getString("order_price"));
            holder.mBinding.buytv.setText("去消费");
        }else if (item.getIntValue("status")==3){
            holder.mBinding.area.setText("已完成:¥"+item.getString("order_price"));
            holder.mBinding.buytv.setText("再次购买");
        }

        holder.mBinding.buytv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getIntValue("status")==1){
                    click.itempay(item,position);

                }else if (item.getIntValue("status")==2){
                    click.itemdetails(item,position);

                }else if (item.getIntValue("status")==3){
                    click.itembuy(item,position);
                }
            }
        });
    }


    public interface click {
        void itemdetails(JSONObject item, int position);
        void itembuy(JSONObject item, int position);
        void itempay(JSONObject item, int position);
    }
}