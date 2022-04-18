package com.project.gimme.controller;

import static com.project.gimme.GimmeApplication.REMOTE_URL;
import static com.project.gimme.GimmeApplication.TOKEN;

import com.google.gson.reflect.TypeToken;
import com.project.gimme.GimmeApplication;
import com.project.gimme.pojo.ChatMsg;
import com.project.gimme.pojo.vo.ChatMsgVO;
import com.project.gimme.pojo.vo.CudRequestVO;
import com.project.gimme.pojo.vo.MessageVO;
import com.project.gimme.pojo.vo.RefreshVO;
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
public class ChatMsgController {

    public static ResponseData<List<MessageVO>> getMessageVoList(String keyword) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/chatMsg/list/info?keyword=" + keyword).get().build();
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

    public static void refresh(RefreshVO refreshVO) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JsonUtil.toJson(refreshVO), mediaType);
        Request request = new Request.Builder()
                .url(REMOTE_URL + "/api/chatMsg/refresh")
                .header(TOKEN, GimmeApplication.getToken())
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
//                    System.out.println("更新成功!");
            }
        }
    }

    public static ResponseData<List<ChatMsgVO>> getChatMsgVoList(String type, Integer objectId, String keyword) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/chatMsg/listVo?type=" + type + "&objectId=" + objectId + "&keyword=" + keyword).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            ResponseData<List<ChatMsgVO>> userResponseData =
                    JsonUtil.fromJson(result, new TypeToken<ResponseData<List<ChatMsgVO>>>() {
                    }.getType());
//            for (ChatMsgVO chatMsgVO : userResponseData.getData()) {
//                chatMsgVO.setText(TEAUtil.decryptByTea(chatMsgVO.getText()));
//            }
            return userResponseData;
        }
        return null;
    }

    public static ResponseData<ChatMsgVO> createChatMsg(ChatMsg chatMsg) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
//        chatMsg.setText(TEAUtil.encryptByTea(chatMsg.getText()));
        CudRequestVO<ChatMsg, Integer> requestVO = new CudRequestVO<ChatMsg, Integer>();
        requestVO.setData(chatMsg);
        requestVO.setMethod(CudRequestVO.CREATE_METHOD);
        requestVO.setKey(null);
        RequestBody body = RequestBody.create(JsonUtil.toJson(requestVO), mediaType);
        Request request = new Request.Builder()
                .url(REMOTE_URL + "/api/chatMsg")
                .header(TOKEN, GimmeApplication.getToken())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            ResponseData<ChatMsgVO> responseData = JsonUtil.fromJson(result, new TypeToken<ResponseData<ChatMsgVO>>() {
            }.getType());
//            responseData.getData().setText(TEAUtil.decryptByTea(responseData.getData().getText()));
            return responseData;
        }
        return null;
    }

    public static void transmitChatMsg(RefreshVO refreshVO) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JsonUtil.toJson(refreshVO), mediaType);
        Request request = new Request.Builder()
                .url(REMOTE_URL + "/api/chatMsg/transmit")
                .header(TOKEN, GimmeApplication.getToken())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {

        }
    }
}
