package com.project.gimme.utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
/**
 * @author DrGilbert
 * @date 2022/2/13 17:12
 */
public class JsonUtil
{
    public static String objectToJsonString(Object object)
    {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.toJson(object);
    }
    public static <T> T fromJson(String json, Class<T> type)
    {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.fromJson(json, type);
    }
    public static <T> T fromJson(String json, Type type)
    {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson.fromJson(json, type);
    }
}
