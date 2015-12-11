package com.project.ruili.beans;

import org.json.JSONObject;



public class Subject {
	
	public int id;
	public String logo;
	public String title;
	public String pubtime;
	public String intro;
	public String img;
	public String thumbImg;
	
	
	
	public Subject() {
		super();
	}



	public Subject(int id, String logo, String title, String pubtime,
			String intro, String img, String thumbImg) {
		super();
		this.id = id;
		this.logo = logo;
		this.title = title;
		this.pubtime = pubtime;
		this.intro = intro;
		this.img = img;
		this.thumbImg = thumbImg;
	}
	
	
	public static final Subject initWithJsonObject(JSONObject obj){
		if (obj == null) return null;
		Subject lb = null;
		try {
			lb=new Subject(obj.getInt("id"), 
					obj.getString("logo"), 
					obj.getString("title"), 
					obj.getString("pubtime"), 
					obj.getString("intro"),
					obj.getString("img"),
					obj.getString("thumbImg"));
			
		} catch (Exception e) {
			
		}
		return lb;
		
		
	}
	

}
