package com.project.gimme.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 15:26
 */
@Data
@Entity(tableName = "msg_bot")
public class MsgBot {
    /**
     * 机器人id
     */
    @PrimaryKey()
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    private Integer id;
    /**
     * 机器人广播消息
     */
    @ColumnInfo(name = "text", typeAffinity = ColumnInfo.TEXT)
    private String text;
    /**
     * 机器人广播时间
     */
    @ColumnInfo(name = "date")
    private Date date;
    /**
     * 群聊id
     */
    @ColumnInfo(name = "group_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer groupId;
    /**
     * 执行时间频率
     */
    @ColumnInfo(name = "cron", typeAffinity = ColumnInfo.TEXT)
    private String cron;
}
