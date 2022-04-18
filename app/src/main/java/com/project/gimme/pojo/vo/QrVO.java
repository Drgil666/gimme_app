package com.project.gimme.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import lombok.Data;

/**
 * @author Gilbert
 * @date 2022/3/29 16:12
 */
@Data
public class QrVO {
    /**
     * 会话id
     */
    private Integer objectId;
    /**
     * 聊天类型
     */
    private String chatType;
    /**
     * 时间戳
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timestamp;
    /**
     * 分享人
     */
    private Integer shareUserId;
}
