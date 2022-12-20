package com.zkwl.qhzwj.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.king.frame.mvvmframe.base.BaseModel;
import com.king.frame.mvvmframe.base.DataViewModel;
import com.king.frame.mvvmframe.http.callback.ApiCallback;
import com.zkwl.qhzwj.api.AppApiService;
import com.zkwl.qhzwj.bean.CommPage;
import com.zkwl.qhzwj.bean.GoodStoreInfoBean;
import com.zkwl.qhzwj.bean.Response;
import com.zkwl.qhzwj.bean.SimpleGoodBean;
import com.zkwl.qhzwj.bean.StoreGoodTypeGroupBean;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;

@HiltViewModel
public class StoreViewModel extends DataViewModel {


    private MutableLiveData<Response<CommPage<SimpleGoodBean>>> liveData = new MutableLiveData<>();
    private MutableLiveData<Response<GoodStoreInfoBean>> liveDatagetGoodStoreInfo = new MutableLiveData<>();
    private MutableLiveData<Response<StoreGoodTypeGroupBean>> liveDatagetStoreGoodType = new MutableLiveData<>();


    @Inject
    public StoreViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }

    public void getGoodList(String page, String category_id, String merchant_id) {
        showLoading();
        getRetrofitService(AppApiService.class).getGoodList(page, "20", "", "", "", "", category_id, merchant_id)
                .enqueue(new ApiCallback<Response<CommPage<SimpleGoodBean>>>() {
                    @Override
                    public void onResponse(Call<Response<CommPage<SimpleGoodBean>>> call, Response<CommPage<SimpleGoodBean>> result) {
                        hideLoading();
                        Log.e("HomeNewTab", "onResponse" + result.toString());
                        liveData.postValue(result);
                    }

                    @Override
                    public void onError(Call<Response<CommPage<SimpleGoodBean>>> call, Throwable t) {
                        hideLoading();
                        Log.e("HomeNewTab", "onError" + t.getMessage().toString());
                    }
                });
    }

    public void getGoodStoreInfo(String id) {
        showLoading();
        getRetrofitService(AppApiService.class).getGoodStoreInfo(id)
                .enqueue(new ApiCallback<Response<GoodStoreInfoBean>>() {
                    @Override
                    public void onResponse(Call<Response<GoodStoreInfoBean>> call, Response<GoodStoreInfoBean> result) {
                        hideLoading();
                        Log.e("HomeNewTab", "onResponse" + result.toString());
                        liveDatagetGoodStoreInfo.postValue(result);
                    }

                    @Override
                    public void onError(Call<Response<GoodStoreInfoBean>> call, Throwable t) {
                        hideLoading();
                        Log.e("HomeNewTab", "onError" + t.getMessage().toString());
                    }
                });
    }
    public void getStoreGoodType(String id) {
        showLoading();
        getRetrofitService(AppApiService.class).getStoreGoodType(id)
                .enqueue(new ApiCallback<Response<StoreGoodTypeGroupBean>>() {
                    @Override
                    public void onResponse(Call<Response<StoreGoodTypeGroupBean>> call, Response<StoreGoodTypeGroupBean> result) {
                        hideLoading();
                        Log.e("HomeNewTab", "onResponse" + result.toString());
                        liveDatagetStoreGoodType.postValue(result);
                    }

                    @Override
                    public void onError(Call<Response<StoreGoodTypeGroupBean>> call, Throwable t) {
                        hideLoading();
                        Log.e("HomeNewTab", "onError" + t.getMessage().toString());
                    }
                });
    }


    public MutableLiveData<Response<CommPage<SimpleGoodBean>>> getliveDatagetCommunityIndexList() {
        return liveData;
    }

    public MutableLiveData<Response<GoodStoreInfoBean>> getliveDatagetGoodStoreInfo() {
        return liveDatagetGoodStoreInfo;
    }
    public MutableLiveData<Response<StoreGoodTypeGroupBean>> getliveDatagetStoreGoodType() {
        return liveDatagetStoreGoodType;
    }

}
