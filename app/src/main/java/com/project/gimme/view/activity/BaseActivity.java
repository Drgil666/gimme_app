package com.project.gimme.view.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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
        //Emoji初始化
        XUI.initTheme(this);
        Emojiconize.activity(this).go();
        super.onCreate(savedInstanceState);
        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
