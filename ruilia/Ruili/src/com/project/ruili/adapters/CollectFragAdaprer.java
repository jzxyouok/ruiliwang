package com.project.ruili.adapters;

import com.project.ruili.beans.ContentTab;
import com.project.ruili.fragments.CollecMessFrg;
import com.project.ruili.fragments.CollecPicFrg;
import com.project.ruili.fragments.CollecSubjFrg;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CollectFragAdaprer extends FragmentPagerAdapter {

	private ContentTab[] datas;
	public Fragment[] fragments=new Fragment[3];
	public CollectFragAdaprer(FragmentManager fm, ContentTab[] datas) {
		super(fm);
		this.datas = datas;
		fragments[0]=new CollecMessFrg();
		fragments[1]=new CollecPicFrg();
		fragments[2]=new CollecSubjFrg();
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments[arg0];
	}

	@Override
	public int getCount() {
		return fragments.length;
	}

	/*
	 * 重写返回标题
	 */
	@Override
	public CharSequence getPageTitle(int position) {
		return datas[position].tname;
	}
}
