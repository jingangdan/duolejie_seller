package com.lss.duolejie_seller;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.lss.duolejie_seller.base.BaseActivity;
import com.lss.duolejie_seller.utils.AESUtil;
import com.lss.duolejie_seller.utils.HttpUtils;
import com.lss.duolejie_seller.utils.JsonUtils;
import com.lss.duolejie_seller.utils.RequestDailog;
import com.lss.duolejie_seller.utils.Tools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author xiaomai
 * 
 */
public class IntentActivity extends BaseActivity {
	private TextView tvString;
	private ImageView im_loginback;
	private String smid = "", shid = "";
	private TextView tv_intent, tv_save;
	private EditText et_sjh, et_jianjie,et_zfmm;
	private String money="",skrname="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_intent);
		initView();
		Intent in = getIntent();
		smid = in.getStringExtra("string");

		Map<String, String> map = new HashMap<String, String>();
		map.put("line_brand_id", getSharedPreferenceValue("id"));
		map.put("brand_token", getSharedPreferenceValue("brand_token"));
		map.put("qrcode_content", smid);
		Long time = null;
		String jm = null;
		try {

			time =System.currentTimeMillis();
	    	System.out.println(time);
	     	String miwen=getSharedPreferenceValue("id")+"|$|"+getSharedPreferenceValue("brand_token")+"|$|"+smid+"|$|"+time.toString();
	     	jm= AESUtil.encrypt(miwen);
			
		} catch (Exception e) {
			
		}
		
		map.put("request_time",""+time);
		map.put("sign", jm);
		String json = JsonUtils.toJson(map);
		HttpUtils.getLineBrandByQRCodeList(getLineBrandByQRCodeList, json);
	}

	private void initView() {
		im_loginback = (ImageView) findViewById(R.id.im_loginback);
		tv_intent = (TextView) findViewById(R.id.tv_intent);
		et_sjh = (EditText) findViewById(R.id.et_sjh);
		et_jianjie = (EditText) findViewById(R.id.et_jianjie);
		et_zfmm = (EditText) findViewById(R.id.et_zfmm);
		tv_save = (TextView) findViewById(R.id.tv_save);
		setListener(im_loginback, tv_save);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.im_loginback:
			IntentActivity.this.finish();
			break;
		case R.id.tv_save:

			money = et_sjh.getText().toString().trim();
			String zhifumima = et_zfmm.getText().toString().trim();
			String jianjie = et_jianjie.getText().toString().trim();
			
			if (TextUtils.isEmpty(money)) {
				Toast.makeText(IntentActivity.this, "金额不能为空", Toast.LENGTH_LONG).show();
			} else if (TextUtils.isEmpty(zhifumima)) {
				Toast.makeText(IntentActivity.this, "支付密码不能为空", Toast.LENGTH_LONG).show();
			}else{
//				RequestDailog.showDialog(this, "正在登录请稍后");
				Map<String, String> map = new HashMap<String, String>();
				map.put("line_brand_id", getSharedPreferenceValue("id"));
				map.put("brand_token", getSharedPreferenceValue("brand_token"));
				map.put("receive_brand_id", shid);
				map.put("money", money);
				map.put("remark", jianjie);
				map.put("pay_password", zhifumima);

				Long time = null;
							String jm = null;
							try {

								time =System.currentTimeMillis();
						    	System.out.println(time);
						     	String miwen=getSharedPreferenceValue("id")+"|$|"+getSharedPreferenceValue("brand_token")+"|$|"+shid+"|$|"+money+"|$|"+jianjie+"|$|"+zhifumima+"|$|"+time.toString();
						     	jm= AESUtil.encrypt(miwen);
								
							} catch (Exception e) {
								
							}
							
							map.put("request_time",""+time);
							map.put("sign", jm);
				String json = JsonUtils.toJson(map);
				HttpUtils.payMoneyLineBrandByQRCode(payMoneyLineBrandByQRCode,json);
			}
			break;
		default:
			break;
		}
		super.onClick(v);
	}

	JsonHttpResponseHandler payMoneyLineBrandByQRCode = new JsonHttpResponseHandler() {

		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			String status = null;
			String desc = null;
			JSONObject data = null;
			try {
				status = response.getString("status");
				desc = response.getString("desc");

			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (status.equals("0"))// 成功
			{
				try {
					data = response.getJSONObject("data");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}

				Intent instss = new Intent(IntentActivity.this,ZhuanZhang3Acitivity.class);
					instss.putExtra("jine",money );
					instss.putExtra("shoukuanren", skrname);
					try {
						instss.putExtra("touxiang",  data.getJSONObject("goodsBrandMap").getString("brand_header"));
					} catch (JSONException e) {
						
					}
					startActivity(instss);
					IntentActivity.this.finish();

			}
			else if (status.equals("111111")) {
				putSharedPreferenceValue("isLoged", "0");
				Toast.makeText(IntentActivity.this, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
				Intent itziliao = new Intent();
				itziliao.setClass(IntentActivity.this, DuoLeMainActivity.class);
				startActivity(itziliao);
			}   else// 失败
			{
				Tools.Notic(IntentActivity.this, desc + "", null);
			}

		}

	};

	JsonHttpResponseHandler getLineBrandByQRCodeList = new JsonHttpResponseHandler() {

		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			String status = null;
			String desc = null;
			JSONObject data = null;
			try {
				status = response.getString("status");
				desc = response.getString("desc");
				data = response.getJSONObject("data");

			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (status.equals("0"))// 成功
			{
				try {
					data = response.getJSONObject("data");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				// data.getString("latitude")
				try {
					JSONObject jobsj = data.getJSONObject("goodsBrandMap");
					skrname = jobsj.getString("brand_name");
					tv_intent.setText(skrname);
					shid = jobsj.getString("id");
				} catch (JSONException e) {

				}

			} 
			else if (status.equals("111111")) {
				putSharedPreferenceValue("isLoged", "0");
				Toast.makeText(IntentActivity.this, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
				Intent itziliao = new Intent();
				itziliao.setClass(IntentActivity.this, DuoLeMainActivity.class);
				startActivity(itziliao);
			}  else// 失败
			{
				Tools.Notic(IntentActivity.this, desc + "", null);
			}

		}

	};

}
