package com.project.gimme.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/11 14:15
 */
@Data
@Entity(nameInDb = "friend")
public class Friend {
    /**
     * 用户id
     */
    @Property(nameInDb = "user_id")
    private Integer userId;
    /**
     * 好友id
     */
    @Property(nameInDb = "friend_id")
    private Integer friendId;
    /**
     * 好友名备注
     */
    @Property(nameInDb = "friend_note")
    private String friendNote;
    /**
     * 最后获取信息的时间
     */
    @Property(nameInDb = "msg_timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date msgTimestamp;

    @Generated(hash = 1937212868)
    public Friend(Integer userId, Integer friendId, String friendNote,
                  Date msgTimestamp) {
        this.userId = userId;
        this.friendId = friendId;
        this.friendNote = friendNote;
        this.msgTimestamp = msgTimestamp;
    }

    @Generated(hash = 287143722)
    public Friend() {
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFriendId() {
        return this.friendId;
    }

    public void setFriendId(Integer friendId) {
        this.friendId = friendId;
    }

    public String getFriendNote() {
        return this.friendNote;
    }

    public void setFriendNote(String friendNote) {
        this.friendNote = friendNote;
    }

    public Date getMsgTimestamp() {
        return this.msgTimestamp;
    }

    public void setMsgTimestamp(Date msgTimestamp) {
        this.msgTimestamp = msgTimestamp;
    }
}
