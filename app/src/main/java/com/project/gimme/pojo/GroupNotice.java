package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 9:34
 */
@Data
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
}
