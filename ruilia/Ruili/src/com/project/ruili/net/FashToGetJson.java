package com.project.ruili.net;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.project.ruili.abs.GetDetailsDataListener;
import com.project.ruili.abs.GetJsonListener;
import com.project.ruili.beans.FashConFragItem;
import com.project.ruili.beans.FashDetailsHead;
import com.project.ruili.beans.FashDetailsView;
import com.project.ruili.beans.FashFragPagerObj;

public class FashToGetJson {

	public static void getFashFragPagerObj(String path, Context context,int id,
			GetJsonListener listener) {
		downloadForObj(path, context, id,listener);
	}

	public static void getFashDetailsData(String path, Context context,GetDetailsDataListener listener) {
		downloadForDetails(path, context,listener);
	}

	private static void downloadForObj(String path, Context context,final int id,
			final GetJsonListener listen) { // 获得getFashFragPagerObj对象
		// TODO Auto-generated method stubg
		Listener<String> listener = new Listener<String>() {// 有数据返回时的回调
			List<FashFragPagerObj> objs = new ArrayList<FashFragPagerObj>();
			List<FashConFragItem> cons = new ArrayList<FashConFragItem>();

			@Override
			public void onResponse(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					JSONArray recArray = jsonObject.getJSONArray("rec");
					for (int i = 0; i < recArray.length(); i++) {// 获取pager里面的对象属性
						JSONObject obj = recArray.getJSONObject(i);
						String title = obj.getString("title");
						String type = obj.getString("type");
						int aid = obj.getInt("aid");
						String channel = obj.getString("channel");
						String img = obj.getString("img");
						objs.add(new FashFragPagerObj(title, type, aid,
								channel, img));
					}
					listen.getPagerData(objs);
					JSONArray artArray = jsonObject.getJSONArray("article");
					for (int j = 0; j < artArray.length(); j++) { // 获得list里面的数据对象
						JSONObject artobj = artArray.getJSONObject(j);
						String logo = artobj.getString("logo");
						int aid = artobj.getInt("aid");
						String title = artobj.getString("title");
						String pubtime = artobj.getString("pubtime");
						String source = artobj.getString("source");
						String intro = artobj.getString("intro");
						String channel = artobj.getString("channel");
						int isvideo = artobj.getInt("isvideo");
						String thumbImg = artobj.getString("thumbImg");
						String channel_en=null;
						if(id==1){
							channel_en= artobj.getString("channel_en");
						}
						cons.add(new FashConFragItem(logo, aid, title, pubtime,
								source, intro, channel,channel_en,isvideo,
								thumbImg));
					}
					listen.getListData(cons);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};
		ErrorListener errorListener = new ErrorListener() {// 没有数据返回时的回调
			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Log.d("TAG", "不好意思，网络数据访问不到");
			}
		};
		StringRequest request = new StringRequest(path, listener, errorListener);// 构建访问服务器返回String的请求
		HttpRequest.getInstance(context).sendRequest(request);// 发出请求

	}

	private static void downloadForDetails(String path, Context context,final GetDetailsDataListener detailsListener) {// 获得详情数据
		// TODO Auto-generated method stub
		// 获得getFashFragPagerObj对象
		// TODO Auto-generated method stubg
		Listener<String> listener = new Listener<String>() {// 有数据返回时的回调
			List<FashDetailsView> views = new ArrayList<FashDetailsView>();
			FashDetailsHead head=null;
			@Override
			public void onResponse(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					JSONObject obj = jsonObject.getJSONObject("article");
						int aid = obj.getInt("aid");
						String title = obj.getString("title");
						String pubtime = obj.getString("pubtime");
						String source = obj.getString("source");
						String intro = obj.getString("intro");
						head=new FashDetailsHead(aid, title, pubtime, source, intro);
						//头部数据回调
						detailsListener.getHeadData(head);
						JSONArray urls=obj.getJSONArray("imgurl");
						for (int i = 0; i < urls.length(); i++) {
							JSONObject urlobj=urls.getJSONObject(i);
							String url=urlobj.getString("url");
							int width=urlobj.getInt("width");
							int height=urlobj.getInt("height");
							views.add(new FashDetailsView(url,width,height));
						}
						//url数据回调
						detailsListener.getUrlData(views);
						
						JSONArray texts=obj.getJSONArray("text");
						//text数据回调
						detailsListener.getTextsData(texts);
						
					

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		};
		ErrorListener errorListener = new ErrorListener() {// 没有数据返回时的回调
			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Log.d("TAG", "不好意思，网络数据访问不到");
			}
		};
		StringRequest request = new StringRequest(path, listener, errorListener);// 构建访问服务器返回String的请求
		HttpRequest.getInstance(context).sendRequest(request);// 发出请求

	}
}
