package com.project.gimme.pojo;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 16:14
 */
@Data
public class ToDoList {
    /**
     * 待办id
     */
    private Integer id;
    /**
     * 发起人id
     */
    private Integer ownerId;
    /**
     * 待办内容
     */
    private String text;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
}
