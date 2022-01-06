package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 16:14
 */
@Data
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
}
