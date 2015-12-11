package com.project.ruili.db;

import java.util.ArrayList;
import java.util.Iterator;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 构建单例
 * 
 * @author Administrator
 * 
 */
public class OpenDBhelp extends SQLiteOpenHelper {

	private static OpenDBhelp openDBhelp = null;
	private final static String DBNAME = "datas.db";
	private final static int DBVERSION = 1;
	private static SQLiteDatabase db;

	private OpenDBhelp(Context context) {
		super(context, DBNAME, null, DBVERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建表
		db.execSQL(DBmessage.CREAT_TABLE);
		System.out.println("创建表成功");
	}

	/**
	 * 升级数据库
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	/**
	 * 获取dbhelp
	 * 
	 * @param context
	 * @return
	 */
	public static OpenDBhelp getDBhelp(Context context) {
		if (openDBhelp == null) {
			synchronized (OpenDBhelp.class) {
				if (openDBhelp == null) {
					openDBhelp = new OpenDBhelp(context);
				}
			}
		}
		return openDBhelp;
	}

	/**
	 * 
	 * @param table
	 *            需要查询的表名和
	 * @param imageUrl
	 *            需要查询的 imageUrl,flag,查询所有 填null
	 * @return
	 */
	public ArrayList<DBbean> getDBDatas(String table, String detailUrl,
			String flag) {
		db = openDBhelp.getWritableDatabase();
		Cursor cursor = null;
		if (detailUrl == null) {
			if (flag == null) {
				cursor = db.query(table, null, null, null, null, null, null);
			} else {
				cursor = db.query(table, null, " flag=? ", new String[] { ""
						+ flag }, null, null, null);
			}
		} else {
			if (flag == null) {
				cursor = db.query(table, null, " detailUrl=? ",
						new String[] { "" + detailUrl }, null, null, null);
			} else {
				cursor = db.query(table, null, " detailUrl=? and flag=?",
						new String[] { "" + detailUrl, flag }, null, null, null);
			}
		}

		if (cursor == null || cursor.getCount() == 0) {
			cursor.close();
			db.close();
			return null;
		}
		ArrayList<DBbean> dBbeans = new ArrayList<DBbean>();
		while (cursor.moveToNext()) {
			DBbean bean = new DBbean();
			bean.tittle = cursor.getString(cursor
					.getColumnIndex(DBmessage.TITTLE));
			bean.summary = cursor.getString(cursor
					.getColumnIndex(DBmessage.SUMMARY));
			bean.imageUrl = cursor.getString(cursor
					.getColumnIndex(DBmessage.IMAGEURL));
			bean.detailUrl = cursor.getString(cursor
					.getColumnIndex(DBmessage.DETAILURL));
			dBbeans.add(bean);
		}
		cursor.close();
		db.close();
		return dBbeans;
	}

	// 插入数据方法
	public void insert2DB(String table, ContentValues values) {
		db = openDBhelp.getWritableDatabase();
		db.insert(table, null, values);
		db.close();
	}

	/**
	 * 根据flag跟 url判断数据库是否存在此记录
	 * 
	 * @param url
	 *            图片的地址
	 * @param flag
	 *            收藏的标记
	 * @return
	 */
	public boolean isExistDatas(String detailUrl){
		ArrayList<DBbean> list = getDBDatas(DBmessage.TNAME, detailUrl, null);
		if (list == null) {
			return false;
		}
		return true;
	}

	/**
	 * 删除数据
	 * 
	 * @param table
	 * @param url
	 *            图片url
	 * @param flag
	 *            标记类型
	 * @return 是否删除成功
	 */
	public boolean delDatas(String detailUrl) {
		db = openDBhelp.getWritableDatabase();
		int num = db.delete(DBmessage.TNAME, "detailUrl=?",
				new String[] { detailUrl });
		if (num > 0) {
			db.close();
			return true;
		}
		db.close();
		return false;
	}

	/**
	 * 资讯添加数据
	 * 
	 * @param url
	 * @param tittle
	 * @param summary
	 * @param flag
	 *            当前标记类型 2=美图 3=专题 1= 资讯
	 */
	public void inserMesData(String detailUrl,String imageUrl, String tittle, String summary, int flag) {
		boolean isExist = isExistDatas(detailUrl);
		if (!isExist) {
			ContentValues values = new ContentValues();
			values.put(DBmessage.DETAILURL, detailUrl);
			values.put(DBmessage.IMAGEURL, imageUrl);
			values.put(DBmessage.TITTLE, tittle);
			values.put(DBmessage.SUMMARY, summary);
			values.put(DBmessage.FLAG, flag);
			insert2DB(DBmessage.TNAME, values);
		}
	}

	/**
	 * 美图跟专题添加数据
	 * 
	 * @param imageUrl
	 * @param tittle
	 * @param flag
	 *            当前标记类型 2=美图 3=专题 1= 资讯
	 */
	public void inserMesData(String detailUrl,String imageUrl, String tittle, int flag) {
		boolean isExist = isExistDatas(detailUrl);
		if (!isExist) {
			ContentValues values = new ContentValues();
			values.put(DBmessage.DETAILURL, detailUrl);
			values.put(DBmessage.IMAGEURL, imageUrl);
			values.put(DBmessage.TITTLE, tittle);
			values.put(DBmessage.FLAG, flag);
			insert2DB(DBmessage.TNAME, values);
		}
	}
}
