package com.zkwl.qhzwj.ui.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zkwl.qhzgyz.R;
import com.zkwl.qhzwj.bean.SimpleGoodBean;
import com.zkwl.qhzwj.ui.rvadapter.BaseQuickAdapter;
import com.zkwl.qhzwj.ui.rvadapter.BaseViewHolder;
import com.zkwl.qhzwj.util.ImgUtil;

import java.util.List;

/**
 * author : songkai
 * date   : 2020/9/288:50
 * desc   :
 */
public class SimpleGoodAdapter extends BaseQuickAdapter<SimpleGoodBean, BaseViewHolder> {
    private String act_type = "";//界面

    public void setAct_type(String act_type) {
        this.act_type = act_type;
    }

    public SimpleGoodAdapter(int layoutResId, @Nullable List<SimpleGoodBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SimpleGoodBean item) {
        ImageView imageView = helper.getView(R.id.simple_good_item_icon);
        TextView tvSalesVolume = helper.getView(R.id.simple_good_item_sales_volume);//销量
        LinearLayout llIntegral = helper.getView(R.id.ll_simple_good_item_integral);//积分
        ImgUtil.loadImage(imageView,item.getImage_url());
        helper.setText(R.id.simple_good_item_good_name, item.getGoods_name());
        helper.setText(R.id.simple_good_item_unit_name, item.getUnit_name());
        helper.setText(R.id.simple_good_item_good_price, "¥" +  item.getMember_price());
        helper.setText(R.id.simple_good_item_integral_num, item.getCode_num_Int() + "");
        tvSalesVolume.setText("销量 " + item.getFact_sales_volume());
        if ("home_good".equals(act_type) || "rem_good".equals(act_type) || "mall_good".equals(act_type)|| "coupon_good".equals(act_type)) {
            //首页-商品推荐
            String code_num_int = item.getCode_num_Int();
            llIntegral.setVisibility(Integer.parseInt(code_num_int) > 0 ? View.VISIBLE : View.GONE);
        } else if ("store_good".equals(act_type)) {
            //商家推荐-商品列表
            llIntegral.setVisibility(View.GONE);
            tvSalesVolume.setVisibility(View.GONE);
        } else {
            llIntegral.setVisibility(View.GONE);
            tvSalesVolume.setVisibility(View.GONE);
        }
    }
}
