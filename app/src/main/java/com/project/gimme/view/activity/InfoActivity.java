package com.project.gimme.view.activity;

import static com.project.gimme.utils.BundleUtil.CHAT_TYPE_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_ID_ATTRIBUTE;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.utils.InfoTypeUtil;
import com.project.gimme.view.fragment.FriendInfoFragment;
import com.project.gimme.view.fragment.OtherInfoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 */
@SuppressLint("NonConstantResourceId")
public class InfoActivity extends SwipeBackActivity {
    private final Integer height = GimmeApplication.getHeight();
    private Integer type;
    private Integer objectId;
    @BindView(R.id.info_top_text)
    TextView tabText;
    @BindView(R.id.info_top_left_button)
    ImageView leftButton;
    @BindView(R.id.info_top_bar)
    RelativeLayout topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);
        getType();
        initTopBar(0.1);
    }

    private void getType() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(CHAT_TYPE_ATTRIBUTE);
        objectId = bundle.getInt(OBJECT_ID_ATTRIBUTE);
        System.out.println("type:" + type + " object_id:" + objectId);
    }

    private void initTopBar(double size) {
        leftButton.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
        });
        topBar.getLayoutParams().height = (int) Math.floor(height * size);
        initFragment();
    }

    private void initFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.getFragments().clear();
        if (type.equals(InfoTypeUtil.Character.TYPE_FRIEND.getCode())) {
            setTopText("聊天设置");
            FriendInfoFragment friendInfoFragment = new FriendInfoFragment();
            fragmentTransaction.replace(R.id.info_fragment, friendInfoFragment).commit();
        } else if (type.equals(InfoTypeUtil.Character.TYPE_SELF.getCode())) {
            setTopText("个人信息");
            FriendInfoFragment friendInfoFragment = new FriendInfoFragment();
            fragmentTransaction.replace(R.id.info_fragment, friendInfoFragment).commit();
        } else if (type.equals(InfoTypeUtil.Character.TYPE_GROUP.getCode())) {
            setTopText("群聊设置");
            OtherInfoFragment otherInfoFragment = new OtherInfoFragment();
            fragmentTransaction.replace(R.id.info_fragment, otherInfoFragment).commit();
        } else if (type.equals(InfoTypeUtil.Character.TYPE_CHANNEL.getCode())) {
            setTopText("频道设置");
            OtherInfoFragment otherInfoFragment = new OtherInfoFragment();
            fragmentTransaction.replace(R.id.info_fragment, otherInfoFragment).commit();
        } else if (type.equals(InfoTypeUtil.Character.TYPE_GROUP_MEMBER.getCode())
                || type.equals(InfoTypeUtil.Character.TYPE_GROUP_SELF.getCode())) {
            setTopText("群聊成员设置");
            FriendInfoFragment friendInfoFragment = new FriendInfoFragment();
            fragmentTransaction.replace(R.id.info_fragment, friendInfoFragment).commit();
        } else if (type.equals(InfoTypeUtil.Character.TYPE_CHANNEL_MEMBER.getCode())
                || type.equals(InfoTypeUtil.Character.TYPE_CHANNEL_SELF.getCode())) {
            setTopText("频道成员设置");
            FriendInfoFragment friendInfoFragment = new FriendInfoFragment();
            fragmentTransaction.replace(R.id.info_fragment, friendInfoFragment).commit();
        } else {
            Toast.makeText(this, "类型错误!", Toast.LENGTH_LONG).show();
        }
    }

    private void setTopText(String text) {
        tabText.setText(text);
    }
}