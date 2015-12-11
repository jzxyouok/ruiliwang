package com.project.ruili.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.project.ruili.R;
import com.project.ruili.activitys.HomeActivity;
import com.project.ruili.adapters.FashionPagerAdapter;
import com.project.ruili.sliding_tab.SlidingTabLayout;
import com.special.ResideMenu.ResideMenu;

public class FashionFragmen extends Fragment implements OnClickListener {
	private ImageView mImageView;
	//viewpager里面图片尺寸为640*562
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// 刷新当前fragment
			View view = inflater.inflate(R.layout.fashion_frag_activity, null);
			SlidingTabLayout slidLayout = (SlidingTabLayout) view
					.findViewById(R.id.fashion_slid_ty);
			slidLayout.layoutTag(true);
			slidLayout.setBackgroundColor(0xffff0000);
			mImageView=(ImageView) view.findViewById(R.id.fash_person_center);
			mImageView.setClickable(true);
			mImageView.setOnClickListener(this);
			ViewPager viewPager = (ViewPager) view
					.findViewById(R.id.fashion_vp);
			viewPager.setAdapter(new FashionPagerAdapter(getFragmentManager()));
			slidLayout.setViewPager(viewPager);
		return view;
	}

	@Override
	public void onClick(View v) {
		HomeActivity activity=(HomeActivity) getActivity();
		activity.resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
		
	}
}
