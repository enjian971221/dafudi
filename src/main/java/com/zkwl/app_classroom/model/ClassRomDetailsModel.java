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
public class ClassRomDetailsModel extends DataViewModel {

    private MutableLiveData<Result<JSONObject>> liveDataCourseinfo = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDataCoursepay = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDataCourseAlipay = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDataCourseYue = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDataBalanceinfo = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDataCheckpaypwd = new MutableLiveData<>();

    @Inject
    public ClassRomDetailsModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }


    public void getCourseinfo(String id) {
        showLoading();
        getRetrofitService(ClassroomApiService.class)
                .getCourseinfo(id)
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        hideLoading();
                        Log.e("result", "" + result.getData().toJSONString());
                        if (result != null && result.getCode() == 200) {
                            liveDataCourseinfo.postValue(result);
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

    public void CourseWechatpay(String sign_data, String id) {
        showLoading();
        getRetrofitService(ClassroomApiService.class)
                .Coursepay(sign_data, id, "2", "")
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        hideLoading();
                        Log.e("result", "" + result.getData().toJSONString());
                        if (result != null && result.getCode() == 200) {
                            liveDataCoursepay.postValue(result);
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
    public void CourseAlipay(String sign_data, String id) {
        showLoading();
        getRetrofitService(ClassroomApiService.class)
                .Coursepay(sign_data, id, "1", "")
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        hideLoading();
                        Log.e("onResponse",""+result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveDataCourseAlipay.postValue(result);
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
    public void CourseYue(String sign_data, String id,String pwd) {
        showLoading();
        getRetrofitService(ClassroomApiService.class)
                .Coursepay(sign_data, id, "3", pwd)
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        hideLoading();
                        Log.e("onResponse",""+result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveDataCourseYue.postValue(result);
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
    public void getBalanceinfo() {
        getRetrofitService(ClassroomApiService.class)
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
    public void Checkpaypwd() {
        getRetrofitService(ClassroomApiService.class)
                .getcheck_paypwd()
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        Log.e("onResponse",""+result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveDataCheckpaypwd.postValue(result);
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


    public LiveData<Result<JSONObject>> getCourseinfoLiveData() {
        return liveDataCourseinfo;
    }
    public LiveData<Result<JSONObject>> getCoursepayLiveData() {
        return liveDataCoursepay;
    }
    public LiveData<Result<JSONObject>> getliveDataCourseAlipay() {
        return liveDataCourseAlipay;
    }
    public LiveData<Result<JSONObject>> getliveDataCourseYue() {
        return liveDataCourseYue;
    }
    public LiveData<Result<JSONObject>> getliveDataCheckpaypwd() {
        return liveDataCheckpaypwd;
    }
    public LiveData<Result<JSONObject>> getliveDataBalanceinfo() {
        return liveDataBalanceinfo;
    }


}
