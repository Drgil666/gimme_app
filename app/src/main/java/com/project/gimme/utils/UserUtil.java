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
    public static final String MALE_ATTRIBUTE = "男";
    public static final String FEMALE_ATTRIBUTE = "女";
    public static final String OTHER_GENDER_ATTRIBUTE = "其他";

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

    @AllArgsConstructor
    @Getter
    public enum Gender {
        /**
         * 男性
         */
        TYPE_MALE(0, MALE_ATTRIBUTE),
        /**
         * 女性
         */
        TYPE_FEMALE(1, FEMALE_ATTRIBUTE),
        /**
         * 其他性别
         */
        TYPE_OTHER(2, OTHER_GENDER_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }

    public static final Character[] CHARACTER_LIST = Character.values();
    public static final HashMap<String, Integer> CHARACTER_MAP = getCharacterMap();
    public static final Gender[] GENDER_LIST = Gender.values();
    public static final HashMap<String, Integer> GENDER_MAP = getGenderMap();

    public static HashMap<String, Integer> getCharacterMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (Character character : CHARACTER_LIST) {
            hashMap.put(character.name, character.code);
        }
        return hashMap;
    }

    public static HashMap<String, Integer> getGenderMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (Gender gen : GENDER_LIST) {
            hashMap.put(gen.name, gen.code);
        }
        return hashMap;
    }

    public static Integer getCharacterByName(String name) {
        if (CHARACTER_MAP.containsKey(name)) {
            return CHARACTER_MAP.get(name);
        }
        return null;
    }

    public static Integer getGenderByName(String name) {
        if (GENDER_MAP.containsKey(name)) {
            return GENDER_MAP.get(name);
        }
        return null;
    }

}
