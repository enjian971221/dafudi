package com.zkwl.qhzwj.bean;

/**
 * Created by Administrator on 2019/9/30.
 */

public class GoodStoreInfoBean {


    /**
     * id : 26
     * merchant_type : 2
     * merchant_no : r9220190817144459616
     * image_url : http://image.sdzkworld.com/goods/20190817/1566024160_1444775.jpeg
     * start_time : 14:43:00
     * end_time : 14:43:00
     * introduction : 付家超市哈哈
     * merchant_name : 付家超市
     * contacter_name : 付其超
     * telephone : 18668907108
     * mobile : 25666028414
     * address : 高新区
     * in_time : 2019-08-17
     * is_service : 0
     * property_id : 52
     * merchant_type_name : 实体类
     */

    private String id;
    private int merchant_type;
    private String merchant_no;
    private String image_url;
    private String start_time;
    private String end_time;
    private String introduction;
    private String merchant_name;
    private String contacter_name;
    private String telephone;
    private String mobile;
    private String address;
    private String in_time;
    private String is_service;
    private String property_id;
    private String merchant_type_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMerchant_type() {
        return merchant_type;
    }

    public void setMerchant_type(int merchant_type) {
        this.merchant_type = merchant_type;
    }

    public String getMerchant_no() {
        return merchant_no;
    }

    public void setMerchant_no(String merchant_no) {
        this.merchant_no = merchant_no;
    }

    public String getImage_url() {
        return image_url == null ? "" : image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getMerchant_name() {
        return merchant_name;
    }

    public void setMerchant_name(String merchant_name) {
        this.merchant_name = merchant_name;
    }

    public String getContacter_name() {
        return contacter_name;
    }

    public void setContacter_name(String contacter_name) {
        this.contacter_name = contacter_name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    public String getIs_service() {
        return is_service;
    }

    public void setIs_service(String is_service) {
        this.is_service = is_service;
    }

    public String getProperty_id() {
        return property_id;
    }

    public void setProperty_id(String property_id) {
        this.property_id = property_id;
    }

    public String getMerchant_type_name() {
        return merchant_type_name;
    }

    public void setMerchant_type_name(String merchant_type_name) {
        this.merchant_type_name = merchant_type_name;
    }
}
