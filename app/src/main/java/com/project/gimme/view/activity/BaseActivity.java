package com.project.gimme.view.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.project.gimme.GimmeApplication;
import com.xuexiang.xui.XUI;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.github.rockerhieu.emojiconize.Emojiconize;

/**
 * @author DrGilbert
 * @date 2022/1/8 16:48
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XUI.initTheme(this);
        Emojiconize.activity(this).go();
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    protected void clearStorage() {
        SharedPreferences sharedPreferences = getSharedPreferences(GimmeApplication.LOCAL_STORAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
