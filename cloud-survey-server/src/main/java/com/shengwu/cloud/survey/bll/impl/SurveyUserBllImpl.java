package com.shengwu.cloud.survey.bll.impl;

import com.shengwu.cloud.survey.bll.SurveyUserBll;
import com.shengwu.cloud.survey.cache.CacheHelper;
import com.shengwu.cloud.survey.dao.SurveyUserDao;
import com.shengwu.cloud.survey.model.ConcernListModel;
import com.shengwu.cloud.survey.model.UserBaseModel;
import com.shengwu.cloud.survey.model.UserConcernModel;
import com.shengwu.cloud.survey.model.UserInfoListModel;
import com.shengwu.cloud.survey.model.UserUpdateModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Author   shengwu。
 * Date:    2017/10/3 0003
 */
@Repository
public class SurveyUserBllImpl implements SurveyUserBll {

    private static final Session mailSession = SurveyUserBllImpl.getMailSession();
    @Autowired
    SurveyUserDao surveyUserDao;
    @Autowired
    CacheHelper cacheHelper;

    public static Session getMailSession() {
        // 发件人电子邮箱
        String mailFromName = "364434660@qq.com";
        String mailFromPassword = "gbwnhtmplxoxbjec";

        // 指定发送邮件的主机为 localhost
        String host = "smtp.qq.com";  //QQ 邮件服务器

        // 获取系统属性
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.port", "465");


        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailFromName, mailFromPassword);
            }
        });
        return session;

    }

    @Override
    public String createUser(UserBaseModel userBaseModel) {
        String userId = null;
        try {
            userId = surveyUserDao.createUser(userBaseModel);
        } catch (Exception e) {
            System.out.println("The userAccount can't create:" + userBaseModel.getUserAccount());
        }
        if (StringUtils.isNotBlank(userId)) {
            userBaseModel.setUserId(userId);
            surveyUserDao.updateUser(userBaseModel);
        }
        return userId;
    }

    @Override
    public UserBaseModel getUser(String userAccount, String password, String updateTime) {
        return surveyUserDao.getUser(userAccount, password, updateTime);
    }

    @Override
    public Boolean updateUser(UserBaseModel userBaseModel) {
        return surveyUserDao.updateUser(userBaseModel);
    }

    @Override
    public Boolean deleteUser(String userId) {
        return surveyUserDao.deleteUser(userId);
    }

    @Override
    public Boolean UpdateUserPassword(UserUpdateModel userUpdateModel) {

        // TODO
        // 用户修改密码操作
        // 1.账号密码验证
        // 2.账号邮箱验证
        // 3.账号手机号验证
        if ("1".equals(userUpdateModel.getType())) {
            return surveyUserDao.updateUserPassword(userUpdateModel);
        } else if ("2".equals(userUpdateModel.getType())) {
            return this.getMailCheckout(userUpdateModel);
        } else if ("3".equals(userUpdateModel.getType())) {
            return false;
        } else {
            return null;
        }
    }

    @Override
    public Boolean addConcern(String userId, String userConcernIds, Boolean isConcern) {
        List<String> userConcernIdList = Arrays.asList(userConcernIds.replaceAll(" ", "").split(","));
        return surveyUserDao.addConcern(userId, userConcernIdList, isConcern);
    }

    @Override
    public ConcernListModel getConcernList(String userId) {
        return null;
    }

    @Override
    public UserInfoListModel getUserInfo(String userId) {
        return null;
    }

    @Override
    public UserInfoListModel getUserInfoList(List<String> userList) {
        return null;
    }

    /**
     * 邮箱发送验证码，验证码存入缓存中。
     * @param userId    用户id
     * @param userMail  用户邮箱
     * @return boolean
     */
    public Boolean getMailCode(String userId, String userMail) {
        String mailTitle = "云调查平台";
        String mailBody = "  账号密码修改验证码,请查收...";
        String idCode = "100001";
        String mailCode = "  账号密码修改验证码: " + idCode;
        boolean isSend = false;
        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(mailSession);
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(mailTitle));
            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userMail));
            // Set Subject: 头部头字段
            message.setSubject(mailBody);
            // 设置消息体
            message.setText(mailCode);
            // 发送消息
            Transport.send(message);
            cacheHelper.setCache(userId, idCode);
            isSend = true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            System.out.println("mail send error.....");
        }
        return isSend;
    }

    /**
     * 邮箱验证。
     * @param userUpdateModel 用户信息
     * @return  Boolean
     */
    private boolean getMailCheckout(UserUpdateModel userUpdateModel) {
        String userMailCode = cacheHelper.getCache(userUpdateModel.getUserId());
        boolean isUpdate = false;
        if (userUpdateModel.getIdCode().equals(userMailCode)) {
            isUpdate = surveyUserDao.updateUserPassword(userUpdateModel);
        }
        return isUpdate;
    }
}
