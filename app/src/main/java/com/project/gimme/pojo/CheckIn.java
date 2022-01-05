package com.project.gimme.pojo;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 15:09
 */
@Data
public class CheckIn {
    /**
     * 签到id
     */
    private Integer id;
    /**
     * 群聊id
     */
    private Integer groupId;
    /**
     * 签到MAC地址/GPS地址
     */
    private String address;
    /**
     * 签到类型
     */
    private Integer type;
}
