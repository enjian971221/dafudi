package com.zkwl.app_classroom.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.king.frame.mvvmframe.base.BaseFragment;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.youth.banner.indicator.CircleIndicator;
import com.zkwl.app_classroom.PaysuccessReceiver;
import com.zkwl.app_classroom.R;
import com.zkwl.app_classroom.adapter.CategoryAdapter;
import com.zkwl.app_classroom.adapter.ClassHomeAdapter;
import com.zkwl.app_classroom.adapter.ImageAdapter;
import com.zkwl.app_classroom.databinding.FragmentClassroomBinding;
import com.zkwl.app_classroom.model.ClassRomModel;
import com.zkwl.app_classroom.ui.ClassroomDetailsActivity;
import com.zkwl.app_classroom.ui.ClassroomListActivity;
import com.zkwl.app_classroom.util.StatusBar;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * 首页
 */
@AndroidEntryPoint
public class ClassRoomFragment extends BaseFragment<ClassRomModel, FragmentClassroomBinding> {


    public int mPage = 1;
    public int mPageSize = 10;
    private ClassHomeAdapter projectListAdapter;
    private CategoryAdapter categoryAdapter;
    public List<String> list = new ArrayList<>();
    public List<JSONObject> rclist = new ArrayList<>();
    public List<JSONObject> categorylist = new ArrayList<>();
    public JSONArray tump;//缓存adlist 的banner数据的课程id

    @Override
    public int getLayoutId() {
        return R.layout.fragment_classroom;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        int statusBarHeight = StatusBar.getStatusBarHeight(getActivity());
        ViewGroup.LayoutParams lp = getViewDataBinding().stu.getLayoutParams();
        lp.height = statusBarHeight;
        getViewDataBinding().stu.setLayoutParams(lp);


        ImageAdapter adapter = new ImageAdapter(list);

        getViewDataBinding().banner.setBannerRound(10);
        getViewDataBinding().banner.setAdapter(adapter)
                .setCurrentItem(0, false)
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(getActivity()))//设置指示器
                .setOnBannerListener((data, position) -> {
                    startActivity(new Intent(getActivity(), ClassroomDetailsActivity.class)
                            .putExtra("id",tump.getJSONObject(position).getString("id"))
                            .putExtra("course_type",tump.getJSONObject(position).getIntValue("course_type"))
                    );
                });


        projectListAdapter = new ClassHomeAdapter(getContext(), rclist, R.layout.item_classroomhome, new ClassHomeAdapter.click() {
            @Override
            public void itemclick(JSONObject item, int position) {
                startActivity(new Intent(getActivity(), ClassroomDetailsActivity.class)
                        .putExtra("id",item.getString("id"))
                        .putExtra("course_type",item.getIntValue("course_type"))
                );
            }
        });
        categoryAdapter = new CategoryAdapter(getContext(), categorylist, R.layout.item_category, new CategoryAdapter.click() {
            @Override
            public void itemclick(JSONObject item, int position) {
                Log.e("itemclick",""+item.getIntValue("id"));
                startActivity(new Intent(getActivity(), ClassroomListActivity.class)
                        .putExtra("category_id", item.getIntValue("id"))
                        .putExtra("title", item.getString("title"))
                );
            }
        });
        getViewDataBinding().recyclerView.setAdapter(projectListAdapter);
        getViewDataBinding().categoryRecyclerView.setAdapter(categoryAdapter);


        getViewModel().getAdlist();
        getViewModel().getCategorylist();
        getViewModel().getCourselist("", "", "2", mPage + "", mPageSize + "");

        getViewModel().getadlistLiveData().observe(this, data -> {

            List<String> imglist = new ArrayList<>();
            for (int i = 0; i < data.getData().size(); i++) {
                imglist.add(data.getData().getJSONObject(i).getString("image_url"));
            }
            tump=data.getData();
            getViewDataBinding().banner.setDatas(imglist);
            getViewDataBinding().banner.start();

        });

        getViewModel().getCategorylistLiveData().observe(this, data -> {

            categorylist.clear();
            for (int i = 0; i < data.getData().size(); i++) {
                categorylist.add(data.getData().getJSONObject(i));
            }

            categoryAdapter.notifyDataSetChanged();

        });
        getViewModel().getcourselist().observe(this, data -> {
            Log.e("data",""+data.toString());
            getViewDataBinding().refreshLayout.finishRefresh();
            getViewDataBinding().refreshLayout.finishLoadMore();
            if (data.getData().getJSONArray("list").size() == 0) {
                getViewDataBinding().refreshLayout.setEnableLoadMore(false);
            }
            for (int i = 0; i < data.getData().getJSONArray("list").size(); i++) {
                rclist.add(data.getData().getJSONArray("list").getJSONObject(i));
            }
            projectListAdapter.notifyDataSetChanged();

        });

        getViewDataBinding().refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                getViewModel().getCourselist("", "", "2", mPage + "", mPageSize + "");
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage = 1;
                rclist.clear();
                getViewDataBinding().refreshLayout.setEnableLoadMore(true);
                getViewModel().getCourselist("", "", "2", mPage + "", mPageSize + "");
                getViewModel().getCategorylist();
                getViewModel().getAdlist();
            }
        });

        getViewDataBinding().tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ClassroomListActivity.class));
            }
        });


        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.zkwl.qhzwj.receiver.Pay_BROADCAST");//网络变化是会发送该广播
        PaysuccessReceiver paysuccessReceiver=new PaysuccessReceiver();
        getContext().registerReceiver(paysuccessReceiver,intentFilter);
    }
}
