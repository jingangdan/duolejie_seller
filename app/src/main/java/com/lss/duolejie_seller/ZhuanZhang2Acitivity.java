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
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.lss.duolejie_seller.base.BaseActivity;
import com.lss.duolejie_seller.utils.AESUtil;
import com.lss.duolejie_seller.utils.AnimateFirstDisplayListener;
import com.lss.duolejie_seller.utils.HttpUtils;
import com.lss.duolejie_seller.utils.ImageOptions;
import com.lss.duolejie_seller.utils.JsonUtils;
import com.lss.duolejie_seller.utils.RequestDailog;
import com.lss.duolejie_seller.utils.Tools;
import com.lss.duolejie_seller.view.CircularImage;
import com.lss.duolejie_seller.view.XListView;
import com.lss.duolejie_seller.view.XListView.IXListViewListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ZhuanZhang2Acitivity extends BaseActivity {
	private CircularImage tv_tx;
	private TextView tv_namefhone,tv_wangjimima,tv_denglu;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();// 图片缓存
	private ImageView im_loginback,im_e2_isshow;
	private EditText et_e2_shoujihao,et_e2_mima;
	private boolean isshow=false;
	private String mima = "",shoujihao = "",jine="",user_id = "",touxiang = "",nick_name = "";

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_zhuanzhang2);
		initView();
	}

	private void initView() {
		im_loginback = (ImageView) findViewById(R.id.im_loginback);
		tv_tx = (CircularImage) findViewById(R.id.tv_tx);
		tv_namefhone = (TextView) findViewById(R.id.tv_namefhone);
		et_e2_shoujihao = (EditText) findViewById(R.id.et_e2_shoujihao);//手机号
		et_e2_mima = (EditText) findViewById(R.id.et_e2_mima);//密码
		im_e2_isshow = (ImageView) findViewById(R.id.im_e2_isshow);//显示密码
		tv_denglu = (TextView) findViewById(R.id.tv_denglu);//登录
		try {
			et_e2_mima.setTransformationMethod(PasswordTransformationMethod.getInstance());
		} catch (Exception e) {
			// TODO: handle exception
		}
		tv_wangjimima = (TextView) findViewById(R.id.tv_wangjimima);//忘记密码
		shoujihao = getIntent().getStringExtra("user_tel");
		user_id = getIntent().getStringExtra("user_id");
		nick_name = getIntent().getStringExtra("nick_name");
		try {
			tv_namefhone.setText(nick_name+"  "+shoujihao);
			touxiang = getIntent().getStringExtra("user_header");
			ImageLoader.getInstance().displayImage(touxiang, tv_tx,ImageOptions.getOpstion(), animateFirstListener);
		} catch (Exception e) {
			
		}
		setListener(im_loginback,tv_denglu,im_e2_isshow,tv_wangjimima);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.im_loginback:
			ZhuanZhang2Acitivity.this.finish();
			break;
		case R.id.tv_denglu://
			mima = et_e2_mima.getText().toString().trim();
			jine = et_e2_shoujihao.getText().toString().trim();
			if (TextUtils.isEmpty(jine)) {
				Toast.makeText(ZhuanZhang2Acitivity.this, "金额不能为空", Toast.LENGTH_LONG).show();
			}else if (TextUtils.isEmpty(mima)) {
				Toast.makeText(ZhuanZhang2Acitivity.this, "支付密码不能为空", Toast.LENGTH_LONG).show();
			}else{
//				RequestDailog.showDialog(this, "正在登录请稍后");
				Map<String, String> map = new HashMap<String, String>();
				map.put("line_brand_id",getSharedPreferenceValue("id"));
				map.put("brand_token",getSharedPreferenceValue("brand_token"));
				map.put("user_id", user_id);
				map.put("money", jine);
				map.put("pay_password", mima);
	Long time = null;
				String jm = null;
				try {

					time =System.currentTimeMillis();
			    	System.out.println(time);
			     	String miwen=getSharedPreferenceValue("id")+"|$|"+getSharedPreferenceValue("brand_token")+"|$|"+user_id+"|$|"+jine+"|$|"+mima+"|$|"+time.toString();
			     	jm= AESUtil.encrypt(miwen);
					
				} catch (Exception e) {
					
				}
				
				map.put("request_time",""+time);
				map.put("sign", jm);
				String json = JsonUtils.toJson(map);
				HttpUtils.lineBrandToUserMoney(lineBrandToUserMoney,json);
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
			itziliao.setClass(ZhuanZhang2Acitivity.this, XiuGaiZhiFuMiMaActivity.class);
			startActivity(itziliao);
			break;
		default:
			break;
		}
		super.onClick(v);
	}
	

	
	JsonHttpResponseHandler lineBrandToUserMoney = new JsonHttpResponseHandler() {

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
				Intent instss = new Intent(ZhuanZhang2Acitivity.this,ZhuanZhang3Acitivity.class);
					instss.putExtra("jine",jine );
					instss.putExtra("shoukuanren", nick_name);
					instss.putExtra("touxiang", touxiang);
				startActivity(instss);
				ZhuanZhang2Acitivity.this.finish();
			}
			else if (status.equals("111111")) {
				putSharedPreferenceValue("isLoged", "0");
				Toast.makeText(ZhuanZhang2Acitivity.this, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
				Intent itziliao = new Intent();
				itziliao.setClass(ZhuanZhang2Acitivity.this, DuoLeMainActivity.class);
				startActivity(itziliao);
			}     else// 失败
			{
				 Tools.Notic(ZhuanZhang2Acitivity.this, desc+"", null);
			}

		}

	};


}
