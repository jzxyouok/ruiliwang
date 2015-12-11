package com.project.ruili.activitys;

import com.project.ruili.net.ApiHelp;
import com.project.ruili.net.UpLoadDatas;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class UpLoadActivitys extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.setDataAndType(Uri.parse("file://"+UpLoadDatas.getFilePath(ApiHelp.APPDOWN)),
				"application/vnd.android.package-archive");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.startActivity(intent);
		finish();
	}
}
