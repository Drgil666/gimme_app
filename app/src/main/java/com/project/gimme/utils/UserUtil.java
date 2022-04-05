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
    public static final String IT_ATTRIBUTE = "计算机/互联网/通信";
    public static final String MAKER_ATTRIBUTE = "生产/工艺/制造";
    public static final String MEDICINE_ATTRIBUTE = "医疗/护理/制药";
    public static final String FINANCE_ATTRIBUTE = "金融/银行/投资/保险";
    public static final String COMMERCE_ATTRIBUTE = "商业/服务业/个体经营";
    public static final String CULTURE_ATTRIBUTE = "文化/广告/传媒";
    public static final String ART_ATTRIBUTE = "艺术/娱乐/表演";
    public static final String LAWYER_ATTRIBUTE = "律师/法务";
    public static final String EDUCATION_ATTRIBUTE = "教育/培训";
    public static final String GOVERNMENT_ATTRIBUTE = "公务员/行政/事业单位";
    public static final String MODEL_ATTRIBUTE = "模特";
    public static final String HOSTESS_ATTRIBUTE = "空姐";
    public static final String STUDENT_ATTRIBUTE = "学生";
    public static final String OTHER_OCCUPATION_ATTRIBUTE = "其他职业";

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
    public static final Occupation[] OCCUPATION_LIST = Occupation.values();
    public static final HashMap<String, Integer> OCCUPATION_MAP = getOccupationMap();

    public static HashMap<String, Integer> getOccupationMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (Occupation occupation : OCCUPATION_LIST) {
            hashMap.put(occupation.name, occupation.code);
        }
        return hashMap;
    }

    public static Integer getOccupationByName(String name) {
        if (OCCUPATION_MAP.containsKey(name)) {
            return OCCUPATION_MAP.get(name);
        }
        return null;
    }

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
         * 男性
         */
        TYPE_MALE(0, MALE_ATTRIBUTE),
        /**
         * 女性
         */
        TYPE_FEMALE(1, FEMALE_ATTRIBUTE),
        /**
         * 其他
         */
        TYPE_OTHER(2, OTHER_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }

    @AllArgsConstructor
    @Getter
    public enum Occupation {
        /**
         * IT
         */
        TYPE_IT(0, IT_ATTRIBUTE),
        /**
         * 制造
         */
        TYPE_MAKER(1, MAKER_ATTRIBUTE),
        /**
         * 医疗
         */
        TYPE_MEDICINE(2, MEDICINE_ATTRIBUTE),
        /**
         * 金融
         */
        TYPE_FINANCE(3, FINANCE_ATTRIBUTE),
        /**
         * 商业
         */
        TYPE_COMMERCE(4, COMMERCE_ATTRIBUTE),
        /**
         * 文化
         */
        TYPE_CULTURE(5, CULTURE_ATTRIBUTE),
        /**
         * 艺术
         */
        TYPE_ART(6, ART_ATTRIBUTE),
        /**
         * 法律
         */
        TYPE_LAWYER(7, LAWYER_ATTRIBUTE),
        /**
         * 教育
         */
        TYPE_EDUCATION(8, EDUCATION_ATTRIBUTE),
        /**
         * 行政
         */
        TYPE_GOVERNMENT(9, GOVERNMENT_ATTRIBUTE),
        /**
         * 模特
         */
        TYPE_MODEL(10, MODEL_ATTRIBUTE),
        /**
         * 空姐
         */
        TYPE_HOSTESS(11, HOSTESS_ATTRIBUTE),
        /**
         * 学生
         */
        TYPE_STUDENT(12, STUDENT_ATTRIBUTE),
        /**
         * 其他职业
         */
        TYPE_OTHER_OCCUPATION(13, OTHER_OCCUPATION_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }
}
