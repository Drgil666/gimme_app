package com.project.gimme.view.fragment;

import static com.project.gimme.utils.BundleUtil.CHAT_TYPE_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_ID_ATTRIBUTE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.project.gimme.R;
import com.project.gimme.controller.UserController;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.pojo.vo.UserVO;
import com.project.gimme.pojo.vo.UserVoParamItem;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.FileUtil;
import com.project.gimme.utils.InfoTypeUtil;
import com.project.gimme.utils.NumberUtil;
import com.project.gimme.utils.UserUtil;
import com.project.gimme.utils.XToastUtils;
import com.project.gimme.view.activity.ChatActivity;
import com.project.gimme.view.adpter.FriendInfoAdapter;
import com.squareup.picasso.Picasso;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lombok.SneakyThrows;

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
    @BindView(R.id.fragment_friend_info_chat_button)
    Button chatButton;
    @BindView(R.id.fragment_friend_info_add_button)
    Button addButton;
    private Boolean isJoined;
    private Unbinder unbinder;
    Handler handler = new Handler();
    FriendInfoAdapter friendInfoAdapter;
    @BindView(R.id.fragment_friend_img)
    ImageView imageView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        getType();
        getUserVO();
        initImageView();
        initTopBar();
        initButton();
        return view;
    }

    private void getType() {
        Bundle bundle = getActivity().getIntent().getExtras();
        type = bundle.getInt(CHAT_TYPE_ATTRIBUTE);
        objectId = bundle.getInt(OBJECT_ID_ATTRIBUTE);
        isJoined = bundle.getBoolean(BundleUtil.IS_JOINED_ATTRIBUTE);
        System.out.println("type:" + type + " object_id:" + objectId + " is joined:" + isJoined);
    }

    private void initButton() {
        if (isJoined) {
            addButton.setVisibility(View.GONE);
            chatButton.setVisibility(View.VISIBLE);
            if (!(type.equals(InfoTypeUtil.Character.TYPE_SELF.getCode())
                    || type.equals(InfoTypeUtil.Character.TYPE_CHANNEL_SELF.getCode())
                    || type.equals(InfoTypeUtil.Character.TYPE_GROUP_SELF.getCode()))) {
                chatButton.setOnClickListener(view -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt(CHAT_TYPE_ATTRIBUTE, type);
                    bundle.putInt(OBJECT_ID_ATTRIBUTE, objectId);
                    Intent intent = new Intent(getContext(), ChatActivity.class).putExtras(bundle);
                    startActivity(intent);
                });
            } else {
                chatButton.setVisibility(View.GONE);
            }
        } else {
            chatButton.setVisibility(View.GONE);
            addButton.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initTopBar() {
        Picasso.with(getContext()).load(R.mipmap.default_icon).into(icon);
        icon.setOnTouchListener((view, motionEvent) -> {
//            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                ((ImageView) view).setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY); // 设置滤镜效果
//            } else {
//                ((ImageView) view).clearColorFilter(); // 清除滤镜效果
//            }
            //TODO:滤镜需要重写
            Picasso.with(getContext()).load(R.mipmap.default_icon).into(imageView);
            imageView.setVisibility(View.VISIBLE);
            return false;//如果return true的话,onClick的事件就不会触发!
        });
    }

    private void initImageView() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.alpha1to0));
                imageView.setVisibility(View.GONE);
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new BottomSheet.BottomListSheetBuilder(getContext())
                        .addItem("保存到相册")
                        .setIsCenter(true)
                        .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                            dialog.dismiss();
                            FileUtil.saveImageToGallery(getContext(), ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                            XToastUtils.toast("保存成功!");
                        })
                        .build()
                        .show();
                return true;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getUserVO() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<UserVO> responseData =
                        UserController.getUserVO(objectId.toString(), ChatMsgUtil.Character.TYPE_FRIEND.getName(), "");
                if (responseData != null && responseData.isSuccess()) {
                    userVO = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (StringUtils.isEmpty(userVO.getNote())) {
                                nick.setText(userVO.getNick());
                            } else {
                                nick.setText(userVO.getNote());
                            }
                            company.setText(userVO.getCompany());
                            motto.setText(userVO.getMotto());
                            List<UserVoParamItem> itemList = getItemList();
                            friendInfoAdapter = new FriendInfoAdapter(getContext(), itemList);
                            listView.setAdapter(friendInfoAdapter);
                        }
                    });
                }
            }
        }).start();
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
            XToastUtils.toast("类型错误!");
        }
        return null;
    }
}