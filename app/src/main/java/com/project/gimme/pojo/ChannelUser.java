package com.project.gimme.pojo;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 11:21
 */
@Data
public class ChannelUser {
    /**
     * 频道id
     */
    private Integer channelId;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 频道昵称
     */
    private String channelNick;
}
