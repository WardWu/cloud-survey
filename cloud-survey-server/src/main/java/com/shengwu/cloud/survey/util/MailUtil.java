package com.shengwu.cloud.survey.util;

import com.shengwu.cloud.survey.cache.CacheHelper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.Random;

/**
 * 创建邮箱工具。
 * Author： shengwu
 * DATE ：  2018/1/12
 */
public class MailUtil {

    private static final String sender = "364434660@qq.com";

    private static final String senderUserName = "364434660@qq.com";   // 邮箱账号
    private static final String senderPassword = "gbwnhtmplxoxbjec";   //邮箱密码

    private static final String mailHost = "smtp.qq.com"; // 指定发送邮件的主机为 localmailHost

    private static final String randomCode = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";

    private static final int mailCodeLength = 4;

    private Session session;

    @Autowired
    private CacheHelper cacheHelper;

    // 获取验证码
    public String getMailCode(String userMail) {
        if (session == null) {
            session = this.getSession();
        }
        // 创建默认的 MimeMessage 对象
        MimeMessage message = new MimeMessage(session);
        String mailCode = this.getRandomStr();
        try {
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(sender));
            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userMail));
            // Set Subject: 头部头字段
            message.setSubject("This is t he Subject Line!");
            // 设置消息体
            message.setText("验证码:" +  mailCode);
            // 发送消息
            Transport.send(message);
            // code加入缓存  设置缓存时间 3min
            cacheHelper.setCache(mailCode, mailCode);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return mailCode;
    }

    // 获取邮箱服务器配置
    private Session getSession() {
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.port", "465");
        properties.setProperty("mail.smtp.socketFactory.port", "465");

        // 设置邮件服务器
        properties.setProperty("mail.smtp.mailHost", mailHost);
        properties.put("mail.smtp.auth", "true");
        try {
            session = Session.getDefaultInstance(properties,new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(senderUserName, senderPassword); //发件人邮件用户名、密码
                }
            });
        } catch (Exception e) {
            System.out.println("getSession.error");
            e.printStackTrace();
        }
        return session;
    }

    // 获取随机数
    private String getRandomStr() {
        Random random = new Random();
        StringBuilder randomStr = new StringBuilder();
        for (int i = 0; i < mailCodeLength; i++) {
            randomStr.append(randomCode.charAt(random.nextInt(62)));
        }
        return randomStr.toString();
    }
}
