package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

/**
 * @author DrGilbert
 * @date 2022/1/4 16:14
 */
@Entity(nameInDb = "todo_list")
public class ToDoList {
    /**
     * 待办id
     */
    @Id
    @Property(nameInDb = "id")
    private Integer id;
    /**
     * 发起人id
     */
    @Property(nameInDb = "owner_id")
    private Integer ownerId;
    /**
     * 待办内容
     */
    @Property(nameInDb = "text")
    private String text;
    /**
     * 开始时间
     */
    @Property(nameInDb = "start_time")
    private Date startTime;
    /**
     * 结束时间
     */
    @Property(nameInDb = "end_time")
    private Date endTime;

    @Generated(hash = 1512001051)
    public ToDoList(Integer id, Integer ownerId, String text, Date startTime,
                    Date endTime) {
        this.id = id;
        this.ownerId = ownerId;
        this.text = text;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Generated(hash = 707050199)
    public ToDoList() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
