package com.project.gimme.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.project.gimme.gen.DaoMaster;
import com.project.gimme.gen.DaoSession;
import com.project.gimme.gen.UserDao;
import com.project.gimme.pojo.User;

import org.greenrobot.greendao.query.QueryBuilder;

/**
 * @author DrGilbert
 * @date 2022/1/7 14:50
 */
public class DataBaseManager {
    private final static String DB_NAME = "gimme.db";
    private static DataBaseManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    public DataBaseManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DataBaseManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DataBaseManager.class) {
                if (mInstance == null) {
                    mInstance = new DataBaseManager(context);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        }
        return openHelper.getWritableDatabase();
    }

    /**
     * 插入用户
     *
     * @param user 要加入的用户
     * @return 是否插入成功
     */
    public void insertUser(User user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        userDao.insert(user);
    }

    /**
     * 更新一条记录
     *
     * @param user 要更新的用户
     */
    public void updateUser(User user) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        userDao.update(user);
    }

    public User getUser(Integer id) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        QueryBuilder<User> queryBuilder = userDao.queryBuilder();
        queryBuilder.where(UserDao.Properties.Id.eq(id));
        return queryBuilder.unique();
    }
}
