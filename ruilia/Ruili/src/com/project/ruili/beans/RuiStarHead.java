package com.project.ruili.beans;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RuiStarHead {

	public String sid;
	public String title;
	public String sdate;
	public String edate;
	public String num;
	public String logo;
	public String[] desc=new String[3];
	
	
	public static RuiStarHead getFromJson(JSONObject object){
		RuiStarHead head=new RuiStarHead();
		try {
			head.sid=object.getString("sid");
			head.title=object.getString("title");
			head.sdate=object.getString("sdate");
			head.edate=object.getString("edate");
			head.num=object.getString("num");
			head.logo=object.getString("logo");
			JSONArray jsonArray = object.getJSONArray("desc");
			head.desc[0]=jsonArray.getString(0);
			head.desc[1]=jsonArray.getString(1);
			head.desc[2]=jsonArray.getString(2);
			return head;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
