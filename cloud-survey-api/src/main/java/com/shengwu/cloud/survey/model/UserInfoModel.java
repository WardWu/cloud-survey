package com.shengwu.cloud.survey.model;

import java.io.Serializable;

/**
 * 用户资料model
 * Author   shengwu。
 * Date:    2017/10/2 0002
 */
public class UserInfoModel implements Serializable {

    /**
     * 用户id。
     */
    private String userId;

    /**
     * 用户账号。
     */
    private String userAccount;

    /**
     * 姓名。
     */
    private String userName;

    /**
     * 用户昵称。
     */
    private String userNickName;

    /**
     * 手机号。
     */
    private int phoneNum;

    /**
     * 邮箱。
     */
    private String mail;

    /**
     * 性别。
     */
    private int sex;

    /**
     * 出生年月,YYYY-mm-DD。
     */
    private String date;

    /**
     * 年龄。
     */
    private int age;

    /**
     * 地址。
     */
    private String address;

    /**
     * 个性签名。
     */
    private String sign;

    /**
     * 个人说明。
     */
    private String introduction;

    /**
     * 头像地址。
     */
    private String iconUrl;

    /**
     * 创建时间。
     */
    private String createTime;

    /**
     * 更新时间。
     */
    private String updateTime;

    /**
     * 账号状态。
     */
    private int isDelete;

    public UserInfoModel() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public int getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(int phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
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

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }
}
