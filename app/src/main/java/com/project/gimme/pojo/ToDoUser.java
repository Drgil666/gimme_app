package com.project.gimme.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 16:39
 */
@Data
@Entity(tableName = "todo_user")
public class ToDoUser {
    /**
     * 待办id
     */
    @PrimaryKey()
    @ColumnInfo(name = "todo_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer toDoId;
    /**
     * 用户id
     */
    @ColumnInfo(name = "user_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer userId;
    /**
     * 待办状态
     */
    @ColumnInfo(name = "status", typeAffinity = ColumnInfo.INTEGER)
    private Integer status;
}
