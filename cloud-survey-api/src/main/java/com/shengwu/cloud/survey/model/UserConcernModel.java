package com.shengwu.cloud.survey.model;

import java.io.Serializable;
import java.util.List;

/**
 * 用户关注model
 * Author   shengwu。
 * Date:    2017/10/2 0002
 */
public class UserConcernModel implements Serializable {

    /**
     * 用户id。
     */
    private String userId;

    /**
     * 用户关注的用户账号id,批量操作。
     */
    private List<String> userConcernIds;

    /**
     * 是否关注。
     */
    private String isConcern;

    /**
     * 创建时间。
     */
    private String createTime;

    /**
     * 更细时间。
     */
    private String updateTime;

    public UserConcernModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getUserConcernIds() {
        return userConcernIds;
    }

    public void setUserConcernIds(List<String> userConcernIds) {
        this.userConcernIds = userConcernIds;
    }

    public String getIsConcern() {
        return isConcern;
    }

    public void setIsConcern(String isConcern) {
        this.isConcern = isConcern;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
