package com.project.gimme.view.fragment;

import static com.project.gimme.utils.BundleUtil.CHAT_TYPE_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_ID_ATTRIBUTE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.project.gimme.R;
import com.project.gimme.pojo.ChannelNotice;
import com.project.gimme.pojo.vo.ChannelVO;
import com.project.gimme.pojo.vo.GroupVO;
import com.project.gimme.pojo.vo.UserVO;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.view.activity.ChatFileActivity;
import com.project.gimme.view.activity.QrActivity;
import com.project.gimme.view.adpter.OtherInfoAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author DrGilbert
 */
public class OtherInfoFragment extends Fragment {
    private Integer type;
    private Integer objectId;
    private GroupVO groupVO = new GroupVO();
    private ChannelVO channelVO = new ChannelVO();
    private TextView topBarNick;
    private TextView topBarDescription;
    private TextView memberLeft;
    private TextView memberRight;
    private GridView gridView;
    private List<UserVO> userVOList = new ArrayList<>();
    private TextView introductionIdLeft;
    private TextView introductionIdRight;
    private RelativeLayout introductionIdLayout;
    private TextView introductionGroupNoticeLeftNick;
    private TextView introductionGroupNoticeLeftText;
    private RelativeLayout introductionGroupNoticeLayout;
    private ChannelNotice channelNotice = new ChannelNotice();
    private RelativeLayout introductionGroupFileLayout;
    private RelativeLayout myLayout;
    private RelativeLayout myChatMsg;
    private RelativeLayout myNote;
    private TextView myNoteRightText;
    private TextView myNoteLeftText;
    private UserVO myUserVO = new UserVO();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_info, container, false);
        topBarNick = view.findViewById(R.id.fragment_other_info_top_nick);
        topBarDescription = view.findViewById(R.id.fragment_other_info_top_description);
        memberLeft = view.findViewById(R.id.fragment_other_info_member_left_text);
        memberRight = view.findViewById(R.id.fragment_other_info_member_right_text);
        gridView = view.findViewById(R.id.fragment_other_info_member_gridview);
        introductionIdLeft = view.findViewById(R.id.fragment_other_info_introduction_left_text);
        introductionIdRight = view.findViewById(R.id.fragment_other_info_introduction_right_text);
        introductionIdLayout = view.findViewById(R.id.fragment_other_info_introduction_id_layout);
        introductionGroupNoticeLeftNick = view.findViewById(R.id.fragment_other_info_introduction_group_notice_nick);
        introductionGroupNoticeLeftText = view.findViewById(R.id.fragment_other_info_introduction_group_notice_text);
        introductionGroupNoticeLayout = view.findViewById(R.id.fragment_other_info_introduction_group_notice_layout);
        introductionGroupFileLayout = view.findViewById(R.id.fragment_other_info_introduction_group_file_layout);
        myLayout = view.findViewById(R.id.fragment_other_info_my_layout);
        myChatMsg = view.findViewById(R.id.fragment_other_info_my_chat_msg);
        myNote = view.findViewById(R.id.fragment_other_info_my_note);
        myNoteRightText = view.findViewById(R.id.fragment_other_info_introduction_note_right_text);
        myNoteLeftText = view.findViewById(R.id.fragment_other_info_introduction_note_left_text);
        getType();
        initTopBar();
        initMember();
        initIntroduction();
        initMyLayout();
        return view;
    }

    private void getType() {
        Bundle bundle = getActivity().getIntent().getExtras();
        type = bundle.getInt(CHAT_TYPE_ATTRIBUTE);
        objectId = bundle.getInt(OBJECT_ID_ATTRIBUTE);
    }

    private void initTopBar() {
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            getGroupVO(objectId);
            topBarNick.setText(groupVO.getNick());
            topBarDescription.setText(groupVO.getDescription());
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            getChannelVO(objectId);
            topBarNick.setText(channelVO.getNick());
            topBarDescription.setText(channelVO.getDescription());
        } else {
            Toast.makeText(getContext(), "类型错误!", Toast.LENGTH_LONG).show();
        }
    }

    private void initMember() {
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            memberLeft.setText("群聊成员");
            memberRight.setText("查看" + groupVO.getTotalCount() + "名群成员");
            initGridView();
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            memberLeft.setText("频道成员");
            memberRight.setText("查看" + channelVO.getTotalCount() + "名频道成员");
            initGridView();
        } else {
            Toast.makeText(getContext(), "类型错误!", Toast.LENGTH_LONG).show();
        }
    }

    private void initIntroduction() {
        introductionIdLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(CHAT_TYPE_ATTRIBUTE, type);
                bundle.putInt(OBJECT_ID_ATTRIBUTE, objectId);
                Intent intent = new Intent(getActivity(), QrActivity.class).putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
            }
        });
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            introductionIdLeft.setText("群聊号与二维码");
            introductionIdRight.setText(groupVO.getId().toString());
            introductionGroupNoticeLayout.setVisibility(View.VISIBLE);
            introductionGroupFileLayout.setVisibility(View.VISIBLE);
            introductionGroupNoticeLeftNick.setText("群公告");
            getChannelNotice(groupVO.getId());
            introductionGroupNoticeLeftText.setText(channelNotice.getText());
            introductionGroupFileLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(CHAT_TYPE_ATTRIBUTE, type);
                    bundle.putInt(OBJECT_ID_ATTRIBUTE, objectId);
                    Intent intent = new Intent(getActivity(), ChatFileActivity.class).putExtras(bundle);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
                }
            });
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            introductionIdLeft.setText("频道号与二维码");
            introductionIdRight.setText(channelVO.getId().toString());
            introductionGroupNoticeLayout.setVisibility(View.GONE);
            introductionGroupFileLayout.setVisibility(View.GONE);
        } else {
            Toast.makeText(getContext(), "类型错误!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void getChannelNotice(Integer objectId) {
        channelNotice.setId(1);
        channelNotice.setChannelId(objectId);
        channelNotice.setType(1);
        channelNotice.setText("这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息这是一条消息");
    }

    private void getGroupVO(Integer objectId) {
        groupVO.setId(objectId);
        groupVO.setCreateTime(new Date());
        groupVO.setNick("群聊" + objectId);
        groupVO.setTotalCount(20);
        groupVO.setDescription("群介绍" + objectId);
    }

    private void getChannelVO(Integer objectId) {
        channelVO.setCreateTime(new Date());
        channelVO.setOwnerId(1);
        channelVO.setNick("频道" + objectId);
        channelVO.setTotalCount(20);
        channelVO.setId(objectId);
    }

    private void getUserVoList(Integer type, Integer objectId) {
        for (int i = 1; i <= 9; i++) {
            UserVO userVO = new UserVO();
            userVO.setId(i);
            if (i % 3 == 1) {
                userVO.setNote("备注" + i);
            }
            userVO.setNick("用户" + i);
            userVO.setAvatar("null");
            userVOList.add(userVO);
        }
        UserVO userVO = new UserVO();
        userVO.setNick("邀请");
        userVO.setId(-1);
        userVOList.add(userVO);
    }

    private void initGridView() {
        getUserVoList(type, objectId);
        gridView.setAdapter(new OtherInfoAdapter(getContext(), userVOList));
    }

    private void getUserVO(Integer type, Integer objectId) {
        myUserVO.setId(1);
        myUserVO.setNick("111");
        myUserVO.setAvatar(null);
        myUserVO.setGender(1);
        myUserVO.setNote("我的备注");
    }

    private void initMyLayout() {
        myChatMsg.setOnClickListener(view -> System.out.println("click!"));
        getUserVO(type, objectId);
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            myNoteRightText.setText(myUserVO.getNote());
            myNoteLeftText.setText("我的群昵称");
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            myNoteRightText.setText(myUserVO.getNote());
            myNoteLeftText.setText("我的频道昵称");
        }
        myNote.setOnClickListener(view -> System.out.println("click!"));
    }
}