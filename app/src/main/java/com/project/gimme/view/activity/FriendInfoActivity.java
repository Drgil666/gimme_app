package com.project.gimme.view.activity;

import static com.project.gimme.utils.BundleUtil.CHAT_TYPE_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_ID_ATTRIBUTE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.UserController;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.pojo.vo.UserVO;
import com.project.gimme.pojo.vo.UserVoParamItem;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.FileUtil;
import com.project.gimme.utils.InfoTypeUtil;
import com.project.gimme.utils.JsonUtil;
import com.project.gimme.utils.NumberUtil;
import com.project.gimme.utils.ParamItemUtil;
import com.project.gimme.utils.UserUtil;
import com.project.gimme.utils.XToastUtils;
import com.project.gimme.view.adpter.MyInformationItemAdapter;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

@SuppressLint("NonConstantResourceId")
public class FriendInfoActivity extends SwipeBackActivity {
    private final Integer height = GimmeApplication.getHeight();
    private Boolean isJoined;
    @BindView(R.id.friend_info_top_left_button)
    ImageView leftButton;
    @BindView(R.id.friend_info_top_bar)
    RelativeLayout topBar;
    private Integer type;
    private Integer objectId;
    @BindView(R.id.friend_info_icon)
    ImageView icon;
    @BindView(R.id.friend_info_nick)
    TextView nick;
    @BindView(R.id.friend_info_company)
    TextView company;
    @BindView(R.id.friend_info_motto)
    TextView motto;
    private UserVO userVO = new UserVO();
    @BindView(R.id.friend_info_listview)
    ListView listView;
    @BindView(R.id.friend_info_chat_button)
    Button chatButton;
    @BindView(R.id.friend_info_add_button)
    Button addButton;
    Handler handler = new Handler();
    MyInformationItemAdapter myInformationItemAdapter;
    @BindView(R.id.friend_img)
    ImageView imageView;
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_info);
        ButterKnife.bind(this);
        getType();
        getUserVO();
        initTopBar(0.1);
        initImageView();
        initButton();
    }

    private void getType() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(CHAT_TYPE_ATTRIBUTE);
        objectId = bundle.getInt(OBJECT_ID_ATTRIBUTE);
        isJoined = bundle.getBoolean(BundleUtil.IS_JOINED_ATTRIBUTE);
        userVO = JsonUtil.fromJson(bundle.getString(BundleUtil.OBJECT_ATTRIBUTE), new TypeToken<UserVO>() {
        }.getType());
        initUser();
