package com.project.gimme.view.activity;

import static com.project.gimme.utils.BundleUtil.CHAT_TYPE_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_ID_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OTHER_ID_ATTRIBUTE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.reflect.TypeToken;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.FriendController;
import com.project.gimme.controller.PersonalMsgController;
import com.project.gimme.controller.UserController;
import com.project.gimme.pojo.Friend;
import com.project.gimme.pojo.PersonalMsg;
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
import java.util.Date;
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
    private String otherId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_info);
        ButterKnife.bind(this);
        getType();
        getUserVO();
        initTopBar(0.1);
        initImageView();
    }

    private void getType() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(CHAT_TYPE_ATTRIBUTE);
        objectId = bundle.getInt(OBJECT_ID_ATTRIBUTE);
        isJoined = bundle.getBoolean(BundleUtil.IS_JOINED_ATTRIBUTE);
        otherId = bundle.getString(OTHER_ID_ATTRIBUTE);
        if (StringUtils.isEmpty(otherId)) {
            otherId = "";
        }
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
            if (userVO.getId().equals(GimmeApplication.getUserId())) {
                addButton.setVisibility(View.GONE);
            }
            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Friend friend = new Friend();
                    friend.setFriendId(objectId);
                    friend.setMsgTimestamp(new Date());
                    friend.setFriendNote(userVO.getNick());
                    createFriend(friend);
                    addButton.setVisibility(View.GONE);
                    XToastUtils.toast("好友添加成功!");
                    chatButton.setVisibility(View.VISIBLE);
                }
            });
            chatButton.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putInt(CHAT_TYPE_ATTRIBUTE, ChatMsgUtil.Character.TYPE_FRIEND.getCode());
                bundle.putInt(OBJECT_ID_ATTRIBUTE, objectId);
                Intent intent = new Intent(this, ChatActivity.class).putExtras(bundle);
                startActivity(intent);
            });
        }

    }

    private void createFriend(Friend friend) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                FriendController.createFriend(friend);
            }
        }).start();
    }

    private void createPersonalMsg(PersonalMsg personalMsg) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                PersonalMsgController.createPersonalMsg(personalMsg);
            }
        }).start();
    }

    private void initImageView() {
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.alpha0to1));
                imageView.setVisibility(View.VISIBLE);
            }
        });
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
                            authorize();
                            FileUtil.saveImageToGallery(mContext, ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                            XToastUtils.toast("保存成功!");
                        })
                        .build()
                        .show();
                return true;
            }
        });
    }

    private void authorize() {
        //判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //这里可以写个对话框之类的项向用户解释为什么要申请权限，
                // 并在对话框的确认键后续再次申请权限.它在用户选择"不再询问"的情况下返回false
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
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
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                    .error(R.mipmap.default_icon)
                    .into(icon);
            Glide.with(this)
                    .load(GimmeApplication.getImageUrl(userVO.getAvatar()))
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
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
                        UserController.getUserVO(objectId.toString(), ChatMsgUtil.Character.TYPE_FRIEND.getName(), otherId.toString());
                if (responseData != null && responseData.isSuccess()) {
                    userVO = responseData.getData();
                    isJoined = userVO.getIsJoined();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            initUser();
                            initButton();
                        }
                    });
                }
            }
        }).start();
    }

    private List<UserVoParamItem> getFriendItemList() {
        List<UserVoParamItem> itemList = new ArrayList<>();
        UserVoParamItem item;
//        if(isJoined) {
//            item = new UserVoParamItem("备注", userVO.getNote(), true, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
//            itemList.add(item);
//        }
        item = new UserVoParamItem("Gimme号", userVO.getId().toString(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("性别", userVO.getGender() != null ? UserUtil.GENDER_LIST[userVO.getGender()].getName() : null, true, ParamItemUtil.ParamType.TYPE_GENDER.getCode());
        itemList.add(item);
        item = new UserVoParamItem("昵称", userVO.getNick(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("邮箱", userVO.getMail(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("所在地", userVO.getCity(), false, ParamItemUtil.ParamType.TYPE_LOCAL.getCode());
        itemList.add(item);
        item = new UserVoParamItem("职业", userVO.getOccupation() != null ? UserUtil.OCCUPATION_LIST[userVO.getOccupation()].getName() : null, true, ParamItemUtil.ParamType.TYPE_OCCUPATION.getCode());
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
            item = new UserVoParamItem("群昵称", userVO.getOtherNick(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
            itemList.add(item);
        } else if (type.equals(InfoTypeUtil.Character.TYPE_CHANNEL_SELF.getCode())) {
            item = new UserVoParamItem("频道昵称", userVO.getOtherNick(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
            itemList.add(item);
        }
        item = new UserVoParamItem("性别", userVO.getGender() != null ? UserUtil.GENDER_LIST[userVO.getGender()].getName() : null, true, ParamItemUtil.ParamType.TYPE_GENDER.getCode());
        itemList.add(item);
        item = new UserVoParamItem("邮箱", userVO.getMail(), true, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("生日", NumberUtil.changeToYearAndMonthAndDay(userVO.getBirthday()), true, ParamItemUtil.ParamType.TYPE_DATE.getCode());
        itemList.add(item);
        item = new UserVoParamItem("所在地", userVO.getCity(), true, ParamItemUtil.ParamType.TYPE_LOCAL.getCode());
        itemList.add(item);
        item = new UserVoParamItem("职业", userVO.getOccupation() != null ? UserUtil.OCCUPATION_LIST[userVO.getOccupation()].getName() : null, true, ParamItemUtil.ParamType.TYPE_OCCUPATION.getCode());
        itemList.add(item);
        return itemList;
    }

    private List<UserVoParamItem> getGroupItemList() {
        List<UserVoParamItem> itemList = new ArrayList<>();
        UserVoParamItem item;
//        if (isJoined) {
//            item = new UserVoParamItem("备注", userVO.getNote(), true, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
//            itemList.add(item);
//        }
        item = new UserVoParamItem("昵称", userVO.getNick(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("Gimme号", userVO.getId().toString(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("性别", userVO.getGender() != null ? UserUtil.GENDER_LIST[userVO.getGender()].getName() : null, true, ParamItemUtil.ParamType.TYPE_GENDER.getCode());
        itemList.add(item);
//        item = new UserVoParamItem("群昵称", userVO.getOtherNick(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
//        itemList.add(item);
        item = new UserVoParamItem("邮箱", userVO.getMail(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("所在地", userVO.getCity(), true, ParamItemUtil.ParamType.TYPE_LOCAL.getCode());
        itemList.add(item);
        item = new UserVoParamItem("职业", userVO.getOccupation() != null ? UserUtil.OCCUPATION_LIST[userVO.getOccupation()].getName() : null, true, ParamItemUtil.ParamType.TYPE_OCCUPATION.getCode());
        itemList.add(item);
        return itemList;
    }

    private List<UserVoParamItem> getChannelItemList() {
        List<UserVoParamItem> itemList = new ArrayList<>();
        UserVoParamItem item;
//        if (isJoined) {
//            item = new UserVoParamItem("备注", userVO.getNote(), true, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
//            itemList.add(item);
//        }
        item = new UserVoParamItem("昵称", userVO.getNick(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("Gimme号", userVO.getId().toString(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("性别", userVO.getGender() != null ? UserUtil.GENDER_LIST[userVO.getGender()].getName() : null, true, ParamItemUtil.ParamType.TYPE_GENDER.getCode());
        itemList.add(item);
//        item = new UserVoParamItem("频道昵称", userVO.getOtherNick(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
//        itemList.add(item);
        item = new UserVoParamItem("邮箱", userVO.getMail(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("所在地", userVO.getCity(), false, ParamItemUtil.ParamType.TYPE_LOCAL.getCode());
        itemList.add(item);
        item = new UserVoParamItem("职业", userVO.getOccupation() != null ? UserUtil.OCCUPATION_LIST[userVO.getOccupation()].getName() : null, true, ParamItemUtil.ParamType.TYPE_OCCUPATION.getCode());
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