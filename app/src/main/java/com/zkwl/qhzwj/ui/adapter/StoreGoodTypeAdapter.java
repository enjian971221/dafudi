package com.zkwl.qhzwj.ui.adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zkwl.qhzgyz.R;
import com.zkwl.qhzwj.bean.StoreGoodTypeBean;
import com.zkwl.qhzwj.ui.rvadapter.BaseQuickAdapter;
import com.zkwl.qhzwj.ui.rvadapter.BaseViewHolder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2019/9/16.
 */

public class StoreGoodTypeAdapter extends BaseQuickAdapter<StoreGoodTypeBean, BaseViewHolder> {

    private Set<String> mSet = new HashSet<>();
    private StoreGoodTypeListener mStoreGoodTypeListener;

    public StoreGoodTypeAdapter(int layoutResId, @Nullable List<StoreGoodTypeBean> data, StoreGoodTypeListener listener) {
        super(layoutResId, data);
        mStoreGoodTypeListener = listener;
        mSet.clear();
        if (data.size() > 0) {
            mSet.add(data.get(0).getCategory_id());
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreGoodTypeBean item) {
        TextView tvLine = helper.getView(R.id.store_good_type_item_line);
        TextView tvName = helper.getView(R.id.store_good_type_item_name);
        LinearLayout llParent = helper.getView(R.id.ll_store_good_type_item);
        tvName.setText(item.getCategory_name());
        if (mSet.contains(item.getCategory_id())) {
            tvLine.setVisibility(View.VISIBLE);
            llParent.setSelected(true);
            tvName.setSelected(true);
        } else {
            tvLine.setVisibility(View.INVISIBLE);
            llParent.setSelected(false);
            tvName.setSelected(false);
        }
        llParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSet.contains(item.getCategory_id())) {

                } else {
                    mSet.clear();
                    mSet.add(item.getCategory_id());
                    mStoreGoodTypeListener.selectType(item);
                    notifyDataSetChanged();
                }

            }
        });
    }

    public void setSelectSet(String id) {
        mSet.clear();
        mSet.add(id);
    }
}
