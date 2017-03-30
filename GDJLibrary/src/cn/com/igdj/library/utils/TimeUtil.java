package cn.com.igdj.library.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat ONLY_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat ONLY_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    public static final SimpleDateFormat TWO_LINE_FORMAT = new SimpleDateFormat("yyyy-MM-dd\nHH:mm:ss");
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("MM-dd HH:mm");

    /**
     * long time to string
     * 
     * @param timeInMillis
     * @param dateFormat
     * @return
     */
    public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
        return dateFormat.format(new Date(timeInMillis));
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     * 
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis) {
        return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     * 
     * @return
     */
    public static long getCurrentTimeInLong() {
        return System.currentTimeMillis();
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     * 
     * @return
     */
    public static String getCurrentTimeInString() {
        return getTime(getCurrentTimeInLong());
    }

    public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
        return getTime(getCurrentTimeInLong(), dateFormat);
    }
    
    /**
     * 
     * @param timeInMillis
     * @return (2014-3-5)
     */
    public static String getTimeYMD(long timeInMillis){
    	return getTime(timeInMillis, ONLY_DATE_FORMAT);
    }
    
    /**
     * 
     * @param timeInMillis
     * @return (15:34:42)
     */
    public static String getTimeHMS(long timeInMillis){
    	return getTime(timeInMillis, ONLY_TIME_FORMAT);
    }
    
    public static String getTimeYDHM(long timeInMillis){
    	return getTime(timeInMillis, DATE_TIME_FORMAT);

    }
     
    /**
     * @param timeInMillis
     * @return (2014-4-11 13:32:54)
     */
    public static String unixToDefaultTime(long timeInMillis){
    	return getTime((timeInMillis*1000L), DEFAULT_DATE_FORMAT);
    }
    
    public static String unixToDefaultTime(long timeInMillis, SimpleDateFormat dateFormat){
    	return getTime((timeInMillis*1000L), dateFormat);
    }
    
    public static String getUseTime(int seconds){
    	int minute = seconds / 60;
    	if(minute > 60){
    		int hour = minute / 60;
    		return hour + "小时" + (minute - (hour * 60)) + "分";
    	}
    	return minute + "分";
    }
}
