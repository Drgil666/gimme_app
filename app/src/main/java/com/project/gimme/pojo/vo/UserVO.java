package com.project.gimme.pojo.vo;

import com.project.gimme.pojo.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author DrGilbert
 * @date 2022/1/19 15:44
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends User {
    /**
     * 好友备注
     */
    private String note;
    /**
     * 群/频道昵称
     */
    private String otherNick;
    /**
     * 群权限/频道权限
     */
    private String otherType;
}
