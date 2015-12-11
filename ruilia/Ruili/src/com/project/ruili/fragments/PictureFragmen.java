package com.project.ruili.fragments;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.project.ruili.R;
import com.project.ruili.activitys.HomeActivity;
import com.project.ruili.activitys.PrettyPicturesShowActivity;
import com.project.ruili.adapters.PrettyPicturesAdapter;
import com.project.ruili.beans.PrettyPictures;
import com.project.ruili.net.ApiHelp;
import com.project.ruili.net.HttpRequest;
import com.project.ruili.refalshlistview.PullToRefreshBase.OnLastItemVisibleListener;
import com.project.ruili.refalshlistview.PullToRefreshBase.OnRefreshListener;
import com.project.ruili.refalshlistview.PullToRefreshGridView;
import com.special.ResideMenu.ResideMenu;


public class PictureFragmen extends Fragment implements OnRefreshListener,
		OnLastItemVisibleListener, OnItemClickListener, OnClickListener {
	private PrettyPicturesAdapter mAdapter;
	private GridView mGridView;
	private int mPage = 1;
	private ArrayList<PrettyPictures> mPictures;
	private PullToRefreshGridView mRefreshGridView;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frag_pretty_pictures, container,
				false);
		mRefreshGridView = ((PullToRefreshGridView) v
				.findViewById(R.id.frag_pretty_pictures_pull));
		mRefreshGridView.setOnRefreshListener(this);
		mRefreshGridView.setPullToRefreshEnabled(true);
		mRefreshGridView.setOnLastItemVisibleListener(this);
		mGridView = ((GridView) mRefreshGridView.getRefreshableView());
		mGridView.setNumColumns(2);
		mGridView.setOnItemClickListener(this);
		v.findViewById(R.id.frag_pretty_pictures_user).setOnClickListener(this);
		init();
		return v;
	}

	
	private void init() {
		requestDataFromNetwork();
	}


	public void onLastItemVisible() {
		mPage = mPage + 1;
		requestDataFromNetwork();
	}

	public void onRefresh() {
		mPage = 1;
		requestDataFromNetwork();
	}
	 

	public void requestDataFromNetwork() {
		Listener<String> lis = new Listener<String>() {
			public void onResponse(String paramAnonymousString) {
			
				ArrayList<PrettyPictures> pictures = pre(paramAnonymousString);
				
				if (mPage == 1) {
					   mPictures = pictures;
						mAdapter = new PrettyPicturesAdapter(getActivity(), mPictures);
						mGridView.setAdapter(mAdapter);
				
				
					
				}else{
					if (mPage > 1) {
						mPictures.addAll(mPictures.size(), pictures);
						mAdapter.notifyDataSetChanged();
					}
					
				}
				mRefreshGridView.onRefreshComplete();
				
			}

		};
		Response.ErrorListener err = new Response.ErrorListener() {
			public void onErrorResponse(VolleyError paramAnonymousVolleyError) {
			}
		};
	    StringRequest localStringRequest = new StringRequest(
	    		Method.GET, 
	    		ApiHelp.PRETTY_PICTURES+ mPage+"", 
	    		lis, 
	    		err);
	    HttpRequest.getInstance(getActivity()).sendRequest(localStringRequest);
	  }
	  
	  /***
	   * JSON 解析
	   * @param paramString
	   * @return
	   */
	  
	public ArrayList<PrettyPictures> pre(String paramString) {
		ArrayList<PrettyPictures> list = new ArrayList<PrettyPictures>();
		try {
			JSONArray localJSONArray = new JSONObject(paramString)
					.getJSONArray("img");
			for (int i = 0; i < localJSONArray.length(); i++) {
				JSONObject json = localJSONArray.getJSONObject(i);
				PrettyPictures pictures = PrettyPictures
						.initWithJsonObject(json);
				list.add(pictures);
			}
		} catch (JSONException localJSONException) {
			localJSONException.printStackTrace();
		}
		return list;
	}
	public void onItemClick(AdapterView<?> paramAdapterView, View paramView,
			int paramInt, long paramLong) {

		Intent intent = new Intent(getActivity(),
				PrettyPicturesShowActivity.class);
		intent.putExtra("aid", (mPictures.get(paramInt)).aid);
		intent.putExtra("tittle", mPictures.get(paramInt).title);
		intent.putExtra("logo", mPictures.get(paramInt).logo);
		startActivity(intent);
	}



	@Override
	public void onClick(View v) {
		HomeActivity activity=(HomeActivity) getActivity();
		activity.resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
		
	}

}
