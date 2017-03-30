package cn.com.igdj.library.utils;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

public class ToastManager {
	private static Toast toast;
    
	
	public static void showToast(final Context context, final CharSequence text) {
		new Handler(context.getMainLooper()).post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
			}
		});
	}
	public static void showToast(final Context context, final int resId) {
		new Handler(context.getMainLooper()).post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Toast.makeText(context, resId, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	
	public static void repeatToast(final Context context, final CharSequence text) {
		new Handler(context.getMainLooper()).post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				        if (toast == null) {
				            toast = Toast.makeText(context,
				                         text, 
				                         Toast.LENGTH_SHORT);
				        } else {
				            toast.setText(text);
				        }
				        toast.show();
				    }
		});
		
	}

}
