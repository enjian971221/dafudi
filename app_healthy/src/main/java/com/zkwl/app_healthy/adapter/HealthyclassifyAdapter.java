package com.zkwl.app_healthy.adapter;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.king.base.adapter.BaseRecyclerAdapter;
import com.zkwl.app_healthy.databinding.ItemHealthyhomeclassifyBinding;
import com.zkwl.app_healthy.util.HealthyImgUtil;

import java.util.List;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class HealthyclassifyAdapter extends BaseRecyclerAdapter<JSONObject, BindingHolder<ItemHealthyhomeclassifyBinding>> {

    click click;
    Context context;


    public HealthyclassifyAdapter(Context context, List<JSONObject> imglist, int layoutId, click click) {
        super(context, imglist, layoutId);
        this.context = context;
        this.click = click;
    }



    @Override
    public void bindViewDatas(BindingHolder<ItemHealthyhomeclassifyBinding> holder, JSONObject item, int position) {

        HealthyImgUtil.loadImage(holder.mBinding.logo,item.getString("logo"),15);
        holder.mBinding.title.setText(item.getString("title"));

        holder.mBinding.rootview.setOnClickListener(view -> click.itemclick(item,position));

    }


    public interface click {
        void itemclick(JSONObject item, int position);
    }
}