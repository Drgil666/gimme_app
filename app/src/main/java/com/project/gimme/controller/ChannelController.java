package com.project.gimme.controller;

import static com.project.gimme.GimmeApplication.REMOTE_URL;
import static com.project.gimme.GimmeApplication.TOKEN;

import com.google.gson.reflect.TypeToken;
import com.project.gimme.GimmeApplication;
import com.project.gimme.pojo.Channel;
import com.project.gimme.pojo.vo.ChannelVO;
import com.project.gimme.pojo.vo.CudRequestVO;
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
public class ChannelController {
    public static ResponseData<List<Channel>> getChannelList(String keyword) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/channel/list?keyword=" + keyword).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            ResponseData<List<Channel>> userResponseData =
                    JsonUtil.fromJson(result, new TypeToken<ResponseData<List<Channel>>>() {
                    }.getType());
            return userResponseData;
        }
        return null;
    }

    public static ResponseData<ChannelVO> getChannelInfo(String channelId) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/channel/info?channelId=" + channelId).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            ResponseData<ChannelVO> userResponseData =
                    JsonUtil.fromJson(result, new TypeToken<ResponseData<ChannelVO>>() {
                    }.getType());
            return userResponseData;
        }
        return null;
    }

    public static ResponseData<Channel> createChannel(Channel channel) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        CudRequestVO<Channel, Integer> requestVO = new CudRequestVO<Channel, Integer>();
        requestVO.setData(channel);
        requestVO.setMethod(CudRequestVO.CREATE_METHOD);
        requestVO.setKey(null);
        RequestBody body = RequestBody.create(JsonUtil.toJson(requestVO), mediaType);
        Request request = new Request.Builder()
                .url(REMOTE_URL + "/api/channel")
                .header(TOKEN, GimmeApplication.getToken())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            return JsonUtil.fromJson(result, new TypeToken<ResponseData<Channel>>() {
            }.getType());
        }
        return null;
    }

    public static ResponseData<List<UserVO>> getChannelMemberList(String channelId, Integer limit) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/channel/member/list?channelId=" + channelId + "&limit=" + limit).get().build();
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

    public static ResponseData<Channel> createChannelWithFriend(List<Integer> idList) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JsonUtil.toJson(idList), mediaType);
        Request request = new Request.Builder()
                .url(REMOTE_URL + "/api/channel/create/friend")
                .header(TOKEN, GimmeApplication.getToken())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            return JsonUtil.fromJson(result, new TypeToken<ResponseData<Channel>>() {
            }.getType());
        }
        return null;
    }

    public static void deleteChannel(Integer channelId) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        CudRequestVO<Channel, Integer> requestVO = new CudRequestVO<>();
        Channel channel = new Channel();
        channel.setId(channelId);
        requestVO.setKey(null);
        requestVO.setData(channel);
        requestVO.setMethod(CudRequestVO.DELETE_METHOD);
        RequestBody body = RequestBody.create(JsonUtil.toJson(requestVO), mediaType);
        Request request = new Request.Builder()
                .url(REMOTE_URL + "/api/channel")
                .header(TOKEN, GimmeApplication.getToken())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
        }
    }
}
