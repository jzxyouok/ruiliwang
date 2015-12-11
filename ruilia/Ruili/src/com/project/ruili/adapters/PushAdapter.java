package com.project.ruili.adapters;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.ruili.R;
import com.project.ruili.beans.Push;
import com.project.ruili.net.HttpRequest;

public class PushAdapter extends BaseAdapter {

	private List<Push> mBeans;
	private HttpRequest mHttpRequest;
	
	public PushAdapter(List<Push> beans, Context context) {
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
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View cview, ViewGroup parent) {
		ViewHolder vh = null;
		if (cview == null) {
			cview = View.inflate(parent.getContext(), R.layout.pudh_item, null);
			ViewHolder viewholder  = new ViewHolder();
			viewholder.imageview = (ImageView)cview.findViewById(R.id.push_item_img);
			viewholder.tv_massage = (TextView) cview.findViewById(R.id.push_item_title);
			viewholder.tv_time = (TextView)cview.findViewById(R.id.push_item_time);
			viewholder.img=(ImageView) cview.findViewById(R.id.push_img);
			cview.setTag(viewholder);
		}
		vh = (ViewHolder)cview.getTag();
		Push lb = (Push) getItem(position);
		vh.tv_time.setText(lb.pubtime);
		vh.tv_massage.setText(lb.title);
		vh.img.setBackgroundResource(R.drawable.my_right_iv);
		String url=lb.thumbImg;
		
		vh.imageview.setTag(url);
		ImageListener lis = ImageLoader.getImageListener(
				vh.imageview, 
				R.drawable.default_topic_item, 
				R.drawable.abc_tab_selected_pressed_holo);
		
		mHttpRequest.loadImage(url, lis, 150, 150);
		return cview;
	}

class ViewHolder {
		
		public ImageView imageview;
		
		public TextView tv_massage;
		
		public TextView tv_time;
		
		public ImageView img;
	}
}
