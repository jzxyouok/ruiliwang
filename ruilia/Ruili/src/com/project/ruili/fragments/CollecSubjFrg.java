package com.project.ruili.fragments;

import java.util.ArrayList;

import com.project.ruili.activitys.PrettyPicturesShowActivity;
import com.project.ruili.activitys.SubjectItemActivity;
import com.project.ruili.adapters.CollecPicAdapter;
import com.project.ruili.db.DBbean;
import com.project.ruili.db.DBmessage;
import com.project.ruili.db.OpenDBhelp;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

public class CollecSubjFrg extends CollecPicFrg {
	private ArrayList<DBbean> datas;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// 从数据库获取数据
		OpenDBhelp dBhelp = OpenDBhelp.getDBhelp(getActivity());
		datas = dBhelp.getDBDatas(DBmessage.TNAME, null, DBmessage.SUBJECT);
		View v = super.initView(inflater, container, 1, datas);
		return v;
	}

	@Override
	public void toDetail(int position, int aid) {
		// 跳转到自己的activity
		Intent intent = new Intent(getActivity(), SubjectItemActivity.class);
		intent.putExtra("lid", aid);
		startActivity(intent);
	}

	@Override
	public void resetDatas(CollecPicAdapter adapter) {
		// 从数据库获取数据
		OpenDBhelp dBhelp = OpenDBhelp.getDBhelp(getActivity());
		ArrayList<DBbean> datas = dBhelp.getDBDatas(DBmessage.TNAME, null,
				DBmessage.SUBJECT);
		adapter .setDatas(datas);
	}
}
