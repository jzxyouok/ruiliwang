package com.project.ruili.beans;

import java.io.Serializable;

public class FashFragPagerObj implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String title;
	public String type;
	public int aid;
	public String channel;
	public String img;
	public FashFragPagerObj(String title, String type, int aid, String channel,
			String img) {
		super();
		this.title = title;
		this.type = type;
		this.aid = aid;
		this.channel = channel;
		this.img = img;
	}
	@Override
	public String toString() {
		return "FashFragPagerObj [title=" + title + ", type=" + type + ", aid="
				+ aid + ", channel=" + channel + ", img=" + img + "]";
	}
	
	
}
