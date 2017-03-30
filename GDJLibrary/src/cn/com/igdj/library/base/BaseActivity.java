package cn.com.igdj.library.base;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import cn.com.igdj.library.R;

public abstract class BaseActivity extends ActionBarActivity {
	protected ActionBar myActionBar = null;
	
	protected TextView titleText = null;

	protected View mCustomView = null;

	protected Button rightButton = null;

	protected TextView rightText = null;

	protected abstract void initView();

	protected abstract void initData();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		myActionBar = getSupportActionBar();
		if (myActionBar != null) {
			myActionBar.setTitle("");
			myActionBar.setDisplayShowCustomEnabled(true);
			myActionBar.setDisplayShowTitleEnabled(false);
			myActionBar.setDisplayHomeAsUpEnabled(false);
			myActionBar.setDisplayShowHomeEnabled(false);
		}

		mCustomView = getLayoutInflater().inflate(R.layout.title, null);
		titleText = (TextView) mCustomView.findViewById(R.id.titleText);
		rightButton = (Button) mCustomView.findViewById(R.id.titleRightButton);
		rightText = (TextView) mCustomView.findViewById(R.id.titleRightText);
		// Calculate ActionBar height
		TypedValue tv = new TypedValue();
		if (this.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
			int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, this.getResources().getDisplayMetrics());
			ActionBar.LayoutParams mp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,actionBarHeight);
			myActionBar.setCustomView(mCustomView, mp);
			mp.gravity = mp.gravity & ~Gravity.HORIZONTAL_GRAVITY_MASK | Gravity.CENTER_HORIZONTAL;
			myActionBar.setCustomView(mCustomView, mp);
		} else {
			ActionBar.LayoutParams mp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,96);
			myActionBar.setCustomView(mCustomView, new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, 100));
			mp.gravity = mp.gravity & ~Gravity.HORIZONTAL_GRAVITY_MASK | Gravity.CENTER_HORIZONTAL;
			myActionBar.setCustomView(mCustomView, mp);
		}
	}
}
