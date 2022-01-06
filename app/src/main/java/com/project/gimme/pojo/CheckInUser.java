package com.project.gimme.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 16:02
 */
@Data
@Entity(tableName = "checkin_user")
public class CheckInUser {
    /**
     * 用户id
     */
    @ColumnInfo(name = "user_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer userId;
    /**
     * 签到id
     */
    @ColumnInfo(name = "checkin_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer checkInId;
    /**
     * 签到时间
     */
    @ColumnInfo(name = "timestamp")
    private Date timestamp;
}
