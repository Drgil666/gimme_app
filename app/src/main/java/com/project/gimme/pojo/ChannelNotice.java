package com.project.gimme.pojo;

import lombok.Data;


/**
 * @author DrGilbert
 */
@Data
public class ChannelNotice {
    /**
     * 频道公告id
     */
    private Integer id;
    /**
     * 频道公告类型
     */
    private Integer type;
    /**
     * 频道id
     */
    private Integer channelId;
    /**
     * 频道公告内容
     */
    private String text;
}
