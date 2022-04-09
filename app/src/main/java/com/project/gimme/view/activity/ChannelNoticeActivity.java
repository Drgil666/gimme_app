package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.project.gimme.R;
import com.project.gimme.controller.ChannelNoticeController;
import com.project.gimme.controller.ChatMsgController;
import com.project.gimme.pojo.ChatMsg;
import com.project.gimme.pojo.vo.ChatMsgVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.FileUtil;
import com.project.gimme.utils.MsgTypeUtil;
import com.project.gimme.utils.XToastUtils;
import com.project.gimme.view.adpter.ChannelNoticeAdapter;
import com.project.gimme.view.adpter.EmojiAdapter;
import com.project.gimme.view.adpter.ExtraOptionAdapter;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;
import com.xuexiang.xutil.app.IntentUtils;

import org.apache.commons.lang3.StringUtils;

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
    @BindView(R.id.channel_notice_bottom_edit_emoji)
    ImageView emojiButton;
    @BindView(R.id.channel_notice_bottom_edit_add)
    ImageView addButton;
    @BindView(R.id.channel_notice_bottom_below_layout)
    RelativeLayout bottomBelowLayout;
    @BindView(R.id.channel_notice_bottom_below_listview)
    GridView bottomBelowGridView;
    @BindView(R.id.channel_notice_bottom_edit_text)
    MultiLineEditText chatBottomEditText;
    private Integer currentBottomBelow = 0;
    private static final Integer EMOJI_GRIDVIEW = 0;
    private static final Integer EXTRA_OPTION_GRIDVIEW = 1;
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
        initChatBottom();
        initChatBottomBelow();
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

    private void initChatBottom() {
        chatBottomEditText.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //System.out.println(chatBottomEditText.getContentText());
            }
        });
        chatBottomEditText.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                    && v.getText() != null
                    && event.getAction() == KeyEvent.ACTION_DOWN) {
//                System.out.println("这里是监听回车事件->" + chatBottomEditText.getContentText());
                if (!StringUtils.isEmpty(chatBottomEditText.getContentText())) {
                    ChatMsg chatMsg = new ChatMsg();
                    chatMsg.setText(chatBottomEditText.getContentText());
                    chatMsg.setMsgType(MsgTypeUtil.MsgType.TYPE_TEXT.getCode());
                    chatMsg.setObjectId(channelNoticeId);
                    chatMsg.setType(ChatMsgUtil.Character.TYPE_CHANNEL.getName());
                    createChatMsg(chatMsg);
                    chatBottomEditText.setContentText(null);
                }
            }
            return true;
        });
    }

    private void initEmojiGridView() {
        currentBottomBelow = EMOJI_GRIDVIEW;
        bottomBelowLayout.setVisibility(View.VISIBLE);
        bottomBelowGridView.setNumColumns(7);
        chatBottomEditText.getEditText().clearFocus();
        bottomBelowGridView.setHorizontalSpacing(10);
        bottomBelowGridView.setVerticalSpacing(10);
        bottomBelowGridView.setAdapter(new EmojiAdapter(this));
        bottomBelowGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String emoji = (String) bottomBelowGridView.getAdapter().getItem(position);
                String text = chatBottomEditText.getEditText().getText().toString();
                text += emoji;
                chatBottomEditText.getEditText().setText(text);
            }
        });
    }

    private void initExtraOptionGridView() {
        currentBottomBelow = EXTRA_OPTION_GRIDVIEW;
        bottomBelowLayout.setVisibility(View.VISIBLE);
        bottomBelowGridView.setNumColumns(4);
        chatBottomEditText.getEditText().clearFocus();
        bottomBelowGridView.setHorizontalSpacing(10);
        bottomBelowGridView.setVerticalSpacing(10);
        bottomBelowGridView.setAdapter(new ExtraOptionAdapter(this));
        bottomBelowGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ExtraOptionAdapter.ExtraOptionItem extraOptionItem = (ExtraOptionAdapter.ExtraOptionItem) bottomBelowGridView.getAdapter().getItem(position);
                System.out.println(extraOptionItem.getText());
                switch (position) {
                    case 0: {
                        //发送图片
                        Intent intent = IntentUtils.getDocumentPickerIntent(IntentUtils.DocumentType.IMAGE);
                        startActivityForResult(intent, MsgTypeUtil.MsgType.TYPE_PIC.getCode());
                        break;
                    }
                    case 1: {
                        Intent intent = IntentUtils.getDocumentPickerIntent(IntentUtils.DocumentType.ANY);
                        startActivityForResult(intent, MsgTypeUtil.MsgType.TYPE_FILE.getCode());
                        //发送文件
                        break;
                    }
                }
            }
        });
    }

    private void initChatBottomBelow() {
        emojiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomBelowLayout.getVisibility() == View.GONE) {
                    Glide.with(mContext).load(R.mipmap.emoji_select).into(emojiButton);
                    Glide.with(mContext).load(R.mipmap.add_plus_round).into(addButton);
                    initEmojiGridView();
                } else {
                    if (currentBottomBelow.equals(EMOJI_GRIDVIEW)) {
                        bottomBelowLayout.setVisibility(View.GONE);
                        Glide.with(mContext).load(R.mipmap.emoji).into(emojiButton);
                        Glide.with(mContext).load(R.mipmap.add_plus_round).into(addButton);
                    } else {
                        Glide.with(mContext).load(R.mipmap.emoji_select).into(emojiButton);
                        Glide.with(mContext).load(R.mipmap.add_plus_round).into(addButton);
                        initEmojiGridView();
                    }
                }
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomBelowLayout.getVisibility() == View.GONE) {
                    Glide.with(mContext).load(R.mipmap.emoji).into(emojiButton);
                    Glide.with(mContext).load(R.mipmap.add_plus_round_select).into(addButton);
                    initExtraOptionGridView();
                } else {
                    if (currentBottomBelow.equals(EXTRA_OPTION_GRIDVIEW)) {
                        bottomBelowLayout.setVisibility(View.GONE);
                        Glide.with(mContext).load(R.mipmap.emoji).into(emojiButton);
                        Glide.with(mContext).load(R.mipmap.add_plus_round).into(addButton);
                    } else {
                        Glide.with(mContext).load(R.mipmap.emoji).into(emojiButton);
                        Glide.with(mContext).load(R.mipmap.add_plus_round_select).into(addButton);
                        initExtraOptionGridView();
                    }
                }
            }
        });
    }

    private void createChatMsg(ChatMsg chatMsg) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<ChatMsgVO> responseData = ChatMsgController.createChatMsg(chatMsg);
                if (responseData != null && responseData.isSuccess()) {
                    ChatMsgVO chatMsgVO = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            chatMsgVOList.add(chatMsgVO);
                            getChatMsgVoList();
                        }
                    });
                }
            }
        }).start();
    }
}