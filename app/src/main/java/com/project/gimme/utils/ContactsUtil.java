package com.project.gimme.utils;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author DrGilbert
 * @date 2022/1/4 11:53
 */
public class ContactsUtil {

    private static final String MESSAGE_ATTRIBUTE = "message";
    private static final String CONTACTS_ATTRIBUTE = "contacts";
    private static final String CREATE_CONTACT_ATTRIBUTE = "create_contact";
    private static final String TRANSMIT_ATTRIBUTE = "transmit";
    private static final String IMAGE_ATTRIBUTE = "image";
    private static final String CHAT_MSG_ATTRIBUTE = "chat_msg";
    private static final String INVITATION_ATTRIBUTE = "invitation";

    @AllArgsConstructor
    @Getter
    public enum SearchType {
        /**
         * 消息类型
         */
        TYPE_MESSAGE(0, MESSAGE_ATTRIBUTE),
        /**
         * 联系人类型
         */
        TYPE_CONTACTS(1, CONTACTS_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }

    @AllArgsConstructor
    @Getter
    public enum ContactType {
        /**
         * 创建类型
         */
        TYPE_CREATE_CONTACT(0, CREATE_CONTACT_ATTRIBUTE),
        /**
         * 转发类型
         */
        TYPE_TRANSMIT(1, TRANSMIT_ATTRIBUTE),
        /**
         * 邀请类型
         */
        TYPE_INVITATION(2, INVITATION_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }

    @AllArgsConstructor
    @Getter
    public enum TransmitType {
        /**
         * 转发图片
         */
        TYPE_IMAGE(0, IMAGE_ATTRIBUTE),
        /**
         * 转发聊天记录
         */
        TYPE_CHAT_MSG(1, CHAT_MSG_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }

    public static final SearchType[] SEARCH_TYPE_LIST = SearchType.values();
    public static final HashMap<String, Integer> SEARCH_TYPE_MAP = getSearchTypeMap();
    public static final ContactType[] CONTACT_TYPE_LIST = ContactType.values();
    public static final HashMap<String, Integer> CONTACT_TYPE_MAP = getContactTypeMap();
    public static final TransmitType[] TRANSMIT_TYPE_LIST = TransmitType.values();
    public static final HashMap<String, Integer> TRANSMIT_TYPE_MAP = getTransmitTypeMap();

    public static HashMap<String, Integer> getSearchTypeMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (SearchType searchType : SEARCH_TYPE_LIST) {
            hashMap.put(searchType.name, searchType.code);
        }
        return hashMap;
    }

    public static HashMap<String, Integer> getContactTypeMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (ContactType contactType : CONTACT_TYPE_LIST) {
            hashMap.put(contactType.name, contactType.code);
        }
        return hashMap;
    }

    public static HashMap<String, Integer> getTransmitTypeMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (TransmitType transmitType : TRANSMIT_TYPE_LIST) {
            hashMap.put(transmitType.name, transmitType.code);
        }
        return hashMap;
    }

    public static Integer getSearchTypeByName(String name) {
        if (SEARCH_TYPE_MAP.containsKey(name)) {
            return SEARCH_TYPE_MAP.get(name);
        }
        return null;
    }

    public static Integer getContactTypeByName(String name) {
        if (CONTACT_TYPE_MAP.containsKey(name)) {
            return CONTACT_TYPE_MAP.get(name);
        }
        return null;
    }

    public static Integer getTransmitTypeByName(String name) {
        if (TRANSMIT_TYPE_MAP.containsKey(name)) {
            return TRANSMIT_TYPE_MAP.get(name);
        }
        return null;
    }
}
