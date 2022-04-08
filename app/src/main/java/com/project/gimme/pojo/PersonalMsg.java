package com.project.gimme.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

/**
 * @author DrGilbert
 * @date 2022/1/4 15:47
 */
@Entity(nameInDb = "personal_msg")
public class PersonalMsg {
    /**
     * 信息通知id
     */
    @Id
    @Property(nameInDb = "id")
    private Integer id;
    /**
     * 消息通知类型
     */
    @Property(nameInDb = "type")
    private String objectType;
    /**
     * 消息创建者id
     */
    @Property(nameInDb = "owner_id")
    private Integer ownerId;
    /**
     * 被操作者id
     */
    @Property(nameInDb = "operator_id")
    private Integer operatorId;
    /**
     * 群聊/个人/频道id
     */
    @Property(nameInDb = "object_id")
    private Integer objectId;
    /**
     * 备注
     */
    @Property(nameInDb = "note")
    private String note;
    /**
     * 处理状态
     */
    @Property(nameInDb = "status")
    private Integer status;
    /**
     * 消息类型
     */
    @Property(nameInDb = "type")
    private String type;
    /**
     * 时间戳
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timestamp;

    @Generated(hash = 982954744)
    public PersonalMsg(Integer id, String objectType, Integer ownerId, Integer operatorId,
                       Integer objectId, String note, Integer status, String type, Date timestamp) {
        this.id = id;
        this.objectType = objectType;
        this.ownerId = ownerId;
        this.operatorId = operatorId;
        this.objectId = objectId;
        this.note = note;
        this.status = status;
        this.type = type;
        this.timestamp = timestamp;
    }

    @Generated(hash = 1820845707)
    public PersonalMsg() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getObjectType() {
        return this.objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Integer getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getOperatorId() {
        return this.operatorId;
    }

    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }

    public Integer getObjectId() {
        return this.objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
