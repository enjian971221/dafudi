package com.zkwl.qhzwj.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.king.frame.mvvmframe.base.BaseModel;
import com.king.frame.mvvmframe.base.DataViewModel;
import com.king.frame.mvvmframe.http.callback.ApiCallback;
import com.zkwl.qhzwj.api.AppApiService;
import com.zkwl.qhzwj.bean.Result;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
public class HomeViewModel extends DataViewModel {

    private MutableLiveData<Result<JSONArray>> liveDatagetHomeNewTab = new MutableLiveData<>();
    private MutableLiveData<Result<JSONArray>> liveDatagetBannerList = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDatagetHomeGoodsList = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDatagetgetHomeMerge = new MutableLiveData<>();

    @Inject
    public HomeViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }


    public void getHomeMerge() {
        showLoading();
        getRetrofitService(AppApiService.class).getHomeMerge()
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        hideLoading();
                        Log.e("HomeNewTab", "onResponse" + result.toString());
                        liveDatagetgetHomeMerge.postValue(result);
                    }

                    @Override
                    public void onError(Call<Result<JSONObject>> call, Throwable t) {
                        hideLoading();
                        Log.e("HomeNewTab", "onError" + t.getMessage().toString());
                    }
                });


    }
    public void HomeNewTab() {
        showLoading();
        getRetrofitService(AppApiService.class).getHomeNewTab()
                .enqueue(new ApiCallback<Result<JSONArray>>() {
                    @Override
                    public void onResponse(Call<Result<JSONArray>> call, Result<JSONArray> result) {
                        hideLoading();
                        Log.e("HomeNewTab", "onResponse" + result.toString());
                        liveDatagetHomeNewTab.postValue(result);
                    }

                    @Override
                    public void onError(Call<Result<JSONArray>> call, Throwable t) {
                        hideLoading();
                        Log.e("HomeNewTab", "onError" + t.getMessage().toString());
                    }
                });


    }

    public void getBannerList() {
        showLoading();
        getRetrofitService(AppApiService.class).getBannerList("1")
                .enqueue(new ApiCallback<Result<JSONArray>>() {
                    @Override
                    public void onResponse(Call<Result<JSONArray>> call, Result<JSONArray> result) {
                        hideLoading();
                        Log.e("getBannerList", "onResponse" + result.toString());
                        liveDatagetBannerList.postValue(result);
                    }

                    @Override
                    public void onError(Call<Result<JSONArray>> call, Throwable t) {
                        hideLoading();
                        Log.e("HomeNewTab", "onError" + t.getMessage().toString());
                    }
                });


    }

    public void getHomeGoodsList(String page, String page_size) {
        showLoading();
        getRetrofitService(AppApiService.class).getHomeGoodsList(page, page_size)
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        hideLoading();
                        Log.e("getHomeGoodsList", "onResponse" + result.toString());
                        liveDatagetHomeGoodsList.postValue(result);
                    }

                    @Override
                    public void onError(Call<Result<JSONObject>> call, Throwable t) {
                        hideLoading();
                        Log.e("HomeNewTab", "onError" + t.getMessage().toString());
                    }
                });


    }

    public MutableLiveData<Result<JSONArray>> getliveDatagetHomeNewTab() {
        return liveDatagetHomeNewTab;
    }
    public MutableLiveData<Result<JSONObject>> getliveDatagetHomeMerge() {
        return liveDatagetgetHomeMerge;
    }

    public MutableLiveData<Result<JSONArray>> getliveDatagetBannerList() {
        return liveDatagetBannerList;
    }

    public MutableLiveData<Result<JSONObject>> getliveDatagetHomeGoodsList() {
        return liveDatagetHomeGoodsList;
    }
}
