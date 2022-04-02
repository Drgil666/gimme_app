package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.project.gimme.R;
import com.project.gimme.controller.ChannelController;
import com.project.gimme.controller.GroupController;
import com.project.gimme.controller.UserController;
import com.project.gimme.pojo.Channel;
import com.project.gimme.pojo.Group;
import com.project.gimme.pojo.vo.ContactVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.pojo.vo.UserVO;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.ContactsUtil;
import com.project.gimme.utils.XToastUtils;
import com.project.gimme.view.adpter.ContactVoAdapter;
import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.xuexiang.xui.widget.tabbar.EasyIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

/**
 * @author 25741
 */
@SuppressLint("NonConstantResourceId")
public class FriendListActivity extends SwipeBackActivity {
    @BindView(R.id.activity_friend_list_indicator1)
    EasyIndicator indicator1;
    @BindView(R.id.activity_friend_list_indicator2)
    EasyIndicator indicator2;
    private final String[] indicatorTitle1 = new String[]{"创建群聊", "创建频道"};
    private final String[] indicatorTitle2 = new String[]{"好友", "群聊", "频道"};
    private Integer type;
    private Integer createMethod = 0;
    private Integer transmitSessionType = 0;
    private Integer chatMsgId = null;
    @BindView(R.id.activity_friend_list_search_edit_text)
    EditText searchText;
    @BindView(R.id.activity_friend_list_listview)
    ListView listView;
    @BindView(R.id.activity_friend_list_bottom_button)
    Button bottomButton;
    private Integer transmitMsgType = null;
    private List<UserVO> userVOList = new ArrayList<>();
    private List<Group> groupList = new ArrayList<>();
    private List<Channel> channelList = new ArrayList<>();
    Handler handler = new Handler();
    private ContactVoAdapter contactVoAdapter;
    private final Context mContext = this;
    @BindView(R.id.activity_friend_list_top_left_button)
    ImageView topLeftButton;
    @BindView(R.id.activity_friend_list_bottom_layout)
    RelativeLayout bottomLayout;
    public static List<Integer> idList = new ArrayList<>();
    private static final Integer CREATE_GROUP = 0;
    private static final Integer CREATE_CHANNEL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);
        ButterKnife.bind(this);
        getType();
        initTopBar();
        initIndicator();
        initSearchType();
        initBottomLayout();
        initListView();
    }

    public static void addItem(Integer id) {
        idList.add(id);
        System.out.println("add:" + id);
    }

    public static void deleteItem(Integer id) {
        idList.remove(id);
        System.out.println("remove:" + id);
    }

    private void initTopBar() {
        topLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getType() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(BundleUtil.CONTACTS_LIST_TYPE_ATTRIBUTE);
        if (type.equals(ContactsUtil.ContactType.TYPE_TRANSMIT.getCode())) {
            transmitMsgType = bundle.getInt(BundleUtil.TRANSMIT_ATTRIBUTE);
            //TODO:chatMsgId还需要修改
        } else if (type.equals(ContactsUtil.ContactType.TYPE_CREATE_CONTACT.getCode())) {

        } else {
            XToastUtils.toast("类型错误!");
        }
    }

    private void initIndicator() {
        if (type.equals(ContactsUtil.ContactType.TYPE_CREATE_CONTACT.getCode())) {
            indicator2.setVisibility(View.GONE);
            indicator1.setVisibility(View.VISIBLE);
            indicator1.setTabTitles(indicatorTitle1);
            indicator1.setOnTabClickListener(new EasyIndicator.OnTabClickListener() {
                @Override
                public void onTabClick(String title, int position) {
                    createMethod = position;
//                    getUserVOList(searchText.getText().toString());
                }
            });
        } else if (type.equals(ContactsUtil.ContactType.TYPE_TRANSMIT.getCode())) {
            indicator1.setVisibility(View.GONE);
            indicator2.setVisibility(View.VISIBLE);
            indicator2.setTabTitles(indicatorTitle2);
            indicator2.setOnTabClickListener(new EasyIndicator.OnTabClickListener() {
                @Override
                public void onTabClick(String title, int position) {
                    transmitSessionType = position;
                    switch (position) {
                        case 0: {
                            getUserVOList(searchText.getText().toString());
                            break;
                        }
                        case 1: {
                            getGroupList(searchText.getText().toString());
                            break;
                        }
                        case 2: {
                            getChannelList(searchText.getText().toString());
                            break;
                        }
                        default: {
                        }
                    }
                }
            });
        }
    }

    private void initSearchType() {
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println(searchText.getText().toString());
            }
        });
    }

    private void initBottomLayout() {
        if (type.equals(ContactsUtil.ContactType.TYPE_CREATE_CONTACT.getCode())) {
            bottomLayout.setVisibility(View.VISIBLE);
        } else if (type.equals(ContactsUtil.ContactType.TYPE_TRANSMIT.getCode())) {
            bottomLayout.setVisibility(View.GONE);
        }
        bottomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(idList.toString());
                if (createMethod.equals(CREATE_GROUP)) {
                    createGroup(idList);
                } else if (createMethod.equals(CREATE_CHANNEL)) {
                    createChannel();
                }
            }
        });
    }

    private void createGroup(List<Integer> idList) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<Group> responseData =
                        GroupController.createGroupWithFriend(idList);
                if (responseData != null && responseData.isSuccess()) {
                    Group group = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Bundle bundle = new Bundle();
                            bundle.putInt(BundleUtil.OBJECT_ID_ATTRIBUTE, group.getId());
                            bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, ChatMsgUtil.Character.TYPE_GROUP.getCode());
                            Intent intent = new Intent(mContext, ChatActivity.class).putExtras(bundle);
                            finish();
                            startActivity(intent);
                        }
                    });
                }
            }
        }).start();
    }

    private void createChannel() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<Channel> responseData =
                        ChannelController.createChannelWithFriend(idList);
                if (responseData != null && responseData.isSuccess()) {
                    Channel channel = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Bundle bundle = new Bundle();
                            bundle.putInt(BundleUtil.OBJECT_ID_ATTRIBUTE, channel.getId());
                            bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, ChatMsgUtil.Character.TYPE_CHANNEL.getCode());
                            Intent intent = new Intent(mContext, ChatActivity.class).putExtras(bundle);
                            finish();
                            startActivity(intent);
                        }
                    });
                }
            }
        }).start();
    }

    private void initListView() {
        getUserVOList("");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (type.equals(ContactsUtil.ContactType.TYPE_CREATE_CONTACT.getCode())) {
                    SmoothCheckBox checkBox = view.findViewById(R.id.listview_friend_list_contact_vo_list_checkbox);
                    checkBox.setChecked(!checkBox.isChecked());
                } else {
                    transmitMessage(chatMsgId, transmitMsgType, contactVoAdapter.getItem(position).getObjectId());
                    XToastUtils.toast("转发成功!");
                    finish();
                }
            }
        });

    }

    private void transmitMessage(Integer chatMsgId, Integer type, Integer objectId) {

    }

    private void getUserVOList(String keyword) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<List<UserVO>> responseData =
                        UserController.getFriendListInfo(keyword);
                if (responseData != null && responseData.isSuccess()) {
                    userVOList = responseData.getData();
                    List<ContactVO> contactVOList = new ArrayList<>();
                    for (UserVO userVO : userVOList) {
                        contactVOList.add(ContactVO.convertUserVO(userVO));
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            contactVoAdapter = new ContactVoAdapter(mContext, contactVOList, type);
                            listView.setAdapter(contactVoAdapter);
                        }
                    });
                }
            }
        }).start();
    }

    private void getGroupList(String keyword) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<List<Group>> responseData =
                        GroupController.getGroupList(keyword);
                if (responseData != null && responseData.isSuccess()) {
                    groupList = responseData.getData();
                    List<ContactVO> contactVOList = new ArrayList<>();
                    for (Group group : groupList) {
                        contactVOList.add(ContactVO.convertGroup(group));
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            contactVoAdapter = new ContactVoAdapter(mContext, contactVOList, type);
                            listView.setAdapter(contactVoAdapter);
                        }
                    });
                }
            }
        }).start();
    }

    private void getChannelList(String keyword) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<List<Channel>> responseData = ChannelController.getChannelList(keyword);
                if (responseData != null && responseData.isSuccess()) {
                    channelList = responseData.getData();
                    List<ContactVO> contactVOList = new ArrayList<>();
                    for (Channel channel : channelList) {
                        contactVOList.add(ContactVO.convertChannel(channel));
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            contactVoAdapter = new ContactVoAdapter(mContext, contactVOList, type);
                            listView.setAdapter(contactVoAdapter);
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    protected void onStart() {
        super.onStart();
        idList = new ArrayList<>();
    }
}