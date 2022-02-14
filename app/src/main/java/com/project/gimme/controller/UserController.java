package com.project.gimme.controller;

import static com.project.gimme.GimmeApplication.REMOTE_URL;

import com.project.gimme.GimmeApplication;
import com.project.gimme.pojo.User;
import com.project.gimme.pojo.vo.LoginVO;
import com.project.gimme.utils.JsonUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author DrGilbert
 * @date 2022/2/13 20:52
 */
public class UserController {
    public static com.project.gimme.pojo.vo.Response<String> login(LoginVO loginVO) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        {
            RequestBody body = RequestBody.create(JsonUtil.objectToJsonString(loginVO), mediaType);
            Request request = new Request.Builder()
                    .url(REMOTE_URL + "/api/user/login")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    com.project.gimme.pojo.vo.Response<String> userResponse =
                            JsonUtil.getResponseBody(result, String.class);
                    return userResponse;
                }
            }
        }
        return null;
    }

    public static com.project.gimme.pojo.vo.Response<User> getUser() throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header("token", GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/user").get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            com.project.gimme.pojo.vo.Response<User> userResponse =
                    JsonUtil.getResponseBody(result, User.class);
            return userResponse;
        }
        return null;
    }
}
