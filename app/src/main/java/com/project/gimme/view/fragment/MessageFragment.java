package com.project.gimme.view.fragment;

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
        getMessageVOList();
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        listView = view.findViewById(R.id.message_list_view);
        searchLayout = view.findViewById(R.id.message_search_layout);
        initSearchLayout(0.07);
        listView.setAdapter(new MessageVoAdapter(getContext(), messageVOList));
        initListView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("destroy1!");
    }

    private void initSearchLayout(double size) {
        searchLayout.getLayoutParams().height = (int) (GimmeApplication.getHeight() * size);
    }

    private void getMessageVOList() {
        MessageVO messageVO = new MessageVO();
        messageVO.setText("这是一条消息这是一条消息这是一条消息");
        messageVO.setId(12345);
        messageVO.setNick("nick1");
        messageVO.setAvatar("avatar1");
        messageVO.setTimestamp(new Date());
        for (int i = 1; i <= 10; i++) {
            messageVOList.add(messageVO);
        }
    }

    private void initListView() {
        listView.setOnItemClickListener((parent, view, position, id) -> {
            System.out.println("tag: " + view.getTag() + "pos: " + position + "id: " + id);
        });
    }
}