package com.project.gimme.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Gilbert
 * @date 2020/9/24 16:33
 */
@Data
@AllArgsConstructor
public class ResponseData<T> {
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

    public static <T> ResponseData<T> createSuc(T o) {
        return new ResponseData<T>(0, null, o);
    }

    public static <T> ResponseData<T> createErr(String msg) {
        return new ResponseData<T>(-1, msg, null);
    }

    public static <T> ResponseData<T> createErr(int code, String msg) {
        return new ResponseData<T>(code, msg, null);
    }

    public static <T> ResponseData<T> createTokenAuthorizedErr() {
        return new ResponseData<>(5, "Token失效或不存在!", null);
    }

    public boolean isSuccess() {
        return code.equals(0);
    }
}
