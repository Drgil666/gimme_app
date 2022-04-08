package com.project.gimme.view.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.project.gimme.R;
import com.project.gimme.controller.PersonalMsgController;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ContactsUtil;
import com.project.gimme.utils.PersonalMsgUtil;
import com.project.gimme.view.activity.PersonalMsgListActivity;
import com.project.gimme.view.activity.SearchActivity;
import com.project.gimme.view.adpter.PersonalMsgAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

/**
 * @author DrGilbert
 */
@SuppressLint("NonConstantResourceId")
public class FriendFragment extends Fragment {
    private List<String> listItem = new ArrayList<>();
    @BindView(R.id.friend_personal_msg_list_view)
    ListView personalMsgListView;
    @BindView(R.id.friend_list_search_edittext)
    EditText searchEditText;
    private List<Long> cntList = new ArrayList<>();
    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        ButterKnife.bind(this, view);
        cntList = new ArrayList<>();
        cntList.add(0L);
        cntList.add(0L);
        personalMsgListView.setAdapter(new PersonalMsgAdapter(getContext(), listItem, cntList));
        initListView();
        initSearchEditText();
        return view;
    }

    private void initSearchEditText() {
        searchEditText.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(BundleUtil.SEARCH_TYPE_ATTRIBUTE, ContactsUtil.SearchType.TYPE_CONTACTS.getCode());
                    Intent intent = new Intent(getContext(), SearchActivity.class).putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(BundleUtil.SEARCH_TYPE_ATTRIBUTE, ContactsUtil.SearchType.TYPE_CONTACTS.getCode());
                Intent intent = new Intent(getContext(), SearchActivity.class).putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void getBadge() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                cntList = new ArrayList<>();
                ResponseData<Long> responseData =
                        PersonalMsgController.getNewPersonalMsgListCount(PersonalMsgUtil.PersonalMsgType.TYPE_FRIEND_PERSONAL_MSG.getCode());
                if (responseData != null && responseData.isSuccess()) {
                    cntList.add(responseData.getData());
                    responseData = PersonalMsgController.getNewPersonalMsgListCount(PersonalMsgUtil.PersonalMsgType.TYPE_OTHER_PERSONAL_MSG.getCode());
                    if (responseData != null && responseData.isSuccess()) {
                        cntList.add(responseData.getData());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                personalMsgListView.setAdapter(new PersonalMsgAdapter(getContext(), listItem, cntList));
                            }
                        });
                    }
                }
            }
        }).start();
    }

    private void getListItem() {
        listItem.clear();
        listItem.add("新朋友");
        listItem.add("群聊/频道消息");
    }

    private void initListView() {
        getListItem();
        getBadge();
        personalMsgListView.setOnItemClickListener((parent, view, position, id) -> {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleUtil.PERSONAL_MSG_TYPE_ATTRIBUTE, position);
            Intent intent = new Intent(getActivity(), PersonalMsgListActivity.class).putExtras(bundle);
            startActivity(intent);
        });
    }
}