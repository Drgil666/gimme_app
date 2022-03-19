package com.project.gimme.pojo.vo;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Gilbert
 * @version 1.0
 * @date 2020/7/19 12:50
 */
@Data
@NoArgsConstructor
public class CudRequestVO<T, K> {
    public static final String CREATE_METHOD = "create";
    public static final String UPDATE_METHOD = "update";
    public static final String DELETE_METHOD = "delete";
    private String method;
    private T data;
    private List<K> key;

    public void setMethod(String method) {
        if (method != null) {
            method = method.toLowerCase();
        }
        this.method = method;
    }
}
