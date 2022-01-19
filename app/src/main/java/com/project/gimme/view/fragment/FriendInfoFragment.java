package com.project.gimme.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.project.gimme.R;
import com.project.gimme.pojo.vo.UserVO;
import com.project.gimme.view.adpter.FriendInfoAdapter;

import java.util.Date;

/**
 * @author DrGilbert
 */
public class FriendInfoFragment extends Fragment {
    private Integer type;
    private Integer objectId;
    private TextView nick;
    private TextView company;
    private TextView motto;
    private UserVO userVO;
    private ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_friend_info, container, false);
        getType();
        nick = view.findViewById(R.id.fragment_friend_info_nick);
        company = view.findViewById(R.id.fragment_friend_info_company);
        motto = view.findViewById(R.id.fragment_friend_info_motto);
        listView = view.findViewById(R.id.fragment_friend_info_listview);
        initListView();
        return view;
    }

    private void getType() {
        Bundle bundle = getActivity().getIntent().getExtras();
        type = bundle.getInt("type");
        objectId = bundle.getInt("object_id");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initListView() {
        getUser();
        listView.setAdapter(new FriendInfoAdapter(getActivity(), userVO));
    }

    private void getUser() {
        userVO.setId(objectId);
        userVO.setNick("用户昵称" + objectId);
        userVO.setMotto("用户个性签名" + objectId);
        userVO.setBirthday(new Date());
        userVO.setCompany("公司" + objectId);
        userVO.setAvatar("test1");
        userVO.setProvince(1);
        userVO.setCity(1);
        userVO.setCountry(1);
        userVO.setMail("xxx@qq.com");
        userVO.setOccupation(1);
        userVO.setNote("备注" + objectId);
    }
}