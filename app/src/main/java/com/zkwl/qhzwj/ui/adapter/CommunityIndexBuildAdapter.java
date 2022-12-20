package com.zkwl.qhzwj.ui.adapter;


import androidx.annotation.Nullable;

import com.zkwl.qhzgyz.R;
import com.zkwl.qhzwj.bean.CommunityIndexBuildBean;
import com.zkwl.qhzwj.ui.rvadapter.BaseQuickAdapter;
import com.zkwl.qhzwj.ui.rvadapter.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2019/9/24.
 */

public class CommunityIndexBuildAdapter extends BaseQuickAdapter<CommunityIndexBuildBean, BaseViewHolder> {
    public CommunityIndexBuildAdapter(int layoutResId, @Nullable List<CommunityIndexBuildBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommunityIndexBuildBean item) {
        helper.setText(R.id.community_index_buil_item_name, item.getBuild_name());
    }
}
