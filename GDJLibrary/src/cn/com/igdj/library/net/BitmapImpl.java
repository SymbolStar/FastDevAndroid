package cn.com.igdj.library.net;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import cn.com.igdj.library.R;
import cn.com.igdj.library.utils.ProgressBarUtil;

public class BitmapImpl {

	private static BitmapImpl bitmapUtils;
	
	private BitmapImpl(){}
	
	private static DisplayImageOptions options = null;

	public void display(final ImageView imageView, String url){
		if(options == null){
			options = new DisplayImageOptions.Builder()  
	        .cacheInMemory(true)  
	        .cacheOnDisc(true)  
	        .showImageForEmptyUri(R.drawable.img_error)
	        .showImageOnFail(R.drawable.img_error)
	        .bitmapConfig(Bitmap.Config.RGB_565)  
	        .build(); 
		}

		ImageLoader.getInstance().displayImage(url, imageView, options);
	} 
	
	public void displayYxt(final ImageView imageView, String url,int ErrorImageId)
	{
		DisplayImageOptions op = new DisplayImageOptions.Builder()  
        .cacheInMemory(true)  
        .cacheOnDisc(true)
        .showImageForEmptyUri(ErrorImageId)
        .showImageOnFail(ErrorImageId)
        .bitmapConfig(Bitmap.Config.RGB_565)  
        .build(); 

		ImageLoader.getInstance().displayImage(url, imageView, op);
	} 
	
	public void displayYXT(final ImageView imageView, String url,int ErrorImageId,final Activity activity)
	{
		DisplayImageOptions op = new DisplayImageOptions.Builder()  
        .cacheInMemory(true)  
        .cacheOnDisc(true)
        .showImageForEmptyUri(ErrorImageId)
        .showImageOnFail(ErrorImageId)
        .bitmapConfig(Bitmap.Config.RGB_565)  
        .build(); 

		ImageLoader.getInstance().displayImage(url, imageView, op, new ImageLoadingListener(){

			@Override
			public void onLoadingStarted(String imageUri, View view) {
				// TODO Auto-generated method stub
				//开始加载的时候执行
				setProgressBarVisibility(View.VISIBLE,activity);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				// TODO Auto-generated method stub
				//加载失败的时候执行
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				// TODO Auto-generated method stub
				//加载成功的时候执行
				setProgressBarVisibility(View.GONE,activity);
			}

			@Override
			public void onLoadingCancelled(String imageUri, View view) {
				// TODO Auto-generated method stub
				//加载取消的时候执行
			}
			
		});
		
	}
	
	private ProgressBar progressBar;
	protected void setProgressBarVisibility(final int visibility, final Activity activity) {
		// TODO Auto-generated method stub
		new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (visibility == View.VISIBLE) {
                    if (progressBar == null) {
                        progressBar = ProgressBarUtil.createProgressBar(activity, null);
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
                if (visibility == View.GONE && progressBar != null) {
                	progressBar = null;
                }
            }
        });
	}

	public static BitmapImpl getInstance(){

		if(bitmapUtils == null){
			bitmapUtils = new BitmapImpl();
		}
		return bitmapUtils;
	}
}
