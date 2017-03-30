package cn.com.igdj.library.utils;

import com.android.volley.VolleyError;

/**
 * Created by fujindong on 2017/3/8.
 */

public abstract class JSONHandler {
    public abstract void onSuccess(String jsonString);

    public abstract void onError(VolleyError errorMessage);
}
