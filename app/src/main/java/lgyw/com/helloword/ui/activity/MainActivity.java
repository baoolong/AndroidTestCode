package lgyw.com.helloword.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import lgyw.com.helloword.BuildConfig;
import lgyw.com.helloword.R;

/**
 * @author MrRight
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG="MainActivity";

    @BindView(R.id.pullRefreshLoadMore)
    Button pullRefreshLoadMore;
    @BindView(R.id.textPathAnimation)
    Button textPathAnimation;
    @BindView(R.id.biliPathAnimation)
    Button biliPathAnimation;
    @BindView(R.id.accessibility)
    Button accessibility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initEvent();
    }

    private void initEvent() {
        pullRefreshLoadMore.setOnClickListener(this);
        textPathAnimation.setOnClickListener(this);
        biliPathAnimation.setOnClickListener(this);
        accessibility.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.pullRefreshLoadMore:
                intent = new Intent(this, PullRefreshLoadMore.class);
                startActivity(intent);
                break;
            case R.id.biliPathAnimation:
                intent = new Intent(this, BiliPathAnimation.class);
                startActivity(intent);
                break;
            case R.id.textPathAnimation:
                intent = new Intent(this, TextPathAnimation.class);
                startActivity(intent);
                break;
            case R.id.accessibility:
                intent = new Intent(this, AccessibilityServices.class);
                startActivity(intent);
                break;
        }
    }
}
