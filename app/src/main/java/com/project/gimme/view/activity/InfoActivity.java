package com.project.gimme.view.activity;

import static com.project.gimme.utils.BundleUtil.OBJECTID_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.TYPE_ATTRIBUTE;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.view.fragment.FriendInfoFragment;
import com.project.gimme.view.fragment.OtherInfoFragment;

/**
 * @author DrGilbert
 */
public class InfoActivity extends SwipeBackActivity {
    private final Integer height = GimmeApplication.getHeight();
    private Integer type;
    private Integer objectId;
    private TextView tabText;
    private ImageView leftButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        tabText = findViewById(R.id.info_top_text);
        leftButton = findViewById(R.id.info_top_left_button);
        getType();
        init(0.1);
    }

    private void getType() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(TYPE_ATTRIBUTE);
        objectId = bundle.getInt(OBJECTID_ATTRIBUTE);
//        System.out.println("type:" + type + " object_id:" + objectId);
    }

    private void init(double size) {
        leftButton.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
        });
        Bundle bundle = new Bundle();
        bundle.putInt(TYPE_ATTRIBUTE, type);
        bundle.putInt(OBJECTID_ATTRIBUTE, objectId);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.getFragments().clear();
        RelativeLayout relativeLayout = findViewById(R.id.info_top_bar);
        relativeLayout.getLayoutParams().height = (int) Math.floor(height * size);
        if (type.equals(ChatMsgUtil.Character.TYPE_FRIEND.getCode())) {
            setTopText("聊天设置");
            FriendInfoFragment friendInfoFragment = new FriendInfoFragment();
            fragmentTransaction.replace(R.id.info_fragment, friendInfoFragment).commit();
        } else if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            setTopText("群聊设置");
            OtherInfoFragment otherInfoFragment = new OtherInfoFragment();
            fragmentTransaction.replace(R.id.info_fragment, otherInfoFragment).commit();
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            setTopText("频道设置");
            OtherInfoFragment otherInfoFragment = new OtherInfoFragment();
            fragmentTransaction.replace(R.id.info_fragment, otherInfoFragment).commit();
        } else {
            Toast.makeText(this, "类型错误!", Toast.LENGTH_LONG).show();
        }
    }

    private void setTopText(String text) {
        tabText.setText(text);
    }
}