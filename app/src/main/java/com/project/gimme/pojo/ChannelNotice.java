package com.project.gimme.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;


/**
 * @author DrGilbert
 */
@Data
@Entity(tableName = "channel_notice")
public class ChannelNotice {
    /**
     * 频道公告id
     */
    @PrimaryKey()
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    private Integer id;
    /**
     * 频道公告类型
     */
    @ColumnInfo(name = "type", typeAffinity = ColumnInfo.INTEGER)
    private Integer type;
    /**
     * 频道id
     */
    @ColumnInfo(name = "channel_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer channelId;
    /**
     * 频道公告内容
     */
    @ColumnInfo(name = "text", typeAffinity = ColumnInfo.TEXT)
    private String text;
}
