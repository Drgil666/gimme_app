package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.gimme.R;
import com.project.gimme.pojo.vo.PersonalMsgVO;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.PersonalMsgUtil;
import com.project.gimme.utils.XToastUtils;
import com.project.gimme.view.adpter.PersonalMsgListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 */
@SuppressLint("NonConstantResourceId")
public class PersonalMsgListActivity extends SwipeBackActivity {
    private List<PersonalMsgVO> personalMsgVOList = new ArrayList<>();
    @BindView(R.id.personal_msg_list_list_view)
    ListView personalMsgListView;
    @BindView(R.id.personal_msg_list_top_nick_text)
    TextView topText;
    @BindView(R.id.personal_msg_list_top_left_button)
    ImageView leftButton;
    private Integer type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_msg_list);
        ButterKnife.bind(this);
        getType();
        initTopBar();
        initPersonalMsgListView();
    }

    private void getPersonalMsgFriendList() {
        PersonalMsgVO personalMsgVO = new PersonalMsgVO();
        personalMsgVO.setId(1);
        personalMsgVO.setObjectId(1);
        personalMsgVO.setOperatorNick("被操作用户" + 1);
        personalMsgVO.setOwnerNick("操作用户" + 1);
        personalMsgVO.setOperatorId(2);
        personalMsgVO.setNote("note" + 1);
        personalMsgVO.setOwnerId(2);
        personalMsgVO.setStatus(PersonalMsgUtil.Status.TYPE_ACCEPT.getCode());
        personalMsgVO.setType(PersonalMsgUtil.FriendPersonalMsg.TYPE_INSERT_FRIEND.getName());
        personalMsgVO.setObjectType(ChatMsgUtil.Character.TYPE_FRIEND.getName());
        personalMsgVOList.add(personalMsgVO);
        personalMsgVO = new PersonalMsgVO();
        personalMsgVO.setId(1);
        personalMsgVO.setObjectId(1);
        personalMsgVO.setOperatorNick("被操作用户" + 1);
        personalMsgVO.setOwnerNick("操作用户" + 1);
        personalMsgVO.setOperatorId(2);
        personalMsgVO.setNote("note" + 1);
        personalMsgVO.setOwnerId(2);
        personalMsgVO.setStatus(PersonalMsgUtil.Status.TYPE_NULL.getCode());
        personalMsgVO.setType(PersonalMsgUtil.FriendPersonalMsg.TYPE_DELETE_FRIEND.getName());
        personalMsgVO.setObjectType(ChatMsgUtil.Character.TYPE_FRIEND.getName());
        personalMsgVOList.add(personalMsgVO);
        personalMsgVO = new PersonalMsgVO();
        personalMsgVO.setId(1);
        personalMsgVO.setObjectId(1);
        personalMsgVO.setOperatorNick("被操作用户" + 1);
        personalMsgVO.setOwnerNick("操作用户" + 1);
        personalMsgVO.setOperatorId(2);
        personalMsgVO.setNote("note" + 1);
        personalMsgVO.setOwnerId(2);
        personalMsgVO.setStatus(PersonalMsgUtil.Status.TYPE_CHOOSE.getCode());
        personalMsgVO.setType(PersonalMsgUtil.FriendPersonalMsg.TYPE_INSERT_FRIEND.getName());
        personalMsgVO.setObjectType(ChatMsgUtil.Character.TYPE_FRIEND.getName());
        personalMsgVOList.add(personalMsgVO);
    }

    private void getPersonalMsgOtherList() {
        PersonalMsgVO personalMsgVO = new PersonalMsgVO();
        personalMsgVO.setId(1);
        personalMsgVO.setObjectId(1);
        personalMsgVO.setOperatorNick("被操作群聊" + 1);
        personalMsgVO.setOwnerNick("操作用户" + 1);
        personalMsgVO.setOwnerId(2);
        personalMsgVO.setOperatorId(2);
        personalMsgVO.setNote("note" + 1);
        personalMsgVO.setStatus(PersonalMsgUtil.Status.TYPE_NULL.getCode());
        personalMsgVO.setObjectNick("群聊" + 1);
        personalMsgVO.setType(PersonalMsgUtil.GroupPersonalMsg.TYPE_INSERT_GROUP.getName());
        personalMsgVO.setObjectType(ChatMsgUtil.Character.TYPE_GROUP.getName());
        personalMsgVOList.add(personalMsgVO);
        personalMsgVO = new PersonalMsgVO();
        personalMsgVO.setId(1);
        personalMsgVO.setObjectId(1);
        personalMsgVO.setOperatorNick("被操作群聊" + 1);
        personalMsgVO.setOwnerNick("操作用户" + 1);
        personalMsgVO.setOwnerId(2);
        personalMsgVO.setOperatorId(2);
        personalMsgVO.setNote("note" + 1);
        personalMsgVO.setStatus(PersonalMsgUtil.Status.TYPE_NULL.getCode());
        personalMsgVO.setObjectNick("群聊" + 1);
        personalMsgVO.setType(PersonalMsgUtil.GroupPersonalMsg.TYPE_DELETE_GROUP.getName());
        personalMsgVO.setObjectType(ChatMsgUtil.Character.TYPE_GROUP.getName());
        personalMsgVOList.add(personalMsgVO);
        personalMsgVO = new PersonalMsgVO();
        personalMsgVO.setId(1);
        personalMsgVO.setObjectId(1);
        personalMsgVO.setOperatorNick("被操作群聊" + 1);
        personalMsgVO.setOwnerNick("操作用户" + 1);
        personalMsgVO.setOwnerId(2);
        personalMsgVO.setOperatorId(2);
        personalMsgVO.setNote("note" + 1);
        personalMsgVO.setStatus(PersonalMsgUtil.Status.TYPE_CHOOSE.getCode());
        personalMsgVO.setObjectNick("群聊" + 1);
        personalMsgVO.setType(PersonalMsgUtil.GroupPersonalMsg.TYPE_INSERT_GROUP_MEMBER.getName());
        personalMsgVO.setObjectType(ChatMsgUtil.Character.TYPE_GROUP.getName());
        personalMsgVOList.add(personalMsgVO);
        personalMsgVO = new PersonalMsgVO();
        personalMsgVO.setId(1);
        personalMsgVO.setObjectId(1);
        personalMsgVO.setOperatorNick("被操作群聊" + 1);
        personalMsgVO.setOwnerNick("操作用户" + 1);
        personalMsgVO.setOwnerId(2);
        personalMsgVO.setOperatorId(2);
        personalMsgVO.setNote("note" + 1);
        personalMsgVO.setStatus(PersonalMsgUtil.Status.TYPE_NULL.getCode());
        personalMsgVO.setObjectNick("群聊" + 1);
        personalMsgVO.setType(PersonalMsgUtil.GroupPersonalMsg.TYPE_DELETE_GROUP_MEMBER.getName());
        personalMsgVO.setObjectType(ChatMsgUtil.Character.TYPE_GROUP.getName());
        personalMsgVOList.add(personalMsgVO);
        personalMsgVO = new PersonalMsgVO();
        personalMsgVO.setId(1);
        personalMsgVO.setObjectId(1);
        personalMsgVO.setOperatorNick("被操作群聊" + 1);
        personalMsgVO.setOwnerNick("操作用户" + 1);
        personalMsgVO.setOwnerId(2);
        personalMsgVO.setOperatorId(2);
        personalMsgVO.setNote("note" + 1);
        personalMsgVO.setStatus(PersonalMsgUtil.Status.TYPE_NULL.getCode());
        personalMsgVO.setObjectNick("群聊" + 1);
        personalMsgVO.setType(PersonalMsgUtil.GroupPersonalMsg.TYPE_UPDATE_GROUP_MEMBER.getName());
        personalMsgVO.setObjectType(ChatMsgUtil.Character.TYPE_GROUP.getName());
        personalMsgVOList.add(personalMsgVO);
        personalMsgVO = new PersonalMsgVO();
        personalMsgVO.setId(1);
        personalMsgVO.setObjectId(1);
        personalMsgVO.setOperatorNick("被操作频道");
        personalMsgVO.setNote("note" + 1);
        personalMsgVO.setOwnerId(2);
        personalMsgVO.setOperatorId(2);
        personalMsgVO.setOwnerNick("操作用户" + 1);
        personalMsgVO.setStatus(PersonalMsgUtil.Status.TYPE_NULL.getCode());
        personalMsgVO.setObjectNick("频道" + 1);
        personalMsgVO.setType(PersonalMsgUtil.ChannelPersonalMsg.TYPE_INSERT_CHANNEL.getName());
        personalMsgVO.setObjectType(ChatMsgUtil.Character.TYPE_CHANNEL.getName());
        personalMsgVOList.add(personalMsgVO);
        personalMsgVO = new PersonalMsgVO();
        personalMsgVO.setId(1);
        personalMsgVO.setObjectId(1);
        personalMsgVO.setOperatorNick("被操作频道");
        personalMsgVO.setNote("note" + 1);
        personalMsgVO.setOwnerId(2);
        personalMsgVO.setOperatorId(2);
        personalMsgVO.setOwnerNick("操作用户" + 1);
        personalMsgVO.setStatus(PersonalMsgUtil.Status.TYPE_NULL.getCode());
        personalMsgVO.setObjectNick("频道" + 1);
        personalMsgVO.setType(PersonalMsgUtil.ChannelPersonalMsg.TYPE_DELETE_CHANNEL.getName());
        personalMsgVO.setObjectType(ChatMsgUtil.Character.TYPE_CHANNEL.getName());
        personalMsgVOList.add(personalMsgVO);
        personalMsgVO = new PersonalMsgVO();
        personalMsgVO.setId(1);
        personalMsgVO.setObjectId(1);
        personalMsgVO.setOperatorNick("被操作频道");
        personalMsgVO.setNote("note" + 1);
        personalMsgVO.setOwnerId(2);
        personalMsgVO.setOperatorId(2);
        personalMsgVO.setOwnerNick("操作用户" + 1);
        personalMsgVO.setStatus(PersonalMsgUtil.Status.TYPE_CHOOSE.getCode());
        personalMsgVO.setObjectNick("频道" + 1);
        personalMsgVO.setType(PersonalMsgUtil.ChannelPersonalMsg.TYPE_INSERT_CHANNEL_MEMBER.getName());
        personalMsgVO.setObjectType(ChatMsgUtil.Character.TYPE_CHANNEL.getName());
        personalMsgVOList.add(personalMsgVO);
        personalMsgVO = new PersonalMsgVO();
        personalMsgVO.setId(1);
        personalMsgVO.setObjectId(1);
        personalMsgVO.setOperatorNick("被操作频道");
        personalMsgVO.setNote("note" + 1);
        personalMsgVO.setOwnerId(2);
        personalMsgVO.setOperatorId(2);
        personalMsgVO.setOwnerNick("操作用户" + 1);
        personalMsgVO.setStatus(PersonalMsgUtil.Status.TYPE_NULL.getCode());
        personalMsgVO.setObjectNick("频道" + 1);
        personalMsgVO.setType(PersonalMsgUtil.ChannelPersonalMsg.TYPE_UPDATE_CHANNEL_MEMBER.getName());
        personalMsgVO.setObjectType(ChatMsgUtil.Character.TYPE_CHANNEL.getName());
        personalMsgVOList.add(personalMsgVO);
        personalMsgVO = new PersonalMsgVO();
        personalMsgVO.setId(1);
        personalMsgVO.setObjectId(1);
        personalMsgVO.setOperatorNick("被操作频道");
        personalMsgVO.setNote("note" + 1);
        personalMsgVO.setOwnerId(2);
        personalMsgVO.setOperatorId(2);
        personalMsgVO.setOwnerNick("操作用户" + 1);
        personalMsgVO.setStatus(PersonalMsgUtil.Status.TYPE_NULL.getCode());
        personalMsgVO.setObjectNick("频道" + 1);
        personalMsgVO.setType(PersonalMsgUtil.ChannelPersonalMsg.TYPE_DELETE_CHANNEL_MEMBER.getName());
        personalMsgVO.setObjectType(ChatMsgUtil.Character.TYPE_CHANNEL.getName());
        personalMsgVOList.add(personalMsgVO);
    }

    private void initTopBar() {
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (type.equals(PersonalMsgUtil.PersonalMsgType.TYPE_FRIEND_PERSONAL_MSG.getCode())) {
            setTopText("新朋友");
            getPersonalMsgFriendList();
        } else if (type.equals(PersonalMsgUtil.PersonalMsgType.TYPE_OTHER_PERSONAL_MSG.getCode())) {
            setTopText("群聊/频道消息");
            getPersonalMsgOtherList();
        } else {
            XToastUtils.toast("类型错误!");
        }
    }

    private void setTopText(String text) {
        topText.setText(text);
    }

    private void getType() {
        Bundle bundle = this.getIntent().getExtras();
        type = bundle.getInt(BundleUtil.PERSONAL_MSG_TYPE_ATTRIBUTE);
        //System.out.println("type:" + type);
    }

    private void initPersonalMsgListView() {
        personalMsgListView.setAdapter(new PersonalMsgListAdapter(this, personalMsgVOList));
    }
}