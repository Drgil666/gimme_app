package com.project.gimme.pojo;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 9:34
 */
@Data
public class GroupNotice {
    /**
     * 群公告id
     */
    private Integer id;
    /**
     * 发布者id
     */
    private Integer ownerId;
    /**
     * 公告所在群聊id
     */
    private Integer groupId;
    /**
     * 公告内容
     */
    private String text;
}
