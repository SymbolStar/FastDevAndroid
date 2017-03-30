package cn.com.igdj.library.utils;

import java.util.Calendar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;
import cn.com.igdj.library.R;
import android.view.LayoutInflater;
import android.view.View;
/**
 * 时间选择  问题没解决
 * @author fujindong
 *
 */
public class DialogTimeUtil {

	public void showDateTimeDialog(Context context)
	{
		 String choseTime ;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		View v = LayoutInflater.from(context).inflate(R.layout.dialog_choose_date, null);
		builder.setTitle("选择日期");
		builder.setView(v);
		final DatePicker datepicker = (DatePicker) v.findViewById(R.id.datepicker);
		final TimePicker timePicker = (TimePicker) v.findViewById(R.id.timepicker);
//		显示当前时间
        timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
            
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
    //            setTitle(String.format("%02d:%02d", hourOfDay, minute));
            }
        });

		Calendar calendar = Calendar.getInstance();
		timePicker.setIs24HourView(true);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));  
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
		int year = calendar.get(Calendar.YEAR);
		int monthofyear = calendar.get(Calendar.MONTH);
		int dayofmonth = calendar.get(Calendar.DAY_OF_MONTH);
		datepicker.init(year, monthofyear, dayofmonth, new DatePicker.OnDateChangedListener()
		{
			public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
			{
//				Bundling.this.showToast("您选择的日期是：" + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日。");
			}
		});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
//				TODO 可能存在问题
				String monthStr = "";
				String dayStr = "";
				if ((datepicker.getMonth() + 1) < 10) {
					monthStr ="0"+(datepicker.getMonth() + 1);
				}else {
					monthStr = (datepicker.getMonth() + 1) + "";
				}
				if (datepicker.getDayOfMonth()<10) {
					dayStr = "0"+datepicker.getDayOfMonth();
				}else {
					dayStr = datepicker.getDayOfMonth() + "";
				}
				String sdt = (datepicker.getYear() + "") + "-" + (monthStr + "") + "-" + (dayStr + "");
				String time = (timePicker.getCurrentHour()+"")+":"+(timePicker.getCurrentMinute()+"");
	//			 choseTime = sdt+" "+time;
				dialog.dismiss();
				
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				dialog.dismiss();
			}
		});
		builder.show();
	}	
	
	
}
