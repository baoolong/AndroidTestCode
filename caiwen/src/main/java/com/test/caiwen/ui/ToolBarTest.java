package com.test.caiwen.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.test.caiwen.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author MrRight Created by MrRight on 2018/4/3.
 */

public class ToolBarTest extends Activity {

    @BindView(R.id.backImage)
    ImageView backImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        setContentView(R.layout.activity_toolbar);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
