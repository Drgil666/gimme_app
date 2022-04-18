package com.project.gimme.view.fragment;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.project.gimme.utils.BundleUtil.CHAT_TYPE_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_ID_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_NICK_ATTRIBUTE;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.ChatMsgController;
import com.project.gimme.pojo.vo.MessageVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.ContactsUtil;
import com.project.gimme.utils.JsonUtil;
import com.project.gimme.view.activity.ChatActivity;
import com.project.gimme.view.activity.MainActivity;
import com.project.gimme.view.activity.SearchActivity;
import com.project.gimme.view.activity.WelcomeActivity;
import com.project.gimme.view.adpter.MessageVoAdapter;
import com.project.gimme.view.listview.PullRefreshListView;
import com.xuexiang.xui.widget.textview.badge.Badge;
import com.xuexiang.xui.widget.textview.badge.BadgeView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lombok.SneakyThrows;

/**
 * @author DrGilbert
 */
@SuppressLint("NonConstantResourceId")
public class MessageFragment extends Fragment {
    private List<MessageVO> messageVOList = new ArrayList<>();
    @BindView(R.id.message_list_view)
    PullRefreshListView listView;
    private Unbinder unbinder;
    Handler handler = new Handler();
    private MessageVoAdapter messageVoAdapter;
    @BindView(R.id.message_search_edittext)
    EditText searchEditText;
    private Timer timer = new Timer();
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            //System.out.println("接收消息!" + System.currentTimeMillis());
            getMessageVOList();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        initEditText();
        return view;
    }

    private void initEditText() {
        searchEditText.setOnFocusChangeListener(new android.view.View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(BundleUtil.SEARCH_TYPE_ATTRIBUTE, ContactsUtil.SearchType.TYPE_MESSAGE.getCode());
                    Intent intent = new Intent(getContext(), SearchActivity.class).putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        searchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(BundleUtil.SEARCH_TYPE_ATTRIBUTE, ContactsUtil.SearchType.TYPE_MESSAGE.getCode());
                Intent intent = new Intent(getContext(), SearchActivity.class).putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        initListView();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                //System.out.println("接收消息!" + System.currentTimeMillis());
                getMessageVOList();
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 1500);
    }

    private Integer getNewMessageCount(List<MessageVO> messageVOList) {
        Integer cnt = 0;
        for (MessageVO messageVO : messageVOList) {
            cnt += messageVO.getNewMessageCount();
        }
        return cnt;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            getMessageVOList();
            timerTask = new TimerTask() {
                @Override
                public void run() {
//                    System.out.println("接收消息!" + System.currentTimeMillis());
                    getMessageVOList();
                }
            };
            timer = new Timer();
            timer.schedule(timerTask, 0, 1500);
        } else {
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private Boolean isEqual(List<MessageVO> messageVOList1, List<MessageVO> messageVOList2) {
        if (messageVOList1.size() != messageVOList2.size()) {
            System.out.println("长度不同!");
            return false;
        }
        for (int i = 0; i < messageVOList1.size(); i++) {
            if (!JsonUtil.toJson(messageVOList1.get(i)).equals(JsonUtil.toJson(messageVOList2.get(i)))) {
                System.out.println("第" + i + "个长度不同!");
                System.out.println(JsonUtil.toJson(messageVOList1.get(i)));
                System.out.println(JsonUtil.toJson(messageVOList2.get(i)));
                return false;
            }
        }
        return true;
    }

    private void getMessageVOList() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<List<MessageVO>> responseData = ChatMsgController.getMessageVoList("");
                if (responseData != null && responseData.isSuccess()) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!isEqual(responseData.getData(), messageVOList)) {
                                System.out.println("MessageVO数据更新!");
                                messageVOList = responseData.getData();
                                if (messageVOList != null && messageVOList.size() != 0) {
                                    createNotice();
                                    createBadge();
                                }
                                messageVoAdapter = new MessageVoAdapter(getContext(), messageVOList);
                                listView.setAdapter(messageVoAdapter);
                                messageVoAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }
        }).start();
    }

    private void createBadge() {
        Integer total = 0;
        for (MessageVO messageVO : messageVOList) {
            if (messageVO.getNewMessageCount() != 0) {
                total += messageVO.getNewMessageCount();
            }
        }
        View layoutCount = (((MainActivity) getContext()).findViewById(R.id.main_message_layout_new_message_count_background));
        if (total != 0) {
            layoutCount.setVisibility(View.VISIBLE);
            Badge badge = new BadgeView(getContext())
                    .bindTarget(layoutCount)
                    .setBadgeTextSize(18, true)
                    .setBadgeNumber(total);
        } else {
            layoutCount.setVisibility(View.GONE);
        }
    }

    private void createNotice() {
        for (MessageVO messageVO : messageVOList) {
            if (messageVO.getNewMessageCount() != 0) {
                createNotification(messageVO);
            }
        }

    }

    public void createNotification(MessageVO messageVO) {
        Integer chatType = ChatMsgUtil.getCharacterByName(messageVO.getType());
        Integer objectId = messageVO.getObjectId();
        String text = messageVO.getNick() + ":" + messageVO.getText();
        Integer noticeType = GimmeApplication.getNotice(chatType, objectId);
        NotificationManager manager = (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);
        Intent intent;
        if (isAppRunning(getContext())) {
            Bundle bundle = new Bundle();
            bundle.putInt(CHAT_TYPE_ATTRIBUTE, chatType);
            bundle.putInt(OBJECT_ID_ATTRIBUTE, objectId);
            bundle.putString(OBJECT_NICK_ATTRIBUTE, messageVO.getNick());
            intent = new Intent(getContext(), ChatActivity.class).putExtras(bundle);
        } else {
            intent = new Intent(getContext(), WelcomeActivity.class);
        }
        PendingIntent pit = PendingIntent.getActivity(getContext(), 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(getContext())
                .setContentTitle(GimmeApplication.APP_NAME)
                //设置标题
                .setContentText(text)
                //设置内容
                .setWhen(messageVO.getTimestamp().getTime())
                //设置时间
                .setSmallIcon(R.mipmap.app_icon_round)
                //设置小图标
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon_round))
                //点击后自动取消
                .setAutoCancel(true)
                //设置大图标
                .setSound(Uri.fromFile(new File("/system/media/audio/ringtones/Barium.ogg")))
                //播放通知音
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                //默认形式播放铃声
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(pit)
                .build();
        manager.notify(noticeType, notification);
        System.out.println("设定通知: " + text);
    }

    private boolean isAppRunning(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = null;
        if (activityManager != null) {
            list = activityManager.getRunningTasks(100);
        }
        if (list == null || list.size() <= 0) {
            return false;
        }
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.baseActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    private boolean isAppForeground(Context context) {
        if (context != null) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> processes;
            if (activityManager != null) {
                processes = activityManager.getRunningAppProcesses();
            } else {
                return false;
            }
            for (ActivityManager.RunningAppProcessInfo processInfo : processes) {
                if (processInfo.processName.equals(context.getPackageName())) {
                    if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void initListView() {
        getMessageVOList();
        listView.setOnItemClickListener((parent, view, position, id) -> {
            MessageVO messageVO = messageVoAdapter.getItem(position - 1);
            //因为多了个请求开头,所以要减去1
            String type = messageVO.getType();
            messageVOList.get(position - 1).setNewMessageCount(0);
            messageVoAdapter.notifyDataSetChanged();
            Integer chatType = ChatMsgUtil.getCharacterByName(type);
            Bundle bundle = new Bundle();
            bundle.putInt(OBJECT_ID_ATTRIBUTE, (int) id);
            bundle.putInt(CHAT_TYPE_ATTRIBUTE, chatType);
            bundle.putString(OBJECT_NICK_ATTRIBUTE, messageVO.getNick());
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
        listView.setOnRefreshListener(new PullRefreshListView.OnRefreshListener() {
            @Override
            public void onPullRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getMessageVOList();
                        messageVoAdapter.notifyDataSetChanged();
                        listView.onRefreshComplete();
                    }
                }, 2000);
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