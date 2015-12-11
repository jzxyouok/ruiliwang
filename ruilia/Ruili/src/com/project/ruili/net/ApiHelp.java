package com.project.ruili.net;

import com.project.ruili.beans.FashContentTab;


public class ApiHelp {

	/**
	 * 列表主机地址
	 */
	public static final String HOST = "api.rayli.com.cn";

	/**
	 * 返回瑞星主页地址
	 * 
	 * @param page
	 *            页码
	 * @param flag
	 *            状态
	 * @return 主页地址
	 */
	public static String getRuiStarURL(int page, int flag) {

		return "http://api.rayli.com.cn/rlw3.0/getStarInfo.php?signature=c5836614c6ea63dbfe0667fdb02b2601&appSource=android&uid=&page="
				+ page + "&flag=" + flag;
	}

	/**
	 * 返回瑞星详细地址
	 * 
	 * @param page
	 *            页码
	 * @param aid
	 *            对应明星id
	 * @return 瑞星详细图集地址
	 */
	public static String getRuiStarDetailURL(int page, String aid) {

		return "http://api.rayli.com.cn/rlw3.0/getStarAlbumByaid.php?signature=c5836614c6ea63dbfe0667fdb02b2601&appSource=android&page="
				+ page + "&aid=" + aid;
	}

	public static final FashContentTab[] FASH_CONTENT_TAB = {
			new FashContentTab(1, "精选", "W10"),
			new FashContentTab(2, "明星", "W06"),
			new FashContentTab(3, "服饰", "W05"),
			new FashContentTab(4, "美容", "W04"),
			new FashContentTab(5, "街拍", "W08"), };
	public static final String BASE_PATH = "cid=W10&pageIndex=1";
	public static final String FASH_CID = "cid=";
	public static final String FASH_INDEX = "&pageIndex=";
	public static final String FASH_SELECTION_PATH = "http://api.rayli.com.cn/rlw3.0/getArticleList.php?signature=c5836614c6ea63dbfe0667fdb02b2601&appSource=android&";
	public static final String FASH_DETAILS_PATH = "http://api.rayli.com.cn/rlw2.0/getArticle.php?signature=c5836614c6ea63dbfe0667fdb02b2601&appSource=android&id=";
	
	
	/**
	 * 专题url 信息推送url
	 */
	public static String subject="http://api.rayli.com.cn/rlw2.0/getTopicList.php?signature=c5836614c6ea63dbfe0667fdb02b2601&appSource=android";
	public static String sbjItem="http://api.rayli.com.cn/rlw2.0/getTopic.php?signature=c5836614c6ea63dbfe0667fdb02b2601&appSource=android";
	public static String sbjItem_item="http://api.rayli.com.cn/rlw2.0/getArticle.php?signature=c5836614c6ea63dbfe0667fdb02b2601&appSource=android";
	public static String push="http://api.rayli.com.cn/rlw2.0/getPushList.php?signature=c5836614c6ea63dbfe0667fdb02b2601&appSource=android";
	
	
	/**
	 * 美图
	 */
	public static final String PRETTY_PICTURES = "http://api.rayli.com.cn/rlw2.0/getImgList.php?signature=c5836614c6ea63dbfe0667fdb02b2601&appSource=android&pageIndex=";
	/**
	 * 美图详情
	 */
	public static final String PRETTY_SHOW = "http://api.rayli.com.cn/rlw2.0/getImg.php?signature=c5836614c6ea63dbfe0667fdb02b2601&appSource=android&aid=";
	/**
	 * 其他应用下载
	 */
	public static final String APPDOWN = "http://down.rayli.com.cn/android/RayliMaquillage.apk";
}

