package com.project.gimme.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Gilbert
 * @date 2020/9/24 16:33
 */
@Data
@AllArgsConstructor
public class Response<T> {
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;

    public static <T> Response<T> createSuc(T o) {
        return new Response<T>(0, null, o);
    }

    public static <T> Response<T> createErr(String msg) {
        return new Response<T>(-1, msg, null);
    }

    public static <T> Response<T> createErr(int code, String msg) {
        return new Response<T>(code, msg, null);
    }

    public static <T> Response<T> createTokenAuthorizedErr() {
        return new Response<>(5, "Token失效或不存在!", null);
    }

    public boolean isSuccess() {
        return code.equals(0);
    }
}
