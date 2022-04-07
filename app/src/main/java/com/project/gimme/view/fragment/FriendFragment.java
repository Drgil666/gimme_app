package com.project.gimme.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.project.gimme.R;
import com.project.gimme.pojo.vo.PersonalMsgVO;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.ContactsUtil;
import com.project.gimme.utils.JsonUtil;
import com.project.gimme.utils.PersonalMsgUtil;
import com.project.gimme.view.activity.PersonalMsgListActivity;
import com.project.gimme.view.activity.SearchActivity;
import com.project.gimme.view.adpter.PersonalMsgAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 */
@SuppressLint("NonConstantResourceId")
public class FriendFragment extends Fragment {
    private List<String> listItem = new ArrayList<>();
    @BindView(R.id.friend_personal_msg_list_view)
    ListView personalMsgListView;
    @BindView(R.id.friend_list_search_edittext)
    EditText searchEditText;
    private List<List<PersonalMsgVO>> personalMsgVOLists;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        ButterKnife.bind(this, view);
        initListView();
        initSearchEditText();
        return view;
    }

    private void initSearchEditText() {
        searchEditText.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(BundleUtil.SEARCH_TYPE_ATTRIBUTE, ContactsUtil.SearchType.TYPE_CONTACTS.getCode());
                    Intent intent = new Intent(getContext(), SearchActivity.class).putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(BundleUtil.SEARCH_TYPE_ATTRIBUTE, ContactsUtil.SearchType.TYPE_CONTACTS.getCode());
                Intent intent = new Intent(getContext(), SearchActivity.class).putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void getListItem() {
        listItem.clear();
        listItem.add("新朋友");
        listItem.add("群聊/频道消息");
    }

    private void getPersonalMsgFriendList() {
        List<PersonalMsgVO> personalMsgVOList = new ArrayList<>();
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
        personalMsgVOLists.add(personalMsgVOList);
    }

    private void getPersonalMsgOtherList() {
        List<PersonalMsgVO> personalMsgVOList = new ArrayList<>();
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
        personalMsgVOLists.add(personalMsgVOList);
    }

    private void initListView() {
        getListItem();
        personalMsgVOLists = new ArrayList<>();
        getPersonalMsgFriendList();
        getPersonalMsgOtherList();
        personalMsgListView.setAdapter(new PersonalMsgAdapter(getContext(), listItem));
        personalMsgListView.setOnItemClickListener((parent, view, position, id) -> {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleUtil.PERSONAL_MSG_TYPE_ATTRIBUTE, position);
            bundle.putString(BundleUtil.OBJECT_ATTRIBUTE, JsonUtil.toJson(personalMsgVOLists.get(position)));
            Intent intent = new Intent(getActivity(), PersonalMsgListActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
}