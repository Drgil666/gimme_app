package com.project.gimme.controller;

import static com.project.gimme.GimmeApplication.REMOTE_URL;
import static com.project.gimme.GimmeApplication.TOKEN;

import com.google.gson.reflect.TypeToken;
import com.project.gimme.GimmeApplication;
import com.project.gimme.pojo.PersonalMsg;
import com.project.gimme.pojo.vo.CudRequestVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.JsonUtil;

import java.io.IOException;

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
public class PersonalMsgController {

    public static ResponseData<Long> getNewPersonalMsgListCount(Integer position) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/personalMsg/count?type=" + position).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            ResponseData<Long> userResponseData =
                    JsonUtil.fromJson(result, new TypeToken<ResponseData<Long>>() {
                    }.getType());
            return userResponseData;
        }
        return null;
    }

    public static ResponseData<PersonalMsg> createPersonalMsg(PersonalMsg personalMsg) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        CudRequestVO<PersonalMsg, Integer> requestVO = new CudRequestVO<PersonalMsg, Integer>();
        requestVO.setData(personalMsg);
        requestVO.setMethod(CudRequestVO.CREATE_METHOD);
        requestVO.setKey(null);
        RequestBody body = RequestBody.create(JsonUtil.toJson(requestVO), mediaType);
        Request request = new Request.Builder()
                .url(REMOTE_URL + "/api/personalMsg")
                .header(TOKEN, GimmeApplication.getToken())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            return JsonUtil.fromJson(result, new TypeToken<ResponseData<PersonalMsg>>() {
            }.getType());
        }
        return null;
    }

    public static ResponseData<PersonalMsg> updatePersonalMsg(PersonalMsg personalMsg) throws IOException {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        CudRequestVO<PersonalMsg, Integer> requestVO = new CudRequestVO<PersonalMsg, Integer>();
        requestVO.setData(personalMsg);
        requestVO.setMethod(CudRequestVO.UPDATE_METHOD);
        requestVO.setKey(null);
        RequestBody body = RequestBody.create(JsonUtil.toJson(requestVO), mediaType);
        Request request = new Request.Builder()
                .url(REMOTE_URL + "/api/personalMsg")
                .header(TOKEN, GimmeApplication.getToken())
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            return JsonUtil.fromJson(result, new TypeToken<ResponseData<PersonalMsg>>() {
            }.getType());
        }
        return null;
    }
}
