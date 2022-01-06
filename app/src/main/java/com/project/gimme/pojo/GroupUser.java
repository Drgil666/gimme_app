package com.project.gimme.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/3 10:47
 */
@Data
@Entity(tableName = "group_user")
public class GroupUser {
    /**
     * 群聊id
     */
    @PrimaryKey()
    @ColumnInfo(name = "group_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer groupId;
    /**
     * 用户id
     */
    @ColumnInfo(name = "user_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer userId;
    /**
     * 用户权限类型
     */
    @ColumnInfo(name = "type", typeAffinity = ColumnInfo.INTEGER)
    private Integer type;
    /**
     * 群聊昵称
     */
    @ColumnInfo(name = "group_nick", typeAffinity = ColumnInfo.TEXT)
    private String groupNick;
}
