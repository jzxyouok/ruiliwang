package com.project.ruili.fragments;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.project.ruili.R;
import com.project.ruili.beans.PrettyPicturesImg;
import com.project.ruili.net.ApiHelp;
import com.project.ruili.net.HttpRequest;
import com.project.ruili.utils.ToasUtils;

public class PrettyPicturesImgFragment extends Fragment implements OnPageChangeListener {
	public int aid;
	
	public ArrayList<PrettyPicturesImg> imgIds;
	public PrettyPicturesImgAdapter mAdapter;
	public ImageView mBt;
	public ViewPager mPager;
	public HttpRequest mRequest;
	public TextView textView;
	public MyListener myListener;  
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		myListener=(MyListener) activity;
	}

	public View onCreateView(LayoutInflater paramLayoutInflater,
			@Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.frag_pretty_pictures_show_img, null);
		mPager = ((ViewPager) localView.findViewById(R.id.frag_pretty_pictures_show_img_vp));
		mBt = ((ImageView) localView.findViewById(R.id.frag_pretty_pictures_show_img_bt));
		JsonString();
		
		mPager.setOnPageChangeListener(this);
		return localView;
	}

	public void JsonString() {
		this.aid = getArguments().getInt("aid");
		String str =ApiHelp.PRETTY_SHOW 
				+ aid;
	

		Listener<String> lis = new Listener<String>() {

			@Override
			public void onResponse(String response) {
				ArrayList<PrettyPicturesImg> localArrayList = new ArrayList<PrettyPicturesImg>();
				
				try {
					JSONArray	localJSONArray = new JSONObject(response).getJSONArray("img");
					for (int i = 0; i < localJSONArray.length(); i++) {
						JSONObject json = localJSONArray.getJSONObject(i);
						PrettyPicturesImg img = PrettyPicturesImg
								.initWithJsonObject(json);
						localArrayList.add(img);
					}
					imgIds=localArrayList;
				
					myListener.callBackFunction("",1+"/"+imgIds.size());
					mAdapter = new PrettyPicturesImgAdapter();
					mPager.setAdapter(mAdapter);
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			

			}
		};
		ErrorListener err = new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
			

			}
		};
		StringRequest localStringRequest = new StringRequest(Method.GET, str,
				lis, err);
		HttpRequest.getInstance(getActivity().getApplicationContext())
				.sendRequest(localStringRequest);
	}

	

	class PrettyPicturesImgAdapter extends PagerAdapter {
		
	

		public void destroyItem(ViewGroup paramViewGroup, int paramInt,
				Object paramObject) {
		}

		public int getCount() {
			return imgIds.size();
		}

		public Object instantiateItem(ViewGroup paramViewGroup, int paramInt) {
			final String imgurl = ((PrettyPicturesImg) imgIds
					.get(paramInt)).img;
		
			final String fileName =imgurl.substring(imgurl.lastIndexOf("/")+1);
			final ImageView localImageView = new ImageView(
					paramViewGroup.getContext());
			localImageView.setTag(imgurl);
			ImageLoader.ImageListener localImageListener = ImageLoader
					.getImageListener(
							localImageView, 
							R.drawable.umeng_socialize_share_pic, 
							R.drawable.umeng_socialize_share_pic);

			HttpRequest.getInstance(getActivity().getApplicationContext())
					.loadImage(
							imgurl, 
							localImageListener, 
							0, 
							0);

			mBt.setOnClickListener(new View.OnClickListener() {
				// ERROR //
				public void onClick(View paramAnonymousView) {
					localImageView.setDrawingCacheEnabled(true);
					Bitmap bitmap = Bitmap.createBitmap(localImageView.getDrawingCache());
					saveImageToGallery(getActivity(),bitmap,fileName);
					localImageView.setDrawingCacheEnabled(false);
				}
			});
			paramViewGroup.addView(localImageView);
			return localImageView;
		}

		public boolean isViewFromObject(View paramView, Object paramObject) {
			return paramView == paramObject;
		}
	}


	public static void saveImageToGallery(Context context, Bitmap bmp,String fileName1) {
		// 首先保存图片
		String dirname ="Boohee";
		File appDir = new File(Environment.getExternalStorageDirectory(),
				dirname);
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		String fileName = fileName1 + ".jpg";
		//String dor =appDir.getAbsolutePath()+"/"+fileName;
		File file = new File(appDir, fileName);
	
		try {
			
			if (!file.exists()) {
			
				file.createNewFile();
				ToasUtils.showLToast( context,"图片保存到图库"+dirname+"中");
			}else{
				ToasUtils.showLToast( context,"图片已经存在");
			}
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 其次把文件插入到系统图库
		try {
			MediaStore.Images.Media.insertImage(context.getContentResolver(),
					file.getAbsolutePath(), fileName, null);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// 最后通知图库更新
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
				Uri.fromFile(new File(file.getPath()))));
	}


	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	
		
	}

	@Override
	public void onPageSelected(int arg0) {
		myListener.callBackFunction(arg0+1+"/"+imgIds.size(),1+"/"+imgIds.size());
	}
	public interface MyListener {
		public void callBackFunction(String num,String first);
		
	} 
	
}
