package com.project.gimme.utils;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Gilbert
 * @date 2022/4/4 13:44
 */
public class MyInfoUtil {
    private static final String FILE_NAME_ATTRIBUTE = "文件";
    private static final String TODO_NAME_ATTRIBUTE = "待办";
    private static final String FILE_DESCRIPTION_ATTRIBUTE = "群文件、聊天文件等";
    private static final String TODO_DESCRIPTION_ATTRIBUTE = "待办事项";

    @AllArgsConstructor
    @Getter
    public enum MyInfoType {
        /**
         * 文字消息
         */
        TYPE_FILE(0, FILE_NAME_ATTRIBUTE, FILE_DESCRIPTION_ATTRIBUTE),
        /**
         * 图片消息
         */
        TYPE_TODO(1, TODO_NAME_ATTRIBUTE, TODO_DESCRIPTION_ATTRIBUTE);
        private final Integer code;
        private final String name;
        private final String description;
    }

    public static final MyInfoType[] MY_INFO_TYPE_LIST = MyInfoType.values();
    public static final HashMap<String, Integer> MY_INFO_TYPE_MAP = getMyInfoTypeMap();

    public static HashMap<String, Integer> getMyInfoTypeMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (MyInfoType myInfoType : MY_INFO_TYPE_LIST) {
            hashMap.put(myInfoType.name, myInfoType.code);
        }
        return hashMap;
    }

    public static Integer getMyInfoTypeByName(String name) {
        if (MY_INFO_TYPE_MAP.containsKey(name)) {
            return MY_INFO_TYPE_MAP.get(name);
        }
        return null;
    }
}
