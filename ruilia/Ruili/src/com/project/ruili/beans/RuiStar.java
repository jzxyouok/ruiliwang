package com.project.ruili.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class RuiStar {

	public String aid;
	public String uid;
	public String username;
	public String votenum;
	public String contestant_nu;
	public String img;
	public String share_count;

	public static RuiStar getFromJson(JSONObject object) {
		RuiStar star = new RuiStar();
		try {
			star.aid = object.getString("aid");
			star.uid = object.getString("uid");
			star.username = object.getString("username");
			star.votenum = object.getString("votenum");
			star.contestant_nu = object.getString("contestant_nu");
			star.img = object.getString("img");
			star.share_count = object.getString("share_count");
			return star;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
