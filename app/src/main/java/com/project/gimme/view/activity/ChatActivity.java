package com.project.gimme.view.activity;

import static com.project.gimme.utils.BundleUtil.CHAT_TYPE_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.INFO_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_ID_ATTRIBUTE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.ChannelController;
import com.project.gimme.controller.ChannelNoticeController;
import com.project.gimme.controller.ChatMsgController;
import com.project.gimme.controller.GroupController;
import com.project.gimme.controller.UserController;
import com.project.gimme.pojo.ChannelNotice;
import com.project.gimme.pojo.ChatMsg;
import com.project.gimme.pojo.vo.ChannelVO;
import com.project.gimme.pojo.vo.ChatMsgVO;
import com.project.gimme.pojo.vo.GroupVO;
import com.project.gimme.pojo.vo.RefreshVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.pojo.vo.UserVO;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.JsonUtil;
import com.project.gimme.utils.XToastUtils;
import com.project.gimme.view.adpter.ChatMsgVoAdapter;
import com.squareup.picasso.Picasso;
import com.xuexiang.xui.widget.edittext.MultiLineEditText;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
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
    Handler handler = new Handler();
    final Context mContext = this;
    @BindView(R.id.chat_bottom_edit_emoji)
    ImageView emojiButton;
    @BindView(R.id.chat_bottom_edit_add)
    ImageView addButton;
    @BindView(R.id.chat_bottom_below_layout)
    RelativeLayout bottomBelowLayout;

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


    private void initBundle() {
        Bundle bundle = this.getIntent().getExtras();
        objectId = bundle.getInt(OBJECT_ID_ATTRIBUTE);
        type = bundle.getInt(CHAT_TYPE_ATTRIBUTE);
        System.out.println("objectId:" + objectId + ",type:" + type);
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
                    handler.post(new Runnable() {
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
        Picasso.with(this).load(R.mipmap.back).into(leftButton);
        leftButton.setOnClickListener(view -> {
            finish();
        });
        Picasso.with(this).load(R.mipmap.info).into(rightButton);
        rightButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(CHAT_TYPE_ATTRIBUTE, type);
            bundle.putInt(OBJECT_ID_ATTRIBUTE, objectId);
            bundle.putBoolean(BundleUtil.IS_JOINED_ATTRIBUTE, true);
            Intent intent = new Intent(this, InfoActivity.class).putExtras(bundle);
            startActivity(intent);
        });
        if (type.equals(ChatMsgUtil.Character.TYPE_FRIEND.getCode())) {
            UserVO userVO = JsonUtil.fromJson(this.getIntent().getExtras().getString(INFO_ATTRIBUTE), UserVO.class);
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
            GroupVO groupVO = JsonUtil.fromJson(this.getIntent().getExtras().getString(INFO_ATTRIBUTE), GroupVO.class);
            if (groupVO != null) {
                setTopNick(groupVO.getNick());
                setTopDescription("共10人");
            }
            getGroupInfo(objectId);
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            ChannelVO channelVO = JsonUtil.fromJson(this.getIntent().getExtras().getString(INFO_ATTRIBUTE), ChannelVO.class);
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
        getChatMessageList(type, objectId);
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
        chatBottomEditText.getEditText().setOnEditorActionListener((v, actionId, event) -> {
            if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                    && v.getText() != null
                    && event.getAction() == KeyEvent.ACTION_DOWN) {
                System.out.println("这里是监听回车事件->" + chatBottomEditText.getContentText());
                if (!StringUtils.isEmpty(chatBottomEditText.getContentText())) {
                    refresh();
                    createChatMsg(chatBottomEditText.getContentText());
                    chatBottomEditText.setContentText(null);
                }
            }
            return true;
        });
    }

    private void initChatBottomBelow() {
        emojiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomBelowLayout.getVisibility() == View.GONE) {
                    bottomBelowLayout.setVisibility(View.VISIBLE);
                } else {
                    bottomBelowLayout.setVisibility(View.GONE);
                }
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomBelowLayout.getVisibility() == View.GONE) {
                    bottomBelowLayout.setVisibility(View.VISIBLE);
                } else {
                    bottomBelowLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    private void createChatMsg(String text) {
        ChatMsg chatMsg = new ChatMsg();
        chatMsg.setText(text);
        chatMsg.setObjectId(objectId);
        chatMsg.setType(ChatMsgUtil.CHARACTER_LIST[type].getName());
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                if (!chatMsg.getType().equals(ChatMsgUtil.Character.TYPE_CHANNEL.getName())) {
                    ResponseData<ChatMsgVO> responseData = ChatMsgController.createChatMsg(chatMsg);
                    if (responseData != null && responseData.isSuccess()) {
                        ChatMsgVO chatMsgVO = responseData.getData();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                chatMsgVOList.add(chatMsgVO);
                                chatMsgVoAdapter.notifyDataSetChanged();
                                chatListView.setSelection(chatMsgVOList.size() - 1);
                            }
                        });
                    }
                } else {
                    ChannelNotice channelNotice = new ChannelNotice();
                    channelNotice.setChannelId(objectId);
                    channelNotice.setType("1");
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
                        chatMsgVO.setId(channelNotice.getId());
                        chatMsgVO.setOwnerId(GimmeApplication.getUserId());
                        chatMsgVO.setOwnerNick("用户id");
                        chatMsgVO.setChannelNoticeCount(0);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                chatMsgVOList.add(chatMsgVO);
                                chatMsgVoAdapter.notifyDataSetChanged();
                                chatListView.setSelection(chatMsgVOList.size() - 1);
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
                    UserVO userVO = responseData.getData();
                    handler.post(new Runnable() {
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
                    GroupVO groupVO = responseData.getData();
                    handler.post(new Runnable() {
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
                    ChannelVO channelVO = responseData.getData();
                    handler.post(new Runnable() {
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
    protected void onDestroy() {
        super.onDestroy();
    }
}