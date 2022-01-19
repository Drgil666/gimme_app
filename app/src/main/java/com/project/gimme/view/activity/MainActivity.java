package com.project.gimme.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.utils.SessionUtil;
import com.project.gimme.view.fragment.FriendFragment;
import com.project.gimme.view.fragment.MessageFragment;
import com.project.gimme.view.fragment.MyInfoFragment;

/**
 * @author DrGilbert
 */
public class MainActivity extends BaseActivity {
    private Integer currentFragment = 1;
    private final Integer height = GimmeApplication.getHeight();
    private final Integer weight = GimmeApplication.getWeight();
    private MessageFragment messageFragment;
    private FriendFragment friendFragment;
    private MyInfoFragment myInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageFragment = new MessageFragment();
        friendFragment = new FriendFragment();
        myInfoFragment = new MyInfoFragment();
        initTopBar(0.1);
        initTopText();
        initBottomBar(0.1);
    }

    private void initTopBar(double size) {
        RelativeLayout relativeLayout = findViewById(R.id.main_top_bar);
        relativeLayout.getLayoutParams().height = (int) Math.floor(height * size);
    }

    private void initTopText() {
        TextView tabText = findViewById(R.id.main_top_text);
        tabText.setText("消息");

    }

    private void setTopText(String text) {
        TextView tabText = findViewById(R.id.main_top_text);
        tabText.setText(text);
    }

    private void setTopRightButton(double size) {
        ImageView topRightButton = findViewById(R.id.main_top_right_button);
        topRightButton.setOnClickListener(view -> {
//                    System.out.println("click!");
                }
        );
    }

    private void initBottomBar(double size) {
        RelativeLayout relativeLayout = findViewById(R.id.main_bottom_bar);
        relativeLayout.getLayoutParams().height = (int) Math.floor(height * size);
        double layoutWeight = weight / 3.0;
        initMessageLayout(layoutWeight);
        initFriendLayout(layoutWeight);
        initMyInfoLayout(weight - 2 * layoutWeight);
    }

    private void initMessageLayout(double size) {
        RelativeLayout relativeLayout = findViewById(R.id.main_message_layout);
        relativeLayout.getLayoutParams().width = (int) size;
        relativeLayout.setOnClickListener(view -> {
            if (!currentFragment.equals(SessionUtil.Character.TYPE_MESSAGE.getCode())) {
                currentFragment = SessionUtil.Character.TYPE_MESSAGE.getCode();
                setTopText(SessionUtil.Character.TYPE_MESSAGE.getName());
                changeFragment(SessionUtil.Character.TYPE_MESSAGE.getCode());
            }
        });
    }

    private void initFriendLayout(double size) {
        RelativeLayout relativeLayout = findViewById(R.id.main_friend_layout);
        relativeLayout.getLayoutParams().width = (int) size;
        relativeLayout.setOnClickListener(view -> {
            if (!currentFragment.equals(SessionUtil.Character.TYPE_FRIEND.getCode())) {
                currentFragment = SessionUtil.Character.TYPE_FRIEND.getCode();
                setTopText(SessionUtil.Character.TYPE_FRIEND.getName());
                changeFragment(SessionUtil.Character.TYPE_FRIEND.getCode());
            }
        });
    }

    private void initMyInfoLayout(double size) {
        RelativeLayout relativeLayout = findViewById(R.id.main_my_info_layout);
        relativeLayout.getLayoutParams().width = (int) size;
        relativeLayout.setOnClickListener(view -> {
            if (!currentFragment.equals(SessionUtil.Character.TYPE_MY_INFO.getCode())) {
                currentFragment = SessionUtil.Character.TYPE_MY_INFO.getCode();
                setTopText(SessionUtil.Character.TYPE_MY_INFO.getName());
                changeFragment(SessionUtil.Character.TYPE_MY_INFO.getCode());
            }
        });
    }

    private void changeFragment(Integer op) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.getFragments().clear();
        if (op.equals(SessionUtil.Character.TYPE_MESSAGE.getCode())) {
            fragmentTransaction.replace(R.id.main_body_fragment, new MessageFragment()).commit();
        } else if (op.equals(SessionUtil.Character.TYPE_FRIEND.getCode())) {
            fragmentTransaction.replace(R.id.main_body_fragment, new FriendFragment()).commit();
        } else if (op.equals(SessionUtil.Character.TYPE_MY_INFO.getCode())) {
            fragmentTransaction.replace(R.id.main_body_fragment, new MyInfoFragment()).commit();
        } else {
            Toast.makeText(this, "类型错误!", Toast.LENGTH_LONG).show();
        }
//        System.out.println("count:" + getSupportFragmentManager().getFragments().size());
    }
}