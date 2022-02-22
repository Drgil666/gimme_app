package com.project.gimme.utils;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author DrGilbert
 * @date 2022/1/4 11:53
 */
public class InfoTypeUtil {
    public static final Character[] CHARACTER_LIST = Character.values();
    public static final HashMap<String, Integer> CHARACTER_MAP = getCharacterMap();
    private static final String FRIEND_ATTRIBUTE = "friend";
    private static final String GROUP_ATTRIBUTE = "group";
    private static final String CHANNEL_ATTRIBUTE = "channel";
    private static final String SELF_ATTRIBUTE = "self";
    private static final String GROUP_MEMBER_ATTRIBUTE = "group_member";
    private static final String GROUP_SELF_ATTRIBUTE = "group_self";
    private static final String CHANNEL_MEMBER_ATTRIBUTE = "channel_member";
    private static final String CHANNEL_SELF_ATTRIBUTE = "channel_self";

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

    @AllArgsConstructor
    @Getter
    public enum Character {
        /**
         * 好友类型
         */
        TYPE_FRIEND(0, FRIEND_ATTRIBUTE),
        /**
         * 群聊类型
         */
        TYPE_GROUP(1, GROUP_ATTRIBUTE),
        /**
         * 频道类型
         */
        TYPE_CHANNEL(2, CHANNEL_ATTRIBUTE),
        /**
         * 群聊成员
         */
        TYPE_GROUP_MEMBER(3, GROUP_MEMBER_ATTRIBUTE),
        /**
         * 群聊成员自己
         */
        TYPE_GROUP_SELF(4, GROUP_SELF_ATTRIBUTE),
        /**
         * 频道成员
         */
        TYPE_CHANNEL_MEMBER(5, CHANNEL_MEMBER_ATTRIBUTE),
        /**
         * 频道自己
         */
        TYPE_CHANNEL_SELF(6, CHANNEL_SELF_ATTRIBUTE),
        /**
         * 自己
         */
        TYPE_SELF(7, SELF_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }
}
