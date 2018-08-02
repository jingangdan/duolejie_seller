package com.lss.duolejie_seller.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.lss.duolejie_seller.data.AppValue;
/**
 * @author 作者 zhang
 * @version 创建时间：2015年1月6日 上午10:33:29 类说明 AsyncHttp 异步联网第三方库
 */
public class HttpUtils {
//	public static final String base_url = "http://115.28.21.137/mobile/";
	public static final String base_url = "http://121.196.193.134/xieshang/mobile/";
	private static AsyncHttpClient client = new AsyncHttpClient(); // 实例话对象
	static {
		client.setTimeout(10000); // 设置链接超时，如果不设置，默认为15s
		client.setMaxRetriesAndTimeout(3, 10000);
		 client.setEnableRedirects(true);

	}

//	public static void initClient(Context c) {
//		PersistentCookieStore myCookieStore = new PersistentCookieStore(c);
//		client.setCookieStore(myCookieStore);
//	}

	public static AsyncHttpClient getClient() {

		return client;
	}

	/**
	 * 登录
	 * 
	 * @param res
	 */
	public static void getDengLu(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/brandLogin.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}

	/**
	 * 申请提现
	 * 
	 * @param res
	 */
	public static void getTiXian(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/addExchangeDataBrand.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}

	/**
	 * 更新商户基本信息接口
	 * 
	 * @param res
	 */
	public static void getUpData(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/updateBrand.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}

	/**
	 * 商户退出接口
	 * 
	 * @param res
	 */
	public static void getTuiChu(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/brandLoginOut.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}

	/**
	 * 获取验证码接口
	 *  type:验证码类型4修改支付密码验证码5修改登录密码验证码
	 * @param res
	 */
	public static void getCode(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/getCode.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}

	/**
	 * 修改密码
	 *  type:验证码类型4修改支付密码验证码5修改登录密码验证码
	 * @param res
	 */
	public static void getXiuGaiMiMa(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/updateBrandPassword.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}

	/**
	 * 获取商家数据接口
	 *  type:验证码类型4修改支付密码验证码5修改登录密码验证码
	 * @param res
	 */
	public static void getGoodsBrandInfoData(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/getGoodsBrandInfoData.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}

	/**
	 * 商家申请二维码接口
	 *  type
	 * @param res
	 */
	public static void getLineBrandQRCode(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/getLineBrandQRCode.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}

	/**
	 * 上传图片
	 *  type
	 * @param res
	 */
	public static void uploadImgNew(AsyncHttpResponseHandler res,File f) {
		String url = "http://39.107.14.118/appInterface/uploadImgNew.jhtml";
		RequestParams params = new RequestParams();
		try {
			params.put("file", f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		client.post(url, params, res);
	}

	/**
	 * 更新商户基本信息接口
	 *  type
	 * @param res
	 */
	public static void updateBrand(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/updateBrand.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}

	public static void updateImgNew(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/updateImgNew.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}


	public static void uploadImgNew(AsyncHttpResponseHandler res,RequestParams params) {
		String url = "http://39.107.14.118/appInterface/uploadImgNew.jhtml";
		client.post(url, params, res);
	}


	/**
	 * 商户入驻多乐街接口
	 *  type
	 * @param res
	 */
	public static void addBrandList(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/addBrandList.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}

	/**
	 *  商户转账接口
	 * 
	 * @param res
	 */
	public static void lineBrandToUserMoney(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/lineBrandToUserMoney.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}
	

	/**
	 *  转账明细接口
	 * 
	 * @param res
	 */
	public static void getBrandExchangeDetailList(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/getBrandExchangeDetailList.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}
	/**
	 * 支出明细接口
	 * 
	 * @param res
	 */
	public static void getBrandMoneyDetailList(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/getBrandMoneyDetailList.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}

	/**
	 *  获取商家收款明细接口
	 * 
	 * @param res
	 */
	public static void getBrandReceivData(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/getBrandReceivData.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}


	/**
	 *  提现记录接口
	 * 
	 * @param res
	 */
	public static void getExchangeMoney(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/getExchangeMoney.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}


	/**
	 *  获取用户信息接口
	 * 
	 * @param res
	 */
	public static void getUserDataInfo(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/getUserDataInfo.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}


	/**
	 *  商户版扫描二维码获取商家数据
	 * 
	 * @param res
	 */
	public static void getLineBrandByQRCodeList(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/getLineBrandByQRCodeList.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}


	/**
	 *  商户版向商家支付接口
	 * 
	 * @param res
	 */
	public static void payMoneyLineBrandByQRCode(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/payMoneyLineBrandByQRCode.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}


	/**
	 *  商户版向商家支付接口
	 * 
	 * @param res
	 */
	public static void getLanguageMoneyList(AsyncHttpResponseHandler res,String key) {
		String url = "http://39.107.14.118/appInterface/getLanguageMoneyList.jhtml";
		RequestParams params = new RequestParams();
		params.put("param", key);
		client.post(url, params, res);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static String iterateParams(HashMap<String,String> params){
		String parameter = "";
		Iterator<String> iterator = params.keySet().iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			parameter += "&"+key+"="+params.get(key);
		}
		return parameter;
	}
	
}
