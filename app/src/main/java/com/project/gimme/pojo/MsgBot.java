package com.project.gimme.pojo;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 15:26
 */
@Data
public class MsgBot {
    /**
     * 机器人id
     */
    private Integer id;
    /**
     * 机器人广播消息
     */
    private String text;
    /**
     * 机器人广播时间
     */
    private Date date;
    /**
     * 群聊id
     */
    private Integer groupId;
    /**
     * 执行时间频率
     */
    private String cron;
}
