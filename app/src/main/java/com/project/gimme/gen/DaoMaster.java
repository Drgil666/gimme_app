package com.project.gimme.gen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.identityscope.IdentityScopeType;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * Master of DAO (schema version 2): knows all DAOs.
 */
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 2;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(Database db, boolean ifNotExists) {
        ChannelDao.createTable(db, ifNotExists);
        ChannelNoticeDao.createTable(db, ifNotExists);
        ChannelUserDao.createTable(db, ifNotExists);
        ChatFileDao.createTable(db, ifNotExists);
        ChatMsgDao.createTable(db, ifNotExists);
        CheckInDao.createTable(db, ifNotExists);
        CheckInUserDao.createTable(db, ifNotExists);
        FriendDao.createTable(db, ifNotExists);
        GroupDao.createTable(db, ifNotExists);
        GroupNoticeDao.createTable(db, ifNotExists);
        GroupUserDao.createTable(db, ifNotExists);
        MsgBotDao.createTable(db, ifNotExists);
        PersonalMsgDao.createTable(db, ifNotExists);
        PersonalMsgUserDao.createTable(db, ifNotExists);
        ToDoListDao.createTable(db, ifNotExists);
        ToDoUserDao.createTable(db, ifNotExists);
        UserDao.createTable(db, ifNotExists);
    }

    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(Database db, boolean ifExists) {
        ChannelDao.dropTable(db, ifExists);
        ChannelNoticeDao.dropTable(db, ifExists);
        ChannelUserDao.dropTable(db, ifExists);
        ChatFileDao.dropTable(db, ifExists);
        ChatMsgDao.dropTable(db, ifExists);
        CheckInDao.dropTable(db, ifExists);
        CheckInUserDao.dropTable(db, ifExists);
        FriendDao.dropTable(db, ifExists);
        GroupDao.dropTable(db, ifExists);
        GroupNoticeDao.dropTable(db, ifExists);
        GroupUserDao.dropTable(db, ifExists);
        MsgBotDao.dropTable(db, ifExists);
        PersonalMsgDao.dropTable(db, ifExists);
        PersonalMsgUserDao.dropTable(db, ifExists);
        ToDoListDao.dropTable(db, ifExists);
        ToDoUserDao.dropTable(db, ifExists);
        UserDao.dropTable(db, ifExists);
    }

    /**
     * WARNING: Drops all table on Upgrade! Use only during development.
     * Convenience method using a {@link DevOpenHelper}.
     */
    public static DaoSession newDevSession(Context context, String name) {
        Database db = new DevOpenHelper(context, name).getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    public DaoMaster(SQLiteDatabase db) {
        this(new StandardDatabase(db));
    }

    public DaoMaster(Database db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(ChannelDao.class);
        registerDaoClass(ChannelNoticeDao.class);
        registerDaoClass(ChannelUserDao.class);
        registerDaoClass(ChatFileDao.class);
        registerDaoClass(ChatMsgDao.class);
        registerDaoClass(CheckInDao.class);
        registerDaoClass(CheckInUserDao.class);
        registerDaoClass(FriendDao.class);
        registerDaoClass(GroupDao.class);
        registerDaoClass(GroupNoticeDao.class);
        registerDaoClass(GroupUserDao.class);
        registerDaoClass(MsgBotDao.class);
        registerDaoClass(PersonalMsgDao.class);
        registerDaoClass(PersonalMsgUserDao.class);
        registerDaoClass(ToDoListDao.class);
        registerDaoClass(ToDoUserDao.class);
        registerDaoClass(UserDao.class);
    }

    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }

    /**
     * Calls {@link #createAllTables(Database, boolean)} in {@link #onCreate(Database)} -
     */
    public static abstract class OpenHelper extends DatabaseOpenHelper {
        public OpenHelper(Context context, String name) {
            super(context, name, SCHEMA_VERSION);
        }

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(Database db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }

    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name) {
            super(context, name);
        }

        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

}
