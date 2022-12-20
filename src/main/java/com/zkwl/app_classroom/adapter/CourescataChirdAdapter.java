package com.zkwl.app_classroom.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.king.base.adapter.BaseRecyclerAdapter;
import com.zkwl.app_classroom.R;
import com.zkwl.app_classroom.databinding.ItemCourescatachirdBinding;

import java.util.List;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class CourescataChirdAdapter extends BaseRecyclerAdapter<JSONObject, BindingHolder<ItemCourescatachirdBinding>> {

    click click;
    Context context;


    public CourescataChirdAdapter(Context context, List<JSONObject> imglist, int layoutId, click click) {
        super(context, imglist, layoutId);
        this.context = context;
        this.click = click;
    }



    @Override
    public void bindViewDatas(BindingHolder<ItemCourescatachirdBinding> holder, JSONObject item, int position) {

        holder.mBinding.title.setText(item.getString("title"));
        holder.mBinding.tvVideo.setText("第"+(position+1)+"节");

        if (item.getIntValue("is_charge")==1){
            holder.mBinding.buy.setBackgroundResource(R.drawable.shape_green10);
            holder.mBinding.buy.setText("免费试学");
            holder.mBinding.buy.setTextColor(Color.parseColor("#FFFFFF"));
        }else {
            holder.mBinding.buy.setBackgroundResource(R.drawable.shape_white10);
            holder.mBinding.buy.setText("需购买");
            holder.mBinding.buy.setTextColor(Color.parseColor("#F44D2B"));
        }
        holder.mBinding.duration.setText("时长："+item.getString("duration"));
        holder.mBinding.rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.itemclick(item,position);
            }
        });

    }


    public interface click {
        void itemclick(JSONObject item, int position);
    }
}