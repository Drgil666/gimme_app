package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

/**
 * @author DrGilbert
 * @date 2022/1/4 10:02
 */
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

    @Generated(hash = 463022955)
    public Channel(Integer id, Integer ownerId, String nick, Date createTime) {
        this.id = id;
        this.ownerId = ownerId;
        this.nick = nick;
        this.createTime = createTime;
    }

    @Generated(hash = 459652974)
    public Channel() {
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

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
