package cn.com.igdj.library.base;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ProgressBar;
import cn.com.igdj.library.utils.ProgressBarUtil;

/**
 * Created by zou on 11/21/15.
 */
public class ProgressBarFragment extends Fragment {
    private ProgressBar progressBar;

    protected void setProgressBarVisibility(final int visibility) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (visibility == View.VISIBLE) {
                    if (progressBar == null && getActivity() != null) {
                        progressBar = ProgressBarUtil.createProgressBar(getActivity(), null);
                    } else {
                    	return;
                    }

                } else if (visibility == View.GONE) {
                    if (progressBar == null) {
                        return;
                    }
                } else if (visibility == View.INVISIBLE) {
                    if (progressBar == null) {
                        return;
                    }
                }
                progressBar.setVisibility(visibility);
            }
        });
    }
}
