package com.project.gimme.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.project.gimme.R;
import com.project.gimme.pojo.Channel;
import com.project.gimme.pojo.Group;
import com.project.gimme.pojo.User;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.view.activity.ChatActivity;
import com.project.gimme.view.adpter.FriendChannelAdapter;
import com.project.gimme.view.adpter.FriendGroupAdapter;
import com.project.gimme.view.adpter.FriendUserAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author DrGilbert
 * @date 2022/1/13 11:04
 */
public class FriendListFragment extends Fragment {
    private TabHost tabHost;
    private List<User> userList = new ArrayList<>();
    private List<Group> groupList = new ArrayList<>();
    private List<Channel> channelList = new ArrayList<>();
    private ListView userListView;
    private ListView groupListView;
    private ListView channelListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_list, container, false);
        tabHost = (TabHost) view.findViewById(android.R.id.tabhost);
        userListView = view.findViewById(R.id.friend_list_friend_list);
        groupListView = view.findViewById(R.id.friend_list_group_list);
        channelListView = view.findViewById(R.id.friend_list_channel_list);
        initUserListView();
        initGroupListView();
        initChannelListView();
        initTabHost();
        return view;
    }

    private void initTabHost() {
        //初始化TabHost容器
        tabHost.setup();
        //在TabHost创建标签，然后设置：标题／图标／标签页布局
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("好友", null).setContent(R.id.friend_list_friend_list));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("群聊", null).setContent(R.id.friend_list_group_list));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("频道", null).setContent(R.id.friend_list_channel_list));
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            TextView textView = tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            textView.setTextSize(20);
        }
        tabHost.setCurrentTab(0);
    }

    private void initUserListView() {
        getUserList();
        userListView.setAdapter(new FriendUserAdapter(getContext(), userList));
        userListView.setOnItemClickListener((parent, view, position, id) -> {
            System.out.println("friend");
            Bundle bundle = getBundle((int) id,
                    ChatMsgUtil.Character.TYPE_FRIEND.getCode());
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        });
    }

    private void getUserList() {
        for (int i = 1; i <= 10; i++) {
            User user = new User();
            user.setId(i);
            user.setNick("好友" + i);
            userList.add(user);
        }
    }

    private void initGroupListView() {
        getGroupList();
        groupListView.setAdapter(new FriendGroupAdapter(getContext(), groupList));
        groupListView.setOnItemClickListener((parent, view, position, id) -> {
            System.out.println("group");
            Bundle bundle = getBundle((int) id,
                    ChatMsgUtil.Character.TYPE_GROUP.getCode());
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        });
    }

    private void getGroupList() {
        for (int i = 1; i <= 10; i++) {
            Group group = new Group();
            group.setId(i);
            group.setNick("群聊" + i);
            group.setCreateTime(new Date());
            groupList.add(group);
        }
    }

    private void initChannelListView() {
        getChannelList();
        channelListView.setAdapter(new FriendChannelAdapter(getContext(), channelList));
        channelListView.setOnItemClickListener((parent, view, position, id) -> {
            System.out.println("channel");
            Bundle bundle = getBundle((int) id,
                    ChatMsgUtil.Character.TYPE_CHANNEL.getCode());
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        });
    }

    private void getChannelList() {
        for (int i = 1; i <= 10; i++) {
            Channel channel = new Channel();
            channel.setId(i);
            channel.setCreateTime(new Date());
            channel.setNick("频道" + i);
            channel.setOwnerId(1);
            channelList.add(channel);
        }
    }

    private Bundle getBundle(Integer id, Integer type) {
        Bundle bundle = new Bundle();
        bundle.putInt("objectId", id);
        bundle.putInt("type", type);
        return bundle;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
