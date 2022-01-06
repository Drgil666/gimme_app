package com.project.gimme.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/3 10:28
 */
@Data
@Entity(tableName = "group")
public class Group {
    /**
     * 用户id
     */
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    private Integer id;
    /**
     * 创建时间
     */
    @ColumnInfo(name = "create_time")
    private Date createTime;
    /**
     * 群聊昵称
     */
    @ColumnInfo(name = "nick", typeAffinity = ColumnInfo.TEXT)
    private String nick;
}
