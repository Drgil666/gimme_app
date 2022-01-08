package com.project.gimme.view.activity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;

/**
 * @author DrGilbert
 */
public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //全屏，隐藏状态栏。
        getSupportActionBar().hide();
        //隐藏标题栏
        setContentView(R.layout.activity_welcome);
        setScreen();
        setWelcomeIcon(0.5, 0.4);
        setWelcomeText(0.6);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(1500);
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                    //跳转
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    public void setScreen() {
        Display display = getWindowManager().getDefaultDisplay();
        Point outSize = new Point();
        display.getSize(outSize);
        Integer x = outSize.x;
        Integer y = outSize.y;
//        Toast.makeText(this, "手机像素为：X:" + x + "  Y:" + y, Toast.LENGTH_LONG).show();
        GimmeApplication.setHeight(y);
        GimmeApplication.setWeight(x);
    }

    private void setWelcomeIcon(Double size, Double top) {
        Integer height = GimmeApplication.getHeight();
        Integer weight = GimmeApplication.getWeight();
        ImageView welcomeIcon = findViewById(R.id.welcome_icon);
        int imageSize = (int) Math.floor((Math.min(height, weight) * size));
        int marginTop = (int) Math.floor((Math.min(height, weight) * top));
        welcomeIcon.getLayoutParams().height = imageSize;
        welcomeIcon.getLayoutParams().width = imageSize;
        //动态设置icon大小
        ViewGroup.MarginLayoutParams marginParams =
                (ViewGroup.MarginLayoutParams) welcomeIcon.getLayoutParams();
        marginParams.setMargins(marginParams.leftMargin,
                marginTop,
                marginParams.leftMargin,
                marginParams.bottomMargin);
        //动态设置icon居中位置
    }

    private void setWelcomeText(Double top) {
        TextView welcomeText = findViewById(R.id.welcome_text);
        Integer height = GimmeApplication.getHeight();
        int marginTop = (int) Math.floor(height * top);
        ViewGroup.MarginLayoutParams marginParams =
                (ViewGroup.MarginLayoutParams) welcomeText.getLayoutParams();
        marginParams.setMargins(marginParams.leftMargin,
                marginTop,
                marginParams.leftMargin,
                marginParams.bottomMargin);
    }
}