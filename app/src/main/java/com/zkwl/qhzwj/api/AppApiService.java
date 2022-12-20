package com.zkwl.qhzwj.api;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zkwl.qhzwj.bean.BindCommunityBean;
import com.zkwl.qhzwj.bean.CommPage;
import com.zkwl.qhzwj.bean.CommunityIndexBean;
import com.zkwl.qhzwj.bean.GoodStoreInfoBean;
import com.zkwl.qhzwj.bean.Response;
import com.zkwl.qhzwj.bean.Result;
import com.zkwl.qhzwj.bean.SimpleGoodBean;
import com.zkwl.qhzwj.bean.StoreGoodTypeGroupBean;
import com.zkwl.qhzwj.bean.UserLoginBean;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public interface AppApiService {


    /*******************************用户**********************************/
    //用户登录-
    @FormUrlEncoded
    @POST("login")
    Call<Result<UserLoginBean>> userLogin(@Field("username") String username, @Field("password") String password, @Field("device_id") String device_id, @Field("login_type") String login_type);

    //用户登录验证码
    @FormUrlEncoded
    @POST("login_sms")
    Call<Result<JSONObject>> userLoginSendCode(@Field("mobile") String mobile);

    //注册发送验证码-
    @FormUrlEncoded
    @POST("register/sms")
    Call<Result<JSONObject>> sendRegisterCode(@Field("mobile") String mobile);



    //注册-
    @FormUrlEncoded
    @POST("register/send")
    Call<Result<JSONObject>> registerUser(@Field("mobile") String mobile, @Field("code") String code, @Field("password") String password, @Field("confirm_password") String confirm_password, @Field("community_id") String community_id);

    //登录-小区选择
    @GET("community_list")
    Call<Result<List<JSONObject>>> getRegisterCommunityList();

    //首页里面的tab-http://beta.sdzkworld.com/api/proprietor/index/get_newmenu
    @GET("index/get_newmenu")
    Call<Result<JSONArray>> getHomeNewTab();

    //首页里面的轮播图-http://beta.sdzkworld.com/api/proprietor/index/advert_list
    @GET("index/advert_list")
    Call<Result<JSONArray>> getBannerList(@Query("url_place") String url_place);

    //首页里面的商品推荐http://beta.sdzkworld.com/api/proprietor/goods_recommend/list
    @GET("goods_recommend/list")
    Call<Result<JSONObject>> getHomeGoodsList(@Query("page") String page, @Query("page_size") String page_size);

    //首页整合接口http://beta.sdzkworld.com/api/proprietor/index/meraps
    @GET("index/meraps")
    Call<Result<JSONObject>> getHomeMerge();

    //首页里面的社区选择 http://beta.sdzkworld.com/api/proprietor/index/community_list
    @GET("index/community_list")
    Call<Response<List<CommunityIndexBean>>> getCommunityIndexList();


    //认证审核详情-绑定社区的时候使用的-http://beta.sdzkworld.com/api/proprietor/proaudit/check
    @GET("proaudit/check")
    Call<Response<BindCommunityBean>> getBindCommunityInfo();

    //获取商品列表-http://beta.sdzkworld.com/api/proprietor/goodsinfo/list?category_id=30&goods_name=&is_new=&merchant_id=27&page=1&page_size=10&price_order=&sales_volume=
    @GET("goodsinfo/list")
    Call<Response<CommPage<SimpleGoodBean>>> getGoodList(@Query("page") String page, @Query("page_size") String page_size, @Query("goods_name") String goods_name,
                                                               @Query("sales_volume") String sales_volume, @Query("price_order") String price_order, @Query("is_new") String is_new,
                                                         @Query("category_id") String category_id, @Query("merchant_id") String merchant_id);


    //*****************************************商家***************************************************
    //获取商家的信息-http://beta.sdzkworld.com/api/proprietor/merchant/info
    @GET("merchant/info")
    Call<Response<GoodStoreInfoBean>> getGoodStoreInfo(@Query("id") String id);


    //获取商家的商品分类-http://beta.sdzkworld.com/api/proprietor/goodscategory/list_by_merchant
    @GET("goodscategory/list_by_merchant")
    Call<Response<StoreGoodTypeGroupBean>> getStoreGoodType(@Query("merchant_id") String merchant_id);

}
