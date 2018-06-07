package lgyw.com.helloword;

import android.app.Application;
import android.content.Context;

import com.elvishew.xlog.LogConfiguration;
import com.elvishew.xlog.LogLevel;
import com.elvishew.xlog.XLog;
import com.tencent.bugly.crashreport.CrashReport;

import lgyw.com.helloword.util.AppUtil;

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initBugly();
        initXLog();
    }


    private void initBugly(){
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = AppUtil.getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        CrashReport.initCrashReport(getApplicationContext(), "4f3cda932a", BuildConfig.DEBUG);
    }


    private void initXLog() {
        LogConfiguration config = new LogConfiguration.Builder()
                .logLevel(BuildConfig.DEBUG ? LogLevel.ALL         // 指定日志级别，低于该级别的日志将不会被打印，默认�? LogLevel.ALL
                        : LogLevel.NONE)
                .tag("GMYJT")                                      // 指定 TAG，默认为 "X-LOG"
                .b()                                               // 允许打印日志边框，默认禁止
                .build();

        XLog.init(config);
    }
}
