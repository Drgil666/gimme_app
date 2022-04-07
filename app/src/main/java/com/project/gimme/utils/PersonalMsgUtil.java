package com.project.gimme.utils;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author DrGilbert
 * @date 2022/1/14 16:04
 */
public class PersonalMsgUtil {
    private static final String INSERT_FRIEND_ATTRIBUTE = "insert_friend";
    private static final String DELETE_FRIEND_ATTRIBUTE = "delete_friend";
    private static final String INSERT_GROUP_ATTRIBUTE = "insert_group";
    private static final String UPDATE_GROUP_ATTRIBUTE = "update_group";
    private static final String DELETE_GROUP_ATTRIBUTE = "delete_group";
    private static final String INSERT_GROUP_MEMBER_ATTRIBUTE = "insert_group_member";
    private static final String UPDATE_GROUP_MEMBER_ATTRIBUTE = "update_group_member";
    private static final String DELETE_GROUP_MEMBER_ATTRIBUTE = "channel";
    private static final String INSERT_CHANNEL_ATTRIBUTE = "insert_channel";
    private static final String UPDATE_CHANNEL_ATTRIBUTE = "update_channel";
    private static final String DELETE_CHANNEL_ATTRIBUTE = "delete_channel";
    private static final String INSERT_CHANNEL_MEMBER_ATTRIBUTE = "insert_channel_member";
    private static final String UPDATE_CHANNEL_MEMBER_ATTRIBUTE = "update_channel_member";
    private static final String DELETE_CHANNEL_MEMBER_ATTRIBUTE = "delete_channel_member";
    private static final String ACCEPT_ATTRIBUTE = "已同意";
    private static final String REJECT_ATTRIBUTE = "已拒绝";
    private static final String TODO_ATTRIBUTE = "待处理";
    private static final String CHOOSE_ATTRIBUTE = "选择";
    private static final String NULL_ATTRIBUTE = "";
    private static final String FRIEND_PERSONAL_MSG_ATTRIBUTE = "friend_personal_msg";
    private static final String OTHER_PERSONAL_MSG_ATTRIBUTE = "other_personal_msg";

    @AllArgsConstructor
    @Getter
    public enum PersonalMsgType {
        /**
         * 好友信息
         */
        TYPE_FRIEND_PERSONAL_MSG(0, FRIEND_PERSONAL_MSG_ATTRIBUTE),
        /**
         * 其他信息
         */
        TYPE_OTHER_PERSONAL_MSG(1, OTHER_PERSONAL_MSG_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }

    @AllArgsConstructor
    @Getter
    public enum FriendPersonalMsg {
        /**
         * 添加好友请求
         */
        TYPE_INSERT_FRIEND(0, INSERT_FRIEND_ATTRIBUTE),
        /**
         * 删除好友通知
         */
        TYPE_DELETE_FRIEND(1, DELETE_FRIEND_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }

    @AllArgsConstructor
    @Getter
    public enum GroupPersonalMsg {
        /**
         * 创建群聊通知
         */
        TYPE_INSERT_GROUP(0, INSERT_GROUP_ATTRIBUTE),
        /**
         * 更新群聊通知
         */
        TYPE_UPDATE_GROUP(1, UPDATE_GROUP_ATTRIBUTE),
        /**
         * 删除群聊通知
         */
        TYPE_DELETE_GROUP(2, DELETE_GROUP_ATTRIBUTE),
        /**
         * 添加群成员请求
         */
        TYPE_INSERT_GROUP_MEMBER(3, INSERT_GROUP_MEMBER_ATTRIBUTE),
        /**
         * 更新群成员通知
         */
        TYPE_UPDATE_GROUP_MEMBER(4, UPDATE_GROUP_MEMBER_ATTRIBUTE),
        /**
         * 删除群成员通知
         */
        TYPE_DELETE_GROUP_MEMBER(5, DELETE_GROUP_MEMBER_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }

    @AllArgsConstructor
    @Getter
    public enum ChannelPersonalMsg {
        /**
         * 创建频道通知
         */
        TYPE_INSERT_CHANNEL(0, INSERT_CHANNEL_ATTRIBUTE),
        /**
         * 更新频道通知
         */
        TYPE_UPDATE_CHANNEL(1, UPDATE_CHANNEL_ATTRIBUTE),
        /**
         * 删除频道通知
         */
        TYPE_DELETE_CHANNEL(2, DELETE_CHANNEL_ATTRIBUTE),
        /**
         * 添加频道成员请求
         */
        TYPE_INSERT_CHANNEL_MEMBER(3, INSERT_CHANNEL_MEMBER_ATTRIBUTE),
        /**
         * 更新频道成员请求
         */
        TYPE_UPDATE_CHANNEL_MEMBER(4, UPDATE_CHANNEL_MEMBER_ATTRIBUTE),
        /**
         * 添加频道成员请求
         */
        TYPE_DELETE_CHANNEL_MEMBER(5, DELETE_CHANNEL_MEMBER_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }

