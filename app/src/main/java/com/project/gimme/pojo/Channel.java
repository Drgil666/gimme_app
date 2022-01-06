package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 10:02
 */
@Data
@Entity(nameInDb = "channel")
public class Channel {
    /**
     * 频道id
     */
    @Id
    @Property(nameInDb = "id")
    private Integer id;
    /**
     * 创建者id
     */
    @Property(nameInDb = "owner_id")
    private Integer ownerId;
    /**
     * 频道名
     */
    @Property(nameInDb = "nick")
    private String nick;
    /**
     * 创建时间
     */
    @Property(nameInDb = "create_time")
    private Date createTime;
}
