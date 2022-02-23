package com.project.gimme.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.util.Date;

/**
 * @author DrGilbert
 * @date 2022/1/4 16:56
 */
@Entity(nameInDb = "chat_file")
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
     * 所在的好友/群聊/频道id
     */
    @Property(nameInDb = "object_id")
    private Integer objectId;
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
    /**
     * 好友/群聊/频道id
     */
    @Property(nameInDb = "type")
    private String type;
    /**
     * 文件大小(bit为单位)
     */
    @Property(nameInDb = "size")
    private Long size;
    /**
     * 文件上传日期
     */
    @Property(nameInDb = "timestamp")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date timestamp;

    @Generated(hash = 1822780623)
    public ChatFile(Integer id, Integer ownerId, Integer objectId, String mongoId,
                    String filename, String type, Long size, Date timestamp) {
        this.id = id;
        this.ownerId = ownerId;
        this.objectId = objectId;
        this.mongoId = mongoId;
        this.filename = filename;
        this.type = type;
        this.size = size;
        this.timestamp = timestamp;
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

    public Integer getObjectId() {
        return this.objectId;
    }

    public void setObjectId(Integer objectId) {
        this.objectId = objectId;
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

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSize() {
        return this.size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Date getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

}
