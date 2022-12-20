package com.zkwl.qhzwj.ui.adapter;

import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.king.base.adapter.BaseRecyclerAdapter;
import com.zkwl.app_classroom.adapter.BindingHolder;
import com.zkwl.qhzgyz.databinding.ItemHometabBinding;
import com.zkwl.qhzwj.util.ImgUtil;

import java.util.List;

public class HomeTabAdapter extends BaseRecyclerAdapter<JSONObject, BindingHolder<ItemHometabBinding>> {

    click click;
    Context context;


    public HomeTabAdapter(Context context, List<JSONObject> imglist, int layoutId, click click) {
        super(context, imglist, layoutId);
        this.context = context;
        this.click = click;
    }



    @Override
    public void bindViewDatas(BindingHolder<ItemHometabBinding> helper, JSONObject jsonObject, int position) {
        helper.getBinding().homeTabItemTitle.setText(""+jsonObject.getString("title"));
        ImgUtil.loadImage(helper.getBinding().homeTabItemIcon,jsonObject.getString("icon"));

    }


    public interface click {
        void itemclick(JSONObject item, int position);
    }
}