package cn.com.igdj.library.utils;

import java.text.DecimalFormat;

public class MathUtil {

	/**
	 * keep decimal
	 * @param content
	 * @param number
	 * @return
	 */
	public static String decimalFormat(double content, int number){
		if(number == 0){
			return (int)content+"";
		}
		String format = "0.";
		for(int i=0; i<number; i++){
			format = format + "0";
		}
		DecimalFormat df = new DecimalFormat(format);
		return df.format(content);
	}
	
	/**
	 * m to km
	 */
	public static String getDistance(int meters){
		if(meters < 1000){
			return meters + "米";
		}
		return decimalFormat(Double.valueOf(meters/1000 + "." + meters%1000), 1) + "km";
	}

}
