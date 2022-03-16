package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.FileProvider;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.ChatFileController;
import com.project.gimme.pojo.ChatFile;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.NumberUtil;

import java.io.File;

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
        filePath = getFilesDir().getAbsolutePath()
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
            Context mContext = getApplicationContext();

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
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                Uri uri;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    //判读版本是否在7.0以上
                                    //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                                    uri = FileProvider.getUriForFile(mContext, getPackageName() + ".fileprovider", new File(name));
                                    //添加这一句表示对目标应用临时授权该Uri所代表的文件
                                } else {
                                    uri = Uri.fromFile(new File(name));
                                }
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                                mContext.grantUriPermission(getPackageName(), uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                                        | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                intent.putExtra(Intent.EXTRA_STREAM, uri);
                                intent.setType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
                                startActivity(Intent.createChooser(intent, "分享"));
                                //TODO:待修复
                            }
                        });
                    }
                }).start();
            }
        });
    }
}