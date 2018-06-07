package lgyw.com.helloword.ui.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author MrRight
 */
public class BiliPathView extends SurfaceView{

    private ExecutorService executorService;
    private ExecutorService executors;
    private Canvas canvas;
    private Paint paint;
    private Bitmap bitmap;
    private Path [] paths=new Path[21];

    public BiliPathView(Context context) {
        super(context);
        init();
    }

    public BiliPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BiliPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        executors=Executors.newFixedThreadPool(2);

        Path path0=new Path();
        path0.moveTo(100,100);
        path0.lineTo(10,100);
        path0.lineTo(10,10);
        path0.lineTo(100,10);
        path0.close();

        Path path1=new Path(path0);
        path1.offset(100,0);

        Path path2=new Path(path1);
        path2.offset(100,0);

        Path path3=new Path(path2);
        path3.offset(100,0);

        Path path4=new Path(path3);
        path4.offset(100,0);

        Path path5=new Path(path4);
        path5.offset(100,0);

        Path path6=new Path(path5);
        path6.offset(100,0);



        Path path7=new Path(path0);
        path7.offset(0,100);

        Path path8=new Path(path7);
        path8.offset(100,0);

        Path path9=new Path(path8);
        path9.offset(100,0);

        Path path10=new Path(path9);
        path10.offset(100,0);

        Path path11=new Path(path10);
        path11.offset(100,0);

        Path path12=new Path(path11);
        path12.offset(100,0);

        Path path13=new Path(path12);
        path13.offset(100,0);



        Path path14=new Path(path0);
        path14.offset(0,200);

        Path path15=new Path(path14);
        path15.offset(100,0);

        Path path16=new Path(path15);
        path16.offset(100,0);

        Path path17=new Path(path16);
        path17.offset(100,0);

        Path path18=new Path(path17);
        path18.offset(100,0);

        Path path19=new Path(path18);
        path19.offset(100,0);

        Path path20=new Path(path19);
        path20.offset(100,0);

        paths[0]=path0;
        paths[1]=path1;
        paths[2]=path2;
        paths[3]=path3;
        paths[4]=path4;
        paths[5]=path5;
        paths[6]=path6;
        paths[7]=path7;
        paths[8]=path8;
        paths[9]=path9;
        paths[10]=path10;
        paths[11]=path11;
        paths[12]=path12;
        paths[13]=path13;
        paths[14]=path14;
        paths[15]=path15;
        paths[16]=path16;
        paths[17]=path17;
        paths[18]=path18;
        paths[19]=path19;
        paths[20]=path20;

        bitmap= Bitmap.createBitmap(1200,1900, Bitmap.Config.ARGB_8888);
        canvas=new Canvas(bitmap);
        paint=new Paint();
        paint.setColor(0xffff0268);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
    }


    public  void startUpdateProgress() {
        executorService = Executors.newFixedThreadPool(10);
        int k=0;
        while(k<21) {
            final int kk=k;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    Log.i("BiliPathView","executorService executor runnable");
                    canvas.drawPath(paths[kk],paint);
                }
            });
            k++;
        }
        executorService.shutdown();
    }



    public void drawToScreen(){
        executors.submit(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        Thread.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(executorService.isTerminated()){
                        Canvas canvas1=BiliPathView.this.getHolder().lockCanvas();
                        Matrix matrix=new Matrix();
                        canvas1.drawBitmap(bitmap,matrix,null);
                        BiliPathView.this.getHolder().unlockCanvasAndPost(canvas1);
                        break;
                    }
                    Log.i("TAG","线程没有结束");
                }
            }
        });

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