//        System.out.println("type:" + type + " object_id:" + objectId + " is joined:" + isJoined);
    }

    private void initTopBar(double size) {
        leftButton.setOnClickListener(view -> {
            finish();
        });
        topBar.getLayoutParams().height = (int) Math.floor(height * size);
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
                    Intent intent = new Intent(this, ChatActivity.class).putExtras(bundle);
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

    private void initImageView() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.alpha1to0));
                imageView.setVisibility(View.GONE);
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new BottomSheet.BottomListSheetBuilder(mContext)
                        .addItem("保存到相册")
                        .setIsCenter(true)
                        .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                            dialog.dismiss();
                            FileUtil.saveImageToGallery(mContext, ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                            XToastUtils.toast("保存成功!");
                        })
                        .build()
                        .show();
                return true;
            }
        });
    }

    private void initUser() {
        if (userVO != null) {
            if (StringUtils.isEmpty(userVO.getNote())) {
                nick.setText(userVO.getNick());
            } else {
                nick.setText(userVO.getNote());
            }
            company.setText(userVO.getCompany());
            motto.setText(userVO.getMotto());
            List<UserVoParamItem> itemList = getItemList();
            myInformationItemAdapter = new MyInformationItemAdapter(this, userVO, itemList);
            listView.setAdapter(myInformationItemAdapter);
            Glide.with(this)
                    .load(GimmeApplication.getImageUrl(userVO.getAvatar()))
                    .error(R.mipmap.default_icon)
                    .into(icon);
            Glide.with(this)
                    .load(GimmeApplication.getImageUrl(userVO.getAvatar()))
                    .error(R.mipmap.default_icon)
                    .into(imageView);
        }
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
                            initUser();
                        }
                    });
                }
            }
        }).start();
    }

    private List<UserVoParamItem> getFriendItemList() {
        List<UserVoParamItem> itemList = new ArrayList<>();
        UserVoParamItem item = new UserVoParamItem("备注", userVO.getNote(), true, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("Gimme号", userVO.getId().toString(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("性别", UserUtil.GENDER_LIST[userVO.getGender()].getName(), false, ParamItemUtil.ParamType.TYPE_GENDER.getCode());
        itemList.add(item);
        item = new UserVoParamItem("昵称", userVO.getNick(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("邮箱", userVO.getMail(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("所在地", userVO.getCity(), false, ParamItemUtil.ParamType.TYPE_LOCAL.getCode());
        itemList.add(item);
        item = new UserVoParamItem("职业", UserUtil.OCCUPATION_LIST[userVO.getOccupation()].getName(), false, ParamItemUtil.ParamType.TYPE_OCCUPATION.getCode());
        itemList.add(item);
        return itemList;
    }

    private List<UserVoParamItem> getOwnerItemList() {
        List<UserVoParamItem> itemList = new ArrayList<>();
        UserVoParamItem item = new UserVoParamItem("昵称", userVO.getNick(), true, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("Gimme号", userVO.getId().toString(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        if (type.equals(InfoTypeUtil.Character.TYPE_GROUP_SELF.getCode())) {
            item = new UserVoParamItem("群昵称", userVO.getNote(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
            itemList.add(item);
        } else if (type.equals(InfoTypeUtil.Character.TYPE_CHANNEL_SELF.getCode())) {
            item = new UserVoParamItem("频道昵称", userVO.getNote(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
            itemList.add(item);
        }
        item = new UserVoParamItem("性别", UserUtil.GENDER_LIST[userVO.getGender()].getName(), true, ParamItemUtil.ParamType.TYPE_GENDER.getCode());
        itemList.add(item);
        item = new UserVoParamItem("邮箱", userVO.getMail(), true, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("生日", NumberUtil.changeToYearAndMonthAndDay(userVO.getBirthday()), true, ParamItemUtil.ParamType.TYPE_DATE.getCode());
        itemList.add(item);
        item = new UserVoParamItem("所在地", userVO.getCity(), true, ParamItemUtil.ParamType.TYPE_LOCAL.getCode());
        itemList.add(item);
        item = new UserVoParamItem("职业", UserUtil.OCCUPATION_LIST[userVO.getOccupation()].getName(), true, ParamItemUtil.ParamType.TYPE_OCCUPATION.getCode());
        itemList.add(item);
        return itemList;
    }

    private List<UserVoParamItem> getGroupItemList() {
        List<UserVoParamItem> itemList = new ArrayList<>();
        UserVoParamItem item;
        if (!StringUtils.isEmpty(userVO.getNote())) {
            item = new UserVoParamItem("备注", userVO.getNote(), true, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
            itemList.add(item);
        }
        item = new UserVoParamItem("昵称", userVO.getNick(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("Gimme号", userVO.getId().toString(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("性别", UserUtil.GENDER_LIST[userVO.getGender()].getName(), false, ParamItemUtil.ParamType.TYPE_GENDER.getCode());
        itemList.add(item);
        item = new UserVoParamItem("群昵称", userVO.getOtherNick(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("邮箱", userVO.getMail(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("所在地", userVO.getCity(), true, ParamItemUtil.ParamType.TYPE_LOCAL.getCode());
        itemList.add(item);
        item = new UserVoParamItem("职业", UserUtil.OCCUPATION_LIST[userVO.getOccupation()].getName(), false, ParamItemUtil.ParamType.TYPE_OCCUPATION.getCode());
        itemList.add(item);
        return itemList;
    }

    private List<UserVoParamItem> getChannelItemList() {
        List<UserVoParamItem> itemList = new ArrayList<>();
        UserVoParamItem item;
        if (!StringUtils.isEmpty(userVO.getNote())) {
            item = new UserVoParamItem("备注", userVO.getNote(), true, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
            itemList.add(item);
        }
        item = new UserVoParamItem("昵称", userVO.getNick(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("Gimme号", userVO.getId().toString(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("性别", UserUtil.GENDER_LIST[userVO.getGender()].getName(), false, ParamItemUtil.ParamType.TYPE_GENDER.getCode());
        itemList.add(item);
        item = new UserVoParamItem("频道昵称", userVO.getOtherNick(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("邮箱", userVO.getMail(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("所在地", userVO.getCity(), false, ParamItemUtil.ParamType.TYPE_LOCAL.getCode());
        itemList.add(item);
        item = new UserVoParamItem("职业", UserUtil.OCCUPATION_LIST[userVO.getOccupation()].getName(), false, ParamItemUtil.ParamType.TYPE_OCCUPATION.getCode());
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