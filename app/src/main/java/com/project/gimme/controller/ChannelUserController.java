package com.project.gimme.controller;

import static com.project.gimme.GimmeApplication.REMOTE_URL;
import static com.project.gimme.GimmeApplication.TOKEN;

import com.google.gson.reflect.TypeToken;
import com.project.gimme.GimmeApplication;
import com.project.gimme.pojo.ChannelUser;
import com.project.gimme.pojo.vo.CudRequestVO;
import com.project.gimme.pojo.vo.ResponseData;
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
public class ChannelUserController {

    public static void deleteChannelUser(Integer channelId, List<Integer> idList) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        ChannelUser channelUser = new ChannelUser();
        channelUser.setChannelId(channelId);
        CudRequestVO<ChannelUser, Integer> requestVO = new CudRequestVO<>();
        requestVO.setMethod(CudRequestVO.DELETE_METHOD);
        requestVO.setData(channelUser);
        requestVO.setKey(idList);
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JsonUtil.toJson(requestVO), mediaType);
        Request request = new Request.Builder()
                .url(REMOTE_URL + "/api/channel/user")
                .header(TOKEN, GimmeApplication.getToken())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {

        }
    }

    public static ResponseData<ChannelUser> getChannelUser(Integer channelId) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/channel/user?channelId=" + channelId).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            ResponseData<ChannelUser> userResponseData =
                    JsonUtil.fromJson(result, new TypeToken<ResponseData<ChannelUser>>() {
                    }.getType());
            return userResponseData;
        }
        return null;
    }

    public static void updateChannelUser(ChannelUser channelUser) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        CudRequestVO<ChannelUser, Integer> requestVO = new CudRequestVO<>();
        requestVO.setMethod(CudRequestVO.UPDATE_METHOD);
        requestVO.setData(channelUser);
        requestVO.setKey(null);
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JsonUtil.toJson(requestVO), mediaType);
        Request request = new Request.Builder()
                .url(REMOTE_URL + "/api/channel/user")
                .header(TOKEN, GimmeApplication.getToken())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {

        }
    }

    public static void createChannelUser(ChannelUser channelUser) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        CudRequestVO<ChannelUser, Integer> requestVO = new CudRequestVO<>();
        requestVO.setMethod(CudRequestVO.CREATE_METHOD);
        requestVO.setData(channelUser);
        requestVO.setKey(null);
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JsonUtil.toJson(requestVO), mediaType);
        Request request = new Request.Builder()
                .url(REMOTE_URL + "/api/channel/user")
                .header(TOKEN, GimmeApplication.getToken())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {

        }
    }
}
