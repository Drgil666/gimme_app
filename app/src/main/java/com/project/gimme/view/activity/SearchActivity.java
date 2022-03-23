package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.gimme.R;
import com.project.gimme.controller.SearchController;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.pojo.vo.SearchVO;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ContactsUtil;
import com.project.gimme.view.adpter.SearchVoAdapter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

@SuppressLint("NonConstantResourceId")
public class SearchActivity extends AppCompatActivity {
    @BindView(R.id.search_search_edit_text)
    EditText searchEditText;
    @BindView(R.id.search_search_cancel)
    TextView searchCancel;
    @BindView(R.id.search_listview)
    ListView searchListView;
    private Integer type;
    private SearchVoAdapter searchVoAdapter;
    private List<SearchVO> searchVOList = new ArrayList<>();
    Handler handler = new Handler();
    private final Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        getType();
        initTopBar();
        initSearchListView();
    }

    private void initSearchListView() {
        searchListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:跳转逻辑
            }
        });
    }

    private void getType() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(BundleUtil.SEARCH_TYPE_ATTRIBUTE);
        System.out.println("search type:" + type);
    }

    private void initTopBar() {
        searchCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println(searchEditText.getText().toString());
                if (!StringUtils.isEmpty(searchEditText.getText().toString())) {
                    getSearchList(searchEditText.getText().toString());
                }
            }
        });
    }

    private void getSearchList(String keyword) {
//        searchVOList = new ArrayList<>();
//        for (int i = 1; i <= 5; i++) {
//            SearchVO searchVO = new SearchVO();
//            searchVO.setAvatar(null);
//            searchVO.setObjectId(i);
//            searchVO.setObjectType(ChatMsgUtil.Character.TYPE_FRIEND.getName());
//            searchVO.setObjectNick("好友" + i);
//            searchVOList.add(searchVO);
//        }
//        for (int i = 1; i <= 5; i++) {
//            SearchVO searchVO = new SearchVO();
//            searchVO.setAvatar(null);
//            searchVO.setObjectId(i);
//            searchVO.setObjectType(ChatMsgUtil.Character.TYPE_GROUP.getName());
//            searchVO.setObjectNick("群聊" + i);
//            searchVOList.add(searchVO);
//        }
//        for (int i = 1; i <= 5; i++) {
//            SearchVO searchVO = new SearchVO();
//            searchVO.setAvatar(null);
//            searchVO.setObjectId(i);
//            searchVO.setObjectType(ChatMsgUtil.Character.TYPE_CHANNEL.getName());
//            searchVO.setObjectNick("频道" + i);
//            searchVOList.add(searchVO);
//        }
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<List<SearchVO>> responseData =
                        SearchController.getSearchVoList(ContactsUtil.SEARCH_TYPE_LIST[type].getName(), keyword);
                if (responseData != null && responseData.isSuccess()) {
                    searchVOList = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            searchVoAdapter = new SearchVoAdapter(mContext, searchVOList);
                            searchListView.setAdapter(searchVoAdapter);
                        }
                    });
                }
            }
        }).start();
    }
}