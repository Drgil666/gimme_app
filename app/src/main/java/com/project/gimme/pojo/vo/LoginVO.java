package com.project.gimme.pojo.vo;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/2/7 13:47
 */
@Data
public class LoginVO {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 密码
     */
    private String password;
}
