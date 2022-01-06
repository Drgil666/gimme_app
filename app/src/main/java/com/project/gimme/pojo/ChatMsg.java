package com.project.gimme.pojo;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 11:48
 */
@Data
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
    private Date timeStamp;
    /**
     * 消息发起者
     */
    @Property(nameInDb = "owner_id")
    private Integer ownerId;
}
