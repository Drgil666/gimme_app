package com.project.gimme.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.ChatFileController;
import com.project.gimme.controller.ChatFileInfoController;
import com.project.gimme.pojo.ChatFile;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.FileUtil;
import com.project.gimme.utils.NumberUtil;
import com.project.gimme.utils.XToastUtils;
import com.xuexiang.xui.widget.dialog.bottomsheet.BottomSheet;

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
    TextView fileNickText;
    @BindView(R.id.chat_file_info_file_size)
    TextView fileSizeText;
    @BindView(R.id.chat_file_info_download_button)
    Button downloadButton;
    private String fileName;
    private String filePath;
    private Long fileSize;
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
        //TODO:?????????????????????id/?????????->uuid???????????????????????????uuid???
        initImageView();
        getFileInfo();
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
                new BottomSheet.BottomListSheetBuilder(mContext)
                        .addItem("???????????????")
                        .setIsCenter(true)
                        .setOnSheetItemClickListener((dialog, itemView, position, tag) -> {
                            dialog.dismiss();
                            authorize();
                            FileUtil.saveImageToGallery(mContext, ((BitmapDrawable) imageView.getDrawable()).getBitmap());
                            XToastUtils.toast("????????????!");
                        })
                        .build()
                        .show();
                return true;
            }
        });
    }

    private void getId() {
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getInt(BundleUtil.CHAT_FILE_INFO_ID_ATTRIBUTE);
        fileName = bundle.getString(BundleUtil.FILE_NAME_ATTRIBUTE);
        fileSize = bundle.getLong(BundleUtil.FILE_SIZE_ATTRIBUTE);
        fileNickText.setText(FileUtil.changeToFileName(fileName, 25));
        fileSizeText.setText(NumberUtil.changeToFileSize(fileSize));
        topText.setText(FileUtil.changeToFileName(fileName, 25));
    }

    private void getFileInfo() {
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
                            fileNickText.setText(FileUtil.changeToFileName(chatFile.getFilename(), 25));
                            fileSizeText.setText(NumberUtil.changeToFileSize(chatFile.getSize()));
                            topText.setText(FileUtil.changeToFileName(chatFile.getFilename(), 25));
                        }
                    });
                }
            }
        }).start();
    }

    private void initTopBar() {
        topLeftButton.setOnClickListener(view -> {
            finish();
        });
    }

    private void authorize() {
        //??????????????????????????????
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //????????????????????????????????????????????????????????????????????????????????? true???
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //?????????????????????????????????????????????????????????????????????????????????
                // ???????????????????????????????????????????????????.??????????????????"????????????"??????????????????false
            } else {
                //????????????????????????????????????????????????????????????????????????1??????????????????????????????????????????onRequestPermissionsResult????????????????????????
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
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
                        //TODO:??????????????????
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                authorize();
                                if (fileName.endsWith(".jpg")
                                        || fileName.endsWith(".gif")
                                        || fileName.endsWith(".png")
                                        || fileName.endsWith(".jpeg")
                                        || fileName.endsWith(".bmp")) {
                                    Glide.with(mContext)
                                            .load(new File(name))
                                            .into(imageView);
                                    imageView.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.alpha0to1));
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