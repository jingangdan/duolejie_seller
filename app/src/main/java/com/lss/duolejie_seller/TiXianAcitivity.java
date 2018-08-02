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

public class TiXianAcitivity extends BaseActivity {
	private EditText et_xingming,et_zhifubao,et_tixianjine;
	private TextView tv_keti,tv_tijiaoshenqin;
	private ImageView im_loginback;

//	private List<VideoList> tiXianJiLus = new ArrayList<VideoList>();
//	private LiShiShouCangAdapter adapter;
	int startRowIndex = 1;
	private Handler mHandler = new Handler();// 异步加载或刷新

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_tixian);
		initView();
	}

	private void initView() {
		im_loginback = (ImageView) findViewById(R.id.im_loginback);
		et_xingming = (EditText) findViewById(R.id.et_xingming);
		et_zhifubao = (EditText) findViewById(R.id.et_zhifubao);
		et_tixianjine = (EditText) findViewById(R.id.et_tixianjine);
		tv_keti = (TextView) findViewById(R.id.tv_keti);
		tv_tijiaoshenqin = (TextView) findViewById(R.id.tv_tijiaoshenqin);

		try {
			tv_keti.setText(getSharedPreferenceValue("brand_money"));
		} catch (Exception e) {
			// TODO: handle exception
		}
		setListener(im_loginback,tv_tijiaoshenqin);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.im_loginback:
			TiXianAcitivity.this.finish();
			break;
		case R.id.tv_tijiaoshenqin:
			String name = et_xingming.getText().toString().trim();
			String zhanghu = et_zhifubao.getText().toString().trim();
			String jine = et_tixianjine.getText().toString().trim();

			if (TextUtils.isEmpty(name)) {
				Toast.makeText(TiXianAcitivity.this, "真实姓名不能为空", Toast.LENGTH_LONG).show();
			} else if (TextUtils.isEmpty(zhanghu)) {
				Toast.makeText(TiXianAcitivity.this, "支付宝账户不能为空", Toast.LENGTH_LONG).show();
			} else if (TextUtils.isEmpty(jine)) {
				Toast.makeText(TiXianAcitivity.this, "提现金额不能为空", Toast.LENGTH_LONG).show();
			}else {
				Map<String, String> map = new HashMap<String, String>();
				map.put("line_brand_id", getSharedPreferenceValue("id"));
				map.put("brand_token", getSharedPreferenceValue("brand_token"));
				map.put("money", jine);
				map.put("name", name);
				map.put("account", zhanghu);
	Long time = null;
				String jm = null;
				try {

					time =System.currentTimeMillis();
			    	System.out.println(time);
			     	String miwen=getSharedPreferenceValue("id")+"|$|"+getSharedPreferenceValue("brand_token")+"|$|"+jine+"|$|"+name+"|$|"+zhanghu+"|$|"+time.toString();
			     	jm= AESUtil.encrypt(miwen);
					
				} catch (Exception e) {
					
				}
				
				map.put("request_time",""+time);
				map.put("sign", jm);
				String json = JsonUtils.toJson(map);
				HttpUtils.getTiXian(res_TiXian,json);
			}
			break;
		default:
			break;
		}
		super.onClick(v);
	}
	

	
	JsonHttpResponseHandler res_TiXian = new JsonHttpResponseHandler() {

		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			// TODO Auto-generated method stub
			super.onSuccess(statusCode, headers, response);
			RequestDailog.closeDialog();
			String status = null;
			String desc = null;
			try {
//				data = response.getJSONObject("data");
				status = response.getString("status");
				desc = response.getString("desc");
//				brand_token = response.getString("brand_token");

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (status.equals("0"))// 成功
			{
				Toast.makeText(TiXianAcitivity.this, "提现申请成功,请耐心等待！",Toast.LENGTH_LONG).show();
				TiXianAcitivity.this.finish();

			}
			else if (status.equals("111111")) {
				putSharedPreferenceValue("isLoged", "0");
				Toast.makeText(TiXianAcitivity.this, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
				Intent itziliao = new Intent();
				itziliao.setClass(TiXianAcitivity.this, DuoLeMainActivity.class);
				startActivity(itziliao);
			}   else// 失败
			{
				 Tools.Notic(TiXianAcitivity.this, desc+"", null);
			}

		}

	};


}
