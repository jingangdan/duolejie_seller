package com.lss.duolejie_seller;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.lss.duolejie_seller.base.BaseActivity;
import com.lss.duolejie_seller.data.AppValue;
import com.lss.duolejie_seller.utils.AESUtil;
import com.lss.duolejie_seller.utils.HttpUtils;
import com.lss.duolejie_seller.utils.HttpUtilsx;
import com.lss.duolejie_seller.utils.JsonUtils;
import com.lss.duolejie_seller.utils.RequestDailog;
import com.lss.duolejie_seller.utils.Tools;
import com.lss.duolejie_seller.utils.ToolsUtil;

public class XiuGaiZhiFuMiMaActivity extends BaseActivity {
	private Context mContext = XiuGaiZhiFuMiMaActivity.this;
	private Button btn_queren;
	private LinearLayout login_duanxin;
	private TextView txt_yanzhengma, tv_getCode_time,et_phone;
	private ImageView im_mimaback;
	private EditText et_new_mima1_byYanZhengMa,
			et_new_mima2_byYanZhengMa,et_yanzhengma;
	public String verify_code = "";
	/**
	 * 是否通过验证
	 */
	private boolean isYanZheng = false;
	private int time = TIME_INIT;
	private static final int TIME_INIT = 60; // 初始化时间用
	private Handler handler = new Handler();
	private String text_NewMima1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_e2_3_xiugaizhifumima);
	}

	@Override
	public void initView(int viewId) {
		super.initView(viewId);
		login_duanxin = (LinearLayout) findViewById(R.id.login_duanxinkuaijie);

		txt_yanzhengma = (TextView) findViewById(R.id.txt_yanzhengma);
		et_phone = (TextView) findViewById(R.id.et_phone);
		et_yanzhengma = (EditText) findViewById(R.id.et_yanzhengma);
		et_phone.setText(getSharedPreferenceValue("brand_tel"));

		btn_queren = (Button) findViewById(R.id.btn_queren);
		im_mimaback = (ImageView) this.findViewById(R.id.im_mimaback);
		tv_getCode_time = (TextView) findViewById(R.id.tv_getCode_time);

		et_new_mima1_byYanZhengMa = (EditText) findViewById(R.id.et_new_mima1_byYanZhengMa);
		et_new_mima2_byYanZhengMa = (EditText) findViewById(R.id.et_new_mima2_byYanZhengMa);
		setListener(btn_queren, im_mimaback,txt_yanzhengma, et_yanzhengma);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		Intent intent = null;
		switch (v.getId()) {
		case R.id.im_mimaback:// 返回
			finish();
			break;
		case R.id.txt_yanzhengma:// 获取验证码
			if (!TextUtils.isEmpty(et_phone.getText().toString())) {
				//
				Map<String, String> map = new HashMap<String, String>();
				map.put("app_login_id", getSharedPreferenceValue("brand_tel"));
				map.put("type", "4");// type:验证码类型4修改支付密码验证码5修改登录密码验证码
	Long timexx = null;
				String jmxx = null;
				try {

					timexx =System.currentTimeMillis();
			     	String miwen=getSharedPreferenceValue("brand_tel")+"|$|"+"4"+"|$|"+timexx.toString();
			     	jmxx= AESUtil.encrypt(miwen);
					
				} catch (Exception e) {
					
				}
				
				map.put("request_time",""+timexx);
				map.put("sign", jmxx);
				String json = JsonUtils.toJson(map);
				HttpUtils.getCode(getCode,json);
				
				tv_getCode_time.setVisibility(View.VISIBLE);
				txt_yanzhengma.setVisibility(View.GONE);
				time = TIME_INIT;
				handler.postDelayed(runnable, 1000);
			} else {
				ToolsUtil.toast(this, "手机号不能为空!");
			}
			break;// 返回

		case R.id.btn_queren:// 确认_去修改密码
			verify_code = et_yanzhengma.getText().toString().trim();
				text_NewMima1 = et_new_mima1_byYanZhengMa.getText()
						.toString().trim();
				String text_NewMima2 = et_new_mima2_byYanZhengMa.getText()
						.toString().trim();
				if (TextUtils.isEmpty(text_NewMima1)) {
					ToolsUtil.toast(this, "请输入新密码");
				} else if (!text_NewMima1.equals(text_NewMima2)) {
					ToolsUtil.toast(this, "两次输入新密码不一致!");
				} else {
					Map<String, String> map = new HashMap<String, String>();
					map.put("brand_tel", getSharedPreferenceValue("brand_tel"));
					map.put("type", "1");//1支付密码2登录密码
					map.put("password", text_NewMima1);
					map.put("auth_code", verify_code);
					Long time = null;
					String jm = null;
					try {

						time =System.currentTimeMillis();
				    	System.out.println(time);
				     	String miwen=getSharedPreferenceValue("brand_tel")+"|$|"+"1"+"|$|"+text_NewMima1+"|$|"+verify_code+"|$|"+time.toString();
				     	jm= AESUtil.encrypt(miwen);
						
					} catch (Exception e) {
						
					}
					
					map.put("request_time",""+time);
					map.put("sign", jm);
					String json = JsonUtils.toJson(map);
					HttpUtils.getXiuGaiMiMa(getXiuGaiMiMa,json);
				}

			break;
		default:
			break;
		}
	}
	
	JsonHttpResponseHandler getXiuGaiMiMa = new JsonHttpResponseHandler() {

		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
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
				Toast.makeText(XiuGaiZhiFuMiMaActivity.this, "密码修改成功！",Toast.LENGTH_LONG).show();
				XiuGaiZhiFuMiMaActivity.this.finish();
				
			}
			else if (status.equals("111111")) {
				putSharedPreferenceValue("isLoged", "0");
				Toast.makeText(XiuGaiZhiFuMiMaActivity.this, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
				Intent itziliao = new Intent();
				itziliao.setClass(XiuGaiZhiFuMiMaActivity.this, DuoLeMainActivity.class);
				startActivity(itziliao);
			}     else// 失败
			{
				 Tools.Notic(XiuGaiZhiFuMiMaActivity.this, desc+"", null);
				// Tools.Notic(B5_MyActivity.this, error+"", null);
			}

		}

	};
	
	JsonHttpResponseHandler getCode = new JsonHttpResponseHandler() {

		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
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
				Toast.makeText(XiuGaiZhiFuMiMaActivity.this, "验证码已发送！",Toast.LENGTH_LONG).show();
				
			}
			else if (status.equals("111111")) {
				putSharedPreferenceValue("isLoged", "0");
				Toast.makeText(XiuGaiZhiFuMiMaActivity.this, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
				Intent itziliao = new Intent();
				itziliao.setClass(XiuGaiZhiFuMiMaActivity.this, DuoLeMainActivity.class);
				startActivity(itziliao);
			}     else// 失败
			{
				 Tools.Notic(XiuGaiZhiFuMiMaActivity.this, desc+"", null);
				// Tools.Notic(B5_MyActivity.this, error+"", null);
			}

		}

	};

	/**
	 * 验证码时间倒计时
	 */
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			time--;
			if (time >= 0) {
				tv_getCode_time.setText("   " + time + "s   ");
				handler.postDelayed(this, 1000);
			} else {
				tv_getCode_time.setVisibility(View.GONE);
				txt_yanzhengma.setVisibility(View.VISIBLE);
				txt_yanzhengma.setText("  重新获取    ");
			}
		}
	};

}
