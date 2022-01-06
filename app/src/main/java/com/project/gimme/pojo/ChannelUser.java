package com.project.gimme.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 11:21
 */
@Data
@Entity(tableName = "channel_user")
public class ChannelUser {
    /**
     * 频道id
     */
    @PrimaryKey()
    @ColumnInfo(name = "channel_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer channelId;
    /**
     * 用户id
     */
    @ColumnInfo(name = "user_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer userId;
    /**
     * 频道昵称
     */
    @ColumnInfo(name = "channel_nick", typeAffinity = ColumnInfo.INTEGER)
    private String channelNick;
}
