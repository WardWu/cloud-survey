package com.shengwu.cloud.survey.api;

/**
 * 调查问题接口。
 * Author： shengwu
 * DATE ：  2017/10/26
 */
public interface SurveyQuestionApi {

    /**
     * 创建调查表。
     */
    String createSurvey();

    /**
     * 修改调查表。
     */
    String updateSurvey();


    /**
     * 添加调查问题。
     */
    String addQuestion();
}
