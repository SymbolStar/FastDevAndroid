package cn.com.igdj.library.base;

import java.util.ArrayList;
import java.util.List;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import cn.com.igdj.library.R;


public abstract class BaseTabActivity extends BaseActivity {
	/**
	 * 是否使用tab上的指示条
	 */
	private boolean isUseIndicator = true;
	/**
	 * 指示条的颜色
	 */
	private String indicatorColor = "#5B1712";
	/**
	 * 指示条的背景颜色
	 */
	private String indicatorBackgroundColor = "#FF7119";
	/**
	 * 指示条
	 */
	private ImageView indicatorLine;
	/**
	 * 指示条的偏移量，像素为单位
	 */
	private int mOffset = 0;

	/**
	 * tabBar
	 */
	protected LinearLayout tabBar;

	/**
	 * tabBar和 indicatorLine的父view
	 */
	private LinearLayout tabBarContailer;
	/**
	 * 当前tab的序号
	 */
	protected int mCurrent = 0;

	private String tabItemBackgroundColorNormal = "#FFFFFF";

	private String tabItemBackgroundColorSelected = "#FF0000";

	private String tabTitleColorNormal = "#646464";

	private String tabTitleColorSelected = "#D20000";


	private int currentFragment;

	public static View viewNeedToAddBadge; //需要加角标的TextView（tab）

	private List<Fragment> fragments; //tab页面
	private List<TabBarItem> tabBarItems; //tab item
	private List<View> tabBarItemViews;



	public abstract List<Fragment> setTabFragments();

	public abstract List<TabBarItem> setTabBarItems();

	public abstract void customiseLayout();

	public abstract void setActionBar(int index);
	
	/**
	 * 检查登录状态
	 * @return
	 */
	public abstract boolean isUserLogin();
	
	/**
	 * 决定当前tab页面是否需要检查登录状态
	 * @param index
	 * @return
	 */
	public abstract boolean isNeedCheckUserLogin(int index);
	
