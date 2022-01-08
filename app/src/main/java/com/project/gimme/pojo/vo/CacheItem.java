package com.project.gimme.pojo.vo;

import java.io.Serializable;

/**
 * @author DrGilbert
 * @date 2022/1/8 17:00
 */
public class CacheItem<T extends Serializable> implements Serializable {
    private String key;
    private T data;
    private long time;

    public CacheItem(String key, T data, long time) {
        this.key = key;
        this.data = data;
        this.time = time;
    }

    public String getKey() {
        return key;
    }

    public T getData() {
        return data;
    }

    public long getTime() {
        return time;
    }
}
