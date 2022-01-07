package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

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
    private Integer type;
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

    @Generated(hash = 1093439052)
    public PersonalMsg(Integer id, Integer type, Integer ownerId,
                       Integer operatorId, Integer objectId) {
        this.id = id;
        this.type = type;
        this.ownerId = ownerId;
        this.operatorId = operatorId;
        this.objectId = objectId;
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

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
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
}
