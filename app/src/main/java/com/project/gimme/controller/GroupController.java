package com.project.gimme.controller;

import static com.project.gimme.GimmeApplication.REMOTE_URL;

import com.project.gimme.GimmeApplication;
import com.project.gimme.pojo.Group;
import com.project.gimme.pojo.vo.GroupVO;
import com.project.gimme.utils.JsonUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author DrGilbert
 * @date 2022/2/13 20:52
 */
public class GroupController {
    public static com.project.gimme.pojo.vo.Response<List<Group>> getGroupList(String keyword) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header("token", GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/group/list?keyword=" + keyword).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            com.project.gimme.pojo.vo.Response<List<Group>> userResponse =
                    JsonUtil.getResponseListBody(result, Group.class);
            return userResponse;
        }
        return null;
    }

    public static com.project.gimme.pojo.vo.Response<GroupVO> getGroupInfo(Integer groupId) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header("token", GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/group/info?groupId=" + groupId.toString()).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            com.project.gimme.pojo.vo.Response<GroupVO> userResponse =
                    JsonUtil.getResponseObjectBody(result, GroupVO.class);
            return userResponse;
        }
        return null;
    }
}
