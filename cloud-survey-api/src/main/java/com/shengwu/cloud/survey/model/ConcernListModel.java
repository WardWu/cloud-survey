package com.shengwu.cloud.survey.model;

import java.io.Serializable;
import java.util.List;

/**
 * 用户关注列表。
 * Author   shengwu。
 * Date:    2017/10/2 0002
 */
public class ConcernListModel implements Serializable {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 用户关注列表。
     */
    private List<String> userConcernList;

    /**
     * 用户粉丝列表。
     */
    private List<String> fansList;

    public ConcernListModel() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getUserConcernList() {
        return userConcernList;
    }

    public void setUserConcernList(List<String> userConcernList) {
        this.userConcernList = userConcernList;
    }

    public List<String> getFansList() {
        return fansList;
    }

    public void setFansList(List<String> fansList) {
        this.fansList = fansList;
    }
}
