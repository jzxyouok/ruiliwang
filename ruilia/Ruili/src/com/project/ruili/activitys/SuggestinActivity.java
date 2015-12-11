package com.project.ruili.activitys;

import com.project.ruili.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class SuggestinActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_suggestion);
	}
	public void getBack(View v){
		  Intent intent = new Intent();  
	      intent.setClass(SuggestinActivity.this, PersonSettingActivity.class);  
	      startActivity(intent);  
	      SuggestinActivity.this.finish();
	}
}
