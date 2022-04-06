package com.project.gimme.view.activity;

import static com.project.gimme.utils.BundleUtil.CHAT_TYPE_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.INFO_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_ID_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_NICK_ATTRIBUTE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.ChannelController;
import com.project.gimme.controller.ChannelNoticeController;
import com.project.gimme.controller.ChatFileInfoController;
import com.project.gimme.controller.ChatMsgController;
import com.project.gimme.controller.GroupController;
import com.project.gimme.controller.UserController;
import com.project.gimme.pojo.ChannelNotice;
import com.project.gimme.pojo.ChatFile;
import com.project.gimme.pojo.ChatMsg;
import com.project.gimme.pojo.vo.ChannelVO;
import com.project.gimme.pojo.vo.ChatMsgFileVO;
import com.project.gimme.pojo.vo.ChatMsgVO;
import com.project.gimme.pojo.vo.GroupVO;
import com.project.gimme.pojo.vo.RefreshVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.pojo.vo.UserVO;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.ContactsUtil;
import com.project.gimme.utils.FileUtil;
import com.project.gimme.utils.JsonUtil;
import com.project.gimme.utils.MsgTypeUtil;
import com.project.gimme.utils.XToastUtils;
import com.project.gimme.view.adpter.ChatMsgVoAdapter;
import com.project.gimme.view.adpter.EmojiAdapter;
import com.project.gimme.view.adpter.ExtraOptionAdapter;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;
import com.xuexiang.xutil.app.IntentUtils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

/**
 * @author DrGilbert
 */
