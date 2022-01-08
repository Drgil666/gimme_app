package com.project.gimme.utils;

import android.os.Environment;
import android.text.TextUtils;

import com.project.gimme.pojo.vo.CacheItem;

import java.io.File;
import java.io.Serializable;

/**
 * @author DrGilbert
 * @date 2022/1/8 16:58
 */
public class CacheManager<T extends Serializable> {

    private static final String DIR = Environment.getExternalStorageDirectory().getPath();
    private static final int SPACE_MIN_CACHE = 1024 * 1024 * 20;
    private static CacheManager manager;

    private CacheManager() {
    }

    public synchronized static CacheManager getInstance() {
        if (manager == null) {
            manager = new CacheManager();
        }
        return manager;
    }

    public void clearCache() {
        File file;
        File[] files;
        if (BaseUtil.checkSdMounted()) {
            file = new File(DIR);
            files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    f.delete();
                }
            }
        }
    }

    public synchronized void putCacheFile(String key, T t, long time) {
        if (!TextUtils.isEmpty(key)) {
            String mdKey = BaseUtil.getMd5(key);
            if (!TextUtils.isEmpty(mdKey)) {
                final CacheItem<T> cacheItem = new CacheItem<T>(mdKey, t, time);
                saveCacheFile(mdKey, cacheItem);
            }
        }
    }

    private boolean saveCacheFile(String mdKey, CacheItem cacheItem) {
        if (BaseUtil.getSDSize() > SPACE_MIN_CACHE) {
            saveCacheObject(cacheItem);
            return true;
        }
        return false;
    }

    private void saveCacheObject(CacheItem cacheItem) {
        if (cacheItem != null) {
            BaseUtil.saveObject(cacheItem, DIR + cacheItem.getKey());
        }
    }

    public synchronized T getCacheFile(String key) {
        if (!TextUtils.isEmpty(key)) {
            String mdKey = BaseUtil.getMd5(key);
            if (!TextUtils.isEmpty(mdKey)) {
                String path = DIR + mdKey;
                if (!TextUtils.isEmpty(path)) {
                    CacheItem<T> cacheItem = BaseUtil.getObject(path);
                    if (cacheItem != null) {
                        if (System.currentTimeMillis() < cacheItem.getTime()) {
                            return cacheItem.getData();
                        }
                    }
                }
            }
        }
        return null;
    }

    public void initCache() {
        if (BaseUtil.checkSdMounted()) {
            if (BaseUtil.getSDSize() < SPACE_MIN_CACHE) {
                clearCache();
            } else {
                File file = new File(DIR);
                if (!file.exists()) {
                    file.mkdirs();
                }
            }
        }
    }
}

