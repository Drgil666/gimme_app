package com.project.gimme.view.activity;

import static com.project.gimme.GimmeApplication.LOCAL_STORAGE;
import static com.project.gimme.GimmeApplication.TOKEN;
import static com.project.gimme.GimmeApplication.USER_ID;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.UserController;
import com.project.gimme.pojo.vo.LoginVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.LogUtil;
import com.project.gimme.utils.XToastUtils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

/**
 * @author DrGilbert
 */
@SuppressLint("NonConstantResourceId")
public class LoginActivity extends BaseActivity {
    @BindView(R.id.user_avatar)
    ImageView welcomeIcon;
    @BindView(R.id.user_password)
    EditText userPassword;
    @BindView(R.id.user_account)
    EditText userAccount;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.register_button)
    Button registerButton;
    private Handler handler = new Handler();
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initUserAvatar(0.4, 0.3);
        initUserAccount(0.45);
        initUserPassword(0.45);
        initLoginButton(0.45);
    }

    private void initUserAvatar(Double size, Double top) {
        Integer height = GimmeApplication.getHeight();
        Integer weight = GimmeApplication.getWeight();
        int imageSize = (int) Math.floor((Math.min(height, weight) * size));
        int marginTop = (int) Math.floor((Math.min(height, weight) * top));
        welcomeIcon.getLayoutParams().height = imageSize;
        welcomeIcon.getLayoutParams().width = imageSize;
        //????????????icon??????
        ViewGroup.MarginLayoutParams marginParams =
                (ViewGroup.MarginLayoutParams) welcomeIcon.getLayoutParams();
        marginParams.setMargins(marginParams.leftMargin,
                marginTop,
                marginParams.leftMargin,
                marginParams.bottomMargin);
        //????????????icon????????????
    }

    private void initUserAccount(Double size) {
        Integer weight = GimmeApplication.getWeight();
        userAccount.clearFocus();
        userAccount.setSelected(false);
        if (GimmeApplication.getUserId() != null) {
            userAccount.setText(GimmeApplication.getUserId().toString());
        }
        userAccount.getLayoutParams().width = (int) Math.floor(weight * size);
    }

    private void initUserPassword(Double size) {
        Integer weight = GimmeApplication.getWeight();
        userPassword.getLayoutParams().width = (int) Math.floor(weight * size);
    }

    private void initLoginButton(Double size) {
        Integer weight = GimmeApplication.getWeight();
        loginButton.getLayoutParams().width = (int) Math.floor(weight * size);
        loginButton.setOnClickListener(view ->
        {
            if (StringUtils.isEmpty(userAccount.getText().toString())
                    || StringUtils.isEmpty(userPassword.getText().toString())) {
                XToastUtils.toast("??????????????????????????????!");
            } else {
                GimmeApplication.setUserId(null);
                GimmeApplication.setToken(null);
                login();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GimmeApplication.setUserId(null);
                GimmeApplication.setToken(null);
                Intent intent = new Intent(mContext, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                LoginVO loginVO = new LoginVO();
                loginVO.setUserId(Integer.parseInt(userAccount.getText().toString()));
                loginVO.setPassword(userPassword.getText().toString());
                ResponseData<String> userResponseData = UserController.login(loginVO);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (userResponseData != null && userResponseData.isSuccess()) {
                            GimmeApplication.setUserId(Integer.parseInt(userAccount.getText().toString()));
                            GimmeApplication.setToken(userResponseData.getData());
                            SharedPreferences sharedPreferences = getSharedPreferences(LOCAL_STORAGE, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(TOKEN, userResponseData.getData());
                            editor.putInt(USER_ID, Integer.parseInt(userAccount.getText().toString()));
                            editor.apply();
                            File file = new File(getApplicationContext().getFilesDir().getAbsolutePath() + "/" + GimmeApplication.getUserId() + "/");
                            if (!file.exists()) {
                                file.mkdirs();
                                //???????????????????????????
                            }
                            LogUtil.log(this.toString(), userResponseData.getData());
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            XToastUtils.toast("????????????!");
                        }
                    }
                });
            }
        }).start();
    }
}