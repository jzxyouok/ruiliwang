package com.project.ruili.adapters;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.ruili.R;
import com.project.ruili.beans.FashDetailsView;
import com.project.ruili.net.HttpRequest;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FashDetailsAdapter extends BaseAdapter {
	private List<FashDetailsView> views=new ArrayList<FashDetailsView>();
	private List<String> texts=new ArrayList<String>();
	private Context context;
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return views.size();
	}
	public FashDetailsAdapter(Context context){
		this.context=context;
	}
	public void addViews(List<FashDetailsView> list){
		this.views=list;
	}
	public void addTexts(List<String> list){
		this.texts=list;
	}
	@Override
	public FashDetailsView getItem(int position) {
		// TODO Auto-generated method stub
		return views.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			ViewHolder holder2=new ViewHolder();
			convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.fash_details_item, parent,false);
			holder2.imageView=(ImageView) convertView.findViewById(R.id.fash_details_item_iv);
			holder2.textView=(TextView) convertView.findViewById(R.id.fash_details_item_tv);
			convertView.setTag(holder2);
		}
		ViewHolder holder=(ViewHolder) convertView.getTag();
		FashDetailsView view=views.get(position);
		holder.textView.setText(texts.get(position));
		holder.imageView.setTag(view.url);
		ImageListener listener=ImageLoader.getImageListener(holder.imageView, R.drawable.default_image, R.drawable.default_image);
		HttpRequest.getInstance(context).loadImage(view.url, listener, view.width, view.height);
		return convertView;
	}
	
	private class ViewHolder{
		ImageView imageView;
		TextView textView;
	}

}
