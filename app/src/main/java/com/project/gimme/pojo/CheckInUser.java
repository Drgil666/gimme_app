package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

/**
 * @author DrGilbert
 * @date 2022/1/4 16:02
 */
@Entity(nameInDb = "checkin_user",
        indexes = {@Index(value = "userId,checkInId", unique = true)})
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

    @Generated(hash = 721790147)
    public CheckInUser(Integer userId, Integer checkInId, Date timestamp) {
        this.userId = userId;
        this.checkInId = checkInId;
        this.timestamp = timestamp;
    }

    @Generated(hash = 1305521975)
    public CheckInUser() {
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCheckInId() {
        return this.checkInId;
    }

    public void setCheckInId(Integer checkInId) {
        this.checkInId = checkInId;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
