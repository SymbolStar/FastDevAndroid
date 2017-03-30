package cn.com.igdj.library.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class GotoUtil {

	public static void gotoActivity(Context context, Class<?> cls) {
		Intent intent = new Intent(context, cls);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	
	public static void gotoActivityForResult(Context context, Class<?> cls, int requestCode) {
		Intent intent = new Intent(context, cls);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		((Activity)context).startActivityForResult(intent, requestCode);
	}
	
	public static void gotoActivityWithBundle(Context context, Class<?> cls, Bundle bundle) {
		Intent intent = new Intent(context, cls);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtras(bundle);
		context.startActivity(intent);
	}
	
	public static void gotoActivityClearTop(Context context, Class<?> cls) {
		Intent intent = new Intent(context, cls);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		context.startActivity(intent);
	}
	
	public static void gotoWebsite(Context context, Uri webUri){
		if(webUri == null){return;}
		Intent intent = new Intent(Intent.ACTION_VIEW, webUri);
		context.startActivity(intent);
	}
	
	public static void gotoPhoneToCall(Context context, String number){
    	Intent intent = new Intent();
    	intent.setAction("android.intent.action.CALL");
    	intent.setData(Uri.parse("tel:"+ number));
    	context.startActivity(intent);
	}
	
	public static void gotoPhoneNoCall(Context context, String number){
    	Intent intent = new Intent();
    	intent.setAction(Intent.ACTION_DIAL);
    	intent.setData(Uri.parse("tel:"+ number));
    	context.startActivity(intent);
	}
	
	public static void gotoSms(Context context, String number, String content){
        Intent localIntent = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:"+number));
        localIntent.putExtra("sms_body", content);
        context.startActivity(localIntent);
	}
	
	public static void gotoEmail(Context context, String title, String content){
        Intent localIntent = new Intent("android.intent.action.SEND");
        localIntent.setType("plain/text");
        localIntent.putExtra("android.intent.extra.EMAIL", "");
        localIntent.putExtra("android.intent.extra.SUBJECT", title);
        localIntent.putExtra("android.intent.extra.TEXT", content);
        context.startActivity(localIntent);
	}
	
	public static void gotoGallery(Context context){
		Intent intent = new Intent();  
        intent.setAction(Intent.ACTION_PICK);  
        intent.setType("image/*");  
        context.startActivity(intent);
	}
	
	public static void gotoGalleryForResult(Context context, int requestCode){
		Intent intent = new Intent();  
        intent.setAction(Intent.ACTION_PICK);  
        intent.setType("image/*");  
        ((Activity) context).startActivityForResult(intent, requestCode);
	}
	
	/**
	 * 设置页面传递的参数，封装到Bundle里
	 * @param params 字符串参数，可以有多个
	 * @return
	 */
	public static final Bundle setBundle(String ...params){
		Bundle bundle = new Bundle();
		bundle.putInt("count", params.length);
		for(int i=0; i<params.length; i++){
			bundle.putString(""+i, params[i]);
		}
		return bundle;
	}
}
