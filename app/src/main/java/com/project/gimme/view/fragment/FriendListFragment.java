package com.project.gimme.view.fragment;

import static com.project.gimme.utils.BundleUtil.INFO_ATTRIBUTE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.project.gimme.R;
import com.project.gimme.controller.ChannelController;
import com.project.gimme.controller.GroupController;
import com.project.gimme.controller.UserController;
import com.project.gimme.pojo.Channel;
import com.project.gimme.pojo.Group;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.pojo.vo.UserVO;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.JsonUtil;
import com.project.gimme.view.activity.ChatActivity;
import com.project.gimme.view.adpter.FriendChannelAdapter;
import com.project.gimme.view.adpter.FriendGroupAdapter;
import com.project.gimme.view.adpter.FriendUserAdapter;
import com.project.gimme.view.listview.PullRefreshListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lombok.SneakyThrows;

/**
 * @author DrGilbert
 * @date 2022/1/13 11:04
 */
@SuppressLint("NonConstantResourceId")
public class FriendListFragment extends Fragment {
    @BindView(android.R.id.tabhost)
    TabHost tabHost;
    @BindView(R.id.friend_list_friend_list)
    PullRefreshListView userListView;
    private List<Group> groupList = new ArrayList<>();
    private List<Channel> channelList = new ArrayList<>();
    @BindView(R.id.friend_list_group_list)
    PullRefreshListView groupListView;
    @BindView(R.id.friend_list_channel_list)
    PullRefreshListView channelListView;
    private List<UserVO> userList = new ArrayList<>();
    private Unbinder unbinder;
    Handler handler = new Handler();
    private FriendUserAdapter friendUserAdapter;
    private FriendGroupAdapter friendGroupAdapter;
    private FriendChannelAdapter friendChannelAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_list, container, false);
        unbinder = ButterKnife.bind(this, view);
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
        userListView.setOnItemClickListener((parent, view, position, id) -> {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleUtil.OBJECT_ID_ATTRIBUTE, (int) id);
            bundle.putString(INFO_ATTRIBUTE, JsonUtil.toJson(userListView.getAdapter().getItem(position)));
            bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, ChatMsgUtil.Character.TYPE_FRIEND.getCode());
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        });
        userListView.setOnRefreshListener(new PullRefreshListView.OnRefreshListener() {
            @Override
            public void onPullRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getUserList();
                        friendUserAdapter.notifyDataSetChanged();
                        userListView.onRefreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
            }
        });
    }

    private void getUserList() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<List<UserVO>> responseData =
                        UserController.getFriendListInfo("");
                if (responseData != null && responseData.isSuccess()) {
                    userList = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            friendUserAdapter = new FriendUserAdapter(getContext(), userList);
                            userListView.setAdapter(friendUserAdapter);
                        }
                    });
                }
            }
        }).start();
    }

    private void initGroupListView() {
        getGroupList();
        groupListView.setOnItemClickListener((parent, view, position, id) -> {
            System.out.println("group");
            Bundle bundle = new Bundle();
            bundle.putInt(BundleUtil.OBJECT_ID_ATTRIBUTE, (int) id);
            bundle.putString(INFO_ATTRIBUTE, JsonUtil.toJson(groupListView.getAdapter().getItem(position)));
            bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, ChatMsgUtil.Character.TYPE_GROUP.getCode());
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        });
        groupListView.setOnRefreshListener(new PullRefreshListView.OnRefreshListener() {
            @Override
            public void onPullRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getGroupList();
                        friendGroupAdapter.notifyDataSetChanged();
                        groupListView.onRefreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
            }
        });
    }

    private void getGroupList() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<List<Group>> responseData =
                        GroupController.getGroupList("");
                if (responseData != null && responseData.isSuccess()) {
                    groupList = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            friendGroupAdapter = new FriendGroupAdapter(getContext(), groupList);
                            groupListView.setAdapter(friendGroupAdapter);
                        }
                    });
                }
            }
        }).start();
    }

    private void initChannelListView() {
        getChannelList();
        channelListView.setOnItemClickListener((parent, view, position, id) -> {
            System.out.println("channel");
            Bundle bundle = new Bundle();
            bundle.putInt(BundleUtil.OBJECT_ID_ATTRIBUTE, (int) id);
            bundle.putString(INFO_ATTRIBUTE, JsonUtil.toJson(channelListView.getAdapter().getItem(position)));
            bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, ChatMsgUtil.Character.TYPE_CHANNEL.getCode());
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        });
        channelListView.setOnRefreshListener(new PullRefreshListView.OnRefreshListener() {
            @Override
            public void onPullRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getChannelList();
                        friendChannelAdapter.notifyDataSetChanged();
                        channelListView.onRefreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {
            }
        });
    }

    private void getChannelList() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<List<Channel>> responseData =
                        null;
                try {
                    responseData = ChannelController.getChannelList("");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (responseData != null && responseData.isSuccess()) {
                    channelList = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            friendChannelAdapter = new FriendChannelAdapter(getContext(), channelList);
                            channelListView.setAdapter(friendChannelAdapter);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
