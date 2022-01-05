package com.project.gimme.pojo;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 15:47
 */

@Data
public class PersonalMsg {
    /**
     * 信息通知id
     */
    private Integer id;
    /**
     * 消息通知类型
     */
    private Integer type;
    /**
     * 消息创建者id
     */
    private Integer ownerId;
    /**
     * 被操作者id
     */
    private Integer operatorId;
    /**
     * 群聊/个人/频道id
     */
    private Integer objectId;
}
