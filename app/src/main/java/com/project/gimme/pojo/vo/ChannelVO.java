package com.project.gimme.pojo.vo;

import com.project.gimme.pojo.Channel;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author DrGilbert
 * @date 2022/1/14 14:33
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChannelVO extends Channel {
    /**
     * 总群人数
     */
    private Integer totalCount;
    /**
     * 群昵称
     */
    private String myNote;
}
