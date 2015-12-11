package com.project.ruili.beans;

import org.json.JSONObject;

public class SbjItem_Item {

	public int aid;
	public String title;
	public String intro;
	public String thumbImg;
	
	public SbjItem_Item() {
		super();
	}

	public SbjItem_Item(int aid, String title, String intro, String thumbImg) {
		super();
		this.aid = aid;
		this.title = title;
		this.intro = intro;
		this.thumbImg = thumbImg;
	}
	
	public static final SbjItem_Item initWithJsonObject(JSONObject obj){
		if (obj == null) return null;
		SbjItem_Item lb = null;
		try {
			lb=new SbjItem_Item(obj.getInt("aid"), 
					obj.getString("title"), 
					obj.getString("intro"),
					obj.getString("thumbImg"));
			
		} catch (Exception e) {
			
		}
		return lb;
		
		
	}

	@Override
	public String toString() {
		return "SbjItem_Item [aid=" + aid + ", title=" + title + ", intro="
				+ intro + ", thumbImg=" + thumbImg + "]";
	}
	
	
	
}
