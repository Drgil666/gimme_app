package com.project.gimme;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import com.project.gimme.controller.Controller;
import com.project.gimme.controller.RetrofitClient;

/**
 * @author DrGilbert
 * @date 2022/1/5 17:05
 */
public class GimmeApplication extends Application {
    private static Controller controller;


    private static String token;
    private static Integer height;
    private static Integer weight;

    @Override
    public void onCreate() {
        super.onCreate();
        controller = RetrofitClient.getInstance().getController();
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

    public static void setHeight(Integer height) {
        GimmeApplication.height = height;
    }

    public static void setWeight(Integer weight) {
        GimmeApplication.weight = weight;
    }

    public static Integer getHeight() {
        return height;
    }

    public static Integer getWeight() {
        return weight;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        GimmeApplication.token = token;
    }
}

