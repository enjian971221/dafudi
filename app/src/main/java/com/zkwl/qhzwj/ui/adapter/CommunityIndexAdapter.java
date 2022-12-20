package com.zkwl.qhzwj.ui.adapter;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zkwl.qhzgyz.R;
import com.zkwl.qhzwj.bean.CommunityIndexBean;
import com.zkwl.qhzwj.bean.CommunityIndexBuildBean;
import com.zkwl.qhzwj.ui.rvadapter.BaseQuickAdapter;
import com.zkwl.qhzwj.ui.rvadapter.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2019/9/24.
 */

public class CommunityIndexAdapter extends BaseQuickAdapter<CommunityIndexBean, BaseViewHolder> {
    private CommunityIndexListener mCommunityIndexListener;

    public CommunityIndexAdapter(int layoutResId, @Nullable List<CommunityIndexBean> data, CommunityIndexListener listener) {
        super(layoutResId, data);
        mCommunityIndexListener = listener;
    }

    @Override
    protected void convert(BaseViewHolder helper, CommunityIndexBean item) {
        helper.setText(R.id.community_index_item_name, item.getCommunity_name());
        RecyclerView recyclerView = helper.getView(R.id.rv_community_index_item);
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
        if (item.getBuild_arr() != null) {
            recyclerView.setVisibility(View.VISIBLE);
            List<CommunityIndexBuildBean> build_arr = item.getBuild_arr();
            CommunityIndexBuildAdapter adapter = new CommunityIndexBuildAdapter(R.layout.community_index_buil_item, build_arr);
            adapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (position < build_arr.size()) {
                        mCommunityIndexListener.selectItem(build_arr.get(position), item.getCommunity_name(),item.getCompany_code());
                    }
                }
            });
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.setVisibility(View.GONE);
        }

    }
}
