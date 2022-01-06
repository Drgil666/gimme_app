package com.project.gimme.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 11:48
 */
@Data
@Entity(tableName = "chat_msg")
public class ChatMsg {
    /**
     * 聊天信息id
     */
    @PrimaryKey()
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    private Integer id;
    /**
     * 聊天内容
     */
    @ColumnInfo(name = "text", typeAffinity = ColumnInfo.TEXT)
    private String text;
    /**
     * 聊天消息类型
     */
    @ColumnInfo(name = "type", typeAffinity = ColumnInfo.INTEGER)
    private Integer type;
    /**
     * 接受者id/群聊id/频道公告id
     */
    @ColumnInfo(name = "object_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer objectId;
    /**
     * 时间戳
     */
    @ColumnInfo(name = "timestamp")
    private Date timeStamp;
    /**
     * 消息发起者
     */
    @ColumnInfo(name = "owner_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer ownerId;
}
