package com.shengwu.cloud.survey.api;

import com.shengwu.cloud.survey.model.SurveyTestModel;

/**
 * Api Test。
 * Created by shengwu on 2017/9/14.
 */
public interface SurveyTestApi {

    /**
     * 获取用户信息。
     *
     * @return SurveyTestModel
     */
    SurveyTestModel getUser();
}
