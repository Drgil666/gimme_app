package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.gimme.R;
import com.project.gimme.controller.ChannelController;
import com.project.gimme.controller.GroupController;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.pojo.vo.UserVO;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.JsonUtil;
import com.project.gimme.utils.UserUtil;
import com.project.gimme.view.adpter.OtherMemberListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

/**
 * @author 25741
 */
@SuppressLint("NonConstantResourceId")
public class OtherMemberListActivity extends SwipeBackActivity {
    @BindView(R.id.other_member_list_top_text)
    TextView topText;
    @BindView(R.id.other_member_list_top_left_button)
    ImageView topLeftButton;
    @BindView(R.id.other_member_list_search_edittext)
    EditText searchText;
    @BindView(R.id.other_member_list_listview)
    ListView listview;
    private Integer type;
    private Handler handler = new Handler();
    private Integer objectId;
    private Context mContext = this;
    private List<UserVO> userVOList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_member_list);
        ButterKnife.bind(this);
        getType();
        initTopBar();
        initListView();
    }

    private void getType() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(BundleUtil.CHAT_TYPE_ATTRIBUTE);
        objectId = bundle.getInt(BundleUtil.OBJECT_ID_ATTRIBUTE);
    }

    private void initTopBar() {
        topLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            topText.setText("群聊成员");
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            topText.setText("频道成员");
        }
    }

    private void getListView() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<List<UserVO>> responseData = null;
                if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
                    responseData = GroupController.getGroupMemberList(objectId.toString(), null);
                } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
                    responseData = ChannelController.getChannelMemberList(objectId.toString(), null);
                }
                if (responseData != null && responseData.isSuccess()) {
                    userVOList = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listview.setAdapter(new OtherMemberListAdapter(mContext, userVOList));
                        }
                    });
                }
            }
        }).start();
    }

    private void initListView() {
        getListView();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserVO userVO = userVOList.get(position);
                Bundle bundle = new Bundle();
                bundle.putInt(BundleUtil.OBJECT_ID_ATTRIBUTE, userVO.getId());
                if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
                    bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, UserUtil.GroupCharacter.TYPE_GROUP_USER.getCode());
                } else {
                    bundle.putInt(BundleUtil.CHAT_TYPE_ATTRIBUTE, UserUtil.ChannelCharacter.TYPE_CHANNEL_USER.getCode());
                }
                bundle.putString(BundleUtil.OBJECT_ATTRIBUTE, JsonUtil.toJson(userVO));
                bundle.putInt(BundleUtil.OTHER_ID_ATTRIBUTE, objectId);
                bundle.putBoolean(BundleUtil.IS_JOINED_ATTRIBUTE, false);
                Intent intent = new Intent(mContext, FriendInfoActivity.class).putExtras(bundle);
                mContext.startActivity(intent);
            }
        });
    }
}