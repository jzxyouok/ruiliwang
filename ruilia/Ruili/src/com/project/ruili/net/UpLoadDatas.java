package com.project.ruili.net;

import java.io.File;

import android.os.Environment;

public class UpLoadDatas {

	public static int upCode;
	public static String up_summary;
	public static String filePath;

	public static String getFilePath(String url) {
		// 获得文件名
		String fileName = url.substring(
				url.lastIndexOf("/") + 1,
				url.length());
		// 创建文件夹
		File file = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/ruili");
		if (!file.exists()) {
			file.mkdirs();
			System.out.println("文件夹创造..");
		}
		File f=new File(file, fileName);
		return f.getAbsolutePath();
	}
}
