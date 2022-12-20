package com.zkwl.qhzwj.bean;

/**
 * Created by Administrator on 2019/9/16.
 */

public class GoodListBean {


    private String id;
    private String image_url;
    private String merchant_id;
    private String goods_introduce;
    private String goods_name;
    private String code_num;
    private String fact_sales_volume;
    private String sales_volume;
    private String unit_name;
    private String goods_unit_count;
    private String member_price;
    private String market_price;
    private int carCount = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoods_unit_count() {
        return goods_unit_count;
    }

    public void setGoods_unit_count(String goods_unit_count) {
        this.goods_unit_count = goods_unit_count;
    }

    public String getMember_price() {
        return member_price;
    }

    public void setMember_price(String member_price) {
        this.member_price = member_price;
    }

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public int getCarCount() {
        return carCount;
    }

    public void setCarCount(int carCount) {
        this.carCount = carCount;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getGoods_introduce() {
        return goods_introduce;
    }

    public void setGoods_introduce(String goods_introduce) {
        this.goods_introduce = goods_introduce;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }

    public String getCode_num() {
        return code_num;
    }

    public void setCode_num(String code_num) {
        this.code_num = code_num;
    }

    public String getFact_sales_volume() {
        return fact_sales_volume;
    }

    public void setFact_sales_volume(String fact_sales_volume) {
        this.fact_sales_volume = fact_sales_volume;
    }

    public String getSales_volume() {
        return sales_volume;
    }

    public void setSales_volume(String sales_volume) {
        this.sales_volume = sales_volume;
    }
}
