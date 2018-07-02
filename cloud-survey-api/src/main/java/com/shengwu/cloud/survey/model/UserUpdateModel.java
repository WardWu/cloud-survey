package com.shengwu.cloud.survey.model;

import java.io.Serializable;

/**
 * 用户账号密码修改model。
 * Author   shengwu。
 * Date:    2017/10/2 0002
 */
public class UserUpdateModel implements Serializable {

    /**
     * 用户id。
     */
    private String userId;

    /**
     * 用户密码。
     */
    private String password;

    /**
     * 用户邮箱。
     */
    private String mail;

    /**
     * 用户手机号。
     */
    private String phoneNum;

    /**
     * 验证码。
     */
    private String idCode;

    /**
     * 用户状态。
     */
    private String isDelete;

    /**
     * 用户请求类型。
     */
    private String type;

    public UserUpdateModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
