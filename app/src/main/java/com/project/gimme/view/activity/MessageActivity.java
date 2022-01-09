package com.project.gimme.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;

/**
 * @author DrGilbert
 */
public class MessageActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initTopBar(0.1);
        initTopText(0.2);
        initBottomBar(0.1);
    }

    private void initTopBar(double size) {
        Integer height = GimmeApplication.getHeight();
        RelativeLayout relativeLayout = findViewById(R.id.top_bar);
        relativeLayout.getLayoutParams().height = (int) Math.floor(height * size);
    }

    private void initTopText(double size) {
        TextView tabText = findViewById(R.id.top_text);
        tabText.setText("消息");

    }

    private void setTopText(String text) {
        TextView tabText = findViewById(R.id.top_text);
        tabText.setText(text);
    }

    private void setTopRightButton(double size) {
        ImageView topRightButton = findViewById(R.id.top_right_button);
        topRightButton.setOnClickListener(view -> {
                    System.out.println("click!");
                }
        );
    }

    private void initBottomBar(double size) {
        Integer height = GimmeApplication.getHeight();
        Integer weight = GimmeApplication.getWeight();
        RelativeLayout relativeLayout = findViewById(R.id.bottom_bar);
        relativeLayout.getLayoutParams().height = (int) Math.floor(height * size);
        Double layoutWeight = weight / 3.0;
        initMessageLayout(layoutWeight);
        initFriendLayout(layoutWeight);
        initMyInfoLayout(weight - 2 * layoutWeight);
    }

    private void initMessageLayout(double size) {
        RelativeLayout relativeLayout = findViewById(R.id.message_layout);
        relativeLayout.getLayoutParams().width = (int) size;
    }

    private void initFriendLayout(double size) {
        RelativeLayout relativeLayout = findViewById(R.id.friend_layout);
        relativeLayout.getLayoutParams().width = (int) size;
    }

    private void initMyInfoLayout(double size) {
        RelativeLayout relativeLayout = findViewById(R.id.my_info_layout);
        relativeLayout.getLayoutParams().width = (int) size;
    }
}