package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * @author DrGilbert
 * @date 2022/1/4 9:34
 */
@Entity(nameInDb = "group_notice")
public class GroupNotice {
    /**
     * 群公告id
     */
    @Id
    @Property(nameInDb = "id")
    private Integer id;
    /**
     * 发布者id
     */
    @Property(nameInDb = "owner_id")
    private Integer ownerId;
    /**
     * 公告所在群聊id
     */
    @Property(nameInDb = "group_id")
    private Integer groupId;
    /**
     * 公告内容
     */
    @Property(nameInDb = "text")
    private String text;

    @Generated(hash = 406469270)
    public GroupNotice(Integer id, Integer ownerId, Integer groupId, String text) {
        this.id = id;
        this.ownerId = ownerId;
        this.groupId = groupId;
        this.text = text;
    }

    @Generated(hash = 1431508448)
    public GroupNotice() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOwnerId() {
        return this.ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getGroupId() {
        return this.groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
