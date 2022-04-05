package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.UserController;
import com.project.gimme.pojo.User;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.view.adpter.MyInformationItemAdapter;

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
    private final Context mContext = this;
    private Handler handler = new Handler();
    private String userNick;
    private String userAvatar;
    private String userCompany;
    private String userMotto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_infomation);
        ButterKnife.bind(this);
        getInfo();
        getUser();
        initInfoLayout();
    }

    private void getInfo() {
        Bundle bundle = getIntent().getExtras();
        userNick = bundle.getString(BundleUtil.OBJECT_NICK_ATTRIBUTE);
        userAvatar = bundle.getString(BundleUtil.USER_AVATAR_ATTRIBUTE);
        userCompany = bundle.getString(BundleUtil.USER_COMPANY_ATTRIBUTE);
        userMotto = bundle.getString(BundleUtil.USER_MOTTO_ATTRIBUTE);
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
                            listView.setAdapter(new MyInformationItemAdapter(mContext, user));
                        }
                    });
                }
            }
        }).start();
    }

}