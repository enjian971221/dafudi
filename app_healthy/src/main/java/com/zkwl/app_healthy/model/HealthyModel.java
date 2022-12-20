package com.zkwl.app_healthy.model;

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
import com.zkwl.app_healthy.api.HealthyApiService;
import com.zkwl.app_healthy.bean.Result;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;


@HiltViewModel
public class HealthyModel extends DataViewModel {

    private MutableLiveData<Result<JSONObject>> liveDataadlist = new MutableLiveData<>();
    private MutableLiveData<Result<JSONArray>> liveDatashoplist = new MutableLiveData<>();
    private MutableLiveData<Result<JSONArray>> liveDataclassify= new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> datagetstemcells= new MutableLiveData<>();

    @Inject
    public HealthyModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }


    public void goodsAlllist(String goods_name, String shop_id,
                             String goods_type, String page,
                             String page_size, String is_recommend) {
        showLoading();
        getRetrofitService(HealthyApiService.class)
                .goodsAlllist(goods_name, shop_id, goods_type, page, page_size, is_recommend)
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        hideLoading();
                        Log.e("result", "" + result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveDataadlist.postValue(result);
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
    public void getclassify() {
        showLoading();
        getRetrofitService(HealthyApiService.class)
                .classify()
                .enqueue(new ApiCallback<Result<JSONArray>>() {
                    @Override
                    public void onResponse(Call<Result<JSONArray>> call, Result<JSONArray> result) {
                        hideLoading();
                        Log.e("result", "" + result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveDataclassify.postValue(result);
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

    public void getshoplist(String name, String longitude, String latitude) {
        showLoading();
        getRetrofitService(HealthyApiService.class)
                .getShoplist(name, longitude, latitude)
                .enqueue(new ApiCallback<Result<JSONArray>>() {
                    @Override
                    public void onResponse(Call<Result<JSONArray>> call, Result<JSONArray> result) {
                        hideLoading();
                        Log.e("result", "" + result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveDatashoplist.postValue(result);
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
    public void getstemcells(String type) {
        showLoading();
        getRetrofitService(HealthyApiService.class)
                .getstemcells(type)
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        hideLoading();
                        Log.e("result", "" + result.toString());
                        if (result != null && result.getCode() == 200) {
                            datagetstemcells.postValue(result);
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

    public LiveData<Result<JSONObject>> getadlistLiveData() {
        return liveDataadlist;
    }

    public LiveData<Result<JSONArray>> getshoplistLiveData() {
        return liveDatashoplist;
    }
    public LiveData<Result<JSONArray>> getliveDataclassify() {
        return liveDataclassify;
    }
    public LiveData<Result<JSONObject>> getdatastemcells() {
        return datagetstemcells;
    }


}
