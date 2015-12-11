package com.project.ruili.abs;

import java.util.List;

import org.json.JSONArray;

import com.project.ruili.beans.FashDetailsHead;
import com.project.ruili.beans.FashDetailsView;



public interface GetDetailsDataListener {
	void getHeadData(FashDetailsHead head);
	void getUrlData(List<FashDetailsView> views);
	void getTextsData(JSONArray texts);
}
