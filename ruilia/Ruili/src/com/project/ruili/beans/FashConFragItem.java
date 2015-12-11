package com.project.ruili.beans;

public class FashConFragItem {
	public String logo;
	public int aid;
	public String title;
	public String pubtime;
	public String source;
	public String intro;
	public String channel;
	public String channel_en;
	public int isvideo;
	public String thumbImg;

	public FashConFragItem(String logo, int aid, String title, String pubtime,
			String source, String intro, String channel,String channel_en, int isvideo,
			String thumbImg) {
		super();
		this.logo = logo;
		this.aid = aid;
		this.title = title;
		this.pubtime = pubtime;
		this.source = source;
		this.intro = intro;
		this.channel = channel;
		this.channel_en = channel_en;
		this.isvideo = isvideo;
		this.thumbImg = thumbImg;
	}

}
