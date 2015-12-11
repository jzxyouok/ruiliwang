package com.project.ruili.adapters;



import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.project.ruili.R;
import com.project.ruili.beans.PrettyPictures;
import com.project.ruili.net.HttpRequest;


public class PrettyPicturesAdapter extends BaseAdapter {
	private HttpRequest mHttpRequest;
	private List<PrettyPictures> mlist = new ArrayList();

	public PrettyPicturesAdapter(Context context, List<PrettyPictures> list) {
		mHttpRequest = HttpRequest.getInstance(context);
		mlist = list;
	}

	public int getCount() {
		return mlist.size();
	}

	public PrettyPictures getItem(int position) {
		return mlist.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		PrettyPictures prettyPictures = (PrettyPictures) mlist.get(position);
		if (convertView == null) {
			convertView = LayoutInflater.from(parent.getContext()).inflate(
					R.layout.item_frag_pretty_pictures, parent, false);
			HoldView hold = new HoldView();
			hold.logoView = ((ImageView) convertView
					.findViewById(R.id.frag_pretty_pictures_item_logo));
			hold.titleView = ((TextView) convertView
					.findViewById(R.id.frag_pretty_pictures_item_title));
			hold.imgnumView = ((TextView) convertView
					.findViewById(R.id.frag_pretty_pictures_item_imgnum));
			convertView.setTag(hold);
		}
		HoldView hold = (HoldView) convertView.getTag();
		hold.imgnumView.setText(prettyPictures.imgnum + "图");
		hold.titleView.setText(prettyPictures.title);
		hold.logoView.setTag(prettyPictures.logo);
		ImageLoader.ImageListener listener = ImageLoader.getImageListener(
				hold.logoView, 
				R.drawable.default_image,
				R.drawable.default_image);
		mHttpRequest.loadImage(prettyPictures.logo, listener, 0, 0);
		return convertView;
	}

	class HoldView {
		TextView imgnumView;
		ImageView logoView;
		TextView titleView;

	}
}
