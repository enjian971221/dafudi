package com.zkwl.app_classroom.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.fastjson.JSONArray;
import com.king.frame.mvvmframe.base.BaseFragment;
import com.zkwl.app_classroom.R;
import com.zkwl.app_classroom.adapter.RecyclewImageAdapter;
import com.zkwl.app_classroom.databinding.FragmentCourseintroductionBinding;
import com.zkwl.app_classroom.model.ClassRomDetailsModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


/**
 * 简介
 */

@AndroidEntryPoint
public class CourseIntroductionFragment extends BaseFragment<ClassRomDetailsModel, FragmentCourseintroductionBinding> {

    private ClassRomDetailsModel classRomDetailsModel;
    private List<String> imglist = new ArrayList<>();
    private RecyclewImageAdapter recyclewImageAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_courseintroduction;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        classRomDetailsModel = new ViewModelProvider(requireActivity()).get(ClassRomDetailsModel.class);

        classRomDetailsModel.getCourseinfoLiveData().observe(this, data -> {
            JSONArray content = data.getData().getJSONArray("content_images");
            for (int i = 0; i < content.size(); i++) {
                imglist.add(content.getString(i));
            }
            recyclewImageAdapter.notifyDataSetChanged();
        });

        recyclewImageAdapter = new RecyclewImageAdapter(getContext(), imglist, R.layout.item_img);
        getViewDataBinding().recyclerView.setAdapter(recyclewImageAdapter);

    }

}
