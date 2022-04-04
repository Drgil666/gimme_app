package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ListView;

import com.project.gimme.R;
import com.project.gimme.controller.ChannelNoticeController;
import com.project.gimme.pojo.vo.ChatMsgVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.ContactsUtil;
import com.project.gimme.utils.FileUtil;
import com.project.gimme.utils.XToastUtils;
import com.project.gimme.view.adpter.ChannelNoticeAdapter;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

/**
 * @author 25741
 */
@SuppressLint("NonConstantResourceId")
public class ChannelNoticeActivity extends SwipeBackActivity {
    private Integer channelNoticeId;
    private List<ChatMsgVO> chatMsgVOList;
    @BindView(R.id.channel_notice_top_left_button)
    ImageView topLeftButton;
    Handler handler = new Handler();
    ChannelNoticeAdapter channelNoticeAdapter;
    private final Context mContext = this;
    @BindView(R.id.channel_notice_list_view)
    ListView channelNoticeListView;
    @BindView(R.id.channel_notice_imageview)
    ImageView imageView;
    public static Integer chatMsgId = null;

    public static void setChatMsgId(Integer id) {
        chatMsgId = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_notice);
        ButterKnife.bind(this);
        getChannelNoticeId();
        getChatMsgVoList();
        initTopBar();
        initImageView();
    }

    private void initImageView() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.alpha1to0));
                imageView.setVisibility(View.GONE);
                getChatMsgVoList();
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new BottomSheet.BottomListSheetBuilder(mContext)
                        .addItem("保存到相册")
                        .addItem("转发")
                        .setIsCenter(true)
                        .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                            dialog.dismiss();
//                            XToastUtils.toast("Item " + (position + 1));
                            switch (position) {
                                case 0: {
                                    FileUtil.saveImageToGallery(mContext, ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                                    XToastUtils.toast("保存成功!");
                                    break;
                                }
                                case 1: {
                                    Bundle bundle = new Bundle();
                                    bundle.putInt(BundleUtil.CONTACTS_LIST_TYPE_ATTRIBUTE, ContactsUtil.ContactType.TYPE_TRANSMIT.getCode());
                                    bundle.putInt(BundleUtil.CHAT_MSG_ID_ATTRIBUTE, chatMsgId);
                                    Intent intent = new Intent(mContext, FriendListActivity.class).putExtras(bundle);
                                    startActivity(intent);
                                    break;
                                }
                                default:
                                    break;
                            }
                        })
                        .build()
                        .show();
                return true;
            }
        });
    }

    private void getChannelNoticeId() {
        Bundle bundle = getIntent().getExtras();
        channelNoticeId = bundle.getInt(BundleUtil.CHANNEL_NOTICE_ID_ATTRIBUTE);
        System.out.println("channelNoticeId:" + channelNoticeId);
    }

    private void initTopBar() {
        topLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getChatMsgVoList() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<List<ChatMsgVO>> responseData =
                        ChannelNoticeController.getChannelNoticeInfo(channelNoticeId);
                if (responseData != null && responseData.isSuccess()) {
                    chatMsgVOList = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            channelNoticeAdapter = new ChannelNoticeAdapter(mContext, chatMsgVOList, ChatMsgUtil.Character.TYPE_CHANNEL_NOTICE.getCode());
                            channelNoticeListView.setAdapter(channelNoticeAdapter);
                            channelNoticeListView.setSelection(chatMsgVOList.size() - 1);
                        }
                    });
                }
            }
        }).start();
    }
}