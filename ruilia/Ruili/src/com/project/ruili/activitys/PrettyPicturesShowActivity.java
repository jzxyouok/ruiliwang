package com.project.ruili.activitys;



import com.project.ruili.R;
import com.project.ruili.adapters.PrettyPicturesShowAdapter;
import com.project.ruili.db.OpenDBhelp;
import com.project.ruili.fragments.PictureFragmen;
import com.project.ruili.fragments.PrettyPicturesImgFragment.MyListener;
import com.project.ruili.net.ApiHelp;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;



public class PrettyPicturesShowActivity extends FragmentActivity implements MyListener, OnPageChangeListener, OnClickListener {
	public static MyHandler myHandler;
	private int aid;
	private String tittle;
	private String logo;
	private ViewPager mViewPager;
	private ProgressDialog progressDialog;
	private TextView mmTextView;
	private PrettyPicturesShowAdapter picturesShowAdapter;
	private String first;
	private ImageView mmImageView;
	private OpenDBhelp mBhelp;
	private static final int ISPRETTYPICTURES =2;

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_pretty_pictures_show);

		mBhelp=OpenDBhelp.getDBhelp(this);
		aid = getIntent().getIntExtra("aid", -1);
		tittle=getIntent().getStringExtra("tittle");
		logo=getIntent().getStringExtra("logo");
		System.out.println(
				"---------------,aid="+aid+	
				",tittle="+tittle+	
				",logo="+logo+
				"---------------"
				
				);
		mmImageView=(ImageView) findViewById(R.id.activity_pretty_pictures_show_collect_iv);
		
		boolean isCollect= mBhelp.isExistDatas(ApiHelp.PRETTY_SHOW + aid);
		if (isCollect) {
			mmImageView.setImageResource(R.drawable.collention_selected);
			
		}else{
			mmImageView.setImageResource(R.drawable.collection_defalt);
		}
		
	
		
		
		 showProgressDialog("", "正在加载请稍后");
		 myHandler = new MyHandler();
		picturesShowAdapter = new PrettyPicturesShowAdapter(
				getSupportFragmentManager(), aid);
		mmTextView=(TextView) findViewById(R.id.activity_pretty_pictures_show_tv);
		
		mmImageView.setOnClickListener(this);
		mViewPager = ((ViewPager) findViewById(R.id.activity_pretty_pictures_show_vp));
		mViewPager.setAdapter(picturesShowAdapter);
		mViewPager.setOnPageChangeListener(this);
	}
	public void callBackFunction(String num,String g){
		mmTextView.setText(num);
		first=g;
	}
	public void showProgressDialog(String paramString1, String paramString2) {
		if (progressDialog == null) {
			progressDialog = ProgressDialog.show(this, paramString1, paramString2, true, false);
			progressDialog.show();
		}
	}

	public class MyHandler extends Handler {

		public void handleMessage(Message paramMessage) {
			super.handleMessage(paramMessage);
			if (paramMessage.getData().getString("color").equals("OK"))
				if ((progressDialog != null) && (progressDialog.isShowing()))
					progressDialog.dismiss();
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onPageSelected(int arg0) {
		if (arg0==0) {
			mmTextView.setText("");	
		}else if(arg0==1) {
			mmTextView.setText(first);
		}
	
	}

	public void getBack(View v){

	finish();
	}
	
	boolean isCollect=false;
	boolean isfirst;
	@Override
	public void onClick(View arg0) {
		if (isCollect) {
			//是收藏 变为不收藏 数据库应删除
			isCollect=false;
			mBhelp.delDatas(ApiHelp.PRETTY_SHOW + aid);	
			mmImageView.setImageResource(R.drawable.collection_defalt);
		}else{
			isCollect=true;
			mmImageView.setImageResource(R.drawable.collention_selected);
			mBhelp.inserMesData(ApiHelp.PRETTY_SHOW + aid, logo, tittle, ISPRETTYPICTURES );
		}
		
	}
	
}

