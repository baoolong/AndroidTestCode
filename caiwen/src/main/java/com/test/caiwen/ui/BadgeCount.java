package com.test.caiwen.ui;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.test.caiwen.utile.AnimatorTest;
import com.test.caiwen.R;
import com.test.caiwen.utile.BadgeUtil;

import java.util.Random;

/**
 * @author  Created by MrRight on 2018/1/16.
 */

public class BadgeCount extends Activity implements View.OnClickListener{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badgecount);
        initView();
    }

    void initView(){
        Button setBadgeButton=findViewById(R.id.setBadge);
        Button clearBadgeButton=findViewById(R.id.clearBadge);
        setBadgeButton.setOnClickListener(this);
        clearBadgeButton.setOnClickListener(this);
        AnimatorTest animatorTest=new AnimatorTest();
        animatorTest.xmlAnimatorTest(setBadgeButton,this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.setBadge:
                int count =new Random().nextInt(99);
                Notification n=showNotifycation(count);
                BadgeUtil.setBadgeCount(this,count,n);
                moveTaskToBack(true);
                break;
            case R.id.clearBadge:
                Notification resetNotify=showNotifycation(0);
                BadgeUtil.resetBadgeCount(this,resetNotify);
                moveTaskToBack(true);
                break;
            default:
        }
    }





    private  Notification showNotifycation(int i){
        String content = 2 + "个联系人发了" + i + "条消息";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setTicker("收到" + i + "条消息");
        builder.setWhen(System.currentTimeMillis());
        Intent intent = new Intent(getBaseContext(), Main.class);

        intent.setAction(getApplicationContext().getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pi = PendingIntent.getActivity(getBaseContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pi);
        builder.setContentTitle(getResources().getText(R.string.app_name));
        builder.setContentText(content);

        final Notification n = builder.build();
        int defaults = Notification.DEFAULT_LIGHTS;

        defaults |= Notification.DEFAULT_SOUND;

        defaults |= Notification.DEFAULT_VIBRATE;


        n.defaults = defaults;
        n.flags = Notification.FLAG_SHOW_LIGHTS | Notification.FLAG_AUTO_CANCEL;
        return n;
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
