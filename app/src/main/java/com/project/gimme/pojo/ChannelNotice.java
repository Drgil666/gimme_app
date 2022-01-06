package com.project.gimme.pojo;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;


/**
 * @author DrGilbert
 */
@Data
@Entity(nameInDb = "channel_notice", indexes = @Index(value = "id DESC"))
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
}
