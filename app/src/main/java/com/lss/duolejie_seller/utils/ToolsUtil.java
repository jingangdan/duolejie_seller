package com.lss.duolejie_seller.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

/**
 * @author  lss 
 * @date 创建时间：2016-1-13 上午10:13:27 
 * @version 1.0 
 * @Description 打印工具类
 */
public class ToolsUtil {
	
	static private Toast mToast = null;

	public static int M_SCREEN_WIDTH;

	public static int M_SCREEN_HEIGHT;
	
	// 打印方法
	public static void print(String tag, String msg) {
		if(true){
			Log.i(tag, msg);
		}
	}
	
	/**
	 * Toast提醒
	 * @param msg
	 */
	public static void toast (Context context,String msg) {
		// 防止Toast重复显示
		if (mToast == null) {  
            mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);  
        } else {  
            mToast.setText(msg);  
            mToast.setDuration(Toast.LENGTH_SHORT);  
        }  
        mToast.show(); 
	}
	
//	/**
//	 * Exception 信息提示
//	 */
//	public static void Notic(Context context, String notic,
//			OnClickListener listener) {
//		UIDialog.ForNotic(context, notic, listener);
//	}
	
	/**
	 * 获取当前应用的版本号
	 */

	public static int getAppVersion(Context context) {
		int version = 0;
		try {
			PackageInfo packinfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			version = packinfo.versionCode;
		} catch (Exception e) {
			e.printStackTrace();
			return version;
		}

		return version;
	}
	
	/**
	 * 格式化时间 昨天今天
	 * @param time 
	 * @return 
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formatDateTime(String time) {
		SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		if(time==null ||"".equals(time)){
			return "";
		}
		Date date = null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Calendar current = Calendar.getInstance();
		
		Calendar today = Calendar.getInstance();	//今天
		
		today.set(Calendar.YEAR, current.get(Calendar.YEAR));
		today.set(Calendar.MONTH, current.get(Calendar.MONTH));
		today.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
		//  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
		today.set( Calendar.HOUR_OF_DAY, 0);
		today.set( Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		
		Calendar yesterday = Calendar.getInstance();	//昨天
		
		yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
		yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
		yesterday.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH)-1);
		yesterday.set( Calendar.HOUR_OF_DAY, 0);
		yesterday.set( Calendar.MINUTE, 0);
		yesterday.set(Calendar.SECOND, 0);
		
		current.setTime(date);
		
		if(current.after(today)){
			return "今天 "+time.split(" ")[1];
		}else if(current.before(today) && current.after(yesterday)){
			
			return "昨天 "+time.split(" ")[1];
		}else{
			int index = time.indexOf("-")+1;
			return time.substring(index, time.length());
		}
	}
	
	/**
	 * 日期，今明后天
	 * @param time
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getDate(String time){
		SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		if(time==null ||"".equals(time)){
			return "";
		}
		Date date = null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
        Calendar today = Calendar.getInstance();  
		Calendar old = Calendar.getInstance();  
		Calendar current = Calendar.getInstance();
		
		//此处的isEver everType startTime  createDate为pojo的属性   
		//此处好像是去除0   
		today.set(Calendar.YEAR, current.get(Calendar.YEAR));
		today.set(Calendar.MONTH, current.get(Calendar.MONTH));
		today.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
		today.set(Calendar.HOUR, 0);  
		today.set(Calendar.MINUTE, 0);  
		today.set(Calendar.SECOND, 0);  
		// 明天
		old.set(Calendar.YEAR, current.get(Calendar.YEAR));
		old.set(Calendar.MONTH, current.get(Calendar.MONTH));
		old.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH)+1);
		old.set(Calendar.HOUR, 0);  
		old.set(Calendar.MINUTE, 0);  
		old.set(Calendar.SECOND, 0);  
		
		current.setTime(date);
		        //老的时间减去今天的时间  
        long intervalMilli = current.getTimeInMillis() - today.getTimeInMillis();  
		int xcts = (int) (intervalMilli / (24 * 60 * 60 * 1000));  
		ToolsUtil.print("----", "xcts ===== "+ xcts);
		ToolsUtil.print("----", "current ===== "+ current);
		ToolsUtil.print("----", "today ===== "+ today);
		//-2:前天 -1：昨天 0：今天 1：明天 2：后天， out：显示日期
		String day = null;
		if(xcts==0){
			day = "今天";
		}else if(xcts == 1){
			day = "明天";
		}else if(xcts == 2){
			day = "后天";
		}else if(xcts>2){
			day = "第"+(xcts)+"天";
		}else{
			day="未设置";
		}
//		if (xcts >= -2 && xcts <= 2) {  
//			return String.valueOf(xcts);  
//		} else {  
//			return "out";  
//		}  
		return day;
	}

	/*public static long getTime(String signTime, String payTime) {
		try {
			long start = DateUtil.stringToLong(signTime, "yyyy-MM-dd HH:mm:ss");
			long end = DateUtil.stringToLong(payTime, "yyyy-MM-dd HH:mm:ss");
			return (end-start)/60000;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}
	}*/
	
	/**
	 * 生成二维码 要转换的地址或字符串,可以是中文
	 * 
	 * @param url
	 * @param width
	 * @param height
	 * @return
	 */
	public static Bitmap createQRImage(String url, final int width, final int height) {
		try {
			// 判断URL合法性
			if (url == null || "".equals(url) || url.length() < 1) {
				return null;
			}
			Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			// 图像数据转换，使用了矩阵转换
			BitMatrix bitMatrix = new QRCodeWriter().encode(url,
					BarcodeFormat.QR_CODE, width, height, hints);
			int[] pixels = new int[width * height];
			// 下面这里按照二维码的算法，逐个生成二维码的图片，
			// 两个for循环是图片横列扫描的结果
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (bitMatrix.get(x, y)) {
						pixels[y * width + x] = 0xff000000;
					} else {
						pixels[y * width + x] = 0xffffffff;
					}
				}
			}
			// 生成二维码图片的格式，使用ARGB_8888
			Bitmap bitmap = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
			return bitmap;
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return null;
	}
	
 	public static String getNowStr(String formatType) {
 		return new SimpleDateFormat(formatType, new Locale("zh_CN")).format(new Date());
 	}
}