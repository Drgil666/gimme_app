package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 15:09
 */
@Data
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
}
