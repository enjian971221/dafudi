package com.zkwl.qhzwj.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseActivity;
import com.zkwl.qhzgyz.R;
import com.zkwl.qhzgyz.databinding.ActivityBindcommunityBinding;
import com.zkwl.qhzwj.bean.BindCommunityBean;
import com.zkwl.qhzwj.bean.EthnicGroupBean;
import com.zkwl.qhzwj.bean.SelectOptionDataSetCommon;
import com.zkwl.qhzwj.model.CommunityIndexViewModel;
import com.zkwl.qhzwj.util.StatusBar;
import com.zkwl.qhzwj.util.UserDataManager;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint
public class BindCommunityActivity extends BaseActivity<CommunityIndexViewModel, ActivityBindcommunityBinding> {


    private String relation_type = "";
    private String community_id = "";
    private String building_id = "";
    private String property_id = "";
    private String user_name = "";
    private String nationality = "";
    private String id_card_type = "";
    private String id_card_no = "";
    private String mobile_phone = "";
    private String gender = "";
    private String ethnic_group = "";
    private String mBtType = "";//操作是增加还是修改
    private List<SelectOptionDataSetCommon> mListType = new ArrayList<>();
    private List<SelectOptionDataSetCommon> mListIdCardType = new ArrayList<>();
    private List<SelectOptionDataSetCommon> mListNationality = new ArrayList<>();
    private List<SelectOptionDataSetCommon> mListGender = new ArrayList<>();
    private List<EthnicGroupBean> mListEthnicGroup = new ArrayList<>();

    private String mA_id;


    @Override
    public int getLayoutId() {
        return R.layout.activity_bindcommunity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        StatusBar.setStatusBar(this, true);
        getViewDataBinding().topbar.setTitle("添加认证");
        getViewDataBinding().topbar.addLeftBackImageButton().setOnClickListener(view -> finish());

        getViewModel().getBindCommunityInfo();

        getViewModel().getliveDatagetBindCommunityInfo().observe(this, basedata -> {
            if (basedata.getData() != null) {
                BindCommunityBean data = basedata.getData();
                if ("4".equals(data.getAudit_status())) {
                    //4 ：首次操作
                    getViewDataBinding().bindCommunitySubmit.setVisibility(View.VISIBLE);
                    getViewDataBinding().bindCommunitySubmit.setText("提交");
                    mBtType = "add";
                } else if ("3".equals(data.getAudit_status())) {
                    ToastUtils.showToast(getContext(),"审核通过请重新登录");
                    //退出登录
                    UserDataManager.userLogout();

                    Intent intent = new Intent(BindCommunityActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else if ("2".equals(data.getAudit_status())) {
                    getViewDataBinding().llBindCommunityCommnet.setVisibility(View.VISIBLE);
                    mBtType = "update";
                    mA_id = data.getId();
                    getViewDataBinding().llBindCommunityBot.setVisibility(View.VISIBLE);
                    getViewDataBinding().bindCommunitySubmit.setVisibility(View.VISIBLE);
                    getViewDataBinding().bindCommunitySubmit.setText("修改");
                    getViewDataBinding().tvBindCommunityType.setText(data.getRelation_name());
                    getViewDataBinding().tvBindCommunityCommunity.setText(data.getCommunity_name());
                    getViewDataBinding().tvBindCommunityBuilding.setText(data.getBuilding_name());
                    getViewDataBinding().etBindCommunityName.setText(data.getUser_name());
                    getViewDataBinding().tvBindCommunityNationality.setText(data.getNationality_name());
                    getViewDataBinding().tvBindCommunityIdCardType.setText(data.getCard_type_name());
                    getViewDataBinding().etBindCommunityIdCardNo.setText(data.getId_card_no());
                    getViewDataBinding().etBindCommunityMobilePhone.setText(data.getMobile_phone());
                    getViewDataBinding().tvBindCommunityGender.setText(data.getGender_name());
                    getViewDataBinding().tvBindCommunityEthnicGroup.setText(data.getEthnic_name());
                    getViewDataBinding().tvBindCommunityStatus.setText(data.getAudit_name());
                    getViewDataBinding().tvBindCommunityCommnet.setText(data.getAudit_commnet());
                    community_id = data.getCommunity_id();
                    relation_type = data.getRelation_type();
                    building_id = data.getBuilding_id();
                    property_id = data.getProperty_id();
                    nationality = data.getNationality();
                    id_card_type = data.getId_card_type();
                    gender = data.getGender();
                    ethnic_group = data.getEthnic_group();
                } else if ("1".equals(data.getAudit_status())) {
                    getViewDataBinding().llBindCommunityBot.setVisibility(View.VISIBLE);
                    getViewDataBinding().tvBindCommunityType.setText(data.getRelation_name());
                    getViewDataBinding().tvBindCommunityCommunity.setText(data.getCommunity_name());
                    getViewDataBinding().tvBindCommunityBuilding.setText(data.getBuilding_name());
                    getViewDataBinding().etBindCommunityName.setText(data.getUser_name());
                    getViewDataBinding().tvBindCommunityNationality.setText(data.getNationality_name());
                    getViewDataBinding().tvBindCommunityIdCardType.setText(data.getCard_type_name());
                    getViewDataBinding().etBindCommunityIdCardNo.setText(data.getId_card_no());
                    getViewDataBinding().etBindCommunityMobilePhone.setText(data.getMobile_phone());
                    getViewDataBinding().tvBindCommunityGender.setText(data.getGender_name());
                    getViewDataBinding().tvBindCommunityEthnicGroup.setText(data.getEthnic_name());
                    getViewDataBinding().tvBindCommunityStatus.setText(data.getAudit_name());
                    getViewDataBinding().tvBindCommunityCommnet.setText(data.getAudit_commnet());
                }
            }
        });

    }
}
