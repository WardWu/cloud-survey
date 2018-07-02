package com.shengwu.cloud.survey.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.shengwu.cloud.survey.api.SurveyUserApi;
import com.shengwu.cloud.survey.bll.SurveyUserBll;
import com.shengwu.cloud.survey.model.ConcernListModel;
import com.shengwu.cloud.survey.model.UserBaseModel;
import com.shengwu.cloud.survey.model.UserConcernModel;
import com.shengwu.cloud.survey.model.UserInfoListModel;
import com.shengwu.cloud.survey.model.UserInfoModel;
import com.shengwu.cloud.survey.model.UserUpdateModel;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * 用户操作接口实现。
 * Author   shengwu。
 * Date:    2017/10/3 0003
 */
@Service
public class SurveyUserApiImpl implements SurveyUserApi {

    @Autowired
    private SurveyUserBll surveyUserBll;

    /**
     * 创建账号。
     */
    @Override
    public String createUser(UserBaseModel userBaseModel) {
        return surveyUserBll.createUser(userBaseModel);
    }

    /**
     * 用户账号登陆/注销。
     */
    @Override
    public UserBaseModel getUser(String userAccount, String password, String updateTime) {
        return surveyUserBll.getUser(userAccount, password, updateTime);
    }

    /**
     * 更新账号信息。
     */
    @Override
    public Boolean updateUser(UserBaseModel userBaseModel) {
        return surveyUserBll.updateUser(userBaseModel);
    }

    /**
     * 删除账号。
     */
    @Override
    public Boolean deleteUser(String userId) {
        return surveyUserBll.deleteUser(userId);
    }

    /**
     * 修改账号密码。
     */
    @Override
    public Boolean UpdateUserPassword(UserUpdateModel userUpdateModel) {
        return surveyUserBll.UpdateUserPassword(userUpdateModel);
    }

    /**
     * 添加关注。
     */
    @Override
    public Boolean addConcern(String userId, String userConcernIds, boolean isConcern) {
        return surveyUserBll.addConcern(userId, userConcernIds, isConcern);
    }

    /**
     * 获取关注列表。
     */
    @Override
    public ConcernListModel getConcernList(String userId) {
        return surveyUserBll.getConcernList(userId);
    }

    /**
     * 获取用户资料。
     */
    @Override
    public UserInfoModel getUserInfo(String userId) {
        return surveyUserBll.getUserInfo(userId).getUserInfoModels().get(0);
    }

    /**
     * 批量获取用户账号。
     */
    @Override
    public UserInfoListModel getUserInfoList(List<String> userList) {

        String userId = StringUtils.join(userList, ",");
        return surveyUserBll.getUserInfo(userId);
    }
}
