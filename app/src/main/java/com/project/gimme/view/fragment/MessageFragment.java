package com.project.gimme.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.pojo.vo.MessageVO;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.view.activity.ChatActivity;
import com.project.gimme.view.adpter.MessageVoAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author DrGilbert
 */
public class MessageFragment extends Fragment {
    private List<MessageVO> messageVOList = new ArrayList<>();
    private ListView listView;
    private RelativeLayout searchLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        System.out.println("messageFragment");
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        listView = view.findViewById(R.id.message_list_view);
        searchLayout = view.findViewById(R.id.message_search_layout);
        initSearchLayout(0.07);
        initListView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initSearchLayout(double size) {
        searchLayout.getLayoutParams().height = (int) (GimmeApplication.getHeight() * size);
    }

    private void getMessageVOList() {
        for (int i = 1; i <= 10; i++) {
            MessageVO messageVO = new MessageVO();
            messageVO.setText("这是一条消息这是一条消息这是一条消息");
            messageVO.setObjectId(i);
            messageVO.setNick("nick" + i);
            messageVO.setAvatar("avatar" + i);
            if (i < 3) {
                messageVO.setType(ChatMsgUtil.Character.TYPE_FRIEND.getCode());
            } else if (i < 6) {
                messageVO.setType(ChatMsgUtil.Character.TYPE_GROUP.getCode());
            } else {
                messageVO.setType(ChatMsgUtil.Character.TYPE_CHANNEL.getCode());
            }
            messageVO.setTimestamp(new Date());
            messageVOList.add(messageVO);
        }
    }

    private void initListView() {
        getMessageVOList();
        listView.setAdapter(new MessageVoAdapter(getContext(), messageVOList));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Integer type = ((MessageVoAdapter) listView.getAdapter()).getItem(position).getType();
//            System.out.println("click");
            Bundle bundle = createBundle((int) id, type);
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        });
    }

    private Bundle createBundle(Integer id, Integer type) {
        Bundle bundle = new Bundle();
        bundle.putInt("objectId", id);
        bundle.putInt("type", type);
        return bundle;
    }

}