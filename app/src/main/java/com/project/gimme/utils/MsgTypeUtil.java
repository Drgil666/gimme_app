package com.project.gimme.utils;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Gilbert
 * @date 2022/4/1 12:16
 */
public class MsgTypeUtil {
    private static final String TEXT_ATTRIBUTE = "text";
    private static final String PIC_ATTRIBUTE = "pic";
    private static final String FILE_ATTRIBUTE = "file";

    @AllArgsConstructor
    @Getter
    public enum MsgType {
        /**
         * 文字消息
         */
        TYPE_TEXT(0, TEXT_ATTRIBUTE),
        /**
         * 图片消息
         */
        TYPE_PIC(1, PIC_ATTRIBUTE),
        /**
         * 文件消息
         */
        TYPE_FILE(2, FILE_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }

    public static final MsgType[] MSG_TYPE_LIST = MsgType.values();
    public static final HashMap<String, Integer> MSG_TYPE_MAP = getMsgTypeMap();

    public static HashMap<String, Integer> getMsgTypeMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (MsgType msgType : MSG_TYPE_LIST) {
            hashMap.put(msgType.name, msgType.code);
        }
        return hashMap;
    }

    public static Integer getMsgTypeByName(String name) {
        if (MSG_TYPE_MAP.containsKey(name)) {
            return MSG_TYPE_MAP.get(name);
        }
        return null;
    }
}
