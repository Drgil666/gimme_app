package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.ChatFileController;
import com.project.gimme.pojo.ChatFile;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.FileOpenUtil;
import com.project.gimme.utils.NumberUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

/**
 * @author DrGilbert
 */
@SuppressLint("NonConstantResourceId")
public class ChatFileInfoActivity extends SwipeBackActivity {

    private Integer id;
    Handler handler = new Handler();
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
    private String fileName;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_file_info);
        ButterKnife.bind(this);
        filePath = getApplicationContext().getFilesDir().getAbsolutePath()
                + "/" + GimmeApplication.getUserId();
        getId();
        getFile();
        initTopBar();
        initDownLoad();
    }

    private void getId() {
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getInt(BundleUtil.CHAT_FILE_INFO_ID_ATTRIBUTE);
        fileName = bundle.getString(BundleUtil.FILE_NAME_ATTRIBUTE);
//        System.out.println("id: " + id + " fileName:" + fileName);
    }

    private void getFile() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<ChatFile> responseData =
                        ChatFileController.getChatFile(id.toString());
                if (responseData != null && responseData.isSuccess()) {
                    chatFile = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            fileNick.setText(chatFile.getFilename());
                            fileSize.setText(NumberUtil.changeToFileSize(chatFile.getSize()));
                            topText.setText(chatFile.getFilename());
                        }
                    });
                }
            }
        }).start();
    }

    private void initTopBar() {
        topLeftButton.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
        });
    }

    private void initDownLoad() {
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        ChatFileController.downloadFile(filePath, id);
                        //TODO:优化文件目录
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                String name = filePath + "/" + fileName;
                                Intent intent = FileOpenUtil.openFile(name);
                                startActivity(intent);
                                //TODO:待修复
                            }
                        });
                    }
                }).start();
            }
        });
    }
}