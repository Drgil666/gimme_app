package com.project.gimme.view.fragment;

import static com.project.gimme.utils.BundleUtil.CHAT_TYPE_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_ID_ATTRIBUTE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.project.gimme.GimmeApplication;
import com.project.gimme.R;
import com.project.gimme.controller.ChatMsgController;
import com.project.gimme.pojo.vo.MessageVO;
import com.project.gimme.pojo.vo.ResponseData;
import com.project.gimme.utils.ChatMsgUtil;
import com.project.gimme.view.activity.ChatActivity;
import com.project.gimme.view.adpter.MessageVoAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lombok.SneakyThrows;

/**
 * @author DrGilbert
 */
public class MessageFragment extends Fragment {
    private List<MessageVO> messageVOList = new ArrayList<>();
    @BindView(R.id.message_list_view)
    ListView listView;
    @BindView(R.id.message_search_layout)
    RelativeLayout searchLayout;
    private Unbinder unbinder;
    Handler handler = new Handler();
    private Date lastFreshTimestamp;//最后一次刷新的时间

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        lastFreshTimestamp = new Date();
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        initSearchLayout(0.07);
        initListView();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initSearchLayout(double size) {
        searchLayout.getLayoutParams().height = (int) (GimmeApplication.getHeight() * size);
    }

    private void getMessageVOList() {
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                ResponseData<List<MessageVO>> responseData = ChatMsgController.getMessageVoList(lastFreshTimestamp);
                if (responseData != null && responseData.isSuccess()) {
                    messageVOList = responseData.getData();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listView.setAdapter(new MessageVoAdapter(getContext(), messageVOList));
                        }
                    });
                }
            }
        }).start();
    }

    private void initListView() {
        getMessageVOList();
        listView.setAdapter(new MessageVoAdapter(getContext(), messageVOList));
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String type = ((MessageVoAdapter) listView.getAdapter()).getItem(position).getType();
            Integer chatType = ChatMsgUtil.getCharacterByName(type);
            Bundle bundle = new Bundle();
            bundle.putInt(OBJECT_ID_ATTRIBUTE, (int) id);
            bundle.putInt(CHAT_TYPE_ATTRIBUTE, chatType);
            Intent intent = new Intent(getActivity(), ChatActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
        });
    }

}