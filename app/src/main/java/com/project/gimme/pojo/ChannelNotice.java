package com.project.gimme.pojo;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;


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
    private Integer type;
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

    @Generated(hash = 2103308173)
    public ChannelNotice(Integer id, Integer type, Integer channelId, String text) {
        this.id = id;
        this.type = type;
        this.channelId = channelId;
        this.text = text;
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

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
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
}
