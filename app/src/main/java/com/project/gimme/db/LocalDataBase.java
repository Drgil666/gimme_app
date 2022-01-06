package com.project.gimme.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.project.gimme.pojo.Channel;
import com.project.gimme.pojo.ChannelNotice;
import com.project.gimme.pojo.ChannelUser;
import com.project.gimme.pojo.ChatMsg;
import com.project.gimme.pojo.CheckIn;
import com.project.gimme.pojo.CheckInUser;
import com.project.gimme.pojo.Group;
import com.project.gimme.pojo.GroupFile;
import com.project.gimme.pojo.GroupNotice;
import com.project.gimme.pojo.GroupUser;
import com.project.gimme.pojo.MsgBot;
import com.project.gimme.pojo.PersonalMsg;
import com.project.gimme.pojo.ToDoList;
import com.project.gimme.pojo.ToDoUser;
import com.project.gimme.pojo.User;

/**
 * @author DrGilbert
 * @date 2022/1/5 16:51
 */
@Database(entities = {User.class, Channel.class,
        ChannelNotice.class, ChannelUser.class, ChatMsg.class,
        CheckIn.class, CheckInUser.class, Group.class, GroupFile.class,
        GroupNotice.class, GroupUser.class, MsgBot.class, PersonalMsg.class,
        ToDoList.class, ToDoUser.class}, version = 13)//导入Pojo类与对应的表，否则对应表不存在！
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
