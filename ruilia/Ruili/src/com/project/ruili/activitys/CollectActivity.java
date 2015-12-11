package com.project.ruili.activitys;

import com.project.ruili.R;
import com.project.ruili.adapters.CollectFragAdaprer;
import com.project.ruili.beans.ContentTab;
import com.project.ruili.fragments.CollecMessFrg;
import com.project.ruili.fragments.CollecPicFrg;
import com.project.ruili.fragments.CollecSubjFrg;
import com.project.ruili.sliding_tab.SlidingTabLayout;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.Window;
import android.widget.TextView;

public class CollectActivity extends FragmentActivity implements
		OnPageChangeListener {
	// 初始化参数
	public static final ContentTab[] TABS = new ContentTab[] {
			new ContentTab(1, "资讯"), new ContentTab(2, "美图"),
			new ContentTab(3, "专题"), };
	public boolean isSeting;
	private SlidingTabLayout tabLayout;
	private CollectFragAdaprer adaprer;
	private int position;
	private CollecMessFrg frg_m;
	private CollecPicFrg frg_p;
	private CollecSubjFrg frg_s;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_collection);
		tabLayout = (SlidingTabLayout) findViewById(R.id.stl_collect);
		tabLayout.setCustomTabView(R.layout.collec_tab_text, R.id.tv_text);
		tabLayout.setCenterModel(true);
		tabLayout.setOnPageChangeListener(this);
		ViewPager pager = (ViewPager) findViewById(R.id.vp_collect);
		adaprer = new CollectFragAdaprer(getSupportFragmentManager(), TABS);
		pager.setAdapter(adaprer);
		// 给三方控件设置ViewPager
		tabLayout.setViewPager(pager);
	}

	// 活动pager监听
	@Override
	public void onPageSelected(int arg0) {
		position = arg0;
		switch (arg0) {
		case 0:
			frg_m = (CollecMessFrg) adaprer.fragments[0];
			frg_m.setActivityStatu();
			break;
		case 1:
			frg_p = (CollecPicFrg) adaprer.fragments[1];
			frg_p.setActivityStatu();
			break;
		case 2:
			frg_s = (CollecSubjFrg) adaprer.fragments[2];
			frg_s.setActivityStatu();
			break;
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	//按下返回键判断当前frg是否为选择状态
	@Override
	public void onBackPressed() {
		if (isSeting) {
			switch (position) {
			case 0:
				if (frg_m == null) {
					frg_m = (CollecMessFrg) adaprer.fragments[0];
				}
				frg_m.backSeting();
				return;
			case 1:
				if (frg_p == null) {
					frg_p = (CollecPicFrg) adaprer.fragments[1];
				}
				frg_p.backSeting();
				return;
			case 2:
				if (frg_s == null) {
					frg_s = (CollecSubjFrg) adaprer.fragments[2];
				}
				frg_s.backSeting();
				return;
			}

		}
		super.onBackPressed();
	}

}
