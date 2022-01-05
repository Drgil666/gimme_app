package com.project.gimme.pojo;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/4 16:56
 */
@Data
public class GroupFile {
    /**
     * 群文件id
     */
    private Integer id;
    /**
     * 上传者id
     */
    private Integer ownerId;
    /**
     * 群聊id
     */
    private Integer groupId;
    /**
     * 文件对应的mongoId
     */
    private String mongoId;
    /**
     * 文件名
     */
    private String filename;
}
