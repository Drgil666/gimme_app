package com.project.gimme.utils;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Gilbert
 * @date 2022/4/18 12:00
 */
public class NotificationUtil {
    private static final String MESSAGE_ATTRIBUTE = "message";

    @AllArgsConstructor
    @Getter
    public enum NoticeType {
        /**
         * 好友类型
         */
        TYPE_MESSAGE(0, MESSAGE_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }


    public static final NoticeType[] CHARACTER_LIST = NoticeType.values();
    public static final HashMap<String, Integer> CHARACTER_MAP = getNoticeMap();

    public static HashMap<String, Integer> getNoticeMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (NoticeType noticeType : CHARACTER_LIST) {
            hashMap.put(noticeType.name, noticeType.code);
        }
        return hashMap;
    }

    public static Integer getNoticeByName(String name) {
        if (CHARACTER_MAP.containsKey(name)) {
            return CHARACTER_MAP.get(name);
        }
        return null;
    }
}
