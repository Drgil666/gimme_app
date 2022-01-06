package com.project.gimme.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 16:14
 */
@Data
@Entity(tableName = "todo_list")
public class ToDoList {
    /**
     * 待办id
     */
    @PrimaryKey()
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    private Integer id;
    /**
     * 发起人id
     */
    @ColumnInfo(name = "owner_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer ownerId;
    /**
     * 待办内容
     */
    @ColumnInfo(name = "text", typeAffinity = ColumnInfo.TEXT)
    private String text;
    /**
     * 开始时间
     */
    @ColumnInfo(name = "start_time")
    private Date startTime;
    /**
     * 结束时间
     */
    @ColumnInfo(name = "end_time")
    private Date endTime;
}
