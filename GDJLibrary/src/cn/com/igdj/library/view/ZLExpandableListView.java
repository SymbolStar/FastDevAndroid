package cn.com.igdj.library.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

/**
 * Created by zou on 7/29/15.
 */
public class ZLExpandableListView extends ExpandableListView {

    boolean isOnMesure = false;

    public ZLExpandableListView(Context context) {
        super(context);
    }

    public ZLExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZLExpandableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//                MeasureSpec.AT_MOST);
//        isOnMesure = false;
//        super.onMeasure(widthMeasureSpec, expandSpec);
//    }

    public boolean isOnMesure() {
        return isOnMesure;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        isOnMesure = false;
        super.onLayout(changed, l, t, r, b);
    }
}
