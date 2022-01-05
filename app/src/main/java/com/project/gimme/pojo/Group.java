package com.project.gimme.pojo;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/3 10:28
 */
@Data
public class Group {
    /**
     * 用户id
     */
    private Integer id;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 群聊昵称
     */
    private String nick;
}
