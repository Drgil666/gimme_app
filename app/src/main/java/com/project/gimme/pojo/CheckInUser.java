package com.project.gimme.pojo;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 16:02
 */
@Data
public class CheckInUser {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 签到id
     */
    private Integer checkInId;
    /**
     * 签到时间
     */
    private Date timestamp;
}
