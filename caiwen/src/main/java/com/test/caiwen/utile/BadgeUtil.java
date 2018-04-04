package com.test.caiwen.utile;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 应用启动图标未读消息数显示 工具类  (效果如：QQ、微信、未读短信 等应用图标)<br/>
 * 依赖于第三方手机厂商(如：小米、三星)的Launcher定制、原生系统不支持该特性<br/>
 * 该工具类 支持的设备有 小米、三星、索尼【其中小米、三星亲测有效、索尼未验证】
 * @author ice_zhengbin@163.com
 *
 */
public class BadgeUtil {
    private final static String TAG="BadgeUtil";
    private int MQTT_IM_NOTIFICATION_ID=1007;

    private static final String SYSTEM_XIAOMI="XIAOMI";
    private static final String SYSTEM_SAMSUNG="SAMSUNG";
    private static final String SYSTEM_HUAWEI_HONOR="HONOR";
    private static final String SYSTEM_HUAWEI="HUAWEI";
    private static final String SYSTEM_NOVA="NOVA";
    private static final String SYSTEM_SONY="SONY";
    private static final String SYSTEM_VIVO="VIVO";
    private static final String SYSTEM_OPPO="OPPO";
    private static final String SYSTEM_LG="LG";
    private static final String SYSTEM_ZUK="ZUK";
    private static final String SYSTEM_HTC="HTC";

    private static final int NOTIFI_ID=16987;


    /**
     * 设置角标
     */
    public static void setBadgeCount(Context context, int count,Notification notification){
        String OSName=android.os.Build.BRAND.trim().toUpperCase();
        Log.i(TAG,OSName);
        if(OSName.equals(SYSTEM_XIAOMI)){
            sendToXiaoMi(context,notification,count);
        }else if(OSName.equals(SYSTEM_SAMSUNG)){
            sendToSamsumg(context,count,notification);
        }else if(OSName.equals(SYSTEM_HUAWEI_HONOR)){
            setBadgeNumToHuawei(context,count,notification);
        }else if(OSName.equals(SYSTEM_HUAWEI)){
            setBadgeNumToHuawei(context,count,notification);
        }else if(OSName.equals(SYSTEM_NOVA)){
            setBadgeOfNOVA(context,notification,NOTIFI_ID,count);
        }else if(OSName.equals(SYSTEM_SONY)){
            sendToSony(context,count);
        }else if(OSName.equals(SYSTEM_VIVO)){
            sendToVivo(context,count,notification);
        }else if(OSName.equals(SYSTEM_OPPO)){
            sendToOppo(context,count,notification);
        }else if(OSName.equals(SYSTEM_LG)){
            sendToSamsumg(context,count,notification);
        }else if(OSName.equals(SYSTEM_ZUK)){
            setBadgeOfZUK(context,notification,NOTIFI_ID,count);
        }else if(OSName.equals(SYSTEM_HTC)){
            setBadgeOfHTC(context,notification,NOTIFI_ID,count);
        }else{
            setBadgeOfDefault(context,notification,NOTIFI_ID,count);
        }
    }



    /*******************************************测试可用***************************************************/






