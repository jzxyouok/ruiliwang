package com.project.ruili.adapters;




import java.util.ArrayList;

import com.project.ruili.beans.PrettyPicturesImg;
import com.project.ruili.beans.PrettyPicturesText;
import com.project.ruili.fragments.PrettyPicturesImgFragment;
import com.project.ruili.fragments.PrettyPicturesTextFragment;




import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PrettyPicturesShowAdapter extends FragmentPagerAdapter {
	public int aid;
	public Context context;
	public ArrayList<PrettyPicturesImg> imgIds = new ArrayList<PrettyPicturesImg>();
	public ArrayList<Fragment> lists = new ArrayList<Fragment>();
	public PrettyPicturesText mPicturesText;

	public PrettyPicturesShowAdapter(FragmentManager fm) {
		super(fm);
	}

	public PrettyPicturesShowAdapter(FragmentManager fm, int id) {
		super(fm);
		aid = id;
	}

	public int getCount() {
		return 2;
	}

	public Fragment getItem(int paramInt) {
		System.out.println("getItem aid =" + aid);

		PrettyPicturesTextFragment textFragment = new PrettyPicturesTextFragment();
		PrettyPicturesImgFragment imgFragment = new PrettyPicturesImgFragment();
		Bundle bundle = new Bundle();
		bundle.putInt("aid", aid);
		textFragment.setArguments(bundle);
		imgFragment.setArguments(bundle);
		lists.add(textFragment);
		lists.add(imgFragment);
		return (Fragment) lists.get(paramInt);
	}
}
