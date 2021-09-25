package com.franklions.example.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * 分页请求对象
 * @author flsh
 * @version 1.0
 * @date 2019-06-30
 * @since Jdk 1.8
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageParamRequest {
    private Integer start;
    private Integer count;
    private String sort;
    private Boolean desc;
    private String  queryStr;

    public PageParamRequest(Integer start, Integer count, String sort, Boolean desc, String queryStr) {
        this.start = start;
        this.count = count;
        this.sort = sort;
        this.desc = desc;
        this.queryStr = queryStr;
    }
}
