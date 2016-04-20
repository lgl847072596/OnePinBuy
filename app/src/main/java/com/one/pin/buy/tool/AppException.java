package com.one.pin.buy.tool;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Looper;
import android.view.View;

import com.one.pin.buy.extension.AppAlertDialog;
import com.one.pin.buy.listener.CallBackInterface;


/**
 * 
 * @ClassName: com.example.exceptiondemo.AppException
 * @Description: 应用程序异常类：用于捕获异常
 * @author zhaokaiqiang
 * @date 2014-11-2 下午10:06:49
 * 
 */

public class AppException extends Exception implements UncaughtExceptionHandler {

	private static final long serialVersionUID = -6262909398048670705L;

	private String message;

	private UncaughtExceptionHandler mDefaultHandler;

	private AppException() {
		super();
		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}

	public AppException(String message, Exception excp) {
		super(message, excp);
		this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 获取APP异常崩溃处理对象
	 * 
	 * @param context
	 * @return
	 */
	public static AppException getAppExceptionHandler() {
		return new AppException();
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {

		if (!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
		}

	}
	/**
	 * 自定义异常处理
	 * 
	 * @param ex
	 * @return true:处理了该异常信息;否则返回false
	 */
	private Timer timer=new Timer();
	private AppAlertDialog dialog;
	private boolean handleException(Throwable ex) {
		if (ex == null) {
			return false;
			
		}
		LogTool.e("应用崩溃",""+getStackTrace(ex));

		final Activity activity = AFWAppManager.getAppManager().currentActivity();

		if (activity == null) {
			return false;
		}
		
		new Thread() {
			@Override
			public void run() {
				timer.schedule(new TimerTask() {
					
					@Override
					public void run() {
						if(dialog!=null){
							dialog.dismiss();
						}
						AFWAppManager.getAppManager().AppExit(activity, false);
					}
				}, 4000);
				
				Looper.prepare();
				dialog = new AppAlertDialog(activity, "程序异常，攻城狮正在赶来...");
				dialog.setOnDismissListener(new OnDismissListener() {

					@Override
					public void onDismiss(DialogInterface dialog) {
					}
				});
				dialog.oneButton("原谅他了", new CallBackInterface<View>() {
					@Override
					public void callBack(View t) {
						dialog.dismiss();
					}
				});
				
				
				Looper.loop();
			}
		}.start();

		return true;
	}
	private String getStackTrace(Throwable th) {

		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		Throwable cause = th;
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		final String stacktraceAsString = result.toString();
		printWriter.close();

		return stacktraceAsString;
	}

}