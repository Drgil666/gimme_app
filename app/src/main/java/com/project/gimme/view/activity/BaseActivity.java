package com.project.gimme.view.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tandong.swichlayout.BaseEffects;
import com.tandong.swichlayout.SwichLayoutInterFace;
import com.tandong.swichlayout.SwitchLayout;
import com.xuexiang.xui.XUI;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.github.rockerhieu.emojiconize.Emojiconize;

/**
 * @author DrGilbert
 * @date 2022/1/8 16:48
 */
public class BaseActivity extends AppCompatActivity implements SwichLayoutInterFace {

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

    @Override
    public void setEnterSwichLayout() {
        SwitchLayout.getSlideFromLeft(this, false, null);
    }

    //第二个Activity退出时的动画效果
    @Override
    public void setExitSwichLayout() {
        SwitchLayout.getSlideToLeft(this, true,
                BaseEffects.getMoreQuickEffect());
    }
}
