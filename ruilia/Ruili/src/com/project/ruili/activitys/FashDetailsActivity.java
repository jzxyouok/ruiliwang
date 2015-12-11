package com.project.ruili.activitys;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.ruili.R;
import com.project.ruili.abs.GetDetailsDataListener;
import com.project.ruili.adapters.FashDetailsAdapter;
import com.project.ruili.beans.FashDetailsHead;
import com.project.ruili.beans.FashDetailsView;
import com.project.ruili.db.OpenDBhelp;
import com.project.ruili.net.ApiHelp;
import com.project.ruili.net.FashToGetJson;

public class FashDetailsActivity extends Activity implements
		OnItemClickListener, OnClickListener {
	private LinearLayout hearderViewLayout;
	private TextView introView;
	private ListView listView;
	// private PersonMessageShowAdapter mAdapter;
	private TextView pubtimeView;
	private TextView sourceView;
	private TextView titleView;
	private int aid;
	private FashDetailsAdapter mAdapter;
	private List<FashDetailsView> photos;
	private OpenDBhelp mDBhelp;
	private ImageView mBackImageView;
	private ImageView mCollectImageView;
	boolean isCollect = false;
	private String detailUrl;
	private String MfisrtImageUrl;
	private String title;
	private String summary;

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fash_details_activity);
		aid = getIntent().getIntExtra("aid", -1);
		mDBhelp = OpenDBhelp.getDBhelp(getApplication());
		hearderViewLayout = ((LinearLayout) LayoutInflater.from(
				getApplicationContext()).inflate(R.layout.fash_details_title,
				null));
		titleView = ((TextView) hearderViewLayout
				.findViewById(R.id.activity_person_message_show_title));
		pubtimeView = ((TextView) hearderViewLayout
				.findViewById(R.id.activity_person_message_show_pubtime));
		sourceView = ((TextView) hearderViewLayout
				.findViewById(R.id.activity_person_message_show_source));
		introView = ((TextView) hearderViewLayout
				.findViewById(R.id.activity_person_message_show_intro));
		mBackImageView = (ImageView) findViewById(R.id.fash_details_collect_back_iv);
		mCollectImageView = (ImageView) findViewById(R.id.fash_details_coleect_iv);
		mBackImageView.setOnClickListener(this);
		mCollectImageView.setOnClickListener(this);
		isCollect = mDBhelp.isExistDatas(ApiHelp.FASH_DETAILS_PATH + aid);
		
		// TODO
		if (isCollect) {
			mCollectImageView.setImageResource(R.drawable.collention_selected);

		} else {
			mCollectImageView.setImageResource(R.drawable.collection_defalt);
		}

		listView = (ListView) findViewById(R.id.activity_person_message_show_lv);
		listView.addHeaderView(hearderViewLayout);
		mAdapter = new FashDetailsAdapter(getApplication());
		listView.setDivider(null);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(this);
		init();
	}

	public void init() {
		detailUrl = ApiHelp.FASH_DETAILS_PATH + aid;
		FashToGetJson.getFashDetailsData(detailUrl, getApplicationContext(),
				new getDetailsJsonData());
	}

	private class getDetailsJsonData implements GetDetailsDataListener {

		@Override
		public void getHeadData(FashDetailsHead head) { // 头部数据回调
			title = head.title;
			summary = head.source;
			titleView.setText(head.title);
			sourceView.setText(head.source);
			pubtimeView.setText(head.pubtime);
			introView.setText(head.intro);
		}

		@Override
		public void getUrlData(List<FashDetailsView> views) { // 图片数据回调
			photos = views;
			MfisrtImageUrl = views.get(0).url;
			mAdapter.addViews(views);
		}

		@Override
		public void getTextsData(JSONArray texts) { // 文字说明数据回调
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < texts.length(); i++) {
				try {
					list.add(texts.getString(i));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.d("TAG", "亲，json数据异常了");
				}
			}

			mAdapter.addTexts(list);

		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// 获得所有的大图url
		ArrayList<String> urls = new ArrayList<String>();
		for (FashDetailsView v : photos) {
			urls.add(v.url);
		}
		Intent intent = new Intent(this, PhotoShowActivity.class);
		// 将所有要显示的大图url传递过去
		intent.putExtra("urls", urls);
		// 传递点击的位置
		intent.putExtra("position", position - 1);
		startActivity(intent);

	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.fash_details_coleect_iv) {
			if (isCollect) {
				Toast.makeText(getApplication(), "亲，已经收藏过了哦",
						Toast.LENGTH_SHORT).show();
				mCollectImageView
						.setImageResource(R.drawable.collection_defalt);
			} else {
				mDBhelp.inserMesData(detailUrl, MfisrtImageUrl, title, summary,
						1);
				mCollectImageView
						.setImageResource(R.drawable.collention_selected);
				Toast.makeText(getApplication(), "亲，收藏好了哦", Toast.LENGTH_SHORT)
						.show();
			}
		} else if (v.getId() == R.id.fash_details_collect_back_iv) {
			finish();
		}

	}

}
