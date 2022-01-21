package com.project.gimme.view.fragment;

import static com.project.gimme.utils.BundleUtil.OBJECTID_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.TYPE_ATTRIBUTE;

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
    private TextView introductionGroupNoticeLeftNick;
    private TextView introductionGroupNoticeLeftText;
    private RelativeLayout introductionGroupNoticeLayout;
    private ChannelNotice channelNotice = new ChannelNotice();

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
        introductionGroupNoticeLeftNick = view.findViewById(R.id.fragment_other_info_introduction_group_notice_nick);
        introductionGroupNoticeLeftText = view.findViewById(R.id.fragment_other_info_introduction_group_notice_text);
        introductionGroupNoticeLayout = view.findViewById(R.id.fragment_other_info_introduction_group_notice_layout);
        getType();
        initTopBar();
        initMember();
        initIntroduction();
        return view;
    }

    private void getType() {
        Bundle bundle = getActivity().getIntent().getExtras();
        type = bundle.getInt(TYPE_ATTRIBUTE);
        objectId = bundle.getInt(OBJECTID_ATTRIBUTE);
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
            memberRight.setText("查看" + groupVO.getTotalCount() + "名频道成员");
            initGridView();
        } else {
            Toast.makeText(getContext(), "类型错误!", Toast.LENGTH_LONG).show();
        }
    }

    private void initIntroduction() {
        if (type.equals(ChatMsgUtil.Character.TYPE_GROUP.getCode())) {
            introductionIdLeft.setText("群聊号与二维码");
            introductionIdRight.setText(groupVO.getId().toString());
            introductionGroupNoticeLayout.setVisibility(View.VISIBLE);
            introductionGroupNoticeLeftNick.setText("群公告");
            getChannelNotice(groupVO.getId());
            introductionGroupNoticeLeftText.setText(channelNotice.getText());
        } else if (type.equals(ChatMsgUtil.Character.TYPE_CHANNEL.getCode())) {
            introductionIdLeft.setText("频道号与二维码");
            introductionIdRight.setText(groupVO.getId());
            introductionGroupNoticeLayout.setVisibility(View.GONE);
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
}