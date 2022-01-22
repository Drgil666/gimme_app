package com.project.gimme.view.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.gimme.R;
import com.project.gimme.pojo.Channel;
import com.project.gimme.pojo.Group;
import com.project.gimme.pojo.User;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;

/**
 * @author DrGilbert
 */
public class QrActivity extends SwipeBackActivity {
    private ImageView topLeftButton;
    private TextView bodyNick;
    private TextView bodyId;
    private ImageView bodyQrCode;
    private ImageView bodyIcon;
    private Integer type;
    private Integer objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        topLeftButton = findViewById(R.id.qr_top_left_button);
        bodyNick = findViewById(R.id.qr_body_nick);
        bodyId = findViewById(R.id.qr_body_id);
        bodyQrCode = findViewById(R.id.qr_body_qr_code);
        getType();
        initTopBar();
        initQrBody();
    }

    private void initTopBar() {
        topLeftButton.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
        });
    }

    private void getType() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(BundleUtil.CHAT_TYPE_ATTRIBUTE);
        objectId = bundle.getInt(BundleUtil.OBJECT_ID_ATTRIBUTE);
    }

    private void initQrBody() {
        if (type.equals(ChatMsgUtil.Character.TYPE_FRIEND.getCode())) {
            User user = getUser(objectId);
            bodyNick.setText(user.getNick());
            bodyId.setText("Gimme号:" + user.getId());
        } else if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            Group group = getGroup(objectId);
            bodyNick.setText(group.getNick());
            bodyId.setText("群号:" + group.getId());
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            Channel channel = getChannel(objectId);
            bodyNick.setText(channel.getNick());
            bodyId.setText("频道号:" + channel.getId());
        } else {
            Toast.makeText(this, "类型错误!", Toast.LENGTH_LONG).show();
        }
    }

    private User getUser(Integer id) {
        User user = new User();
        user.setId(id);
        user.setNick("昵称" + id);
        return user;
    }

    private Group getGroup(Integer id) {
        Group group = new Group();
        group.setId(id);
        group.setNick("群聊" + id);
        return group;
    }

    private Channel getChannel(Integer id) {
        Channel channel = new Channel();
        channel.setNick("频道" + id);
        channel.setId(id);
        return channel;
    }
}