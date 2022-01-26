package com.project.gimme.view.activity;

import static com.project.gimme.utils.BundleUtil.CHAT_TYPE_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_ID_ATTRIBUTE;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.gimme.R;
import com.project.gimme.pojo.vo.ChatFileVO;
import com.project.gimme.view.adpter.ChatFileAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author DrGilbert
 */
public class ChatFileActivity extends SwipeBackActivity {
    private Integer groupId;
    private Integer type;
    private ImageView topLeftButton;
    private TextView searchText;
    private String searchKeyword = "";
    private List<ChatFileVO> chatFileVOList = new ArrayList<>();
    private TextView totalFileText;
    private ListView chatFileListView;
    private ChatFileAdapter chatFileAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_file);
        topLeftButton = findViewById(R.id.chat_file_top_left_button);
        searchText = findViewById(R.id.chat_file_search_text);
        totalFileText = findViewById(R.id.chat_file_total_file_text);
        chatFileListView = findViewById(R.id.chat_file_listview);
        chatFileAdapter = new ChatFileAdapter(this, chatFileVOList);
        getType();
        getChatFileList();
        initTopBar();
        setTotalFileText();
        initChatFileListView();
    }

    private void getType() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(CHAT_TYPE_ATTRIBUTE);
        groupId = bundle.getInt(OBJECT_ID_ATTRIBUTE);
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
                setTotalFileText();
                chatFileAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getChatFileList() {
        chatFileVOList.clear();
        for (int i = 1; i <= 10; i++) {
            ChatFileVO chatFileVO = new ChatFileVO();
            chatFileVO.setFilename("文件" + i + searchKeyword + ".txt");
            chatFileVO.setId(i);
            chatFileVO.setOwnerId(i);
            chatFileVO.setOwnerNick("用户" + i);
            chatFileVO.setObjectId(groupId);
            chatFileVO.setMongoId("111");
            chatFileVO.setSize(10451245645L);
            chatFileVO.setTimestamp(new Date());
            chatFileVO.setType(type);
            chatFileVOList.add(chatFileVO);
        }
    }

    private void setTotalFileText() {
        totalFileText.setText("共" + chatFileVOList.size() + "个文件");
    }

    private void initChatFileListView() {
        chatFileListView.setAdapter(chatFileAdapter);
    }
}