package com.zkwl.app_healthy.fragment;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.king.frame.mvvmframe.base.BaseFragment;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zkwl.app_healthy.PaysuccessReceiver;
import com.zkwl.app_healthy.R;
import com.zkwl.app_healthy.adapter.HealthyAdapter;
import com.zkwl.app_healthy.adapter.HealthyclassifyAdapter;
import com.zkwl.app_healthy.databinding.FragmentHealthyBinding;
import com.zkwl.app_healthy.model.HealthyModel;
import com.zkwl.app_healthy.ui.GoodsDetailsActivity;
import com.zkwl.app_healthy.ui.OrderListActivity;
import com.zkwl.app_healthy.ui.SearchGoodsActivity;
import com.zkwl.app_healthy.ui.WebActivity;
import com.zkwl.app_healthy.util.HealthyImgUtil;
import com.zkwl.app_healthy.util.LocationUtils;
import com.zkwl.app_healthy.util.SpsUtil;
import com.zkwl.app_healthy.util.StatusBar;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * 首页
 */
@AndroidEntryPoint
public class HealthyFragment extends BaseFragment<HealthyModel, FragmentHealthyBinding> {

    private List<JSONObject> mList = new ArrayList<>();
    private List<JSONObject> mclassifyList = new ArrayList<>();
    public int mPage = 1;
    public int mPageSize = 10;
    private String shop_id = "";//暂时写1
    private HealthyAdapter healthyAdapter;
    private HealthyclassifyAdapter healthyclassifyAdapter;
    private JSONObject jsdata;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_healthy;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        int statusBarHeight = StatusBar.getStatusBarHeight(getActivity());
        ViewGroup.LayoutParams lp = getViewDataBinding().stu.getLayoutParams();
        lp.height = statusBarHeight;
        getViewDataBinding().stu.setLayoutParams(lp);


        getViewModel().getclassify();


        healthyclassifyAdapter = new HealthyclassifyAdapter(getContext(), mclassifyList, R.layout.item_healthyhomeclassify, new HealthyclassifyAdapter.click() {
            @Override
            public void itemclick(JSONObject item, int position) {
                switch (item.getIntValue("type")) {
                    case 1:
                        //套餐
                        startActivity(new Intent(getActivity(), SearchGoodsActivity.class)
                                .putExtra("type", "1")
                                .putExtra("title", item.getString("title"))
                        );
                        break;
                    case 2:
                        //单项
                        startActivity(new Intent(getActivity(), SearchGoodsActivity.class)
                                .putExtra("type", "2")
                                .putExtra("title", item.getString("title"))
                        );
                        break;
                    case 3:
                        //
                        break;
                    case 4:
                        //全部
                        startActivity(new Intent(getActivity(), SearchGoodsActivity.class)
                                .putExtra("title", item.getString("title"))
                        );
                        break;
                }
            }
        });
        getViewDataBinding().recyclerViewcat.setAdapter(healthyclassifyAdapter);
        healthyAdapter = new HealthyAdapter(getContext(), mList, R.layout.item_healthyhome, new HealthyAdapter.click() {
            @Override
            public void itemclick(JSONObject item, int position) {
                startActivity(new Intent(getContext(), GoodsDetailsActivity.class)
                        .putExtra("goods_type", item.getIntValue("goods_type"))
                        .putExtra("id", item.getString("id"))
                );
            }
        });
        getViewDataBinding().recyclerView.setAdapter(healthyAdapter);

        if (checkAndRequestPermission()) {
            /*直接在你需要的地方这样使用即可，经纬度和位置信息看注释*/ //获取位置location
            Location location = LocationUtils.getInstance(getActivity()).showLocation();
            if (location != null) {
                latitude = location.getLatitude();//纬度
                longitude = location.getLongitude();//经度
            } else {
                Log.i("FLY.LocationUtils", "address");
            }
        }


        //不管弹窗如何，先获取出来选择门店
        getViewModel().goodsAlllist("", shop_id, "", mPage + "", mPageSize + "", "2");

        if (TextUtils.isEmpty(SpsUtil.getString(getContext(), "shop_id", ""))) {
            //如果本地缓存中没有门店id，此时需要打开底部弹窗显示选择门店
            getViewModel().getshoplist("", longitude + "", latitude + "");
        } else {
            getViewDataBinding().placeTv.setText(SpsUtil.getString(getContext(), "shop_name", ""));
            shop_id = SpsUtil.getString(getContext(), "shop_id", "");
            getViewModel().goodsAlllist("", shop_id, "", mPage + "", mPageSize + "", "2");
        }


