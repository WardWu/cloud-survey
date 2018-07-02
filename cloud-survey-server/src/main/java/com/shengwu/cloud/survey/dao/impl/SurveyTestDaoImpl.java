package com.shengwu.cloud.survey.dao.impl;

import com.shengwu.cloud.survey.dao.SurveyTestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * SurveyTestDaoImpl。
 * Created by shengwu on 2017/9/14.
 */

@Repository
public class SurveyTestDaoImpl implements SurveyTestDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Map<String, Object>> getUserList() {
        String sql = "SELECT edu_system_code AS  userId, edu_system_name AS userName FROM uc_edu_system";
        return jdbcTemplate.queryForList(sql);
    }
}
