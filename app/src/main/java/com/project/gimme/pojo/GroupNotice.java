package com.project.gimme.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 9:34
 */
@Data
@Entity(tableName = "group_notice")
public class GroupNotice {
    /**
     * 群公告id
     */
    @PrimaryKey()
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    private Integer id;
    /**
     * 发布者id
     */
    @ColumnInfo(name = "owner_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer ownerId;
    /**
     * 公告所在群聊id
     */
    @ColumnInfo(name = "group_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer groupId;
    /**
     * 公告内容
     */
    @ColumnInfo(name = "text", typeAffinity = ColumnInfo.TEXT)
    private String text;
}
