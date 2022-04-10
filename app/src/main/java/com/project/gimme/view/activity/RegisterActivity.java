package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.UserController;
import com.project.gimme.pojo.User;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.UserUtil;
import com.project.gimme.utils.XToastUtils;

import org.apache.commons.lang3.StringUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

@SuppressLint("NonConstantResourceId")
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.register_password)
    EditText registerPassword;
    @BindView(R.id.register_repeat_password)
    EditText registerRepeatPassword;
    @BindView(R.id.register_button)
    Button registerButton;
    private Handler handler = new Handler();
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initButton();
    }

    private void initButton() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password1 = registerPassword.getText().toString();
                String password2 = registerRepeatPassword.getText().toString();
                if (StringUtils.isEmpty(password1)) {
                    XToastUtils.toast("密码不可为空!");
                } else if (StringUtils.isEmpty(password2)) {
                    XToastUtils.toast("重复密码不可为空!");
                } else if (!password1.equals(password2)) {
                    XToastUtils.toast("重复密码必须与密码相同!");
                } else {
                    User user = new User();
                    user.setPassword(password1);
                    user.setOccupation(UserUtil.Occupation.TYPE_OTHER_OCCUPATION.getCode());
                    user.setPersonalMsgTimestamp(new Date());
                    user.setBirthday(new Date());
                    user.setNick("默认昵称");
                    user.setCompany("默认公司");
                    user.setMotto("默认个性签名");
                    register(user);
                }
            }
        });
    }

    private void register(User user) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<User> userResponseData = UserController.createUser(user);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (userResponseData != null && userResponseData.isSuccess()) {
                            User user = userResponseData.getData();
                            Integer userId = user.getId();
                            GimmeApplication.setUserId(userId);
                            XToastUtils.toast("注册成功!");
                            finish();
                        } else {
                            XToastUtils.toast("注册失败!");
                        }
                    }
                });
            }
        }).start();
    }
}