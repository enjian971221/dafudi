package com.zkwl.qhzwj.bean;


import com.king.base.util.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2019/9/12.
 */

public class UserLoginBean {


    /**
     * user_id : 51
     * access_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiIiLCJhdWQiOiIiLCJpYXQiOjE1NjgyNTUxMzUsImV4cCI6MTU3MDg0NzEzNSwibmJmIjoxMzU3MDAwMDAwLCJqdGkiOiI2RjgxQUE1NjIxMDMxRDc4NjQwRjkwMjFGNzA1M0JCQSIsImlkIjo1MSwibmlja25hbWUiOiJcdTVmMjBcdTk2ZWFcdTY2MGUifQ.WDaHS8NZ9EMrwk1ezAk0s01b3ydOV49qAzJ0V6wpP5k
     * area_token : {"community_id":27,"building_id":1618,"property_id":52}
     * community_name : 荷塘月色
     * expires_in : 1568341535
     * community_id : 27
     */

    private String user_id;
    private String access_token;
    private LoginAreaTokenBean area_token;
    private String community_name;
    private String expires_in;
    private String photo;
    private String nick_name;
    private String mobile_phone;
    private String community_id;
    private String company_code;
    private List<String> community_tag;

    public List<String> getCommunity_tag() {
        return community_tag;
    }

    public void setCommunity_tag(List<String> community_tag) {
        this.community_tag = community_tag;
    }

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public LoginAreaTokenBean getArea_token() {
        return area_token;
    }

    public void setArea_token(LoginAreaTokenBean area_token) {
        this.area_token = area_token;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public String getProperty_id() {
        if (area_token != null && StringUtils.isNotBlank(area_token.getProperty_id())) {
            return area_token.getProperty_id();
        }
        return "";
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }
}
