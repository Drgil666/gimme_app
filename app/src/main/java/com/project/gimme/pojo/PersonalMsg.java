package com.project.gimme.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 15:47
 */

@Data
@Entity(tableName = "personal_msg")
public class PersonalMsg {
    /**
     * 信息通知id
     */
    @PrimaryKey()
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    private Integer id;
    /**
     * 消息通知类型
     */
    @ColumnInfo(name = "type", typeAffinity = ColumnInfo.INTEGER)
    private Integer type;
    /**
     * 消息创建者id
     */
    @ColumnInfo(name = "owner_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer ownerId;
    /**
     * 被操作者id
     */
    @ColumnInfo(name = "operator_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer operatorId;
    /**
     * 群聊/个人/频道id
     */
    @ColumnInfo(name = "object_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer objectId;
}
