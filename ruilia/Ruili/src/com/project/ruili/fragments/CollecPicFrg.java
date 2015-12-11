package com.project.ruili.fragments;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.GridView;

import com.project.ruili.R;
import com.project.ruili.activitys.CollectActivity;
import com.project.ruili.activitys.PrettyPicturesShowActivity;
import com.project.ruili.adapters.CollecMesAdapter;
import com.project.ruili.adapters.CollecPicAdapter;
import com.project.ruili.db.DBbean;
import com.project.ruili.db.DBmessage;
import com.project.ruili.db.OpenDBhelp;

public class CollecPicFrg extends Fragment implements OnClickListener,
		OnItemClickListener, OnItemLongClickListener {
	private boolean isSeting;
	private CollectActivity activity;
	private View layout;
	private CollecPicAdapter adapter;
	private GridView listView;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// 从数据库获取数据
		OpenDBhelp dBhelp = OpenDBhelp.getDBhelp(getActivity());
		ArrayList<DBbean> datas = dBhelp.getDBDatas(DBmessage.TNAME, null,
				DBmessage.PICTURE);
		View v = initView(inflater, container, 2, datas);
		return v;
	}

	public void onStart() {
		resetDatas(adapter);
		super.onStart();
	}

	public void resetDatas(CollecPicAdapter adapter) {
		// 从数据库获取数据
		OpenDBhelp dBhelp = OpenDBhelp.getDBhelp(getActivity());
		ArrayList<DBbean> datas = dBhelp.getDBDatas(DBmessage.TNAME, null,
				DBmessage.PICTURE);
		adapter.setDatas(datas);
	}

	public View initView(LayoutInflater inflater, ViewGroup container,
			int numColumns, ArrayList<DBbean> datas) {
		// 解析布局
		View v = inflater.inflate(R.layout.collec_pic, container, false);
		listView = (GridView) v.findViewById(R.id.lv_collec);
		listView.setNumColumns(numColumns);
		// 设置item点击
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		layout = v.findViewById(R.id.layout_collect_p);
		// 判断是否编辑状态,显示删除按钮
		if (isSeting) {
			toSeting();
		}
		layout.setOnClickListener(this);
		adapter = new CollecPicAdapter(datas, getActivity());
		listView.setAdapter(adapter);
		activity = (CollectActivity) getActivity();
		return v;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.layout_collect_p:// 点击删除
			// 删除被选中的数据
			adapter.delDatas();
			backSeting();
			break;
		}
	}

	// 选择状态
	private void toSeting() {
		isSeting = true;
		// 显示删除框
		layout.setVisibility(View.VISIBLE);
		// 显示item 选择框
		adapter.setSeting(true);
		activity.isSeting = true;
	}

	// 退出选择状态
	public void backSeting() {
		isSeting = false;
		// 隐藏删除框
		layout.setVisibility(View.GONE);
		adapter.setSeting(false);
		activity.isSeting = false;
		adapter.reSetChecked();
	}

	// activity 设置状态
	public void setActivityStatu() {
		if (isSeting) {
			toSeting();
		} else {
			backSeting();
		}
	}

	// item点击
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (isSeting) {
			// 选择checkBox
			CheckBox box = (CheckBox) view.findViewById(R.id.cb_coll_mesitem);
			box.setChecked(!box.isChecked());
			// 改变数据
			adapter.getDatas().get(position).isCheck = box.isChecked();
		} else {
			int aid = getAid(position);
			toDetail(position, aid);
		}
	}

	/**
	 * 获得aid
	 * 
	 * @param position
	 * @return
	 */
	private int getAid(int position) {
		// 跳转到详细
		String url = adapter.getDatas().get(position).detailUrl;
		int aid = Integer.parseInt(url.substring(url.lastIndexOf("=") + 1));
		return aid;
	}

	/**
	 * 跳转到对应的详细界面,子类要复写
	 */
	public void toDetail(int position, int aid) {
		Intent intent = new Intent(getActivity(),
				PrettyPicturesShowActivity.class);
		// System.out.println("aid="+aid);
		intent.putExtra("aid", aid);
		startActivity(intent);
	}

	@Override
	public void onDestroy() {
		// TODO 保存数据到数据库
		super.onDestroy();
	}

	// 长按
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		if (!isSeting) {
			// 跳转选择状态
			toSeting();
		} else {
			backSeting();
		}
		return true;
	}
}
