package com.project.gimme.view.activity;

import static com.project.gimme.utils.BundleUtil.CHAT_TYPE_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.INFO_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_ID_ATTRIBUTE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.gimme.R;
import com.project.gimme.controller.ChannelController;
import com.project.gimme.controller.ChatMsgController;
import com.project.gimme.controller.GroupController;
import com.project.gimme.controller.UserController;
import com.project.gimme.pojo.vo.ChannelVO;
import com.project.gimme.pojo.vo.ChatMsgVO;
import com.project.gimme.pojo.vo.GroupVO;
import com.project.gimme.pojo.vo.RefreshVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.pojo.vo.UserVO;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.JsonUtil;
import com.project.gimme.view.adpter.ChatMsgVoAdapter;

import org.apache.commons.lang3.StringUtils;

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
    private List<ChatMsgVO> chatMsgList = new ArrayList<>();
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
    @BindView(R.id.chat_bottom_edittext)
    EditText chatBottomEditText;
    Handler handler = new Handler();

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
        for (int i = 1; i <= 10; i++) {
            ChatMsgVO chatMsgVO = new ChatMsgVO();
            chatMsgVO.setId(i);
            chatMsgVO.setOwnerId(1);
            chatMsgVO.setText("这是一条信息" + i);
            chatMsgVO.setObjectId(objectId);
            chatMsgVO.setIsSelf(i % 2 == 1);
            chatMsgVO.setType(ChatMsgUtil.CHARACTER_LIST[type].getName());
            chatMsgVO.setOwnerNick("这是一个昵称" + chatMsgVO.getId());
            chatMsgVO.setTimeStamp(new Date());
            chatMsgList.add(chatMsgVO);
        }
    }

    private void initTopBar() {
        leftButton.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
        });
        rightButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(CHAT_TYPE_ATTRIBUTE, type);
            bundle.putInt(OBJECT_ID_ATTRIBUTE, objectId);
            Intent intent = new Intent(this, InfoActivity.class).putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
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
            Toast.makeText(this, "类型错误!", Toast.LENGTH_LONG).show();
        }
    }

    private void initChatListView() {
        getChatMessageList(type, objectId);
        chatListView.setAdapter(new ChatMsgVoAdapter(this, chatMsgList, type));
        chatListView.setSelection(chatMsgList.size() - 1);
    }

    private void initChatBottom() {
        chatBottomEditText.setOnEditorActionListener((v, actionId, event) -> {
            if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                    && v.getText() != null
                    && event.getAction() == KeyEvent.ACTION_DOWN) {
                System.out.println("这里是监听回车事件");
            }
            return true;
        });
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
                            if (StringUtils.isEmpty(userVO.getNote()))
                                setTopNick(userVO.getNick());
                            else {
                                setTopNick(userVO.getNote());
                            }
                            setTopDescription(userVO.getMotto());
                        }
                    });
                } else {
                    finish();
                    overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
                    Looper.prepare();
                    Toast.makeText(ChatActivity.this, "该用户不存在!", Toast.LENGTH_LONG).show();
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
                    overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
                    Looper.prepare();
                    Toast.makeText(ChatActivity.this, "该群组不存在!", Toast.LENGTH_LONG).show();
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
                    overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
                    Looper.prepare();
                    Toast.makeText(ChatActivity.this, "该频道不存在!", Toast.LENGTH_LONG).show();
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