	/**
	 * 需要检查登录且未登录的需要登录
	 */
	public abstract void goLogin();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_base_tab);
		customiseLayout();
		initView();
		try {
			addViews(setTabBarItems(),setTabFragments());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void initView() {
		indicatorLine = (ImageView) findViewById(R.id.tab_scroll_img);
		if (isUseIndicator) {
			indicatorLine.setVisibility(View.VISIBLE);
		} else {
			indicatorLine.setVisibility(View.GONE);
		}
		tabBar = (LinearLayout) findViewById(R.id.tab_bar);
		tabBarContailer = (LinearLayout) findViewById(R.id.tab);
	}

	private void addViews(List<TabBarItem> items, List<Fragment> fragments) throws Exception{
		if(items == null || items.size() == 0){
			throw new Exception(" tab items is null");
		} else if(fragments == null || fragments.size() == 0){
			throw new Exception(" fragment is null");
		} else if(items.size() != fragments.size()){
			throw new Exception("tab text length is not equals fragment list size");
		} else {
			tabBarItems = items;
			this.fragments = fragments;
			setIndicator();
			setTabItem();
			currentFragment = 0;
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			List<Fragment> oldFraments = getSupportFragmentManager().getFragments();
			if(oldFraments != null) {
				for(int i = 0 ; i < oldFraments.size(); i++) {
					transaction.remove(oldFraments.get(i));
				}
			}
            transaction.add(R.id.fragment_contaniner, fragments.get(0)).commit();
			setIndicatorPosition(0);
		}
	}

	private void setIndicator() {
		tabBarContailer.setBackgroundColor(Color.parseColor(indicatorBackgroundColor));
		indicatorLine.setBackgroundColor(Color.parseColor(indicatorColor));
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		//tab的个数
		int nums = tabBarItems.size();
//		LayoutParams params = (LayoutParams) new LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1);
		LayoutParams params = (LayoutParams) indicatorLine.getLayoutParams();
		//每个tab的宽度
		int bmpW = dm.widthPixels / nums;
		params.width = bmpW;
		indicatorLine.setLayoutParams(params);
		int screenW = dm.widthPixels;
		mOffset = (screenW / nums);
		Matrix matrix = new Matrix();
		matrix.postTranslate(mOffset, 0);
		indicatorLine.setImageMatrix(matrix);// 设置动画初始位置
		mCurrent = 0;
	}

	private void setIndicatorPosition(int position){
		Animation animation = new TranslateAnimation(mOffset * mCurrent, mOffset * position, 0, 0);
        mCurrent = position;
        animation.setFillAfter(true);
        animation.setDuration(300);
        indicatorLine.startAnimation(animation);
		for (int i = 0; i < tabBarItems.size(); i++) {
			FrameLayout tab = (FrameLayout) tabBar.getChildAt(i);
			LinearLayout tabItemView = (LinearLayout) tab.getChildAt(0);
			ImageView tabImage = (ImageView) tabItemView.getChildAt(0);
			TextView tabTitle = (TextView) tabItemView.getChildAt(1);
			if (i != position) {
				tabItemView.setBackgroundColor(Color.parseColor(tabItemBackgroundColorNormal));
				tabTitle.setTextColor(Color.parseColor(tabTitleColorNormal));
				tabImage.setSelected(false);
			} else {
				tabItemView.setBackgroundColor(Color.parseColor(tabItemBackgroundColorSelected));
				tabTitle.setTextColor(Color.parseColor(tabTitleColorSelected));
				tabImage.setSelected(true);
			}
		}
		setActionBar(mCurrent);
	}

	private void setTabItem(){
		if (tabBarItemViews == null) {
			tabBarItemViews = new ArrayList<View>();
		} else {
			tabBarItemViews.clear();
		}
		for (int i = 0; i < tabBarItems.size(); i++) {
			TabBarItem tabBarItem = tabBarItems.get(i);
			LayoutInflater inflater = LayoutInflater.from(this);
			final View tabBarItemView = inflater.inflate(R.layout.tab_bar_item, null);
			ImageView image = (ImageView) tabBarItemView.findViewById(R.id.tab_image);
			final TextView title = (TextView) tabBarItemView.findViewById(R.id.tab_title);

			image.setImageDrawable(tabBarItem.drawable);
			title.setText(tabBarItem.title);
			final int position = i;
			tabBarItemView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					setCurrentTab(position);
				}
			});

			LayoutParams params = new LayoutParams(0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1);
			tabBarItemView.setLayoutParams(params);

			FrameLayout frameLayout = new FrameLayout(this);
			frameLayout.setLayoutParams(params);
			FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,android.view.ViewGroup.LayoutParams.MATCH_PARENT);
			tabBarItemView.setLayoutParams(layoutParams);
			frameLayout.addView(tabBarItemView);
			if (i == 0) {
				frameLayout.setBackgroundColor(Color.parseColor(tabItemBackgroundColorSelected));
				title.setTextColor(Color.parseColor(tabTitleColorSelected));
			} else {
				frameLayout.setBackgroundColor(Color.parseColor(tabItemBackgroundColorNormal));
				title.setTextColor(Color.parseColor(tabItemBackgroundColorNormal));
			}
			tabBar.addView(frameLayout);
			tabBarItemViews.add(tabBarItemView);
			if (i == 1) {
				viewNeedToAddBadge = tabBarItemView;
			}
		}
	}
	
	public void setCurrentTab(int position) {
		if(position > fragments.size()) {
			position = 0;
		}
		if(position < 0) {
			position = 0;
		}
		if(isNeedCheckUserLogin(position)) {
			if(isUserLogin()) {
				
				switchContent(fragments.get(mCurrent),fragments.get(position));
				setIndicatorPosition(position);
			} else {
				switchContent(fragments.get(mCurrent),fragments.get(0));
				setIndicatorPosition(0);
				goLogin();
			}
		} else {
			switchContent(fragments.get(mCurrent),fragments.get(position));
			setIndicatorPosition(position);
		}
	}
	
	public void switchContent(Fragment from, Fragment to) {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		if (!to.isAdded()) { // 先判断是否被add过
			transaction.hide(from).add(R.id.fragment_contaniner,to).commit();
		} else {
			transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
		}
	}

    public static View getShopCartTextView() {
        return viewNeedToAddBadge;
    }

	/**
	 * 设置是否使用滑动指示条
	 * @param isUseIndicator
	 */
	public void setIsUseIndicator(boolean isUseIndicator) {
		this.isUseIndicator = isUseIndicator;
	}

	/**
	 * 设置滑动指示条的颜色
	 * @param indicatorColor
	 */
	public void setIndicatorColor(String indicatorColor) {
		this.indicatorColor = indicatorColor;
	}

	/**
	 * 设置滑动指示条的背景颜色
	 * @param indicatorBackgroundColor
	 */
	public void setIndicatorBackgroundColor(String indicatorBackgroundColor) {
		this.indicatorBackgroundColor = indicatorBackgroundColor;
	}


	/**
	 * 设置未选中时item的背景颜色
	 * @param tabItemBackgroundColorNormal
	 */
	public void setTabItemBackgroundColorNormal(String tabItemBackgroundColorNormal) {
		this.tabItemBackgroundColorNormal = tabItemBackgroundColorNormal;
	}

	/**
	 * 设置选中时item的背景颜色
	 * @param tabItemBackgroundColorSelected
	 */
	public void setTabItemBackgroundColorSelected(String tabItemBackgroundColorSelected) {
		this.tabItemBackgroundColorSelected = tabItemBackgroundColorSelected;
	}



	@Override
	public void onBackPressed() {
		//按回退时，先回到首页，如果在首页，则执行退出
		if (mCurrent != 0) {
			switchContent(fragments.get(mCurrent),fragments.get(0));
			setIndicatorPosition(0);
		} else {
			super.onBackPressed();
		}
	}

	public String getTabTitleColorNormal() {
		return tabTitleColorNormal;
	}

	public void setTabTitleColorNormal(String tabTitleColorNormal) {
		this.tabTitleColorNormal = tabTitleColorNormal;
	}

	public String getTabTitleColorSelected() {
		return tabTitleColorSelected;
	}

	public void setTabTitleColorSelected(String tabTitleColorSelected) {
		this.tabTitleColorSelected = tabTitleColorSelected;
	}

	public int getCurrentTabIndex() {
		return mCurrent;
	}
	
	
}
