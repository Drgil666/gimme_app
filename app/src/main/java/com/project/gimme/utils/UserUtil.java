package com.project.gimme.utils;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author DrGilbert
 * @date 2021/3/23 20:29
 */
public class UserUtil {
    public static final String ROOT_ATTRIBUTE = "root";
    public static final String GROUP_OWNER_ATTRIBUTE = "group_owner";
    public static final String GROUP_ADMIN_ATTRIBUTE = "group_admin";
    public static final String CHANNEL_OWNER_ATTRIBUTE = "channel_owner";
    public static final String USER_ATTRIBUTE = "user";

    @AllArgsConstructor
    @Getter
    public enum Character {
        /**
         * 超级用户
         */
        TYPE_ROOT(0, ROOT_ATTRIBUTE),
        /**
         * 群聊群主
         */
        TYPE_GROUP_OWNER(1, GROUP_OWNER_ATTRIBUTE),
        /**
         * 群聊管理员
         */
        TYPE_GROUP_ADMIN(2, GROUP_ADMIN_ATTRIBUTE),
        /**
         * 频道广播用户
         */
        TYPE_CHANNEL_OWNER(3, CHANNEL_OWNER_ATTRIBUTE),
        /**
         * 普通用户
         */
        TYPE_USER(4, USER_ATTRIBUTE);
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
