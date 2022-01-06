package com.project.gimme.pojo;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/3 10:47
 */
@Data
@Entity(nameInDb = "group_user",
        indexes = @Index(value = "group_id,user_id", unique = true))
public class GroupUser {
    /**
     * 群聊id
     */
    @Property(nameInDb = "group_id")
    private Integer groupId;
    /**
     * 用户id
     */
    @Property(nameInDb = "user_id")
    private Integer userId;
    /**
     * 用户权限类型
     */
    @Property(nameInDb = "type")
    private Integer type;
    /**
     * 群聊昵称
     */
    @Property(nameInDb = "group_nick")
    private String groupNick;
}
