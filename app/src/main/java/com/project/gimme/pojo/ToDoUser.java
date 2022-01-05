package com.project.gimme.pojo;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 16:39
 */
@Data
public class ToDoUser {
    /**
     * 待办id
     */
    private Integer toDoId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 待办状态
     */
    private Integer status;
}
