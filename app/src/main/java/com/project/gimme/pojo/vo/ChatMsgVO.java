package com.project.gimme.pojo.vo;

import com.project.gimme.pojo.ChatMsg;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author DrGilbert
 * @date 2022/1/14 13:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChatMsgVO extends ChatMsg {
    /**
     * 是否是自己
     */
    private Boolean isSelf;
    /**
     * 消息发送者昵称
     */
    private String ownerNick;
}
