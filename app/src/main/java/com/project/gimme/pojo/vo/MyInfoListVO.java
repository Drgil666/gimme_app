package com.project.gimme.pojo.vo;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/17 17:24
 */
@Data
public class MyInfoListVO {
    /**
     * 功能名称
     */
    private String nick;
    /**
     * 功能描述
     */
    private String description;
    /**
     * 功能类型
     */
    private String type;
}
