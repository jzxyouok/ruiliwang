package com.project.ruili.activitys;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.ruili.R;
import com.project.ruili.adapters.RuiStarDetailGridAdapter;
import com.project.ruili.beans.RuiStarDetail;
import com.project.ruili.beans.RuiStarDetailHead;
import com.project.ruili.net.ApiHelp;
import com.project.ruili.net.HttpRequest;
import com.project.ruili.utils.ToasUtils;
import com.pubu.gridview.StaggeredGridView;
import com.android.volley.Response.Listener;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RuiStarDetailActivity extends Activity implements 
		ErrorListener, Listener<String>, OnItemClickListener, OnRefreshListener, OnClickListener {

	private StaggeredGridView gridView;
	private SwipeRefreshLayout reflashView;
	private HttpRequest request;
	private int page = 1;
	private String aid;
	private RuiStarDetailHead head = new RuiStarDetailHead();
	private ArrayList<RuiStarDetail> photos = new ArrayList<RuiStarDetail>();
	private TextView headName;
	private TextView headTime;
	private TextView headSummary;
	private ImageView icon;
	private Button votes;
	private RuiStarDetailGridAdapter adapter;
	private StringRequest stringRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ruistardetail);
		findViewById(R.id.iv_ruistar_back).setOnClickListener(this);
		// 获得aid
		aid = getIntent().getStringExtra("aid");
		// 获得网络请求对象
		request = HttpRequest.getInstance(this);
		// 发送请求
		sendRequest();
		// 找到刷新控件
		reflashView = (SwipeRefreshLayout) findViewById(R.id.prfv_stardetail);
		//设置下拉监听
		reflashView.setOnRefreshListener(this);
		// 设置刷新控件属性
		// 设置下拉圆圈上的颜色，蓝色、绿色、橙色、红色
		reflashView.setColorSchemeResources(android.R.color.holo_green_light,
				android.R.color.holo_blue_light,
				android.R.color.holo_orange_light,android.R.color.holo_purple);
		/*
		 * // 设置下拉监听 reflashView.setOnResetUI(this);
		 */
		gridView = (StaggeredGridView) findViewById(R.id.gv_stardetail);
		// 设置item点击监听
		gridView.setOnItemClickListener(this);
		// 解析头部控件
		View headView = View.inflate(this, R.layout.ruistar_detail_head, null);
		// 给griView设置头部控件
		gridView.addHeaderView(headView);
		votes = (Button) headView.findViewById(R.id.bt_ruistar_detail_vols);
		icon = (ImageView) headView.findViewById(R.id.iv_ruistar_detail_icon);
		headName = (TextView) headView
				.findViewById(R.id.tv_ruistar_detail_name);
		headTime = (TextView) headView
				.findViewById(R.id.tv_ruistar_detail_time);
		headSummary = (TextView) headView
				.findViewById(R.id.tv_ruistar_detail_summary);
		adapter = new RuiStarDetailGridAdapter(request);
		gridView.setAdapter(adapter);
	}

	// 发送网络请求
	private void sendRequest() {
		page++;
		stringRequest = new StringRequest(Method.GET,
				ApiHelp.getRuiStarDetailURL(page, aid), this, this);
		request.sendRequest(stringRequest);
	}

/*	*//**
	 * 下拉刷新
	 *//*
	@Override
	public void onReflash(MyPullReflashView view) {
		page = 0;
		sendRequest();
	}*/

	// 网络正确回调
	@Override
	public void onResponse(String response) {
		// 获取json数据
		reflashView.setRefreshing(false);
		getFromJson(response);
		if (head.username == null) {
			return;
		}
		// 设置头部数据
		setHeadData();
	}

	// 设置头部数据
	private void setHeadData() {
		// 设置头像信息
		setImage();
		votes.setText("总票数:" + head.votenum + "票");
		headName.setText(head.username);
		headTime.setText("共" + head.photonum + "张照片| " + head.createdate);
		headSummary.setText(head.desc);
	}

	// 设置头像信息
	private void setImage() {
		// 绑定tag
		icon.setTag(head.userphoto);
		// 获得图片加载回调接口
		ImageListener lis = ImageLoader.getImageListener(icon,
				R.drawable.ic_nickname, R.drawable.ic_nickname);
		// 加载图片
		request.loadImage(head.userphoto, lis, 0, 0);
	}

	// 获取json数据
	private void getFromJson(String response) {
		try {
			// 解析json
			if (response == null) {
				ToasUtils.showLToast(this, "服务器错误");
				return;
			}
			JSONObject object = new JSONObject(response);
			JSONObject result = object.getJSONObject("result");
			if (!result.getString("message").equals("成功")) {
				ToasUtils.showLToast(this, "服务器错误");
				return;
			}
			// 获得照片数据
			getPhotosDatas(object);
			// 给适配器设置数据
			adapter.setdatas(photos);
			// 获得View头的数据
			getHeadData(object);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	// 网络错误回调
	@Override
	public void onErrorResponse(VolleyError error) {
		/*
		 * // 隐藏刷新头 reflashView.reSetUi();
		 */
		reflashView.setRefreshing(false);
		ToasUtils.showLToast(this, "网络错误");
	}

	// 获得照片数据
	private void getPhotosDatas(JSONObject object) throws JSONException {
		JSONArray piclist = object.getJSONArray("piclist");
		if (page == 1) {
			photos = new ArrayList<RuiStarDetail>();
		}
		for (int i = 0; i < piclist.length(); i++) {
			JSONObject photo = piclist.getJSONObject(i);
			RuiStarDetail detail = new RuiStarDetail();
			detail.bigimg = photo.getString("bigimg");
			detail.create_time = photo.getString("create_time");
			detail.img = photo.getString("img");
			detail.shareid = photo.getString("shareid");
			detail.height = photo.getInt("height");
			detail.width = photo.getInt("width");
			photos.add(detail);
		}
	}

	// 获得View头数据
	private void getHeadData(JSONObject object) throws JSONException {
		JSONObject album = object.getJSONObject("album");
		head.createdate = album.getString("createdate");
		head.desc = album.getString("desc");
		head.photonum = album.getString("photonum");
		head.uid = album.getString("uid");
		head.username = album.getString("username");
		head.userphoto = album.getString("userphoto");
		head.votenum = album.getString("votenum");
	}

	// 点击item
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		ToasUtils.showLToast(this, "点击了" + arg2);
		// 获得所有的大图url
		ArrayList<String> urls = new ArrayList<String>();
		for (RuiStarDetail data : photos) {
			urls.add(data.bigimg);
		}
		Intent intent = new Intent(this, PhotoShowActivity.class);
		// 将所有要显示的大图url传递过去
		intent.putExtra("urls", urls);
		// 传递点击的位置
		intent.putExtra("position", arg2 - 1);
		startActivity(intent);
	}

	//下拉刷新
	@Override
	public void onRefresh() {
		page = 0;
		sendRequest();
	}

	@Override
	public void onClick(View v) {
		finish();
	}
}
