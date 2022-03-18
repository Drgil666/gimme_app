package com.project.gimme.controller;

import static com.project.gimme.GimmeApplication.REMOTE_URL;
import static com.project.gimme.GimmeApplication.TOKEN;

import com.google.gson.reflect.TypeToken;
import com.project.gimme.GimmeApplication;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.JsonUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author DrGilbert
 * @date 2022/2/13 20:52
 */
public class ChatFileInfoController {
    public static void downloadFile(String filePath, Integer chatFileId, String fileName) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/chat/file/download?chatFileId=" + chatFileId).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            System.out.println(filePath);
            InputStream inputStream = response.body().byteStream();
            System.out.println(fileName);
            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
                //创建用户对应文件夹
            }
            file = new File(filePath, fileName);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, len);
            }
            fileOutputStream.flush();
        }
    }

    public static ResponseData<String> upLoadFile(File file, String chatType, Integer objectId) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(),
                        RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .addFormDataPart("chatType", chatType)
                .addFormDataPart("objectId", objectId.toString())
                .build();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/chat/file/upload")
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            ResponseData<String> userResponseData =
                    JsonUtil.fromJson(result, new TypeToken<ResponseData<String>>() {
                    }.getType());
            return userResponseData;
        }
        return null;
    }
}
