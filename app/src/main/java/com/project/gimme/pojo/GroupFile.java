package com.project.gimme.pojo;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 16:56
 */
@Data
@Entity(nameInDb = "group_file")
public class GroupFile {
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
}
