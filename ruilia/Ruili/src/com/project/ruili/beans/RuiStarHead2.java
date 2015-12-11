package com.project.ruili.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class RuiStarHead2 {

	public int contentid;
	public String title;
	public String url;
	public String thumb;
	public long time;
	public String zhuanji;

	public static RuiStarHead2 getFromJson(JSONObject object) {
		RuiStarHead2 head2 = new RuiStarHead2();
		try {
			head2.contentid = object.getInt("contentid");
			head2.title = object.getString("title");
			head2.url = object.getString("url");
			head2.thumb = object.getString("thumb");
			head2.zhuanji = object.getString("zhuanji");
			head2.time = object.getLong("time");
			return head2;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
