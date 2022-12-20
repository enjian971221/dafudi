package com.zkwl.qhzwj.bean;

/**
 * Created by Administrator on 2019/9/12.
 */

public class LoginAreaTokenBean {

    /**
     * community_id : 27
     * building_id : 1618
     * property_id : 52
     */

    private String community_id;
    private String building_id;
    private String property_id;

    public String getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(String community_id) {
        this.community_id = community_id;
    }

    public String getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(String building_id) {
        this.building_id = building_id;
    }

    public String getProperty_id() {
        return property_id;
    }

    public void setProperty_id(String property_id) {
        this.property_id = property_id;
    }

    @Override
    public String toString() {
        return "{\"community_id\":"+community_id+", \"building_id\":"+building_id+",\"property_id\":"+property_id+"}";
    }
}
