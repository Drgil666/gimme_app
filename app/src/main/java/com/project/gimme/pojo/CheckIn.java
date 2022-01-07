package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * @author DrGilbert
 * @date 2022/1/4 15:09
 */
@Entity(nameInDb = "checkin")
public class CheckIn {
    /**
     * 签到id
     */
    @Id
    @Property(nameInDb = "id")
    private Integer id;
    /**
     * 群聊id
     */
    @Property(nameInDb = "group_id")
    private Integer groupId;
    /**
     * 签到MAC地址/GPS地址
     */
    @Property(nameInDb = "address")
    private String address;
    /**
     * 签到类型
     */
    @Property(nameInDb = "type")
    private Integer type;

    @Generated(hash = 1969079022)
    public CheckIn(Integer id, Integer groupId, String address, Integer type) {
        this.id = id;
        this.groupId = groupId;
        this.address = address;
        this.type = type;
    }

    @Generated(hash = 1821846413)
    public CheckIn() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
