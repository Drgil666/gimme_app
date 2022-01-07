package com.project.gimme;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.sqlite.SQLiteDatabase;

import com.project.gimme.controller.Controller;
import com.project.gimme.controller.RetrofitClient;
import com.project.gimme.gen.DaoMaster;
import com.project.gimme.gen.DaoSession;

/**
 * @author DrGilbert
 * @date 2022/1/5 17:05
 */
public class GimmeApplication extends Application {
    private static Controller controller;
    private static String token;
    private static final String DB_NAME = "gimme.db";
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        controller = RetrofitClient.getInstance().getController();
        initGreenDao();
        //创建单例
    }

    /**
     * 初始化GreenDao
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, DB_NAME);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    /**
     * 寻找对应的activity
     *
     * @param context 对应上下文
     * @return 对应的activity
     */
    public static Activity findActivity(Context context) {
        if (context instanceof Activity) {
            return (Activity) context;
        }
        if (context instanceof ContextWrapper) {
            ContextWrapper wrapper = (ContextWrapper) context;
            return findActivity(wrapper.getBaseContext());
        } else {
            return null;
        }
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
