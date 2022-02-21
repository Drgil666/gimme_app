package com.project.gimme;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;

import com.project.gimme.controller.TestController;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * @author DrGilbert
 * @date 2022/1/5 17:05
 */
public class GimmeApplication extends Application {
    private static String token = "";
    private static Integer height;
    private static Integer weight;
    public static final Integer TYPE_ERROR = -1;
    public static final String REMOTE_URL = "http://10.21.234.24:8080";
    public static final String APP_KEY = "pvxdm17jpdthr";
    public static final String LOCAL_STORAGE = "gimme_token";
    public static final String TOKEN = "token";

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        RongIMClient.init(getApplicationContext(), APP_KEY);
        //滑动工具初始化
        BGASwipeBackHelper.init(this, null);
        //字体初始化
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ping_fang_sc.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        TestController.test(REMOTE_URL + "/api/user/check");
//        SharedPreferences sharedPreferences = getSharedPreferences(LOCAL_STORAGE, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("token", null);
//        editor.apply();
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

