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

    public UserVoParamItem(String name, String value, boolean isArrow) {
        this.paramName = name;
        this.paramValue = value;
        this.isArrow = isArrow;
    }
}