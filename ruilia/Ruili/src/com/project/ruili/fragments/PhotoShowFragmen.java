package com.project.ruili.fragments;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.ruili.R;
import com.project.ruili.net.HttpRequest;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PhotoShowFragmen extends Fragment {
	private String url;
	private HttpRequest request=HttpRequest.getInstance(getActivity());
	public PhotoShowFragmen(String url) {
		this.url = url;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v=inflater.inflate(R.layout.photoshow_frag, container, false);
		final ImageView photoImg = (ImageView) v.findViewById(R.id.iv_photo_show);
	//	ProgressBar bar=(ProgressBar) v.findViewById(R.id.pb_photo_load);
		// 照片控件
		final PhotoViewAttacher mAttacher = new PhotoViewAttacher(photoImg);
		// 绑定tag
		photoImg.setTag(url);
		// 加载图片
		request.loadImage(url, new ImageListener() {
			public void onResponse(ImageContainer response, boolean isImmediate) {
				photoImg.setImageBitmap(response.getBitmap());
				mAttacher.update();
			}
			
			public void onErrorResponse(VolleyError error) {
			}
		}, 0, 0);
		return v;
	}
}
