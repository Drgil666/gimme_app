package com.project.gimme.pojo.vo;

import lombok.Data;

/**
 * @author Gilbert
 * @date 2022/3/14 10:27
 */
@Data
public class RefreshVO {
    /**
     * 会话id
     */
    private Integer objectId;
    /**
     * 聊天类型
     */
    private String chatType;
    /**
     * 聊天记录id
     */
    private Integer chatMsgId;
}
