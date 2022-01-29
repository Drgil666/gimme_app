package com.project.gimme.view.fragment;

import static com.project.gimme.utils.BundleUtil.CHAT_TYPE_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_ID_ATTRIBUTE;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author DrGilbert
 */
public class MessageFragment extends Fragment {
    private List<MessageVO> messageVOList = new ArrayList<>();
    @BindView(R.id.message_list_view)
    ListView listView;
    @BindView(R.id.message_search_layout)
    RelativeLayout searchLayout;
    private Unbinder unbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        initSearchLayout(0.07);
        initListView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initSearchLayout(double size) {
        searchLayout.getLayoutParams().height = (int) (GimmeApplication.getHeight() * size);
    }

    private void getMessageVOList() {
        for (int i = 1; i <= 10; i++) {
            MessageVO messageVO = new MessageVO();
            messageVO.setText("这是一条消息这是一条消息这是一条消息");
            messageVO.setObjectId(i);
            messageVO.setAvatar("avatar" + i);
            if (i < 3) {
                messageVO.setNick("好友" + i);
                messageVO.setType(ChatMsgUtil.Character.TYPE_FRIEND.getCode());
            } else if (i < 6) {
                messageVO.setNick("群聊" + i);
                messageVO.setType(ChatMsgUtil.Character.TYPE_GROUP.getCode());
            } else {
                messageVO.setNick("频道" + i);
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
            Bundle bundle = new Bundle();
            bundle.putInt(OBJECT_ID_ATTRIBUTE, (int) id);
            bundle.putInt(CHAT_TYPE_ATTRIBUTE, type);
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        });
    }

}