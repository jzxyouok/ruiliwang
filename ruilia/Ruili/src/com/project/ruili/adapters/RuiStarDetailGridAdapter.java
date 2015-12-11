package com.project.ruili.adapters;

import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.ruili.R;
import com.project.ruili.beans.RuiStarDetail;
import com.project.ruili.net.HttpRequest;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class RuiStarDetailGridAdapter extends BaseAdapter {

	private HttpRequest request;
	private ArrayList<RuiStarDetail> photos = new ArrayList<RuiStarDetail>();

	public RuiStarDetailGridAdapter(HttpRequest request) {
		this.request=request;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return photos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return photos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(parent.getContext(),
					R.layout.ruistar_detail_item, null);
			holder=new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.iv_ruistar_detail_item);
			convertView.setTag(holder);
		}
		RuiStarDetail data = photos.get(position);
		holder = (ViewHolder) convertView.getTag();
		holder.image.setTag(data.img);
		// 下载图片
		// 获得图片加载回调接口
		ImageListener lis = ImageLoader.getImageListener(holder.image,
				R.drawable.umeng_socialize_share_pic, R.drawable.umeng_socialize_share_pic);
		// 加载图片
		request.loadImage(data.img, lis, 0, 0);
		return convertView;
	}

	private class ViewHolder {
		ImageView image;
	}

	//设置数据
	public void setdatas(ArrayList<RuiStarDetail> photos2) {
		this.photos=photos2;
		notifyDataSetChanged();
	}

}
