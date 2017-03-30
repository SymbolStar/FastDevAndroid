package cn.com.igdj.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

/**
 * <li>set height base on children</li>
 * <li>scroll bottom listener</li>
 * @author kyc
 *
 */
public class KListView extends ListView implements OnScrollListener{
	private OnScrollBottomListener listener = null;

	//滑动到最后一项回调接口
	public interface OnScrollBottomListener{
		public void onScrollBottomListener();
	}
	public void setOnScrollBottomListener(OnScrollBottomListener listener){
		this.listener = listener;
	}
	
	public KListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setOnScrollListener(this);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if(firstVisibleItem + visibleItemCount == totalItemCount){
			//滑动到底部
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		
		//滑动到底部且停止滚动时实现回调函数(可以做继续加载等操作)
		if(view.getLastVisiblePosition() == view.getCount() - 1
				&& scrollState == OnScrollListener.SCROLL_STATE_IDLE
				&& listener != null){
			listener.onScrollBottomListener();
		}
	}
	
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
