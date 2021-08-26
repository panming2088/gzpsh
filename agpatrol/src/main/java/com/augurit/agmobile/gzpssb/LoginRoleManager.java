package com.augurit.agmobile.gzpssb;

import android.text.TextUtils;

import com.augurit.agmobile.gzps.BaseApplication;
import com.augurit.am.fw.net.util.SharedPreferencesUtil;

/**
 * com.augurit.agmobile.gzpssb
 * Created by sdb on 2018/4/11  9:20.
 * Desc：登录角色判断
 */

public class LoginRoleManager {
    public LoginRoleManager() {
    }


    public static void setCurLoginRole(String userType) {
        String type = "";
        if (!TextUtils.isEmpty(userType)) {
            userType = userType.toLowerCase();
            if (userType.contains("ps_") && userType.contains("nw") && userType.contains("psh")) {
                //领导
                type = LoginRoleConstant.LOGIN_LEADER;

            } else if (userType.contains("ps_") && !userType.contains("nw") && !userType.contains("psh")) {
                //排水
                type = LoginRoleConstant.LOGIN_PS;

            } else if (userType.contains("nw") && !userType.contains("ps_") && !userType.contains("psh")) {
                //农污
                type = LoginRoleConstant.LOGIN_WS;

            } else if (userType.contains("psh") && !userType.contains("nw") && !userType.contains("ps_")) {
                //排水户
                type = LoginRoleConstant.LOGIN_PSH;

            } else if (userType.contains("nw") && userType.contains("psh") && !userType.contains("ps_")) {
                //排水户+农污
                type = LoginRoleConstant.LOGIN_PSH_WS;

            } else if (userType.contains("ps_") && userType.contains("psh") && !userType.contains("nw")) {
                //排水户+排水
                type = LoginRoleConstant.LOGIN_PSH_PS;

            } else if (userType.contains("ps_") && userType.contains("nw") && !userType.contains("psh")) {
                //农污+排水
                type = LoginRoleConstant.LOGIN_PS_WS;

            } else {
                //领导
                type = LoginRoleConstant.LOGIN_LEADER;
            }
        }else{
            type = LoginRoleConstant.LOGIN_LEADER;
        }

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(BaseApplication.application);
        sharedPreferencesUtil.setString("login_role", type);
    }

    public static String getCurLoginrRole() {
        return new SharedPreferencesUtil(BaseApplication.application).getString("login_role", "0");
    }
}
