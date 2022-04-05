package com.project.gimme.controller;

import static com.project.gimme.GimmeApplication.REMOTE_URL;
import static com.project.gimme.GimmeApplication.TOKEN;

import com.google.gson.reflect.TypeToken;
import com.project.gimme.GimmeApplication;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.pojo.vo.SearchVO;
import com.project.gimme.utils.JsonUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author 25741
 */
public class SearchController {
    public static ResponseData<List<SearchVO>> getSearchVoList(String searchType, String keyword) throws IOException {
        //创建OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .header(TOKEN, GimmeApplication.getToken())
                .url(REMOTE_URL + "/api/search?keyword=" + keyword + "&searchType=" + searchType).get().build();
        Call call = client.newCall(request);
        Response response = call.execute();
        if (response.isSuccessful()) {
            String result = response.body().string();
            ResponseData<List<SearchVO>> userResponseData =
                    JsonUtil.fromJson(result, new TypeToken<ResponseData<List<SearchVO>>>() {
                    }.getType());
            return userResponseData;
        }
        return null;
    }
}
