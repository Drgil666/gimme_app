package com.project.gimme;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import com.project.gimme.controller.Controller;
import com.project.gimme.controller.RetrofitClient;
import com.project.gimme.database.LocalDataBase;

import lombok.Data;

/**
 * @author DrGilbert
 * @date 2022/1/5 17:05
 */
@Data
public class GimmeApplication extends Application {
    private static LocalDataBase localDataBase;
    private static Controller controller;
    public static String token;

    @Override
    public void onCreate() {
        super.onCreate();
        localDataBase = LocalDataBase.getInstance(this);
        controller = RetrofitClient.getInstance().getController();
        //创建单例
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
}
