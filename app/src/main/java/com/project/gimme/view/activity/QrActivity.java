package com.project.gimme.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.ChannelController;
import com.project.gimme.controller.GroupController;
import com.project.gimme.controller.UserController;
import com.project.gimme.pojo.Group;
import com.project.gimme.pojo.User;
import com.project.gimme.pojo.vo.ChannelVO;
import com.project.gimme.pojo.vo.GroupVO;
import com.project.gimme.pojo.vo.QrVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.FileUtil;
import com.project.gimme.utils.JsonUtil;
import com.project.gimme.utils.XToastUtils;
import com.xuexiang.xqrcode.XQRCode;
import com.xuexiang.xutil.display.ImageUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

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
    @BindView(R.id.qr_button)
    Button qrButton;
    private Context mContext = this;
    private Integer type;
    private Integer objectId;
    private String img;
    private String objectNick;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        ButterKnife.bind(this);
        getType();
        initTopBar();
    }

    private void initTopBar() {
        topLeftButton.setOnClickListener(view -> {
            finish();
        });
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authorize();
                FileUtil.saveImageToGallery(mContext, ((BitmapDrawable) bodyQrCode.getDrawable()).getBitmap());
                XToastUtils.toast("????????????!");
            }
        });
    }

    private void authorize() {
        //??????????????????????????????
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //????????????????????????????????????????????????????????????????????????????????? true???
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //?????????????????????????????????????????????????????????????????????????????????
                // ???????????????????????????????????????????????????.??????????????????"????????????"??????????????????false
            } else {
                //????????????????????????????????????????????????????????????????????????1??????????????????????????????????????????onRequestPermissionsResult????????????????????????
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    private void getType() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(BundleUtil.CHAT_TYPE_ATTRIBUTE);
        objectId = bundle.getInt(BundleUtil.OBJECT_ID_ATTRIBUTE);
        if (bundle.getString(BundleUtil.USER_AVATAR_ATTRIBUTE) != null) {
            img = bundle.getString(BundleUtil.USER_AVATAR_ATTRIBUTE);
        }
        if (bundle.getString(BundleUtil.OBJECT_NICK_ATTRIBUTE) != null) {
            objectNick = bundle.getString(BundleUtil.OBJECT_NICK_ATTRIBUTE);
        }
        getVO();
    }

    private void getVO() {
        if (type.equals(ChatMsgUtil.Character.TYPE_FRIEND.getCode())) {
            getUserVO();
        } else if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            getGroupVO();
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            getChannelVO();
        } else {
            XToastUtils.toast("????????????!");
        }
    }

    private void getUserVO() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<User> responseData =
                        UserController.getUser("");
                if (responseData != null && responseData.isSuccess()) {
                    User user = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            objectNick = user.getNick();
                            img = user.getAvatar();
                            initQrBody();
                        }
                    });
                }
            }
        }).start();
    }

    private void getChannelVO() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<ChannelVO> responseData =
                        ChannelController.getChannelInfo(objectId.toString());
                if (responseData != null && responseData.isSuccess()) {
                    ChannelVO channelVO = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            objectNick = channelVO.getNick();
                            img = channelVO.getAvatar();
                            initQrBody();
                        }
                    });
                }
            }
        }).start();
    }

    private void getGroupVO() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<GroupVO> responseData =
                        GroupController.getGroupInfo(objectId.toString());
                if (responseData != null && responseData.isSuccess()) {
                    Group groupVO = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            objectNick = groupVO.getNick();
                            img = groupVO.getAvatar();
                            initQrBody();
                        }
                    });
                }
            }
        }).start();
    }

    private void initQrBody() {
        bodyNick.setText(objectNick);
        if (type.equals(ChatMsgUtil.Character.TYPE_FRIEND.getCode())) {
            bodyId.setText("Gimme???:" + objectId);
        } else if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            bodyId.setText("??????:" + objectId);
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            bodyId.setText("?????????:" + objectId);
        } else {
            XToastUtils.toast("????????????!");
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
        createQrCodeWithLogo(ImageUtils.getBitmap(R.mipmap.app_icon));
    }

    private void createQrCodeWithLogo(Bitmap logo) {
        QrVO qrVO = new QrVO();
        qrVO.setObjectId(objectId);
        qrVO.setChatType(ChatMsgUtil.CHARACTER_LIST[type].getName());
        qrVO.setTimestamp(new Date());
        qrVO.setShareUserId(GimmeApplication.getUserId());
        bodyQrCode.setImageBitmap(XQRCode.createQRCodeWithLogo(JsonUtil.toJson(qrVO), 400, 400, logo));
    }
}