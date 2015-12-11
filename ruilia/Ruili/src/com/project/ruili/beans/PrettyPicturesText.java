package com.project.ruili.beans;

import org.json.JSONException;
import org.json.JSONObject;

public class PrettyPicturesText
{
  public int aid;
  public String intro;
  public String pubtime;
  public String source;
  public String title;

  public PrettyPicturesText(int aid, String source, String intro, 
		  String pubtime, String title)
  {
    this.aid = aid;
    this.source = source;
    this.intro = intro;
    this.pubtime = pubtime;
    this.title = title;
  }

  public static final PrettyPicturesText initWithJsonObject(JSONObject jsonObject)
  {
    if (jsonObject == null)
      return null;
    try
    {
      PrettyPicturesText picturesText = new PrettyPicturesText(jsonObject.getInt("aid"), jsonObject.getString("source"), jsonObject.getString("intro"), jsonObject.getString("pubtime"), jsonObject.getString("title"));
      return picturesText;
    }
    catch (JSONException jsonException)
    {
    	jsonException.printStackTrace();
    }
    return null;
  }

  public String toString()
  {
    return "PrettyPicturesText [aid=" + this.aid + ", source=" + this.source + ", intro=" + this.intro + ", pubtime=" + this.pubtime + ", title=" + this.title + "]";
  }
}

/* Location:           C:\Documents and Settings\Administrator\桌面\吴�?师\day7反编�?升级\反编译工具包\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     com.example.ruidemo.bean.PrettyPicturesText
 * JD-Core Version:    0.6.2
 */