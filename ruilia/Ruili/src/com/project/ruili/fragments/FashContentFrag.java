package com.project.ruili.fragments;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RadioButton;

import com.project.ruili.R;
import com.project.ruili.abs.GetJsonListener;
import com.project.ruili.activitys.FashDetailsActivity;
import com.project.ruili.adapters.FashConPagerAdapter;
import com.project.ruili.adapters.FashContentAdapter;
import com.project.ruili.beans.FashConFragItem;
import com.project.ruili.beans.FashFragPagerObj;
import com.project.ruili.beans.FashItemContent;
import com.project.ruili.net.ApiHelp;
import com.project.ruili.net.FashToGetJson;
import com.project.ruili.refalshlistview.PullToRefreshBase.OnLastItemVisibleListener;
import com.project.ruili.refalshlistview.PullToRefreshBase.OnRefreshListener;
import com.project.ruili.refalshlistview.PullToRefreshListView;

//viewpager里面图片尺寸为640*562
public class FashContentFrag extends Fragment implements OnRefreshListener,
		OnLastItemVisibleListener, OnPageChangeListener, OnItemClickListener {
	private int id;
	private String cid;
	private int index = 1;
	private PullToRefreshListView mRefreshListview;
	private ListView listView;
	private FashConPagerAdapter pagerAdapter;
	private FashContentAdapter adapter;
	private RadioButton button1;
	private RadioButton button2;
	private RadioButton button3;
	private List<FashFragPagerObj> objs;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		id = bundle.getInt("id");
		cid = bundle.getString("cid");
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// 解析布局文件
		View view = inflater.inflate(R.layout.fashion_content_frag, null);

		// 获得布局文件里面的viewpager
		pagerAdapter = new FashConPagerAdapter(getChildFragmentManager());
		// 得到布局文件里面的listView
		mRefreshListview = (PullToRefreshListView) view
				.findViewById(R.id.fash_content_frag_lv);
		listView = mRefreshListview.getRefreshableView();
		// 设置下拉刷新可用
		mRefreshListview.setPullToRefreshEnabled(true);
		// 设置下拉刷新的事件监听
		mRefreshListview.setOnRefreshListener(this);
		// 设置当Listview的最后一个Itemview可见时的监听
		mRefreshListview.setOnLastItemVisibleListener(this);
		View pagerView = inflater.inflate(R.layout.fash_con_pager_item, null);
		ViewPager viewpager = (ViewPager) pagerView
				.findViewById(R.id.fash_cont_frag_vp);
		viewpager.setOnPageChangeListener(this);
		//找到三个按钮
		button1 = (RadioButton) pagerView
				.findViewById(R.id.fash_con_pager_rb_one);
		button1.setChecked(true);
		button2 = (RadioButton) pagerView
				.findViewById(R.id.fash_con_pager_rb_two);
		button3 = (RadioButton) pagerView
				.findViewById(R.id.fash_con_pager_rb_three);
		// 获得一个适配器
		adapter = new FashContentAdapter(id);
		// 给listview添加一个头部view
		//获取屏幕的宽
		//TODO
		listView.addHeaderView(pagerView);
		// 给viewpager设置适配器
		viewpager.setAdapter(pagerAdapter);
		// 给listView设置适配器
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(this);

		getJsonData();
		// 返回整个布局文件
		return view;
	}

	public void getJsonData() {
		// 测试数据请求
		FashToGetJson.getFashFragPagerObj(ApiHelp.FASH_SELECTION_PATH
				+ ApiHelp.FASH_CID + cid + ApiHelp.FASH_INDEX
				+ index, getActivity(), id, new GetJsonListener() {
			@Override
			public void getPagerData(List<FashFragPagerObj> list) { // viewpager对象属性的回调
				objs=list;
				// 往viewpager适配器添加数据
				pagerAdapter.addData(objs);
			}

			@Override
			public void getListData(List<FashConFragItem> items) {// listview对象属性的回调
				// 往listview适配器添加数据
				List<FashItemContent> cons = new ArrayList<FashItemContent>();
				for (int i = 0; i < items.size(); i++) {
					FashConFragItem item = items.get(i);
					cons.add(new FashItemContent(item.logo, item.title,
							item.channel_en, item.intro,item.aid));
				}
				adapter.addListData(cons);
				adapter.notifyDataSetChanged();
				mRefreshListview.onRefreshComplete();
				button1.setVisibility(View.VISIBLE);
				button2.setVisibility(View.VISIBLE);
				button3.setVisibility(View.VISIBLE);
			}
		});
	}

	@Override
	public void onLastItemVisible() {
		++index;
		FashToGetJson.getFashFragPagerObj(ApiHelp.FASH_SELECTION_PATH
				+ ApiHelp.FASH_CID + cid + ApiHelp.FASH_INDEX
				+ index, getActivity(), id, new GetJsonListener() {
			@Override
			public void getPagerData(List<FashFragPagerObj> list) {
				objs=list;
			}

			@Override
			public void getListData(List<FashConFragItem> items) {// listview对象属性的回调
				// 往listview适配器添加数据
				List<FashItemContent> cons = new ArrayList<FashItemContent>();
				for (int i = 0; i < items.size(); i++) {
					FashConFragItem item = items.get(i);
					cons.add(new FashItemContent(item.logo, item.title,
							item.channel_en, item.intro,item.aid));
				}
				adapter.addListData(cons);
			}
		});

	}

	@Override
	public void onRefresh() {
		if (index > 1)
			index=1;
		// 测试数据请求
				FashToGetJson.getFashFragPagerObj(ApiHelp.FASH_SELECTION_PATH
						+ ApiHelp.FASH_CID + cid + ApiHelp.FASH_INDEX
						+ index, getActivity(), id, new GetJsonListener() {
					@Override
					public void getPagerData(List<FashFragPagerObj> list) { // viewpager对象属性的回调
						objs=list;
						pagerAdapter.addData(objs);
						
					}

					@Override
					public void getListData(List<FashConFragItem> items) {// listview对象属性的回调
						// 往listview适配器添加数据
						List<FashItemContent> cons = new ArrayList<FashItemContent>();
						for (int i = 0; i < items.size(); i++) {
							FashConFragItem item = items.get(i);
							cons.add(new FashItemContent(item.logo, item.title,
									item.channel_en, item.intro,item.aid));
						}
						adapter.setListData(cons);
						adapter.notifyDataSetChanged();
						mRefreshListview.onRefreshComplete();
					}
				});
	}

	@Override
	public void onPageScrollStateChanged(int i) {
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {		
		
	}

	@Override
	public void onPageSelected(int i) {
			switch (i) {
			case 0:
				button1.setChecked(true);
				button2.setChecked(false);
				button3.setChecked(false);
				break;
			case 1:
				button2.setChecked(true);
				button1.setChecked(false);
				button3.setChecked(false);
				break;
			case 2:
				button3.setChecked(true);
				button1.setChecked(false);
				button2.setChecked(false);
				break;

			default:
				break;
			}
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		FashItemContent content=adapter.getItem(position);
		int aid=content.aid;
		Intent intent=new Intent(getActivity(), FashDetailsActivity.class);
		intent.putExtra("aid", aid);
		
		startActivity(intent);
		
	}
}
