package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

/**
 * @author DrGilbert
 * @date 2022/1/4 16:14
 */
@Entity(nameInDb = "todo_user",
        indexes = {@Index(value = "toDoId,userId", unique = true)})
public class ToDoUser {
    /**
     * 待办id
     */
    @Property(nameInDb = "todo_id")
    private Integer toDoId;
    /**
     * 发起人id
     */
    @Property(nameInDb = "status")
    private Integer status;
    /**
     * 用户id
     */
    @Property(nameInDb = "user_id")
    private Integer userId;

    @Generated(hash = 168325577)
    public ToDoUser(Integer toDoId, Integer status, Integer userId) {
        this.toDoId = toDoId;
        this.status = status;
        this.userId = userId;
    }

    @Generated(hash = 2062771375)
    public ToDoUser() {
    }

    public Integer getToDoId() {
        return this.toDoId;
    }

    public void setToDoId(Integer toDoId) {
        this.toDoId = toDoId;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
