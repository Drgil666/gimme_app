package com.project.gimme.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 群聊昵称
     */
    @Property(nameInDb = "nick")
    private String nick;
    /**
     * 群描述
     */
    @Property(nameInDb = "description")
    private String description;

    @Generated(hash = 1637966632)
    public Group(Integer id, Date createTime, String nick, String description) {
        this.id = id;
        this.createTime = createTime;
        this.nick = nick;
        this.description = description;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
