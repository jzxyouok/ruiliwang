package com.project.ruili.adapters;

import java.util.ArrayList;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.ruili.R;
import com.project.ruili.beans.FashItemContent;
import com.project.ruili.net.HttpRequest;

public class FashContentAdapter extends BaseAdapter {
	List<FashItemContent> cons = new ArrayList<FashItemContent>();
	private FashConPagerAdapter pagerAdapter;
	private int id;

	public FashContentAdapter(int id) {
		this.id = id;
	}

	public void setListData(List<FashItemContent> list) {
		this.cons = list;
	}

	public void addListData(List<FashItemContent> list) {
		this.cons.addAll(list);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return cons.size();
	}

	@Override
	public FashItemContent getItem(int position) {
		// TODO Auto-generated method stub
		return cons.get(position-1);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (id != 1) {
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(
						R.layout.fash_con_list_item, parent, false);
				ViewHolderItem holder2 = new ViewHolderItem();
				holder2.imageView = (ImageView) convertView
						.findViewById(R.id.fash_con_list_item_iv);
				holder2.title = (TextView) convertView
						.findViewById(R.id.fash_con_list_item_title);
				holder2.intro = (TextView) convertView
						.findViewById(R.id.fash_con_list_item_tv_intro);
				convertView.setTag(holder2);
			}
			ViewHolderItem holder = (ViewHolderItem) convertView.getTag();
			FashItemContent content = cons.get(position);
			holder.title.setText("" + content.title);
			String intro_show = null;
			if (content.intro.length()>=32) {
				intro_show = content.intro.substring(0, 31);
			}else{
				intro_show = content.intro;
			}
			holder.intro.setText(intro_show + "...");
			holder.imageView.setTag(content.imageView);
			ImageListener imglis = ImageLoader.getImageListener(
					holder.imageView, R.drawable.ic_launcher,
					R.drawable.ic_launcher);
			HttpRequest.getInstance(parent.getContext()).loadImage(
					content.imageView, imglis, 100, 100);
		} else {
			if (convertView == null) {
				convertView = LayoutInflater.from(parent.getContext()).inflate(
						R.layout.fash_content_item, parent, false);
				ViewHolder holder2 = new ViewHolder();
				holder2.imageView = (ImageView) convertView
						.findViewById(R.id.fash_content_item_iv);
				holder2.title = (TextView) convertView
						.findViewById(R.id.fash_con_list_title);
				holder2.channel_en = (TextView) convertView
						.findViewById(R.id.fash_con_list_channel_en);
				convertView.setTag(holder2);
			}
			ViewHolder holder = (ViewHolder) convertView.getTag();
			FashItemContent content = cons.get(position);
			holder.title.setText("" + content.title);
			holder.channel_en.setText("" + content.channel_en);
			holder.imageView.setTag(content.imageView);
			ImageListener imglis = ImageLoader.getImageListener(
					holder.imageView, R.drawable.default_image,
					R.drawable.default_image);
			HttpRequest.getInstance(parent.getContext()).loadImage(
					content.imageView, imglis, 0, 0);
		}

		return convertView;
	}

	class ViewHolder {
		ImageView imageView;
		TextView title;
		TextView channel_en;
	}

	class ViewHolderItem {
		ImageView imageView;
		TextView title;
		TextView intro;
	}

}
