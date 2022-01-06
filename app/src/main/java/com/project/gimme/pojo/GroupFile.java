package com.project.gimme.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 16:56
 */
@Data
@Entity(tableName = "group_file")
public class GroupFile {
    /**
     * 群文件id
     */
    @PrimaryKey()
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    private Integer id;
    /**
     * 上传者id
     */
    @ColumnInfo(name = "owner_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer ownerId;
    /**
     * 群聊id
     */
    @ColumnInfo(name = "group_id", typeAffinity = ColumnInfo.INTEGER)
    private Integer groupId;
    /**
     * 文件对应的mongoId
     */
    @ColumnInfo(name = "mongo_id", typeAffinity = ColumnInfo.TEXT)
    private String mongoId;
    /**
     * 文件名
     */
    @ColumnInfo(name = "filename", typeAffinity = ColumnInfo.TEXT)
    private String filename;
}
