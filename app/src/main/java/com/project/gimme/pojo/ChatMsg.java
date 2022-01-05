package com.project.gimme.pojo;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 11:48
 */
@Data
public class ChatMsg {
    /**
     * 聊天信息id
     */
    private Integer id;
    /**
     * 聊天内容
     */
    private String text;
    /**
     * 聊天消息类型
     */
    private Integer type;
    /**
     * 接受者id/群聊id/频道公告id
     */
    private Integer objectId;
    /**
     * 时间戳
     */
    private Date timeStamp;
    /**
     * 消息发起者
     */
    private Integer ownerId;
}
