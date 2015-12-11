package com.project.ruili.db;

import android.provider.BaseColumns;

public class DBmessage {

	/**
	 * 代表收藏资讯flag
	 */
	public static final String MESSAGE=1+"";
	/**
	 * 代表收藏美图
	 */
	public static final String PICTURE=2+"";
	/**
	 * 代表收藏专题flag
	 */
	public static final String SUBJECT=3+"";
	/**
	 * 表名
	 */
	public static final String TNAME = "collection";

	/**
	 *图片路径字段名
	 */
	public static final String IMAGEURL = "imageUrl";
	/**
	 *图片路径字段名
	 */
	public static final String DETAILURL = "detailUrl";
	/**
	 * tittle字段名
	 */
	public static final String TITTLE = "tittle";
	/**
	 * summary字段名
	 */
	public static final String SUMMARY = "summary";


	/**
	 * flag=1 资讯  flag =2 美图 flag=3 专题
	 */
	public static final String FLAG = "flag";
	
	/**
	 * 建表语句
	 */
	public static final String CREAT_TABLE = "create table " + TNAME + " ("
			+ BaseColumns._ID + " integer primary key autoincrement, " + IMAGEURL
			+ " text, " + DETAILURL + " text, "+ SUMMARY + " text, " + TITTLE + " text, " 
			+FLAG+" integer)";

}
