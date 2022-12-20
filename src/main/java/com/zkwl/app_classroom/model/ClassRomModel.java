package com.zkwl.app_classroom.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.king.frame.mvvmframe.base.BaseModel;
import com.king.frame.mvvmframe.base.DataViewModel;
import com.king.frame.mvvmframe.http.callback.ApiCallback;
import com.zkwl.app_classroom.api.ClassroomApiService;
import com.zkwl.app_classroom.bean.Result;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;


@HiltViewModel
public class ClassRomModel extends DataViewModel {

    private MutableLiveData<Result<JSONArray>> liveDataadlist = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDatacourselist = new MutableLiveData<>();
    private MutableLiveData<Result<JSONArray>> liveDataCategorylist = new MutableLiveData<>();

    @Inject
    public ClassRomModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }


    public void getAdlist() {
        showLoading();
        getRetrofitService(ClassroomApiService.class)
                .getAdlist()
                .enqueue(new ApiCallback<Result<JSONArray>>() {
                    @Override
                    public void onResponse(Call<Result<JSONArray>> call, Result<JSONArray> result) {
                        hideLoading();
//                        Log.e("result", "" + result.getData().toJSONString());
                        if (result != null && result.getCode() == 200) {
                            liveDataadlist.postValue(result);
                        } else {
                            sendMessage(result.getMessage(), true);
                        }
                    }

                    @Override
                    public void onError(Call<Result<JSONArray>> call, Throwable t) {
                        hideLoading();
                        Log.e("result", "onError" + t.getMessage());
                        sendMessage(t.getMessage(), true);
                    }
                });
    }

    public void getCategorylist() {
        showLoading();
        getRetrofitService(ClassroomApiService.class)
                .getCategorylist()
                .enqueue(new ApiCallback<Result<JSONArray>>() {
                    @Override
                    public void onResponse(Call<Result<JSONArray>> call, Result<JSONArray> result) {
                        hideLoading();
                        Log.e("result", "" + result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveDataCategorylist.postValue(result);
                        } else {
                            sendMessage(result.getMessage(), true);
                        }
                    }

                    @Override
                    public void onError(Call<Result<JSONArray>> call, Throwable t) {
                        hideLoading();
                        Log.e("result", "onError" + t.getMessage());
                        sendMessage(t.getMessage(), true);
                    }
                });
    }

    public void getCourselist(String title, String category_id, String is_recommend, String page, String page_size) {
        showLoading();
        getRetrofitService(ClassroomApiService.class)
                .getCourselist(title, category_id, is_recommend, page, page_size)
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        hideLoading();
                        if (result != null && result.getCode() == 200) {
                            Log.e("result", "" + result.getData().toJSONString());
                            liveDatacourselist.postValue(result);
                        } else {
                            sendMessage(result.getMessage(), true);
                        }
                    }

                    @Override
                    public void onError(Call<Result<JSONObject>> call, Throwable t) {
                        hideLoading();
                        Log.e("result", "onError" + t.getMessage());
                        sendMessage(t.getMessage(), true);
                    }
                });
    }

    public LiveData<Result<JSONArray>> getadlistLiveData() {
        return liveDataadlist;
    }

    public LiveData<Result<JSONArray>> getCategorylistLiveData() {
        return liveDataCategorylist;
    }

    public LiveData<Result<JSONObject>> getcourselist() {
        return liveDatacourselist;
    }


}
