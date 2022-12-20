package com.zkwl.qhzwj.ui.adapter;


import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.zkwl.qhzgyz.R;
import com.zkwl.qhzwj.bean.GoodListBean;
import com.zkwl.qhzwj.ui.rvadapter.BaseQuickAdapter;
import com.zkwl.qhzwj.ui.rvadapter.BaseViewHolder;
import com.zkwl.qhzwj.util.ImgUtil;

import java.util.List;

/**
 * Created by Administrator on 2019/9/16.
 */

public class StoreGoodAdapter extends BaseQuickAdapter<GoodListBean, BaseViewHolder> {

    public StoreGoodAdapter(int layoutResId, @Nullable List<GoodListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodListBean item) {
        ImageView ivIcon = helper.getView(R.id.store_good_item_icon);
        ImgUtil.loadImage( ivIcon,item.getImage_url());
        helper.setText(R.id.store_good_item_name, item.getGoods_name());
        helper.setText(R.id.store_good_item_unit, item.getGoods_unit_count() + item.getUnit_name());
        helper.setText(R.id.store_good_item_member_price, "¥" + item.getMember_price());
    }
}
