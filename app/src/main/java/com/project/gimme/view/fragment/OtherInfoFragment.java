package com.project.gimme.view.fragment;

import static com.project.gimme.utils.BundleUtil.CHAT_TYPE_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_ID_ATTRIBUTE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.project.gimme.R;
import com.project.gimme.controller.ChannelController;
import com.project.gimme.controller.GroupController;
import com.project.gimme.controller.GroupNoticeController;
import com.project.gimme.pojo.GroupNotice;
import com.project.gimme.pojo.vo.ChannelVO;
import com.project.gimme.pojo.vo.GroupVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.pojo.vo.UserVO;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.XToastUtils;
import com.project.gimme.view.activity.ChatFileActivity;
import com.project.gimme.view.activity.ParamActivity;
import com.project.gimme.view.activity.QrActivity;
import com.project.gimme.view.adpter.OtherInfoAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lombok.SneakyThrows;

/**
 * @author DrGilbert
 */
@SuppressLint("NonConstantResourceId")
public class OtherInfoFragment extends Fragment {
    private Integer type;
    private Integer objectId;
    private GroupVO groupVO = new GroupVO();
    private ChannelVO channelVO = new ChannelVO();
    @BindView(R.id.fragment_other_info_top_nick)
    TextView topBarNick;
    @BindView(R.id.fragment_other_info_top_description)
    TextView topBarDescription;
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
    TextView myNoteRightText;
    @BindView(R.id.fragment_other_info_introduction_note_left_text)
    TextView myNoteLeftText;
    private Unbinder unbinder;
    private GroupNotice groupNotice = new GroupNotice();
    private Boolean isJoined;
    @BindView(R.id.fragment_other_img)
    ImageView imageView;
    @BindView(R.id.fragment_other_info_top_bar_icon)
    ImageView topBarIcon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        getType();
        initTopBar();
        initMember();
        initIntroduction();
        initMyLayout();
        initImageView();
        return view;
    }

    private void initImageView() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.alpha1to0));
                imageView.setVisibility(View.GONE);
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    private void getType() {
        Bundle bundle = getActivity().getIntent().getExtras();
        type = bundle.getInt(CHAT_TYPE_ATTRIBUTE);
        objectId = bundle.getInt(OBJECT_ID_ATTRIBUTE);
        isJoined = bundle.getBoolean(BundleUtil.IS_JOINED_ATTRIBUTE);
        System.out.println("type:" + type + " object_id:" + objectId + " is joined:" + isJoined);
    }

    private void initTopBar() {
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            getGroupVO(objectId);
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            getChannelVO(objectId);
        } else {
            XToastUtils.toast("类型错误!");
        }
        topBarIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Picasso.with(getContext()).load(R.mipmap.default_icon).into(imageView);
                imageView.setVisibility(View.VISIBLE);
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
    }

    private void initIntroduction() {
        if (!isJoined) {
            introductionGroupFileLayout.setVisibility(View.GONE);
        }
        introductionIdLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(CHAT_TYPE_ATTRIBUTE, type);
                bundle.putInt(OBJECT_ID_ATTRIBUTE, objectId);
                Intent intent = new Intent(getActivity(), QrActivity.class).putExtras(bundle);
                startActivity(intent);
            }
        });
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
                    bundle.putInt(CHAT_TYPE_ATTRIBUTE, type);
                    bundle.putInt(OBJECT_ID_ATTRIBUTE, objectId);
                    Intent intent = new Intent(getActivity(), ChatFileActivity.class).putExtras(bundle);
                    startActivity(intent);
                }
            });
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            introductionIdLeft.setText("频道号与二维码");
            introductionGroupNoticeLayout.setVisibility(View.GONE);
            introductionGroupFileLayout.setVisibility(View.GONE);
        } else {
            XToastUtils.toast("类型错误!");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
                            introductionIdRight.setText(groupVO.getId().toString());
                            memberRight.setText("查看" + groupVO.getTotalCount() + "名群成员");
                            topBarNick.setText(groupVO.getNick());
                            topBarDescription.setText(groupVO.getDescription());
                            myNoteRightText.setText(groupVO.getMyNote());
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
                            introductionIdRight.setText(channelVO.getId().toString());
                            memberRight.setText("查看" + channelVO.getTotalCount() + "名频道成员");
                            topBarNick.setText(channelVO.getNick());
                            topBarDescription.setText(channelVO.getDescription());
                            myNoteRightText.setText(channelVO.getMyNote());
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
                                gridView.setAdapter(new OtherInfoAdapter(getContext(), userVOList));
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
                                gridView.setAdapter(new OtherInfoAdapter(getContext(), userVOList));
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
        myChatMsg.setOnClickListener(view -> System.out.println("click!"));
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            myNoteLeftText.setText("我的群昵称");
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            myNoteLeftText.setText("我的频道昵称");
        }
        myNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
                    bundle.putString(BundleUtil.PARAM_NAME_ATTRIBUTE, "群昵称");
                    bundle.putString(BundleUtil.PARAM_VALUE_ATTRIBUTE, groupVO.getMyNote());
                } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
                    bundle.putString(BundleUtil.PARAM_NAME_ATTRIBUTE, channelVO.getMyNote());
                }
                Intent intent = new Intent(getContext(), ParamActivity.class).putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}