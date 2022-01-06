package com.project.gimme.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 10:02
 */
@Data
@Entity(tableName = "channel")
public class Channel {
    /**
     * 频道id
     */
    @PrimaryKey()
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    private Integer id;
    /**
     * 创建者id
     */
    @ColumnInfo(name = "owner_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer ownerId;
    /**
     * 频道名
     */
    @ColumnInfo(name = "nick", typeAffinity = ColumnInfo.TEXT)
    private String nick;
    /**
     * 创建时间
     */
    @ColumnInfo(name = "create_time")
    private Date createTime;
}
