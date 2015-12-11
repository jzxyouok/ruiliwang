package com.project.ruili.adapters;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.ruili.R;
import com.project.ruili.adapters.SubjectAdapter.ViewHolder;
import com.project.ruili.beans.SbjItem_Item;
import com.project.ruili.net.HttpRequest;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SubjectItemAdapter extends BaseAdapter {
	
	List<SbjItem_Item> mList;
	private HttpRequest mHttpRequest;
	public SubjectItemAdapter(List<SbjItem_Item> beans, Context context){
		mList = beans;
		mHttpRequest = HttpRequest.getInstance(context);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View cview, ViewGroup parent) {
		ViewHolder vh = null;
		if(cview==null){
			ViewHolder viewholder  = new ViewHolder();
			cview = View.inflate(parent.getContext(), R.layout.subjectitem_item, null);
			viewholder.title=(TextView) cview.findViewById(R.id.sbjitem_item_title);
			viewholder.intro=(TextView) cview.findViewById(R.id.sbjitem_item_intro);
			viewholder.img=(ImageView) cview.findViewById(R.id.sbjitem_item_image);
			cview.setTag(viewholder);
		}
		vh = (ViewHolder)cview.getTag();
		SbjItem_Item lb = (SbjItem_Item) getItem(position);
		vh.title.setText(lb.title);
		vh.intro.setText(lb.intro);
		String url=lb.thumbImg;
		vh.img.setTag(url);
		
		ImageListener lis = ImageLoader.getImageListener(
				vh.img, 
				R.drawable.default_topic_item, 
				R.drawable.abc_tab_selected_pressed_holo);
		
		mHttpRequest.loadImage(url, lis, 150, 150);
		
		return cview;
	}
	
class ViewHolder {
		
		public TextView title;
		public TextView intro;
		public ImageView img;
		
	}

}
