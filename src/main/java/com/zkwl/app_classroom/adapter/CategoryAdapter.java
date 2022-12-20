package com.zkwl.app_classroom.adapter;

import android.content.Context;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.king.base.adapter.BaseRecyclerAdapter;
import com.zkwl.app_classroom.databinding.ItemCategoryBinding;
import com.zkwl.app_classroom.util.ClassroomImgUtil;

import java.util.List;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class CategoryAdapter extends BaseRecyclerAdapter<JSONObject, BindingHolder<ItemCategoryBinding>> {

    click click;
    Context context;


    public CategoryAdapter(Context context, List<JSONObject> imglist, int layoutId, click click) {
        super(context, imglist, layoutId);
        this.context = context;
        this.click = click;
    }



    @Override
    public void bindViewDatas(BindingHolder<ItemCategoryBinding> holder, JSONObject item, int position) {

        holder.mBinding.title.setText(item.getString("title"));
        ClassroomImgUtil.loadImage(holder.mBinding.goodimg,item.getString("image_url"),5);
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