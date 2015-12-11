package com.project.ruili.activitys;

import static com.android.volley.Request.Method.GET;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.project.ruili.R;
import com.project.ruili.adapters.PushAdapter;
import com.project.ruili.beans.Push;
import com.project.ruili.net.ApiHelp;
import com.project.ruili.net.HttpRequest;
import com.project.ruili.refalshlistview.PullToRefreshBase.OnLastItemVisibleListener;
import com.project.ruili.refalshlistview.PullToRefreshBase.OnRefreshListener;
import com.project.ruili.refalshlistview.PullToRefreshListView;

public class PushActivity extends Activity implements OnRefreshListener, OnLastItemVisibleListener, OnItemClickListener, OnClickListener {
	
	private View mView;
	private PullToRefreshListView mRefreshListview;
	//声明指向刷新组建的内部listview对象的引用
	private ListView mListview;
	private int mPage =1;
	
	private static final int PAGE_LIMIT = 20;
	
	private List<Push> mBeans;
	private PushAdapter mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_push);
		
		findViewById(R.id.push_message).setOnClickListener(this);
		
		mRefreshListview=(PullToRefreshListView) findViewById(R.id.push_lv);
		//设置下拉刷新可用
		mRefreshListview.setPullToRefreshEnabled(true);
		//设置下拉刷新的事件监听
		mRefreshListview.setOnRefreshListener(this);
		//设置当Listview的最后一个Itemview可见时的监听
		mRefreshListview.setOnLastItemVisibleListener(this);
		//获取RefreshListview里面的Listview对象
		mListview = mRefreshListview.getRefreshableView();
		mListview.setOnItemClickListener(this);
		mListview.setDividerHeight(10);
		initData();
	}
	
	
	private void initData() {
		//加载网络数据
		requestDataFromNetwork();		
	}
	
	private void requestDataFromNetwork() {

		//接受网络访问完成的结果
		Listener<String> listener = new Listener<String>() {

			@Override
			public void onResponse(String response) {
				System.out.println(response);
				List<Push> beans = parseData(response);
				//下拉刷新
				if (mPage == 1) {
					mBeans = beans;
					if(mAdapter==null){
						mAdapter = new PushAdapter(beans,PushActivity.this.getApplicationContext());
						mListview.setAdapter(mAdapter);
					}else {
						mAdapter.notifyDataSetChanged();
						
					}
				//加载更多
				}else if(mPage >1){
					mBeans.addAll(mBeans.size(),beans);
					mAdapter.notifyDataSetChanged();
				}
				//刷新动作完成
				mRefreshListview.onRefreshComplete();
			}
		};
		//接受访问网络异常的错误信息
		ErrorListener errorlis = new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				
			}
		};
		
		//新建一个Request对象
		StringRequest request = new StringRequest(
				ApiHelp.push+"?page="+mPage
				+"&rows="+PAGE_LIMIT , 
				listener, errorlis
				);
		
		HttpRequest.getInstance(PushActivity.this.getApplicationContext()).sendRequest(request);
		
		
	}


	protected List<Push> parseData(String response) {
		Log.d("TAG", "source=="+response);
		List<Push> beans = new ArrayList<Push>();
		//将String对象转化为JsonObject对象
		try {
			JSONObject json_obj = new JSONObject(response);
			JSONArray array = json_obj.getJSONArray("pushs");
			int len = array.length();
			//判断array里面的长度为0的话就不遍历
			if (len ==0)return beans;
			//遍历array并把jsonobject转化为Lorebean对象，然后添加到beans里面
			for (int i = 0; i < len; i++) {
				JSONObject obj = array.getJSONObject(i);
				Push bean = Push.initWithJsonObject(obj);
				beans.add(bean);
			}
			} catch (JSONException e) {
				e.printStackTrace();
				}
		return beans;
	}


	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Intent intent = new Intent(PushActivity.this,FashDetailsActivity.class);

		   //传递id到showActivity里面
		   intent.putExtra("aid", mBeans.get(position).id);
		   startActivity(intent);
		
	}
	
	/**
	 * 响应当listview的最后一个item可见的时候的行为(上拉加载)
	 */
	@Override
	public void onLastItemVisible() {
		mPage++;
		//加载网络数据
		requestDataFromNetwork();			
	}
	
	
	
	/**
	 * 响应下拉刷新的请求
	 */
	@Override
	public void onRefresh() {
		mPage =1;
		//加载网络数据
		requestDataFromNetwork();	
		System.out.println(mBeans.size());
	}


	@Override
	public void onClick(View arg0) {

		finish();
	}

}
