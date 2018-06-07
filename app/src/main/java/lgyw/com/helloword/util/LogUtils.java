package lgyw.com.helloword.util;

import com.elvishew.xlog.XLog;


public class LogUtils {


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
