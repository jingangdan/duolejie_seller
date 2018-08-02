package com.lss.duolejie_seller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.external.maxwin.view.XListView.IXListViewListener;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.lss.duolejie_seller.adapter.ShouKuanAdapter;
import com.lss.duolejie_seller.adapter.TiXianAdapter;
import com.lss.duolejie_seller.adapter.ZhiChuAdapter;
import com.lss.duolejie_seller.adapter.ZhuanZhangAdapter;
import com.lss.duolejie_seller.base.BaseActivity;
import com.lss.duolejie_seller.utils.AESUtil;
import com.lss.duolejie_seller.utils.HttpUtils;
import com.lss.duolejie_seller.utils.JsonUtils;
import com.lss.duolejie_seller.utils.MyListView;
import com.lss.duolejie_seller.utils.RequestDailog;
import com.lss.duolejie_seller.utils.Tools;

public class B4_OrderActivity extends BaseActivity implements
		IXListViewListener {

	// order_state 订单状态（待发货:20,已发货:30,已完成:40,已取消:0）
	private static final int DAIFAHUO = 20;
	private static final int YIFAHUO = 30;
	private static final int YIWANCHENG = 40;
	private static final int YIQUXIAO = 0;

	private MyListView listview;
	ZhuanZhangAdapter adapter;
	ZhiChuAdapter adapter1;
	ShouKuanAdapter adapter2;
	TiXianAdapter adapter3;
	List<Map<String, Object>> dataList1 = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> dataList2 = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> dataList3 = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> dataList4 = new ArrayList<Map<String, Object>>();
	private ImageView orderstatus_back;
	private int status = 20;
	View v101, v102, v103, v104;

	LinearLayout ll_daifukuan, ll_daifahuo, ll_daishouhuo, ll_yishouhuo;

	String key;
	int curpage = 1;
	int curpage1 = 1;
	int curpage2 = 1;
	int curpage3 = 1;
	int listSize = 0;

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Map<String, String> map = new HashMap<String, String>();
		map.put("line_brand_id", getSharedPreferenceValue("id"));
		map.put("pageNum", curpage + "");
		map.put("limitNum", "10");
		Long time = null;
		String jm = null;
		try {

			time =System.currentTimeMillis();
	    	System.out.println(time);
	     	String miwen=getSharedPreferenceValue("id")+"|$|"+curpage + ""+"|$|"+"10"+"|$|"+time.toString();
	     	jm= AESUtil.encrypt(miwen);
			
		} catch (Exception e) {
			
		}
		
		map.put("request_time",""+time);
		map.put("sign", jm);
		String json = JsonUtils.toJson(map);
		HttpUtils.getBrandExchangeDetailList(getBrandExchangeDetailList, json);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		key = getSharedPreferenceValue("key");
		initView(R.layout.ui_order);
		initUI();
		setListener(ll_daifukuan, ll_daifahuo, ll_daishouhuo, ll_yishouhuo,
				orderstatus_back);
		v101.setVisibility(View.VISIBLE);
		v102.setVisibility(View.GONE);
		v103.setVisibility(View.GONE);
		v104.setVisibility(View.GONE);
		status = DAIFAHUO;
		listview.setPullLoadEnable(true);
		listview.setPullRefreshEnable(true);
		listview.setXListViewListener(this, 0);
		listview.setRefreshTime();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		v101 = findViewById(R.id.v101);
		v102 = findViewById(R.id.v102);
		v103 = findViewById(R.id.v103);
		v104 = findViewById(R.id.v104);
		orderstatus_back = (ImageView) findViewById(R.id.orderstatus_back);
		listview = (MyListView) findViewById(R.id.listview_orderlist);
		ll_daifukuan = (LinearLayout) findViewById(R.id.ll_daifukuan);
		ll_daifahuo = (LinearLayout) findViewById(R.id.ll_daifahuo);
		ll_daishouhuo = (LinearLayout) findViewById(R.id.ll_daishouhuo);
		ll_yishouhuo = (LinearLayout) findViewById(R.id.ll_yishouhuo);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Long time = null;
		String jm = null;
		switch (v.getId()) {
		case R.id.ll_daifukuan:// 转账
			curpage = 1;
			dataList1.clear();
			curpage1 = 1;
			dataList2.clear();
			curpage2 = 1;
			dataList3.clear();
			curpage3 = 1;
			dataList4.clear();
			v101.setVisibility(View.VISIBLE);
			v102.setVisibility(View.GONE);
			v103.setVisibility(View.GONE);
			v104.setVisibility(View.GONE);
			status = DAIFAHUO;
			Map<String, String> map = new HashMap<String, String>();
			map.put("line_brand_id", getSharedPreferenceValue("id"));
			map.put("pageNum", curpage + "");
			map.put("limitNum", "10");
			try {

				time =System.currentTimeMillis();
		    	System.out.println(time);
		     	String miwen=getSharedPreferenceValue("id")+"|$|"+curpage + ""+"|$|"+"10"+"|$|"+time.toString();
		     	jm= AESUtil.encrypt(miwen);
				
			} catch (Exception e) {
				
			}
			
			map.put("request_time",""+time);
			map.put("sign", jm);
			String json = JsonUtils.toJson(map);
			HttpUtils.getBrandExchangeDetailList(getBrandExchangeDetailList,
					json);
			break;
		case R.id.ll_daifahuo:// 支出
			curpage = 1;
			dataList1.clear();
			curpage1 = 1;
			dataList2.clear();
			curpage2 = 1;
			dataList3.clear();
			curpage3 = 1;
			dataList4.clear();
			v101.setVisibility(View.GONE);
			v102.setVisibility(View.VISIBLE);
			v103.setVisibility(View.GONE);
			v104.setVisibility(View.GONE);
			status = YIFAHUO;
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("line_brand_id", getSharedPreferenceValue("id"));
			map1.put("pageNum", curpage1 + "");
			map1.put("limitNum", "10");
			try {

				time =System.currentTimeMillis();
		    	System.out.println(time);
		     	String miwen=getSharedPreferenceValue("id")+"|$|"+curpage1 + ""+"|$|"+"10"+"|$|"+time.toString();
		     	jm= AESUtil.encrypt(miwen);
				
			} catch (Exception e) {
				
			}
			
			map1.put("request_time",""+time);
			map1.put("sign", jm);
			String json1 = JsonUtils.toJson(map1);
			HttpUtils.getBrandMoneyDetailList(getBrandMoneyDetailList, json1);
			break;
		case R.id.ll_daishouhuo:// 收款
			curpage = 1;
			dataList1.clear();
			curpage1 = 1;
			dataList2.clear();
			curpage2 = 1;
			dataList3.clear();
			curpage3 = 1;
			dataList4.clear();
			v101.setVisibility(View.GONE);
			v102.setVisibility(View.GONE);
			v103.setVisibility(View.VISIBLE);
			v104.setVisibility(View.GONE);
			status = YIWANCHENG;
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("line_brand_id", getSharedPreferenceValue("id"));
			map2.put("brand_token", getSharedPreferenceValue("brand_token"));
			map2.put("pageNum", curpage2 + "");
			map2.put("limitNum", "10");
			try {

				time =System.currentTimeMillis();
		    	System.out.println(time);
		     	String miwen=getSharedPreferenceValue("id")+"|$|"+getSharedPreferenceValue("brand_token")+"|$|"+curpage2 + ""+"|$|"+"10"+"|$|"+time.toString();
		     	jm= AESUtil.encrypt(miwen);
				
			} catch (Exception e) {
				
			}
			
			map2.put("request_time",""+time);
			map2.put("sign", jm);
			String json2 = JsonUtils.toJson(map2);
			HttpUtils.getBrandReceivData(getBrandReceivData, json2);
			break;
		case R.id.ll_yishouhuo:// 提现
			curpage = 1;
			dataList1.clear();
			curpage1 = 1;
			dataList2.clear();
			curpage2 = 1;
			dataList3.clear();
			curpage3 = 1;
			dataList4.clear();
			v101.setVisibility(View.GONE);
			v102.setVisibility(View.GONE);
			v103.setVisibility(View.GONE);
			v104.setVisibility(View.VISIBLE);
			status = YIQUXIAO;
			Map<String, String> map3 = new HashMap<String, String>();
			map3.put("line_brand_id", getSharedPreferenceValue("id"));
			map3.put("brand_token", getSharedPreferenceValue("brand_token"));
			map3.put("pageNum", curpage3 + "");
			map3.put("limitNum", "10");
			try {

				time =System.currentTimeMillis();
		    	System.out.println(time);
		     	String miwen=getSharedPreferenceValue("id")+"|$|"+getSharedPreferenceValue("brand_token")+"|$|"+curpage3 + ""+"|$|"+"10"+"|$|"+time.toString();
		     	jm= AESUtil.encrypt(miwen);
				
			} catch (Exception e) {
				
			}
			
			map3.put("request_time",""+time);
			map3.put("sign", jm);

			String json3 = JsonUtils.toJson(map3);
			HttpUtils.getExchangeMoney(getExchangeMoney, json3);
			break;
		case R.id.orderstatus_back:
			B4_OrderActivity.this.finish();
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	/* 转账 */
	JsonHttpResponseHandler getBrandExchangeDetailList = new JsonHttpResponseHandler() {

		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			String status = null;
			String desc = null;
			JSONObject dataxx = null;
			try {
				status = response.getString("status");
				desc = response.getString("desc");
				dataxx = response.getJSONObject("data");

			} catch (JSONException e) {
				// TODO Auto-generated catch block

			}
			if (status.equals("0"))// 成功
			{

				try {
					JSONArray jsonarry = dataxx
							.getJSONArray("brandExchangeList");
					listSize = jsonarry.length();
					for (int j = 0; j < jsonarry.length(); j++) {
						Map<String, Object> map = new HashMap();
						JSONObject orderJsonObject = jsonarry.getJSONObject(j);
						map.put("nick_name", orderJsonObject.get("nick_name"));
						map.put("create_time",
								orderJsonObject.get("create_time"));
						map.put("total_money",
								orderJsonObject.get("total_money"));
						map.put("user_header",
								orderJsonObject.get("user_header"));
						dataList1.add(map);
					}
					Log.e("dataList", dataList1 + "");
					adapter1 = new ZhiChuAdapter(B4_OrderActivity.this,
							dataList2, key);
					listview.setAdapter(adapter1);
					adapter1.notifyDataSetChanged();

					adapter2 = new ShouKuanAdapter(B4_OrderActivity.this,
							dataList3, key);
					listview.setAdapter(adapter2);
					adapter2.notifyDataSetChanged();

					adapter3 = new TiXianAdapter(B4_OrderActivity.this,
							dataList4, key);
					listview.setAdapter(adapter3);
					adapter3.notifyDataSetChanged();

					adapter = new ZhuanZhangAdapter(B4_OrderActivity.this,
							dataList1, key);
					listview.setAdapter(adapter);
					adapter.notifyDataSetChanged();


				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else if (status.equals("111111")) {
				putSharedPreferenceValue("isLoged", "0");
				Toast.makeText(B4_OrderActivity.this, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
				Intent itziliao = new Intent();
				itziliao.setClass(B4_OrderActivity.this, DuoLeMainActivity.class);
				startActivity(itziliao);
			}  else// 失败
			{
				try {
					if (curpage>=1) {
						SdfeEdrw1();
						SdfeEdrw2();
						SdfeEdrw3();
						
						adapter = new ZhuanZhangAdapter(B4_OrderActivity.this,
								dataList1, key);
						listview.setAdapter(adapter);
						adapter.notifyDataSetChanged();
					}else {
						SdfeEdrw();
					}
				} catch (Exception e) {
				}
				Tools.Notic(B4_OrderActivity.this, desc + "", null);
				// Tools.Notic(B5_MyActivity.this, error+"", null);
			}

		}

	};

	/* 支出 */
	JsonHttpResponseHandler getBrandMoneyDetailList = new JsonHttpResponseHandler() {

		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			String status = null;
			String desc = null;
			JSONObject dataxx = null;
			try {
				status = response.getString("status");
				desc = response.getString("desc");
				dataxx = response.getJSONObject("data");

			} catch (JSONException e) {
				// TODO Auto-generated catch block

			}
			if (status.equals("0"))// 成功
			{

				try {
					JSONArray jsonarry = dataxx.getJSONArray("brandList");
					listSize = jsonarry.length();
					for (int j = 0; j < jsonarry.length(); j++) {
						Map<String, Object> map = new HashMap();
						JSONObject orderJsonObject = jsonarry.getJSONObject(j);
						map.put("brand_name", orderJsonObject.get("brand_name"));
						map.put("create_time",
								orderJsonObject.get("create_time"));
						map.put("all_price", orderJsonObject.get("all_price"));
						map.put("brand_header",
								orderJsonObject.get("brand_header"));
						dataList2.add(map);
					}
					Log.e("dataList", dataList2 + "");
					adapter = new ZhuanZhangAdapter(B4_OrderActivity.this,
							dataList1, key);
					listview.setAdapter(adapter);
					adapter.notifyDataSetChanged();

					adapter2 = new ShouKuanAdapter(B4_OrderActivity.this,
							dataList3, key);
					listview.setAdapter(adapter2);
					adapter2.notifyDataSetChanged();

					adapter3 = new TiXianAdapter(B4_OrderActivity.this,
							dataList4, key);
					listview.setAdapter(adapter3);
					adapter3.notifyDataSetChanged();

					adapter1 = new ZhiChuAdapter(B4_OrderActivity.this,
							dataList2, key);
					listview.setAdapter(adapter1);
					adapter1.notifyDataSetChanged();
					/*
					 * 
					 * try { ImageLoader.getInstance().displayImage(
					 * getSharedPreferenceValue("header"), view_view,
					 * ImageOptions.getOpstion(), animateFirstListener); } catch
					 * (Exception e) {
					 * 
					 * }
					 */

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else if (status.equals("111111")) {
				putSharedPreferenceValue("isLoged", "0");
				Toast.makeText(B4_OrderActivity.this, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
				Intent itziliao = new Intent();
				itziliao.setClass(B4_OrderActivity.this, DuoLeMainActivity.class);
				startActivity(itziliao);
			}  else// 失败
			{
				try {
					if (curpage>=1) {
						SdfeEdrw();
						SdfeEdrw2();
						SdfeEdrw3();
						adapter1 = new ZhiChuAdapter(B4_OrderActivity.this,
								dataList2, key);
						listview.setAdapter(adapter1);
						adapter1.notifyDataSetChanged();
					}else {
						SdfeEdrw1();
					}
				} catch (Exception e) {
				}
				Tools.Notic(B4_OrderActivity.this, desc + "", null);
				// Tools.Notic(B5_MyActivity.this, error+"", null);
			}

		}

	};

	/* 收款 */
	JsonHttpResponseHandler getBrandReceivData = new JsonHttpResponseHandler() {

		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			String status = null;
			String desc = null;
			JSONObject dataxx = null;
			try {
				status = response.getString("status");
				desc = response.getString("desc");
				dataxx = response.getJSONObject("data");

			} catch (JSONException e) {
				// TODO Auto-generated catch block

			}
			if (status.equals("0"))// 成功
			{

				try {
					JSONArray jsonarry = dataxx
							.getJSONArray("lineBrandQrcodeList");
					listSize = jsonarry.length();
					for (int j = 0; j < jsonarry.length(); j++) {
						Map<String, Object> map = new HashMap();
						JSONObject orderJsonObject = jsonarry.getJSONObject(j);
						map.put("nick_name", orderJsonObject.get("nick_name"));
						map.put("user_header",
								orderJsonObject.get("user_header"));
						map.put("brand_id", orderJsonObject.get("brand_id"));
						map.put("pay_money", orderJsonObject.get("pay_money"));
						map.put("create_time",
								orderJsonObject.get("create_time"));
						map.put("id", orderJsonObject.get("id"));
						dataList3.add(map);
					}
					Log.e("dataList", dataList3 + "");
					adapter = new ZhuanZhangAdapter(B4_OrderActivity.this,
							dataList1, key);
					listview.setAdapter(adapter);
					adapter.notifyDataSetChanged();

					adapter1 = new ZhiChuAdapter(B4_OrderActivity.this,
							dataList2, key);
					listview.setAdapter(adapter1);
					adapter1.notifyDataSetChanged();

					adapter3 = new TiXianAdapter(B4_OrderActivity.this,
							dataList4, key);
					listview.setAdapter(adapter3);
					adapter3.notifyDataSetChanged();

					adapter2 = new ShouKuanAdapter(B4_OrderActivity.this,
							dataList3, key);
					listview.setAdapter(adapter2);
					adapter2.notifyDataSetChanged();
					/*
					 * 
					 * try { ImageLoader.getInstance().displayImage(
					 * getSharedPreferenceValue("header"), view_view,
					 * ImageOptions.getOpstion(), animateFirstListener); } catch
					 * (Exception e) {
					 * 
					 * }
					 */

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else if (status.equals("111111")) {
				putSharedPreferenceValue("isLoged", "0");
				Toast.makeText(B4_OrderActivity.this, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
				Intent itziliao = new Intent();
				itziliao.setClass(B4_OrderActivity.this, DuoLeMainActivity.class);
				startActivity(itziliao);
			}  else// 失败
			{
				try {
					if (curpage>=1) {
						SdfeEdrw();
						SdfeEdrw1();
						SdfeEdrw3();
						
						adapter2 = new ShouKuanAdapter(B4_OrderActivity.this,
								dataList3, key);
						listview.setAdapter(adapter2);
						adapter2.notifyDataSetChanged();
					}else {
						SdfeEdrw2();
					}
				} catch (Exception e) {
				}
				Tools.Notic(B4_OrderActivity.this, desc + "", null);
				// Tools.Notic(B5_MyActivity.this, error+"", null);
			}

		}

	};

	/* 提现 */
	JsonHttpResponseHandler getExchangeMoney = new JsonHttpResponseHandler() {

		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			String status = null;
			String desc = null;
			JSONObject dataxx = null;
			try {
				status = response.getString("status");
				desc = response.getString("desc");
				dataxx = response.getJSONObject("data");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
			}
			if (status.equals("0"))// 成功
			{
				try {
					JSONArray jsonarry = dataxx
							.getJSONArray("brandExchangeList");
					listSize = jsonarry.length();
					for (int j = 0; j < jsonarry.length(); j++) {
						Map<String, Object> map = new HashMap();
						JSONObject orderJsonObject = jsonarry.getJSONObject(j);
						map.put("id", orderJsonObject.get("id"));
						map.put("create_time",
								orderJsonObject.get("create_time"));
						map.put("brand_id", orderJsonObject.get("brand_id"));
						map.put("exchange_type",
								orderJsonObject.get("exchange_type"));
						map.put("status", orderJsonObject.get("status"));
						map.put("surplusMoney",
								orderJsonObject.get("surplusMoney"));
						map.put("deductionMoney",
								orderJsonObject.get("deductionMoney"));
						dataList4.add(map);
					}
					Log.e("dataList", dataList4 + "");
					adapter = new ZhuanZhangAdapter(B4_OrderActivity.this,
							dataList1, key);
					listview.setAdapter(adapter);
					adapter.notifyDataSetChanged();

					adapter1 = new ZhiChuAdapter(B4_OrderActivity.this,
							dataList2, key);
					listview.setAdapter(adapter1);
					adapter1.notifyDataSetChanged();

					adapter2 = new ShouKuanAdapter(B4_OrderActivity.this,
							dataList3, key);
					listview.setAdapter(adapter2);
					adapter2.notifyDataSetChanged();

					adapter3 = new TiXianAdapter(B4_OrderActivity.this,
							dataList4, key);
					listview.setAdapter(adapter3);
					adapter3.notifyDataSetChanged();
					/*
					 * 
					 * try { ImageLoader.getInstance().displayImage(
					 * getSharedPreferenceValue("header"), view_view,
					 * ImageOptions.getOpstion(), animateFirstListener); } catch
					 * (Exception e) {
					 * 
					 * }
					 */

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else if (status.equals("111111")) {
				putSharedPreferenceValue("isLoged", "0");
				Toast.makeText(B4_OrderActivity.this, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
				Intent itziliao = new Intent();
				itziliao.setClass(B4_OrderActivity.this, DuoLeMainActivity.class);
				startActivity(itziliao);
			}  else// 失败
			{
				try {
					int i = curpage;
					if (curpage>=1) {
						SdfeEdrw();
						SdfeEdrw1();
						SdfeEdrw2();
						adapter3 = new TiXianAdapter(B4_OrderActivity.this,
								dataList4, key);
						listview.setAdapter(adapter3);
						adapter3.notifyDataSetChanged();
					}else {
						SdfeEdrw3();
					}
				} catch (Exception e) {
				}
				Tools.Notic(B4_OrderActivity.this, desc + "", null);
				// Tools.Notic(B5_MyActivity.this, error+"", null);
			}

		}

	};

	@Override
	public void onRefresh(int id) {
		if (status == 20) {
			dataList1.clear();
			curpage = 1;
			Map<String, String> map = new HashMap<String, String>();
			map.put("line_brand_id", getSharedPreferenceValue("id"));
			map.put("pageNum", curpage + "");
			map.put("limitNum", "10");
			Long time = null;
			String jm = null;
			try {

				time =System.currentTimeMillis();
		    	System.out.println(time);
		     	String miwen=getSharedPreferenceValue("id")+"|$|"+curpage + ""+"|$|"+"10"+"|$|"+time.toString();
		     	jm= AESUtil.encrypt(miwen);
				
			} catch (Exception e) {
				
			}
			
			map.put("request_time",""+time);
			map.put("sign", jm);

			String json = JsonUtils.toJson(map);
			HttpUtils.getBrandExchangeDetailList(getBrandExchangeDetailList,
					json);
		} else if (status == 30) {
			curpage1 = 1;
			dataList2.clear();
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("line_brand_id", getSharedPreferenceValue("id"));
			map1.put("pageNum", curpage1 + "");
			map1.put("limitNum", "10");
			Long time = null;
			String jm = null;
			try {

				time =System.currentTimeMillis();
		    	System.out.println(time);
		     	String miwen=getSharedPreferenceValue("id")+"|$|"+curpage1 + ""+"|$|"+"10"+"|$|"+time.toString();
		     	jm= AESUtil.encrypt(miwen);
				
			} catch (Exception e) {
				
			}
			
			map1.put("request_time",""+time);
			map1.put("sign", jm);
			String json1 = JsonUtils.toJson(map1);
			HttpUtils.getBrandMoneyDetailList(getBrandMoneyDetailList, json1);
		} else if (status == 40) {
			curpage2 = 1;
			dataList3.clear();
			Map<String, String> map2 = new HashMap<String, String>();
			map2.put("line_brand_id", getSharedPreferenceValue("id"));
			map2.put("brand_token", getSharedPreferenceValue("brand_token"));
			map2.put("pageNum", curpage2 + "");
			map2.put("limitNum", "10");
			Long time = null;
			String jm = null;
			try {

				time =System.currentTimeMillis();
		    	System.out.println(time);
		     	String miwen=getSharedPreferenceValue("id")+"|$|"+getSharedPreferenceValue("brand_token")+"|$|"+curpage2 + ""+"|$|"+"10"+"|$|"+time.toString();
		     	jm= AESUtil.encrypt(miwen);
				
			} catch (Exception e) {
				
			}
			
			map2.put("request_time",""+time);
			map2.put("sign", jm);
			String json2 = JsonUtils.toJson(map2);
			HttpUtils.getBrandReceivData(getBrandReceivData, json2);
		} else {
			dataList4.clear();
			curpage3 = 1;
			Map<String, String> map3 = new HashMap<String, String>();
			map3.put("line_brand_id", getSharedPreferenceValue("id"));
			map3.put("brand_token", getSharedPreferenceValue("brand_token"));
			map3.put("pageNum", curpage3 + "");
			map3.put("limitNum", "10");
			Long time = null;
			String jm = null;
			try {

				time =System.currentTimeMillis();
		    	System.out.println(time);
		     	String miwen=getSharedPreferenceValue("id")+"|$|"+getSharedPreferenceValue("brand_token")+"|$|"+curpage3 + ""+"|$|"+"10"+"|$|"+time.toString();
		     	jm= AESUtil.encrypt(miwen);
				
			} catch (Exception e) {
				
			}
			
			map3.put("request_time",""+time);
			map3.put("sign", jm);
			String json3 = JsonUtils.toJson(map3);
			HttpUtils.getExchangeMoney(getExchangeMoney, json3);
		}
	}

	@Override
	public void onLoadMore(int id) {
		if (status == 20) {

//			if (listSize > 10) {
				curpage++;
				Map<String, String> map = new HashMap<String, String>();
				map.put("line_brand_id", getSharedPreferenceValue("id"));
				map.put("pageNum", curpage + "");
				map.put("limitNum", "10");
				Long time = null;
				String jm = null;
				try {

					time =System.currentTimeMillis();
			    	System.out.println(time);
			     	String miwen=getSharedPreferenceValue("id")+"|$|"+curpage + ""+"|$|"+"10"+"|$|"+time.toString();
			     	jm= AESUtil.encrypt(miwen);
					
				} catch (Exception e) {
					
				}
				
				map.put("request_time",""+time);
				map.put("sign", jm);
				String json = JsonUtils.toJson(map);
				HttpUtils.getBrandExchangeDetailList(
						getBrandExchangeDetailList, json);
//			} else {
//				Toast.makeText(B4_OrderActivity.this, "只有这些转账数据", 500).show();
//			}
		} else if (status == 30) {

//			if (listSize > 10) {
				curpage1++;
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("line_brand_id", getSharedPreferenceValue("id"));
				map1.put("pageNum", curpage1 + "");
				map1.put("limitNum", "10");
				Long time = null;
				String jm = null;
				try {

					time =System.currentTimeMillis();
			    	System.out.println(time);
			     	String miwen=getSharedPreferenceValue("id")+"|$|"+curpage1 + ""+"|$|"+"10"+"|$|"+time.toString();
			     	jm= AESUtil.encrypt(miwen);
					
				} catch (Exception e) {
					
				}
				
				map1.put("request_time",""+time);
				map1.put("sign", jm);
				String json1 = JsonUtils.toJson(map1);
				HttpUtils.getBrandMoneyDetailList(getBrandMoneyDetailList,
						json1);
//			} else {
//				Toast.makeText(B4_OrderActivity.this, "只有这些支出数据", 500).show();
//			}
		} else if (status == 40) {

//			if (listSize > 10) {
				curpage2++;
				Map<String, String> map2 = new HashMap<String, String>();
				map2.put("line_brand_id", getSharedPreferenceValue("id"));
				map2.put("brand_token", getSharedPreferenceValue("brand_token"));
				map2.put("pageNum", curpage2 + "");
				map2.put("limitNum", "10");
				Long time = null;
				String jm = null;
				try {

					time =System.currentTimeMillis();
			    	System.out.println(time);
			     	String miwen=getSharedPreferenceValue("id")+"|$|"+getSharedPreferenceValue("brand_token")+"|$|"+curpage2 + ""+"|$|"+"10"+"|$|"+time.toString();
			     	jm= AESUtil.encrypt(miwen);
					
				} catch (Exception e) {
					
				}
				
				map2.put("request_time",""+time);
				map2.put("sign", jm);
				String json2 = JsonUtils.toJson(map2);
				HttpUtils.getBrandReceivData(getBrandReceivData, json2);
//			} else {
//				Toast.makeText(B4_OrderActivity.this, "只有这些收款数据", 500).show();
//			}
		} else {
//			if (listSize > 10) {
				curpage3++;

				Map<String, String> map3 = new HashMap<String, String>();
				map3.put("line_brand_id", getSharedPreferenceValue("id"));
				map3.put("brand_token", getSharedPreferenceValue("brand_token"));
				map3.put("pageNum", curpage3 + "");
				map3.put("limitNum", "10");
				Long time = null;
				String jm = null;
				try {

					time =System.currentTimeMillis();
			    	System.out.println(time);
			     	String miwen=getSharedPreferenceValue("id")+"|$|"+getSharedPreferenceValue("brand_token")+"|$|"+curpage3 + ""+"|$|"+"10"+"|$|"+time.toString();
			     	jm= AESUtil.encrypt(miwen);
					
				} catch (Exception e) {
					
				}
				
				map3.put("request_time",""+time);
				map3.put("sign", jm);
				String json3 = JsonUtils.toJson(map3);
				HttpUtils.getExchangeMoney(getExchangeMoney, json3);
//			} else {
//				Toast.makeText(B4_OrderActivity.this, "只有这些提现数据", 500).show();
//			}
		}
	}
	
	
	public void SdfeEdrw(){

		curpage = 1;
		dataList1.clear();
		
		adapter = new ZhuanZhangAdapter(B4_OrderActivity.this,
				dataList1, key);
		listview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
	public void SdfeEdrw1(){

		curpage1 = 1;
		dataList2.clear();

		adapter1 = new ZhiChuAdapter(B4_OrderActivity.this,
				dataList2, key);
		listview.setAdapter(adapter1);
		adapter1.notifyDataSetChanged();
	}
	public void SdfeEdrw2(){

		curpage2 = 1;
		dataList3.clear();
		
		adapter2 = new ShouKuanAdapter(B4_OrderActivity.this,
				dataList3, key);
		listview.setAdapter(adapter2);
		adapter2.notifyDataSetChanged();
	}
	public void SdfeEdrw3(){

		curpage3 = 1;
		dataList4.clear();
		

		adapter3 = new TiXianAdapter(B4_OrderActivity.this,
				dataList4, key);
		listview.setAdapter(adapter3);
		adapter3.notifyDataSetChanged();
	}
	
	
	
	
	
}
