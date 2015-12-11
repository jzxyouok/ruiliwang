package com.project.ruili.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.project.ruili.fragments.FashContentFrag;
import com.project.ruili.fragments.FashionFragmen;
import com.project.ruili.net.ApiHelp;

public class FashionPagerAdapter extends FragmentStatePagerAdapter {
	private FragmentManager mFraManager;
	public FashionPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
		mFraManager=fm;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return ApiHelp.FASH_CONTENT_TAB.length;
	}

	@Override
	public Fragment getItem(int id) {
		FashContentFrag frag=new FashContentFrag();
		Bundle bundle=new Bundle();
		bundle.putString("cid", ApiHelp.FASH_CONTENT_TAB[id].cid);
		bundle.putInt("id",ApiHelp.FASH_CONTENT_TAB[id].id);
		frag.setArguments(bundle);
		return frag;
	}	
	
	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		return ApiHelp.FASH_CONTENT_TAB[position].name;
	}
	

}
