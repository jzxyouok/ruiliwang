package com.project.ruili.activitys;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.PrintStream;

import com.project.ruili.R;
import com.project.ruili.net.ApiHelp;
import com.project.ruili.net.LoadHelps;
import com.project.ruili.net.LoadHelps.DownLoadFinishiListen;
import com.project.ruili.utils.DataCleanManager;
import com.project.ruili.utils.ToasUtils;

public class PersonSettingActivity extends Activity implements
		View.OnClickListener, OnClickListener, DownLoadFinishiListen {
	private ImageView button;
	private TextView cacheView;
	boolean isPush = false;

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_person_setting);
		try {
			String str = DataCleanManager.getCacheSize(getCacheDir());
			cacheView = ((TextView) findViewById(R.id.activity_person_setting_cleancache_tv));
			cacheView.setText(str);
			// 监听应用下载点击
			findViewById(R.id.set_dloadapp).setOnClickListener(this);
			button = ((ImageView) findViewById(R.id.activity_person_setting_pushSwith));
			button.setImageResource(R.drawable.switch_off);
			button.setOnClickListener(this);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/***
	 * 这里的Activity 需要更换 在集成的时候
	 * 
	 * @param v
	 */
	public void getBack(View v) {
		Intent intent = new Intent();
		intent.setClass(PersonSettingActivity.this, HomeActivity.class);
		startActivity(intent);
		PersonSettingActivity.this.finish();
	}

	public void copyRight(View v) {
		startActivity(new Intent(this, CopyRightActivity.class));
	}

	public void updata(View v) {
		ToasUtils.showLToast(getApplicationContext(), "已经是最新版");
	}

	public void suggestion(View v) {
		startActivity(new Intent(this, SuggestinActivity.class));
	}

	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.activity_person_setting_pushSwith:
			if (!isPush) {
				button.setImageResource(R.drawable.switch_off);
				isPush = true;

			} else {
				button.setImageResource(R.drawable.switch_on);
				isPush = false;
			}
			break;

		case R.id.set_dloadapp:// 点击应用下载
			// 弹出diaolg
			showDLDialog();
			break;
		}
	}

	//弹出下载对话框
	private void showDLDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this); // 先得到构造器
		builder.setTitle("是否下载应用?"); // 设置标题
		builder.setMessage("建议在wifi环境下载"); // 设置内容
		builder.setPositiveButton("下载", this);
		builder.setNegativeButton("取消", this);
		builder.show();
	}

	public void cleanCache(View v) {
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		localBuilder.setMessage("确认清除缓存吗?");
		localBuilder.setTitle("");
		localBuilder.setNegativeButton("确认",
				new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymousDialogInterface,
							int paramAnonymousInt) {
						paramAnonymousDialogInterface.dismiss();
						DataCleanManager
								.cleanInternalCache(getApplicationContext());
						try {
							String str = DataCleanManager
									.getCacheSize(getApplicationContext()
											.getCacheDir());
							System.out.println("---------------size1 =" + str);
							cacheView.setText(str);
							ToasUtils.showLToast(getApplicationContext(),
									"缓存已清除");
							return;
						} catch (Exception localException) {
							localException.printStackTrace();
						}
					}
				});
		localBuilder.setPositiveButton("取消",
				new DialogInterface.OnClickListener() {
					public void onClick(
							DialogInterface paramAnonymousDialogInterface,
							int paramAnonymousInt) {
						paramAnonymousDialogInterface.dismiss();
					}
				});
		localBuilder.create().show();
	}

	//dialog按钮监听
	@Override
	public void onClick(DialogInterface dialog, int which) {
		if (which==Dialog.BUTTON_POSITIVE) {//点击下载
			LoadHelps.load(this, this,ApiHelp.APPDOWN);
		}else if (which==Dialog.BUTTON_NEGATIVE) {//点击取消
		}
	}

	//应用下载完成监听
	@Override
	public void downLoadFinish() {
		//弹出安装对话框
		showInstallDialog();
	}

	//现在安装对话框
	private void showInstallDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this); // 先得到构造器
		builder.setTitle("下载完成"); // 设置标题
		builder.setMessage("应用下载完成,是否安装?"); // 设置内容
		builder.setPositiveButton("安装", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//调整到安装界面
				startActivity(new Intent(PersonSettingActivity.this, UpLoadActivitys.class));
			}
		});
		builder.setNegativeButton("稍后", this);
		builder.show();
	}

}
