package com.zkwl.qhzwj.util;


import com.king.base.util.StringUtils;
import com.zkwl.qhzwj.api.Constants;
import com.zkwl.qhzwj.bean.UserLoginBean;

/**
 * @author songkai
 * @date on 2019/8/20
 */
public class UserDataManager {

    public static void saveUserInfo(UserLoginBean userLoginBean) {
        SpsUtil.put( Constants.USER_ID, userLoginBean.getUser_id());
        SpsUtil.put( Constants.USER_NAME, userLoginBean.getNick_name());
        SpsUtil.put( Constants.USER_PHONE, userLoginBean.getMobile_phone());
        SpsUtil.put( Constants.USER_PHOTO, userLoginBean.getPhoto());
        SpsUtil.put( Constants.AccessToken, userLoginBean.getAccess_token());
        SpsUtil.put( Constants.CommunityName, userLoginBean.getCommunity_name());
        SpsUtil.put( Constants.CommunityId, userLoginBean.getCommunity_id());
        SpsUtil.put( Constants.PROPERTY_ID, userLoginBean.getProperty_id());
        SpsUtil.put( Constants.CompanyCode, userLoginBean.getCompany_code());
        //判断用户是否绑定小区
        if (userLoginBean.getArea_token() != null && StringUtils.isNotBlank(userLoginBean.getArea_token().getBuilding_id()) && StringUtils.isNotBlank(userLoginBean.getArea_token().getCommunity_id()) && StringUtils.isNotBlank(userLoginBean.getArea_token().getProperty_id())) {
            if (StringUtils.equals(userLoginBean.getArea_token().getBuilding_id(), "0")) {
                SpsUtil.put( Constants.IsAreaToken, false);
            } else {
                SpsUtil.put( Constants.IsAreaToken, true);
            }
            SpsUtil.put( Constants.AreaToken, userLoginBean.getArea_token().toString());
        } else {
            SpsUtil.put( Constants.IsAreaToken, false);
        }

    }

    public static void userLogout() {
        SpsUtil.put( Constants.USER_IS_FRIST_LOGIN, false);
        SpsUtil.put( Constants.USER_ID, "");
        SpsUtil.put( Constants.USER_NAME, "");
        SpsUtil.put( Constants.USER_PHONE, "");
        SpsUtil.put( Constants.USER_PHOTO, "");
        SpsUtil.put( Constants.AccessToken, "");
        SpsUtil.put( Constants.CommunityName, "");
        SpsUtil.put( Constants.CommunityId, "");
        SpsUtil.put( Constants.PROPERTY_ID, "");
        SpsUtil.put( Constants.AreaToken, "");
        SpsUtil.put( Constants.CompanyCode, "");
        SpsUtil.put( Constants.IsAreaToken, false);

    }



}