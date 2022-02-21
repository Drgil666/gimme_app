package com.project.gimme.pojo.vo;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/11 14:10
 */
@Data
public class MessageVO {
    /**
     * 用户/群聊/频道id
     */
    private Integer objectId;
    /**
     * 用户/群聊/频道昵称
     */
    private String nick;
    /**
     * 用户/群聊/频道头像的mongoId
     */
    private String avatar;
    /**
     * 最后一句聊天记录
     */
    private String text;
    /**
     * 时间戳
     */
    private Date timestamp;

    /**
     * 聊天类型
     */
    private String type;
    /**
     * 新消息条数
     */
    private Integer newMessageCount;
}
