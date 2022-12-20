package com.zkwl.qhzwj.fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.king.frame.mvvmframe.base.BaseFragment;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.indicator.CircleIndicator;
import com.zkwl.app_classroom.adapter.ImageAdapter;
import com.zkwl.qhzgyz.R;
import com.zkwl.qhzgyz.databinding.FragmentHomeBinding;
import com.zkwl.qhzwj.api.Constants;
import com.zkwl.qhzwj.model.HomeViewModel;
import com.zkwl.qhzwj.ui.BindCommunityActivity;
import com.zkwl.qhzwj.ui.CommunityIndexActivity;
import com.zkwl.qhzwj.ui.ShopGoodInfoActivity;
import com.zkwl.qhzwj.ui.adapter.HomeGoodsAdapter;
import com.zkwl.qhzwj.ui.adapter.HomeTabAdapter;
import com.zkwl.qhzwj.util.SpsUtil;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class HomeFragment extends BaseFragment<HomeViewModel, FragmentHomeBinding> {

    private int mpage = 1;
    private int mpagesize = 10;
    private HomeGoodsAdapter homeGoodsAdapter;
    private HomeTabAdapter homeTabAdapter;
    private List<JSONObject> mList = new ArrayList<>();
    private List<JSONObject> mTabList = new ArrayList<>();
    public List<String> list = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        getViewDataBinding().mTvCommunityName.setText(SpsUtil.getString(Constants.CommunityName, ""));
        getViewDataBinding().mTvCommunityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CommunityIndexActivity.class);
                startActivityForResult(intent, 789);
                return;
            }
        });

        ImageAdapter adapter = new ImageAdapter(list);

        getViewDataBinding().banner.setBannerRound(10);
        getViewDataBinding().banner.setAdapter(adapter)
                .setCurrentItem(0, false)
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(getActivity()))//设置指示器
                .setOnBannerListener((data, position) -> {

                });

        homeGoodsAdapter = new HomeGoodsAdapter(getContext(), mList, R.layout.item_goodhome, new HomeGoodsAdapter.click() {
            @Override
            public void itemclick(JSONObject item, int position) {

                //先判断用户是否绑定了社区
                if (!userIsBingdArea()) {
                    showBindCommunityDialog();
                    return;
                }

                Intent intentInfo = new Intent(getActivity(), ShopGoodInfoActivity.class);
                intentInfo.putExtra("g_id", item.getString("goods_id"));
                startActivity(intentInfo);



//                Intent intent = new Intent(getActivity(), StoreActivity.class);
//                intent.putExtra("s_id", item.getString("merchant_id"));
//                startActivity(intent);

            }
        });
        homeTabAdapter = new HomeTabAdapter(getContext(), mTabList, R.layout.item_hometab, new HomeTabAdapter.click() {
            @Override
            public void itemclick(JSONObject item, int position) {

            }
        });
        getViewDataBinding().tabrecyclerView.setAdapter(homeTabAdapter);
        getViewDataBinding().recyclerView.setAdapter(homeGoodsAdapter);
        getViewDataBinding().refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mpage++;
                getViewModel().getHomeGoodsList(mpage + "", mpagesize + "");
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                getViewModel().HomeNewTab();
                getViewModel().getHomeMerge();
                getViewModel().getBannerList();


                mList.clear();
                mpage = 1;
                getViewDataBinding().refreshLayout.setEnableLoadMore(true);
                getViewModel().getHomeGoodsList(mpage + "", mpagesize + "");
            }
        });

        getViewModel().HomeNewTab();
        getViewModel().getHomeMerge();
        getViewModel().getBannerList();
        getViewModel().getHomeGoodsList(mpage + "", mpagesize + "");

        getViewModel().getliveDatagetHomeMerge().observe(this,data->{

            getViewDataBinding().weather.setText(data.getData().getJSONObject("sky").getString("weather"));
            getViewDataBinding().temphigh.setText(data.getData().getJSONObject("sky").getString("templow")+"°"+"~"+data.getData().getJSONObject("sky").getString("temphigh")+"°");

        });

        getViewModel().getliveDatagetHomeNewTab().observe(this, data -> {

            mTabList.clear();
            for (int i=0;i<data.getData().size();i++){
                mTabList.add(data.getData().getJSONObject(i));
            }
            homeTabAdapter.notifyDataSetChanged();


        });
        getViewModel().getliveDatagetBannerList().observe(this, data -> {

            List<String> imglist = new ArrayList<>();
            for (int i = 0; i < data.getData().size(); i++) {
                imglist.add(data.getData().getJSONObject(i).getString("image_url"));
            }
            getViewDataBinding().banner.setDatas(imglist);
            getViewDataBinding().banner.start();

        });
        getViewModel().getliveDatagetHomeGoodsList().observe(this, data -> {
            getViewDataBinding().refreshLayout.finishRefresh();
            getViewDataBinding().refreshLayout.finishLoadMore();
            JSONArray list = data.getData().getJSONArray("list");
            if (list.size() == 0) {
                getViewDataBinding().refreshLayout.setEnableLoadMore(false);
            }
            for (int i = 0; i < list.size(); i++) {
                mList.add(list.getJSONObject(i));
            }
            homeGoodsAdapter.notifyDataSetChanged();

        });

    }

    public boolean userIsBingdArea() {
        return SpsUtil.getBoolean(Constants.IsAreaToken);
    }

    /**
     * 显示绑定社区弹框
     */
    private void showBindCommunityDialog() {
        new QMUIDialog.MessageDialogBuilder(getActivity())
                .setTitle("提示")
                .setMessage("请先进行身份认证")
                .addAction("取消", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .addAction("去绑定", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                        startActivity(new Intent(getActivity(), BindCommunityActivity.class));
                        finish();
                    }
                })
                .show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 789 && resultCode == RESULT_OK && data != null && data.getStringExtra("community_name") != null) {
            getViewDataBinding().mTvCommunityName.setText(data.getStringExtra("community_name"));

            getViewModel().HomeNewTab();
            getViewModel().getHomeMerge();
            getViewModel().getBannerList();
            mpage=1;
            getViewModel().getHomeGoodsList(mpage + "", mpagesize + "");

        }
    }

}
