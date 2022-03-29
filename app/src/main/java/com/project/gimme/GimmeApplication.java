package com.project.gimme;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.StrictMode;

import com.project.gimme.controller.TestController;
import com.xuexiang.xui.XUI;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import io.rong.imlib.RongIMClient;

/**
 * @author DrGilbert
 * @date 2022/1/5 17:05
 */
public class GimmeApplication extends Application {
    private static String token = "";
    private static Integer height;
    private static Integer weight;
    private static Integer userId = 2;
    public static final Integer TYPE_ERROR = -1;
    public static final String REMOTE_URL = "http://10.21.234.24:8080";
    public static final String APP_KEY = "pvxdm17jpdthr";
    public static final String LOCAL_STORAGE = "gimme_token";
    public static final String TOKEN = "token";
    public static final String APP_NAME = "gimme";

    public static Integer getUserId() {
        return userId;
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

    public static void setUserId(Integer userId) {
        GimmeApplication.userId = userId;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        XUI.init(this);
        XUI.debug(true);
        RongIMClient.init(getApplicationContext(), APP_KEY);
        //滑动工具初始化
        BGASwipeBackHelper.init(this, null);
        //字体初始化
        XUI.initFontStyle("fonts/ping_fang_sc.ttf");
        TestController.test(REMOTE_URL + "/api/user/check");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
//        SharedPreferences sharedPreferences = getSharedPreferences(LOCAL_STORAGE, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.clear();
//        editor.apply();
    }
}

