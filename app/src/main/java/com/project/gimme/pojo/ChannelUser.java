package com.project.gimme.pojo;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

/**
 * @author DrGilbert
 * @date 2022/1/4 11:21
 */
@Entity(nameInDb = "channel_user",
        indexes = {@Index(value = "channelId,userId", unique = true)})
public class ChannelUser {
    /**
     * 频道id
     */
    @Property(nameInDb = "channel_id")
    private Integer channelId;
    /**
     * 用户id
     */
    @Property(nameInDb = "user_id")
    private Integer userId;
    /**
     * 频道昵称
     */
    @Property(nameInDb = "channel_nick")
    private String channelNick;

    @Generated(hash = 778503692)
    public ChannelUser(Integer channelId, Integer userId, String channelNick) {
        this.channelId = channelId;
        this.userId = userId;
        this.channelNick = channelNick;
    }

    @Generated(hash = 765616289)
    public ChannelUser() {
    }

    public Integer getChannelId() {
        return this.channelId;
    }

    public void setChannelId(Integer channelId) {
        this.channelId = channelId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getChannelNick() {
        return this.channelNick;
    }

    public void setChannelNick(String channelNick) {
        this.channelNick = channelNick;
    }
}
