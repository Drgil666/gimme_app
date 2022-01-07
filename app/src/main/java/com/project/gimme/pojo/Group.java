package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

/**
 * @author DrGilbert
 * @date 2022/1/3 10:28
 */
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

    @Generated(hash = 1156945570)
    public Group(Integer id, Date createTime, String nick) {
        this.id = id;
        this.createTime = createTime;
        this.nick = nick;
    }

    @Generated(hash = 117982048)
    public Group() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
}
