package com.project.gimme.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/11 14:10
 */
@Data
public class MessageVO {
    /**
     * 好友/群聊/频道id
     */
    private Integer objectId;
    /**
     * 好友/群聊/频道昵称
     */
    private String nick;
    /**
     * 好友/群聊/频道头像的mongoId
     */
    private String avatar;
    /**
     * 最后一句聊天记录
     */
    private String text;
    /**
     * 时间戳
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timestamp;
    /**
     * 聊天类型
     */
    private Integer type;
}
