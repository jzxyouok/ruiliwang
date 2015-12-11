package com.project.ruili.fragments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.ruili.R;
import com.project.ruili.activitys.HomeActivity;
import com.project.ruili.activitys.RuiStarDetailActivity;
import com.project.ruili.adapters.RuiStarListAdapter;
import com.project.ruili.beans.RuiStar;
import com.project.ruili.beans.RuiStarHead;
import com.project.ruili.beans.RuiStarHead2;
import com.project.ruili.net.ApiHelp;
import com.project.ruili.net.HttpRequest;
import com.project.ruili.refalshlistview.PullToRefreshBase.OnRefreshListener;
import com.project.ruili.refalshlistview.PullToRefreshListView;
import com.project.ruili.utils.ToasUtils;
import com.special.ResideMenu.ResideMenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class RuiStarFragmen extends Fragment implements OnRefreshListener,
		OnClickListener, OnItemClickListener, ErrorListener, Listener<String>,
		OnScrollListener {

	private static final int FIRST = 1;
	private ListView listView;
	private PullToRefreshListView ptrlv;
	private ArrayList<RuiStar> listStars = new ArrayList<RuiStar>();
	private ArrayList<RuiStar> listStars2 = new ArrayList<RuiStar>();
	private RuiStarHead headData;
	private ArrayList<RuiStarHead2> head2Datas = new ArrayList<RuiStarHead2>();
	private RuiStarListAdapter adapter;
	private int page_0 = 0;
	private int page_1 = 0;
	private int flag = 0;
	private HttpRequest request;
	private ImageView headIv;
	private ImageView head2Iv4;
	private ImageView head2Iv3;
	private ImageView head2Iv2;
	private ImageView head2Iv1;
	private TextView headtv1;
	private TextView headtv3;
	private TextView headtv4;
	private TextView headtv5;
	private TextView headtv6;
	private TextView head2tv1;
	private TextView head2tv2;
	private TextView head2tv3;
	private TextView head2tv4;
	private Button bt_foot_loadmore;
	private View ly_loading;
	private StringRequest stringRequest;
	private boolean isLoading;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//System.out.println("onCreate");
		if (request == null) {
			request = HttpRequest.getInstance(getActivity());
		}
		if (adapter == null) {
			adapter = new RuiStarListAdapter(request);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_ruistar, null, false);
		//找到个人中心控件
		view.findViewById(R.id.iv_center).setOnClickListener(this);
		initView(inflater, view);
		// 初始化数据
		sentRequest();
		//System.out.println("sentRequest");
		return view;
	}

	// 初始化数据
	private void initView(LayoutInflater inflater, View view) {
		ptrlv = (PullToRefreshListView) view.findViewById(R.id.lv_ruistar_list);
		// 设置下拉刷新可用
		ptrlv.setPullToRefreshEnabled(true);
		// 设置下拉监听
		ptrlv.setOnRefreshListener(this);
		headerView = inflater.inflate(R.layout.ruistar_list_item_header,
				null, false);
		//TODO 
		headerView.setVisibility(View.GONE);
		headIv = (ImageView) headerView.findViewById(R.id.iv_ruistar_headiv);
		
		headerView2 = inflater.inflate(R.layout.ruistar_list_item_header2,
				null, false);
		headerView2.setVisibility(View.GONE);
		head2Iv1 = (ImageView) headerView2
				.findViewById(R.id.iv_ruistar_head2_fir);
		head2Iv1.setOnClickListener(this);
		head2Iv2 = (ImageView) headerView2
				.findViewById(R.id.iv_ruistar_head2_sec);
		head2Iv2.setOnClickListener(this);
		head2Iv3 = (ImageView) headerView2
				.findViewById(R.id.iv_ruistar_head2_thi);
		head2Iv3.setOnClickListener(this);
		head2Iv4 = (ImageView) headerView2
				.findViewById(R.id.iv_ruistar_head2_fou);
		head2Iv4.setOnClickListener(this);
		headtv1 = (TextView) headerView.findViewById(R.id.tv_ruistar_headtv1);
		headtv3 = (TextView) headerView.findViewById(R.id.tv_ruistar_headtv3);
		headtv4 = (TextView) headerView.findViewById(R.id.tv_ruistar_headtv4);
		headtv5 = (TextView) headerView.findViewById(R.id.tv_ruistar_headtv5);
		headtv6 = (TextView) headerView.findViewById(R.id.tv_ruistar_headtv6);
		// 找到头2的文字控件
		head2tv1 = (TextView) headerView2
				.findViewById(R.id.tv_ruistar_head2tv1);
		head2tv2 = (TextView) headerView2
				.findViewById(R.id.tv_ruistar_head2tv2);
		head2tv3 = (TextView) headerView2
				.findViewById(R.id.tv_ruistar_head2tv3);
		head2tv4 = (TextView) headerView2
				.findViewById(R.id.tv_ruistar_head2tv4);
		View headerView3 = inflater.inflate(R.layout.ruistar_list_item_header3,
				null, false);
		headerView3.findViewById(R.id.rb_ruistar_first)
				.setOnClickListener(this);
		headerView3.findViewById(R.id.rb_ruistar_second).setOnClickListener(
				this);
		// 找到底部刷新
		// 找到加载更多按钮
		// 添加尾部刷新布局
		View footView = View.inflate(getActivity(),
				R.layout.ruistarfrag_footer, null);
		bt_foot_loadmore = (Button) footView.findViewById(R.id.bt_loadmore);
		bt_foot_loadmore.setOnClickListener(this);
		ly_loading = footView.findViewById(R.id.laoyout_progressbar);

		listView = ptrlv.getRefreshableView();
		listView.setOnScrollListener(this);
		listView.addHeaderView(headerView);
		listView.addHeaderView(headerView2);
		listView.addHeaderView(headerView3);
		listView.addFooterView(footView);
		listView.setOnItemClickListener(this);
		listView.setAdapter(adapter);
	}

	/**
	 * 下拉刷新
	 */
	@Override
	public void onRefresh() {
		if (page_0 == FIRST && flag == 0) {
			page_0 = 0;
		} else if (page_1 == FIRST && flag == 1) {
			page_1 = 0;
		}
		// 发送数据
		sentRequest();
	}

	// 发送请求
	private void sentRequest() {
		if (flag == 0) {
			page_0++;
			stringRequest = new StringRequest(Method.GET,
					ApiHelp.getRuiStarURL(page_0, flag), this, this);
		} else {
			page_1++;
			stringRequest = new StringRequest(Method.GET,
					ApiHelp.getRuiStarURL(page_1, flag), this, this);
		}

		request.sendRequest(stringRequest);
		bt_foot_loadmore.setVisibility(View.GONE);
		ly_loading.setVisibility(View.VISIBLE);
		isLoading = true;

	}

	// 点击事件
	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getActivity(), RuiStarDetailActivity.class);
		switch (v.getId()) {

		case R.id.iv_ruistar_head2_fir:
			intent.putExtra("aid", head2Datas.get(0).zhuanji);
			startActivity(intent);
			break;
		case R.id.iv_ruistar_head2_sec:
			intent.putExtra("aid", head2Datas.get(1).zhuanji);
			startActivity(intent);
			break;
		case R.id.iv_ruistar_head2_thi:
			intent.putExtra("aid", head2Datas.get(2).zhuanji);
			startActivity(intent);
			break;
		case R.id.iv_ruistar_head2_fou:
			intent.putExtra("aid", head2Datas.get(3).zhuanji);
			startActivity(intent);
			break;
		case R.id.bt_loadmore:// 点击加载更多
			sentRequest();
			break;
		case R.id.rb_ruistar_first:// 点击加载初赛
			flag = 0;
			adapter.setDatas(listStars);
			break;
		case R.id.rb_ruistar_second:// 点击加载复赛
			flag = 1;
			adapter.setDatas(listStars2);
			if (page_1 == 0) {
				sentRequest();
			}
			break;
		case R.id.iv_center:// 点击个人中心
		HomeActivity activity=(HomeActivity) getActivity();
		activity.resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
			break;
		}
	}

	// 点击item,跳转对应详细界面
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// 点击的是加载更多
		ToasUtils.showLToast(getActivity(), "" + arg2);
		Intent intent = new Intent(getActivity(), RuiStarDetailActivity.class);
		if (flag == 0) {
			intent.putExtra("aid", listStars.get(arg2 - 3).aid);
		} else {
			intent.putExtra("aid", listStars2.get(arg2 - 3).aid);
		}
		startActivity(intent);
		if (arg2 == adapter.getCount() + 3) {
			return;
		}
	}

	// 网络正确回调
	@Override
	public void onResponse(String response) {
		// 如果是下拉刷新则 让刷新头隐藏
		if (page_0 == FIRST && flag == 0) {
			ptrlv.onRefreshComplete();
		} else if (page_1 == FIRST && flag == 1) {
			ptrlv.onRefreshComplete();
		}
		isLoading = false;
		bt_foot_loadmore.setVisibility(View.VISIBLE);
		ly_loading.setVisibility(View.GONE);
		// 解析json
		getFromString(response);
		// 添加数据到适配器
		if (flag == 0) {
			adapter.setDatas(listStars);
		} else {
			adapter.setDatas(listStars2);
		}
		if (headData != null && head2Datas.size() > 0) {
			// 下载头部图片
			loadHeadImage();
			// 设置头部文字
			setText();
		}
	}

	// 设置头部文字
	private void setText() {
		headtv1.setText("NO.130  " + headData.title);
		headtv3.setText("1. " + headData.desc[0]);
		headtv4.setText("2. " + headData.desc[1]);
		headtv5.setText("3. " + headData.desc[2]);
		headtv6.setText("4. 报名时间 :" + headData.sdate + "-" + headData.edate);

		head2tv1.setText("冠军:" + head2Datas.get(0).title);
		head2tv2.setText("亚军:" + head2Datas.get(1).title);
		head2tv3.setText("季军:" + head2Datas.get(2).title);
		head2tv4.setText("人气:" + head2Datas.get(3).title);
	}

	// 加载头部的图片
	private void loadHeadImage() {
		// 加载View头的图片
		// 设置头1图片tag
		headIv.setTag(headData.logo);
		// 设置头2tag
		head2Iv1.setTag(head2Datas.get(0).thumb);
		head2Iv2.setTag(head2Datas.get(1).thumb);
		head2Iv3.setTag(head2Datas.get(2).thumb);
		head2Iv4.setTag(head2Datas.get(3).thumb);
		// 获得图片加载回调接口
		ImageListener lis = ImageLoader.getImageListener(headIv,
				R.drawable.default_image, R.drawable.default_image);
		// 加载图片
		request.loadImage(headData.logo, lis, 0, 0);
		// 获得图片加载回调接口
		lis = ImageLoader.getImageListener(head2Iv1,
				R.drawable.default_image, R.drawable.default_image);
		// 加载图片
		request.loadImage(head2Datas.get(0).thumb, lis, 0, 0);

		// 获得图片加载回调接口
		lis = ImageLoader.getImageListener(head2Iv2,
				R.drawable.default_image, R.drawable.default_image);
		// 加载图片
		request.loadImage(head2Datas.get(1).thumb, lis, 0, 0);

		// 获得图片加载回调接口
		lis = ImageLoader.getImageListener(head2Iv3,
				R.drawable.default_image, R.drawable.default_image);
		// 加载图片
		request.loadImage(head2Datas.get(2).thumb, lis, 0, 0);

		// 获得图片加载回调接口
		lis = ImageLoader.getImageListener(head2Iv4,
				R.drawable.default_image, R.drawable.default_image);
		// 加载图片
		request.loadImage(head2Datas.get(3).thumb, lis, 0, 0);
	}

	// 网络错误回调
	@Override
	public void onErrorResponse(VolleyError error) {
		if (page_0 == FIRST && flag == 0) {
			ptrlv.onRefreshComplete();
		} else if (page_1 == FIRST && flag == 1) {
			ptrlv.onRefreshComplete();
		}
		isLoading = false;
		ToasUtils.showLToast(getActivity(), "网络错误");
		bt_foot_loadmore.setVisibility(View.VISIBLE);
		ly_loading.setVisibility(View.GONE);
	}

	// 解析瑞星主页json
	private void getFromString(String response) {
		try {
			// 解析json
			if (response == null) {
				ToasUtils.showLToast(getActivity(), "服务器错误");
				return;
			}
			JSONObject object = new JSONObject(response);
			JSONObject result = object.getJSONObject("result");
			if (!result.getString("message").equals("成功")) {
				ToasUtils.showLToast(getActivity(), "服务器错误");
				return;
			}
			// System.out.println(response);
			// 解析瑞星列表
			JSONObject list = object.getJSONObject("list");
			JSONObject data = list.getJSONObject("data");
			JSONArray starlist = data.getJSONArray("starlist");
			chooseDataList(starlist);
			if (flag == 1) {
				return;
			}
			// 解析上期颁奖台
			JSONArray wlist = object.getJSONArray("wlist");
			for (int i = 0; i < wlist.length(); i++) {
				JSONObject obj = wlist.getJSONObject(i);
				JSONArray oldStar = obj.getJSONArray("data");
				JSONArray oldStar1 = oldStar.getJSONArray(0);
				JSONObject obj1 = oldStar1.getJSONObject(0);
				RuiStarHead2 head2 = RuiStarHead2.getFromJson(obj1);
				head2Datas.add(head2);
				//显示第二个头
				headerView2.setVisibility(View.VISIBLE);
			}
			// 解析头
			JSONArray active = object.getJSONArray("active");
			JSONObject obj = active.getJSONObject(0);
			headData = RuiStarHead.getFromJson(obj);
			//显示头
			headerView.setVisibility(View.VISIBLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 判断使用是初赛还是复赛
	private void chooseDataList(JSONArray starlist) throws JSONException {
		ArrayList<RuiStar> listStars3;
		// 下拉刷新 则让原来数据清空
		if (page_0 == FIRST && flag == 0) {
			listStars = new ArrayList<RuiStar>();
		} else if (page_1 == FIRST && flag == 1) {
			listStars2 = new ArrayList<RuiStar>();
		}
		// 判断是初赛还是复赛
		if (flag == 0) {
			listStars3 = listStars;
		} else {
			listStars3 = listStars2;
		}
		// 添加数据
		for (int i = 0; i < starlist.length(); i++) {
			JSONObject starObject = starlist.getJSONObject(i);
			RuiStar star = RuiStar.getFromJson(starObject);
			listStars3.add(star);
		}
	}

	// 上下滑动监听
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (isListEnd && scrollState == 2 && !isLoading) {// 代表到list底部了
			sentRequest();
		}
	}

	private boolean isListEnd;// 判断是否到list的底部了
	private View headerView;
	private View headerView2;

	public void onScroll(AbsListView view, int first, int count, int total) {
		if (first + count == total) {
			isListEnd = true;
		} else {
			isListEnd = false;
		}
	}
}
