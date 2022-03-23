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

    public static final SearchType[] SEARCH_TYPE_LIST = SearchType.values();
    public static final HashMap<String, Integer> SEARCH_TYPE_MAP = getSearchTypeMap();

    public static HashMap<String, Integer> getSearchTypeMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (SearchType searchType : SEARCH_TYPE_LIST) {
            hashMap.put(searchType.name, searchType.code);
        }
        return hashMap;
    }

    public static Integer getSearchTypeByName(String name) {
        if (SEARCH_TYPE_MAP.containsKey(name)) {
            return SEARCH_TYPE_MAP.get(name);
        }
        return null;
    }

}
