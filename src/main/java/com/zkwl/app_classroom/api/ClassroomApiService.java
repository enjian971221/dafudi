package com.zkwl.app_classroom.api;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zkwl.app_classroom.bean.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public interface ClassroomApiService {


    /**
     * 轮播图列表
     *
     * @return
     */
    @GET("course/ad_list")
    Call<Result<JSONArray>> getAdlist();


    /**
     * 分类列表
     *
     * @return
     */
    @GET("course/category_list")
    Call<Result<JSONArray>> getCategorylist();

    /**
     * 课程列表
     *
     * @return
     */
    @GET("course/list")
    Call<Result<JSONObject>> getCourselist(@Query("title") String title, @Query("category_id") String category_id,
                                           @Query("is_recommend") String is_recommend, @Query("page") String page, @Query("page_size") String page_size);

    /**
     * 课程详情
     *
     * @return
     */
    @GET("course/info")
    Call<Result<JSONObject>> getCourseinfo(@Query("id") String id);

    /**
     * 评论列表
     *
     * @return
     */
    @GET("course/comment_list")
    Call<Result<JSONArray>> getCommentlist(@Query("class_hour_id") String class_hour_id);

    /**
     * 课时详情
     *
     * @return
     */
    @GET("course/class_hour_info")
    Call<Result<JSONObject>> getClasshourinfo(@Query("id") String id);

    /**
     * 我的账户余额
     *
     * @return
     */
    @GET("balance/info")
    Call<Result<JSONObject>> getBalanceinfo();

    /**
     * 我的账户余额
     *
     * @return
     */
    @GET("user/check_paypwd")
    Call<Result<JSONObject>> getcheck_paypwd();


    //发送验证码-http://beta.sdzkworld.com/api/proprietor/user/send_sms
    // 1 用户登录发送验证码2 修改密码 3注册 4修改支付密码
    @GET("user/send_sms")
    Call<Result<JSONObject>> sendVerificationCode(@Query("sms_type") String sms_type);

    //设置支付密码-http://beta.sdzkworld.com/api/proprietor/user/paypwd
    @PATCH("user/paypwd")
    Call<Result<JSONObject>> setPayPwd(@Query("code") String code, @Query("confirm_password") String confirm_password);

    //评论-
    @FormUrlEncoded
    @POST("course/add_comment")
    Call<Result<JSONObject>> add_comment(@Field("class_hour_id") String class_hour_id,@Field("comment_msg") String comment_msg);

    //点赞-
    @FormUrlEncoded
    @POST("course/add_like")
    Call<Result<JSONObject>> add_like(@Field("comment_id") String comment_id);

    //点赞-
    @FormUrlEncoded
    @POST("course/reply_comment")
    Call<Result<JSONObject>> reply_comment(@Field("parent_comment_id") String parent_comment_id,@Field("comment_msg") String comment_msg);


    /**
     * 发起支付
     *
     * @return
     */
    @FormUrlEncoded
    @POST("course/pay")
    Call<Result<JSONObject>> Coursepay(@Field("sign_data") String sign_data, @Field("id") String id
            , @Field("pay_type") String pay_type, @Field("paypwd") String paypwd);


}
