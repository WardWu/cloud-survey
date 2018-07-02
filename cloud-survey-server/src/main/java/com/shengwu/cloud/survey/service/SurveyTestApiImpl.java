package com.shengwu.cloud.survey.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.shengwu.cloud.survey.api.SurveyTestApi;
import com.shengwu.cloud.survey.bll.SurveyTestBll;
import com.shengwu.cloud.survey.model.SurveyTestModel;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 接口实现。
 * Created by shengwu on 2017/9/14.
 */
@Service
public class SurveyTestApiImpl implements SurveyTestApi {

    @Autowired
    private SurveyTestBll surveyTestBll;

    public SurveyTestModel getUser() {
        return surveyTestBll.getUserList().get(0);
    }
}