    /**
     * 向华为手机SDK发送未读消息的请求
     * @param num num
     */
    private static void setBadgeNumToHuawei(Context context, int num,Notification notification){
        try{
            Bundle localBundle = new Bundle();
            localBundle.putString("package", context.getPackageName());
            localBundle.putString("class", getLauncherClassName(context));
            localBundle.putInt("badgenumber", num);
            context.getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, localBundle);
            NotificationManager notifyMgr = (NotificationManager)(context.getSystemService(Context.NOTIFICATION_SERVICE));
            if(num!=0)notifyMgr.notify(NOTIFI_ID, notification);else notifyMgr.cancel(NOTIFI_ID);
        }catch(Exception e){
            e.printStackTrace();
            Log.e("HUAWEI" + " Badge error", "set Badge failed");
        }
    }


    /**
     * 向三星手机发送未读消息数广播
     * 测试有效果，但是不能进行更新
     * @param num 消息数
     */
    private static void sendToSamsumg(Context context, int num,final Notification notification){
        // 获取你当前的应用
        String launcherClassName = getLauncherClassName(context);
        reguesterSamsung(context);
        if (launcherClassName == null) {
            return;
        }
        try {
            Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
            intent.putExtra("badge_count", num);
            intent.putExtra("badge_count_package_name", context.getPackageName());
            intent.putExtra("badge_count_class_name", launcherClassName);
            context.sendBroadcast(intent);
            NotificationManager notifyMgr = (NotificationManager)(context.getSystemService(Context.NOTIFICATION_SERVICE));
            if(num!=0)notifyMgr.notify(NOTIFI_ID, notification);else notifyMgr.cancel(NOTIFI_ID);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("SAMSUNG" + " Badge error", "set Badge failed");
        }
    }




    /**
     * 向小米手机发送未读消息数广播
     * @param num 未读消息数
     */
    private static void sendToXiaoMi(final Context context, final Notification notification,final int num) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Field field = notification.getClass().getDeclaredField("extraNotification");
                    Object extraNotification = field.get(notification);
                    Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", int.class);
                    method.invoke(extraNotification, num);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Xiaomi" + " Badge error", "set Badge failed");
                }
                NotificationManager notifyMgr = (NotificationManager)(context.getSystemService(Context.NOTIFICATION_SERVICE));
                if(num!=0)notifyMgr.notify(NOTIFI_ID, notification);else notifyMgr.cancel(NOTIFI_ID);
            }
        },550);
    }





    /*******************************************测试可用***************************************************/












    /**
     * 向索尼手机发送未读消息数广播<br/>
     * 据说：需添加权限：<uses-permission android:name="com.sonyericsson.home.permission.BROADCAST_BADGE" /> [未验证]
     * @param count
     */
    private static void sendToSony(Context context, int count){
        String launcherClassName = getLauncherClassName(context);
        if (launcherClassName == null) {
            return;
        }
        boolean isShow = true;
        if (count == 0) {
          isShow = false;
        }
        Intent localIntent = new Intent();
        localIntent.setAction("com.sonyericsson.home.action.UPDATE_BADGE");
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.SHOW_MESSAGE",isShow);//是否显示
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.ACTIVITY_NAME",launcherClassName );//启动页
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.MESSAGE", String.valueOf(count));//数字
        localIntent.putExtra("com.sonyericsson.home.intent.extra.badge.PACKAGE_NAME", context.getPackageName());//包名
        context.sendBroadcast(localIntent);
    }




    /**
     * 向OPPO手机发送未读消息的通知
     * @param context 上下文
     * @param num 未读消息数
     */
    private static void sendToOppo(Context context, int num,Notification notification){
        try {
            if (num == 0) {num = -1;}
            Intent intent = new Intent("com.oppo.unsettledevent");
            intent.putExtra("pakeageName", context.getPackageName());
            intent.putExtra("number", num);
            intent.putExtra("upgradeNumber", num);
            if (canResolveBroadcast(context, intent)) {
                context.sendBroadcast(intent);
            } else {
                try {
                    Bundle extras = new Bundle();
                    extras.putInt("app_badge_count", num);
                    context.getContentResolver().call(Uri.parse("content://com.android.badge/badge"), "setAppBadgeCount", null, extras);
                    NotificationManager notifyMgr = (NotificationManager)(context.getSystemService(Context.NOTIFICATION_SERVICE));
                    if(num!=-1)notifyMgr.notify(NOTIFI_ID, notification);else notifyMgr.cancel(NOTIFI_ID);
                } catch (Throwable th) {
                    Log.e("OPPO" + " Badge error", "unable to resolve intent: " + intent.toString());
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("OPPO" + " Badge error", "set Badge failed");
        }
    }


    /**
     * 向魅族手机发送未读消息的请求
     * @param num 未读消息数
     */
    private static void sendToMizu(Context context, int num){

    }


    /**
     * 向步步高手机发送未读消息
     * @param context 上下文
     */
    private static void sendToVivo(Context context, int num,Notification notification){
        try {
            Intent localIntent = new Intent("launcher.action.CHANGE_APPLICATION_NOTIFICATION_NUM");
            localIntent.putExtra("packageName", context.getPackageName());
            localIntent.putExtra("className", getLauncherClassName(context));
            localIntent.putExtra("notificationNum", num);
            context.sendBroadcast(localIntent);
            NotificationManager notifyMgr = (NotificationManager)(context.getSystemService(Context.NOTIFICATION_SERVICE));
            if(num!=0)notifyMgr.notify(NOTIFI_ID, notification);else notifyMgr.cancel(NOTIFI_ID);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("VIVO" + " Badge error", "set Badge failed");
        }
    }


    /**
     * 向一加手机发送未读消息
     * @param num 未读消息数
     */
    private static void sendToOnePlus(Context context, int num){

    }


    /**
     * ZUK
      * @param context 上下文
     * @param notification 通知
     * @param NOTIFI_ID id
     * @param num 未读消息数
     */
    private static void setBadgeOfZUK(Context context,Notification notification,int NOTIFI_ID, int num){
        final Uri CONTENT_URI = Uri.parse("content://com.android.badge/badge");
        try {
            Bundle extra = new Bundle();
            extra.putInt("app_badge_count", num);
            context.getContentResolver().call(CONTENT_URI, "setAppBadgeCount", null, extra);
            NotificationManager notifyMgr = (NotificationManager)(context.getSystemService(Context.NOTIFICATION_SERVICE));
            if(num!=0)notifyMgr.notify(NOTIFI_ID, notification);else notifyMgr.cancel(NOTIFI_ID);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("ZUK" + " Badge error", "set Badge failed");
        }

    }



    /**
     * HTC
     */
    private static void setBadgeOfHTC(Context context,Notification notification,int NOTIFI_ID,int num){
        try {
            Intent intent1 = new Intent("com.htc.launcher.action.SET_NOTIFICATION");
            intent1.putExtra("com.htc.launcher.extra.COMPONENT", context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getComponent().flattenToShortString());
            intent1.putExtra("com.htc.launcher.extra.COUNT", num);
            Intent intent = new Intent("com.htc.launcher.action.UPDATE_SHORTCUT");
            intent.putExtra("packagename", context.getPackageName());
            intent.putExtra("count", num);
            if (canResolveBroadcast(context, intent1) || canResolveBroadcast(context, intent)) {
                context.sendBroadcast(intent1);
                context.sendBroadcast(intent);
            } else {
                Log.e("HTC" + " Badge error", "unable to resolve intent: " + intent.toString());
            }
            NotificationManager notifyMgr = (NotificationManager)(context.getSystemService(Context.NOTIFICATION_SERVICE));
            if(num!=0)notifyMgr.notify(NOTIFI_ID, notification);else notifyMgr.cancel(NOTIFI_ID);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("HTC" + " Badge error", "set Badge failed");
        }
    }


    /**
     * NOVA
     */
    private static void setBadgeOfNOVA(Context context,Notification notification,int NOTIFI_ID,int num){
        try{
            ContentValues contentValues = new ContentValues();
            contentValues.put("tag", context.getPackageName()+ "/" +getLauncherClassName(context));
            contentValues.put("count", num);
            context.getContentResolver().insert(Uri.parse("content://com.teslacoilsw.notifier/unread_count"), contentValues);

            NotificationManager notifyMgr = (NotificationManager)(context.getSystemService(Context.NOTIFICATION_SERVICE));
            if(num!=0)notifyMgr.notify(NOTIFI_ID, notification);else notifyMgr.cancel(NOTIFI_ID);


        }catch (Exception e){
            e.printStackTrace();
            Log.e("NOVA" + " Badge error", "set Badge failed");
        }
    }



    /**
     * 其他
     */
    private static void setBadgeOfDefault(Context context,Notification notification,int NOTIFI_ID,int num){
        try {
            Intent intent = new Intent("android.intent.action.BADGE_COUNT_UPDATE");
            intent.putExtra("badge_count", num);
            intent.putExtra("badge_count_package_name", context.getPackageName());
            intent.putExtra("badge_count_class_name", getLauncherClassName(context));
            if (canResolveBroadcast(context, intent)) {
                context.sendBroadcast(intent);
            } else {
                Log.e("Default" + " Badge error", "unable to resolve intent: " + intent.toString());
            }
            NotificationManager notifyMgr = (NotificationManager)(context.getSystemService(Context.NOTIFICATION_SERVICE));
            if(num!=0)notifyMgr.notify(NOTIFI_ID, notification);else notifyMgr.cancel(NOTIFI_ID);
        }catch (Exception e){
            e.printStackTrace();
            Log.e("Default" + " Badge error", "set Badge failed");
        }

    }


    /**
     * 重置、清除Badge未读显示数<br/>
     * @param context
     */
    public static void resetBadgeCount(Context context,Notification notification) {
        setBadgeCount(context, 0,notification);
    }



    /**
     * 获取APP的启动Activity
     * @param context The context of the application package.
     * @return launcher activity name of this application. From the
     *         "android:name" attribute.
     */
    private static String getLauncherClassName(Context context) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setPackage(context.getPackageName());
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        ResolveInfo info = packageManager
                .resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if (info == null) {
            info = packageManager.resolveActivity(intent, 0);
        }
        String launcherClassName=info.activityInfo.name;
        Log.i(TAG,"launcherClassName:"+launcherClassName);
        return launcherClassName;
    }

    //广播
    public static boolean canResolveBroadcast(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> receivers = packageManager.queryBroadcastReceivers(intent, 0);
        return receivers != null && receivers.size() > 0;
    }



    //检测是否注册成功  会打印出来
    private static void testReguster(Context context) {

        Uri uri = Uri.parse("content://com.sec.badge/apps");
        Cursor c = context.getContentResolver().query(uri, null, null, null, null);
        if (c == null) {return;}
        try {
            if (!c.moveToFirst()) {return;}
            c.moveToPosition(-1);
            while (c.moveToNext()) {
                String pkg = c.getString(1);
                String clazz = c.getString(2);
                int badgeCount = c.getInt(3);
                Log.d("BadgeTest", "package: " + pkg + ", class: " + clazz + ", count: "+badgeCount);
            }
        } finally {
            c.close();
        }
    }


    /**
     * 注册三星权限   这个方法在程序第一次进入的时候要调用一下
     *
     * @param context 上下文
     */
    private static void reguesterSamsung(Context context) {
        ContentValues cv = new ContentValues();
        cv.put("package", context.getPackageName());
        //写入加载页的全路径名称 如：com.xxx.xx.ui.activity.SplashActivity
        cv.put("class", getLauncherClassName(context));
        // integer count you want to display
        cv.put("badgecount", 0);
        // Execute insert
        context.getContentResolver().insert(Uri.parse("content://com.sec.badge/apps"), cv);
        testReguster(context);
    }

}