package com.zkwl.app_classroom.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSONObject;
import com.king.base.util.StringUtils;
import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import com.zkwl.app_classroom.R;
import com.zkwl.app_classroom.adapter.ClassHomeAdapter;
import com.zkwl.app_classroom.databinding.ActivityClassroomlistBinding;
import com.zkwl.app_classroom.model.ClassRomModel;
import com.zkwl.app_classroom.util.StatusBar;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import timber.log.Timber;


@AndroidEntryPoint
public class ClassroomListActivity extends BaseActivity<ClassRomModel, ActivityClassroomlistBinding> {

    public int mPage=1;
    public int mPageSize=10;
    private ClassHomeAdapter projectListAdapter;
    public List<JSONObject> rclist = new ArrayList<>();
    private String category_id="";//分类id

    @Override
    public int getLayoutId() {
        return R.layout.activity_classroomlist;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        registerMessageEvent(message -> {
            Timber.d("message:%s", message);
            ToastUtils.showToast(getContext(), message);
        });

        StatusBar.setStatusBar(this,true);
        getViewDataBinding().title.setTitle("课程列表");
        if (StringUtils.isNotBlank(getIntent().getStringExtra("title"))){
            getViewDataBinding().title.setTitle(getIntent().getStringExtra("title"));
        }
        getViewDataBinding().title.addLeftBackImageButton().setOnClickListener(v -> finish());

        category_id=getIntent().getIntExtra("category_id",0)+"";
        Log.e("category_id",""+category_id);
        projectListAdapter = new ClassHomeAdapter(getContext(), rclist, R.layout.item_classroomhome, new ClassHomeAdapter.click() {
            @Override
            public void itemclick(JSONObject item, int position) {
                startActivity(new Intent(ClassroomListActivity.this,ClassroomDetailsActivity.class)
                        .putExtra("id",item.getString("id"))
                        .putExtra("course_type",item.getIntValue("course_type"))
                );

            }
        });
        getViewDataBinding().recyclerView.setAdapter(projectListAdapter);


        getViewModel().getCourselist(getViewDataBinding().edSearch.getText().toString(),category_id,"",mPage+"",mPageSize+"");

        getViewModel().getcourselist().observe(this, data -> {
            getViewDataBinding().refreshLayout.finishRefresh();
            getViewDataBinding().refreshLayout.finishLoadMore();
            if (data.getData().getJSONArray("list").size()==0){
                getViewDataBinding().refreshLayout.setEnableLoadMore(false);
            }
            for (int i=0;i<data.getData().getJSONArray("list").size();i++){
                rclist.add(data.getData().getJSONArray("list").getJSONObject(i));
            }
            projectListAdapter.notifyDataSetChanged();
        });

        getViewDataBinding().refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPage++;
                getViewModel().getCourselist(getViewDataBinding().edSearch.getText().toString(),category_id,"",mPage+"",mPageSize+"");
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPage=1;
                rclist.clear();
                getViewDataBinding().refreshLayout.setEnableLoadMore(true);
                getViewModel().getCourselist(getViewDataBinding().edSearch.getText().toString(),category_id,"",mPage+"",mPageSize+"");
            }
        });


        getViewDataBinding().edSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //关闭软键盘
                    mPage=1;
                    rclist.clear();
                    StatusBar.chageInputState(getContext());
                    getViewModel().getCourselist(getViewDataBinding().edSearch.getText().toString(),category_id,"1",mPage+"",mPageSize+"");
                    return true;
                }
                return false;
            }
        });
    }
}
