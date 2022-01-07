package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * @author DrGilbert
 * @date 2022/1/4 15:26
 */
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

    @Generated(hash = 2003299456)
    public MsgBot(Integer id, String text, Integer groupId, String cron) {
        this.id = id;
        this.text = text;
        this.groupId = groupId;
        this.cron = cron;
    }

    @Generated(hash = 733085996)
    public MsgBot() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getCron() {
        return this.cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }
}
