package com.project.gimme.view.activity;

import static com.project.gimme.utils.BundleUtil.CHAT_TYPE_ATTRIBUTE;
import static com.project.gimme.utils.BundleUtil.OBJECT_ID_ATTRIBUTE;

import android.os.Bundle;
import android.widget.ImageView;

import com.project.gimme.R;

/**
 * @author DrGilbert
 */
public class GroupFileActivity extends SwipeBackActivity {
    private Integer groupId;
    private Integer type;
    private ImageView topLeftButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_file);
        topLeftButton = findViewById(R.id.group_file_top_left_button);
        getGroupId();
        initTopBar();
    }

    private void getGroupId() {
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt(CHAT_TYPE_ATTRIBUTE);
        groupId = bundle.getInt(OBJECT_ID_ATTRIBUTE);
    }

    private void initTopBar() {
        topLeftButton.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
        });
    }
}