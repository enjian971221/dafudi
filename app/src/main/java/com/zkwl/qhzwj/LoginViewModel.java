package com.zkwl.qhzwj;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.alibaba.fastjson.JSONObject;
import com.king.frame.mvvmframe.base.BaseModel;
import com.king.frame.mvvmframe.base.DataViewModel;
import com.king.frame.mvvmframe.http.callback.ApiCallback;
import com.zkwl.qhzwj.api.AppApiService;
import com.zkwl.qhzwj.bean.Result;
import com.zkwl.qhzwj.bean.UserLoginBean;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
@HiltViewModel
public class LoginViewModel extends DataViewModel {

    private MutableLiveData<Result<UserLoginBean>> liveData = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> userLoginSendCode = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> sendRegisterCode = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> registerUser = new MutableLiveData<>();
    private MutableLiveData<Result<List<JSONObject>>> getRegisterCommunityList = new MutableLiveData<>();

    @Inject
    public LoginViewModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }


    public void Login(String username, String password,  String device_id, String login_type) {
        showLoading();
        getRetrofitService(AppApiService.class).userLogin(username, password, device_id, login_type)
                .enqueue(new ApiCallback<Result<UserLoginBean>>() {
                    @Override
                    public void onResponse(Call<Result<UserLoginBean>> call, Result<UserLoginBean> result) {
                        hideLoading();
                        Log.e("onResponse","result"+result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveData.postValue(result);
                        } else {
                            sendMessage(result.getMessage(), true);
                        }
                    }

                    @Override
                    public void onError(Call<Result<UserLoginBean>> call, Throwable t) {
                        hideLoading();
                        Log.e("result", "onError" + t.getMessage());
                        sendMessage(t.getMessage(), true);
                    }
                });
    }
    public void userLoginSendCode(String username) {
        getRetrofitService(AppApiService.class).userLoginSendCode(username)
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        Log.e("onResponse","result"+result.toString());
                        if (result != null && result.getCode() == 200) {
                            userLoginSendCode.postValue(result);
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
    public void sendRegisterCode(String username) {
        showLoading();
        getRetrofitService(AppApiService.class).sendRegisterCode(username)
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        hideLoading();
                        Log.e("onResponse","result"+result.toString());
                        if (result != null && result.getCode() == 200) {
                            sendRegisterCode.postValue(result);
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
    public void registerUser(String mobile, String code, String password, String confirm_password,String community_id) {
        showLoading();
        getRetrofitService(AppApiService.class).registerUser(mobile,code,password,confirm_password,community_id)
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        hideLoading();
                        Log.e("onResponse","result"+result.toString());
                        if (result != null && result.getCode() == 200) {
                            registerUser.postValue(result);
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
    public void RegisterCommunityList() {
        getRetrofitService(AppApiService.class).getRegisterCommunityList()
                .enqueue(new ApiCallback<Result<List<JSONObject>>>() {
                    @Override
                    public void onResponse(Call<Result<List<JSONObject>>> call, Result<List<JSONObject>> result) {
                        Log.e("onResponse","result"+result.toString());
                        if (result != null && result.getCode() == 200) {
                            getRegisterCommunityList.postValue(result);
                        } else {
                            sendMessage(result.getMessage(), true);
                        }
                    }

                    @Override
                    public void onError(Call<Result<List<JSONObject>>> call, Throwable t) {
                        Log.e("result", "onError" + t.getMessage());
                        sendMessage(t.getMessage(), true);
                    }
                });
    }




    public MutableLiveData<Result<UserLoginBean>> getLogindata(){
        return liveData;
    }
    public MutableLiveData<Result<JSONObject>> getuserLoginSendCode(){
        return userLoginSendCode;
    }
    public MutableLiveData<Result<JSONObject>> getsendRegisterCode(){
        return sendRegisterCode;
    }
    public MutableLiveData<Result<JSONObject>> getregisterUser(){
        return registerUser;
    }

    public MutableLiveData<Result<List<JSONObject>>> getRegisterCommunityList(){
        return getRegisterCommunityList;
    }
}
