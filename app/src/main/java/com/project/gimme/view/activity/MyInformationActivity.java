package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.reflect.TypeToken;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.UserController;
import com.project.gimme.pojo.User;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.pojo.vo.UserVoParamItem;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.JsonUtil;
import com.project.gimme.utils.NumberUtil;
import com.project.gimme.utils.ParamItemUtil;
import com.project.gimme.utils.UserUtil;
import com.project.gimme.view.adpter.MyInformationItemAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

/**
 * @author 25741
 */
@SuppressLint("NonConstantResourceId")
public class MyInformationActivity extends SwipeBackActivity {
    private User user = new User();
    @BindView(R.id.my_information_info_icon)
    ImageView infoIcon;
    @BindView(R.id.my_information_info_nick)
    TextView infoNick;
    @BindView(R.id.my_information_info_company)
    TextView infoCompany;
    @BindView(R.id.my_information_info_motto)
    TextView infoMotto;
    @BindView(R.id.my_information_listview)
    ListView listView;
    @BindView(R.id.my_information_top_left_button)
    ImageView topLeftButton;
    private final Context mContext = this;
    private Handler handler = new Handler();
    private String userNick;
    private String userAvatar;
    private String userCompany;
    private String userMotto;
    private List<UserVoParamItem> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_infomation);
        ButterKnife.bind(this);
        getInfo();
        getUser();
        initTopBar();
        initInfoLayout();
    }

    private void getInfo() {
        Bundle bundle = getIntent().getExtras();
        userNick = bundle.getString(BundleUtil.OBJECT_NICK_ATTRIBUTE);
        userAvatar = bundle.getString(BundleUtil.USER_AVATAR_ATTRIBUTE);
        userCompany = bundle.getString(BundleUtil.USER_COMPANY_ATTRIBUTE);
        userMotto = bundle.getString(BundleUtil.USER_MOTTO_ATTRIBUTE);
        user = JsonUtil.fromJson(bundle.getString(BundleUtil.OBJECT_ATTRIBUTE),
                new TypeToken<User>() {
                }.getType());
        initListView();
    }

    private void initTopBar() {
        topLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initInfoLayout() {
        infoNick.setText(userNick);
        infoCompany.setText(userCompany);
        infoMotto.setText(userMotto);
        Glide.with(this)
                .load(GimmeApplication.getImageUrl(userAvatar))
                .error(R.mipmap.default_icon)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                .into(infoIcon);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getUser();
    }

    private void getUser() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<User> userResponseData = UserController.getUser("");
                if (userResponseData != null && userResponseData.isSuccess()) {
                    user = userResponseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            infoNick.setText(user.getNick());
                            infoCompany.setText(user.getCompany());
                            infoMotto.setText(user.getMotto());
                            Glide.with(mContext)
                                    .load(GimmeApplication.getImageUrl(user.getAvatar()))
                                    .error(R.mipmap.default_icon)
                                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(10)))
                                    .into(infoIcon);
                            initListView();
                        }
                    });
                }
            }
        }).start();
    }

    private void initItemList() {
        itemList = new ArrayList<>();
        UserVoParamItem item = new UserVoParamItem("Gimme账号", user.getId().toString(), false, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("性别", UserUtil.GENDER_LIST[user.getGender()].getName(), true, ParamItemUtil.ParamType.TYPE_GENDER.getCode());
        itemList.add(item);
        item = new UserVoParamItem("生日", NumberUtil.changeToYearAndMonthAndDay(user.getBirthday()), true, ParamItemUtil.ParamType.TYPE_DATE.getCode());
        itemList.add(item);
        item = new UserVoParamItem("职业", UserUtil.OCCUPATION_LIST[user.getOccupation()].getName(), true, ParamItemUtil.ParamType.TYPE_OCCUPATION.getCode());
        itemList.add(item);
        item = new UserVoParamItem("公司", user.getCompany(), true, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("邮箱", user.getMail(), true, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("个性签名", user.getMotto(), true, ParamItemUtil.ParamType.TYPE_TEXT.getCode());
        itemList.add(item);
        item = new UserVoParamItem("所在地", user.getCity(), true, ParamItemUtil.ParamType.TYPE_LOCAL.getCode());
        itemList.add(item);
    }

    private void initListView() {
        initItemList();
        listView.setAdapter(new MyInformationItemAdapter(mContext, user, itemList));
    }
}