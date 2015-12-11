package com.project.ruili.fragments;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.project.ruili.R;
import com.project.ruili.activitys.PrettyPicturesShowActivity;
import com.project.ruili.beans.PrettyPicturesText;
import com.project.ruili.net.ApiHelp;
import com.project.ruili.net.HttpRequest;

public class PrettyPicturesTextFragment extends Fragment {
	private int aid;
	private Boolean isOK = false;
	private PrettyPicturesText mPicturesText;
	private HttpRequest mRequest;
	private ProgressDialog progressDialog;

	public View onCreateView(LayoutInflater paramLayoutInflater,
			@Nullable ViewGroup paramViewGroup, @Nullable Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(
				R.layout.frag_pretty_pictures_show_text, null);
		requestDataFromNetwork(localView);
		return localView;
	}

	public void requestDataFromNetwork(final View v) {
		aid = getArguments().getInt("aid");
		Listener<String> lis = new Listener<String>() {

			@Override
			public void onResponse(String response) {

				try {
					JSONObject localJSONObject = new JSONObject(response)
							.getJSONObject("data");
					mPicturesText = PrettyPicturesText
							.initWithJsonObject(localJSONObject);
					System.out.println("mPicturesText ="
							+ mPicturesText.toString());
					Message message = new Message();
					Bundle bundle = new Bundle();
					bundle.putString("color", "OK");
					message.setData(bundle);
					PrettyPicturesShowActivity.myHandler.sendMessage(message);
					TextView title = (TextView) v
							.findViewById(R.id.frag_pretty_pictures_show_text_title);
					TextView source = (TextView) v
							.findViewById(R.id.frag_pretty_pictures_show_text_source);
					TextView pubtime = (TextView) v
							.findViewById(R.id.frag_pretty_pictures_show_text_pubtime);
					TextView intro = (TextView) v
							.findViewById(R.id.frag_pretty_pictures_show_text_intro);
					title.setText(mPicturesText.title.toString());
					source.setText(mPicturesText.source.toString());
					pubtime.setText(mPicturesText.pubtime.toString());
					intro.setText(mPicturesText.intro.toString());

				} catch (JSONException e) {

					e.printStackTrace();
				}

			}
		};
		ErrorListener err = new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {

			}
		};

		StringRequest localStringRequest = new StringRequest(Method.GET,
				ApiHelp.PRETTY_SHOW + aid, lis, err);
		HttpRequest.getInstance(getActivity().getApplicationContext())
				.sendRequest(localStringRequest);
		isOK = true;
	}
	

}
