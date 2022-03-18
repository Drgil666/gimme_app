package com.project.gimme.controller;

import static com.project.gimme.GimmeApplication.REMOTE_URL;
import static com.project.gimme.GimmeApplication.TOKEN;

import com.google.gson.reflect.TypeToken;
import com.project.gimme.GimmeApplication;
import com.project.gimme.pojo.User;
import com.project.gimme.pojo.vo.LoginVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.pojo.vo.UserVO;
import com.project.gimme.utils.JsonUtil;

import java.io.IOException;
import java.util.List;

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
    public static ResponseData<String> login(LoginVO loginVO) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        {
            RequestBody body = RequestBody.create(JsonUtil.toJson(loginVO), mediaType);
            Request request = new Request.Builder()
                    .url(REMOTE_URL + "/api/user/login")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String result = response.body().string();
                    return JsonUtil.fromJson(result, new TypeToken<ResponseData<String>>() {
                    }.getType());
                }
            }
        }
        return null;
    }

    public static ResponseData<User> getUser(String id) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/user?userId=" + id).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            ResponseData<User> userResponseData =
                    JsonUtil.fromJson(result, new TypeToken<ResponseData<User>>() {
                    }.getType());
            return userResponseData;
        }
        return null;
    }

    public static ResponseData<UserVO> getUserVO(String friendId, String type, String objectId) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/user/userVo?friendId=" + friendId + "&type=" + type + "&objectId=" + objectId).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            ResponseData<UserVO> userResponseData =
                    JsonUtil.fromJson(result, new TypeToken<ResponseData<UserVO>>() {
                    }.getType());
            return userResponseData;
        }
        return null;
    }

    public static ResponseData<List<User>> getFriendList(String keyword) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/user/friend/list?keyword=" + keyword).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            ResponseData<List<User>> userResponseData =
                    JsonUtil.fromJson(result, new TypeToken<ResponseData<List<User>>>() {
                    }.getType());
            return userResponseData;
        }
        return null;
    }

    public static ResponseData<List<UserVO>> getFriendListInfo(String keyword) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/user/friend/list/info?keyword=" + keyword).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            ResponseData<List<UserVO>> userResponseData =
                    JsonUtil.fromJson(result, new TypeToken<ResponseData<List<UserVO>>>() {
                    }.getType());
            return userResponseData;
        }
        return null;
    }
}
