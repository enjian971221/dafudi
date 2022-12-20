package com.zkwl.app_healthy.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.youth.banner.indicator.CircleIndicator;
import com.zkwl.app_healthy.R;
import com.zkwl.app_healthy.adapter.HealthyImageAdapter;
import com.zkwl.app_healthy.adapter.HealthyRecyclewImageAdapter;
import com.zkwl.app_healthy.databinding.ActivityGoodsdetailsBinding;
import com.zkwl.app_healthy.model.HealthyGoodsModel;
import com.zkwl.app_healthy.util.SpsUtil;
import com.zkwl.app_healthy.util.StatusBar;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;

@AndroidEntryPoint
public class GoodsDetailsActivity extends BaseActivity<HealthyGoodsModel, ActivityGoodsdetailsBinding> {

    private List<String> recyclerViewimglist = new ArrayList<>();
    private HealthyRecyclewImageAdapter healthyRecyclewImageAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_goodsdetails;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBar.setStatusBar(this,true);
        registerMessageEvent(message -> {
            Timber.d("message:%s", message);
            ToastUtils.showToast(getContext(), message);
        });

        getViewDataBinding().backimg.setOnClickListener(view -> finish());
        getViewModel().getGoodsdetail(getIntent().getIntExtra("goods_type", 0),
                getIntent().getStringExtra("id"),
                SpsUtil.getString(getContext(), "shop_id", ""));

        getViewModel().getadgoodmealinfoLiveData().observe(this, data -> {


            List<String> imglist = new ArrayList<>();
            for (int i = 0; i < data.getData().getJSONArray("goods_img").size(); i++) {
                imglist.add(data.getData().getJSONArray("goods_img").getString(i).toString());
            }
            getViewDataBinding().goodsImg.setDatas(imglist);
            getViewDataBinding().goodsImg.start();

            recyclerViewimglist.clear();
            for (int i = 0; i < data.getData().getJSONArray("goods_detail_img").size(); i++) {
                recyclerViewimglist.add(data.getData().getJSONArray("goods_detail_img").getString(i).toString());
            }
            healthyRecyclewImageAdapter.notifyDataSetChanged();


            getViewDataBinding().goodsName.setText(data.getData().getString("goods_name"));
            getViewDataBinding().goodsPrice.setText("原价:¥"+data.getData().getString("goods_price"));
            getViewDataBinding().goodsPrice1.setText("原价:¥"+data.getData().getString("goods_price"));
            getViewDataBinding().vipPrice.setText("会员价：¥"+data.getData().getString("vip_price"));
            getViewDataBinding().vipPrice1.setText("会员价：¥"+data.getData().getString("vip_price"));
            getViewDataBinding().vipPrice.setVisibility(View.VISIBLE);
            getViewDataBinding().vipPrice1.setVisibility(View.VISIBLE);
            getViewDataBinding().goodsSales.setText("已售"+data.getData().getString("goods_sales")+"份");
        });

        getViewModel().getgoodinfoLiveData().observe(this, data -> {


            List<String> imglist = new ArrayList<>();
            for (int i = 0; i < data.getData().getJSONArray("goods_img").size(); i++) {
                imglist.add(data.getData().getJSONArray("goods_img").getString(i));
            }
            getViewDataBinding().goodsImg.setDatas(imglist);
            getViewDataBinding().goodsImg.start();


            recyclerViewimglist.clear();
            for (int i = 0; i < data.getData().getJSONArray("goods_detail_img").size(); i++) {
                recyclerViewimglist.add(data.getData().getJSONArray("goods_detail_img").getString(i).toString());
            }
            healthyRecyclewImageAdapter.notifyDataSetChanged();

            getViewDataBinding().vipPrice.setVisibility(View.GONE);
            getViewDataBinding().vipPrice1.setVisibility(View.GONE);
            getViewDataBinding().goodsName.setText(data.getData().getString("goods_name"));
            getViewDataBinding().goodsPrice.setText("原价:¥"+data.getData().getString("goods_price"));
            getViewDataBinding().goodsPrice1.setText("原价: ¥"+data.getData().getString("goods_price"));
            getViewDataBinding().goodsSales.setText("已售"+data.getData().getString("goods_sales")+"份");


        });



        HealthyImageAdapter adapter = new HealthyImageAdapter(recyclerViewimglist);

        getViewDataBinding().goodsImg.setBannerRound(10);
        getViewDataBinding().goodsImg.setAdapter(adapter)
                .setCurrentItem(0, false)
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(this))//设置指示器
                .setOnBannerListener((data, position) -> {

                });

        healthyRecyclewImageAdapter = new HealthyRecyclewImageAdapter(getContext(), recyclerViewimglist, R.layout.healthyitem_img);
        getViewDataBinding().recyclerView.setAdapter(healthyRecyclewImageAdapter);


        getViewDataBinding().buytv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),BuyDetailsActivity.class)
                        .putExtra("id",getIntent().getStringExtra("id"))
                );
            }
        });
    }
}
