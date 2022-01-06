package com.project.gimme.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 15:09
 */
@Data
@Entity(tableName = "checkin")
public class CheckIn {
    /**
     * 签到id
     */
    @PrimaryKey()
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    private Integer id;
    /**
     * 群聊id
     */
    @ColumnInfo(name = "group_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer groupId;
    /**
     * 签到MAC地址/GPS地址
     */
    @ColumnInfo(name = "address", typeAffinity = ColumnInfo.TEXT)
    private String address;
    /**
     * 签到类型
     */
    @ColumnInfo(name = "type", typeAffinity = ColumnInfo.INTEGER)
    private Integer type;
}
