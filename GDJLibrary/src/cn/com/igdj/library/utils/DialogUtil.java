package cn.com.igdj.library.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import cn.com.igdj.library.R;
import cn.jpush.android.data.t;


public class DialogUtil {
	public static final String DEFAULT_DIALOG_MESSAGE = "加载中...";
	public static ProgressDialog pDialog = null;
	
	public static void showProgressDialog(Context context){
		showProgressDialog(context, DEFAULT_DIALOG_MESSAGE);
	}
	
	public static void showProgressDialog(Context context, String message){
		showProgressDialog(context, message, true);
	}
	
	
	public static void showProgressDialog(Context context, String message, boolean cancelable){
		if(pDialog != null){
			pDialog.dismiss();
			pDialog = null;
		}
		pDialog = new ProgressDialog(context);
		pDialog.setCancelable(cancelable);
		pDialog.show();
		
		View v = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null);
		TextView textView = (TextView) v.findViewById(R.id.msg);
		textView.setText(message);
		
		((ImageView)v.findViewById(R.id.close)).setVisibility(View.GONE);
		
		((ImageView)v.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cancelProgressDialog();
			}
		});
		
		pDialog.setContentView(v);
		WindowManager.LayoutParams params = pDialog.getWindow().getAttributes();
		params.width = ScreenUtil.dpToPxInt(context, 120);
		params.height = ScreenUtil.dpToPxInt(context, 120);
		pDialog.getWindow().setAttributes(params); 
	}
	
	public static ProgressDialog getProgressDialog(Context context, String message){
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setMessage(message);
		return dialog;
	}
	
	
	public static void cancelProgressDialog(){
		if(pDialog == null) return;
		if(pDialog.isShowing()){
			pDialog.dismiss();
		}
	}
	
	/**
	 * 显示列表形式的对话框
	 * @param context
	 * @param title 标题
	 * @param items 数组
	 * @param listener 点击某项的回调(重写onItemClick)
	 */
	public static void showDialog(Context context, String title, String[] items, final DialogCallback listener){
		final AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		
		View v = LayoutInflater.from(context).inflate(R.layout.dialog_view, null);
		if(title != null){
			((TextView)v.findViewById(R.id.item_title)).setText(title);
		}
		ListView lv = (ListView) v.findViewById(R.id.list); 
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
				dialog.dismiss();
				if(listener != null) listener.onItemClick(position);
			}
		});
		lv.setAdapter(new DialogListAdapter(context, items));
		Window window = dialog.getWindow();
		window.setContentView(v);
		
		WindowManager m = ((Activity) context).getWindowManager();
		Display display = m.getDefaultDisplay();	
		WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
		params.width = (int) (display.getWidth() * 0.8);
		dialog.getWindow().setAttributes(params); 
	}
	
	/**
	 * 可自定义View的对话框,带标题,确定和取消按钮
	 * @param context
	 * @param title 标题
	 * @param view 自定义View
	 * @param callback 点击按钮回调(重写onOk)
	 */
	public static void showDialog(Context context, String title, View view, final DialogCallback callback){
		showDialog(context, title, view, null, null, false, callback);
	}
	
	/**
	 * 仅显示内容和确定按钮的提示对话框
	 * @param context
	 * @param title
	 */
	public static void showDialog(Context context, String title, final DialogCallback callback){
		showDialog(context, title, null, null, null, true, callback);
	}
	
	/**
	 * 显示简单的对话框,带标题,左右按钮
	 * @param context
	 * @param title 标题
	 * @param leftBtnText 左按钮显示
	 * @param rightBtnText 右按钮显示
	 * @param callback 点击按钮回调(重写onLeft和onRight)
	 */
	public static void showDialog(Context context, String title, String leftBtnText, String rightBtnText, final DialogCallback callback){
		showDialog(context, title, null, leftBtnText, rightBtnText, false, callback);
	}
	
	@SuppressWarnings("deprecation")
	private static void showDialog(Context context, String title, View view, String leftBtnText, String rightBtnText, boolean onlyOk, final DialogCallback callback){
		final AlertDialog dialog = new AlertDialog.Builder(context).create();
		dialog.show();
		
		View v = LayoutInflater.from(context).inflate(R.layout.dialog_simple_view, null);
		
		if(title != null){
			((TextView)v.findViewById(R.id.item_title)).setText(title);
		}
		
		if(view != null){
			LinearLayout llInsertView = (LinearLayout) v.findViewById(R.id.insert_view);
			llInsertView.addView(view, 0);
			llInsertView.setVisibility(View.VISIBLE);
		}
		
		Button btnLeft = (Button) v.findViewById(R.id.btn_ok);
		Button btnRight = (Button) v.findViewById(R.id.btn_cancel);
		
		if(onlyOk) btnRight.setVisibility(View.GONE);
		
		if(leftBtnText != null) btnLeft.setText(leftBtnText);
		if(rightBtnText != null) btnRight.setText(rightBtnText);
		
		btnLeft.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				if(callback != null){
					callback.onOk();
					callback.onLeft();
				}
			}
		});
		btnRight.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				if(callback != null){
					callback.onRight();
				}
			}
		});
		
		Window window = dialog.getWindow();
		window.setContentView(v);
		
		WindowManager m = ((Activity) context).getWindowManager();
		Display display = m.getDefaultDisplay();	
		WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
		params.width = (int) (display.getWidth() * 0.8);

		dialog.getWindow().setAttributes(params); 
	}
}
