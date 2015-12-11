package com.project.ruili.refalshlistview;


import android.content.Context;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;

public class PullToRefreshGridView extends PullToRefreshAdapterViewBase<GridView> {

//	private LoadingLayout headerLoadingView;
//	private LoadingLayout footerLoadingView;

	class InternalListView extends GridView implements EmptyViewMethodAccessor {

		public InternalListView(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		@Override
		public void setEmptyView(View emptyView) {
			PullToRefreshGridView.this.setEmptyView(emptyView);
		}
		
		@Override
		public void setEmptyViewInternal(View emptyView) {
			super.setEmptyView(emptyView);
		}

		public ContextMenuInfo getContextMenuInfo() {
			return super.getContextMenuInfo();
		}
	}

	public PullToRefreshGridView(Context context) {
		super(context);
		this.setDisableScrollingWhileRefreshing(false);
	}
	
	public PullToRefreshGridView(Context context, int mode) {
		super(context, mode);
		this.setDisableScrollingWhileRefreshing(false);
	}

	public PullToRefreshGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setDisableScrollingWhileRefreshing(false);
	}

	@Override
	public ContextMenuInfo getContextMenuInfo() {
		return ((InternalListView) getRefreshableView()).getContextMenuInfo();
	}


	@Override
	protected final GridView createRefreshableView(Context context, AttributeSet attrs) {
		GridView lv = new InternalListView(context, attrs);

		lv.setId(android.R.id.list);
		return lv;
	}


}
