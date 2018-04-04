package com.test.caiwen;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.LogUtils;
import com.elvishew.xlog.XLog;
import com.elvishew.xlog.printer.AndroidPrinter;
import com.elvishew.xlog.printer.ConsolePrinter;
import com.elvishew.xlog.printer.Printer;
import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;
import com.test.caiwen.utile.LogUtil;

/**
 * @author Created by MrRight on 2018/3/6.
 */

public class App extends Application{
    private static final String TAG="Application";
    private static boolean isNeedRelaunch = false;
    private int count = 0;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        initHotFix();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initXlog();
        registerActivityLifecycleCallback();
        SophixManager.getInstance().queryAndLoadNewPatch();
    }

    private void initXlog() {
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(BuildConfig.DEBUG ? LogLevel.ALL             // 指定日志级别，低于该级别的日志将不会被打印，默认为 LogLevel.ALL
                        : LogLevel.NONE)
                .tag("MY_TAG")                                         // 指定 TAG，默认为 "X-LOG"
                .b()                                                   // 允许打印日志边框，默认禁止
                .build();

        Printer androidPrinter = new AndroidPrinter();             // 通过 android.util.Log 打印日志的打印器
        Printer consolePrinter = new ConsolePrinter();             // 通过 System.out 打印日志到控制台的打印器

        XLog.init(                                                 // 初始化 XLog
                config,                                                // 指定日志配置，如果不指定，会默认使用 new LogConfiguration.Builder().build()
                androidPrinter,                                        // 添加任意多的打印器。如果没有添加任何打印器，会默认使用 AndroidPrinter(Android)/ConsolePrinter(java)
                consolePrinter);
    }


    private void initHotFix(){
        String appVersion;
        try {
            appVersion = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (Exception e) {
            appVersion = "1.1.0";
        }
        // initialize必须放在attachBaseContext最前面，初始化代码直接写在Application类里面，切勿封装到其他类。
        SophixManager.getInstance().setContext(this)
                .setAppVersion(appVersion)
                .setAesKey(null)
                .setEnableDebug(BuildConfig.DEBUG)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            Log.w(TAG,"补丁加载成功");
                            // 表明补丁加载成功
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            isNeedRelaunch = true;
                            Log.w(TAG,"补丁生效需要重启");
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            // 建议: 用户可以监听进入后台事件, 然后调用killProcessSafely自杀，以此加快应用补丁，详见1.3.2.3
                        } else {
                            // 其它错误信息, 查看PatchStatus类说明
                            Log.i(TAG, code + "----" + info);
                        }
                    }
                }).initialize();
    }


    private void registerActivityLifecycleCallback() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityStopped(Activity activity) {
                count--;
                if (count == 0) {
                    // 进入后台
                    LogUtil.i(TAG, "APP进入后台运行");
                    if (isNeedRelaunch) {
                        isNeedRelaunch = false;
                        SophixManager.getInstance().killProcessSafely();
                    }
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                count++;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }
        });
    }
}
