package com.shengwu.cloud.survey.model;

import java.io.Serializable;
import java.util.List;

/**
 * 用户资料列表。
 * Author   shengwu。
 * Date:    2017/10/2 0002
 */
public class UserInfoListModel implements Serializable {

    /**
     * 总数。
     */
    private Long total;

    /**
     * 用户资料列表。
     */
    private List<UserInfoModel> userInfoModels;

    public UserInfoListModel() {}

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<UserInfoModel> getUserInfoModels() {
        return userInfoModels;
    }

    public void setUserInfoModels(List<UserInfoModel> userInfoModels) {
        this.userInfoModels = userInfoModels;
    }
}
