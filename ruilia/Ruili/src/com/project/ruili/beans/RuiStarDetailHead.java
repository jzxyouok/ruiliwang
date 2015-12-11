package com.project.ruili.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class RuiStarDetailHead {

	public String uid;
	public String username;
	public String userphoto;
	public String votenum;
	public String createdate;
	public String photonum;
	public String desc;
	
	public static RuiStarDetailHead getFromJson(JSONObject object){
		RuiStarDetailHead head=new RuiStarDetailHead();
		try {
			head.uid=object.getString("uid");
			head.username=object.getString("username");
			head.userphoto=object.getString("userphoto");
			head.votenum=object.getString("votenum");
			head.createdate=object.getString("createdate");
			head.photonum=object.getString("photonum");
			head.desc=object.getString("desc");
			return head;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
}
