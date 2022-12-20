package com.zkwl.app_classroom.adapter;

import android.content.Context;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.king.base.adapter.BaseRecyclerAdapter;
import com.zkwl.app_classroom.R;
import com.zkwl.app_classroom.databinding.ItemCommentBinding;
import com.zkwl.app_classroom.util.ClassroomImgUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by enjina on 2021/10/13.
 */

public class CommentAdapter extends BaseRecyclerAdapter<JSONObject, BindingHolder<ItemCommentBinding>> {



    onclick onclick;
    Context context;
    public CommentAdapter(Context context, List<JSONObject> imglist, int layoutId, onclick click) {
        super(context, imglist, layoutId);
        this.context = context;
        this.onclick = click;
    }



    @Override
    public void bindViewDatas(BindingHolder<ItemCommentBinding> helper, JSONObject item, int position) {

        ClassroomImgUtil.loadImage(helper.mBinding.photo,item.getString("photo"),15);
        helper.mBinding.nickName.setText(item.getString("nick_name"));
        helper.mBinding.addTime.setText(item.getString("add_time"));
        helper.mBinding.msg.setText(item.getString("msg"));
        helper.mBinding.likeCount.setText(item.getIntValue("like_count")+"");
        if (item.getIntValue("is_like")==1){
            helper.mBinding.dianzanimg.setImageResource(R.mipmap.icon_classroom_commnet1);
        }else {
            helper.mBinding.dianzanimg.setImageResource(R.mipmap.icon_classroom_commnet3);
        }
        if (item.getJSONArray("children")!=null && item.getJSONArray("children").size()>0){
            helper.mBinding.childrensize.setText(item.getJSONArray("children").size()+"");
            List<JSONObject> imglist  = new ArrayList<>();
            for(int i = 0;i<item.getJSONArray("children").size();i++){
                imglist.add(item.getJSONArray("children").getJSONObject(i));
            }
            CommentChildrenAdapter childrenAdapter =new CommentChildrenAdapter(context, imglist, R.layout.item_commentchildren, new CommentChildrenAdapter.onclick() {
                @Override
                public void click(JSONObject tump) {
                    onclick.erjiclick(tump);
                }
            });
            helper.mBinding.recyclerView.setAdapter(childrenAdapter);

        }else {
            helper.mBinding.recyclerView.setVisibility(View.GONE);
        }


        helper.mBinding.dianzanll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.dianzan(item);
            }
        });

        helper.mBinding.msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.click(item);
            }
        });
    }


    public interface onclick{
        void click(JSONObject tump);
        void erjiclick(JSONObject tump);
        void dianzan(JSONObject tump);
    }
}

