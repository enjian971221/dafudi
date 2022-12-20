package com.zkwl.qhzwj.ui.adapter;

import android.content.Context;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.king.base.adapter.BaseRecyclerAdapter;
import com.zkwl.app_classroom.adapter.BindingHolder;
import com.zkwl.qhzgyz.databinding.ItemGoodhomeBinding;
import com.zkwl.qhzwj.util.ImgUtil;

import java.util.List;

public class HomeGoodsAdapter extends BaseRecyclerAdapter<JSONObject, BindingHolder<ItemGoodhomeBinding>> {

    click click;
    Context context;


    public HomeGoodsAdapter(Context context, List<JSONObject> imglist, int layoutId, click click) {
        super(context, imglist, layoutId);
        this.context = context;
        this.click = click;
    }



    @Override
    public void bindViewDatas(BindingHolder<ItemGoodhomeBinding> helper, JSONObject jsonObject, int position) {
        helper.getBinding().goodsName.setText(jsonObject.getString("goods_name"));
        helper.getBinding().memberPrice.setText("¥"+jsonObject.getString("member_price"));
        ImgUtil.loadImage(helper.getBinding().goodimg,jsonObject.getString("image_url"));

        helper.getBinding().rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click.itemclick(jsonObject,position);
            }
        });

    }


    public interface click {
        void itemclick(JSONObject item, int position);
    }
}