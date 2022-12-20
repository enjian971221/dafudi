package com.zkwl.qhzwj.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.king.frame.mvvmframe.base.BaseModel;
import com.king.frame.mvvmframe.base.DataViewModel;
import com.king.frame.mvvmframe.http.callback.ApiCallback;
import com.zkwl.qhzwj.api.AppApiService;
import com.zkwl.qhzwj.bean.BindCommunityBean;
import com.zkwl.qhzwj.bean.CommunityIndexBean;
import com.zkwl.qhzwj.bean.Response;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
public class CommunityIndexViewModel extends DataViewModel {

    private MutableLiveData<Response<List<CommunityIndexBean>>> liveDatagetCommunityIndexList = new MutableLiveData<>();
    private MutableLiveData<Response<BindCommunityBean>> liveDatagetBindCommunityInfo = new MutableLiveData<>();

    @Inject
    public CommunityIndexViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }


    public void getCommunityIndexList() {
        showLoading();
        getRetrofitService(AppApiService.class).getCommunityIndexList()
                .enqueue(new ApiCallback<Response<List<CommunityIndexBean>>>() {
                    @Override
                    public void onResponse(Call<Response<List<CommunityIndexBean>>> call, Response<List<CommunityIndexBean>> result) {
                        hideLoading();
                        Log.e("HomeNewTab", "onResponse" + result.toString());
                        liveDatagetCommunityIndexList.postValue(result);
                    }

                    @Override
                    public void onError(Call<Response<List<CommunityIndexBean>>> call, Throwable t) {
                        hideLoading();
                        Log.e("HomeNewTab", "onError" + t.getMessage().toString());
                    }
                });


    }

    public void getBindCommunityInfo() {
        showLoading();
        getRetrofitService(AppApiService.class).getBindCommunityInfo()
                .enqueue(new ApiCallback<Response<BindCommunityBean>>() {
                    @Override
                    public void onResponse(Call<Response<BindCommunityBean>> call, Response<BindCommunityBean> result) {
                        hideLoading();
                        Log.e("getBindCommunityInfo", "onResponse" + result.toString());
                        liveDatagetBindCommunityInfo.postValue(result);
                    }

                    @Override
                    public void onError(Call<Response<BindCommunityBean>> call, Throwable t) {
                        hideLoading();
                        Log.e("HomeNewTab", "onError" + t.getMessage().toString());
                    }
                });


    }


    public MutableLiveData<Response<List<CommunityIndexBean>>> getliveDatagetCommunityIndexList() {
        return liveDatagetCommunityIndexList;
    }

    public MutableLiveData<Response<BindCommunityBean>> getliveDatagetBindCommunityInfo() {
        return liveDatagetBindCommunityInfo;
    }
}
