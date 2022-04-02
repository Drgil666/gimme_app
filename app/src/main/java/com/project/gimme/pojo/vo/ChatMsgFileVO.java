package com.project.gimme.pojo.vo;

import lombok.Data;

/**
 * @author Gilbert
 * @date 2022/4/2 11:37
 */
@Data
public class ChatMsgFileVO {
    /**
     * 文件id
     */
    private Integer chatFileId;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小
     */
    private Long fileSize;
}
