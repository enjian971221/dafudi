package com.zkwl.app_healthy.api;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zkwl.app_healthy.bean.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author <a href="mailto:jenly1314@gmail.com">Jenly</a>
 */
public interface HealthyApiService {


    /**
     * 商品列表
     *
     * @return
     */
    @GET("traditional/goods/all_list")
    Call<Result<JSONObject>> goodsAlllist(@Query("goods_name") String goods_name, @Query("shop_id") String shop_id,
                                          @Query("goods_type") String goods_type, @Query("page") String page,
                                          @Query("page_size") String page_size, @Query("is_recommend") String is_recommend);

    /**
     * 分类列表
     *
     * @return
     */
    @GET("traditional/shop/classify")
    Call<Result<JSONArray>> classify();

    /**
     * 门店列表
     *
     * @return
     */
    @GET("traditional/shop/list")
    Call<Result<JSONArray>> getShoplist(@Query("name") String name, @Query("longitude") String longitude,
                                        @Query("latitude") String latitude);

    /**
     * 普通商品详情
     *
     * @return
     */
    @GET("traditional/goods/detail")
    Call<Result<JSONObject>> getGoodsdetail(@Query("id") String id, @Query("shop_id") String shop_id);


    /**
     * 套餐详情
     *
     * @return
     */
    @GET("traditional/goods.meal/detail")
    Call<Result<JSONObject>> getGoodsmealdetail(@Query("id") String id, @Query("shop_id") String shop_id);

    /**
     * 我的账户余额
     *
     * @return
     */
    @GET("balance/info")
    Call<Result<JSONObject>> getBalanceinfo();

    /**
     * 购买详情
     *
     * @return
     */
    @GET("traditional/order/goods_money")
    Call<Result<JSONObject>> getGoodsmoney(@Query("goods_id") String goods_id, @Query("shop_id") String shop_id);

    /**
     * 订单列表
     *
     * @return
     */
    @GET("traditional/order/list")
    Call<Result<JSONObject>> getOrderList(@Query("status") String status, @Query("page") String page, @Query("page_size") String page_size);

    /**
     * 订单详情
     *
     * @return
     */
    @GET("traditional/order/detail")
    Call<Result<JSONObject>> getOrderdetail(@Query("id") String id);

    /**
     * 干细胞介绍
     *
     * @return
     */
    @GET("stemcells/get")
    Call<Result<JSONObject>> getstemcells(@Query("type") String type);


    /**
     * 中医理疗支付
     *
     * @return
     */
    @FormUrlEncoded
    @POST("traditional/order/goods_pay")
    Call<Result<JSONObject>> getGoodspay(@Field("pay_type") String pay_type, @Field("goods_id") String goods_id, @Field("shop_id") String shop_id);


    /**
     * 中医理疗支付
     *
     * @return
     */
    @FormUrlEncoded
    @POST("traditional/order/go_pay")
    Call<Result<JSONObject>> getGospay(@Field("id") String id, @Field("pay_type") String pay_type);


}
