package com.project.ruili.beans;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class PrettyPictures
{
  public int aid;
  public String content;
  public int imgnum;
  public String intro;
  public String logo;
  public String pubtime;
  public String title;

  public PrettyPictures(int aid, String logo, String intro, String title, String content, 
		  String pubtime, int imgnum)
  {
    this.aid = aid;
    this.logo = logo;
    this.intro = intro;
    this.title = title;
    this.content = content;
    this.pubtime = pubtime;
    this.imgnum = imgnum;
  }

  public static final PrettyPictures initWithJsonObject(JSONObject jsonObject)
  {
    if (jsonObject == null)
    {
      Log.d("TAG", "json 为空");
      return null;
    }
    try
    {
      PrettyPictures pictures = new PrettyPictures(jsonObject.getInt("aid"), jsonObject.getString("logo"), jsonObject.getString("intro"), jsonObject.getString("title"), jsonObject.optString("content"), jsonObject.getString("pubtime"), jsonObject.getInt("imgnum"));
      return pictures;
    }
    catch (JSONException jsonException)
    {
    	jsonException.printStackTrace();
    }
    return null;
  }

  public String toString()
  {
    return "PrettyPictures [aid=" + this.aid + ", logo=" + this.logo + ", intro=" + this.intro + ", title=" + this.title + ", content=" + this.content + ", pubtime=" + this.pubtime + ", imgnum=" + this.imgnum + "]";
  }
}

