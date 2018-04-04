package com.test.caiwen.utile;

import com.elvishew.xlog.XLog;

/**
 * @author Created by MrRight on 2018/3/6.
 */

public class LogUtil {

    public static void v(String tag,String msg){
        XLog.v(tag +"_" + msg);
    }

    public static void d(String tag,String msg){
        XLog.d(tag +"_" + msg);
    }

    public static void i(String tag,String msg){
        XLog.i(tag +"_" + msg);
    }

    public static void w(String tag,String msg){
        XLog.w(tag +"_" + msg);
    }

    public static void e(String tag,String msg){
        XLog.e(tag +"_" + msg);
    }
}