@SuppressLint("NonConstantResourceId")
public class ChatActivity extends SwipeBackActivity {
    private Integer type;
    private Integer objectId;
    @BindView(R.id.chat_bottom_edit_text)
    MultiLineEditText chatBottomEditText;
    @BindView(R.id.chat_list_view)
    ListView chatListView;
    @BindView(R.id.chat_top_left_button)
    ImageView leftButton;
    @BindView(R.id.chat_top_right_button)
    ImageView rightButton;
    @BindView(R.id.chat_top_nick_text)
    TextView topNick;
    @BindView(R.id.chat_top_description_text)
    TextView topDescription;
    private List<ChatMsgVO> chatMsgVOList = new ArrayList<>();
    private ChatMsgVoAdapter chatMsgVoAdapter;
    Handler handler1 = new Handler();
    Handler handler2 = new Handler();
    final Context mContext = this;
    @BindView(R.id.chat_bottom_edit_emoji)
    ImageView emojiButton;
    @BindView(R.id.chat_bottom_edit_add)
    ImageView addButton;
    @BindView(R.id.chat_bottom_below_layout)
    RelativeLayout bottomBelowLayout;
    @BindView(R.id.chat_bottom_below_listview)
    GridView bottomBelowGridView;
    private Integer currentBottomBelow = 0;
    private static final Integer EMOJI_GRIDVIEW = 0;
    private static final Integer EXTRA_OPTION_GRIDVIEW = 1;
    @BindView(R.id.chat_imageview)
    ImageView imageView;
    public static Integer chatMsgId = null;
    private UserVO userVO = new UserVO();
    private GroupVO groupVO = new GroupVO();
    private ChannelVO channelVO = new ChannelVO();
    private String nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        initBundle();
        refresh();
        initTopBar();
        initChatListView();
        initChatBottom();
        initChatBottomBelow();
    }

    public static void setChatMsgId(Integer id) {
        chatMsgId = id;
    }

    private void initBundle() {
        Bundle bundle = this.getIntent().getExtras();
        objectId = bundle.getInt(OBJECT_ID_ATTRIBUTE);
        type = bundle.getInt(CHAT_TYPE_ATTRIBUTE);
        nick = bundle.getString(OBJECT_NICK_ATTRIBUTE);
        setTopNick(nick);
        //System.out.println("objectId:" + objectId + ",type:" + type + ",nick:" + nick);
    }

    private void refresh() {
        RefreshVO refreshVO = new RefreshVO();
        refreshVO.setChatType(ChatMsgUtil.CHARACTER_LIST[type].getName());
        refreshVO.setObjectId(objectId);
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ChatMsgController.refresh(refreshVO);
            }
        }).start();
    }

    private void getChatMessageList(Integer type, Integer objectId) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<List<ChatMsgVO>> responseData =
                        ChatMsgController.getChatMsgVoList(ChatMsgUtil.CHARACTER_LIST[type].getName(), objectId, "");
                if (responseData != null && responseData.isSuccess()) {
                    chatMsgVOList = responseData.getData();
                    handler1.post(new Runnable() {
                        @Override
                        public void run() {
                            chatMsgVoAdapter = new ChatMsgVoAdapter(mContext, chatMsgVOList, type);
                            chatListView.setAdapter(chatMsgVoAdapter);
                            chatListView.setSelection(chatMsgVOList.size() - 1);
                            chatMsgVoAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        }).start();
    }

    private void initTopBar() {
        Glide.with(this).load(R.mipmap.back).into(leftButton);
        leftButton.setOnClickListener(view -> {
            finish();
        });
        Glide.with(this).load(R.mipmap.info).into(rightButton);
        if (type.equals(ChatMsgUtil.Character.TYPE_FRIEND.getCode())) {
            rightButton.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putInt(CHAT_TYPE_ATTRIBUTE, type);
                bundle.putInt(OBJECT_ID_ATTRIBUTE, objectId);
                bundle.putBoolean(BundleUtil.IS_JOINED_ATTRIBUTE, true);
                bundle.putString(BundleUtil.OBJECT_ATTRIBUTE, JsonUtil.toJson(userVO));
                Intent intent = new Intent(this, FriendInfoActivity.class).putExtras(bundle);
                startActivity(intent);
            });
            userVO = JsonUtil.fromJson(this.getIntent().getExtras().getString(INFO_ATTRIBUTE), UserVO.class);
            if (userVO != null) {
                if (StringUtils.isEmpty(userVO.getNote())) {
                    setTopNick(userVO.getNick());
                } else {
                    setTopNick(userVO.getNote());
                }
                setTopDescription(userVO.getMotto());
            }
            getUserInfo(objectId);
        } else if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            rightButton.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putInt(CHAT_TYPE_ATTRIBUTE, type);
                bundle.putInt(OBJECT_ID_ATTRIBUTE, objectId);
                bundle.putBoolean(BundleUtil.IS_JOINED_ATTRIBUTE, true);
                bundle.putString(BundleUtil.OBJECT_ATTRIBUTE, JsonUtil.toJson(groupVO));
                Intent intent = new Intent(this, OtherInfoActivity.class).putExtras(bundle);
                startActivity(intent);
            });
            groupVO = JsonUtil.fromJson(this.getIntent().getExtras().getString(INFO_ATTRIBUTE), GroupVO.class);
            if (groupVO != null) {
                setTopNick(groupVO.getNick());
                setTopDescription("共10人");
            }
            getGroupInfo(objectId);
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            rightButton.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putInt(CHAT_TYPE_ATTRIBUTE, type);
                bundle.putInt(OBJECT_ID_ATTRIBUTE, objectId);
                bundle.putBoolean(BundleUtil.IS_JOINED_ATTRIBUTE, true);
                bundle.putString(BundleUtil.OBJECT_ATTRIBUTE, JsonUtil.toJson(channelVO));
                Intent intent = new Intent(this, OtherInfoActivity.class).putExtras(bundle);
                startActivity(intent);
            });
            channelVO = JsonUtil.fromJson(this.getIntent().getExtras().getString(INFO_ATTRIBUTE), ChannelVO.class);
            if (channelVO != null) {
                setTopNick(channelVO.getNick());
                setTopDescription("共10人");
            }
            getChannelInfo(objectId);
        } else {
            XToastUtils.toast("类型错误!");
        }
    }

    private void initChatListView() {
        initImageView();
    }

    private void initImageView() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.alpha1to0));
                imageView.setVisibility(View.GONE);
                getChatMessageList(type, objectId);
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
                System.out.println(chatBottomEditText.getContentText());
            }
        });
        //TODO:切换页面时需要关闭输入法，或者关闭表情界面等。
        chatBottomEditText.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                    && v.getText() != null
                    && event.getAction() == KeyEvent.ACTION_DOWN) {
                System.out.println("这里是监听回车事件->" + chatBottomEditText.getContentText());
                if (!StringUtils.isEmpty(chatBottomEditText.getContentText())) {
                    refresh();
                    ChatMsg chatMsg = new ChatMsg();
                    chatMsg.setText(chatBottomEditText.getContentText());
                    chatMsg.setMsgType(MsgTypeUtil.MsgType.TYPE_TEXT.getCode());
                    chatMsg.setObjectId(objectId);
                    chatMsg.setType(ChatMsgUtil.CHARACTER_LIST[type].getName());
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
                    case 2: {
                        //待办
                        Bundle bundle = new Bundle();
                        bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, type);
                        bundle.putInt(BundleUtil.OBJECT_ID_ATTRIBUTE, objectId);
                        Intent intent = new Intent(mContext, ToDoListActivity.class);
                        startActivity(intent);
                        break;
                    }
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MsgTypeUtil.MsgType.TYPE_PIC.getCode()) {
            if (data != null) {
                Uri uri = data.getData();
                if (FileUtil.getRealPathFromUri(this, uri) != null) {
                    //从uri得到绝对路径，并获取到file文件
                    File file = new File(FileUtil.getRealPathFromUri(this, uri));
                    uploadFile(file, ChatMsgUtil.CHARACTER_LIST[type].getName(), objectId, MsgTypeUtil.MsgType.TYPE_PIC.getCode());
                }
            }
        } else if (requestCode == MsgTypeUtil.MsgType.TYPE_FILE.getCode()) {
            if (data != null) {
                Uri uri = data.getData();
                if (FileUtil.getRealPathFromUri(this, uri) != null) {
                    //从uri得到绝对路径，并获取到file文件
                    File file = new File(FileUtil.getRealPathFromUri(this, uri));
                    uploadFile(file, ChatMsgUtil.CHARACTER_LIST[type].getName(), objectId, MsgTypeUtil.MsgType.TYPE_FILE.getCode());
                }
            }
        }
    }

    private void uploadFile(File file, String chatType, Integer objectId, Integer msgType) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<ChatFile> responseData = ChatFileInfoController.upLoadFile(file, chatType, objectId);
                if (responseData != null && responseData.isSuccess()) {
                    ChatFile chatFile = responseData.getData();
                    handler1.post(new Runnable() {
                        @Override
                        public void run() {
                            ChatMsg chatMsg = new ChatMsg();
                            chatMsg.setMsgType(MsgTypeUtil.MSG_TYPE_LIST[msgType].getCode());
                            chatMsg.setType(ChatMsgUtil.CHARACTER_LIST[type].getName());
                            chatMsg.setObjectId(objectId);
                            if (msgType.equals(MsgTypeUtil.MsgType.TYPE_PIC.getCode())) {
                                chatMsg.setText(chatFile.getMongoId());
                            } else if (msgType.equals(MsgTypeUtil.MsgType.TYPE_FILE.getCode())) {
                                ChatMsgFileVO chatMsgFileVO = new ChatMsgFileVO();
                                chatMsgFileVO.setChatFileId(chatFile.getId());
                                chatMsgFileVO.setFileName(file.getName());
                                chatMsgFileVO.setFileSize(file.length() * 8);
                                chatMsg.setText(JsonUtil.toJson(chatMsgFileVO));
                            }
                            chatMsg.setOwnerId(chatFile.getOwnerId());
                            chatMsg.setTimeStamp(new Date());
                            createChatMsg(chatMsg);
                        }
                    });
                }
            }
        }).start();
    }

    private void initChatBottomBelow() {
        emojiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomBelowLayout.getVisibility() == View.GONE) {
                    initEmojiGridView();
                } else {
                    if (currentBottomBelow.equals(EMOJI_GRIDVIEW)) {
                        bottomBelowLayout.setVisibility(View.GONE);
                    } else {
                        initEmojiGridView();
                    }
                }
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomBelowLayout.getVisibility() == View.GONE) {
                    initExtraOptionGridView();
                } else {
                    if (currentBottomBelow.equals(EXTRA_OPTION_GRIDVIEW)) {
                        bottomBelowLayout.setVisibility(View.GONE);
                    } else {
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
                if (!chatMsg.getType().equals(ChatMsgUtil.Character.TYPE_CHANNEL.getName())) {
                    ResponseData<ChatMsgVO> responseData = ChatMsgController.createChatMsg(chatMsg);
                    if (responseData != null && responseData.isSuccess()) {
                        ChatMsgVO chatMsgVO = responseData.getData();
                        handler2.post(new Runnable() {
                            @Override
                            public void run() {
                                chatMsgVOList.add(chatMsgVO);
                                getChatMessageList(type, objectId);
                            }
                        });
                    }
                } else {
                    ChannelNotice channelNotice = new ChannelNotice();
                    channelNotice.setChannelId(objectId);
                    channelNotice.setType(chatMsg.getMsgType());
                    channelNotice.setText(chatMsg.getText());
                    ResponseData<ChannelNotice> responseData = ChannelNoticeController.createChannelNotice(channelNotice);
                    if (responseData != null && responseData.isSuccess()) {
                        channelNotice = responseData.getData();
                        ChatMsgVO chatMsgVO = new ChatMsgVO();
                        chatMsgVO.setType(ChatMsgUtil.Character.TYPE_CHANNEL.getName());
                        chatMsgVO.setTimeStamp(channelNotice.getCreateTime());
                        chatMsgVO.setIsSelf(true);
                        chatMsgVO.setObjectId(channelNotice.getId());
                        chatMsgVO.setText(channelNotice.getText());
                        chatMsgVO.setMsgType(channelNotice.getType());
                        chatMsgVO.setId(channelNotice.getId());
                        chatMsgVO.setOwnerId(GimmeApplication.getUserId());
                        chatMsgVO.setOwnerNick("用户id");
                        chatMsgVO.setChannelNoticeCount(0);
                        handler2.post(new Runnable() {
                            @Override
                            public void run() {
                                chatMsgVOList.add(chatMsgVO);
                                getChatMessageList(type, objectId);
                            }
                        });
                    }
                }
            }
        }).start();
    }

    private void setTopNick(String text) {
        topNick.setText(text);
    }

    private void setTopDescription(String text) {
        topDescription.setText(text);
    }

    private void getUserInfo(Integer id) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<UserVO> responseData =
                        UserController.getUserVO(id.toString(), ChatMsgUtil.Character.TYPE_FRIEND.getName(), "");
                if (responseData != null && responseData.isSuccess()) {
                    userVO = responseData.getData();
                    handler1.post(new Runnable() {
                        @Override
                        public void run() {
                            if (StringUtils.isEmpty(userVO.getNote())) {
                                setTopNick(userVO.getNick());
                            } else {
                                setTopNick(userVO.getNote());
                            }
                            setTopDescription(userVO.getMotto());
                        }
                    });
                } else {
                    finish();
                    Looper.prepare();
                    XToastUtils.toast("该用户不存在!");
                    Looper.loop();
                }
            }
        }).start();
    }

    private void getGroupInfo(Integer id) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<GroupVO> responseData =
                        GroupController.getGroupInfo(id.toString());
                if (responseData != null && responseData.isSuccess()) {
                    groupVO = responseData.getData();
                    handler1.post(new Runnable() {
                        @Override
                        public void run() {
                            setTopNick(groupVO.getNick());
                            setTopDescription("共" + groupVO.getTotalCount() + "人");
                        }
                    });
                } else {
                    finish();
                    Looper.prepare();
                    XToastUtils.toast("该群组不存在!");
                    Looper.loop();
                }
            }
        }).start();
    }

    private void getChannelInfo(Integer id) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<ChannelVO> responseData =
                        ChannelController.getChannelInfo(id.toString());
                if (responseData != null && responseData.isSuccess()) {
                    channelVO = responseData.getData();
                    handler1.post(new Runnable() {
                        @Override
                        public void run() {
                            setTopNick(channelVO.getNick());
                            setTopDescription("共" + channelVO.getTotalCount() + "人");
                        }
                    });
                } else {
                    finish();
                    Looper.prepare();
                    XToastUtils.toast("该频道不存在!");
                    Looper.loop();
                }
            }
        }).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getChatMessageList(type, objectId);
        bottomBelowLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}