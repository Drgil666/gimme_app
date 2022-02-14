package com.project.gimme.view.activity;

import static com.project.gimme.GimmeApplication.TOKEN_CACHE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.UserController;
import com.project.gimme.pojo.vo.LoginVO;
import com.project.gimme.pojo.vo.Response;

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
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Response<String> userResponse = (Response<String>) msg.obj;
                    if (msg.obj != null && userResponse.isSuccess()) {
                        GimmeApplication.setToken(userResponse.getData());
                        SharedPreferences sharedPreferences = getSharedPreferences(TOKEN_CACHE, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("token", userResponse.getData());
                        editor.apply();
                        System.out.println(userResponse.getData());
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "登录失败!", Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
    };

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

    private Boolean tokenValidate() {
        String token = GimmeApplication.getToken();
        //TODO:等待后端服务器接口实现
        return true;
    }

    private void initUserAvatar(Double size, Double top) {
        Integer height = GimmeApplication.getHeight();
        Integer weight = GimmeApplication.getWeight();
        int imageSize = (int) Math.floor((Math.min(height, weight) * size));
        int marginTop = (int) Math.floor((Math.min(height, weight) * top));
        welcomeIcon.getLayoutParams().height = imageSize;
        welcomeIcon.getLayoutParams().width = imageSize;
        //动态设置icon大小
        ViewGroup.MarginLayoutParams marginParams =
                (ViewGroup.MarginLayoutParams) welcomeIcon.getLayoutParams();
        marginParams.setMargins(marginParams.leftMargin,
                marginTop,
                marginParams.leftMargin,
                marginParams.bottomMargin);
        //动态设置icon居中位置
    }

    private void initUserAccount(Double size) {
        Integer weight = GimmeApplication.getWeight();
        userAccount.clearFocus();
        userAccount.setSelected(false);
        userAccount.getLayoutParams().width = (int) Math.floor(weight * size);
    }

    private void initUserPassword(Double size) {
        Integer weight = GimmeApplication.getWeight();
        userPassword.getLayoutParams().width = (int) Math.floor(weight * size);
    }

    private void initLoginButton(Double size) {
        Integer weight = GimmeApplication.getWeight();
        loginButton.getLayoutParams().width = (int) Math.floor(weight * size);
        loginButton.setOnClickListener(view -> {
            GimmeApplication.setToken(null);
            LoginVO loginVO = new LoginVO();
            loginVO.setUserId(Integer.parseInt(userAccount.getText().toString()));
            loginVO.setPassword(userPassword.getText().toString());
            new Thread(new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    Message msg = Message.obtain();
                    msg.what = 1;
                    msg.obj = UserController.login(loginVO);
                    handler.handleMessage(msg);
                }
            }).start();
        });
    }
}