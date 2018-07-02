package com.shengwu.cloud.survey.util;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.fastjson.JSON;
import com.shengwu.cloud.survey.api.SurveyTestApi;
import com.shengwu.cloud.survey.api.SurveyUserApi;
import com.shengwu.cloud.survey.common.BeanUtil;
import com.shengwu.cloud.survey.model.UserBaseModel;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Properties;

/**
 * client启动。
 * Created by shengwu on 2017/9/14.
 */
public class SurveyClientFactory {

    protected static String serverUrl;
    private static String appName;

    public static String getAppName() {
        return appName;
    }

    public static String getServerUrl() {
        return serverUrl;
    }

    public static void initHeader() {
        Properties properties = new Properties();
        String propFilePath = System.getProperty("superdiamond.localFilePath");

        try {
            if (propFilePath != null) {
                properties.load(SurveyClientFactory.class.getClassLoader().getResourceAsStream(propFilePath.substring(propFilePath.indexOf("/") + 1)));
                serverUrl = properties.getProperty("data.authorization.zookeeper.url");
                appName = properties.getProperty("data.authorization.appname");
            }

            RpcContext.getContext().setAttachment("appname", appName);
            RpcContext.getContext().setAttachment("clienttype", "Java");
            RpcContext.getContext().setAttachment("clientversion", "1.0.0-SNAPSHOT");
        } catch (IOException e) {
            System.err.println("读取data.properties文件时发生异常，无法初始化DataAuthDubboClientFactory");
        }
    }

    public static SurveyTestApi createApiUsageStatisticsService() {
        return BeanUtil.getBean(SurveyTestApi.class, "apiUsageStatisticsService");
    }

    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/spring/application-context.xml"});
        context.start();
        SurveyUserApi surveyUserApi = context.getBean("surveyUserApiService", SurveyUserApi.class);
        UserBaseModel model = getUserBaseModel();
        boolean isDelete = surveyUserApi.deleteUser("00000");
        System.out.println(isDelete);
//        String userId = surveyUserApi.createUser(model);
//        boolean isUpdate = surveyUserApi.updateUser(model);
        UserBaseModel model1 = surveyUserApi.getUser(model.getUserAccount(), model.getUserPassword(), null);
//        System.out.println(userId);
//        System.out.println(isUpdate);
        System.out.println(JSON.toJSONString(model1));
    }

    public static UserBaseModel getUserBaseModel() {
        UserBaseModel model = new UserBaseModel();
        model.setUserAccount("user2");
        model.setUserPassword("123456");
        model.setUserName("shengwu");
        model.setUserNickName("shengwu");
        model.setPhoneNum("18010801996");
        model.setMail("shengwu@iflytek.com");
        model.setSex("0");
        model.setDate("2017-10-03");
        model.setAge("18");
        model.setAddress("中国-安徽-合肥");
        model.setSign("个性签名");
        model.setIntroduction("个人说明");
        model.setIconUrl("www.baidu.com");
        model.setCreateTime("");
        model.setUpdateTime("");
        return model;
    }
}
