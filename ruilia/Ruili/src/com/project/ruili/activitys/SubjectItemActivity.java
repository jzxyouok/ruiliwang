package com.project.ruili.activitys;

import static com.android.volley.Request.Method.GET;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.ruili.R;
import com.project.ruili.adapters.SubjectItemAdapter;
import com.project.ruili.beans.SbjItemHeader;
import com.project.ruili.beans.SbjItem_Item;
import com.project.ruili.db.DBmessage;
import com.project.ruili.db.OpenDBhelp;
import com.project.ruili.net.ApiHelp;
import com.project.ruili.net.HttpRequest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SubjectItemActivity extends Activity implements
		OnItemClickListener, OnClickListener {

	private SbjItemHeader mHeader;
	private View mView;
	private ImageView mImage;
	private HttpRequest mHttpRequest;
	private int mId;
	private ListView mListview;
	private SubjectItemAdapter mAdapter;
	private TextView title;
	private TextView sourse;
	private TextView pumtime;
	private TextView intro;

	private List<SbjItem_Item> mBeans;
	private boolean isCollect;
	private ImageView mmImageView;
	private OpenDBhelp mBhelp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_subjectitem);

		mId = getIntent().getIntExtra("lid", -1);
		mView = getLayoutInflater().inflate(R.layout.sbjitem_header, null);

		findViewById(R.id.subject_message_back).setOnClickListener(this);
		findViewById(R.id.activity_pretty_pictures_show_collect_iv)
				.setOnClickListener(this);

		initView();
		mListview = (ListView) findViewById(R.id.sbjitem_lv);
		mListview.addHeaderView(mView);
		mListview.setOnItemClickListener(this);
		mmImageView = (ImageView) findViewById(R.id.activity_pretty_pictures_show_collect_iv);

		mBhelp = OpenDBhelp.getDBhelp(this);
		isCollect = mBhelp.isExistDatas(ApiHelp.sbjItem + "&id=" + mId);
		if (isCollect) {
			mmImageView.setImageResource(R.drawable.collention_selected);

		} else {
			mmImageView.setImageResource(R.drawable.collection_defalt);
		}
		initData();
	}

	// public boolean onCreateOptionsMenu(Menu menu) {
	//
	// getMenuInflater().inflate(R.menu.menu_show, menu);
	//
	// return true;
	// }

	private void initView() {

		title = (TextView) mView.findViewById(R.id.sbjitem_title_tv);
		sourse = (TextView) mView.findViewById(R.id.sourse);
		pumtime = (TextView) mView.findViewById(R.id.sbjitem_time);
		intro = (TextView) mView.findViewById(R.id.intro);
		mImage = (ImageView) mView.findViewById(R.id.sbjitem_img);

	}

	private void initHeader() {
		if (mHeader == null)
			return;
		title.setText(mHeader.title);
		sourse.setText(mHeader.source);
		pumtime.setText(mHeader.pubtime);
		intro.setText(mHeader.intro);

		String url = mHeader.img;
		mImage.setTag(url);
		ImageListener lis = ImageLoader.getImageListener(mImage,
				R.drawable.default_topic_item,
				R.drawable.abc_tab_selected_pressed_holo);
		mHttpRequest = HttpRequest.getInstance(SubjectItemActivity.this);
		mHttpRequest.loadImage(url, lis, 0, 0);
	}

	private void initData() {

		// 加载网络数据
		// requestDataFromNetworkHeader();

		requestDataFromNetworkItem();
	}

	private void requestDataFromNetworkItem() {

		Listener<String> listener = new Listener<String>() {

			@Override
			public void onResponse(String response) {
				// System.out.println(response);
				List<SbjItem_Item> beans = parseDataItem(response);
				mHeader = parseDataHeader(response);
				initHeader();
				//System.out.println("--aaa-------"+ beans);
				mBeans = beans;
				mAdapter = new SubjectItemAdapter(beans,
						SubjectItemActivity.this.getApplicationContext());
				mListview.setAdapter(mAdapter);
			}
		};
		// 接受访问网络异常的错误信息
		ErrorListener errorlis = new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

			}
		};

		// 新建一个Request对象
		StringRequest request = new StringRequest(GET, ApiHelp.sbjItem + "&id="
				+ mId, listener, errorlis);
		HttpRequest.getInstance(
				SubjectItemActivity.this.getApplicationContext()).sendRequest(
				request);
	}

	// private void requestDataFromNetworkHeader() {
	//
	// Listener<String> listener=new Listener<String>() {
	//
	// @Override
	// public void onResponse(String response) {
	// mHeader=parseDataHeader(response);
	// System.out.println("_------------------------_______________________"+mHeader.toString());
	// initView();
	//
	// }
	// };
	//
	// ErrorListener errorlis = new ErrorListener() {
	//
	// @Override
	// public void onErrorResponse(VolleyError error) {
	//
	// }
	// };
	// StringRequest request = new StringRequest(
	// GET,ApiHelp.sbjItem+"&id="+mId,
	// listener, errorlis
	// );
	// System.out.println("-----------------------------------------------------"+ApiHelp.sbjItem+"&id="+mId);
	// HttpRequest.getInstance(SubjectItemActivity.this.getApplicationContext()).sendReqeust(request);
	// }

	protected List<SbjItem_Item> parseDataItem(String source) {
		List<SbjItem_Item> beans = new ArrayList<SbjItem_Item>();
		try {
			// 将String对象转化为JsonObject对象
			JSONObject json_obj = new JSONObject(source);
			JSONObject topi = json_obj.getJSONObject("topic");
			// 得到jsonobject里面的jsonArray
			JSONArray array = topi.getJSONArray("part");
			JSONObject json_item = array.getJSONObject(0);
			JSONArray arrayitem = json_item.getJSONArray("articles");
			int len = arrayitem.length();

			// 判断array里面的长度为0的话就不遍历
			if (len == 0)
				return beans;
			// 遍历array并把jsonobject转化为Lorebean对象，然后添加到beans里面
			for (int i = 0; i < len; i++) {
				JSONObject obj = arrayitem.getJSONObject(i);
				SbjItem_Item bean = SbjItem_Item.initWithJsonObject(obj);
				System.out
						.println("_----------------------------changdu-------------------"
								+ bean);
				beans.add(bean);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return beans;
	}

	private SbjItemHeader parseDataHeader(String source) {
		SbjItemHeader header = new SbjItemHeader();
		try {
			// 将String对象转化为JsonObject对象
			JSONObject json_obj = new JSONObject(source);
			// 得到jsonobject里面的jsonArray
			JSONObject topi = json_obj.getJSONObject("topic");

			header = SbjItemHeader.initWithJsonObject(topi);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return header;

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		Intent intent = new Intent(SubjectItemActivity.this,
				FashDetailsActivity.class);

		// 传递id到showActivity里面
		intent.putExtra("aid", mBeans.get(position).aid);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.activity_pretty_pictures_show_collect_iv:
			if (isCollect) {
				// 是收藏 变为不收藏 数据库应删除
				isCollect = false;
				// TODO .........
				mBhelp.delDatas(ApiHelp.sbjItem + "&id=" + mId);
				mmImageView.setImageResource(R.drawable.collection_defalt);
			} else {
				isCollect = true;
				mmImageView.setImageResource(R.drawable.collention_selected);
				mBhelp.inserMesData(ApiHelp.sbjItem + "&id=" + mId,
						mHeader.img, mHeader.title, 3);
			}
			break;
		case R.id.subject_message_back:
			finish();
			break;
		}
	}
}
