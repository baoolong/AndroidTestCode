package lgyw.com.helloword.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import lgyw.com.helloword.ui.weight.BiliPathImageView;

/**
 * @author MrRight
 */
public class TextPathAnimation extends AppCompatActivity {

    BiliPathImageView imageView;
    private Handler handler=new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageView=new BiliPathImageView(this);
        setContentView(imageView);
        waitAndInvokeRedraw();
    }

    private void waitAndInvokeRedraw(){
        imageView.startUpdateProgress();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            imageView.drawToScreen();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
