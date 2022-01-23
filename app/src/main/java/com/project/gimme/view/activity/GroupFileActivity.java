package com.project.gimme.view.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.project.gimme.R;
import com.project.gimme.utils.BundleUtil;

/**
 * @author DrGilbert
 */
public class GroupFileActivity extends SwipeBackActivity {
    private Integer groupId;
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
        groupId = bundle.getInt(BundleUtil.OBJECT_ID_ATTRIBUTE);
    }

    private void initTopBar() {
        topLeftButton.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
        });
    }
}