package com.project.gimme.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.project.gimme.R;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.view.activity.PersonalMsgListActivity;
import com.project.gimme.view.adpter.PersonalMsgAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author DrGilbert
 */
public class FriendFragment extends Fragment {
    private List<String> listItem = new ArrayList<>();
    @BindView(R.id.friend_personal_msg_list_view)
    ListView personalMsgListView;
    private Unbinder unbinder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        System.out.println("friendFragment");
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        unbinder = ButterKnife.bind(this, view);
        initListView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getListItem() {
        listItem.add("新朋友");
        listItem.add("群聊/频道消息");
    }

    private void initListView() {
        getListItem();
        personalMsgListView.setAdapter(new PersonalMsgAdapter(getContext(), listItem));
        personalMsgListView.setOnItemClickListener((parent, view, position, id) -> {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleUtil.PERSONAL_MSG_TYPE_ATTRIBUTE, position);
            Intent intent = new Intent(getActivity(), PersonalMsgListActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        });
    }
}