package com.test.caiwen.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.test.caiwen.R;
import com.test.caiwen.customview.TwinkleTextView;

/**
 * @author Created by MrRight on 2018/2/26.
 */

public class TwinkleText extends Activity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twinlletext);
        TwinkleTextView twinkleTextView=findViewById(R.id.mTextView);
        twinkleTextView.setText("龙岗远望");
        twinkleTextView.setBackgroundColor(0xffff0000);
    }
}
