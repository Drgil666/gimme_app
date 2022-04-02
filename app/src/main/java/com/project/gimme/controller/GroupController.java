package com.project.gimme.controller;

import static com.project.gimme.GimmeApplication.REMOTE_URL;
import static com.project.gimme.GimmeApplication.TOKEN;

import com.google.gson.reflect.TypeToken;
import com.project.gimme.GimmeApplication;
import com.project.gimme.pojo.Group;
import com.project.gimme.pojo.vo.GroupVO;
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
public class GroupController {
    public static ResponseData<List<Group>> getGroupList(String keyword) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/group/list?keyword=" + keyword).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            ResponseData<List<Group>> userResponseData =
                    JsonUtil.fromJson(result, new TypeToken<ResponseData<List<Group>>>() {
                    }.getType());
            return userResponseData;
        }
        return null;
    }

    public static ResponseData<GroupVO> getGroupInfo(String groupId) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/group/info?groupId=" + groupId).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            ResponseData<GroupVO> userResponseData =
                    JsonUtil.fromJson(result, new TypeToken<ResponseData<GroupVO>>() {
                    }.getType());
            return userResponseData;
        }
        return null;
    }

    public static ResponseData<List<UserVO>> getGroupMemberList(String groupId, Integer limit) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/group/member/list?groupId=" + groupId + "&limit=" + limit).get().build();
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

    public static ResponseData<Group> createGroupWithFriend(List<Integer> idList) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JsonUtil.toJson(idList), mediaType);
        Request request = new Request.Builder()
                .url(REMOTE_URL + "/api/group/create/friend")
                .header(TOKEN, GimmeApplication.getToken())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            return JsonUtil.fromJson(result, new TypeToken<ResponseData<Group>>() {
            }.getType());
        }
        return null;
    }
}
