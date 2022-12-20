package com.zkwl.qhzwj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.king.frame.mvvmframe.base.BaseActivity;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zkwl.qhzgyz.R;
import com.zkwl.qhzgyz.databinding.ActivityStoreBinding;
import com.zkwl.qhzwj.bean.GoodStoreInfoBean;
import com.zkwl.qhzwj.bean.SimpleGoodBean;
import com.zkwl.qhzwj.bean.StoreGoodTypeBean;
import com.zkwl.qhzwj.model.StoreViewModel;
import com.zkwl.qhzwj.ui.adapter.SimpleGoodAdapter;
import com.zkwl.qhzwj.ui.adapter.StoreGoodTypeAdapter;
import com.zkwl.qhzwj.ui.adapter.StoreGoodTypeListener;
import com.zkwl.qhzwj.ui.rvadapter.BaseQuickAdapter;
import com.zkwl.qhzwj.util.ImgUtil;
import com.zkwl.qhzwj.util.StatusBar;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StoreActivity extends BaseActivity<StoreViewModel, ActivityStoreBinding> {


    private List<StoreGoodTypeBean> mTypeList = new ArrayList<>();
    private StoreGoodTypeAdapter mTypeAdapter;
    private List<SimpleGoodBean> mGoodList = new ArrayList<>();
    private SimpleGoodAdapter mGoodAdapter;
    private String mS_id;
    private int pageIndex = 1;
    private String mTypeId = "all";


    @Override
    public int getLayoutId() {
        return R.layout.activity_store;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBar.setStatusBar(this, true);
        getViewDataBinding().topbar.setTitle("社区选择");
        getViewDataBinding().topbar.addLeftBackImageButton().setOnClickListener(view -> finish());

        Intent intent = getIntent();
        mS_id = intent.getStringExtra("s_id");


        mTypeAdapter = new StoreGoodTypeAdapter(R.layout.store_good_type_item, mTypeList, new StoreGoodTypeListener() {
            @Override
            public void selectType(StoreGoodTypeBean bean) {
                mTypeId = bean.getCategory_id();
                pageIndex = 1;
                getViewModel().getGoodList(pageIndex + "", mTypeId, mS_id);
            }
        });
        mGoodAdapter = new SimpleGoodAdapter(R.layout.simple_good_item, mGoodList);
        mGoodAdapter.setAct_type("store_good");
        View viewEmpty = LayoutInflater.from(this).inflate(R.layout.common_data_empty, null);
        mGoodAdapter.setEmptyView(viewEmpty);
        getViewDataBinding().rvStoreType.setAdapter(mTypeAdapter);
        getViewDataBinding().rvStoreInfo.setAdapter(mGoodAdapter);
        getViewDataBinding().srlStoreInfo.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageIndex++;
                getViewModel().getGoodList(pageIndex + "", mTypeId, mS_id);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageIndex = 1;
                getViewModel().getGoodList(pageIndex + "", mTypeId, mS_id);
            }
        });
        mGoodAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mGoodList.size() > position) {
//                    SimpleGoodBean goodListBean = mGoodList.get(position);
//                    String id = goodListBean.getId();
//                    Intent intentInfo = new Intent(StoreActivity.this, ShopGoodInfoActivity.class);
//                    intentInfo.putExtra("g_id", id);
//                    startActivity(intentInfo);
                }
            }
        });

        getViewModel().getGoodStoreInfo(mS_id);
        getViewModel().getStoreGoodType(mS_id);
        pageIndex = 1;
        getViewModel().getGoodList(pageIndex + "", mTypeId, mS_id);


        getViewModel().getliveDatagetGoodStoreInfo().observe(this,data->{
            if (data.getData() != null) {
                GoodStoreInfoBean infoBean = data.getData();
                getViewDataBinding().topbar.setTitle(infoBean.getMerchant_name());
                getViewDataBinding().tvStoreName.setText(infoBean.getMerchant_name());
                getViewDataBinding().tvStoreIntroduction.setText(infoBean.getIntroduction());
                ImgUtil.loadImage(getViewDataBinding().ivStoreIcon,infoBean.getImage_url());
            }
        });

        getViewModel().getliveDatagetCommunityIndexList().observe(this,data->{

            if (pageIndex == 1) {
                mGoodList.clear();
            }
            if (data.getData().getList() != null) {
                mGoodList.addAll(data.getData().getList());
            }
            if (mGoodAdapter != null) {
                mGoodAdapter.notifyDataSetChanged();
            }
        });


        getViewModel().getliveDatagetStoreGoodType().observe(this,response->{
            mTypeList.clear();
            if (response.getData() != null && response.getData().getList() != null) {
                mTypeList.addAll(response.getData().getList());
            }
            mTypeAdapter.setSelectSet(mTypeId);
            mTypeAdapter.notifyDataSetChanged();
        });
    }
}
