package com.project.ruili.adapters;

import java.util.ArrayList;

import com.project.ruili.fragments.PhotoShowFragmen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PhotoFragAdapter extends FragmentPagerAdapter {
	private ArrayList<String> urls=new java.util.ArrayList<String>();
	public PhotoFragAdapter(FragmentManager fm,ArrayList<String> urls) {
		super(fm);
		this.urls=urls;
	}

	@Override
	public Fragment getItem(int arg0) {
		return new PhotoShowFragmen(urls.get(arg0));
	}

	@Override
	public int getCount() {
		return urls.size();
	}

}
