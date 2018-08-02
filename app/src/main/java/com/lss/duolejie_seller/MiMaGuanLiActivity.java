package com.lss.duolejie_seller;

/**
 * 首页界面 lss 6.17
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lss.duolejie_seller.base.BaseActivity;

public class MiMaGuanLiActivity extends BaseActivity {
	private ImageView im_loginback;
	private LinearLayout ll_dlmmgl,ll_zfmmgl;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_mimaguanli);
		initView();
	}

	private void initView() {
		im_loginback = (ImageView) findViewById(R.id.im_loginback);
		ll_dlmmgl = (LinearLayout) findViewById(R.id.ll_dlmmgl);
		ll_zfmmgl = (LinearLayout) findViewById(R.id.ll_zfmmgl);
		setListener(ll_zfmmgl,im_loginback,ll_dlmmgl);
		}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.im_loginback:
			MiMaGuanLiActivity.this.finish();
			break;
		case R.id.ll_dlmmgl:
			Intent intent_to_login = new Intent(MiMaGuanLiActivity.this,XiuGaiDengLuMiMaActivity.class);
			startActivity(intent_to_login);
			break;
		case R.id.ll_zfmmgl:
			Intent intentsa = new Intent(MiMaGuanLiActivity.this,XiuGaiZhiFuMiMaActivity.class);
			startActivity(intentsa);
			break;
		default:
			break;
		}
		super.onClick(v);
	}
	

}
