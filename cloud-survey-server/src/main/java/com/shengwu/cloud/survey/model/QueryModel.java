package com.shengwu.cloud.survey.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 查询model。
 * Author： shengwu
 * DATE ：  2018/1/15
 */
public class QueryModel implements Serializable {
    // id, 多个逗号隔开
    private String ids;
    // index
    private String indexName;
    // type
    private String typeName;
    // 查询条件,  与
    private Map<String, Object> mustQuery = new HashMap<>();
    // 查询条件， 或
    private Map<String, Object> boolQuery = new HashMap<>();
    // 查询条件， 非
    private Map<String, Object> mustNotQuery = new HashMap<>();

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Map<String, Object> getMustQuery() {
        return mustQuery;
    }

    public void setMustQuery(Map<String, Object> mustQuery) {
        this.mustQuery = mustQuery;
    }

    public Map<String, Object> getBoolQuery() {
        return boolQuery;
    }

    public void setBoolQuery(Map<String, Object> boolQuery) {
        this.boolQuery = boolQuery;
    }

    public Map<String, Object> getMustNotQuery() {
        return mustNotQuery;
    }

    public void setMustNotQuery(Map<String, Object> mustNotQuery) {
        this.mustNotQuery = mustNotQuery;
    }
}
