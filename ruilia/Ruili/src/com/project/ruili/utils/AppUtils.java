package com.project.ruili.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class AppUtils {

	/**
	 * 获取应用的版本名字
	 * @param context
	 * @return null为异常
	 */
	public static String getAppVersionName(Context context){
		try {
		 PackageManager manager = context.getPackageManager();
		 PackageInfo info = null;
		 info = manager.getPackageInfo(context.getPackageName(), 0);
		 String version = info.versionName;
			return version;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取应用的
	 * @param context
	 * @return -1 则异常
	 */
	public static int getAppVersionCode(Context context){
		try {
			PackageManager manager = context.getPackageManager();
			PackageInfo info = null;
			info = manager.getPackageInfo(context.getPackageName(), 0);
			int version = info.versionCode;
			return version;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
