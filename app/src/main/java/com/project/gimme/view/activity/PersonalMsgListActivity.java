package com.project.gimme.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.gimme.R;
import com.project.gimme.pojo.vo.PersonalMsgVO;
import com.project.gimme.utils.BundleUtil;
import com.project.gimme.view.adpter.PersonalMsgListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author DrGilbert
 */
public class PersonalMsgListActivity extends SwipeBackActivity {
    private static final Integer TYPE_PERSONAL = 0;
    private static final Integer TYPE_OTHER = 1;
    private List<PersonalMsgVO> personalMsgVOList = new ArrayList<>();
    private ListView personalMsgListView;
    private TextView topText;
    private ImageView leftButton;
    private Integer type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_msg_list);
        getType();
        leftButton = findViewById(R.id.personal_msg_list_top_left_button);
        personalMsgListView = findViewById(R.id.personal_msg_list_list_view);
        topText = findViewById(R.id.personal_msg_list_top_nick_text);
        initTopBar();
    }

    private void initTopBar() {
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
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
            Toast.makeText(this, "类型错误!", Toast.LENGTH_LONG).show();
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
            personalMsgVO.setType(i - 1);
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
            personalMsgVO.setType(i - 1);
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
        overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
        super.onDestroy();
    }
}