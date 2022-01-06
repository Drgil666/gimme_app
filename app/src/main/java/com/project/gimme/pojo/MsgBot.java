package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 15:26
 */
@Data
@Entity(nameInDb = "msg_bot")
public class MsgBot {
    /**
     * 机器人id
     */
    @Id
    @Property(nameInDb = "id")
    private Integer id;
    /**
     * 机器人广播消息
     */
    @Property(nameInDb = "text")
    private String text;
    /**
     * 群聊id
     */
    @Property(nameInDb = "group_id")
    private Integer groupId;
    /**
     * 执行时间频率
     */
    @Property(nameInDb = "cron")
    private String cron;
}
