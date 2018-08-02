package com.lss.duolejie_seller;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import cn.jpush.android.api.JPushInterface;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.lss.duolejie_seller.base.BaseActivity;
import com.lss.duolejie_seller.utils.AESUtil;
import com.lss.duolejie_seller.utils.HttpUtils;
import com.lss.duolejie_seller.utils.HttpUtilsx;
import com.lss.duolejie_seller.utils.JsonUtils;
import com.lss.duolejie_seller.utils.RequestDailog;
import com.lss.duolejie_seller.utils.Tools;
/**
 * 登录界面
 * @author lss
 *
 */
public class B5_1_LoginActivity extends BaseActivity {
	private Context mContext = B5_1_LoginActivity.this;
	private ImageView im_loginback,im_e2_isshow;
	private TextView tv_denglu,tv_wangjimima;
	private EditText et_e2_shoujihao,et_e2_mima;
	private boolean isshow=false;
	public String mobile = "", mima = "";
	private RequestQueue mRequestQueue;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView(R.layout.ui_login);
		mRequestQueue = Volley.newRequestQueue(this);	
		initView();
	}

	private void initView() {
		im_loginback = (ImageView) findViewById(R.id.im_loginback);//设置
		tv_denglu = (TextView) findViewById(R.id.tv_denglu);//登录
		et_e2_shoujihao = (EditText) findViewById(R.id.et_e2_shoujihao);//手机号
		et_e2_mima = (EditText) findViewById(R.id.et_e2_mima);//密码
		im_e2_isshow = (ImageView) findViewById(R.id.im_e2_isshow);//显示密码
		try {
			et_e2_mima.setTransformationMethod(PasswordTransformationMethod.getInstance());
		} catch (Exception e) {
			// TODO: handle exception
		}
		tv_wangjimima = (TextView) findViewById(R.id.tv_wangjimima);//忘记密码
		
		setListener(im_loginback,tv_denglu,im_e2_isshow,tv_wangjimima);
	}


	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.im_loginback://返回
			B5_1_LoginActivity.this.finish();
			break;
		case R.id.tv_denglu://
			mobile = et_e2_shoujihao.getText().toString().trim();
			mima = et_e2_mima.getText().toString().trim();
			String id = "";
