package com.project.gimme.pojo.vo;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/20 14:52
 */
@Data
public class UserVoParamItem {
    private String paramName;
    private String paramValue;
    private Boolean isArrow;
    private Integer paramType;

    public UserVoParamItem(String paramName, String paramValue, boolean isArrow, Integer type) {
        this.paramName = paramName;
        this.paramValue = paramValue;
        this.isArrow = isArrow;
        this.paramType = type;
    }
}