package com.project.ruili.adapters;


import java.util.List;


import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.ruili.R;
import com.project.ruili.beans.Subject;
import com.project.ruili.net.HttpRequest;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SubjectAdapter extends BaseAdapter{
	
	private List<Subject> mBeans;
	private HttpRequest mHttpRequest;

	public SubjectAdapter(List<Subject> beans, Context context) {
		mBeans = beans;
		mHttpRequest = HttpRequest.getInstance(context);
	}

	@Override
	public int getCount() {
		return mBeans.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mBeans.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View cview, ViewGroup parent) {
		ViewHolder vh = null;
		if (cview == null) {
			cview = View.inflate(parent.getContext(), R.layout.activity_subject_lv, null);
			ViewHolder viewholder  = new ViewHolder();
			viewholder.imageview = (ImageView)cview.findViewById(R.id.sbj_lv_image);
			viewholder.tv_massage = (TextView) cview.findViewById(R.id.sbj_lv_massage);
			viewholder.tv_time = (TextView)cview.findViewById(R.id.sbj_lv_time_tv);
			cview.setTag(viewholder);
		}
		vh = (ViewHolder)cview.getTag();
		Subject lb = (Subject) getItem(position);
		vh.tv_time.setText(lb.pubtime);
		vh.tv_massage.setText(lb.intro);
		String url=lb.img;
		
		vh.imageview.setTag(url);
		ImageListener lis = ImageLoader.getImageListener(
				vh.imageview, 
				R.drawable.default_topic_item, 
				R.drawable.abc_tab_selected_pressed_holo);
		
		mHttpRequest.loadImage(url, lis, 0, 0);
		return cview;
	}
	
class ViewHolder {
		
		public ImageView imageview;
		
		public TextView tv_massage;
		
		public TextView tv_time;
	}

	
}
