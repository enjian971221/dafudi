package com.zkwl.qhzwj.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.king.base.util.StringUtils;
import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.orhanobut.logger.Logger;
import com.zkwl.qhzgyz.R;
import com.zkwl.qhzgyz.databinding.ActivityCommunityindexBinding;
import com.zkwl.qhzwj.api.Constants;
import com.zkwl.qhzwj.bean.CommunityIndexBean;
import com.zkwl.qhzwj.bean.CommunityIndexBuildBean;
import com.zkwl.qhzwj.model.CommunityIndexViewModel;
import com.zkwl.qhzwj.ui.adapter.CommunityIndexAdapter;
import com.zkwl.qhzwj.ui.adapter.CommunityIndexListener;
import com.zkwl.qhzwj.util.SpsUtil;
import com.zkwl.qhzwj.util.StatusBar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CommunityIndexActivity extends BaseActivity<CommunityIndexViewModel, ActivityCommunityindexBinding> {

    private CommunityIndexAdapter mAdapter;
    private List<CommunityIndexBean> mList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_communityindex;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBar.setStatusBar(this,true);
        getViewDataBinding().topbar.setTitle("社区选择");
        getViewDataBinding().topbar.addLeftBackImageButton().setOnClickListener(view -> finish());

        mAdapter = new CommunityIndexAdapter(R.layout.community_index_item, mList, new CommunityIndexListener() {
            @Override
            public void selectItem(CommunityIndexBuildBean bean, String community_name, String company_code) {
                String building_id = bean.getBuilding_id();
                String community_id = bean.getCommunity_id();
                String property_id = bean.getProperty_id();
                String string = SpsUtil.getString(Constants.AreaToken, "");
                try {
                    JSONObject jsonObject = new JSONObject(string);
                    jsonObject.put("community_id", community_id);
                    jsonObject.put("building_id", building_id);
                    jsonObject.put("property_id", property_id);
                    SpsUtil.put(Constants.CommunityId, community_id);
                    SpsUtil.put(Constants.PROPERTY_ID, property_id);
                    SpsUtil.put(Constants.CommunityName, community_name);
//                    SpsUtil.put(Constants.CompanyCode, company_code);
                    String object = jsonObject.toString();
                    SpsUtil.put(Constants.AreaToken, object);
                    if (StringUtils.equals(building_id, "0")) {
                        SpsUtil.put(Constants.IsAreaToken, false);
                    } else {
                        SpsUtil.put(Constants.IsAreaToken, true);
                    }
                    Logger.d("社区选择切换-->" + object);
                    Intent intent = new Intent();
                    intent.putExtra("community_name", community_name);
                    setResult(RESULT_OK, intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showToast(getContext(), "小区切换失败");
                }
            }
        });

        getViewDataBinding().recyclerView.setAdapter(mAdapter);

        getViewModel().getCommunityIndexList();
        getViewModel().getliveDatagetCommunityIndexList().observe(this, data -> {


            mList.clear();
            if (data.getData() != null) {
                mList.addAll(data.getData());
            }
            mAdapter.notifyDataSetChanged();
        });

    }
}
