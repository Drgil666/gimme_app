package com.project.gimme.pojo.vo;

import com.project.gimme.pojo.ChatMsg;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/14 13:50
 */
@Data
public class ChatMsgVO extends ChatMsg {
    /**
     * 是否是自己
     */
    private Boolean isSelf;
}
