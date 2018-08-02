package com.lss.duolejie_seller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.jiguang.d.c.t;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.lss.duolejie_seller.base.BaseActivity;
import com.lss.duolejie_seller.utils.AESUtil;
import com.lss.duolejie_seller.utils.HttpUtils;
import com.lss.duolejie_seller.utils.JsonUtils;
import com.lss.duolejie_seller.utils.RequestDailog;
import com.lss.duolejie_seller.utils.Tools;
import com.lss.duolejie_seller.utils.UIDialog;

/**
 * 类说明
 * 
 * @author zyk
 * @version 创建时间：2015-7-25 下午4:05:41
 */
public class B_1_1_Edit extends BaseActivity {
	private ImageView iv_back;
	private EditText et_dianpuming, et_dianzhu, et_xiangxidizhi, et_jianjie,
			et_yaoqingma, et_shoujihao;
	private TextView tv_xuanze, tv_zhizhao, tv_logo, tv_save;
	/* 头像上传 */
	private static final int PAIZHAO = 14;
	private static final int XIANGCE = 15;
	private String timeString;
	private String cutnameString;
	private String filename;
	private int islogo = 1;
	private String zz = "", lg = "";
	private String addressByGPS;;
	double latitude,lnglng;

	// 声明AMapLocationClientOption对象
	public AMapLocationClientOption mLocationOption = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_edit);
		initUI();
		// 初始化定位
		mLocationClient = new AMapLocationClient(getApplicationContext());
		// 设置定位回调监听
		mLocationClient.setLocationListener(mLocationListener);

		// 初始化AMapLocationClientOption对象
		mLocationOption = new AMapLocationClientOption();
		// 设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
		mLocationOption.setLocationMode(AMapLocationMode.Battery_Saving);

		// 给定位客户端对象设置定位参数
		mLocationClient.setLocationOption(mLocationOption);
		// 启动定位
		mLocationClient.startLocation();

		/**
		 * 获取一次定位
		 */
		// 该方法默认为false，true表示只定位一次
		mLocationOption.setOnceLocation(true);
		
		
	}

	private void initUI() {
		// TODO Auto-generated method stub
		iv_back = (ImageView) findViewById(R.id.iv_back);
		et_dianpuming = (EditText) findViewById(R.id.et_dianpuming);
		et_dianzhu = (EditText) findViewById(R.id.et_dianzhu);
		et_xiangxidizhi = (EditText) findViewById(R.id.et_xiangxidizhi);
		et_jianjie = (EditText) findViewById(R.id.et_jianjie);
		et_yaoqingma = (EditText) findViewById(R.id.et_yaoqingma);
		et_shoujihao = (EditText) findViewById(R.id.et_shoujihao);
		tv_xuanze = (TextView) findViewById(R.id.tv_xuanze);
		tv_zhizhao = (TextView) findViewById(R.id.tv_zhizhao);
		tv_logo = (TextView) findViewById(R.id.tv_logo);
		tv_save = (TextView) findViewById(R.id.tv_save);
		setListener(iv_back, tv_xuanze, tv_zhizhao, tv_logo, tv_save);
	}

	
	
	
	
	
	
	
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_back:
			this.finish();
			break;
		case R.id.tv_save:
			String dianpuming = et_dianpuming.getText().toString().trim();
			String dianzhuming = et_dianzhu.getText().toString().trim();
			String xxdizhi = et_xiangxidizhi.getText().toString().trim();
			String jianjie = et_jianjie.getText().toString().trim();
			String zhizhaotu = zz;
			String logo = lg;
			String yaoqingma = et_yaoqingma.getText().toString().trim();
			String dianhua = et_shoujihao.getText().toString().trim();

			if (TextUtils.isEmpty(dianpuming)) {
				Toast.makeText(B_1_1_Edit.this, "店铺名称不能为空", Toast.LENGTH_LONG)
						.show();
			} else if (TextUtils.isEmpty(dianzhuming)) {
				Toast.makeText(B_1_1_Edit.this, "店主姓名不能为空", Toast.LENGTH_LONG)
						.show();
			} else if (TextUtils.isEmpty(xxdizhi)) {
				Toast.makeText(B_1_1_Edit.this, "详细地址不能为空", Toast.LENGTH_LONG)
						.show();
			} else if (TextUtils.isEmpty(jianjie)) {
				Toast.makeText(B_1_1_Edit.this, "经营项目简介不能为空", Toast.LENGTH_LONG)
						.show();
			} else if (TextUtils.isEmpty(zhizhaotu)) {
				Toast.makeText(B_1_1_Edit.this, "请上传营业执照", Toast.LENGTH_LONG)
						.show();
			} else if (TextUtils.isEmpty(logo)) {
				Toast.makeText(B_1_1_Edit.this, "请上传店铺logo", Toast.LENGTH_LONG)
						.show();
			} else if (TextUtils.isEmpty(dianhua)) {
				Toast.makeText(B_1_1_Edit.this, "联系方式不能为空", Toast.LENGTH_LONG)
						.show();
			} else {
				Map<String, String> map = new HashMap<String, String>();
				map.put("merchant_name", dianzhuming);
				map.put("brand_name", dianpuming);
				map.put("map_address", addressByGPS);
				map.put("brand_address", xxdizhi);
				map.put("brand_introduction", jianjie);
				map.put("bus_license", zhizhaotu);
				map.put("brand_header", logo);
				map.put("brand_invite", yaoqingma);
				map.put("brand_tel", dianhua);
				Long time = null;
				String jm = null;
				try {

					time =System.currentTimeMillis();
			    	System.out.println(time);
			     	String miwen=dianpuming+"|$|"+dianzhuming+"|$|"+addressByGPS+"|$|"+xxdizhi+"|$|"+jianjie+"|$|"+zhizhaotu+"|$|"+logo+"|$|"+yaoqingma+"|$|"+dianhua+"|$|"+time.toString();
			     	jm= AESUtil.encrypt(miwen);
					
				} catch (Exception e) {
					
				}
				
				map.put("request_time",""+time);
				map.put("sign", jm);
				String json = JsonUtils.toJson(map);
				HttpUtils.addBrandList(addBrandList, json);
			}
			break;
		case R.id.tv_xuanze:// 跳转到地图界面
			/* 设置位置 */
			Intent intent_getlocation = new Intent(B_1_1_Edit.this,
					B3_23_LocationActivity.class);
			
			intent_getlocation.putExtra("geoLat", latitude);
			intent_getlocation.putExtra("geoLng", lnglng);
			startActivityForResult(intent_getlocation, 23);
			break;
		case R.id.tv_zhizhao:
			islogo = 0;
			UIDialog.ForThreeBtn(this, new String[] { "相册", "拍照", "取消" }, this);
			break;
		case R.id.tv_logo:
			islogo = 1;
			UIDialog.ForThreeBtn(this, new String[] { "相册", "拍照", "取消" }, this);
			break;
		case R.id.dialog_modif_1:// 相册
			UIDialog.closeDialog();
			/**
			 * 刚开始，我自己也不知道ACTION_PICK是干嘛的，后来直接看Intent源码，
			 * 可以发现里面很多东西,Intent是个很强大的东西，大家一定仔细阅读下
			 */
			Intent intent_toXIANGCE = new Intent(Intent.ACTION_PICK, null);
			/**
			 * 下面这句话，与其它方式写是一样的效果，如果： intent.setData(MediaStore.Images
			 * .Media.EXTERNAL_CONTENT_URI); intent.setType(""image/*");设置数据类型
			 * 如果朋友们要限制上传到服务器的图片类型时可以直接写如 ："image/jpeg 、 image/png等的类型"
			 * 这个地方小马有个疑问，希望高手解答下：就是这个数据URI与类型为什么要分两种形式来写呀？有什么区别？
			 */
			intent_toXIANGCE.setDataAndType(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			startActivityForResult(intent_toXIANGCE, XIANGCE);
			break;
		case R.id.dialog_modif_2:// 拍照
			UIDialog.closeDialog();
			/**
			 * 下面这句还是老样子，调用快速拍照功能，至于为什么叫快速拍照，大家可以参考如下官方
			 * 文档，you_sdk_path/docs/guide/topics/media/camera.html
			 * 我刚看的时候因为太长就认真看，其实是错的，这个里面有用的太多了，所以大家不要认为
			 * 官方文档太长了就不看了，其实是错的，这个地方小马也错了，必须改正
			 */
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"'IMG'_yyyyMMddHHmmss");
			timeString = dateFormat.format(date);
			createSDCardDir();
			Intent intent_PAIZHAO = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intent_PAIZHAO.putExtra(MediaStore.EXTRA_OUTPUT, Uri
					.fromFile(new File(Environment
							.getExternalStorageDirectory() + "/DCIM/Camera",
							timeString + ".jpg")));
			startActivityForResult(intent_PAIZHAO, PAIZHAO);
			break;
		case R.id.dialog_modif_3:// 取消
			UIDialog.closeDialog();
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	// *****************************网络请求返回操作
	// end******************************************
	// *****************************onActivityResult操作
	// begin******************************************
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case XIANGCE:
			try {
				startPhotoZoom(data.getData());
			} catch (Exception e) {
				// TODO: handle exception
				Toast.makeText(this, "您没有选择任何照片", Toast.LENGTH_LONG).show();
			}
			break;
		// 如果是调用相机拍照时
		case PAIZHAO:
			// File temp = new File(Environment.getExternalStorageDirectory()
			// + "/xiaoma.jpg");
			// 给图片设置名字和路径
			 if (resultCode == RESULT_OK) {

					File temp = new File(Environment.getExternalStorageDirectory()
							.getPath() + "/DCIM/Camera/" + timeString + ".jpg");
					startPhotoZoom(Uri.fromFile(temp));
                 }else {
					
				}
			break;
		case 23:// 定位
			if (data != null) {
				// address, lat, lng
				addressByGPS = data.getStringExtra("address");// 地址
				tv_xuanze.setText(addressByGPS);
				;
			}
			break;

		}
	}

	// *****************************onActivityResult操作
	// end******************************************
	// *****************************图像处理操作
	// begin******************************************
	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Bitmap photoBmp = null;
		if (uri != null) {
			try {
				photoBmp = getBitmapFormUri(B_1_1_Edit.this, uri);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Drawable drawable = new BitmapDrawable(photoBmp);

			/**
			 * 下面注释的方法是将裁剪之后的图片以Base64Coder的字符方式上 传到服务器，QQ头像上传采用的方法跟这个类似
			 */
			savaBitmap(photoBmp);
		}
	}

	public void createSDCardDir() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			// 创建一个文件夹对象，赋值为外部存储器的目录
			File sdcardDir = Environment.getExternalStorageDirectory();
			// 得到一个路径，内容是sdcard的文件夹路径和名字
			String path = sdcardDir.getPath() + "/DCIM/Camera";
			File path1 = new File(path);
			if (!path1.exists()) {
				// 若不存在，创建目录，可以在应用启动的时候创建
				path1.mkdirs();
			}
		}
	}

	// 将剪切后的图片保存到本地图片上！
	public void savaBitmap(Bitmap bitmap) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMddHHmmss");
		cutnameString = dateFormat.format(date);
		filename = Environment.getExternalStorageDirectory().getPath() + "/"
				+ cutnameString + ".jpg";
		Tools.Log("filename=" + filename);
		File f = new File(filename);
		putSharedPreferenceValue("headImg_filename", filename);

		FileOutputStream fOut = null;
		try {
			f.createNewFile();
			fOut = new FileOutputStream(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);// 把Bitmap对象解析成流

		try {
			fOut.flush();
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (islogo > 0) {// 0执照,1是logo
			tv_logo.setText(filename);
		} else {
			tv_zhizhao.setText(filename);
		}
		HttpUtils.uploadImgNew(uploadImgNew, f);
	}

	JsonHttpResponseHandler uploadImgNew = new JsonHttpResponseHandler() {

		public void onSuccess(int statusCode, Header[] headers,
				org.json.JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			String status = null;
			String desc = null;
			String fullPath = null;
			org.json.JSONObject data = null;
			try {
				status = response.getString("status");
				desc = response.getString("desc");
				fullPath = response.getString("data");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (status.equals("0"))// 成功
			{

				if (islogo > 0) {// 1是logo
					lg = fullPath;
				} else {// 0执照,
					zz = fullPath;
				}
				Toast.makeText(B_1_1_Edit.this, "图片上传成功！", Toast.LENGTH_LONG)
						.show();

			}else if (status.equals("111111")) {
				putSharedPreferenceValue("isLoged", "0");
				Toast.makeText(B_1_1_Edit.this, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
				Intent itziliao = new Intent();
				itziliao.setClass(B_1_1_Edit.this, DuoLeMainActivity.class);
				startActivity(itziliao);
			} else// 失败
			{
				Tools.Notic(B_1_1_Edit.this, desc + "", null);
			}

		}

	};

	/**
	 * 通过uri获取图片并进行压缩
	 * 
	 * @param uri
	 */
	public static Bitmap getBitmapFormUri(Activity ac, Uri uri)
			throws FileNotFoundException, IOException {
		InputStream input = ac.getContentResolver().openInputStream(uri);
		BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
		onlyBoundsOptions.inJustDecodeBounds = true;
		onlyBoundsOptions.inDither = true;// optional
		onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
		BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
		input.close();
		int originalWidth = onlyBoundsOptions.outWidth;
		int originalHeight = onlyBoundsOptions.outHeight;
		if ((originalWidth == -1) || (originalHeight == -1))
			return null;
		// 图片分辨率以480x800为标准
		float hh = 800f;// 这里设置高度为800f
		float ww = 480f;// 这里设置宽度为480f
		// 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
		int be = 1;// be=1表示不缩放
		if (originalWidth > originalHeight && originalWidth > ww) {// 如果宽度大的话根据宽度固定大小缩放
			be = (int) (originalWidth / ww);
		} else if (originalWidth < originalHeight && originalHeight > hh) {// 如果高度高的话根据宽度固定大小缩放
			be = (int) (originalHeight / hh);
		}
		if (be <= 0)
			be = 1;
		// 比例压缩
		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inSampleSize = be;// 设置缩放比例
		bitmapOptions.inDither = true;// optional
		bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// optional
		input = ac.getContentResolver().openInputStream(uri);
		Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
		input.close();

		return compressImage(bitmap);// 再进行质量压缩
	}

	/**
	 * 质量压缩方法
	 * 
	 * @param image
	 * @return
	 */
	public static Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			// 第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差 ，第三个参数：保存压缩后的数据的流
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
			options -= 10;// 每次都减少10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	JsonHttpResponseHandler addBrandList = new JsonHttpResponseHandler() {

		public void onSuccess(int statusCode, Header[] headers,
				org.json.JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			String status = null;
			String desc = null;
			try {
				status = response.getString("status");
				desc = response.getString("desc");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (status.equals("0"))// 成功
			{
				Toast.makeText(B_1_1_Edit.this, "商家入驻申请成功！,请耐心等候",
						Toast.LENGTH_LONG).show();
				B_1_1_Edit.this.finish();

			}else if (status.equals("111111")) {
				putSharedPreferenceValue("isLoged", "0");
				Toast.makeText(B_1_1_Edit.this, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
				Intent itziliao = new Intent();
				itziliao.setClass(B_1_1_Edit.this, DuoLeMainActivity.class);
				startActivity(itziliao);
			}  else// 失败
			{
				Tools.Notic(B_1_1_Edit.this, desc + "", null);
			}

		}

	};

	// 声明AMapLocationClient类对象
	public AMapLocationClient mLocationClient = null;
	// 声明定位回调监听器
	public AMapLocationListener mLocationListener = new AMapLocationListener() {

		@Override
		public void onLocationChanged(AMapLocation amapLocation) {
			// TODO Auto-generated method stub
			if (amapLocation != null) {
				if (amapLocation.getErrorCode() == 0) {
					// 可在其中解析amapLocation获取相应内容。
					double locationType = amapLocation.getLocationType();// 获取当前定位结果来源，如网络定位结果，详见定位类型表
					latitude = amapLocation.getLatitude();// 获取纬度
					lnglng = amapLocation.getLongitude();
					Log.e("Amap==经度：纬度", "locationType:" + locationType
							+ ",latitude:" + latitude);
				} else {
					// 定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
					Log.e("AmapError", "location Error, ErrCode:"
							+ amapLocation.getErrorCode() + ", errInfo:"
							+ amapLocation.getErrorInfo());
				}
			}
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		mLocationClient.stopLocation();
	}

}
