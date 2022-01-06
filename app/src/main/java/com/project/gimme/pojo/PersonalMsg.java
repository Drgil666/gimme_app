package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 15:47
 */

@Data
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
}
