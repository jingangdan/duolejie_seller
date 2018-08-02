package com.lss.duolejie_seller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jdbc.camerademo.decode.ZBarScanCallBack;
import com.jdbc.camerademo.view.ScanFragment;

/**
 * 
 * @author xiaomai
 * 
 */
public class SaoMiaoActivity extends FragmentActivity implements
		ZBarScanCallBack {

	private FragmentManager fragmentManage;
	private FragmentTransaction transaction;
	ScanFragment scaFrahment;
	private LinearLayout ll;
	private ImageView im_loginback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		im_loginback = (ImageView) findViewById(R.id.im_loginback);
		im_loginback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SaoMiaoActivity.this.finish();
			}
		});
		fragmentManage = getSupportFragmentManager();
		transaction = fragmentManage.beginTransaction();
		scaFrahment = new ScanFragment();
		transaction.add(R.id.ll, scaFrahment);
		transaction.commit();
	}

	@Override
	public void CallBack(String string) {
		// TODO Auto-generated method stub
		onScanStop();
		Intent in = new Intent(SaoMiaoActivity.this, IntentActivity.class);
		in.putExtra("string", string);
		startActivity(in);
	}

	@Override
	public void onScanStart() {
		// TODO Auto-generated method stub
		scaFrahment.onScanStart();
	}

	@Override
	public void onScanStop() {
		// TODO Auto-generated method stub
		scaFrahment.onScanStop();
	}
}
