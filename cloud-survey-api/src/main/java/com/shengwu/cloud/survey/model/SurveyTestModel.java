package com.shengwu.cloud.survey.model;

import java.io.Serializable;

/**
 * Model Test。
 * Created by shengwu on 2017/9/14.
 */
public class SurveyTestModel implements Serializable {

    /**
     * 用户id。
     */
    private String userId;

    /**
     * 用户名称。
     */
    private String userName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
