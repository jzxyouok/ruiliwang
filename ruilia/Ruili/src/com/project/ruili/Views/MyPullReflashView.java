package com.project.ruili.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;

/**
 * 布局时PullReflashView的第一个子View必须是ABSListview,并且必须要一个
 * 
 * @author Administrator
 * 
 */
public class MyPullReflashView extends ViewGroup {

	private AbsListView listView;
	private LinearLayout headLayout;
	private int downY;
	private int type;
	private Scroller scroller;
	private Refalash onReflash;// 回调接口
	private int priDy;
	private TextView textView;
	private boolean isReflash;

	public MyPullReflashView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public MyPullReflashView(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * 回调接口用来还原刷新头
	 */

	public interface Refalash {
		public void onReflash(MyPullReflashView view);
	}

	/**
	 * 设置回掉接口
	 */

	public void setOnResetUI(Refalash reFlash) {
		this.onReflash = reFlash;
	}

	/**
	 * 动画计算方式,每次invalidate都会调用
	 * 
	 * @param context
	 */

	@Override
	public void computeScroll() {
		// 判断是否完成动画
		if (scroller.computeScrollOffset()) {
			// 获得下一个坐标
			int y = scroller.getCurrY();
			// 在此滚动到下一个坐标
			scrollTo(0, y);
			// 请求刷新
			invalidate();
		}
	}

	private void initView(Context context) {
		scroller = new Scroller(context, new AccelerateDecelerateInterpolator());
		LayoutParams paLayoutParams = new LayoutParams(
				LayoutParams.MATCH_PARENT, 30);
		headLayout = new LinearLayout(context);
		headLayout.setLayoutParams(paLayoutParams);
		headLayout.setBackgroundColor(0xffffffff);
		// 设置子控件剧中
		headLayout.setGravity(Gravity.CENTER);
		LayoutParams params = new LayoutParams(15, 15);
		// 下拉进度控件
		ProgressBar bar = new ProgressBar(context);
		bar.setLayoutParams(params);
		textView = new TextView(context);
		textView.setText("下拉刷新");
		textView.setTextSize(15f);
		textView.setPadding(0, 10, 0, 10);
		headLayout.addView(bar);
		headLayout.addView(textView);
		addView(headLayout);
	}

	/**
	 * 设置ListView
	 */
	public void setABSlistView(AbsListView view) {
		this.listView = view;
		// 必须重新测量布局
		requestLayout();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 测量刷新头的大小,高度固定为25;
		headLayout.measure(widthMeasureSpec, 30);
		// System.out.println("headLayout="+headLayout.getMeasuredHeight());
		// 测量ListView
		// 判断listview是否存在
		if (listView == null) {
			// 从布局获得listview
			View chiView = getChildAt(1);
			// 判断是否为listView
			if (chiView instanceof AbsListView) {
				listView = (AbsListView) chiView;
			} else {
				throw new IllegalArgumentException("刷新布局下第一个子布局必须为AbsListView");
			}
		}
		if (listView == null) {
			throw new IllegalArgumentException("缺少AbsListView");
		}
		// 测量ListView,为副布局的长跟宽
		listView.measure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// 布局刷新头,放在父布局正上方
		headLayout.layout(l, -headLayout.getMeasuredHeight(), r, 0);
		// 布局listview,跟父布局一样
		listView.layout(l, t - getPHeight()/11, r, b);
		
	}

	//获取屏幕高度
	private int getPHeight() {
		WindowManager wm = (WindowManager) getContext().getSystemService(
				Context.WINDOW_SERVICE);

		int pHeight = wm.getDefaultDisplay().getHeight();
		return pHeight;
	}

	/**
	 * 事件分发,如果在分发事件中处理了事件 则事件不再往下分发! 就算调用super.dispatchTouchEvent(ev);也没用
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// 将用户操作置0;
		type = 0;
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 获得按下的x坐标,并把事件分发下去
			isReflash = false;
			downY = (int) ev.getY();
			priDy = downY;
			super.dispatchTouchEvent(ev);
			break;
		case MotionEvent.ACTION_MOVE:
			// 先判断用户操作状态
			getUserType(ev);

			if (type == 0) {
				return true;
			}
			if (type == -1) {// 上推,直接分发给listview
				super.dispatchTouchEvent(ev);
			}
			if (type == 1) {// 下拉,判断状态是否拉出刷新头,如果listview第一个为0,并且第一个item的上边框位置为0,则拉出刷新头
				if (listView.getFirstVisiblePosition() == 0
						&& listView.getChildAt(0).getTop() == 0) {
					isReflash = true;
					int mY = (int) ev.getY();
					int dy = downY - mY;
					// 如果拉出刷新头 则减小下拉比例
					if (mY - priDy > headLayout.getHeight()) {
						dy = dy / 5;
					}
					scrollBy(0, dy);
					downY = mY;
				} else {
					super.dispatchTouchEvent(ev);
				}
			}
			break;
		case MotionEvent.ACTION_UP:// listView listView的飞滚是UP事件实现的.
			// 判断刷新头的拉出状态
			int scY = getScrollY();// 表示画布往上拉的像素值
			if (scY <= -headLayout.getMeasuredHeight()) {// 说明刷新头已经拉出
				scroller.startScroll(0, scY, 0,
						-scY - headLayout.getMeasuredHeight(), 300);
				textView.setText("正在刷新...");
				// 回调开始下载
				onReflash.onReflash(this);
			} else {// 头没有被全部拉出
				scroller.startScroll(0, scY, 0, -scY, 300);
			}
			// 刷新界面
			invalidate();
			// 如果用户下拉 则不触发其他事件
			if (isReflash || (ev.getY() - priDy > 10 && isReflash)) {
				break;
			}
			super.dispatchTouchEvent(ev);
			break;
		}
		return true;
	}

	private void getUserType(MotionEvent ev) {
		// 获得当前的Y
		int curY = (int) ev.getY();
		if (Math.abs(downY - curY) < 10) {// 人为用户没有上啦或者下拉
			type = 0;
		}

		if (downY - curY >= 10) {// 用户上推
			type = -1;
		}

		if (downY - curY <= -10) {
			type = 1;
		}

	}

	/**
	 * 还原刷新头的方法
	 */

	public void reSetUi() {
		scroller.startScroll(0, getScrollY(), 0, -getScrollY(), 300);
		textView.setText("下拉刷新...");
	}
}
