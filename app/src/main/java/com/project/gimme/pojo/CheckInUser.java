package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 16:02
 */
@Data
@Entity(nameInDb = "checkin_user",
        indexes = @Index(value = "user_id,checkin_id", unique = true))
public class CheckInUser {
    /**
     * 用户id
     */
    @Property(nameInDb = "user_id")
    private Integer userId;
    /**
     * 签到id
     */
    @Property(nameInDb = "checkin_id")
    private Integer checkInId;
    /**
     * 签到时间
     */
    @Property(nameInDb = "timestamp")
    private Date timestamp;
}
