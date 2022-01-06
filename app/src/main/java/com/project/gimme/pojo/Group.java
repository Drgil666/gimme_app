package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/3 10:28
 */
@Data
@Entity(nameInDb = "group")
public class Group {
    /**
     * 用户id
     */
    @Id
    @Property(nameInDb = "id")
    private Integer id;
    /**
     * 创建时间
     */
    @Property(nameInDb = "create_time")
    private Date createTime;
    /**
     * 群聊昵称
     */
    @Property(nameInDb = "nick")
    private String nick;
}
