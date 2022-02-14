package com.project.gimme.controller;

import static com.project.gimme.GimmeApplication.REMOTE_URL;

import com.project.gimme.GimmeApplication;
import com.project.gimme.pojo.Channel;
import com.project.gimme.pojo.vo.ChannelVO;
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
public class ChannelController {
    public static com.project.gimme.pojo.vo.Response<List<Channel>> getChannelList(String keyword) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header("token", GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/channel/list?keyword=" + keyword).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            com.project.gimme.pojo.vo.Response<List<Channel>> userResponse =
                    JsonUtil.getResponseListBody(result, Channel.class);
            return userResponse;
        }
        return null;
    }

    public static com.project.gimme.pojo.vo.Response<ChannelVO> getChannelInfo(String channelId) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header("token", GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/channel/info?channelId=" + channelId).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            com.project.gimme.pojo.vo.Response<ChannelVO> userResponse =
                    JsonUtil.getResponseObjectBody(result, ChannelVO.class);
            return userResponse;
        }
        return null;
    }
}
