package com.zkwl.app_classroom.adapter;

import android.content.Context;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.king.base.adapter.BaseRecyclerAdapter;
import com.zkwl.app_classroom.R;
import com.zkwl.app_classroom.databinding.ItemClassroomhomeBinding;
import com.zkwl.app_classroom.util.ClassroomImgUtil;

import java.util.List;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public class ClassHomeAdapter extends BaseRecyclerAdapter<JSONObject, BindingHolder<ItemClassroomhomeBinding>> {

    click click;
    Context context;


    public ClassHomeAdapter(Context context, List<JSONObject> imglist, int layoutId, click click) {
        super(context, imglist, layoutId);
        this.context = context;
        this.click = click;
    }



    @Override
    public void bindViewDatas(BindingHolder<ItemClassroomhomeBinding> holder, JSONObject item, int position) {

        holder.mBinding.title.setText(item.getString("title"));
        holder.mBinding.peopleNumber.setText(item.getIntValue("people_number")+"人在线学习");
        if (item.getIntValue("is_charge")==2){
            holder.mBinding.price.setText("¥" + item.getString("price"));
        }else {
            holder.mBinding.price.setText("免费");
        }




        ClassroomImgUtil.loadImage(holder.mBinding.goodimg,item.getString("image_url"),20);


        if (item.getIntValue("course_type")==1){
            holder.mBinding.buy.setBackgroundResource(R.drawable.shape_green10);
            holder.mBinding.buy.setText("立即学习");
        }else {
            holder.mBinding.buy.setBackgroundResource(R.drawable.shape_pink10);
            holder.mBinding.buy.setText("立即报名");
        }
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