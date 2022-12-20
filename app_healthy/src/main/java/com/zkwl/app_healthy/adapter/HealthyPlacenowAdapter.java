package com.zkwl.app_healthy.adapter;

import android.content.Context;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.king.base.adapter.BaseRecyclerAdapter;
import com.zkwl.app_healthy.databinding.ItemHealthyplacenowBinding;
import com.zkwl.app_healthy.util.HealthyImgUtil;

import java.util.List;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class HealthyPlacenowAdapter extends BaseRecyclerAdapter<JSONObject, BindingHolder<ItemHealthyplacenowBinding>> {

    click click;
    Context context;

    private int selectpostion=-1;
    public void setpostion(int postion){
        selectpostion=postion;
        notifyDataSetChanged();
    }

    public HealthyPlacenowAdapter(Context context, List<JSONObject> imglist, int layoutId, click click) {
        super(context, imglist, layoutId);
        this.context = context;
        this.click = click;
    }



    @Override
    public void bindViewDatas(BindingHolder<ItemHealthyplacenowBinding> holder, JSONObject item, int position) {

        holder.mBinding.selectimg.setVisibility(View.GONE);
        HealthyImgUtil.loadImage(holder.mBinding.logo,item.getString("logo"),15);
        holder.mBinding.name.setText(item.getString("name"));
        holder.mBinding.area.setText(""+item.getString("area"));


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