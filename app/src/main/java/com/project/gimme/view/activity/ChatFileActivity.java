package com.project.gimme.view.activity;

import static com.project.gimme.utils.BundleUtil.CHAT_TYPE_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_ID_ATTRIBUTE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.gimme.R;
import com.project.gimme.controller.ChatFileController;
import com.project.gimme.pojo.vo.ChatFileVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.view.adpter.ChatFileAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

/**
 * @author DrGilbert
 */
@SuppressLint("NonConstantResourceId")
public class ChatFileActivity extends SwipeBackActivity {
    Handler handler = new Handler();
    private Integer type;
    @BindView(R.id.chat_file_info_top_left_button)
    ImageView topLeftButton;
    @BindView(R.id.chat_file_search_text)
    TextView searchText;
    private String searchKeyword = "";
    private List<ChatFileVO> chatFileVOList = new ArrayList<>();
    @BindView(R.id.chat_file_total_file_text)
    TextView totalFileText;
    @BindView(R.id.chat_file_listview)
    ListView chatFileListView;
    private ChatFileAdapter chatFileAdapter;
    private Integer objectId;
    @BindView(R.id.chat_file_add_button)
    FloatingActionButton chatFileAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_file);
        ButterKnife.bind(this);
        getType();
        getChatFileList();
        initTopBar();
        initChatFileListView();
        initChatFileAddButton();
    }

    private void getType() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(CHAT_TYPE_ATTRIBUTE);
        objectId = bundle.getInt(OBJECT_ID_ATTRIBUTE);
    }

    private void initChatFileAddButton() {
        //TODO:添加上传文件
    }

    private void initTopBar() {
        topLeftButton.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
        });
        searchText.setText("");
        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                System.out.println(searchText.getText().toString());
                searchKeyword = searchText.getText().toString();
                getChatFileList();
                chatFileAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getChatFileList() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                String chatType = ChatMsgUtil.CHARACTER_LIST[type].getName();
                ResponseData<List<ChatFileVO>> responseData =
                        ChatFileController.getChatFileVoByObjectId(chatType, objectId, searchKeyword);
                if (responseData != null && responseData.isSuccess()) {
                    chatFileVOList = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            chatFileAdapter = new ChatFileAdapter(ChatFileActivity.this, chatFileVOList);
                            chatFileListView.setAdapter(chatFileAdapter);
                            totalFileText.setText("共" + chatFileVOList.size() + "个文件");
                        }
                    });
                }
            }
        }).start();
    }

    private void initChatFileListView() {
        chatFileListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleUtil.CHAT_FILE_INFO_ID_ATTRIBUTE, (int) l);
            Intent intent = new Intent(getApplicationContext(), ChatFileInfoActivity.class).putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        });
    }
}