    @AllArgsConstructor
    @Getter
    public enum Status {
        /**
         * 请求已同意
         */
        TYPE_ACCEPT(0, ACCEPT_ATTRIBUTE),
        /**
         * 请求已拒绝
         */
        TYPE_REJECT(1, REJECT_ATTRIBUTE),
        /**
         * 空信息
         */
        TYPE_NULL(2, NULL_ATTRIBUTE),
        /**
         * 待处理
         */
        TYPE_TODO(3, TODO_ATTRIBUTE),
        /**
         * 选择
         */
        TYPE_CHOOSE(4, CHOOSE_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }

    public static final FriendPersonalMsg[] FRIEND_PERSONAL_MSG_LIST = FriendPersonalMsg.values();
    public static final HashMap<String, Integer> FRIEND_PERSONAL_MSG_MAP = getFriendPersonalMsgMap();
    public static final GroupPersonalMsg[] GROUP_PERSONAL_MSG_LIST = GroupPersonalMsg.values();
    public static final HashMap<String, Integer> GROUP_PERSONAL_MSG_MAP = getGroupPersonalMsgMap();
    public static final ChannelPersonalMsg[] CHANNEL_PERSONAL_MSG_LIST = ChannelPersonalMsg.values();
    public static final HashMap<String, Integer> CHANNEL_PERSONAL_MSG_MAP = getChannelPersonalMsgMap();
    public static final Status[] STATUS_LIST = Status.values();
    public static final HashMap<String, Integer> STATUS_MAP = getStatusMap();
    public static final PersonalMsgType[] PERSONAL_MSG_TYPE_LIST = PersonalMsgType.values();
    public static final HashMap<String, Integer> PERSONAL_MSG_TYPE_MAP = getPersonalMsgTypeMap();

    public static HashMap<String, Integer> getStatusMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (Status status : STATUS_LIST) {
            hashMap.put(status.name, status.code);
        }
        return hashMap;
    }

    public static HashMap<String, Integer> getFriendPersonalMsgMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (FriendPersonalMsg friendPersonalMsg : FRIEND_PERSONAL_MSG_LIST) {
            hashMap.put(friendPersonalMsg.name, friendPersonalMsg.code);
        }
        return hashMap;
    }

    public static HashMap<String, Integer> getPersonalMsgTypeMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (PersonalMsgType personalMsgType : PERSONAL_MSG_TYPE_LIST) {
            hashMap.put(personalMsgType.name, personalMsgType.code);
        }
        return hashMap;
    }

    public static Integer getPersonalMsgTypeByName(String name) {
        if (PERSONAL_MSG_TYPE_MAP.containsKey(name)) {
            return PERSONAL_MSG_TYPE_MAP.get(name);
        }
        return null;
    }

    public static Integer getFriendPersonalMsgByName(String name) {
        if (FRIEND_PERSONAL_MSG_MAP.containsKey(name)) {
            return FRIEND_PERSONAL_MSG_MAP.get(name);
        }
        return null;
    }

    public static Integer getStatusByName(String name) {
        if (STATUS_MAP.containsKey(name)) {
            return STATUS_MAP.get(name);
        }
        return null;
    }

    public static HashMap<String, Integer> getGroupPersonalMsgMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (GroupPersonalMsg groupPersonalMsg : GROUP_PERSONAL_MSG_LIST) {
            hashMap.put(groupPersonalMsg.name, groupPersonalMsg.code);
        }
        return hashMap;
    }

    public static Integer getGroupPersonalMsgByName(String name) {
        if (GROUP_PERSONAL_MSG_MAP.containsKey(name)) {
            return GROUP_PERSONAL_MSG_MAP.get(name);
        }
        return null;
    }

    public static HashMap<String, Integer> getChannelPersonalMsgMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (ChannelPersonalMsg channelPersonalMsg : CHANNEL_PERSONAL_MSG_LIST) {
            hashMap.put(channelPersonalMsg.name, channelPersonalMsg.code);
        }
        return hashMap;
    }

    public static Integer getChannelPersonalMsgByName(String name) {
        if (CHANNEL_PERSONAL_MSG_MAP.containsKey(name)) {
            return CHANNEL_PERSONAL_MSG_MAP.get(name);
        }
        return null;
    }
}
