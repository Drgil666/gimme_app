package com.project.gimme.view.fragment;

import static com.project.gimme.utils.BundleUtil.CHAT_TYPE_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_ID_ATTRIBUTE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.project.gimme.R;
import com.project.gimme.pojo.vo.UserVO;
import com.project.gimme.pojo.vo.UserVoParamItem;
import com.project.gimme.utils.UserUtil;
import com.project.gimme.view.adpter.FriendInfoAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 */
public class FriendInfoFragment extends Fragment {
    private Integer type;
    private Integer objectId;
    @BindView(R.id.fragment_friend_info_nick)
    TextView nick;
    @BindView(R.id.fragment_friend_info_company)
    TextView company;
    @BindView(R.id.fragment_friend_info_motto)
    TextView motto;
    private UserVO userVO = new UserVO();
    @BindView(R.id.fragment_friend_info_listview)
    ListView listView;
    @BindView(R.id.fragment_friend_info_button)
    Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_friend_info, container, false);
        ButterKnife.bind(this, view);
        getType();
        getUserVO();
        initTopBar();
        initListView();
        initButton();
        return view;
    }

    private void getType() {
        Bundle bundle = getActivity().getIntent().getExtras();
        type = bundle.getInt(CHAT_TYPE_ATTRIBUTE);
        objectId = bundle.getInt(OBJECT_ID_ATTRIBUTE);
    }

    private void initButton() {
        button.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putInt(CHAT_TYPE_ATTRIBUTE, type);
            bundle.putInt(OBJECT_ID_ATTRIBUTE, objectId);
            getActivity().finish();
            getActivity().overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
        });
    }

    private void initTopBar() {
        nick.setText(userVO.getNick());
        company.setText(userVO.getCompany());
        motto.setText(userVO.getMotto());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initListView() {
        List<UserVoParamItem> itemList = getUserVParamItemList();
        listView.setAdapter(new FriendInfoAdapter(getActivity(), itemList));
    }

    private void getUserVO() {
        userVO.setId(objectId);
        userVO.setNick("用户昵称" + objectId);
        userVO.setMotto("用户个性签名" + objectId);
        userVO.setBirthday(new Date());
        userVO.setCompany("公司" + objectId);
        userVO.setAvatar("test1");
        userVO.setGender(0);
        userVO.setProvince(1);
        userVO.setCity(1);
        userVO.setCountry(1);
        userVO.setMail("xxx@qq.com");
        userVO.setOccupation(1);
        userVO.setNote("备注" + objectId);
    }

    private List<UserVoParamItem> getUserVParamItemList() {
        List<UserVoParamItem> itemList = new ArrayList<>();
        UserVoParamItem item = new UserVoParamItem("备注", userVO.getNote(), true);
        itemList.add(item);
        item = new UserVoParamItem("Gimme号", userVO.getId().toString(), true);
        itemList.add(item);
        item = new UserVoParamItem("性别", UserUtil.GENDER_LIST[userVO.getGender()].getName(), false);
        itemList.add(item);
        item = new UserVoParamItem("昵称", userVO.getNick(), false);
        itemList.add(item);
        item = new UserVoParamItem("邮箱", userVO.getMail(), false);
        itemList.add(item);
        return itemList;
    }
}