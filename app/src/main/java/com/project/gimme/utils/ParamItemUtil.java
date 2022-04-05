package com.project.gimme.utils;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Gilbert
 * @date 2022/4/1 12:16
 */
public class ParamItemUtil {
    private static final String TEXT_ATTRIBUTE = "text";
    private static final String DATE_ATTRIBUTE = "date";
    private static final String GENDER_ATTRIBUTE = "gender";
    private static final String OCCUPATION_ATTRIBUTE = "occupation";
    private static final String LOCAL_ATTRIBUTE = "local";

    @AllArgsConstructor
    @Getter
    public enum ParamType {
        /**
         * 文字
         */
        TYPE_TEXT(0, TEXT_ATTRIBUTE),
        /**
         * 日期
         */
        TYPE_DATE(1, DATE_ATTRIBUTE),
        /**
         * 性别
         */
        TYPE_GENDER(2, GENDER_ATTRIBUTE),
        /**
         * 职业
         */
        TYPE_OCCUPATION(3, OCCUPATION_ATTRIBUTE),
        /**
         *
         */
        TYPE_LOCAL(4, LOCAL_ATTRIBUTE);
        private final Integer code;
        private final String name;
    }

    public static final ParamType[] PARAM_TYPE_LIST = ParamType.values();
    public static final HashMap<String, Integer> PARAM_TYPE_MAP = getParamTypeMap();

    public static HashMap<String, Integer> getParamTypeMap() {
        HashMap<String, Integer> hashMap = new HashMap<>(10);
        for (ParamType paramType : PARAM_TYPE_LIST) {
            hashMap.put(paramType.name, paramType.code);
        }
        return hashMap;
    }

    public static Integer getMsgTypeByName(String name) {
        if (PARAM_TYPE_MAP.containsKey(name)) {
            return PARAM_TYPE_MAP.get(name);
        }
        return null;
    }
}
