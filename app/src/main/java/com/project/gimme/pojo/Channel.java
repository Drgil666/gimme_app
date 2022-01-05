package com.project.gimme.pojo;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 10:02
 */
@Data
public class Channel {
    /**
     * 频道id
     */
    private Integer id;
    /**
     * 创建者id
     */
    private Integer ownerId;
    /**
     * 频道名
     */
    private String nick;
    /**
     * 创建时间
     */
    private Date createTime;
}
