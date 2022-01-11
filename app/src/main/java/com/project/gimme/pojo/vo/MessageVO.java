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
     * 用户id
     */
    private Integer id;
    /**
     * 用户昵称
     */
    private String nick;
    /**
     * 用户头像的mongoId
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
}
