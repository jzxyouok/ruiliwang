package com.project.ruili.activitys;

import java.util.ArrayList;

import com.project.ruili.R;
import com.project.ruili.adapters.PhotoFragAdapter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Window;

public class PhotoShowActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_photoshow);
		// 获得传递过来的数据
		ArrayList<String> urls = getIntent().getStringArrayListExtra("urls");
		int position = getIntent().getIntExtra("position", -1);
		// 找到ViewPager
		ViewPager vPager = (ViewPager) findViewById(R.id.vp_photo);
		vPager.setAdapter(new PhotoFragAdapter(getSupportFragmentManager(),
				urls));
		vPager.setCurrentItem(position);
	}
}
