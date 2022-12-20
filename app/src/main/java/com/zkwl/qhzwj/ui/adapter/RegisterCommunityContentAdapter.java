package com.zkwl.qhzwj.ui.adapter;

import android.content.Context;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.king.base.adapter.BaseRecyclerAdapter;
import com.zkwl.app_classroom.adapter.BindingHolder;
import com.zkwl.qhzgyz.R;
import com.zkwl.qhzgyz.databinding.ItemRegisterCommunityContentBinding;

import java.util.List;

public class RegisterCommunityContentAdapter extends BaseRecyclerAdapter<JSONObject, BindingHolder<ItemRegisterCommunityContentBinding>> {

    click click;
    Context context;


    public RegisterCommunityContentAdapter(Context context, List<JSONObject> imglist, int layoutId, click click) {
        super(context, imglist, layoutId);
        this.context = context;
        this.click = click;
    }



    @Override
    public void bindViewDatas(BindingHolder<ItemRegisterCommunityContentBinding> helper, JSONObject item, int position) {

        helper.setText(R.id.register_community_content_item_title, item.getString("community_name"));
        helper.getView(R.id.ll_register_community_content_item).setOnClickListener(new View.OnClickListener() {
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