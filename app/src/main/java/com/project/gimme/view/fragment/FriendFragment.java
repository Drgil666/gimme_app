package com.project.gimme.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.project.gimme.R;
import com.project.gimme.view.adpter.PersonalMsgAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DrGilbert
 */
public class FriendFragment extends Fragment {
    private List<String> listItem = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        getListItem();
        ListView personalMsgListView = view.findViewById(R.id.friend_personal_msg_list_view);
        personalMsgListView.setAdapter(new PersonalMsgAdapter(getContext(), listItem));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void getListItem() {
        listItem.add("新朋友");
        listItem.add("群/频道消息");
    }
}