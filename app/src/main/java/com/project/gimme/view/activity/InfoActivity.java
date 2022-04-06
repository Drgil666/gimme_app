package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.InfoTypeUtil;
import com.project.gimme.utils.XToastUtils;
import com.project.gimme.view.fragment.FriendInfoFragment;
import com.project.gimme.view.fragment.OtherInfoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 */
@SuppressLint("NonConstantResourceId")
public class InfoActivity extends SwipeBackActivity {
    private Integer currentFragment = InfoTypeUtil.Character.TYPE_FRIEND.getCode();
    private final Integer height = GimmeApplication.getHeight();
    private Integer type;
    private Integer objectId;
    private Boolean isJoined;
    @BindView(R.id.info_top_text)
    TextView tabText;
    @BindView(R.id.info_top_left_button)
    ImageView leftButton;
    @BindView(R.id.info_top_bar)
    RelativeLayout topBar;
    private FriendInfoFragment friendInfoFragment;
    private OtherInfoFragment otherInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);
        friendInfoFragment = new FriendInfoFragment();
        otherInfoFragment = new OtherInfoFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.getFragments().clear();
        getType();
        initTopBar(0.1);
    }

    private void getType() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(BundleUtil.CHAT_TYPE_ATTRIBUTE);
        objectId = bundle.getInt(BundleUtil.OBJECT_ID_ATTRIBUTE);
        isJoined = bundle.getBoolean(BundleUtil.IS_JOINED_ATTRIBUTE);
//        System.out.println("type:" + type + " object_id:" + objectId + " is joined:" + isJoined);
    }

    private void initTopBar(double size) {
        leftButton.setOnClickListener(view -> {
            finish();
        });
        topBar.getLayoutParams().height = (int) Math.floor(height * size);
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment to = getFragment(type);
        if (type.equals(InfoTypeUtil.Character.TYPE_FRIEND.getCode())) {
            setTopText("聊天设置");
        } else if (type.equals(InfoTypeUtil.Character.TYPE_SELF.getCode())) {
            setTopText("个人信息");
        } else if (type.equals(InfoTypeUtil.Character.TYPE_GROUP.getCode())) {
            setTopText("群聊设置");
        } else if (type.equals(InfoTypeUtil.Character.TYPE_CHANNEL.getCode())) {
            setTopText("频道设置");
        } else if (type.equals(InfoTypeUtil.Character.TYPE_GROUP_MEMBER.getCode())
                || type.equals(InfoTypeUtil.Character.TYPE_GROUP_SELF.getCode())) {
            setTopText("群聊成员设置");
        } else if (type.equals(InfoTypeUtil.Character.TYPE_CHANNEL_MEMBER.getCode())
                || type.equals(InfoTypeUtil.Character.TYPE_CHANNEL_SELF.getCode())) {
            setTopText("频道成员设置");
        } else {
            XToastUtils.toast("类型错误!");
        }
        fragmentTransaction.replace(R.id.info_fragment, to).commit();
    }

    private Fragment getFragment(Integer type) {
        if (type.equals(InfoTypeUtil.Character.TYPE_FRIEND.getCode())) {
            return friendInfoFragment;
        } else if (type.equals(InfoTypeUtil.Character.TYPE_SELF.getCode())) {
            return friendInfoFragment;
        } else if (type.equals(InfoTypeUtil.Character.TYPE_GROUP.getCode())) {
            return otherInfoFragment;
        } else if (type.equals(InfoTypeUtil.Character.TYPE_CHANNEL.getCode())) {
            return otherInfoFragment;
        } else if (type.equals(InfoTypeUtil.Character.TYPE_GROUP_MEMBER.getCode())
                || type.equals(InfoTypeUtil.Character.TYPE_GROUP_SELF.getCode())) {
            return friendInfoFragment;
        } else if (type.equals(InfoTypeUtil.Character.TYPE_CHANNEL_MEMBER.getCode())
                || type.equals(InfoTypeUtil.Character.TYPE_CHANNEL_SELF.getCode())) {
            return friendInfoFragment;
        } else {
            return null;
        }
    }

    private void setTopText(String text) {
        tabText.setText(text);
    }
}