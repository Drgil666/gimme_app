package com.project.gimme.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 群介绍
     */
    @Property(nameInDb = "description")
    private String description;
    /**
     * 频道头像
     */
    @Property(nameInDb = "avatar")
    private String avatar;

    @Generated(hash = 678621732)
    public Channel(Integer id, Integer ownerId, String nick, Date createTime,
                   String description, String avatar) {
        this.id = id;
        this.ownerId = ownerId;
        this.nick = nick;
        this.createTime = createTime;
        this.description = description;
        this.avatar = avatar;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
