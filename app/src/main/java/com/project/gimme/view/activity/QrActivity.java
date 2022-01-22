package com.project.gimme.view.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.project.gimme.R;

/**
 * @author DrGilbert
 */
public class QrActivity extends SwipeBackActivity {
    private ImageView topLeftButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        topLeftButton = findViewById(R.id.qr_top_left_button);
        initTopBar();
    }

    private void initTopBar() {
        topLeftButton.setOnClickListener(view -> {
            finish();
            overridePendingTransition(R.anim.back_left_in, R.anim.back_right_out);
        });
    }
}