package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;

/**
 * @author DrGilbert
 * @date 2022/2/3 16:11
 */
@Entity(nameInDb = "personal_msg_user")
public class PersonalMsgUser {
    /**
     * 信息通知id
     */
    @Property(nameInDb = "personal_msg_id")
    private Integer personalMsgId;
    /**
     * 接受者id
     */
    @Property(nameInDb = "accept_id")
    private Integer acceptId;

    @Generated(hash = 1709686697)
    public PersonalMsgUser(Integer personalMsgId, Integer acceptId) {
        this.personalMsgId = personalMsgId;
        this.acceptId = acceptId;
    }

    @Generated(hash = 1469461893)
    public PersonalMsgUser() {
    }

    public Integer getPersonalMsgId() {
        return this.personalMsgId;
    }

    public void setPersonalMsgId(Integer personalMsgId) {
        this.personalMsgId = personalMsgId;
    }

    public Integer getAcceptId() {
        return this.acceptId;
    }

    public void setAcceptId(Integer acceptId) {
        this.acceptId = acceptId;
    }
}
