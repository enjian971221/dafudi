package com.zkwl.app_healthy.model;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.king.base.util.ToastUtils;
import com.king.frame.mvvmframe.base.BaseModel;
import com.king.frame.mvvmframe.base.DataViewModel;
import com.king.frame.mvvmframe.http.callback.ApiCallback;
import com.zkwl.app_healthy.api.HealthyApiService;
import com.zkwl.app_healthy.bean.Result;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;


@HiltViewModel
public class HealthyGoodsModel extends DataViewModel {

    private MutableLiveData<Result<JSONObject>> liveDatagoodinfo = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDatagoodmealinfo = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDatagoodmoney = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDatagoodpayAli = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDatagoodpayWechat = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDataBalanceinfo = new MutableLiveData<>();
    @Inject
    public HealthyGoodsModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }

    public void getBalanceinfo() {
        getRetrofitService(HealthyApiService.class)
                .getBalanceinfo()
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        Log.e("onResponse",""+result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveDataBalanceinfo.postValue(result);
                        } else {
                            sendMessage(result.getMessage(), true);
                        }
                    }

                    @Override
                    public void onError(Call<Result<JSONObject>> call, Throwable t) {
                        Log.e("result", "onError" + t.getMessage());
                        sendMessage(t.getMessage(), true);
                    }
                });
    }

    public void getGoodsdetail(int goodtype, String id, String shop_id) {
        if (TextUtils.isEmpty(id)) {
            ToastUtils.showToast(getApplication().getApplicationContext(), "商品id不能为空");
            return;
        }

        if (TextUtils.isEmpty(shop_id)) {
            ToastUtils.showToast(getApplication().getApplicationContext(), "门店id不能为空");
            return;
        }
        showLoading();

        if (goodtype == 1) {
            //套餐商品
            getRetrofitService(HealthyApiService.class)
                    .getGoodsmealdetail(id, shop_id)
                    .enqueue(new ApiCallback<Result<JSONObject>>() {
                        @Override
                        public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                            hideLoading();
                            Log.e("result", "" + result.toString());
                            if (result != null && result.getCode() == 200) {
                                liveDatagoodinfo.postValue(result);
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

        } else if (goodtype == 2) {
            //普通商品
            getRetrofitService(HealthyApiService.class)
                    .getGoodsdetail(id, shop_id)
                    .enqueue(new ApiCallback<Result<JSONObject>>() {
                        @Override
                        public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                            hideLoading();
                            Log.e("result", "" + result.toString());
                            if (result != null && result.getCode() == 200) {
                                liveDatagoodmealinfo.postValue(result);
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
    }

    public void getGoodsmoney(String goods_id, String shop_id) {
        if (TextUtils.isEmpty(goods_id)) {
            ToastUtils.showToast(getApplication().getApplicationContext(), "商品id不能为空");
            return;
        }

        if (TextUtils.isEmpty(shop_id)) {
            ToastUtils.showToast(getApplication().getApplicationContext(), "门店id不能为空");
            return;
        }
        showLoading();

        getRetrofitService(HealthyApiService.class)
                .getGoodsmoney(goods_id, shop_id)
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        hideLoading();
                        Log.e("result", "" + result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveDatagoodmoney.postValue(result);
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

    public void getGoodspayAli(String goods_id, String shop_id) {
        if (TextUtils.isEmpty(goods_id)) {
            ToastUtils.showToast(getApplication().getApplicationContext(), "商品id不能为空");
            return;
        }

        if (TextUtils.isEmpty(shop_id)) {
            ToastUtils.showToast(getApplication().getApplicationContext(), "门店id不能为空");
            return;
        }
        showLoading();

        getRetrofitService(HealthyApiService.class)
                .getGoodspay("1", goods_id, shop_id)
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        hideLoading();
                        Log.e("result", "" + result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveDatagoodpayAli.postValue(result);
                        } else {
                            sendMessage(result.getMessage());
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
    public void getGoodspayWechat( String goods_id, String shop_id) {
        if (TextUtils.isEmpty(goods_id)) {
            ToastUtils.showToast(getApplication().getApplicationContext(), "商品id不能为空");
            return;
        }

        if (TextUtils.isEmpty(shop_id)) {
            ToastUtils.showToast(getApplication().getApplicationContext(), "门店id不能为空");
            return;
        }
        showLoading();

        getRetrofitService(HealthyApiService.class)
                .getGoodspay("2", goods_id, shop_id)
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        hideLoading();
                        Log.e("result", "" + result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveDatagoodpayWechat.postValue(result);
                        } else {
                            sendMessage(result.getMessage());
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

    public LiveData<Result<JSONObject>> getgoodinfoLiveData() {
        return liveDatagoodinfo;
    }

    public LiveData<Result<JSONObject>> getadgoodmealinfoLiveData() {
        return liveDatagoodmealinfo;
    }

    public LiveData<Result<JSONObject>> getgoodmoneyLiveData() {
        return liveDatagoodmoney;
    }

    public LiveData<Result<JSONObject>> getgoodpayaliLiveData() {
        return liveDatagoodpayAli;
    }
    public LiveData<Result<JSONObject>> getgoodpaywechatLiveData() {
        return liveDatagoodpayWechat;
    }
    public LiveData<Result<JSONObject>> getliveDataBalanceinfo() {
        return liveDataBalanceinfo;
    }

}
