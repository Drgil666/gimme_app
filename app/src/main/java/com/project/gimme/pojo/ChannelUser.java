package com.project.gimme.pojo;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 11:21
 */
@Data
@Entity(nameInDb = "channel_user",
        indexes = @Index(value = "channel_id,user_id", unique = true))
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
}
