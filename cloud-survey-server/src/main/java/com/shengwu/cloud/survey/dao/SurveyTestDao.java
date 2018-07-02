package com.shengwu.cloud.survey.dao;

import java.util.List;
import java.util.Map;

/**
 * SurveyTestDao
 * Created by shengwu on 2017/9/14.
 */


public interface SurveyTestDao {

    /**
     * 获取用户列表。
     *
     * @return 用户列表
     */
    List<Map<String, Object>> getUserList();
}
