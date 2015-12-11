package com.project.ruili.beans;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class SbjItemHeader {

	public int id;
	public String title;
	public String intro;
	public String pubtime;
	public String source;
	public String wapurl;
	public String logo;
	public String img;
	public JSONArray last;
	
	public SbjItemHeader() {
		super();
	}

	public SbjItemHeader(int id, String title, String intro, String pubtime,
			String source, String wapurl, String logo, String img,JSONArray list) {
		super();
		this.id = id;
		this.title = title;
		this.intro = intro;
		this.pubtime = pubtime;
		this.source = source;
		this.wapurl = wapurl;
		this.logo = logo;
		this.img = img;
		this.last=list;
	}
	
	public static final SbjItemHeader initWithJsonObject(JSONObject obj){
		if (obj == null) return null;
		SbjItemHeader lb = null;
		try {
			lb=new SbjItemHeader(obj.getInt("id"), 
					obj.getString("title"), 
					obj.getString("intro"), 
					obj.getString("pubtime"), 
					obj.getString("source"),
					obj.getString("wapurl"),
					obj.getString("logo"),
					obj.getString("img"),
					obj.getJSONArray("part"));
					
			
		} catch (Exception e) {
			
		}
		return lb;
		
		
	}

	@Override
	public String toString() {
		return "SbjItemHeader [id=" + id + ", title=" + title + ", intro="
				+ intro + ", pubtime=" + pubtime + ", source=" + source
				+ ", wapurl=" + wapurl + ", logo=" + logo + ", img=" + img
				+ "]";
	}
	
	
	
}
