package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.project.gimme.R;
import com.project.gimme.pojo.vo.PersonalMsgVO;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.utils.JsonUtil;
import com.project.gimme.utils.PersonalMsgUtil;
import com.project.gimme.utils.XToastUtils;
import com.project.gimme.view.adpter.PersonalMsgListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 */
@SuppressLint("NonConstantResourceId")
public class PersonalMsgListActivity extends SwipeBackActivity {
    private List<PersonalMsgVO> personalMsgVOList = new ArrayList<>();
    @BindView(R.id.personal_msg_list_list_view)
    ListView personalMsgListView;
    @BindView(R.id.personal_msg_list_top_nick_text)
    TextView topText;
    @BindView(R.id.personal_msg_list_top_left_button)
    ImageView leftButton;
    private Integer type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_msg_list);
        ButterKnife.bind(this);
        getType();
        initTopBar();
    }

    private void initTopBar() {
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (type.equals(PersonalMsgUtil.PersonalMsgType.TYPE_FRIEND_PERSONAL_MSG.getCode())) {
            setTopText("新朋友");
            initPersonalMsgListView();
        } else if (type.equals(PersonalMsgUtil.PersonalMsgType.TYPE_OTHER_PERSONAL_MSG.getCode())) {
            setTopText("群聊/频道消息");
            initPersonalMsgListView();
        } else {
            XToastUtils.toast("类型错误!");
        }
    }

    private void setTopText(String text) {
        topText.setText(text);
    }

    private void getType() {
        Bundle bundle = this.getIntent().getExtras();
        type = bundle.getInt(BundleUtil.PERSONAL_MSG_TYPE_ATTRIBUTE);
        personalMsgVOList = JsonUtil.fromJson(bundle.getString(BundleUtil.OBJECT_ATTRIBUTE),
                new TypeToken<List<PersonalMsgVO>>() {
                }.getType());
        //System.out.println("type:" + type);
    }

    private void initPersonalMsgListView() {
        personalMsgListView.setAdapter(new PersonalMsgListAdapter(this, personalMsgVOList));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}