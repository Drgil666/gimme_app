package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.pojo.Channel;
import com.project.gimme.pojo.Group;
import com.project.gimme.pojo.User;
import com.project.gimme.pojo.vo.QrVO;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.XToastUtils;
import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xutil.display.ImageUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 */
@SuppressLint("NonConstantResourceId")
public class QrActivity extends SwipeBackActivity {
    @BindView(R.id.qr_top_left_button)
    ImageView topLeftButton;
    @BindView(R.id.qr_body_nick)
    TextView bodyNick;
    @BindView(R.id.qr_body_id)
    TextView bodyId;
    @BindView(R.id.qr_body_qr_code)
    ImageView bodyQrCode;
    @BindView(R.id.qr_body_icon)
    ImageView bodyIcon;
    private Integer type;
    private Integer objectId;
    private String img;
    private String objectNick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        ButterKnife.bind(this);
        getType();
        initTopBar();
        initQrBody();
        createQrCodeWithLogo(ImageUtils.getBitmap(R.mipmap.app_icon));
    }

    private void initTopBar() {
        topLeftButton.setOnClickListener(view -> {
            finish();
        });
    }

    private void getType() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(BundleUtil.CHAT_TYPE_ATTRIBUTE);
        objectId = bundle.getInt(BundleUtil.OBJECT_ID_ATTRIBUTE);
        img = bundle.getString(BundleUtil.USER_AVATAR_ATTRIBUTE);
        objectNick = bundle.getString(BundleUtil.OBJECT_NICK_ATTRIBUTE);
    }

    private void initQrBody() {
        bodyNick.setText(objectNick);
        if (type.equals(ChatMsgUtil.Character.TYPE_FRIEND.getCode())) {
            bodyId.setText("Gimme号:" + objectId);
        } else if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            bodyId.setText("群号:" + objectId);
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            bodyId.setText("频道号:" + objectId);
        } else {
            XToastUtils.toast("类型错误!");
        }
        Glide.with(this)
                .load(GimmeApplication.getImageUrl(img))
                .error(R.mipmap.default_icon)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(bodyIcon);
        bodyQrCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createQrCodeWithLogo(ImageUtils.getBitmap(R.mipmap.app_icon));
            }
        });
    }

    private void createQrCodeWithLogo(Bitmap logo) {
        QrVO qrVO = new QrVO();
        qrVO.setObjectId(objectId);
        qrVO.setChatType(ChatMsgUtil.CHARACTER_LIST[type].getName());
        qrVO.setTimestamp(new Date());
        qrVO.setShareUserId(GimmeApplication.getUserId());
        bodyQrCode.setImageBitmap(XQRCode.createQRCodeWithLogo(new Gson().toJson(qrVO), 400, 400, logo));
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