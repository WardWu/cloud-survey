package com.shengwu.cloud.survey.model;

import java.io.Serializable;

/**
 * 分页model。
 * Author： shengwu
 * DATE ：  2018/1/15
 */
public class PageModel implements Serializable {

    // 分页：查询数量
    private Integer limit = 10;

    // 分页：查询页数
    private Integer skip = 0;

    // 排序字段，多个逗号隔开
    private String order;

    // 排序顺序，默认升序
    private boolean desc = false;

    public Integer getLimit() {
        if (this.limit == null || this.limit < 1) {
            limit = 10;
        }
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getSkip() {
        if (this.skip == null || this.skip < 0) {
            skip = 0;
        }
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isDesc() {
        return desc;
    }

    public void setDesc(boolean desc) {
        this.desc = desc;
    }
}
