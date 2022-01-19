package com.project.gimme.utils;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author DrGilbert
 * @date 2022/1/19 14:31
 */
public class SessionUtil {
    private static final String MESSAGE_ATTRIBUTE = "消息";
    private static final String FRIEND_ATTRIBUTE = "联系人";
    private static final String MY_INFO_ATTRIBUTE = "我的";

    @AllArgsConstructor
    @Getter
    public enum Character {
        /**
         * 消息
         */
        TYPE_MESSAGE(0, MESSAGE_ATTRIBUTE),
        /**
         * 联系人
         */
        TYPE_FRIEND(1, FRIEND_ATTRIBUTE),
        /**
         * 我的
         */
        TYPE_MY_INFO(2, MY_INFO_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }

    public static final Character[] CHARACTER_LIST = Character.values();
    public static final HashMap<String, Integer> CHARACTER_MAP = getCharacterMap();

    public static HashMap<String, Integer> getCharacterMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (Character character : CHARACTER_LIST) {
            hashMap.put(character.name, character.code);
        }
        return hashMap;
    }

    public static Integer getCharacterByName(String name) {
        if (CHARACTER_MAP.containsKey(name)) {
            return CHARACTER_MAP.get(name);
        }
        return null;
    }
}
