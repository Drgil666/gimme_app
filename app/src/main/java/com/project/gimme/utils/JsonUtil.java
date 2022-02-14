package com.project.gimme.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.project.gimme.pojo.vo.Response;

/**
 * @author DrGilbert
 * @date 2022/2/13 17:12
 */
public class JsonUtil {
    public static String objectToJsonString(Object object) {
        return JSON.toJSONString(object);
    }

    public static Object jsonStringToObject(String json, Class<?> cls) {
        return JSON.parseObject(json, cls);
    }

    public static <T> Response<T> getResponseBody(String json, Class<T> cls) {
        JSONObject jsonObject = JSON.parseObject(json);
        Response<T> response = Response.createSuc(null);
        response.setCode(Integer.parseInt(jsonObject.get("code").toString()));
        response.setMsg((String) jsonObject.get("msg"));
        String data = jsonObject.get("data").toString();
        if (cls.equals(String.class)) {
            response.setData((T) data);
        } else {
            response.setData(JSON.parseObject(data, cls));
        }
        return response;
    }
}
