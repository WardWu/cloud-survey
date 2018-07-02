package com.shengwu.cloud.survey.bll.impl;


import com.alibaba.fastjson.JSON;
import com.shengwu.cloud.survey.bll.SurveyTestBll;
import com.shengwu.cloud.survey.dao.SurveyTestDao;
import com.shengwu.cloud.survey.model.SurveyTestModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SurveyTestBllImpl。
 * Created by shengwu on 2017/9/14.
 */
@Repository
public class SurveyTestBllImpl implements SurveyTestBll {

    @Autowired
    SurveyTestDao surveyTestDao;

    @Override
    public List<SurveyTestModel> getUserList() {
        List<SurveyTestModel> models =
                JSON.parseArray(JSON.toJSONString(surveyTestDao.getUserList()), SurveyTestModel.class);
        return models;
    }
}
