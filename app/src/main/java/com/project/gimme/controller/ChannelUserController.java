package com.project.gimme.controller;

import static com.project.gimme.GimmeApplication.REMOTE_URL;
import static com.project.gimme.GimmeApplication.TOKEN;

import com.project.gimme.GimmeApplication;
import com.project.gimme.pojo.ChannelUser;
import com.project.gimme.pojo.vo.CudRequestVO;
import com.project.gimme.utils.JsonUtil;

import java.io.IOException;
import java.util.List;

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
}
