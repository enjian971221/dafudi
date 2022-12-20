package com.zkwl.qhzwj.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/9/24.
 */

public class CommunityIndexBean {


    /**
     * id : 8928
     * user_id : 51
     * community_id : 27
     * community_name : 荷塘月色
     */

    private String id;
    private String user_id;
    private String community_id;
    private String company_code;
    private String community_name;
    private List<CommunityIndexBuildBean> build_arr;

    public String getCompany_code() {
        return company_code;
    }

    public void setCompany_code(String company_code) {
        this.company_code = company_code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public List<CommunityIndexBuildBean> getBuild_arr() {
        return build_arr;
    }

    public void setBuild_arr(List<CommunityIndexBuildBean> build_arr) {
        this.build_arr = build_arr;
    }

    @Override
    public String toString() {
        return "CommunityIndexBean{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", community_id='" + community_id + '\'' +
                ", company_code='" + company_code + '\'' +
                ", community_name='" + community_name + '\'' +
                ", build_arr=" + build_arr +
                '}';
    }
}
