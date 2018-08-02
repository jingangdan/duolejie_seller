package com.lss.duolejie_seller;

/**
 * 首页界面 lss 6.17
 */

import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.lss.duolejie_seller.view.CustomDialog;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ShangJiaZhongXinActivity extends BaseActivity {
	private CircularImage cim_mytouxiang;
	private ImageView im_loginback;
	private LinearLayout ll_tiaozhuanbianji;
	private TextView tv_bianji,tv_shangjiaming,tv_shid,tv_yqm;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();// 图片缓存
	private LinearLayout ll_mimaguanli,ll_bangzhu,ll_guanyu,ll_tuichu,ll_zhangdan;
	private RelativeLayout rl_kg;
	private ImageView im_kai,im_guan;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_shangjiazhongxin);
		initView();
	}

	private void initView() {
		cim_mytouxiang = (CircularImage) findViewById(R.id.cim_mytouxiang);
		im_loginback = (ImageView) findViewById(R.id.im_loginback);
		tv_bianji = (TextView) findViewById(R.id.tv_bianji);
		tv_shangjiaming = (TextView) findViewById(R.id.tv_shangjiaming);
		tv_shid = (TextView) findViewById(R.id.tv_shid);
		tv_yqm = (TextView) findViewById(R.id.tv_yqm);
		tv_shangjiaming.setText("商家名称:"+getSharedPreferenceValue("brand_name"));
		tv_shid.setText("商户ID:"+getSharedPreferenceValue("id"));
		tv_yqm.setText("邀请码:"+getSharedPreferenceValue("brand_invite"));
		ll_tiaozhuanbianji = (LinearLayout) findViewById(R.id.ll_tiaozhuanbianji);
		ll_mimaguanli = (LinearLayout) findViewById(R.id.ll_mimaguanli);
		ll_bangzhu = (LinearLayout) findViewById(R.id.ll_bangzhu);
		ll_guanyu = (LinearLayout) findViewById(R.id.ll_guanyu);
		ll_tuichu = (LinearLayout) findViewById(R.id.ll_tuichu);
		ll_zhangdan = (LinearLayout) findViewById(R.id.ll_zhangdan);
		im_kai = (ImageView) findViewById(R.id.im_kai);
		im_guan = (ImageView) findViewById(R.id.im_guan);
		rl_kg = (RelativeLayout) findViewById(R.id.rl_kg);
		try {
			if (getSharedPreferenceValue("kaiguan").equals("1")) {
				im_kai.setVisibility(View.VISIBLE);
				im_guan.setVisibility(View.INVISIBLE);
			}else {
				im_kai.setVisibility(View.INVISIBLE);
				im_guan.setVisibility(View.VISIBLE);
			}
			
		} catch (Exception e) {
			putSharedPreferenceValue("kaiguan", "1");
		}
		try {
			ImageLoader.getInstance().displayImage(
					getSharedPreferenceValue("brand_header"), cim_mytouxiang,
					ImageOptions.getOpstion(), animateFirstListener);
		} catch (Exception e) {
			
		}
		setListener(rl_kg,cim_mytouxiang,im_loginback,ll_tiaozhuanbianji,tv_bianji,ll_mimaguanli,ll_bangzhu,ll_zhangdan,ll_guanyu,ll_tuichu);
		}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.im_loginback:
			ShangJiaZhongXinActivity.this.finish();
			break;
		case R.id.ll_tiaozhuanbianji:
			Intent intent_to_login = new Intent(ShangJiaZhongXinActivity.this,BianJiZiLiaoActivity.class);
			startActivity(intent_to_login);
			ShangJiaZhongXinActivity.this.finish();
			break;
		case R.id.tv_bianji:
			Intent intentsa = new Intent(ShangJiaZhongXinActivity.this,BianJiZiLiaoActivity.class);
			startActivity(intentsa);
			ShangJiaZhongXinActivity.this.finish();
			break;
		case R.id.ll_mimaguanli:
			Intent intes = new Intent(ShangJiaZhongXinActivity.this,MiMaGuanLiActivity.class);
			startActivity(intes);
			break;
		case R.id.ll_bangzhu:
			Intent intentssa = new Intent(ShangJiaZhongXinActivity.this,DuoLeBangZhuActivity.class);
			startActivity(intentssa);
			break;
		case R.id.ll_guanyu:
			Intent intentsea = new Intent(ShangJiaZhongXinActivity.this,DuoLeGuanYuActivity.class);
			startActivity(intentsea);
			break;
		case R.id.ll_zhangdan:
			Intent insts = new Intent(ShangJiaZhongXinActivity.this,B4_OrderActivity.class);
			startActivity(insts);
			break;
		case R.id.ll_tuichu:
			CustomDialog.Builder builder = new CustomDialog.Builder(this);
			builder.setMessage("确定退出？");
			//builder.setTitle("提示");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					//设置你的操作事项
					Map<String, String> map = new HashMap<String, String>();
					map.put("line_brand_id", getSharedPreferenceValue("id"));
					Long time = null;
					String jm = null;
					try {

						time =System.currentTimeMillis();
				    	System.out.println(time);
				     	String miwen=getSharedPreferenceValue("id")+"|$|"+time.toString();
				     	jm= AESUtil.encrypt(miwen);
						
					} catch (Exception e) {
						
					}
					
					map.put("request_time",""+time);
					map.put("sign", jm);
					String json = JsonUtils.toJson(map);
					HttpUtils.getTuiChu(getTuiChu,json);
					
				}
			});

			builder.setNegativeButton("取消",
					new android.content.DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});

			builder.create().show();

			break;
		case R.id.rl_kg:
			if (getSharedPreferenceValue("kaiguan").equals("1")) {
				putSharedPreferenceValue("kaiguan","0");
				im_kai.setVisibility(View.INVISIBLE);
				im_guan.setVisibility(View.VISIBLE);
			}else {
				putSharedPreferenceValue("kaiguan","1");
				im_kai.setVisibility(View.VISIBLE);
				im_guan.setVisibility(View.INVISIBLE);
			}
			
			break;
		default:
			break;
		}
		super.onClick(v);
	}
	
	JsonHttpResponseHandler getTuiChu = new JsonHttpResponseHandler() {

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


				putSharedPreferenceValue("isLoged", "0");
				Toast.makeText(ShangJiaZhongXinActivity.this, "退出成功！",Toast.LENGTH_LONG).show();
				Intent itziliao = new Intent();
				itziliao.setClass(ShangJiaZhongXinActivity.this, DuoLeMainActivity.class);
				startActivity(itziliao);
				ShangJiaZhongXinActivity.this.finish();
				/*try {
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/


			}
			else if (status.equals("111111")) {
				try {
					System.exit(0);
				} catch (Exception e) {
					// TODO: handle exception
				}
				putSharedPreferenceValue("isLoged", "0");
				Toast.makeText(ShangJiaZhongXinActivity.this, "您的账号已在其他地方登录", Toast.LENGTH_LONG).show();
				Intent itziliao = new Intent();
				itziliao.setClass(ShangJiaZhongXinActivity.this, DuoLeMainActivity.class);
				startActivity(itziliao);
			}   else// 失败
			{
				 Tools.Notic(ShangJiaZhongXinActivity.this, desc+"", null);
				// Tools.Notic(B5_MyActivity.this, error+"", null);
			}

		}

	};

}
