package com.project.gimme.pojo;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;


/**
 * @author DrGilbert
 */
@Entity(nameInDb = "channel_notice",
        indexes = {@Index(value = "id DESC")})
public class ChannelNotice {
    /**
     * 频道公告id
     */
    @Id
    @Property(nameInDb = "id")
    private Integer id;
    /**
     * 频道公告类型
     */
    @Property(nameInDb = "type")
    private String type;
    /**
     * 频道id
     */
    @Property(nameInDb = "channel_id")
    private Integer channelId;
    /**
     * 频道公告内容
     */
    @Property(nameInDb = "text")
    private String text;
    /**
     * 频道公告创建时间
     */
    private Date createTime;

    @Generated(hash = 248381806)
    public ChannelNotice(Integer id, String type, Integer channelId, String text,
                         Date createTime) {
        this.id = id;
        this.type = type;
        this.channelId = channelId;
        this.text = text;
        this.createTime = createTime;
    }

    @Generated(hash = 2146001694)
    public ChannelNotice() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getChannelId() {
        return this.channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
