package com.project.gimme.view.activity;

import static com.project.gimme.utils.BundleUtil.CHAT_TYPE_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_ID_ATTRIBUTE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.ChatFileController;
import com.project.gimme.controller.ChatFileInfoController;
import com.project.gimme.pojo.ChatFile;
import com.project.gimme.pojo.vo.ChatFileVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.FileUtil;
import com.project.gimme.utils.XToastUtils;
import com.project.gimme.view.adpter.ChatFileAdapter;
import com.project.gimme.view.listview.PullRefreshListView;

import java.io.File;
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
    @BindView(R.id.chat_file_top_left_button)
    ImageView topLeftButton;
    @BindView(R.id.chat_file_search_text)
    TextView searchText;
    private String searchKeyword = "";
    private List<ChatFileVO> chatFileVOList = new ArrayList<>();
    @BindView(R.id.chat_file_total_file_text)
    TextView totalFileText;
    @BindView(R.id.chat_file_listview)
    PullRefreshListView chatFileListView;
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
        chatFileAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                //intent.setType("image/*");//选择图片
                //intent.setType("audio/*"); //选择音频
                //intent.setType("video/*"); //选择视频 （mp4 3gp 是android支持的视频格式）
                //intent.setType("video/*;image/*");//同时选择视频和图片
                intent.setType("*/*");//无类型限制
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);
            }
        });
    }

    @SneakyThrows
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
        if (FileUtil.getRealPathFromUri(this, uri) != null) {
            //从uri得到绝对路径，并获取到file文件
            File file = new File(FileUtil.getRealPathFromUri(this, uri));
            uploadFile(file, ChatMsgUtil.CHARACTER_LIST[type].getName(), objectId);
        }
    }

    private void uploadFile(File file, String chatType, Integer objectId) {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<ChatFile> responseData = ChatFileInfoController.upLoadFile(file, chatType, objectId);
                if (responseData != null && responseData.isSuccess()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            XToastUtils.toast("上传成功!");
                            getChatFileList();
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
                        ChatFileController.getChatFileVoListByObjectId(chatType, objectId, searchKeyword);
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
        chatFileListView.setOnItemClickListener((adapterView, view, position, id) -> {
            Bundle bundle = new Bundle();
            bundle.putInt(BundleUtil.CHAT_FILE_INFO_ID_ATTRIBUTE, (int) id);
            bundle.putString(BundleUtil.FILE_NAME_ATTRIBUTE, chatFileVOList.get(position - 1).getFilename());
            bundle.putLong(BundleUtil.FILE_SIZE_ATTRIBUTE, chatFileVOList.get(position - 1).getSize());
            Intent intent = new Intent(getApplicationContext(), ChatFileInfoActivity.class).putExtras(bundle);
            startActivity(intent);
        });
        chatFileListView.setOnRefreshListener(new PullRefreshListView.OnRefreshListener() {
            @Override
            public void onPullRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getChatFileList();
                        chatFileAdapter.notifyDataSetChanged();
                        chatFileListView.onRefreshComplete();
                    }
                }, GimmeApplication.DRAG_TIME);
            }

            @Override
            public void onLoadMore() {
//                下拉方法
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        getMessageVOList();
//                        messageVoAdapter.notifyDataSetChanged();
//                        listView.onRefreshComplete();
//                    }
//                }, 2000);
            }
        });
    }
}