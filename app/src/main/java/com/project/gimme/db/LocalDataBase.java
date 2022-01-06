package com.project.gimme.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.project.gimme.pojo.User;

/**
 * @author DrGilbert
 * @date 2022/1/5 16:51
 */
@Database(entities = {User.class}, version = 13)
public abstract class LocalDataBase extends RoomDatabase {
    private static final String DATABASE_NAME = "gimme_local";
    private static LocalDataBase databaseInstance;

    /**
     * 创建单例模式
     *
     * @param context 被创建的单例
     * @return 单例对象
     */
    public static synchronized LocalDataBase getInstance(Context context) {
        if (databaseInstance == null) {
            databaseInstance = Room
                    .databaseBuilder(context.getApplicationContext(), LocalDataBase.class, DATABASE_NAME)
                    .build();
        }
        return databaseInstance;
    }

    /**
     * 用户类相关交互
     *
     * @return 对应的接口
     */
    public abstract LocalDao userDao();
}
