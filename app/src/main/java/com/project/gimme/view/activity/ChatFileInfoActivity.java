package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.gimme.R;
import com.project.gimme.pojo.ChatFile;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.NumberUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 */
@SuppressLint("NonConstantResourceId")
public class ChatFileInfoActivity extends SwipeBackActivity {

    private Integer id;
    private ChatFile chatFile = new ChatFile();
    @BindView(R.id.chat_file_info_top_text)
    TextView topText;
    @BindView(R.id.chat_file_info_top_left_button)
    ImageView topLeftButton;
    @BindView(R.id.chat_file_info_file_nick)
    TextView fileNick;
    @BindView(R.id.chat_file_info_file_size)
    TextView fileSize;
    @BindView(R.id.chat_file_info_download_button)
    Button downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_file_info);
        ButterKnife.bind(this);
        getId();
        getFile();
        initTopBar();
        initFileLayout();
        initDownLoad();
    }

    private void getId() {
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getInt(BundleUtil.CHAT_FILE_INFO_ID_ATTRIBUTE);
        System.out.println("id: " + id);
    }

    private void getFile() {
        chatFile.setId(id);
        chatFile.setMongoId("111");
        chatFile.setFilename("文件.txt");
        chatFile.setSize(1512123123L);
        chatFile.setType(1);
        chatFile.setOwnerId(1);
    }

    private void initTopBar() {
        topLeftButton.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
        });
        topText.setText(chatFile.getFilename());
    }

    private void initFileLayout() {
        fileNick.setText(chatFile.getFilename());
        fileSize.setText(NumberUtil.changeToFileSize(chatFile.getSize()));
    }

    private void initDownLoad() {
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }
}