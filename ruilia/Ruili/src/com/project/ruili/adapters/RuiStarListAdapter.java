package com.project.ruili.adapters;

import java.util.ArrayList;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.ruili.R;
import com.project.ruili.beans.RuiStar;
import com.project.ruili.net.HttpRequest;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RuiStarListAdapter extends BaseAdapter  {
	private HttpRequest request;
	private ArrayList<RuiStar> listStars = new ArrayList<RuiStar>();
	public RuiStarListAdapter(HttpRequest request) {
		this.request = request;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listStars.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listStars.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(parent.getContext(),
					R.layout.ruistar_list_item, null);
			ViewHolder holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.iv_ruistar_listitem);
			holder.colnum = (TextView) convertView
					.findViewById(R.id.tv_ruistar_listitem_colnum);
			holder.id = (TextView) convertView
					.findViewById(R.id.tv_ruistar_listitem_id);
			holder.number = (TextView) convertView
					.findViewById(R.id.tv_ruistar_listitem_number);
			holder.volnum = (TextView) convertView
					.findViewById(R.id.tv_ruistar_listitem_votnums);
			convertView.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		RuiStar data = listStars.get(position);
		holder.colnum.setText(data.share_count + "");
		holder.id.setText(data.username);
		holder.number.setText(data.contestant_nu + "号");
		holder.volnum.setText(data.votenum + "");
		// 设置tag
		holder.image.setTag(data.img);
		// 获得图片加载回调接口
		ImageListener lis = ImageLoader.getImageListener(holder.image,
				R.drawable.default_image, R.drawable.default_image);
		// 加载图片
		request.loadImage(data.img, lis, 0, 0);
		return convertView;
	}

	private class ViewHolder {
		ImageView image;
		TextView number;
		TextView id;
		TextView colnum;
		TextView volnum;
	}

	// 添加瑞星数据
	public void setDatas(ArrayList<RuiStar> listStars) {
		this.listStars=listStars;
		notifyDataSetChanged();
	}
	
	//返回数据给外界
	public ArrayList<RuiStar> getDatas(){
		return listStars;
	}
}
