package lgyw.com.helloword.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import butterknife.BindView;
import butterknife.ButterKnife;
import lgyw.com.helloword.R;
import lgyw.com.helloword.service.AccessibilitySampleService;

/**
 * @author MrRight
 */
public class AccessibilityServices extends AppCompatActivity implements View.OnClickListener {

    private final String TAG="AccessibilityServices";

    @BindView(R.id.openButton)
    Button openButton;
    @BindView(R.id.startService)
    Button startService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accessibility);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        openButton.setOnClickListener(this);
        startService.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.openButton:
                intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivity(intent);
                break;
            case R.id.startService:
                intent = new Intent(AccessibilityServices.this, AccessibilitySampleService.class);
                startService(intent);
                break;
            default:
        }
    }

}
