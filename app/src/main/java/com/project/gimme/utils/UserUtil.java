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
    public static final String GROUP_USER_ATTRIBUTE = "group_user";
    public static final String GROUP_OWNER_ATTRIBUTE = "group_owner";
    public static final String GROUP_ADMIN_ATTRIBUTE = "group_admin";
    public static final String CHANNEL_OWNER_ATTRIBUTE = "channel_owner";
    public static final String CHANNEL_USER_ATTRIBUTE = "channel_user";
    public static final String USER_ATTRIBUTE = "user";
    public static final String ADMIN_ATTRIBUTE = "admin";
    public static final String OWNER_ATTRIBUTE = "owner";
    public static final String MALE_ATTRIBUTE = "男";
    public static final String FEMALE_ATTRIBUTE = "女";
    public static final String OTHER_ATTRIBUTE = "其他";

    public static HashMap<String, Integer> getGenderMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (Gender gender : GENDER_LIST) {
            hashMap.put(gender.name, gender.code);
        }
        return hashMap;
    }

    @AllArgsConstructor
    @Getter
    public enum GroupCharacter {
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
         * 群聊普通成员
         */
        TYPE_GROUP_USER(3, GROUP_USER_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }

    @AllArgsConstructor
    @Getter
    public enum ChannelCharacter {
        /**
         * 超级用户
         */
        TYPE_ROOT(0, ROOT_ATTRIBUTE),
        /**
         * 频道广播用户
         */
        TYPE_CHANNEL_OWNER(4, CHANNEL_OWNER_ATTRIBUTE),
        /**
         * 频道普通用户
         */
        TYPE_CHANNEL_USER(5, CHANNEL_USER_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }

    public static Integer getGenderByName(String name) {
        if (GENDER_MAP.containsKey(name)) {
            return GENDER_MAP.get(name);
        }
        return null;
    }

    public static final UserCharacter[] USER_CHARACTER_LIST = UserCharacter.values();
    public static final HashMap<String, Integer> USER_CHARACTER_MAP = getUserCharacterMap();
    public static final GroupCharacter[] GROUP_CHARACTER_LIST = GroupCharacter.values();
    public static final HashMap<String, Integer> GROUP_CHARACTER_MAP = getGroupCharacterMap();
    public static final ChannelCharacter[] CHANNEL_CHARACTER_LIST = ChannelCharacter.values();
    public static final HashMap<String, Integer> CHANNEL_CHARACTER_MAP = getChannelCharacterMap();
    public static final Gender[] GENDER_LIST = Gender.values();
    public static final HashMap<String, Integer> GENDER_MAP = getGenderMap();

    public static HashMap<String, Integer> getUserCharacterMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (UserCharacter character : USER_CHARACTER_LIST) {
            hashMap.put(character.name, character.code);
        }
        return hashMap;
    }

    public static Integer getUserCharacterByName(String name) {
        if (USER_CHARACTER_MAP.containsKey(name)) {
            return USER_CHARACTER_MAP.get(name);
        }
        return null;
    }

    public static HashMap<String, Integer> getGroupCharacterMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (GroupCharacter character : GROUP_CHARACTER_LIST) {
            hashMap.put(character.name, character.code);
        }
        return hashMap;
    }

    public static Integer getGroupCharacterByName(String name) {
        if (GROUP_CHARACTER_MAP.containsKey(name)) {
            return GROUP_CHARACTER_MAP.get(name);
        }
        return null;
    }

    public static HashMap<String, Integer> getChannelCharacterMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (ChannelCharacter character : CHANNEL_CHARACTER_LIST) {
            hashMap.put(character.name, character.code);
        }
        return hashMap;
    }

    public static Integer getChannelCharacterByName(String name) {
        if (CHANNEL_CHARACTER_MAP.containsKey(name)) {
            return CHANNEL_CHARACTER_MAP.get(name);
        }
        return null;
    }

    @AllArgsConstructor
    @Getter
    public enum UserCharacter {
        /**
         * 超级用户
         */
        TYPE_ROOT(0, ROOT_ATTRIBUTE),
        /**
         * 创建者用户
         */
        TYPE_OWNER(1, OWNER_ATTRIBUTE),
        /**
         * 管理员用户
         */
        TYPE_ADMIN(2, ADMIN_ATTRIBUTE),
        /**
         * 普通用户
         */
        TYPE_USER(3, USER_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }

    @AllArgsConstructor
    @Getter
    public enum Gender {
        /**
         * 超级用户
         */
        TYPE_MALE(0, MALE_ATTRIBUTE),
        /**
         * 频道广播用户
         */
        TYPE_FEMALE(1, FEMALE_ATTRIBUTE),
        /**
         * 频道普通用户
         */
        TYPE_OTHER(2, OTHER_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }
}
