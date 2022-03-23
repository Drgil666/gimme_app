package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.gimme.R;
import com.project.gimme.pojo.vo.SearchVO;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.view.adpter.SearchVoAdapter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        getType();
        initTopBar();
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
                System.out.println("这里是监听回车事件->" + searchEditText.getText().toString());
                if (!StringUtils.isEmpty(searchEditText.getText().toString())) {
                    getSearchList(searchEditText.getText().toString());
                }
            }
        });
    }

    private void getSearchList(String keyword) {
        searchVOList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            SearchVO searchVO = new SearchVO();
            searchVO.setAvatar(null);
            searchVO.setObjectId(i);
            searchVO.setObjectType(ChatMsgUtil.Character.TYPE_FRIEND.getName());
            searchVO.setObjectNick("好友" + i);
            searchVOList.add(searchVO);
        }
        for (int i = 1; i <= 5; i++) {
            SearchVO searchVO = new SearchVO();
            searchVO.setAvatar(null);
            searchVO.setObjectId(i);
            searchVO.setObjectType(ChatMsgUtil.Character.TYPE_GROUP.getName());
            searchVO.setObjectNick("群聊" + i);
            searchVO.setMemberCount(i + 10);
            searchVOList.add(searchVO);
        }
        for (int i = 1; i <= 5; i++) {
            SearchVO searchVO = new SearchVO();
            searchVO.setAvatar(null);
            searchVO.setObjectId(i);
            searchVO.setObjectType(ChatMsgUtil.Character.TYPE_CHANNEL.getName());
            searchVO.setObjectNick("频道" + i);
            searchVO.setMemberCount(i + 20);
            searchVOList.add(searchVO);
        }
        searchVoAdapter = new SearchVoAdapter(this, searchVOList);
        searchListView.setAdapter(searchVoAdapter);
    }
}