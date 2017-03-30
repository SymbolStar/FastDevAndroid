package cn.com.igdj.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 计算高度
 */
public class MyListView extends ListView {

	public MyListView(Context context) {
		super(context);
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// 此处是代码的关键
		// MeasureSpec.AT_MOST的意思就是wrap_content
		// Integer.MAX_VALUE >> 2 是使用最大值的意思,也就表示的无边界模式
		// Integer.MAX_VALUE >> 2 此处表示是福布局能够给他提供的大小
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
