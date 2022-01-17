package com.project.gimme.view.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import io.github.rockerhieu.emojiconize.Emojiconize;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * @author DrGilbert
 * @date 2022/1/8 16:48
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Emoji初始化
        Emojiconize.activity(this).go();
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
