package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.project.gimme.R;
import com.project.gimme.pojo.vo.PersonalMsgVO;
import com.project.gimme.utils.BundleUtil;
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
    private static final Integer TYPE_PERSONAL = 0;
    private static final Integer TYPE_OTHER = 1;
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
        if (type.equals(TYPE_PERSONAL)) {
            setTopText("新朋友");
            getPersonalMsgPersonalList();
            initPersonalMsgListView();
        } else if (type.equals(TYPE_OTHER)) {
            setTopText("群聊/频道消息");
            getPersonalMsgOtherList();
            initPersonalMsgListView();
        } else {
            XToastUtils.toast("类型错误!");
        }
    }

    private void setTopText(String text) {
        topText.setText(text);
    }

    private void getPersonalMsgPersonalList() {
        for (int i = 1; i <= 2; i++) {
            PersonalMsgVO personalMsgVO = new PersonalMsgVO();
            personalMsgVO.setId(i);
            personalMsgVO.setObjectId(1);
            personalMsgVO.setOperatorNick("被操作用户");
            personalMsgVO.setObjectId(1);
            personalMsgVO.setNote("note" + i);
            personalMsgVO.setStatus(0);
            personalMsgVO.setObjectNick("群聊/频道" + i);
            personalMsgVO.setType((i - 1) + "");
            personalMsgVOList.add(personalMsgVO);
        }
    }

    private void getPersonalMsgOtherList() {
        for (int i = 3; i <= 13; i++) {
            PersonalMsgVO personalMsgVO = new PersonalMsgVO();
            personalMsgVO.setId(i);
            personalMsgVO.setObjectId(1);
            personalMsgVO.setOperatorNick("被操作用户");
            personalMsgVO.setObjectId(1);
            personalMsgVO.setNote("note" + i);
            personalMsgVO.setStatus(0);
            personalMsgVO.setObjectNick("群聊/频道" + i);
            personalMsgVO.setType((i - 1) + "");
            personalMsgVOList.add(personalMsgVO);
        }
    }

    private void getType() {
        Bundle bundle = this.getIntent().getExtras();
        type = bundle.getInt(BundleUtil.PERSONAL_MSG_TYPE_ATTRIBUTE);
        System.out.println("type:" + type);
    }

    private void initPersonalMsgListView() {
        personalMsgListView.setAdapter(new PersonalMsgListAdapter(this, personalMsgVOList));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}