package cn.com.igdj.library.pullrefresh;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import cn.com.igdj.library.pullrefresh.CustomScrollView;


public class PullToRefreshCustomScrollView extends PullToRefreshBase<CustomScrollView> {
    /**
     * 构造方法
     * 
     * @param context context
     */
    public PullToRefreshCustomScrollView(Context context) {
        this(context, null);
    }
    
    /**
     * 构造方法
     * 
     * @param context context
     * @param attrs attrs
     */
    public PullToRefreshCustomScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    /**
     * 构造方法
     * 
     * @param context context
     * @param attrs attrs
     * @param defStyle defStyle
     */
    public PullToRefreshCustomScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * @see com.nj1s.lib.pullrefresh.PullToRefreshBase#createRefreshableView(android.content.Context, android.util.AttributeSet)
     */
    @Override
    protected CustomScrollView createRefreshableView(Context context, AttributeSet attrs) {
    	CustomScrollView scrollView = new CustomScrollView(context, attrs);
        return scrollView;
    }

    /**
     * @see com.nj1s.lib.pullrefresh.PullToRefreshBase#isReadyForPullDown()
     */
    @Override
    protected boolean isReadyForPullDown() {
        return mRefreshableView.getScrollY() == 0;
    }

    /**
     * @see com.nj1s.lib.pullrefresh.PullToRefreshBase#isReadyForPullUp()
     */
    @Override
    protected boolean isReadyForPullUp() {
        View scrollViewChild = mRefreshableView.getChildAt(0);
        if (null != scrollViewChild) {
            return mRefreshableView.getScrollY() >= (scrollViewChild.getHeight() - getHeight());
        }
        
        return false;
    }
    
//    private int stateToSave;
//    ////////////////////////////////////////////////////////////
//    @Override
//    public Parcelable onSaveInstanceState() {
//      //begin boilerplate code that allows parent classes to save state
//    	Parcelable superState = super.onSaveInstanceState();
//
//    	SavedState ss = new SavedState(superState);
//      //end
//      
//    	ss.stateToSave = this.stateToSave;
//
//    	return ss;
//    }
//    
//    @Override
//    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> container) {
//    	// TODO Auto-generated method stub
//    	super.dispatchRestoreInstanceState(container);
//    }
//    
//    @Override
//    protected void dispatchSaveInstanceState(SparseArray<Parcelable> container) {
//    	// TODO Auto-generated method stub
//    	super.dispatchSaveInstanceState(container);
//    }
//
//    @Override
//    public void onRestoreInstanceState(Parcelable state) {
//      //begin boilerplate code so parent classes can restore state
//      if(!(state instanceof SavedState)) {
//        super.onRestoreInstanceState(state);
//        return;
//      }
//
//      SavedState ss = (SavedState)state;
//      super.onRestoreInstanceState(ss.getSuperState());
//      //end
//
//      this.stateToSave = ss.stateToSave;
//    }
//
//    static class SavedState extends BaseSavedState {
//      int stateToSave;
//
//      SavedState(Parcelable superState) {
//        super(superState);
//      }
//
//      private SavedState(Parcel in) {
//        super(in);
//        this.stateToSave = in.readInt();
//      }
//
//      @Override
//      public void writeToParcel(Parcel out, int flags) {
//        super.writeToParcel(out, flags);
//        out.writeInt(this.stateToSave);
//      }
//
//      //required field that makes Parcelables from a Parcel
//      public static final Parcelable.Creator<SavedState> CREATOR =
//          new Parcelable.Creator<SavedState>() {
//            public SavedState createFromParcel(Parcel in) {
//              return new SavedState(in);
//            }
//            public SavedState[] newArray(int size) {
//              return new SavedState[size];
//            }
//      };
//    }
}
