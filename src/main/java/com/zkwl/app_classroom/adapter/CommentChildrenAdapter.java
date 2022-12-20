package com.zkwl.app_classroom.adapter;

import android.content.Context;
import android.view.View;

import com.alibaba.fastjson.JSONObject;
import com.king.base.adapter.BaseRecyclerAdapter;
import com.king.base.util.StringUtils;
import com.zkwl.app_classroom.databinding.ItemCommentchildrenBinding;

import java.util.List;

/**
 * Created by enjina on 2021/10/13.
 */

public class CommentChildrenAdapter extends BaseRecyclerAdapter<JSONObject, BindingHolder<ItemCommentchildrenBinding>> {



    onclick onclick;

    public CommentChildrenAdapter(Context context, List<JSONObject> imglist, int layoutId, onclick click) {
        super(context, imglist, layoutId);
        this.onclick = click;
    }



    @Override
    public void bindViewDatas(BindingHolder<ItemCommentchildrenBinding> helper, JSONObject item, int position) {

        helper.mBinding.nickName.setText(item.getString("nick_name")+":");
        helper.mBinding.msg.setText(item.getString("msg"));
        if (StringUtils.isNotBlank(item.getString("parent_user_name"))){
            helper.mBinding.nickName.setText(item.getString("nick_name")+":回复 "+item.getString("parent_user_name")+":");
        }
        helper.mBinding.rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclick.click(item);
            }
        });

    }


    public interface onclick{
        void click(JSONObject tump);
    }
}