try {

	
	 JPushInterface.setDebugMode(true);
    JPushInterface.init(this);

   id =  JPushInterface.getRegistrationID(this);
} catch (Exception e) {

}
			if (TextUtils.isEmpty(mobile)) {
				Toast.makeText(B5_1_LoginActivity.this, "手机号不能为空", Toast.LENGTH_LONG).show();
			} else if (TextUtils.isEmpty(mima)) {
				Toast.makeText(B5_1_LoginActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
			}else{
//				RequestDailog.showDialog(this, "正在登录请稍后");
				Map<String, String> map = new HashMap<String, String>();
				map.put("brand_tel", mobile);
				map.put("password", mima);
				map.put("jpush_id", id);
				map.put("version", "1.0");
				Long time = null;
				String jm = null;
				try {

					time =System.currentTimeMillis();
					
			     	String miwen=mobile+"|$|"+mima+"|$|"+id+"|$|"+"1.0"+"|$|"+time.toString();
			    	jm = AESUtil.encrypt(miwen);
			    	
				} catch (Exception e) {
					
				}
				
				map.put("request_time",""+time);
				map.put("sign", jm);
				String json = JsonUtils.toJson(map);
				HttpUtils.getDengLu(getDengLu,json);
			}
			
			break;
		case R.id.im_e2_isshow://显示密码
			
			if (isshow) {
				isshow = false;
				//隐藏密码
				et_e2_mima.setTransformationMethod(PasswordTransformationMethod.getInstance());
				im_e2_isshow.setImageResource(R.drawable.xianshiyincang);
			}else {
				isshow = true;
				//显示密码      
				et_e2_mima.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
				im_e2_isshow.setImageResource(R.drawable.eyexian);
			}
			break;
		case R.id.tv_wangjimima://忘记密码
			Intent itziliao = new Intent();
			itziliao.setClass(B5_1_LoginActivity.this, WangJiMiMaActivity.class);
			startActivity(itziliao);
			break;
		default:
			break;
		}
	}

	
	JsonHttpResponseHandler getDengLu = new JsonHttpResponseHandler() {

		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			String status = null;
			JSONObject data = null;
			String desc = null;
			String brand_token = null;
			try {
				status = response.getString("status");
				desc = response.getString("desc");
				brand_token = response.getString("brand_token");
				data = response.getJSONObject("data");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				
			}
			if (status.equals("0"))// 成功
			{
				
				try {
					putSharedPreferenceValue("latitude",data.getString("latitude"));
					putSharedPreferenceValue("brand_tel",data.getString("brand_tel"));
					putSharedPreferenceValue("brand_ip_address",data.getString("brand_ip_address"));
					putSharedPreferenceValue("brand_href",data.getString("brand_href"));
				putSharedPreferenceValue("recommend_user_id",data.getString("recommend_user_id"));
				putSharedPreferenceValue("consume_num",data.getString("consume_num"));
				putSharedPreferenceValue("create_time",data.getString("create_time"));
				putSharedPreferenceValue("star_num",data.getString("star_num"));
				putSharedPreferenceValue("brand_ip",data.getString("brand_ip"));
				putSharedPreferenceValue("qrcode",data.getString("qrcode"));
				putSharedPreferenceValue("brand_type",data.getString("brand_type"));
				putSharedPreferenceValue("brand_money",data.getString("brand_money"));
				putSharedPreferenceValue("phone",data.getString("phone"));
				putSharedPreferenceValue("brand_name",data.getString("brand_name"));
				putSharedPreferenceValue("md5",data.getString("md5"));
				putSharedPreferenceValue("bus_license",data.getString("bus_license"));
				putSharedPreferenceValue("brand_address",data.getString("brand_address"));
				putSharedPreferenceValue("user_id",data.getString("user_id"));
				putSharedPreferenceValue("longitude",data.getString("longitude"));
				putSharedPreferenceValue("is_own",data.getString("is_own"));
				putSharedPreferenceValue("zhifubao",data.getString("zhifubao"));
				putSharedPreferenceValue("id",data.getString("id"));
				putSharedPreferenceValue("is_open",data.getString("is_open"));
				putSharedPreferenceValue("guide_img",data.getString("guide_img"));
				putSharedPreferenceValue("password",data.getString("password"));
				putSharedPreferenceValue("map_address",data.getString("map_address"));
				putSharedPreferenceValue("brand_header",data.getString("brand_header"));
				putSharedPreferenceValue("pay_password",data.getString("pay_password"));
				putSharedPreferenceValue("jpush_id",data.getString("jpush_id"));
				putSharedPreferenceValue("brand_invite",data.getString("brand_invite"));
				putSharedPreferenceValue("brand_status",data.getString("brand_status"));
				putSharedPreferenceValue("brand_introduction",data.getString("brand_introduction"));
				putSharedPreferenceValue("qrcode_content",data.getString("qrcode_content"));
				putSharedPreferenceValue("merchant_name",data.getString("merchant_name"));
				putSharedPreferenceValue("pay_money",data.getString("pay_money"));
				putSharedPreferenceValue("time",data.getString("time"));
				putSharedPreferenceValue("brand_token",brand_token);
				putSharedPreferenceValue("kaiguan", "1");
				
				
				
				
				try {
					putSharedPreferenceValue("isLoged","1");
				} catch (Exception e) {
					
				}
				
				
				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Toast.makeText(B5_1_LoginActivity.this, "登录成功！",Toast.LENGTH_LONG).show();

				Intent intent_to_login = new Intent(B5_1_LoginActivity.this,DuoLeMainActivity.class);
				startActivity(intent_to_login);

			}else if (status.equals("111111")) {
				putSharedPreferenceValue("isLoged", "0");
				 Tools.Notic(B5_1_LoginActivity.this, desc+"", null);
			}  else// 失败
			{
				 Tools.Notic(B5_1_LoginActivity.this, desc+"", null);
				// Tools.Notic(B5_MyActivity.this, error+"", null);
			}

		}

	};
}
