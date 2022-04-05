package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.ChannelController;
import com.project.gimme.controller.GroupController;
import com.project.gimme.pojo.vo.ChannelVO;
import com.project.gimme.pojo.vo.GroupVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.pojo.vo.UserVO;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.FileUtil;
import com.project.gimme.utils.XToastUtils;
import com.project.gimme.view.adpter.OtherInfoAdapter;
import com.xuexiang.xui.widget.dialog.DialogLoader;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;

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
        initBottomLayout();
    }

    private void initTopBar() {
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            topBarText.setText("群聊资料");
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            topBarText.setText("频道资料");
        } else {
            XToastUtils.toast("类型错误!");
        }
        topLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
                        .addItem("保存到相册")
                        .setIsCenter(true)
                        .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                            dialog.dismiss();
                            FileUtil.saveImageToGallery(mContext, ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                            XToastUtils.toast("保存成功!");
                        })
                        .build()
                        .show();
                return true;
            }
        });
    }

    private void getType() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(BundleUtil.CHAT_TYPE_ATTRIBUTE);
        objectId = bundle.getInt(BundleUtil.OBJECT_ID_ATTRIBUTE);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initInfoLayout() {
        topInfoIcon.setOnTouchListener((view, motionEvent) -> {
            imageView.setVisibility(View.VISIBLE);
            return true;
            //如果return true的话,onClick的事件就不会触发!
        });
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            getGroupVO(objectId);
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            getChannelVO(objectId);
        } else {
            XToastUtils.toast("类型错误!");
        }
    }

    private void initMemberLayout() {
        getUserVoList(type, objectId);
        memberLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogLoader.getInstance()
                        .showTipDialog(mContext,
                                "提示",
                                "请先加入群/频道!",
                                "确定");
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
                            memberRightText.setText("查看" + groupVO.getTotalCount() + "名群成员");
                            topInfoNick.setText(groupVO.getNick());
                            introductionDescription.setText(groupVO.getDescription());
                            Glide.with(mContext)
                                    .load(GimmeApplication.getImageUrl(groupVO.getAvatar()))
                                    .error(R.mipmap.default_icon)
                                    .into(imageView);
                            Glide.with(mContext)
                                    .load(GimmeApplication.getImageUrl(groupVO.getAvatar()))
                                    .error(R.mipmap.default_icon)
                                    .into(topInfoIcon);
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
                            memberRightText.setText("查看" + channelVO.getTotalCount() + "名频道成员");
                            topInfoNick.setText(channelVO.getNick());
                            introductionDescription.setText(channelVO.getDescription());
                            Glide.with(mContext)
                                    .load(GimmeApplication.getImageUrl(channelVO.getAvatar()))
                                    .error(R.mipmap.default_icon)
                                    .into(imageView);
                            Glide.with(mContext)
                                    .load(GimmeApplication.getImageUrl(channelVO.getAvatar()))
                                    .error(R.mipmap.default_icon)
                                    .into(topInfoIcon);
                        }
                    });
                }
            }
        }).start();
    }
}