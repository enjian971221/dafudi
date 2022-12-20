package com.zkwl.app_healthy.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
public class OrderModel extends DataViewModel {

    private MutableLiveData<Result<JSONObject>> liveDataadlist = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDataOrderdetail = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDataGospayAli = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDataGospayWechat = new MutableLiveData<>();

    @Inject
    public OrderModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }


    public void getOrderList(String status, String page,
                             String page_size) {
        showLoading();
        getRetrofitService(HealthyApiService.class)
                .getOrderList(status, page, page_size)
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
    public void getOrderdetail(String id) {
        showLoading();
        getRetrofitService(HealthyApiService.class)
                .getOrderdetail(id)
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        hideLoading();
                        Log.e("result", "" + result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveDataOrderdetail.postValue(result);
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
    public void getGospayAli(String id) {
        showLoading();
        getRetrofitService(HealthyApiService.class)
                .getGospay(id,"1")
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        hideLoading();
                        Log.e("result", "" + result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveDataGospayAli.postValue(result);
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
    public void getGospayWechat(String id) {
        showLoading();
        getRetrofitService(HealthyApiService.class)
                .getGospay(id,"2")
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        hideLoading();
                        Log.e("result", "" + result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveDataGospayWechat.postValue(result);
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
    public LiveData<Result<JSONObject>> getliveDataOrderdetail() {
        return liveDataOrderdetail;
    }
    public LiveData<Result<JSONObject>> getliveDataGospayAli() {
        return liveDataGospayAli;
    }
    public LiveData<Result<JSONObject>> getliveDataGospayWechat() {
        return liveDataGospayWechat;
    }

}
