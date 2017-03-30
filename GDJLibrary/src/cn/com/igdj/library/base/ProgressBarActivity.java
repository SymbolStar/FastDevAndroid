package cn.com.igdj.library.base;

import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import cn.com.igdj.library.utils.ProgressBarUtil;

/**
 * Created by zou on 8/17/15.
 */
public abstract class ProgressBarActivity extends BaseActivity {

    private ProgressBar progressBar;

    protected void setProgressBarVisibility(final int visibility) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (visibility == View.VISIBLE) {
                    if (progressBar == null) {
                        progressBar = ProgressBarUtil.createProgressBar(ProgressBarActivity.this, null);
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