        getViewModel().getstemcells("2");

        getViewModel().getliveDataclassify().observe(this, data -> {
            mclassifyList.clear();
            for (int i = 0; i < data.getData().size(); i++) {
                mclassifyList.add(data.getData().getJSONObject(i));
            }
            healthyclassifyAdapter.notifyDataSetChanged();

        });


        getViewModel().getdatastemcells().observe(this, data -> {
            jsdata = data.getData();
            HealthyImgUtil.loadImage(getViewDataBinding().hotimg, data.getData().getString("image"), 1);
        });

        getViewModel().getadlistLiveData().observe(this, data -> {
            getViewDataBinding().refreshLayout.finishRefresh();
            getViewDataBinding().refreshLayout.finishLoadMore();
            if (data.getData().getJSONArray("list").size() == 0) {
                getViewDataBinding().refreshLayout.setEnableLoadMore(false);
            }
            for (int i = 0; i < data.getData().getJSONArray("list").size(); i++) {
                mList.add(data.getData().getJSONArray("list").getJSONObject(i));
            }
            healthyAdapter.notifyDataSetChanged();
        });

        getViewModel().getshoplistLiveData().observe(this, data -> {


            SelectPlaceFragment payDetailFragment = new SelectPlaceFragment();
            Bundle bundle = new Bundle();
            bundle.putDouble("latitude", latitude);
            bundle.putDouble("longitude", longitude);
            bundle.putString("data", data.getData().toString());
            payDetailFragment.setArguments(bundle);
            payDetailFragment.show(getChildFragmentManager(), "payDetailFragment");
            payDetailFragment.setPayDialogListener(new SelectplaceDialogListener() {
                @Override
                public void payNext(JSONObject jsonObject) {
                    SpsUtil.put(getContext(), "shop_id", shop_id = jsonObject.getString("id"));
                    SpsUtil.put(getContext(), "shop_name", jsonObject.getString("name"));
                    getViewDataBinding().placeTv.setText(jsonObject.getString("name"));


                    mList.clear();
                    mPage = 1;
                    getViewModel().goodsAlllist("", shop_id, "", mPage + "", mPageSize + "", "2");

                }
            });

        });

        getViewDataBinding().hotimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), WebActivity.class)
                        .putExtra("title", jsdata.getString("title"))
                        .putExtra("content", jsdata.getString("content"))
                );
            }
        });
        getViewDataBinding().placeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getViewModel().getshoplist("", longitude + "", latitude + "");
            }
        });

        getViewDataBinding().listimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SearchGoodsActivity.class));
            }
        });

        getViewDataBinding().order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), OrderListActivity.class));
            }
        });
        getViewDataBinding().refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                getViewModel().goodsAlllist("", shop_id, "", mPage + "", mPageSize + "", "2");
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getViewDataBinding().refreshLayout.setEnableLoadMore(true);
                mList.clear();
                mPage = 1;
                getViewModel().goodsAlllist("", shop_id, "", mPage + "", mPageSize + "", "2");
            }
        });

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.zkwl.qhzwj.receiver.Pay_BROADCAST");//网络变化是会发送该广播
        PaysuccessReceiver paysuccessReceiver = new PaysuccessReceiver();
        getContext().registerReceiver(paysuccessReceiver, intentFilter);

    }

    private double latitude;
    private double longitude;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            /*此处做你的处理*/
            Location location = LocationUtils.getInstance(getActivity()).showLocation();
            if (location != null) {
                String tude = "纬度：" + location.getLatitude() + "经度：" + location.getLongitude();
                latitude = location.getLatitude();//纬度
                longitude = location.getLongitude();//经度
            } else {
                Log.e("FLY.LocationUtils", "address");
            }
        }
    }

    private static final int PERMISSIONS_REQUEST_CODE = 1024;

    private boolean checkAndRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        List<String> lackedPermission = new ArrayList<>();
        if (!(getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (lackedPermission.isEmpty()) {
            return true;
        }
        // 请求缺少的权限，在 onRequestPermissionsResult 中返回是否获得权限
        String[] requestPermissions = new String[lackedPermission.size()];
        lackedPermission.toArray(requestPermissions);
        requestPermissions(requestPermissions, PERMISSIONS_REQUEST_CODE);
        return false;
    }
}
