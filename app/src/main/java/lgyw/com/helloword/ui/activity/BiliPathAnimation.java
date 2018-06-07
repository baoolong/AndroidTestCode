package lgyw.com.helloword.ui.activity;

import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;

import lgyw.com.helloword.ui.weight.BiliPathView;

/**
 * @author MrRight
 */
public class BiliPathAnimation extends AppCompatActivity implements SurfaceHolder.Callback{

    BiliPathView biliPathView;
    SurfaceHolder mHolder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        biliPathView=new BiliPathView(this);

        mHolder=biliPathView.getHolder();
        mHolder.addCallback(this);
        setContentView(biliPathView);
    }

    private void init(){
        Path path1 = new Path();
        path1.moveTo(310, 0);
        path1.lineTo(310, 400);
        path1.lineTo(210, 500);
        path1.lineTo(210, 600);
        path1.lineTo(310, 700);
        path1.lineTo(310, 1280);

        Paint srcPaint=new Paint();
        String text="Hello world";
        Path desPath = new Path();
        srcPaint.getTextPath(text,0,text.length(),50,100,desPath);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        biliPathView.startUpdateProgress();
        biliPathView.drawToScreen();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
