package com.project.gimme.view.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.gimme.R;
import com.project.gimme.utils.BundleUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author DrGilbert
 */
@SuppressLint("NonConstantResourceId")
public class ParamActivity extends SwipeBackActivity {
    @BindView(R.id.param_top_text)
    TextView topText;
    @BindView(R.id.param_top_left_text)
    TextView topLeftText;
    @BindView(R.id.param_top_right_text)
    TextView topRightText;
    @BindView(R.id.param_top_bar)
    RelativeLayout topBar;
    @BindView(R.id.param_param_text)
    EditText paramText;
    @BindView(R.id.param_param_layout)
    RelativeLayout paramLayout;
    private String paramName;
    private String paramValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_param);
        ButterKnife.bind(this);
        getParam();
        initTopBar();
        initParamLayout();
    }

    private void getParam() {
        Bundle bundle = getIntent().getExtras();
        paramName = bundle.getString(BundleUtil.PARAM_NAME_ATTRIBUTE);
        paramValue = bundle.getString(BundleUtil.PARAM_VALUE_ATTRIBUTE);
    }

    private void initTopBar() {
        topLeftText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
            }
        });
        topRightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
            }
        });
        topText.setText("编辑" + paramName);
    }

    private void initParamLayout() {
        paramText.setText(paramValue);
        paramText.setFocusableInTouchMode(true);
        paramText.setFocusable(true);
        paramText.requestFocus();
    }
}