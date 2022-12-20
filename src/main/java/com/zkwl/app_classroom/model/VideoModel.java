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
public class VideoModel extends DataViewModel {

    private MutableLiveData<Result<JSONArray>> liveDataadlist = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDataClasshourinfo = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDataadd_comment = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDataadd_like = new MutableLiveData<>();
    private MutableLiveData<Result<JSONObject>> liveDatareply_comment = new MutableLiveData<>();

    @Inject
    public VideoModel(@NonNull Application application, BaseModel model) {
        super(application, model);
    }


    public void getCommentlist(String id) {
        getRetrofitService(ClassroomApiService.class)
                .getCommentlist(id)
                .enqueue(new ApiCallback<Result<JSONArray>>() {
                    @Override
                    public void onResponse(Call<Result<JSONArray>> call, Result<JSONArray> result) {
                        Log.e("result", "" + result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveDataadlist.postValue(result);
                        } else {
                            sendMessage(result.getMessage(), true);
                        }
                    }

                    @Override
                    public void onError(Call<Result<JSONArray>> call, Throwable t) {
                        Log.e("result", "onError" + t.getMessage());
                        sendMessage(t.getMessage(), true);
                    }
                });
    }

    public void getClasshourinfo(String id) {
        getRetrofitService(ClassroomApiService.class)
                .getClasshourinfo(id)
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        Log.e("result", "" + result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveDataClasshourinfo.postValue(result);
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

    public void add_comment(String class_hour_id, String comment_msg) {
        showLoading();
        getRetrofitService(ClassroomApiService.class)
                .add_comment(class_hour_id, comment_msg)
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        hideLoading();
                        Log.e("result", "" + result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveDataadd_comment.postValue(result);
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

    public void add_like(String class_hour_id) {
        getRetrofitService(ClassroomApiService.class)
                .add_like(class_hour_id)
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        Log.e("result", "" + result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveDataadd_like.postValue(result);
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

    public void reply_comment(String parent_comment_id, String comment_msg) {
        showLoading();
        getRetrofitService(ClassroomApiService.class)
                .reply_comment(parent_comment_id, comment_msg)
                .enqueue(new ApiCallback<Result<JSONObject>>() {
                    @Override
                    public void onResponse(Call<Result<JSONObject>> call, Result<JSONObject> result) {
                        hideLoading();
                        Log.e("result", "" + result.toString());
                        if (result != null && result.getCode() == 200) {
                            liveDatareply_comment.postValue(result);
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

    public LiveData<Result<JSONObject>> GetliveDataClasshourinfo() {
        return liveDataClasshourinfo;
    }

    public LiveData<Result<JSONObject>> getliveDataadd_comment() {
        return liveDataadd_comment;
    }

    public LiveData<Result<JSONObject>> getliveDataadd_like() {
        return liveDataadd_like;
    }

    public LiveData<Result<JSONObject>> getliveDatareply_comment() {
        return liveDatareply_comment;
    }

}
