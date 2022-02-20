package com.project.gimme.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

/**
 * @author DrGilbert
 * @date 2022/1/4 11:48
 */
@Entity(nameInDb = "chat_msg")
public class ChatMsg {
    /**
     * 聊天信息id
     */
    @Id
    @Property(nameInDb = "id")
    private Integer id;
    /**
     * 聊天内容
     */
    @Property(nameInDb = "text")
    private String text;
    /**
     * 聊天消息类型
     */
    @Property(nameInDb = "type")
    private Integer type;
    /**
     * 接受者id/群聊id/频道公告id
     */
    @Property(nameInDb = "object_id")
    private Integer objectId;
    /**
     * 时间戳
     */
    @Property(nameInDb = "timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timeStamp;
    /**
     * 消息发起者
     */
    @Property(nameInDb = "owner_id")
    private Integer ownerId;

    @Generated(hash = 1952304784)
    public ChatMsg(Integer id, String text, Integer type, Integer objectId,
                   Date timeStamp, Integer ownerId) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.objectId = objectId;
        this.timeStamp = timeStamp;
        this.ownerId = ownerId;
    }

    @Generated(hash = 1355502543)
    public ChatMsg() {
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

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getObjectId() {
        return this.objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Date getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }
}
