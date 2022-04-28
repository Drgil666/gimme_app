package com.project.gimme.pojo;


import android.os.Build;
import android.util.Base64;

import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.gimme.GimmeApplication;
import com.project.gimme.utils.TEAUtil;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

/**
 * @author DrGilbert
 * @date 2022/1/4 11:48
 */
@Entity(nameInDb = "chat_msg")
public class ChatMsg {
    /**
     * 聊天信息id
     */
    @Id
    @Property(nameInDb = "id")
    private Integer id;
    /**
     * 聊天内容
     */
    @Property(nameInDb = "text")
    private String text;
    /**
     * 聊天消息类型
     */
    @Property(nameInDb = "type")
    private String type;
    /**
     * 接受者id/群聊id/频道公告id
     */
    @Property(nameInDb = "object_id")
    private Integer objectId;
    /**
     * 时间戳
     */
    @Property(nameInDb = "timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timeStamp;
    /**
     * 消息发起者
     */
    @Property(nameInDb = "owner_id")
    private Integer ownerId;
    /**
     * 消息类型
     */
    @Property(nameInDb = "msgType")
    private Integer msgType;

    @Generated(hash = 1878353699)
    public ChatMsg(Integer id, String text, String type, Integer objectId,
                   Date timeStamp, Integer ownerId, Integer msgType) {
        this.id = id;
        this.text = text;
        this.type = type;
        this.objectId = objectId;
        this.timeStamp = timeStamp;
        this.ownerId = ownerId;
        this.msgType = msgType;
    }

    @Generated(hash = 1355502543)
    public ChatMsg() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void encode() {
        if (GimmeApplication.IS_TEA) {
            byte[] bytes = TEAUtil.encryptByTea(text);
            text = Base64.encodeToString(bytes, Base64.NO_WRAP);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void decode() {
        if (GimmeApplication.IS_TEA) {
            byte[] bytes = Base64.decode(text, Base64.NO_WRAP);
            text = TEAUtil.decryptByTea(bytes);
        }
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getObjectId() {
        return this.objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public Date getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Integer getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getMsgType() {
        return this.msgType;
    }

    public void setMsgType(Integer msgType) {
        this.msgType = msgType;
    }

}
