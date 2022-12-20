package com.zkwl.app_classroom.adapter;

import android.content.Context;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.king.base.adapter.BaseRecyclerAdapter;
import com.zkwl.app_classroom.R;
import com.zkwl.app_classroom.databinding.ItemCourescataBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class CourescataAdapter extends BaseRecyclerAdapter<JSONObject, BindingHolder<ItemCourescataBinding>> {

    click click;
    Context context;

    int user_buy=0;

    public void setUser_buy(int user_buy) {
        this.user_buy = user_buy;
    }


    public CourescataAdapter(Context context, List<JSONObject> imglist, int layoutId, click click) {
        super(context, imglist, layoutId);
        this.context = context;
        this.click = click;
    }


    @Override
    public void bindViewDatas(BindingHolder<ItemCourescataBinding> holder, JSONObject item, int position) {

        holder.mBinding.title.setText(item.getString("title"));
        JSONArray chapter_data = item.getJSONArray("class_hour_data");
        List<JSONObject> rclist = new ArrayList<>();


        CourescataChirdAdapter courescataChirdAdapter = new CourescataChirdAdapter(context, rclist, R.layout.item_courescatachird, new CourescataChirdAdapter.click() {
            @Override
            public void itemclick(JSONObject item, int position) {
                click.itemclick(item, position);
            }
        });
        holder.mBinding.recyclerView.setAdapter(courescataChirdAdapter);


        for (int i = 0; i < chapter_data.size(); i++) {
            rclist.add(chapter_data.getJSONObject(i));
        }
        courescataChirdAdapter.notifyDataSetChanged();

    }


    public interface click {
        void itemclick(JSONObject item, int position);
    }
}