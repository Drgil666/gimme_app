package com.project.gimme.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.ChatFileController;
import com.project.gimme.controller.ChatFileInfoController;
import com.project.gimme.pojo.ChatFile;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.FileUtil;
import com.project.gimme.utils.NumberUtil;
import com.project.gimme.utils.PicassoTransformation;
import com.squareup.picasso.Picasso;

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
    private Integer type;
    private Integer objectId;
    final Context mContext = this;
    @BindView(R.id.chat_file_info_img)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_file_info);
        ButterKnife.bind(this);
        String path = getExternalFilesDir(null).getPath();
        getId();
        filePath = path + "/" + GimmeApplication.getUserId() + "/" + id;
        //TODO:本地数据库采用id/文件名->uuid映射，文件存储采用uuid。
        initImageView();
        getFile();
        initTopBar();
        downLoad();
    }

    private void initImageView() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.alpha1to0));
                imageView.setVisibility(View.GONE);
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    private void getId() {
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getInt(BundleUtil.CHAT_FILE_INFO_ID_ATTRIBUTE);
        fileName = bundle.getString(BundleUtil.FILE_NAME_ATTRIBUTE);
        type = bundle.getInt(BundleUtil.CHAT_TYPE_ATTRIBUTE);
        objectId = bundle.getInt(BundleUtil.OBJECT_ID_ATTRIBUTE);
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

    private void authorize() {
        //判断是否已经赋予权限
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限.它在用户选择"不再询问"的情况下返回false
            } else {
                //申请权限，字符串数组内是一个或多个要申请的权限，1是申请权限结果的返回参数，在onRequestPermissionsResult可以得知申请结果
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1);
            }
        }
    }

    private void downLoad() {
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        ChatFileInfoController.downloadFile(filePath, id, fileName);
                        String name = filePath + "/" + fileName;
                        //TODO:优化文件目录
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                authorize();
                                if (fileName.endsWith(".jpg")
                                        || fileName.endsWith(".gif")
                                        || fileName.endsWith(".png")
                                        || fileName.endsWith(".jpeg")
                                        || fileName.endsWith(".bmp")) {
                                    Picasso.with(mContext)
                                            .load(new File(name))
                                            .transform(new PicassoTransformation())
                                            .into(imageView);
                                    imageView.setVisibility(View.VISIBLE);
                                } else {
                                    FileUtil.openFile(mContext, name);
                                }
                            }
                        });
                    }
                }).start();
            }
        });
    }
}