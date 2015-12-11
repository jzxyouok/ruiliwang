package com.project.ruili.adapters;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.project.ruili.beans.FashFragPagerObj;
import com.project.ruili.fragments.FashConPagerFrag;

public class FashConPagerAdapter extends FragmentStatePagerAdapter {
	List<FashFragPagerObj> objs=new ArrayList<FashFragPagerObj>();
	public FashConPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}
	@Override
	public Fragment getItem(int id) {
		FashConPagerFrag frag=new FashConPagerFrag();
		Bundle bundle=new Bundle();
		bundle.putInt("id",id);
		bundle.putSerializable("obj", objs.get(id));
		frag.setArguments(bundle);
		return frag;
	}

	@Override
	public int getCount() {
		return objs.size();
		
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		
		return super.getPageTitle(position);
	}
	public void addData(List<FashFragPagerObj> objs){
		this.objs=objs;
		notifyDataSetChanged();
	}
}
