package com.project.gimme;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;
import android.os.StrictMode;

import androidx.annotation.Nullable;
import androidx.emoji.bundled.BundledEmojiCompatConfig;
import androidx.emoji.text.EmojiCompat;

import com.project.gimme.controller.TestController;
import com.tandong.switchlayout.SwitchLayout;
import com.xuexiang.xui.XUI;

import org.apache.commons.lang3.StringUtils;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * @author DrGilbert
 * @date 2022/1/5 17:05
 */
public class GimmeApplication extends Application {
    private static String token = "";
    private static Integer height;
    private static Integer weight;
    private static Integer userId = null;
    public static final Integer TYPE_ERROR = -1;
    //            public static final String REMOTE_URL = "http://10.21.234.24:8080";
    public static final String REMOTE_URL = "https://zjgsucheckin.top:8443";
    public static final String APP_KEY = "pvxdm17jpdthr";
    public static final String LOCAL_STORAGE = "gimme_token";
    public static final String TOKEN = "token";
    public static final String APP_NAME = "gimme";
    public static final String USER_ID = "userId";
    public static final Integer NOTICE_NUMBER = 100000;
    public static final Integer MESSAGE_TIME = 2500;
    public static final Integer DRAG_TIME = 1500;
    public static final Boolean IS_TEA = false;
    public static Integer getUserId() {
        return userId;
    }

    public static Integer getNotice(Integer type, Integer objectId) {
        return type * NOTICE_NUMBER + objectId;
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

    public static String getImageUrl(String mongoId) {
        return REMOTE_URL + "/api/chat/file/image" + (StringUtils.isEmpty(mongoId) ? "" : "/" + mongoId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        XUI.init(this);
        XUI.debug(true);
//        RongIMClient.init(getApplicationContext(), APP_KEY);
        //滑动工具初始化
        BGASwipeBackHelper.init(this, null);
        //字体初始化
        XUI.initFontStyle("ping_fang_sc.ttf");
        //设置动画效果时间
        SwitchLayout.animDuration = 300;
        TestController.test();
        loadEmojiFromBundled();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    private void loadEmojiFromBundled() {
        EmojiCompat.Config config = new BundledEmojiCompatConfig(getApplicationContext());

        config.setReplaceAll(true)
                .registerInitCallback(new EmojiCompat.InitCallback() {
                    @Override
                    public void onInitialized() {
                    }

                    @Override
                    public void onFailed(@Nullable Throwable throwable) {
                    }
                });
        EmojiCompat.init(config);
    }
}

