package com.project.gimme.controller;

import static com.project.gimme.GimmeApplication.REMOTE_URL;
import static com.project.gimme.GimmeApplication.TOKEN;

import com.google.gson.reflect.TypeToken;
import com.project.gimme.GimmeApplication;
import com.project.gimme.pojo.ChatFile;
import com.project.gimme.pojo.vo.ChatFileVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.JsonUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author DrGilbert
 * @date 2022/2/13 20:52
 */
public class ChatFileController {
    public static ResponseData<List<ChatFileVO>> getChatFileVoByObjectId(String type, Integer objectId, String keyword) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/chat/file/list/info?type=" + type + "&objectId=" + objectId.toString() + "&keyword=" + keyword).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            ResponseData<List<ChatFileVO>> userResponseData =
                    JsonUtil.fromJson(result, new TypeToken<ResponseData<List<ChatFileVO>>>() {
                    }.getType());
            return userResponseData;
        }
        return null;
    }

    public static ResponseData<ChatFile> getChatFile(String fileId) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/chat/file?fileId=" + fileId).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            ResponseData<ChatFile> userResponseData =
                    JsonUtil.fromJson(result, new TypeToken<ResponseData<ChatFile>>() {
                    }.getType());
            return userResponseData;
        }
        return null;
    }

    public static void downloadFile(Integer chatFileId) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/chat/file/download?chatFileId=" + chatFileId).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            InputStream inputStream = response.body().byteStream();
            FileOutputStream fileOutputStream = null;
            System.out.println(response.headers().get("content-disposition"));
//            fileOutputStream = new FileOutputStream(new File();
//            byte[] buffer = new byte[1024];
//            int len = 0;
//            while ((len = inputStream.read(buffer)) != -1) {
//                fileOutputStream.write(buffer, 0, len);
//            }
//            fileOutputStream.flush();
        }
    }
}
