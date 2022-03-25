package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.project.gimme.R;
import com.project.gimme.controller.ChannelNoticeController;
import com.project.gimme.pojo.vo.ChatMsgVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.view.adpter.ChatMsgVoAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import lombok.SneakyThrows;

@SuppressLint("NonConstantResourceId")
public class ChannelNoticeActivity extends AppCompatActivity {
    private Integer channelNoticeId;
    private List<ChatMsgVO> chatMsgVOList;
    @BindView(R.id.channel_notice_top_left_button)
    ImageView topLeftButton;
    Handler handler = new Handler();
    ChatMsgVoAdapter chatMsgVoAdapter;
    private final Context mContext = this;
    @BindView(R.id.channel_notice_list_view)
    ListView channelNoticeListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_notice);
        ButterKnife.bind(this);
        getChannelNoticeId();
        getChatMsgVoList();
        initTopBar();
    }

    private void getChannelNoticeId() {
        Bundle bundle = getIntent().getExtras();
        channelNoticeId = bundle.getInt(BundleUtil.CHANNEL_NOTICE_ID_ATTRIBUTE);
        System.out.println("channelNoticeId:" + channelNoticeId);
    }

    private void initTopBar() {
        topLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getChatMsgVoList() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<List<ChatMsgVO>> responseData =
                        ChannelNoticeController.getChannelNoticeInfo(channelNoticeId);
                if (responseData != null && responseData.isSuccess()) {
                    chatMsgVOList = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            chatMsgVoAdapter = new ChatMsgVoAdapter(mContext, chatMsgVOList, ChatMsgUtil.Character.TYPE_CHANNEL_NOTICE.getCode());
                            channelNoticeListView.setAdapter(chatMsgVoAdapter);
                            channelNoticeListView.setSelection(chatMsgVOList.size() - 1);
                        }
                    });
                }
            }
        }).start();
    }
}