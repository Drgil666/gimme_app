package com.project.gimme.view.fragment;

import static com.project.gimme.utils.BundleUtil.CHAT_TYPE_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_ID_ATTRIBUTE;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.project.gimme.R;
import com.project.gimme.pojo.vo.UserVO;
import com.project.gimme.pojo.vo.UserVoParamItem;
import com.project.gimme.utils.InfoTypeUtil;
import com.project.gimme.utils.NumberUtil;
import com.project.gimme.utils.UserUtil;
import com.project.gimme.view.adpter.FriendInfoAdapter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author DrGilbert
 */
@SuppressLint("NonConstantResourceId")
public class FriendInfoFragment extends Fragment {
    private Integer type;
    private Integer objectId;
    @BindView(R.id.fragment_friend_info_icon)
    ImageView icon;
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
    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_friend_info, container, false);
        unbinder = ButterKnife.bind(this, view);
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
        System.out.println("type:" + type + " id:" + objectId);
    }

    private void initButton() {
        if (!(type.equals(InfoTypeUtil.Character.TYPE_SELF.getCode())
                || type.equals(InfoTypeUtil.Character.TYPE_CHANNEL_SELF.getCode())
                || type.equals(InfoTypeUtil.Character.TYPE_GROUP_SELF.getCode()))) {
            button.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putInt(CHAT_TYPE_ATTRIBUTE, type);
                bundle.putInt(OBJECT_ID_ATTRIBUTE, objectId);
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
            });
        } else {
            button.setVisibility(View.GONE);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTopBar() {
        icon.setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ((ImageView) view).setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY); // 设置滤镜效果
            } else {
                ((ImageView) view).clearColorFilter(); // 清除滤镜效果
            }
            return false;//如果return true的话,onClick的事件就不会触发!
        });
        nick.setText(userVO.getNick());
        company.setText(userVO.getCompany());
        motto.setText(userVO.getMotto());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initListView() {
        List<UserVoParamItem> itemList = getItemList();
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
        userVO.setCountryNick("中国");
        userVO.setProvinceNick("浙江");
        userVO.setCityNick("杭州");
        userVO.setOccupationNick("程序员");
        userVO.setOtherNick("其他昵称");
    }

    private List<UserVoParamItem> getFriendItemList() {
        List<UserVoParamItem> itemList = new ArrayList<>();
        UserVoParamItem item = new UserVoParamItem("备注", userVO.getNote(), true);
        itemList.add(item);
        item = new UserVoParamItem("Gimme号", userVO.getId().toString(), false);
        itemList.add(item);
        item = new UserVoParamItem("性别", UserUtil.GENDER_LIST[userVO.getGender()].getName(), false);
        itemList.add(item);
        item = new UserVoParamItem("昵称", userVO.getNick(), false);
        itemList.add(item);
        item = new UserVoParamItem("邮箱", userVO.getMail(), false);
        itemList.add(item);
        return itemList;
    }

    private List<UserVoParamItem> getOwnerItemList() {
        List<UserVoParamItem> itemList = new ArrayList<>();
        UserVoParamItem item = new UserVoParamItem("昵称", userVO.getNick(), true);
        itemList.add(item);
        item = new UserVoParamItem("Gimme号", userVO.getId().toString(), false);
        itemList.add(item);
        if (type.equals(InfoTypeUtil.Character.TYPE_GROUP_SELF.getCode())) {
            item = new UserVoParamItem("群昵称", userVO.getNote(), false);
            itemList.add(item);
        } else if (type.equals(InfoTypeUtil.Character.TYPE_CHANNEL_SELF.getCode())) {
            item = new UserVoParamItem("频道昵称", userVO.getNote(), false);
            itemList.add(item);
        }
        item = new UserVoParamItem("性别", UserUtil.GENDER_LIST[userVO.getGender()].getName(), true);
        itemList.add(item);
        item = new UserVoParamItem("邮箱", userVO.getMail(), true);
        itemList.add(item);
        item = new UserVoParamItem("生日", NumberUtil.changeToYearAndMonthAndDay(userVO.getBirthday()), true);
        itemList.add(item);
        item = new UserVoParamItem("所在地", userVO.getCountryNick() + "-" + userVO.getProvinceNick() + "-" + userVO.getCityNick(), true);
        itemList.add(item);
        item = new UserVoParamItem("职业", userVO.getOccupationNick(), true);
        itemList.add(item);
        return itemList;
    }

    private List<UserVoParamItem> getGroupItemList() {
        List<UserVoParamItem> itemList = new ArrayList<>();
        UserVoParamItem item;
        if (!StringUtils.isEmpty(userVO.getNote())) {
            item = new UserVoParamItem("备注", userVO.getNote(), true);
            itemList.add(item);
        }
        item = new UserVoParamItem("昵称", userVO.getNick(), false);
        itemList.add(item);
        item = new UserVoParamItem("Gimme号", userVO.getId().toString(), false);
        itemList.add(item);
        item = new UserVoParamItem("性别", UserUtil.GENDER_LIST[userVO.getGender()].getName(), false);
        itemList.add(item);
        item = new UserVoParamItem("群昵称", userVO.getOtherNick(), false);
        itemList.add(item);
        item = new UserVoParamItem("邮箱", userVO.getMail(), false);
        itemList.add(item);
        return itemList;
    }

    private List<UserVoParamItem> getChannelItemList() {
        List<UserVoParamItem> itemList = new ArrayList<>();
        UserVoParamItem item;
        if (!StringUtils.isEmpty(userVO.getNote())) {
            item = new UserVoParamItem("备注", userVO.getNote(), true);
            itemList.add(item);
        }
        item = new UserVoParamItem("昵称", userVO.getNick(), false);
        itemList.add(item);
        item = new UserVoParamItem("Gimme号", userVO.getId().toString(), false);
        itemList.add(item);
        item = new UserVoParamItem("性别", UserUtil.GENDER_LIST[userVO.getGender()].getName(), false);
        itemList.add(item);
        item = new UserVoParamItem("频道昵称", userVO.getOtherNick(), false);
        itemList.add(item);
        item = new UserVoParamItem("邮箱", userVO.getMail(), false);
        itemList.add(item);
        return itemList;
    }

    private List<UserVoParamItem> getItemList() {
        if (type.equals(InfoTypeUtil.Character.TYPE_SELF.getCode())
                || type.equals(InfoTypeUtil.Character.TYPE_GROUP_SELF.getCode())
                || type.equals(InfoTypeUtil.Character.TYPE_CHANNEL_SELF.getCode())) {
            return getOwnerItemList();
        } else if (type.equals(InfoTypeUtil.Character.TYPE_FRIEND.getCode())) {
            return getFriendItemList();
        } else if (type.equals(InfoTypeUtil.Character.TYPE_GROUP_MEMBER.getCode())) {
            return getGroupItemList();
        } else if (type.equals(InfoTypeUtil.Character.TYPE_CHANNEL_MEMBER.getCode())) {
            return getChannelItemList();
        } else {
            Toast.makeText(getActivity(), "类型错误!", Toast.LENGTH_SHORT).show();
        }
        return null;
    }
}