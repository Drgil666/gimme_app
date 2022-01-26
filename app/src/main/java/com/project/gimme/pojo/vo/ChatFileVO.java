package com.project.gimme.pojo.vo;

import com.project.gimme.pojo.ChatFile;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author DrGilbert
 * @date 2022/1/26 13:06
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChatFileVO extends ChatFile {
    /**
     * 上传者昵称
     */
    private String ownerNick;
}
