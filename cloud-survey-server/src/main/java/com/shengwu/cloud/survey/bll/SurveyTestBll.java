package com.shengwu.cloud.survey.bll;

import com.shengwu.cloud.survey.model.SurveyTestModel;

import java.util.List;

/**
 * SurveyTestBll。
 * Created by shengwu on 2017/9/14.
 */
public interface SurveyTestBll {

    /**
     * 获取用户列表。
     *
     * @return 用户列表
     */
    List<SurveyTestModel> getUserList();
}
