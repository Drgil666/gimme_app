package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * @author DrGilbert
 * @date 2022/1/4 16:56
 */
@Entity(nameInDb = "group_file")
public class ChatFile {
    /**
     * 群文件id
     */
    @Id
    @Property(nameInDb = "id")
    private Integer id;
    /**
     * 上传者id
     */
    @Property(nameInDb = "owner_id")
    private Integer ownerId;
    /**
     * 群聊id
     */
    @Property(nameInDb = "group_id")
    private Integer groupId;
    /**
     * 文件对应的mongoId
     */
    @Property(nameInDb = "mongo_id")
    private String mongoId;
    /**
     * 文件名
     */
    @Property(nameInDb = "filename")
    private String filename;

    @Generated(hash = 723486968)
    public ChatFile(Integer id, Integer ownerId, Integer groupId, String mongoId,
                    String filename) {
        this.id = id;
        this.ownerId = ownerId;
        this.groupId = groupId;
        this.mongoId = mongoId;
        this.filename = filename;
    }

    @Generated(hash = 1330691015)
    public ChatFile() {
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
    public String getMongoId() {
        return this.mongoId;
    }
    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }
    public String getFilename() {
        return this.filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
}
