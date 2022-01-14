package com.project.gimme.view.activity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.project.gimme.R;
import com.project.gimme.pojo.vo.PersonalMsgVO;
import com.project.gimme.view.adpter.PersonalMsgListAdapter;

import java.util.List;

/**
 * @author DrGilbert
 */
public class PersonalMsgListActivity extends BaseActivity {
    private static final Integer TYPE_PERSONAL = 1;
    private static final Integer TYPE_OTHER = 2;
    private List<PersonalMsgVO> personalMsgVOList;
    private ListView personalMsgListView;
    private TextView topText;
    private Integer type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_msg_list);
        getType();
        personalMsgListView = findViewById(R.id.personal_msg_list_list_view);
        initPersonalMsgListView();
    }

    private void getPersonalMsgList() {
        if (type.equals(TYPE_PERSONAL)) {
            getPersonalMsgPersonalList();
        } else {
            getPersonalMsgOtherList();
        }
    }

    private void getPersonalMsgPersonalList() {
        for (int i = 1; i <= 2; i++) {
            PersonalMsgVO personalMsgVO = new PersonalMsgVO();
            personalMsgVO.setId(i);
            personalMsgVO.setObjectId(1);
            personalMsgVO.setObjectNick("被操作用户");
            personalMsgVO.setObjectId(1);
            personalMsgVO.setObjectNick("群聊/频道" + i);
            personalMsgVO.setType(i - 1);
        }
    }

    private void getPersonalMsgOtherList() {
        for (int i = 3; i <= 13; i++) {
            PersonalMsgVO personalMsgVO = new PersonalMsgVO();
            personalMsgVO.setId(i);
            personalMsgVO.setObjectId(1);
            personalMsgVO.setObjectNick("被操作用户");
            personalMsgVO.setObjectId(1);
            personalMsgVO.setObjectNick("群聊/频道" + i);
            personalMsgVO.setType(i - 1);
        }
    }

    private void getType() {
        Bundle bundle = this.getIntent().getExtras();
        type = bundle.getInt("type");
    }

    private void initPersonalMsgListView() {
        getPersonalMsgList();
        personalMsgListView.setAdapter(new PersonalMsgListAdapter(this, personalMsgVOList));
    }
}