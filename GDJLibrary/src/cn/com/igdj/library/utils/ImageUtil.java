package cn.com.igdj.library.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.Base64;
import android.widget.ImageView;

/**
 * ImageUtils
 * <ul>
 * convert between Bitmap, byte array, Drawable
 * <li>{@link #bitmapToByte(Bitmap)}</li>
 * <li>{@link #bitmapToDrawable(Bitmap)}</li>
 * <li>{@link #byteToBitmap(byte[])}</li>
 * <li>{@link #byteToDrawable(byte[])}</li>
 * <li>{@link #drawableToBitmap(Drawable)}</li>
 * <li>{@link #drawableToByte(Drawable)}</li>
 * </ul>
 * <ul>
 * get image
 * <li>{@link #getInputStreamFromUrl(String, int)}</li>
 * <li>{@link #getBitmapFromUrl(String, int)}</li>
 * <li>{@link #getDrawableFromUrl(String, int)}</li>
 * </ul>
 * <ul>
 * scale image
 * <li>{@link #scaleImageTo(Bitmap, int, int)}</li>
 * <li>{@link #scaleImage(Bitmap, float, float)}</li>
 * </ul>
 * 
 */
public class ImageUtil {

    /**
     * convert Bitmap to byte array
     * 
     * @param b
     * @return
     */
    public static byte[] bitmapToByte(Bitmap b) {
        if (b == null) {
            return null;
        }

        ByteArrayOutputStream o = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, o);
        return o.toByteArray();
    }

    /**
     * convert byte array to Bitmap
     * 
     * @param b
     * @return
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     * convert Drawable to Bitmap
     * 
     * @param d
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable d) {
        return d == null ? null : ((BitmapDrawable)d).getBitmap();
    }

    /**
     * convert Bitmap to Drawable
     * 
     * @param b
     * @return
     */
    @SuppressWarnings("deprecation")
	public static Drawable bitmapToDrawable(Bitmap b) {
        return b == null ? null : new BitmapDrawable(b);
    }

    /**
     * convert Drawable to byte array
     * 
     * @param d
     * @return
     */
    public static byte[] drawableToByte(Drawable d) {
        return bitmapToByte(drawableToBitmap(d));
    }

    /**
     * convert byte array to Drawable
     * 
     * @param b
     * @return
     */
    public static Drawable byteToDrawable(byte[] b) {
        return bitmapToDrawable(byteToBitmap(b));
    }

    /**
     * scale image
     * 
     * @param org
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap scaleImageTo(Bitmap org, int newWidth, int newHeight) {
        return scaleImage(org, (float)newWidth / org.getWidth(), (float)newHeight / org.getHeight());
    }

    /**
     * scale image
     * 
     * @param org
     * @param scaleWidth sacle of width
     * @param scaleHeight scale of height
     * @return
     */
    public static Bitmap scaleImage(Bitmap org, float scaleWidth, float scaleHeight) {
        if (org == null) {
            return null;
        }

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
    }
    
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx){  
        
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);  
        Canvas canvas = new Canvas(output);  
   
        final int color = 0xff424242;  
        final Paint paint = new Paint();  
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());  
        final RectF rectF = new RectF(rect);  
   
        paint.setAntiAlias(true);  
        canvas.drawARGB(0, 0, 0, 0);  
        paint.setColor(color);  
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);  
   
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        canvas.drawBitmap(bitmap, rect, rect, paint);  
   
        return output;  
    } 
    
	public static Bitmap compressBitmap(String path, int width, int height) {
		
		Options options = new Options();
		options.inJustDecodeBounds = true;
		
		BitmapFactory.decodeFile(path, options);
		int imageWidth = options.outWidth;
		int imageHeight = options.outHeight;

		int scaleX = imageWidth / width;
		int scaleY = imageHeight / height;
		int scale = 1;
		if (scaleX >= scaleY && scaleX >= 1) {
			scale = scaleX;
		} else if (scaleY >= scaleX && scaleY >= 1) {
			scale = scaleY;
		}

		options.inJustDecodeBounds = false;
		options.inSampleSize = scale;
		return BitmapFactory.decodeFile(path, options);
	}
	
	public static void compressBmpToFile(Bitmap bmp,File file){  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        int options = 80;//个人喜欢从80开始,  
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);  
        while (baos.toByteArray().length / 1024 > 100) {   
            baos.reset();  
            options -= 10;  
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);  
        }  
        try {  
            FileOutputStream fos = new FileOutputStream(file);  
            fos.write(baos.toByteArray());  
            fos.flush();  
            fos.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
	
	 public static Bitmap compressImageFromFile(String srcPath) {  
	        BitmapFactory.Options newOpts = new BitmapFactory.Options();  
	        newOpts.inJustDecodeBounds = true;//只读边,不读内容  
	        Bitmap bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
	  
	        newOpts.inJustDecodeBounds = false;  
	        int w = newOpts.outWidth;  
	        int h = newOpts.outHeight;  
	        float hh = 800f;//  
	        float ww = 480f;//  
	        int be = 1;  
	        if (w > h && w > ww) {  
	            be = (int) (newOpts.outWidth / ww);  
	        } else if (w < h && h > hh) {  
	            be = (int) (newOpts.outHeight / hh);  
	        }  
	        if (be <= 0)  
	            be = 1;  
	        newOpts.inSampleSize = be;//设置采样率  
	          
	        newOpts.inPreferredConfig = Config.ARGB_8888;//该模式是默认的,可不设  
	        newOpts.inPurgeable = true;// 同时设置才会有效  
	        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收  
	          
	        bitmap = BitmapFactory.decodeFile(srcPath, newOpts);  
//	      return compressBmpFromBmp(bitmap);//原来的方法调用了这个方法企图进行二次压缩  
	                                    //其实是无效的,大家尽管尝试  
	        return bitmap;  
	    }  
	 
	private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(android.R.color.transparent);
    public static void fadeInDisplay(ImageView imageView, Bitmap bitmap) {
        final TransitionDrawable transitionDrawable =
                new TransitionDrawable(new Drawable[]{
                        TRANSPARENT_DRAWABLE,
                        new BitmapDrawable(imageView.getResources(), bitmap)
                });
        imageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(500);
    }
    
    public static String getUploadFileStr(String filePath){
    	byte[] fileData = FileUtil.getBytesFromFile(new File(filePath)); 
    	String temp = Base64.encodeToString(fileData, 0, fileData.length, Base64.DEFAULT); //base64加密
    	return temp;
    }
    
    public static String bitmap2String(Bitmap bitmap){

    	String result = null;  
        ByteArrayOutputStream baos = null;  
        try {  
            if (bitmap != null) {  
                baos = new ByteArrayOutputStream();  
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
      
                baos.flush();  
                baos.close();  
      
                byte[] bitmapBytes = baos.toByteArray();  
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (baos != null) {  
                    baos.flush();  
                    baos.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return result;  
    }
}
