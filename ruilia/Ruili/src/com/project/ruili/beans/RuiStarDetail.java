package com.project.ruili.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class RuiStarDetail {

	public String shareid;
	public String img;
	public String bigimg;
	public int height;
	public int width;
	public String create_time;
	
	public static RuiStarDetail getFromJson(JSONObject object){
		RuiStarDetail head=new RuiStarDetail();
		try {
			head.shareid=object.getString("shareid");
			head.img=object.getString("img");
			head.create_time=object.getString("create_time");
			head.bigimg=object.getString("bigimg");
			head.height=object.getInt("height");
			head.width=object.getInt("width");
			return head;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
