package com.project.gimme.view.fragment;

import static com.project.gimme.utils.BundleUtil.CHAT_TYPE_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_ID_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_NICK_ATTRIBUTE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.project.gimme.R;
import com.project.gimme.controller.ChatMsgController;
import com.project.gimme.pojo.vo.MessageVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.utils.ContactsUtil;
import com.project.gimme.utils.JsonUtil;
import com.project.gimme.view.activity.ChatActivity;
import com.project.gimme.view.activity.SearchActivity;
import com.project.gimme.view.adpter.MessageVoAdapter;
import com.project.gimme.view.listview.PullRefreshListView;

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
            System.out.println("接收消息!" + System.currentTimeMillis());
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
                System.out.println("接收消息!" + System.currentTimeMillis());
                getMessageVOList();
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
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
                    System.out.println("接收消息!" + System.currentTimeMillis());
                    getMessageVOList();
                }
            };
            timer = new Timer();
            timer.schedule(timerTask, 0, 1000);
        } else {
            timer.cancel();
            timer = null;
            timerTask.cancel();
            timerTask = null;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private Boolean isEqual(List<MessageVO> messageVOList1, List<MessageVO> messageVOList2) {
        return JsonUtil.toJson(messageVOList1).equals(JsonUtil.toJson(messageVOList2));
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
                                messageVOList = responseData.getData();
                                messageVoAdapter = new MessageVoAdapter(getContext(), messageVOList);
                                listView.setAdapter(messageVoAdapter);
                            }
                        }
                    });
                }
            }
        }).start();
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