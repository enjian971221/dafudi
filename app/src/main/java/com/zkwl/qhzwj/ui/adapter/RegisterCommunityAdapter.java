package com.zkwl.qhzwj.ui.adapter;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.king.base.adapter.BaseRecyclerAdapter;
import com.zkwl.app_classroom.adapter.BindingHolder;
import com.zkwl.qhzgyz.R;
import com.zkwl.qhzgyz.databinding.ItemRegisterCommunityHeaderBinding;

import java.util.ArrayList;
import java.util.List;

public class RegisterCommunityAdapter  extends BaseRecyclerAdapter<JSONObject, BindingHolder<ItemRegisterCommunityHeaderBinding>> {

    click click;
    Context context;


    public RegisterCommunityAdapter(Context context, List<JSONObject> imglist, int layoutId, click click) {
        super(context, imglist, layoutId);
        this.context = context;
        this.click = click;
    }



    @Override
    public void bindViewDatas(BindingHolder<ItemRegisterCommunityHeaderBinding> helper, JSONObject jsonObject, int position) {

        helper.setText(R.id.register_community_header_item_title, jsonObject.getString("title"));
        RecyclerView recyclerView = helper.getView(R.id.register_community_header_item_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        JSONArray list = jsonObject.getJSONArray("list");
        List<JSONObject> imglist  = new ArrayList<>();
        for(int i = 0;i<list.size();i++){
            imglist.add(list.getJSONObject(i));
        }
        Log.e("imglist",""+imglist.toString());
        RegisterCommunityContentAdapter contentAdapter =new RegisterCommunityContentAdapter(context, imglist, R.layout.item_register_community_content, new RegisterCommunityContentAdapter.click() {
            @Override
            public void itemclick(JSONObject item, int position) {
                    click.itemclick(item,position);
            }
        });
        recyclerView.setAdapter(contentAdapter);
    }


    public interface click {
        void itemclick(JSONObject item, int position);
    }
}