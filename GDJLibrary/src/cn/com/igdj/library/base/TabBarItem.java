package cn.com.igdj.library.base;

import android.graphics.drawable.Drawable;

/**
 * Created by zou on 5/8/15.
 */
public class TabBarItem {

    Drawable drawable;
    String title;

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
