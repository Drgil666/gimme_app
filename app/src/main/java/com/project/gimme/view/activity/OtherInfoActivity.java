package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.reflect.TypeToken;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.ChannelController;
import com.project.gimme.controller.ChannelUserController;
import com.project.gimme.controller.GroupController;
import com.project.gimme.controller.GroupNoticeController;
import com.project.gimme.controller.GroupUserController;
import com.project.gimme.pojo.ChannelUser;
import com.project.gimme.pojo.GroupNotice;
import com.project.gimme.pojo.GroupUser;
import com.project.gimme.pojo.vo.ChannelVO;
import com.project.gimme.pojo.vo.GroupVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.pojo.vo.UserVO;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.ContactsUtil;
import com.project.gimme.utils.InfoTypeUtil;
import com.project.gimme.utils.JsonUtil;
import com.project.gimme.utils.UserUtil;
import com.project.gimme.utils.XToastUtils;
import com.project.gimme.view.adpter.OtherInfoAdapter;
import com.xuexiang.xutil.common.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

/**
 * @author 25741
 */
@SuppressLint("NonConstantResourceId")
public class OtherInfoActivity extends SwipeBackActivity {
    private final Integer height = GimmeApplication.getHeight();
    private Integer type;
    private Integer objectId;
    @BindView(R.id.other_info_top_text)
    TextView tabText;
    @BindView(R.id.other_info_top_left_button)
    ImageView leftButton;
    @BindView(R.id.other_info_top_bar)
    RelativeLayout topInfoBar;
    private GroupVO groupVO = new GroupVO();
    private ChannelVO channelVO = new ChannelVO();
    @BindView(R.id.fragment_other_info_top_bar_icon)
    ImageView topBarIcon;
    @BindView(R.id.fragment_other_info_top_nick)
    TextView topBarNick;
    @BindView(R.id.fragment_other_info_top_description)
    TextView topBarDescription;
    @BindView(R.id.fragment_other_info_member_layout)
    RelativeLayout memberLayout;
    @BindView(R.id.fragment_other_info_member_left_text)
    TextView memberLeft;
    @BindView(R.id.fragment_other_info_member_right_text)
    TextView memberRight;
    @BindView(R.id.fragment_other_info_member_gridview)
    GridView gridView;
    private List<UserVO> userVOList = new ArrayList<>();
    @BindView(R.id.fragment_other_info_introduction_left_text)
    TextView introductionIdLeft;
    @BindView(R.id.fragment_other_info_introduction_right_text)
    TextView introductionIdRight;
    @BindView(R.id.fragment_other_info_introduction_id_layout)
    RelativeLayout introductionIdLayout;
    @BindView(R.id.fragment_other_info_introduction_group_notice_nick)
    TextView introductionGroupNoticeLeftNick;
    @BindView(R.id.fragment_other_info_introduction_group_notice_text)
    TextView introductionGroupNoticeLeftText;
    @BindView(R.id.fragment_other_info_introduction_group_notice_layout)
    RelativeLayout introductionGroupNoticeLayout;
    Handler handler = new Handler();
    @BindView(R.id.fragment_other_info_introduction_group_file_layout)
    RelativeLayout introductionGroupFileLayout;
    @BindView(R.id.fragment_other_info_my_layout)
    RelativeLayout myLayout;
    @BindView(R.id.fragment_other_info_my_chat_msg)
    RelativeLayout myChatMsg;
    @BindView(R.id.fragment_other_info_my_note)
    RelativeLayout myNote;
    @BindView(R.id.fragment_other_info_introduction_note_right_text)
    EditText myNoteRightText;
    @BindView(R.id.fragment_other_info_introduction_note_left_text)
    TextView myNoteLeftText;
    @BindView(R.id.fragment_other_info_exit_button)
    Button exitButton;
    private GroupNotice groupNotice = new GroupNotice();
    private Boolean isJoined;
    @BindView(R.id.fragment_other_info_top_bar)
    RelativeLayout topBar;
    private final Context mContext = this;
    private GroupUser groupUser = new GroupUser();
    private ChannelUser channelUser = new ChannelUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_info);
        ButterKnife.bind(this);
        initTopBar(0.1);
        getType();
        initTopBar();
        initMember();
        initGridView();
        initIntroduction();
        initMyLayout();
    }

    private void initTopBar(double size) {
        leftButton.setOnClickListener(view -> {
            finish();
        });
        topInfoBar.getLayoutParams().height = (int) Math.floor(height * size);
    }


    private void getType() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(BundleUtil.CHAT_TYPE_ATTRIBUTE);
        objectId = bundle.getInt(BundleUtil.OBJECT_ID_ATTRIBUTE);
        isJoined = bundle.getBoolean(BundleUtil.IS_JOINED_ATTRIBUTE);
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            groupVO = JsonUtil.fromJson(bundle.getString(BundleUtil.OBJECT_ATTRIBUTE), new TypeToken<GroupVO>() {
            }.getType());
            initGroupVO();
            initExitButton();
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            channelVO = JsonUtil.fromJson(bundle.getString(BundleUtil.OBJECT_ATTRIBUTE), new TypeToken<ChannelVO>() {
            }.getType());
            initChannelVO();
            initExitButton();
        }
        //System.out.println("type:" + type + " object_id:" + objectId + " is joined:" + isJoined);
    }

    private void initTopBar() {
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            getGroupVO(objectId);
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            getChannelVO(objectId);
        } else {
            XToastUtils.toast("类型错误!");
        }
        topBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, type);
                bundle.putInt(BundleUtil.OBJECT_ID_ATTRIBUTE, objectId);
                bundle.putBoolean(BundleUtil.IS_JOINED_ATTRIBUTE, true);
                Intent intent = new Intent(mContext, OtherInformationActivity.class).putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initMember() {
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            memberLeft.setText("群聊成员");
            getUserVoList(type, objectId);
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            memberLeft.setText("频道成员");
            getUserVoList(type, objectId);
        } else {
            XToastUtils.toast("类型错误!");
        }
        memberLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, type);
                bundle.putInt(BundleUtil.OBJECT_ID_ATTRIBUTE, objectId);
                Intent intent = new Intent(mContext, OtherMemberListActivity.class).putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void initGridView() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                if (id == -1) {
                    bundle.putInt(BundleUtil.CONTACTS_LIST_TYPE_ATTRIBUTE, ContactsUtil.ContactType.TYPE_INVITATION.getCode());
                    bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, type);
                    bundle.putInt(BundleUtil.CHAT_MSG_ID_ATTRIBUTE, objectId);
                    Intent intent = new Intent(mContext, FriendListActivity.class).putExtras(bundle);
                    startActivity(intent);
                } else {
                    if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
                        bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, InfoTypeUtil.Character.TYPE_CHANNEL_MEMBER.getCode());

                    } else {
                        bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, InfoTypeUtil.Character.TYPE_GROUP_MEMBER.getCode());
                    }
                    bundle.putString(BundleUtil.OTHER_ID_ATTRIBUTE, objectId.toString());
                    bundle.putInt(BundleUtil.OBJECT_ID_ATTRIBUTE, userVOList.get(position).getId());
                    bundle.putBoolean(BundleUtil.IS_JOINED_ATTRIBUTE, false);
                    bundle.putString(BundleUtil.OBJECT_ATTRIBUTE, JsonUtil.toJson(userVOList.get(position)));
                    Intent intent = new Intent(mContext, FriendInfoActivity.class).putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
    }

    private void initIntroduction() {
        if (!isJoined) {
            introductionGroupFileLayout.setVisibility(View.GONE);
        }
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            introductionIdLeft.setText("群聊号与二维码");
            introductionGroupNoticeLayout.setVisibility(View.VISIBLE);
            introductionGroupFileLayout.setVisibility(View.VISIBLE);
            introductionGroupNoticeLeftNick.setText("群公告");
            introductionGroupNoticeLeftText.setText(groupNotice.getText());
            getGroupNotice(objectId);
            introductionGroupFileLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, type);
                    bundle.putInt(BundleUtil.OBJECT_ID_ATTRIBUTE, objectId);
                    Intent intent = new Intent(mContext, ChatFileActivity.class).putExtras(bundle);
                    startActivity(intent);
                }
            });
            introductionIdLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, type);
                    bundle.putInt(BundleUtil.OBJECT_ID_ATTRIBUTE, objectId);
                    bundle.putString(BundleUtil.USER_AVATAR_ATTRIBUTE, groupVO.getAvatar());
                    bundle.putString(BundleUtil.OBJECT_NICK_ATTRIBUTE, groupVO.getNick());
                    Intent intent = new Intent(mContext, QrActivity.class).putExtras(bundle);
                    startActivity(intent);
                }
            });
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            introductionIdLeft.setText("频道号与二维码");
            introductionGroupNoticeLayout.setVisibility(View.GONE);
            introductionGroupFileLayout.setVisibility(View.GONE);
            introductionIdLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, type);
                    bundle.putInt(BundleUtil.OBJECT_ID_ATTRIBUTE, objectId);
                    bundle.putString(BundleUtil.USER_AVATAR_ATTRIBUTE, channelVO.getAvatar());
                    bundle.putString(BundleUtil.OBJECT_NICK_ATTRIBUTE, channelVO.getNick());
                    Intent intent = new Intent(mContext, QrActivity.class).putExtras(bundle);
                    startActivity(intent);
                }
            });
        } else {
            XToastUtils.toast("类型错误!");
        }
    }

    private void getGroupNotice(Integer objectId) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<GroupNotice> responseData =
                        GroupNoticeController.getGroupNotice(objectId.toString(), "");
                if (responseData != null && responseData.isSuccess()) {
                    groupNotice = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            introductionGroupNoticeLeftText.setText(groupNotice.getText());
                        }
                    });
                }
            }
        }).start();
    }

    private void initGroupVO() {
        introductionIdRight.setText(groupVO.getId().toString());
        memberRight.setText("查看" + groupVO.getTotalCount() + "名群成员");
        topBarNick.setText(groupVO.getNick());
        topBarDescription.setText(groupVO.getDescription());
        myNoteRightText.setText(groupVO.getMyNote());
        Glide.with(this)
                .load(GimmeApplication.getImageUrl(groupVO.getAvatar()))
                .error(R.mipmap.default_icon)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(topBarIcon);
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
                            initGroupVO();
                            initExitButton();
                        }
                    });
                }
            }
        }).start();
    }

    private void initChannelVO() {
        introductionIdRight.setText(channelVO.getId().toString());
        memberRight.setText("查看" + channelVO.getTotalCount() + "名频道成员");
        topBarNick.setText(channelVO.getNick());
        topBarDescription.setText(channelVO.getDescription());
        myNoteRightText.setText(channelVO.getMyNote());
        Glide.with(this)
                .load(GimmeApplication.getImageUrl(channelVO.getAvatar()))
                .error(R.mipmap.default_icon)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(topBarIcon);
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
                            initChannelVO();
                            initExitButton();
                        }
                    });
                }
            }
        }).start();
    }

    private void getUserVoList(Integer type, Integer objectId) {
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    ResponseData<List<UserVO>> responseData =
                            GroupController.getGroupMemberList(objectId.toString(), 9);
                    if (responseData != null && responseData.isSuccess()) {
                        userVOList = responseData.getData();
                        UserVO userVO = new UserVO();
                        userVO.setNick("邀请");
                        userVO.setId(-1);
                        userVOList.add(userVO);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                gridView.setAdapter(new OtherInfoAdapter(mContext, userVOList));
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
                            ChannelController.getChannelMemberList(objectId.toString(), 9);
                    if (responseData != null && responseData.isSuccess()) {
                        userVOList = responseData.getData();
                        UserVO userVO = new UserVO();
                        userVO.setNick("邀请");
                        userVO.setId(-1);
                        userVOList.add(userVO);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                gridView.setAdapter(new OtherInfoAdapter(mContext, userVOList));
                            }
                        });
                    }
                }
            }).start();
        }
    }

    private void initMyLayout() {
        if (!isJoined) {
            myLayout.setVisibility(View.GONE);
        }
        myChatMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, type);
                bundle.putInt(BundleUtil.OBJECT_ID_ATTRIBUTE, objectId);
                Intent intent = new Intent(mContext, ChatMsgActivity.class).putExtras(bundle);
                startActivity(intent);
            }
        });
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            myNoteLeftText.setText("我的群昵称");
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            myNoteLeftText.setText("我的频道昵称");
        }
        myNoteRightText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!StringUtils.isEmpty(myNoteRightText.getText().toString())) {
                    if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
                        updateGroupUser(groupVO.getId());
                    } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
                        updateChannelUser(channelVO.getId());
                    }
                } else {
                    XToastUtils.toast("备注不可为空!");
                }
            }
        });
    }

    private void updateGroupUser(Integer groupId) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<GroupUser> responseData =
                        GroupUserController.getGroupUser(groupId);
                if (responseData != null && responseData.isSuccess()) {
                    groupUser = responseData.getData();
                    groupUser.setGroupNick(myNoteRightText.getText().toString());
                    GroupUserController.updateGroupUser(groupUser);
                }
            }
        }).start();
    }

    private void updateChannelUser(Integer channelId) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<ChannelUser> responseData =
                        ChannelUserController.getChannelUser(channelId);
                if (responseData != null && responseData.isSuccess()) {
                    channelUser = responseData.getData();
                    channelUser.setChannelNick(myNoteRightText.getText().toString());
                    ChannelUserController.updateChannelUser(channelUser);
                }
            }
        }).start();
    }

    private void initExitButton() {
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            if (groupVO.getMyPriority().equals(UserUtil.GroupCharacter.TYPE_GROUP_USER.getName())) {
                exitButton.setText("退出群聊");
                exitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteGroupUser(groupVO.getId());
                    }
                });
            } else {
                exitButton.setText("解散群聊");
                exitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteGroup(groupVO.getId());
                    }
                });
            }
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            if (channelVO.getMyPriority().equals(UserUtil.ChannelCharacter.TYPE_CHANNEL_USER.getName())) {
                exitButton.setText("退出频道");
                exitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteChannelUser(channelVO.getId());
                    }
                });
            } else {
                exitButton.setText("解散频道");
                exitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteChannel(channelVO.getId());
                    }
                });
            }
        }
    }

    private void deleteGroupUser(Integer groupId) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                GroupUserController.deleteGroupUser(groupId, new ArrayList<>());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }).start();
    }

    private void deleteGroup(Integer groupId) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                GroupController.deleteGroup(groupId);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }).start();
    }

    private void deleteChannelUser(Integer channelId) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ChannelUserController.deleteChannelUser(channelId, new ArrayList<>());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }).start();
    }

    private void deleteChannel(Integer channelId) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ChannelController.deleteChannel(channelId);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getType();
        initMember();
    }
}