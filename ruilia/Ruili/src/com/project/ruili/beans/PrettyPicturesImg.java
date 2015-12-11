package com.project.ruili.beans;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class PrettyPicturesImg
{
  public String img;
  public int imgorder;

  public PrettyPicturesImg(int imgorder, String img)
  {
    this.imgorder = imgorder;
    this.img = img;
  }

  public static final PrettyPicturesImg initWithJsonObject(JSONObject jsonObject)
  {
    if (jsonObject == null)
    {
      Log.d("TAG", "json 为空");
      return null;
    }
    try
    {
      PrettyPicturesImg prettyPicturesImg = new PrettyPicturesImg(jsonObject.getInt("imgorder"), jsonObject.getString("img"));
      return prettyPicturesImg;
    }
    catch (JSONException jsonException)
    {
    	jsonException.printStackTrace();
    }
    return null;
  }

  public String toString()
  {
    return "PrettyPicturesImg [imgorder=" + this.imgorder + ", img=" + this.img + "]";
  }
}

