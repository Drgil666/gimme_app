package com.project.gimme.view.activity;

import static com.project.gimme.utils.BundleUtil.OBJECTID_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.TYPE_ATTRIBUTE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.gimme.R;
import com.project.gimme.pojo.User;
import com.project.gimme.pojo.vo.ChannelVO;
import com.project.gimme.pojo.vo.ChatMsgVO;
import com.project.gimme.pojo.vo.GroupVO;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.view.adpter.ChatMsgVOAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author DrGilbert
 */
public class ChatActivity extends SwipeBackActivity {
    private Integer type;
    private Integer objectId;
    private List<ChatMsgVO> chatMsgList = new ArrayList<>();
    private ListView chatListView;
    private ImageView leftButton;
    private ImageView rightButton;
    private TextView topNick;
    private TextView topDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        chatListView = findViewById(R.id.chat_list_view);
        leftButton = findViewById(R.id.chat_top_left_button);
        rightButton = findViewById(R.id.chat_top_right_button);
        topNick = findViewById(R.id.chat_top_nick_text);
        topDescription = findViewById(R.id.chat_top_description_text);
        chatListView = findViewById(R.id.chat_list_view);
        initBundle();
        initTopBar();
        initChatListView();
    }

    private void initBundle() {
        Bundle bundle = this.getIntent().getExtras();
        objectId = bundle.getInt(OBJECTID_ATTRIBUTE);
        type = bundle.getInt(TYPE_ATTRIBUTE);
        System.out.println("objectId:" + objectId + ",type:" + type);
    }

    private void getChatMessageList(Integer type, Integer objectId) {
        for (int i = 1; i <= 10; i++) {
            ChatMsgVO chatMsg = new ChatMsgVO();
            chatMsg.setId(i);
            chatMsg.setOwnerId(1);
            chatMsg.setText("这是一条信息" + i);
            chatMsg.setObjectId(objectId);
            chatMsg.setIsSelf(i % 2 == 1);
            chatMsg.setType(type);
            chatMsg.setTimeStamp(new Date());
            chatMsgList.add(chatMsg);
        }
    }

    private void initTopBar() {
        leftButton.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
        });
        rightButton.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(TYPE_ATTRIBUTE, type);
            bundle.putInt(OBJECTID_ATTRIBUTE, objectId);
            Intent intent = new Intent(this, InfoActivity.class).putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        });
        if (type.equals(ChatMsgUtil.Character.TYPE_FRIEND.getCode())) {
            User user = getUserInfo(objectId);
            setTopNick(user.getNick());
            setTopDescription(user.getMotto());
        } else if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            GroupVO groupVO = getGroupInfo(objectId);
            setTopNick(groupVO.getNick());
            setTopDescription("共" + groupVO.getTotalCount() + "人");
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            ChannelVO channelVO = getChannelInfo(objectId);
            setTopNick(channelVO.getNick());
            setTopDescription("共" + channelVO.getTotalCount() + "人");
        } else {
            Toast.makeText(this, "类型错误!", Toast.LENGTH_LONG).show();
        }
    }

    private void initChatListView() {
        getChatMessageList(type, objectId);
        chatListView.setAdapter(new ChatMsgVOAdapter(this, chatMsgList));
        chatListView.setSelection(chatMsgList.size() - 1);
    }

    private void setTopNick(String text) {
        topNick.setText(text);
    }

    private void setTopDescription(String text) {
        topDescription.setText(text);
    }

    private User getUserInfo(Integer id) {
        User user = new User();
        user.setId(id);
        user.setNick("好友" + id);
        user.setMotto("好友个性签名");
        return user;
    }

    private GroupVO getGroupInfo(Integer id) {
        GroupVO groupVO = new GroupVO();
        groupVO.setId(id);
        groupVO.setNick("群聊" + id);
        groupVO.setCreateTime(new Date());
        groupVO.setTotalCount(10);
        return groupVO;
    }

    private ChannelVO getChannelInfo(Integer id) {
        ChannelVO channelVO = new ChannelVO();
        channelVO.setId(id);
        channelVO.setNick("频道" + id);
        channelVO.setOwnerId(1);
        channelVO.setTotalCount(10);
        channelVO.setCreateTime(new Date());
        return channelVO;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}