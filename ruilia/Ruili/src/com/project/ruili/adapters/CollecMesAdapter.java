package com.project.ruili.adapters;

import java.util.ArrayList;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.ruili.R;
import com.project.ruili.db.DBbean;
import com.project.ruili.db.OpenDBhelp;
import com.project.ruili.net.HttpRequest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class CollecMesAdapter extends BaseAdapter {

	private ArrayList<DBbean> datas = new ArrayList<DBbean>();
	private boolean isSeting;// 标记是否编辑状态
	private HttpRequest request;
	private OpenDBhelp dBhelp;
	public CollecMesAdapter(ArrayList<DBbean> datas,Context context) {
		if (datas!=null) {
			this.datas = datas;
		}
		request=HttpRequest.getInstance(context);
		dBhelp=OpenDBhelp.getDBhelp(context);
	}

	// 设置状态
	public void setSeting(boolean seting) {
		this.isSeting = seting;
		notifyDataSetChanged();
	}

	// 获得数据
	public ArrayList<DBbean> getDatas() {
		return datas;
	}

	@Override
	public int getCount() {
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = View.inflate(parent.getContext(),
					R.layout.collect_mes_item, null);
			ViewHolder holder = new ViewHolder();
			holder.image = (ImageView) convertView
					.findViewById(R.id.iv_coll_mes_image);
			holder.sum = (TextView) convertView
					.findViewById(R.id.tv_coll_mes_sum);
			holder.tittle = (TextView) convertView
					.findViewById(R.id.tv_coll_mes_titt);
			holder.check = (CheckBox) convertView
					.findViewById(R.id.cb_coll_mesitem);
			convertView.setTag(holder);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		DBbean data = datas.get(position);
		holder.tittle.setText(data.tittle + "");
		holder.sum.setText(data.summary);
		// 设置是否显示checkBox
		if (isSeting) {
			holder.check.setVisibility(View.VISIBLE);
			// 设置是否选中
			holder.check.setChecked(data.isCheck);
		} else {
			holder.check.setVisibility(View.GONE);
		}
		//设置tag
		holder.image.setTag(data.imageUrl);
		// 获得图片加载回调接口
		ImageListener lis = ImageLoader.getImageListener(holder.image,
				R.drawable.default_topic_item, R.drawable.default_topic_item);
		// 加载图片
		request.loadImage(data.imageUrl, lis, 80, 80);
		return convertView;
	}

	private class ViewHolder {
		ImageView image;
		TextView tittle;
		TextView sum;
		CheckBox check;
	}

	// 删除选中数据
	public void delDatas() {
		ArrayList<DBbean> delDatas = new ArrayList<DBbean>();
		for (DBbean data : datas) {
			if (data.isCheck) {
				delDatas.add(data);
				//删除数据库数据
				dBhelp.delDatas(data.detailUrl);
			}
		}
		//删除适配器数据
		datas.removeAll(delDatas);
		notifyDataSetChanged();
	}

	// 退出选择模式 将已选的box
	public void reSetChecked() {
		for (DBbean data : datas) {
			data.isCheck = false;
		}
	}
}
