package com.project.ruili.activitys;

import com.project.ruili.R;
import com.project.ruili.fragments.FashionFragmen;
import com.project.ruili.fragments.PictureFragmen;
import com.project.ruili.fragments.RuiStarFragmen;
import com.project.ruili.fragments.SubjectFragmen;
import com.project.ruili.utils.ToasUtils;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenu.OnMenuListener;
import com.special.ResideMenu.ResideMenuItem;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

public class HomeActivity extends FragmentActivity implements OnClickListener,
		OnMenuListener {

	private FashionFragmen fashionFragmen;
	private PictureFragmen pictureFragmen;
	private RuiStarFragmen ruiStarFragmen;
	private SubjectFragmen subjectFragmen;
	private FragmentManager manager;
	private View bt_fashion;
	private View bt_ruistar;
	private View bt_picture;
	private View bt_subject;
	public ResideMenu resideMenu;
	private ResideMenuItem item_tittle;
	private ResideMenuItem item_collect;
	private ResideMenuItem item_set;
	private boolean isMenuOpen;
	private boolean isBack;
	private Handler handler = new Handler() {};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_home);
		initFragment();
		// 设置背景为不可用状态
		setNotBG();
		// 默认第一个被点击
		bt_fashion.setBackgroundColor(0xffff0000);
		setResidMenu();
	}

	// 拦截返回键
	@Override
	public void onBackPressed() {
		if (isMenuOpen) {
			resideMenu.closeMenu();
			isMenuOpen = false;
			return;
		}
		if (isBack) {
			finish();
		} else {
			isBack=true;
			ToasUtils.showLToast(this, "按返回键退出");
			handler.postDelayed(new Runnable() {
				public void run() {
					isBack = false;
				}
			}, 2000);
		}
	}

	@Override
	public void onClick(View v) {
		// System.out.println("点击");
		FragmentTransaction transaction = manager.beginTransaction();
		// 隐藏所有frgs
		hintFrgs(transaction);
		switch (v.getId()) {
		case R.id.layouy_home_news:
			// 设置全部单选框为不被点击
			setNotBG();
			// 显示当前frgs
			transaction.show(fashionFragmen);
			bt_fashion.setBackgroundColor(0xffff0000);
			transaction.commit();
			break;
		case R.id.layouy_home_star:
			// 设置全部单选框为不被点击
			setNotBG();
			transaction.show(ruiStarFragmen);
			bt_ruistar.setBackgroundColor(0xffff0000);
			transaction.commit();
			break;
		case R.id.layouy_home_pic:
			// 设置全部单选框为不被点击
			setNotBG();
			transaction.show(pictureFragmen);
			bt_picture.setBackgroundColor(0xffff0000);
			transaction.commit();
			break;
		case R.id.layouy_home_sub:
			// 设置全部单选框为不被点击
			setNotBG();
			transaction.show(subjectFragmen);
			bt_subject.setBackgroundColor(0xffff0000);
			transaction.commit();
			break;
		}

		if (v == item_collect) {
			startActivity(new Intent(this, CollectActivity.class));
		} else if (v == item_set) {
			startActivity(new Intent(this, PersonSettingActivity.class));
		} else if (v == item_tittle) {
			startActivity(new Intent(this, PushActivity.class));
		}
	}

	// 设置layoutn背景为透明
	private void setNotBG() {
		bt_fashion.setBackgroundColor(0x00000000);
		bt_picture.setBackgroundColor(0x00000000);
		bt_ruistar.setBackgroundColor(0x00000000);
		bt_subject.setBackgroundColor(0x00000000);
	}

	// 初始化数据
	private void initFragment() {
		bt_fashion = findViewById(R.id.layouy_home_news);
		bt_fashion.setOnClickListener(this);
		bt_ruistar = findViewById(R.id.layouy_home_star);
		bt_ruistar.setOnClickListener(this);
		bt_picture = findViewById(R.id.layouy_home_pic);
		bt_picture.setOnClickListener(this);
		bt_subject = findViewById(R.id.layouy_home_sub);
		bt_subject.setOnClickListener(this);

		// 创建需要的fragment
		fashionFragmen = new FashionFragmen();
		pictureFragmen = new PictureFragmen();
		ruiStarFragmen = new RuiStarFragmen();
		subjectFragmen = new SubjectFragmen();

		manager = getSupportFragmentManager();
		FragmentTransaction transaction;
		transaction = manager.beginTransaction();
		transaction.add(R.id.frag_home, fashionFragmen, "fashionFragmen");
		transaction.add(R.id.frag_home, ruiStarFragmen, "ruiStarFragmen");
		transaction.add(R.id.frag_home, pictureFragmen, "pictureFragmen");
		transaction.add(R.id.frag_home, subjectFragmen, "subjectFragmen");
		hintFrgs(transaction);
		transaction.show(fashionFragmen);
		// 将fragment加入到栈中
		transaction.addToBackStack(null);
		transaction.commit();
	}

	// 隐藏所有frg
	private void hintFrgs(FragmentTransaction transaction) {
		transaction.hide(fashionFragmen);
		transaction.hide(ruiStarFragmen);
		transaction.hide(pictureFragmen);
		transaction.hide(subjectFragmen);
	}

	// 设置侧滑
	private void setResidMenu() {
		resideMenu = new ResideMenu(this);
		resideMenu.setBackground(R.drawable.menu_background);
		resideMenu.attachToActivity(this);
		resideMenu.setScaleValue(0.7f);
		// 设置3D模式
		// resideMenu.setUse3D(true);
		// 设置右边不用
		resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_RIGHT);
		resideMenu.setSwipeDirectionDisable(ResideMenu.DIRECTION_LEFT);
		resideMenu.setMenuListener(this);
		// create menu items;
		String titles[] = { "推荐", "收藏", "设置" };
		int icon[] = { R.drawable.ic_message, R.drawable.ic_collection,
				R.drawable.usercenter_setting_on };

		item_tittle = new ResideMenuItem(this, icon[0], titles[0]);
		item_tittle.setOnClickListener(this);
		// orResideMenu.DIRECTION_RIGHT
		resideMenu.addMenuItem(item_tittle, ResideMenu.DIRECTION_LEFT);

		item_collect = new ResideMenuItem(this, icon[1], titles[1]);
		item_collect.setOnClickListener(this);
		// orResideMenu.DIRECTION_RIGHT
		resideMenu.addMenuItem(item_collect, ResideMenu.DIRECTION_LEFT);

		item_set = new ResideMenuItem(this, icon[2], titles[2]);
		item_set.setOnClickListener(this);
		// orResideMenu.DIRECTION_RIGHT
		resideMenu.addMenuItem(item_set, ResideMenu.DIRECTION_LEFT);

	}

	// 侧滑菜单监听
	@Override
	public void openMenu() {
		isMenuOpen = true;
	}

	public void closeMenu() {
		isMenuOpen = false;
	}

}
