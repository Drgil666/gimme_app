package com.project.gimme.pojo.vo;

import lombok.Data;

/**
 * @author Gilbert
 * @date 2022/3/23 12:30
 */
@Data
public class SearchVO {
    /**
     * 会话头像
     */
    private String avatar;
    /**
     * 会话名
     */
    private String objectNick;
    /**
     * 会话成员个数
     */
    private Integer memberCount;
    /**
     * 会话id
     */
    private Integer objectId;
    /**
     * 会话类型
     */
    private String objectType;
}
