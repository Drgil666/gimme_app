package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.UserController;
import com.project.gimme.pojo.User;
import com.project.gimme.pojo.vo.ResponseData;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

/**
 * @author 25741
 */
@SuppressLint("NonConstantResourceId")
public class MyInformationActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_infomation);
        ButterKnife.bind(this);
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
                                    .load(GimmeApplication.REMOTE_URL + "/api/chat/file/download/" + user.getAvatar())
                                    .into(infoIcon);
                        }
                    });
                }
            }
        }).start();
    }

}