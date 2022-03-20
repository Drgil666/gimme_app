package com.project.gimme.controller;

import static com.project.gimme.GimmeApplication.REMOTE_URL;
import static com.project.gimme.GimmeApplication.TOKEN;

import com.google.gson.reflect.TypeToken;
import com.project.gimme.GimmeApplication;
import com.project.gimme.pojo.Channel;
import com.project.gimme.pojo.ChannelNotice;
import com.project.gimme.pojo.vo.ChannelVO;
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
public class ChannelNoticeController {
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

    public static ResponseData<ChannelNotice> createChannelNotice(ChannelNotice channelNotice) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        CudRequestVO<ChannelNotice, Integer> requestVO = new CudRequestVO<ChannelNotice, Integer>();
        requestVO.setData(channelNotice);
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
            return JsonUtil.fromJson(result, new TypeToken<ResponseData<ChannelNotice>>() {
            }.getType());
        }
        return null;
    }
}
