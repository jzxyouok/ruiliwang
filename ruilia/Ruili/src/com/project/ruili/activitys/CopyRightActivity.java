package com.project.ruili.activitys;

import com.project.ruili.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class CopyRightActivity extends Activity
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.acitivity_copyright);
  }
  public void getBack(View v){
	  Intent intent = new Intent();  
      intent.setClass(CopyRightActivity.this, PersonSettingActivity.class);  
      startActivity(intent);  
      CopyRightActivity.this.finish();   
  }
}

	