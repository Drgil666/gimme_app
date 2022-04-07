package com.project.gimme.pojo.vo;

import com.project.gimme.pojo.PersonalMsg;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author DrGilbert
 * @date 2022/1/14 16:19
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PersonalMsgVO extends PersonalMsg {
    /**
     * 操作群聊/频道名
     */
    private String objectNick;
    /**
     * 被操作用户名
     */
    private String operatorNick;
    /**
     * 操作者用户名
     */
    private String ownerNick;
    /**
     * 操作者头像
     */
    private String avatar;
}
