package com.project.ruili.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.project.ruili.R;
import com.project.ruili.activitys.FashDetailsActivity;
import com.project.ruili.beans.FashFragPagerObj;
import com.project.ruili.net.HttpRequest;

public class FashConPagerFrag extends Fragment implements OnClickListener {
	private FashFragPagerObj obj;
	private int id;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fash_con_pager_frag, null);
		ImageView imageView = (ImageView) view
				.findViewById(R.id.fash_con_pager_frag_iv);
		TextView textView = (TextView) view
				.findViewById(R.id.fash_con_pager_farg_tv);
		imageView.setOnClickListener(this);
		setView(imageView, textView);
		return view;
	}

	private void setView(ImageView imageView, TextView textView) {
		textView.setText(obj.title);

		imageView.setTag(obj.img);
		ImageListener listener = ImageLoader.getImageListener(imageView,
				R.drawable.default_image, R.drawable.default_image);
		HttpRequest.getInstance(getActivity()).loadImage(obj.img, listener, 0,0);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle bundle = getArguments();
		id = bundle.getInt("id");
		obj = (FashFragPagerObj) bundle.getSerializable("obj");

	}
	@Override
	public void onClick(View v) {
		int aid=obj.aid;
		Log.d("TAG", "aid==="+aid);
		Intent intent=new Intent(getActivity(), FashDetailsActivity.class);
		intent.putExtra("aid", aid);
		startActivity(intent);
	}


}
