package com.zkwl.app_classroom.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.king.frame.mvvmframe.base.BaseFragment;
import com.zkwl.app_classroom.R;
import com.zkwl.app_classroom.adapter.CourescataAdapter;
import com.zkwl.app_classroom.databinding.FragmentCoursecatalogBinding;
import com.zkwl.app_classroom.model.ClassRomDetailsModel;
import com.zkwl.app_classroom.ui.VideoActivity;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;


/**
 * 课程目录
 */

@AndroidEntryPoint
public class CourseCatalogFragment extends BaseFragment<ClassRomDetailsModel, FragmentCoursecatalogBinding> {

    private ClassRomDetailsModel classRomDetailsModel;
    private CourescataAdapter courescataAdapter;
    public List<JSONObject> courescata = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_coursecatalog;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        classRomDetailsModel = new ViewModelProvider(requireActivity()).get(ClassRomDetailsModel.class);

        courescataAdapter = new CourescataAdapter(getContext(), courescata, R.layout.item_courescata, new CourescataAdapter.click() {
            @Override
            public void itemclick(JSONObject item, int position) {

                Log.e("itemclick",""+item.toString());
                startActivity(new Intent(getActivity(), VideoActivity.class)
                        .putExtra("id",item.getString("id"))
                        .putExtra("courescata",courescata.toString())
                );

            }
        });
        classRomDetailsModel.getCourseinfoLiveData().observe(this, data -> {
            Log.e("data","getCourseinfoLiveData"+data.getData().getJSONArray("chapter_data").toJSONString());
            courescata.clear();

            JSONArray chapter_data = data.getData().getJSONArray("chapter_data");
            for (int i=0;i<chapter_data.size();i++){
                courescata.add(chapter_data.getJSONObject(i));
            }
            courescataAdapter.notifyDataSetChanged();

            courescataAdapter.setUser_buy(data.getData().getIntValue("user_buy"));

        });
        getViewDataBinding().recyclerView.setAdapter(courescataAdapter);


    }
}
