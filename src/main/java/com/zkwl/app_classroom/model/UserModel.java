package com.zkwl.app_classroom.model;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
public class UserModel  extends DataViewModel {
    private MutableLiveData<Result<JSONObject>> liveDatasend_sms = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDatasetPayPwd = new MutableLiveData<>();

    @Inject
    public UserModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }


    public void sendVerificationCode(String type) {
        showLoading();
        getRetrofitService(ClassroomApiService.class)
                .sendVerificationCode(type)
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
//                        Log.e("result", "" + result.getData().toJSONString());
                        hideLoading();
                        if (result != null && result.getCode() == 200) {
                            liveDatasend_sms.postValue(result);
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
    public void setPayPwd(String code,  String confirm_password) {
        getRetrofitService(ClassroomApiService.class)
                .setPayPwd(code,confirm_password)
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        if (result != null && result.getCode() == 200) {
                            liveDatasetPayPwd.postValue(result);
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


    public LiveData<Result<JSONObject>> getadlistliveDatasend_sms() {
        return liveDatasend_sms;
    }
    public LiveData<Result<JSONObject>> getadliveDatasetPayPwd() {
        return liveDatasetPayPwd;
    }

}
