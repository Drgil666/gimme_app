package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 16:39
 */
@Data
@Entity(nameInDb = "todo_user",
        indexes = @Index(value = "todo_id,user_id", unique = true))
public class ToDoUser {
    /**
     * 待办id
     */
    @Property(nameInDb = "todo_id")
    private Integer toDoId;
    /**
     * 用户id
     */
    @Property(nameInDb = "user_id")
    private Integer userId;
    /**
     * 待办状态
     */
    @Property(nameInDb = "status")
    private Integer status;
}
