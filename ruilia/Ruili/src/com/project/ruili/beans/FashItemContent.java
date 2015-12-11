package com.project.ruili.beans;

public class FashItemContent {
	public String imageView;
	public String title;
	public String channel_en;
	public String intro;
	public int aid;
	public FashItemContent(String imageView, String title, String channel_en,String intro,int aid) {
		super();
		this.imageView = imageView;
		this.title = title;
		this.channel_en = channel_en;
		this.intro=intro;
		this.aid=aid;
	}
	@Override
	public String toString() {
		return "FashItemContent [imageView=" + imageView + ", title=" + title+ ", intro=" + intro
				+ ", channel_en=" + channel_en + ", aid=" + aid + "]";
	}
	
	
}
