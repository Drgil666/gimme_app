package com.project.gimme.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.project.gimme.pojo.vo.Response;

import java.util.List;

/**
 * @author DrGilbert
 * @date 2022/2/13 17:12
 */
public class JsonUtil {
    public static String objectToJsonString(Object object) {
        return JSON.toJSONString(object);
    }

    public static <T> T jsonStringToObject(String json, Class<?> cls) {
        return (T) JSON.parseObject(json, cls);
    }

    public static <T> Response<T> getResponseObjectBody(String json, Class<T> cls) {
        JSONObject jsonObject = JSON.parseObject(json);
        Response<T> response = Response.createSuc(null);
        response.setCode(Integer.parseInt(jsonObject.get("code").toString()));
        response.setMsg((String) jsonObject.get("msg"));
        if (jsonObject.get("data") != null) {
            String data = jsonObject.get("data").toString();
            if (cls.equals(String.class)) {
                response.setData((T) data);
            } else {
                response.setData(JSON.parseObject(data, cls));
            }
        } else {
            response.setData(null);
        }
        return response;
    }

    public static <T> Response<List<T>> getResponseListBody(String json, Class<T> cls) {
        JSONObject jsonObject = JSON.parseObject(json);
        Response<List<T>> response = Response.createSuc(null);
        response.setCode(Integer.parseInt(jsonObject.get("code").toString()));
        response.setMsg((String) jsonObject.get("msg"));
        if (jsonObject.get("data") != null) {
            String data = jsonObject.get("data").toString();
            List<T> array = JSONArray.parseArray(data, cls);
            response.setData(array);
        } else {
            response.setData(null);
        }
        return response;
    }
}
