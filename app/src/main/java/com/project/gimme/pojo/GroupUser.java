package com.project.gimme.pojo;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;

/**
 * @author DrGilbert
 * @date 2022/1/3 10:47
 */
@Entity(nameInDb = "group_user",
        indexes = {@Index(value = "groupId,userId", unique = true)})
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

    @Generated(hash = 283657958)
    public GroupUser(Integer groupId, Integer userId, Integer type,
                     String groupNick) {
        this.groupId = groupId;
        this.userId = userId;
        this.type = type;
        this.groupNick = groupNick;
    }

    @Generated(hash = 1548903865)
    public GroupUser() {
    }

    public Integer getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getGroupNick() {
        return this.groupNick;
    }

    public void setGroupNick(String groupNick) {
        this.groupNick = groupNick;
    }
}
