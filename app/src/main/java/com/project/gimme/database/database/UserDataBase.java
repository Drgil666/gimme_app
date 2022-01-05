package com.project.gimme.database.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.project.gimme.database.dao.UserDao;
import com.project.gimme.pojo.User;

/**
 * @author DrGilbert
 * @date 2022/1/5 16:51
 */
@Database(entities = {User.class}, version = 13)
public abstract class UserDataBase extends RoomDatabase {
    private static final String DATABASE_NAME = "gimme_local";
    private static UserDataBase databaseInstance;

    //单例模式
    public static synchronized UserDataBase getInstance(Context context) {
        if (databaseInstance == null) {
            databaseInstance = Room
                    .databaseBuilder(context.getApplicationContext(), UserDataBase.class, DATABASE_NAME)
                    .build();
        }
        return databaseInstance;
    }

    /**
     * 用户类相关交互
     *
     * @return 对应的接口
     */
    public abstract UserDao userDao();
}
