package com.lss.duolejie_seller;

/**
 * 首页界面 lss 6.17
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.lss.duolejie_seller.base.BaseActivity;
import com.lss.duolejie_seller.utils.AESUtil;
import com.lss.duolejie_seller.utils.HttpUtils;
import com.lss.duolejie_seller.utils.JsonUtils;
import com.lss.duolejie_seller.utils.RequestDailog;
import com.lss.duolejie_seller.utils.Tools;
import com.lss.duolejie_seller.view.XListView;
import com.lss.duolejie_seller.view.XListView.IXListViewListener;

public class ZhuanZhang1Acitivity extends BaseActivity {
	private EditText et_sjh;
	private TextView tv_xiayibu;
	private ImageView im_loginback;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_zhuanzhang1);
		initView();
	}

	private void initView() {
		im_loginback = (ImageView) findViewById(R.id.im_loginback);
		
		et_sjh = (EditText) findViewById(R.id.et_sjh);
		tv_xiayibu = (TextView) findViewById(R.id.tv_xiayibu);
		setListener(im_loginback,tv_xiayibu);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.im_loginback:
			ZhuanZhang1Acitivity.this.finish();
			break;
		case R.id.tv_xiayibu:
			String name = et_sjh.getText().toString().trim();

			if (TextUtils.isEmpty(name)) {
				Toast.makeText(ZhuanZhang1Acitivity.this, "对方账户不能为空", Toast.LENGTH_LONG).show();
			} else {
				Map<String, String> map = new HashMap<String, String>();
				map.put("user_id_or_tel", name);
	Long time = null;
				String jm = null;
				try {

					time =System.currentTimeMillis();
			    	System.out.println(time);
			     	String miwen=name+"|$|"+time.toString();
			     	jm= AESUtil.encrypt(miwen);
					
				} catch (Exception e) {
					
				}
				
				map.put("request_time",""+time);
				map.put("sign", jm);
				String json = JsonUtils.toJson(map);
				HttpUtils.getUserDataInfo(getUserDataInfo,json);
			}
			break;
		default:
			break;
		}
		super.onClick(v);
	}
	

	
	JsonHttpResponseHandler getUserDataInfo = new JsonHttpResponseHandler() {

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
				
				/*"data": {
			    "nick_name": "用户昵称",
			    "user_tel": "用户手机号",
			    "user_header": "用户头像"
		     }*/

				Intent instss = new Intent(ZhuanZhang1Acitivity.this,ZhuanZhang2Acitivity.class);
				try {
					instss.putExtra("nick_name", data.getString("nick_name"));
					instss.putExtra("user_tel", data.getString("user_tel"));
					instss.putExtra("user_header", data.getString("user_header"));
					instss.putExtra("user_id", data.getString("user_id"));

				} catch (JSONException e) {
					
				}
				startActivity(instss);
				ZhuanZhang1Acitivity.this.finish();

			}
			else if (status.equals("111111")) {
				putSharedPreferenceValue("isLoged", "0");
				Toast.makeText(ZhuanZhang1Acitivity.this, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
				Intent itziliao = new Intent();
				itziliao.setClass(ZhuanZhang1Acitivity.this, DuoLeMainActivity.class);
				startActivity(itziliao);
			}     else// 失败
			{
				 Tools.Notic(ZhuanZhang1Acitivity.this, desc+"", null);
			}

		}

	};


}
