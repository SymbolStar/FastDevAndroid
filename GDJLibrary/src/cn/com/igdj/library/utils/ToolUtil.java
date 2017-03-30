package cn.com.igdj.library.utils;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import cn.com.igdj.library.R;

public class ToolUtil {

	public static String getResString(Context context, int resId){
		return context.getResources().getString(resId);
	}
	/**
	 * push a notification
	 * 有问题，别用
	 */
	@SuppressWarnings("deprecation")
	public static void pushNotification(Context context, Class<?> cls, int resIcon, String title, String content, int notificationId){
		Notification notification = new Notification();
		notification.icon = resIcon;
		notification.tickerText = title;
		Intent localIntent = new Intent();
		localIntent.setClass(context, cls);
		PendingIntent pIntent = PendingIntent.getActivity(context, 0, localIntent, 0);
//		notification.setLatestEventInfo(context, title, content, pIntent);
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(notificationId, notification);
	}
	
	/**
	 * remove a notification from a id
	 */
	public static void removeNotification(Context context, int notificationId){
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(notificationId);
	}
	
	/**
	 * shake
	 * @param context 
	 */
	public static void shake(Context context){
		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		long[] pattern = { 100, 400, 100, 400 }; 
		vibrator.vibrate(pattern, -1); 
	}
	
	/**
	 * play music
	 * @param context 
	 * @param musicUri	
	 */
	public static void playMusic(Context context, Uri musicUri){
		if(musicUri == null){return;}
		MediaPlayer mMediaPlayer = MediaPlayer.create(context, musicUri);
		mMediaPlayer.setLooping(false);
		mMediaPlayer.start();
	}
	
	/**
	 * get phone resolution
	 * @param context
	 * @return (480*800)
	 */
	public static String getScreenMetrics(Context context) {
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
		return screenWidth + "*" + screenHeight;
	}
	
	/**
	 * get phone status bar height
	 * @param context
	 * @return
	 */
    public static int getStatusBarHeight(Context context){
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        } 
        return statusBarHeight;
    }
    
	/**
	 * scan SD card
	 */
	public static void sdScan(Context context) {
		Uri uri = Uri.parse("file://"+ Environment.getExternalStorageDirectory());
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, uri));
	}
	
	public static boolean isServiceRunning(Context context, String className){
		  ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		    for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
		        if (className.equals(service.service.getClassName())) {
		            return true;
		        }
		    }
		    return false;
	}
	
	public static String getPhotoPathFromUri(Context context, Uri uri){
		String imagePath = null;
		Cursor cursor = null;
		try{
			String [] proj = {MediaStore.Images.Media.DATA};
			cursor = context.getContentResolver().query(uri, proj, null, null, null);
			 if(cursor!=null){
		        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		        if (cursor.getCount() > 0 && cursor.moveToFirst()){
		            imagePath = cursor.getString(column_index);
		        }
			 }
		}catch(Exception e){
			
		}finally{
			if(cursor != null){
				cursor.close();
			}
		}
		return imagePath;
	}
	
	public static String MD5(String str){
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			md.reset();
			md.update(str.getBytes());
			byte[] md5 = md.digest();

			StringBuilder sb = new StringBuilder();
			for (byte b : md5){
				sb.append(String.format("%02x", b & 0xff));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}

	}
	
	public static <V> boolean isListEmpty(List<V> list){
		return list == null || list.size() == 0;
	}
	
	public static Drawable getStar(Context context, int level){
		level = (level / 5) * 5;

		String resStr = "star" + (level > 50 ? 50 : level);
		int resId = context.getResources().getIdentifier(resStr, "drawable", context.getPackageName());
		try{
			return context.getResources().getDrawable(resId);
		}catch(Exception e){
			return context.getResources().getDrawable(R.drawable.star5);
		}
	}
	
	/**
	 * 获取手机版本号
	 * @param context
	 * @return
	 */
	public static String getVersionName(Context context){
		PackageManager manager = context.getPackageManager();
		PackageInfo info;
		try {
			info = manager.getPackageInfo(context.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return info.versionName;
	}
	
	/**
	 *  根据listviewitem的高度现实listview的高度
	 * @param listView listview item必须是LinearLayout,这个方法会导致listview刷新两次，复杂页面不建议使用，建议针对具体的页面自行计算高度
	 * 而不要用这里自动计算高度的方法
	 */
	public static void setListViewHeight(ListView listView) {

		ListAdapter listAdapter = listView.getAdapter();

		if(listAdapter == null) {

			return;

		}

		int totalHeight = 0;

		for (int i = 0; i < listAdapter.getCount(); i++) {

			View listItem = listAdapter.getView(i, null, listView);

			listItem.measure(0, 0);

			totalHeight += listItem.getMeasuredHeight();

		}
		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}
}
