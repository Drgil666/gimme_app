package com.project.gimme.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.ChannelController;
import com.project.gimme.controller.ChannelUserController;
import com.project.gimme.controller.ChatFileInfoController;
import com.project.gimme.controller.GroupController;
import com.project.gimme.controller.GroupUserController;
import com.project.gimme.pojo.ChannelUser;
import com.project.gimme.pojo.GroupUser;
import com.project.gimme.pojo.vo.ChannelVO;
import com.project.gimme.pojo.vo.GroupVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.pojo.vo.UserVO;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.FileUtil;
import com.project.gimme.utils.MsgTypeUtil;
import com.project.gimme.utils.UserUtil;
import com.project.gimme.utils.XToastUtils;
import com.project.gimme.view.adpter.OtherInfoAdapter;
import com.xuexiang.xui.widget.dialog.DialogLoader;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;
import com.xuexiang.xutil.app.IntentUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

/**
 * @author 25741
 */
@SuppressLint("NonConstantResourceId")
public class OtherInformationActivity extends SwipeBackActivity {
    @BindView(R.id.other_information_top_info_icon)
    ImageView topInfoIcon;
    private Integer type;
    private Integer objectId;
    @BindView(R.id.other_information_imageview)
    ImageView imageView;
    @BindView(R.id.other_information_top_info_nick)
    TextView topInfoNick;
    private final Context mContext = this;
    @BindView(R.id.other_information_info_top_left_button)
    ImageView topLeftButton;
    @BindView(R.id.other_information_member_right_text)
    TextView memberRightText;
    private GroupVO groupVO = new GroupVO();
    private ChannelVO channelVO = new ChannelVO();
    Handler handler = new Handler();
    @BindView(R.id.other_information_introduction_left_description)
    TextView introductionDescription;
    private List<UserVO> userVOList = new ArrayList<>();
    @BindView(R.id.other_information_member_gridview)
    GridView memberGridView;
    @BindView(R.id.other_information_info_top_text)
    TextView topBarText;
    @BindView(R.id.other_information_member_layout)
    RelativeLayout memberLayout;
    private Boolean isJoin = false;
    @BindView(R.id.other_information_bottom_add)
    Button addButton;
    @BindView(R.id.other_information_bottom_chat)
    Button chatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_information);
        ButterKnife.bind(this);
        getType();
        initTopBar();
        initInfoLayout();
        initImageView();
        initMemberLayout();
    }

    private void initTopBar() {
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            topBarText.setText("????????????");
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            topBarText.setText("????????????");
        } else {
            XToastUtils.toast("????????????!");
        }
        topLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MsgTypeUtil.MsgType.TYPE_FILE.getCode()) {
            if (data != null) {
                Uri uri = data.getData();
                if (FileUtil.getRealPathFromUri(this, uri) != null) {
                    //???uri?????????????????????????????????file??????
                    File file = new File(FileUtil.getRealPathFromUri(this, uri));
                    uploadFile(file, ChatMsgUtil.CHARACTER_LIST[type].getName(), objectId);
                }
            }
        }
    }

    private void uploadFile(File file, String type, Integer objectId) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ChatFileInfoController.uploadAvatar(file, type, objectId);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.callOnClick();
                        Glide.with(mContext)
                                .load(file)
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                .into(topInfoIcon);
                        Glide.with(mContext)
                                .load(file)
                                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                .into(imageView);
                        XToastUtils.toast("??????????????????!");
                    }
                });
            }
        }).start();
    }

    private void initImageView() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.alpha1to0));
                imageView.setVisibility(View.GONE);
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new BottomSheet.BottomListSheetBuilder(mContext)
                        .addItem("???????????????")
                        .addItem("????????????")
                        .setIsCenter(true)
                        .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                            dialog.dismiss();
                            authorize();
                            switch (position) {
                                case 0: {
                                    FileUtil.saveImageToGallery(mContext, ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                                    XToastUtils.toast("????????????!");
                                    break;
                                }
                                case 1: {
                                    Intent intent = IntentUtils.getDocumentPickerIntent(IntentUtils.DocumentType.IMAGE);
                                    startActivityForResult(intent, MsgTypeUtil.MsgType.TYPE_FILE.getCode());
                                }
                            }
                        })
                        .build()
                        .show();
                return true;
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
        isJoin = bundle.getBoolean(BundleUtil.IS_JOINED_ATTRIBUTE);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initInfoLayout() {
        topInfoIcon.setOnTouchListener((view, motionEvent) -> {
            imageView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.alpha0to1));
            imageView.setVisibility(View.VISIBLE);
            return true;
            //??????return true??????,onClick????????????????????????!
        });
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            getGroupVO(objectId);
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            getChannelVO(objectId);
        } else {
            XToastUtils.toast("????????????!");
        }
    }

    private void initMemberLayout() {
        getUserVoList(type, objectId);
        memberLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogLoader.getInstance()
                        .showTipDialog(mContext,
                                "??????",
                                "???????????????/??????!",
                                "??????");
            }
        });
        memberGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DialogLoader.getInstance()
                        .showTipDialog(mContext,
                                "??????",
                                "???????????????/??????!",
                                "??????");
            }
        });
    }

    private void getUserVoList(Integer type, Integer objectId) {
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    ResponseData<List<UserVO>> responseData =
                            GroupController.getGroupMemberList(objectId.toString(), 10);
                    if (responseData != null && responseData.isSuccess()) {
                        userVOList = responseData.getData();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                memberGridView.setAdapter(new OtherInfoAdapter(mContext, userVOList));
                            }
                        });
                    }
                }
            }).start();
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    ResponseData<List<UserVO>> responseData =
                            ChannelController.getChannelMemberList(objectId.toString(), 10);
                    if (responseData != null && responseData.isSuccess()) {
                        userVOList = responseData.getData();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                memberGridView.setAdapter(new OtherInfoAdapter(mContext, userVOList));
                            }
                        });
                    }
                }
            }).start();
        }
    }

    private void initBottomLayout() {
        if (isJoin) {
            chatButton.setVisibility(View.VISIBLE);
            addButton.setVisibility(View.GONE);
        } else {
            chatButton.setVisibility(View.GONE);
            addButton.setVisibility(View.VISIBLE);
        }
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, type);
                bundle.putInt(BundleUtil.OBJECT_ID_ATTRIBUTE, objectId);
                Intent intent = new Intent(mContext, ChatActivity.class).putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chatButton.setVisibility(View.VISIBLE);
                addButton.setVisibility(View.GONE);
                if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
                    createGroupUser(groupVO.getId(), GimmeApplication.getUserId());
                } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
                    createChannelUser(channelVO.getId(), GimmeApplication.getUserId());
                }
                XToastUtils.toast("????????????!");
            }
        });
    }

    private void createGroupUser(Integer groupId, Integer userId) {
        GroupUser groupUser = new GroupUser();
        groupUser.setGroupId(groupId);
        groupUser.setUserId(userId);
        groupUser.setType(UserUtil.GroupCharacter.TYPE_GROUP_USER.getName());
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                GroupUserController.createGroupUser(groupUser);
            }
        }).start();
    }

    private void createChannelUser(Integer channelId, Integer userId) {
        ChannelUser channelUser = new ChannelUser();
        channelUser.setChannelId(channelId);
        channelUser.setUserId(userId);
        channelUser.setType(UserUtil.ChannelCharacter.TYPE_CHANNEL_USER.getName());
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ChannelUserController.createChannelUser(channelUser);
            }
        }).start();
    }

    private void getGroupVO(Integer objectId) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<GroupVO> responseData =
                        GroupController.getGroupInfo(objectId.toString());
                if (responseData != null && responseData.isSuccess()) {
                    groupVO = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            memberRightText.setText("??????" + groupVO.getTotalCount() + "????????????");
                            topInfoNick.setText(groupVO.getNick());
                            isJoin = groupVO.getMyNote() != null;
                            introductionDescription.setText(groupVO.getDescription());
                            Glide.with(mContext)
                                    .load(GimmeApplication.getImageUrl(groupVO.getAvatar()))
                                    .error(R.mipmap.default_icon)
                                    .into(imageView);
                            Glide.with(mContext)
                                    .load(GimmeApplication.getImageUrl(groupVO.getAvatar()))
                                    .error(R.mipmap.default_icon)
                                    .into(topInfoIcon);
                            initBottomLayout();
                        }
                    });
                }
            }
        }).start();
    }

    private void getChannelVO(Integer objectId) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<ChannelVO> responseData =
                        ChannelController.getChannelInfo(objectId.toString());
                if (responseData != null && responseData.isSuccess()) {
                    channelVO = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            memberRightText.setText("??????" + channelVO.getTotalCount() + "???????????????");
                            topInfoNick.setText(channelVO.getNick());
                            isJoin = groupVO.getMyNote() != null;
                            introductionDescription.setText(channelVO.getDescription());
                            Glide.with(mContext)
                                    .load(GimmeApplication.getImageUrl(channelVO.getAvatar()))
                                    .error(R.mipmap.default_icon)
                                    .into(imageView);
                            Glide.with(mContext)
                                    .load(GimmeApplication.getImageUrl(channelVO.getAvatar()))
                                    .error(R.mipmap.default_icon)
                                    .into(topInfoIcon);
                            initBottomLayout();
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initInfoLayout();
        initMemberLayout();
    }
}