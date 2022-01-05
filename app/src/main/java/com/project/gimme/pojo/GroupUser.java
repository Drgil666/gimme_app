package com.project.gimme.pojo;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/3 10:47
 */
@Data
public class GroupUser {
    /**
     * 群聊id
     */
    private Integer groupId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户权限类型
     */
    private Integer type;
    /**
     * 群聊昵称
     */
    private String groupNick;
}
