package com.project.ruili.abs;

import java.util.List;

import com.project.ruili.beans.FashConFragItem;
import com.project.ruili.beans.FashFragPagerObj;


public interface GetJsonListener {
	void getPagerData(List<FashFragPagerObj> list);
	void getListData(List<FashConFragItem> list);
}
