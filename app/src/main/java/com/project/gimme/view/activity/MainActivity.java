package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.utils.SessionUtil;
import com.project.gimme.view.fragment.FriendFragment;
import com.project.gimme.view.fragment.MessageFragment;
import com.project.gimme.view.fragment.MyInfoFragment;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 */
@SuppressLint("NonConstantResourceId")
public class MainActivity extends BaseActivity {
    private Integer currentFragment = SessionUtil.Character.TYPE_MESSAGE.getCode();
    private final Integer height = GimmeApplication.getHeight();
    private final Integer weight = GimmeApplication.getWeight();
    private MessageFragment messageFragment;
    private FriendFragment friendFragment;
    private MyInfoFragment myInfoFragment;
    @BindView(R.id.main_top_text)
    TextView topText;
    @BindView(R.id.main_top_right_button)
    ImageView topRightButton;
    @BindView(R.id.main_message_layout_icon)
    ImageView messageIcon;
    @BindView(R.id.main_message_layout_text)
    TextView messageText;
    @BindView(R.id.main_friend_layout_icon)
    ImageView friendIcon;
    @BindView(R.id.main_friend_layout_text)
    TextView friendText;
    @BindView(R.id.main_my_info_layout_icon)
    ImageView myInfoIcon;
    @BindView(R.id.main_my_info_layout_text)
    TextView myInfoText;
    @BindView(R.id.main_message_layout_new_message_count_background)
    RelativeLayout newMessageCountBackGround;
    @BindView(R.id.main_message_layout_new_message_count)
    TextView newMessageCount;

    //TODO:动态更新的部分仍然需要修复
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        messageFragment = new MessageFragment();
        friendFragment = new FriendFragment();
        myInfoFragment = new MyInfoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.getFragments().clear();
        fragmentTransaction.add(R.id.main_body_fragment, messageFragment).commit();
        initTopBar(0.1);
        initTopText();
        initBottomBar(0.1);
        initNewMessageCount(0);
    }

    public void initNewMessageCount(Integer count) {
        if (count != 0) {
            newMessageCountBackGround.setVisibility(View.VISIBLE);
            newMessageCount.setText(count > 99 ? "99+" : count.toString());
        } else {
            newMessageCountBackGround.setVisibility(View.INVISIBLE);
        }
    }

    private void initTopBar(double size) {
        RelativeLayout relativeLayout = findViewById(R.id.main_top_bar);
        relativeLayout.getLayoutParams().height = (int) Math.floor(height * size);
    }

    private void initTopText() {
        topText.setText("消息");
    }

    private void setTopText(String text) {
        topText.setText(text);
    }

    private void setTopRightButton(double size) {
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
                setTopText(SessionUtil.Character.TYPE_MESSAGE.getName());
                changeFragment(SessionUtil.Character.TYPE_MESSAGE.getCode());
                currentFragment = SessionUtil.Character.TYPE_MESSAGE.getCode();
            }
        });
    }

    private void initFriendLayout(double size) {
        RelativeLayout relativeLayout = findViewById(R.id.main_friend_layout);
        relativeLayout.getLayoutParams().width = (int) size;
        relativeLayout.setOnClickListener(view -> {
            if (!currentFragment.equals(SessionUtil.Character.TYPE_FRIEND.getCode())) {
                setTopText(SessionUtil.Character.TYPE_FRIEND.getName());
                changeFragment(SessionUtil.Character.TYPE_FRIEND.getCode());
                currentFragment = SessionUtil.Character.TYPE_FRIEND.getCode();
            }
        });
    }

    private void initMyInfoLayout(double size) {
        RelativeLayout relativeLayout = findViewById(R.id.main_my_info_layout);
        relativeLayout.getLayoutParams().width = (int) size;
        relativeLayout.setOnClickListener(view -> {
            if (!currentFragment.equals(SessionUtil.Character.TYPE_MY_INFO.getCode())) {
                setTopText(SessionUtil.Character.TYPE_MY_INFO.getName());
                changeFragment(SessionUtil.Character.TYPE_MY_INFO.getCode());
                currentFragment = SessionUtil.Character.TYPE_MY_INFO.getCode();
            }
        });
    }

    private void changeFragment(Integer op) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment to = getFragment(op);
        Fragment from = getFragment(currentFragment);
        if (!to.isAdded()) {//未被add
            fragmentTransaction.hide(from).add(R.id.main_body_fragment, to).commit();
        } else {//已经被add
            fragmentTransaction.hide(from).show(to).commit();
        }
        changeUIColor(op);
    }

    private void changeUIColor(Integer op) {
        Picasso.with(this).load(R.mipmap.message).into(messageIcon);
        Picasso.with(this).load(R.mipmap.contacts).into(friendIcon);
        Picasso.with(this).load(R.mipmap.my_info).into(myInfoIcon);
        messageText.setTextColor(R.color.black);
        friendText.setTextColor(R.color.black);
        myInfoText.setTextColor(R.color.black);
        if (op.equals(SessionUtil.Character.TYPE_MESSAGE.getCode())) {
            Picasso.with(this).load(R.mipmap.message_select).into(messageIcon);
            messageText.setTextColor(R.color.gimme_color);
            Picasso.with(this).load(R.mipmap.add_plus).into(topRightButton);
        } else if (op.equals(SessionUtil.Character.TYPE_FRIEND.getCode())) {
            Picasso.with(this).load(R.mipmap.contacts_select).into(friendIcon);
            friendText.setTextColor(R.color.gimme_color);
            Picasso.with(this).load(R.mipmap.add_plus).into(topRightButton);
        } else if (op.equals(SessionUtil.Character.TYPE_MY_INFO.getCode())) {
            Picasso.with(this).load(R.mipmap.my_info_select).into(myInfoIcon);
            myInfoText.setTextColor(R.color.gimme_color);
            Picasso.with(this).load(R.mipmap.setting).into(topRightButton);
            //TODO:右上角逻辑需要修改
        }
    }

    private Fragment getFragment(Integer op) {
        if (op.equals(SessionUtil.Character.TYPE_MESSAGE.getCode())) {
            return messageFragment;
        } else if (op.equals(SessionUtil.Character.TYPE_FRIEND.getCode())) {
            return friendFragment;
        } else if (op.equals(SessionUtil.Character.TYPE_MY_INFO.getCode())) {
            return myInfoFragment;
        } else {
            return null;
        }
    }
}