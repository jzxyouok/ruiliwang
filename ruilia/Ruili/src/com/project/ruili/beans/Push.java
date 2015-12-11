package com.project.ruili.beans;

import org.json.JSONObject;

public class Push {

	public int id;
	public String title;
	public String pubtime;
	public String pushtype;
	public String thumbImg;
	public Push() {
		super();
	}
	public Push(int id, String title, String pubtime, String pushtype,
			String thumbImg) {
		super();
		this.id = id;
		this.title = title;
		this.pubtime = pubtime;
		this.pushtype = pushtype;
		this.thumbImg = thumbImg;
	}
	
	public static final Push initWithJsonObject(JSONObject obj){
		if (obj == null) return null;
		Push lb = null;
		try {
			lb=new Push( 
					obj.getInt("id"), 
					obj.getString("title"),
					obj.getString("pubtime"),
					obj.getString("pushtype"),
					obj.getString("thumbImg"));
			
		} catch (Exception e) {
			
		}
		return lb;
		
		
	}
}
