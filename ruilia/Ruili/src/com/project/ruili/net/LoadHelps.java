package com.project.ruili.net;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.project.ruili.R;
import com.project.ruili.activitys.UpLoadActivitys;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;

public class LoadHelps {

	private static final int NEW_MSG = 0;
	private static final int REQUESTCODE = 111;
	/**
	 * 下载完成监听
	 * @author Administrator
	 *
	 */
	public interface DownLoadFinishiListen{
		public void downLoadFinish();
	}
	
	public static void load(final Context context,
			final DownLoadFinishiListen dlisListen,final String loadUrl) {
		new Thread(new Runnable() {
			public void run() {
				try {
					URL url = new URL(loadUrl);
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();
					connection.setReadTimeout(3000);
					connection.setRequestMethod("GET");
					connection.connect();
					int reCode = connection.getResponseCode();
					if (reCode == 200) {
						System.out.println("链接服务器ok");
						// 获取文件长度
						long fileLen = connection.getContentLength();
						int curPro = 0;
						InputStream ips = connection.getInputStream();
						// 获得文件绝对路径
						String filePath=UpLoadDatas.getFilePath(loadUrl);
						FileOutputStream fos = new FileOutputStream(filePath);
						// 保存文件到本地
						int len = 0;
						byte[] bur = new byte[1024 * 8];
						while ((len = ips.read(bur)) > 0) {
							fos.write(bur, 0, len);
							curPro += len;
							// 发送通知到系统,显示进度
							sendNotification(context, fileLen, curPro);
						}
						Looper.prepare();
						dlisListen.downLoadFinish();
						Looper.loop();
						ips.close();
						fos.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	// 发送进度通知到系统
	private static void sendNotification(Context context, long fileLen, int len) {
		// 1.创建通知对象
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context);
		// 设置通知的图标,最基本的信息，不可缺少
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentTitle("文件下载");
		builder.setContentText("正在下载中...");
		builder.setAutoCancel(true);

		if (fileLen == len) {
			builder.setContentText("文件下载完成,点击安装");
		// 创建PendingIntent，PendingIntent用来包装Intent对象，
			//以便在特定的时候才去将Intent发送出去，比如用户点击了通知的时候，
			//PendingIntent包装的Intent才会被发送到系统中
			Intent intent = new Intent(context, UpLoadActivitys.class);
			PendingIntent pendingIntent = PendingIntent.getActivity(context,
					REQUESTCODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

			builder.setContentIntent(pendingIntent);
		}
		builder.setProgress((int) fileLen, len, false);

		// 2.获取系统的状态栏通知管理器
		NotificationManager manager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// 3.发送状态栏通知
		manager.notify(NEW_MSG, builder.build());
	}
}
