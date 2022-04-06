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

import com.project.gimme.R;
import com.project.gimme.controller.ChatMsgController;
import com.project.gimme.pojo.vo.ChatMsgVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.view.adpter.ChatMsgAdapter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

/**
 * @author 25741
 */
@SuppressLint("NonConstantResourceId")
public class ChatMsgActivity extends BaseActivity {
    @BindView(R.id.chat_msg_search_edit_text)
    EditText searchEditText;
    @BindView(R.id.chat_msg_search_cancel)
    TextView searchCancel;
    @BindView(R.id.chat_msg_listview)
    ListView searchListView;
    private List<ChatMsgVO> chatMsgVOList = new ArrayList<>();
    private ChatMsgAdapter chatMsgAdapter;
    private Handler handler = new Handler();
    private Integer type;
    private Integer objectId;
    private Context mContext = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_msg);
        ButterKnife.bind(this);
        getType();
        initTopBar();
    }

    private void getType() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(BundleUtil.CHAT_TYPE_ATTRIBUTE);
        objectId = bundle.getInt(BundleUtil.OBJECT_ID_ATTRIBUTE);
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
                if (StringUtils.isEmpty(searchEditText.getText().toString())) {
                    chatMsgVOList = new ArrayList<>();
                    chatMsgAdapter = new ChatMsgAdapter(mContext, chatMsgVOList);
                    searchListView.setAdapter(chatMsgAdapter);
                } else {
                    getChatMessageList(type, objectId, searchEditText.getText().toString());
                }
            }
        });
    }

    private void getChatMessageList(Integer type, Integer objectId, String keyword) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<List<ChatMsgVO>> responseData =
                        ChatMsgController.getChatMsgVoList(ChatMsgUtil.CHARACTER_LIST[type].getName(), objectId, keyword);
                if (responseData != null && responseData.isSuccess()) {
                    chatMsgVOList = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            chatMsgAdapter = new ChatMsgAdapter(mContext, chatMsgVOList);
                            searchListView.setAdapter(chatMsgAdapter);
                        }
                    });
                }
            }
        }).start();
    }
}