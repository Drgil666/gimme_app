package com.project.gimme.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.view.fragment.FriendFragment;
import com.project.gimme.view.fragment.MessageFragment;
import com.project.gimme.view.fragment.MyInfoFragment;

/**
 * @author DrGilbert
 */
public class MainActivity extends BaseActivity {
    private final int TYPE_MESSAGE = 1;
    private final int TYPE_FRIEND = 2;
    private final int TYPE_MY_INFO = 3;
    private Integer currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentFragment = 1;
        setContentView(R.layout.activity_main);
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
        relativeLayout.setOnClickListener(view -> {
            System.out.println("click1!");
            if (currentFragment != TYPE_MESSAGE) {
                currentFragment = TYPE_MESSAGE;
                setTopText("信息");
                changeFragment(TYPE_MESSAGE);
            }
        });
    }

    private void initFriendLayout(double size) {
        RelativeLayout relativeLayout = findViewById(R.id.friend_layout);
        relativeLayout.getLayoutParams().width = (int) size;
        relativeLayout.setOnClickListener(view -> {
            System.out.println("click2!");
            if (currentFragment != TYPE_FRIEND) {
                setTopText("联系人");
                currentFragment = TYPE_FRIEND;
                changeFragment(TYPE_FRIEND);
            }
        });
    }

    private void initMyInfoLayout(double size) {
        RelativeLayout relativeLayout = findViewById(R.id.my_info_layout);
        relativeLayout.getLayoutParams().width = (int) size;
        relativeLayout.setOnClickListener(view -> {
            System.out.println("click3!");
            if (currentFragment != TYPE_MY_INFO) {
                setTopText("我的");
                currentFragment = TYPE_MY_INFO;
                changeFragment(TYPE_MY_INFO);
            }
        });
    }

    private void changeFragment(Integer op) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (op) {
            case TYPE_MESSAGE: {
                transaction.replace(R.id.body_fragment, new MessageFragment());
                transaction.commit();
                break;
            }
            case TYPE_FRIEND: {
                transaction.replace(R.id.body_fragment, new FriendFragment());
                transaction.commit();
                break;
            }
            case TYPE_MY_INFO: {
                transaction.replace(R.id.body_fragment, new MyInfoFragment());
                transaction.commit();
                break;
            }
            default:
                break;
        }
    }
}