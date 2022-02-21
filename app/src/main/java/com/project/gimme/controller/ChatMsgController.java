package com.project.gimme.controller;

import static com.project.gimme.GimmeApplication.REMOTE_URL;
import static com.project.gimme.GimmeApplication.TOKEN;

import com.google.gson.reflect.TypeToken;
import com.project.gimme.GimmeApplication;
import com.project.gimme.pojo.vo.MessageVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.JsonUtil;
import com.project.gimme.utils.NumberUtil;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author DrGilbert
 * @date 2022/2/13 20:52
 */
public class ChatMsgController {

    public static ResponseData<List<MessageVO>> getMessageVoList(Date date) throws IOException {
        String timestamp = NumberUtil.changeToStandardTimestamp(date);
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/chatMsg/list/info?timestamp=" + timestamp).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            ResponseData<List<MessageVO>> userResponseData =
                    JsonUtil.fromJson(result, new TypeToken<ResponseData<List<MessageVO>>>() {
                    }.getType());
            return userResponseData;
        }
        return null;
    }
}
