package com.project.gimme.utils;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author DrGilbert
 * @date 2022/1/4 11:53
 */
public class ChatMsgUtil {
    private static final String FRIEND_ATTRIBUTE = "friend";
    private static final String GROUP_ATTRIBUTE = "group";
    private static final String CHANNEL_ATTRIBUTE = "channel";
    private static final String CHANNEL_NOTICE_ATTRIBUTE = "channel_notice";
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
         * 频道公告类型
         */
        TYPE_CHANNEL_NOTICE(3, CHANNEL_NOTICE_ATTRIBUTE);
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
