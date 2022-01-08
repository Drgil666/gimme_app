package com.project.gimme.view.activity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;

/**
 * @author DrGilbert
 */
public class LoginActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setUserAvatar(0.4, 0.3);
        setUserAccount(0.45);
        setUserPassword(0.45);
        setLoginButton(0.45);
    }

    private Boolean tokenValidate() {
        String token = GimmeApplication.getToken();
        //TODO:等待后端服务器接口实现
        return true;
    }

    private void setUserAvatar(Double size, Double top) {
        Integer height = GimmeApplication.getHeight();
        Integer weight = GimmeApplication.getWeight();
        ImageView welcomeIcon = findViewById(R.id.user_avatar);
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

    private void setUserAccount(Double size) {
        Integer weight = GimmeApplication.getWeight();
        EditText userAccount = findViewById(R.id.user_account);
        userAccount.getLayoutParams().width = (int) Math.floor(weight * size);
    }

    private void setUserPassword(Double size) {
        Integer weight = GimmeApplication.getWeight();
        EditText userPassword = findViewById(R.id.user_password);
        userPassword.getLayoutParams().width = (int) Math.floor(weight * size);
    }

    private void setLoginButton(Double size) {
        Integer weight = GimmeApplication.getWeight();
        Button loginButton = findViewById(R.id.login_button);
        loginButton.getLayoutParams().width = (int) Math.floor(weight * size);
    }
}